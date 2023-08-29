package com.aqi.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aqi.admin.entity.base.SysRole;
import com.aqi.admin.entity.base.SysUser;
import com.aqi.admin.entity.base.SysUserRole;
import com.aqi.admin.entity.dto.LoginDTO;
import com.aqi.admin.entity.dto.SysUserDTO;
import com.aqi.admin.entity.vo.CaptchaData;
import com.aqi.admin.mapper.SysUserMapper;
import com.aqi.admin.service.ISysRoleService;
import com.aqi.admin.service.ISysUserRoleService;
import com.aqi.admin.service.ISysUserService;
import com.aqi.common.core.constant.CommonConstant;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.core.utils.PasswordUtil;
import com.aqi.common.redis.constant.CacheConstant;
import com.aqi.common.redis.utils.RedisUtils;
import com.aqi.common.secure.entity.SystemUser;
import com.aqi.common.secure.utils.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wf.captcha.SpecCaptcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysUserRoleService userRoleService;

    private final ISysRoleService roleService;

    private final RedisUtils redisUtils;

    // 账号锁定30分钟
    private static int LOCK_TIME = 30;


    @Override
    public String login(LoginDTO loginDTO) {
        // 新增密码锁定校验
        //登录进来，第一步判断账户是否锁定
        Object lock = redisUtils.get(CacheConstant.USER_LOCK + loginDTO.getUsername());
        if (lock != null && "lock".equals(lock.toString())) {
            throw new RuntimeException("该帐号登录已锁定,请 " + LOCK_TIME + "分钟后再试！");
        }
        //第二步判断是否错误次数达到五次，如果达到5次，那就锁定账户，锁定值为lock，锁定时间30分钟
        Object lockTimes = redisUtils.get(CacheConstant.USER_LOCK_TIMES + loginDTO.getUsername());
        if (lockTimes != null && "5".equals(lockTimes.toString())) {
            redisUtils.set(CacheConstant.USER_LOCK + loginDTO.getUsername(), "lock", 30 * 60);
            throw new RuntimeException("该帐号登录已锁定,请 " + LOCK_TIME + "分钟后再试！");
        }
        // 校验验证码
        String code = loginDTO.getCode();
        String key = loginDTO.getKey();
        String redisCode = (String) redisUtils.get(CacheConstant.CAPTCHA_CACHE_PRE + key);
        if (StrUtil.isBlank(redisCode)) {
            throw new RuntimeException("验证码已过期");
        }
        if (!code.equals(redisCode)) {
            throw new RuntimeException("验证码错误");
        }
        String username = loginDTO.getUsername();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = getOne(queryWrapper);
        if (sysUser == null) {
            throw new RuntimeException("登录用户：" + username + " 不存在");
        }
        if (CommonConstant.DISABLE.equals(sysUser.getStatus())) {
            throw new RuntimeException("对不起，您的账号：" + username + " 已停用");
        }
        if (!SecureUtil.matchesPassword(loginDTO.getPassword(), sysUser.getUserPassword())) {
            if (redisUtils.exists(CacheConstant.USER_LOCK_TIMES + loginDTO.getUsername())) {
                String num = (String) redisUtils.get(CacheConstant.USER_LOCK_TIMES + loginDTO.getUsername());
                int i = Integer.parseInt(num);
                i = i + 1;
                redisUtils.set(CacheConstant.USER_LOCK_TIMES + loginDTO.getUsername(), String.valueOf(i), 60 * 5);
            } else {
                redisUtils.set(CacheConstant.USER_LOCK_TIMES + loginDTO.getUsername(), "1", 60 * 5);
            }

            throw new RuntimeException("密码错误,第 " + redisUtils.get(CacheConstant.USER_LOCK_TIMES + loginDTO.getUsername()) + " 次登录失败");
        }
        SystemUser systemUser = this.convertSystemUser(sysUser);
        String token = createToken(systemUser);
        return token;
    }

    @Override
    public Page<SysUser> queryUsersByPage(SysUserDTO sysUserDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysUserDTO.getUserName()), SysUser::getUserName, sysUserDTO.getUserName());
        Long deptId = sysUserDTO.getDeptId();
        queryWrapper.eq(deptId != null, SysUser::getDeptId, deptId);
        Page<SysUser> page = page(new Page<SysUser>(pageNum, pageSize), queryWrapper);
        return page;
    }

    @Override
    public void delUserByUserId(Long userId) {
        //删除用户之后还要删除用户和角色关联表中的数据
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        userRoleService.remove(queryWrapper);
        this.removeById(userId);
    }

    @Override
    public void changeUserStatus(Long userId, String userStatus) {
        SysUser sysUser = new SysUser();
        sysUser.setStatus(userStatus);
        sysUser.setUserId(userId);
        updateById(sysUser);
    }

    @Override
    public void updateUser(SysUserDTO sysUserDTO) {
        Long[] roleIds = sysUserDTO.getRoleIds();
        if (sysUserDTO.getRoleIds() == null || sysUserDTO.getRoleIds().length < 1) {
            throw new RuntimeException("用户下必须有权限角色");
        }
        validateUpdateUser(sysUserDTO);
        SysUser sysUser = BeanCopyUtils.copy(sysUserDTO, SysUser.class);
        sysUser.setUserPassword(null);
        updateById(sysUser);

        // 配置角色先删除对应角色再重新添加
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUserRole::getUserId, sysUserDTO.getUserId());
        userRoleService.remove(queryWrapper);
        // 保存用户角色
        this.saveUserRole(roleIds, sysUserDTO.getUserId());
    }

    @Override
    public void createUser(SysUserDTO sysUserDTO) {
        Long userId = IdWorker.getId();
        Long[] roleIds = sysUserDTO.getRoleIds();
        if (sysUserDTO.getRoleIds() == null || sysUserDTO.getRoleIds().length < 1) {
            throw new RuntimeException("用户下必须有权限角色");
        }
        validateCreateUser(sysUserDTO);
        SysUser sysUser = BeanCopyUtils.copy(sysUserDTO, SysUser.class);
        sysUser.setUserPassword(PasswordUtil.encryptFromString(sysUserDTO.getUserPassword()));
        sysUser.setUserId(userId);
        save(sysUser);

        this.saveUserRole(roleIds, userId);
    }

    @Override
    public void resetPassword(Long userId, String userPassword) {
        this.validatePass(userPassword);
        // 密码加密
        userPassword = PasswordUtil.encryptFromString(userPassword);
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setUserPassword(userPassword);
        updateById(sysUser);
    }

    private void validatePass(String newPassword) {
        // 密码复杂度限制
        // 密码必须由字母和数字组成（同时包括数字和数字）；密码长度大于等于8个字符。
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        boolean matches = Pattern.matches(pattern, newPassword);
        if (!matches) {
            throw new RuntimeException("密码必须包含大小写字母数字及特殊字符，且8-20位之间");
        }
    }

    @Override
    public void updateUserPassword(Long userId, String oldPassword, String newPassword) {
        this.validatePass(newPassword);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserId, userId);
        SysUser sysUser = getOne(queryWrapper);
        oldPassword = PasswordUtil.encryptFromString(oldPassword);
        boolean matches = oldPassword.equals(sysUser.getUserPassword());
        // 先校验老密码对不对。
        if (!matches) {
            throw new RuntimeException("原密码不正确");
        }
        String encodeNewPassword = PasswordUtil.encryptFromString(newPassword);
        sysUser.setUserPassword(encodeNewPassword);
        updateById(sysUser);
    }

    @Override
    public CaptchaData getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisUtils.set(CacheConstant.CAPTCHA_CACHE_PRE + key, verCode, CacheConstant.CAPTCHA_CACHE_EXPIRE_TIME);
        // 将key和base64返回给前端
        CaptchaData captchaData = new CaptchaData();
        captchaData.setExpired(1800);
        captchaData.setKey(key);
        captchaData.setImg(specCaptcha.toBase64());
        return captchaData;
    }

    private void saveUserRole(Long[] roleIds, long userId) {
        List<SysUserRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }
        userRoleService.saveBatch(list);

    }

    private void validateCreateUser(SysUserDTO sysUserDTO) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, sysUserDTO.getUserName());
        List<SysUser> list1 = list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list1)) {
            throw new RuntimeException("用户名已被占用");
        }
        LambdaQueryWrapper<SysUser> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(SysUser::getUserEmail, sysUserDTO.getUserEmail());
        List<SysUser> list2 = list(queryWrapper2);
        if (CollectionUtil.isNotEmpty(list2)) {
            throw new RuntimeException("邮箱已被占用");
        }
    }

    private void validateUpdateUser(SysUserDTO sysUserDTO) {
        validateUserName(sysUserDTO);
        validateUserEmail(sysUserDTO);
    }

    private void validateUserEmail(SysUserDTO sysUserDTO) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserEmail, sysUserDTO.getUserEmail());
        List<SysUser> list = list(queryWrapper);
        for (SysUser user : list) {
            if (user.getUserId().longValue() != sysUserDTO.getUserId().longValue()) {
                throw new RuntimeException("邮箱已经被其他用户占用");
            }
        }
    }

    private void validateUserName(SysUserDTO sysUserDTO) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, sysUserDTO.getUserName());
        List<SysUser> list = list(queryWrapper);
        for (SysUser user : list) {
            if (user.getUserId().longValue() != sysUserDTO.getUserId().longValue()) {
                throw new RuntimeException("用户名已经被其他用户占用");
            }
        }
    }

    /**
     * 生成token
     *
     * @param systemUser
     * @return
     */
    private String createToken(SystemUser systemUser) {
        String token = SecureUtil.createToken(systemUser);
        if (StrUtil.isBlank(token)) {
            throw new RuntimeException("生成token失败");
        }
        return token;
    }

    private SystemUser convertSystemUser(SysUser sysUser) {
        SystemUser systemUser = BeanCopyUtils.copy(sysUser, SystemUser.class);
        systemUser.setUserId(String.valueOf(sysUser.getUserId()));
        List<Long> roleIds = this.getRoleIds(sysUser.getUserId());
        if (CollectionUtil.isEmpty(roleIds)) {
            throw new RuntimeException("该用户没有配置任何角色");
        }
        // 转string list，long数据在前端会缺失经度
        systemUser.setRoleIds(roleIds.stream().map(x -> String.valueOf(x)).collect(Collectors.toList()));
        Set<String> roleList = this.getRoleList(roleIds);
        Set<String> permissions = roleService.queryUserPermissions(roleIds);
        Integer dataScope = roleService.getMaxDataScope(roleIds);
        systemUser.setDataScope(dataScope);
        systemUser.setRoles(roleList);
        systemUser.setPermissions(permissions);
        systemUser.setDeptId(String.valueOf(sysUser.getDeptId()));
        return systemUser;
    }

    private List<Long> getRoleIds(Long userId) {
        List<Long> roleList = new ArrayList<>();
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> list = userRoleService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            roleList = list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        }
        return roleList;
    }

    private Set<String> getRoleList(List<Long> roleIds) {
        List<SysRole> sysRoles = roleService.listByIds(roleIds);
        Set<String> roleList = new HashSet<>();
        if (CollectionUtil.isNotEmpty(sysRoles)) {
            for (SysRole sysRole : sysRoles) {
                roleList.add(sysRole.getRoleKey());
            }
        }
        return roleList;
    }
}
