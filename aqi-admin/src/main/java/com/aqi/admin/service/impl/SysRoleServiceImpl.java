package com.aqi.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aqi.admin.entity.base.SysMenu;
import com.aqi.admin.entity.base.SysRole;
import com.aqi.admin.entity.base.SysRoleMenu;
import com.aqi.admin.entity.base.SysUserRole;
import com.aqi.admin.entity.dto.RoleMenuDTO;
import com.aqi.admin.entity.dto.SysRoleDTO;
import com.aqi.admin.entity.vo.SysRoleVo;
import com.aqi.admin.entity.wrapper.SysRoleWrapper;
import com.aqi.admin.mapper.SysRoleMapper;
import com.aqi.admin.service.ISysMenuService;
import com.aqi.admin.service.ISysRoleMenuService;
import com.aqi.admin.service.ISysRoleService;
import com.aqi.admin.service.ISysUserRoleService;
import com.aqi.common.core.constant.CommonConstant;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    private final ISysRoleMenuService roleMenuService;

    private final ISysMenuService menuService;

    private final ISysUserRoleService userRoleService;

    @Override
    public Set<String> queryUserPermissions(List<Long> roleIds) {
        Set<String> permissions = new HashSet<String>();
        for (Long roleId : roleIds) {
            LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
            List<SysRoleMenu> list = roleMenuService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                List<Long> menuIds = list.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
                List<SysMenu> menuList = menuService.listByIds(menuIds);
                for (SysMenu sysMenu : menuList) {
                    if (StrUtil.isNotBlank(sysMenu.getMenuPerms())) {
                        permissions.add(sysMenu.getMenuPerms());
                    }
                }
            }
        }
        return permissions;
    }

    @Override
    public List<SysRoleVo> queryAllRoles() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getStatus, CommonConstant.USE);
        List<SysRole> list = list(queryWrapper);
        return SysRoleWrapper.build().listVO(list);
    }

    @Override
    public List<String> queryRoleIdsByUserId(Long userId) {
        List<String> collect = new ArrayList<>();
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> list = userRoleService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            collect = list.stream().map(x -> String.valueOf(x.getRoleId())).collect(Collectors.toList());
        }
        return collect;
    }

    @Override
    public void addRole(SysRoleDTO sysRoleDTO) {
        long roleId = IdWorker.getId();
        SysRole sysRole = BeanCopyUtils.copy(sysRoleDTO, SysRole.class);
        sysRole.setRoleId(roleId);
        save(sysRole);
    }

    @Override
    public void updateRole(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = BeanCopyUtils.copy(sysRoleDTO, SysRole.class);
        updateById(sysRole);
    }

    @Override
    public void changeRoleStatus(Long roleId, String roleStatus) {
        // 停用操作
        if (CommonConstant.DISABLE.equals(roleStatus)) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getRoleId, roleId);
            List<SysUserRole> list = userRoleService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                // 如果有用户配置了该角色则不能停用，给予提示。
                throw new RuntimeException("有用户配置了该角色导致无法停用，请先让用户删除该角色");
            }
        }
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setStatus(roleStatus);
        updateById(sysRole);
    }

    @Override
    public void updateRoleMenus(RoleMenuDTO roleMenuDTO) {
        //配置菜单先删除对应菜单再重新添加
        LambdaQueryWrapper<SysRoleMenu> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SysRoleMenu::getRoleId, roleMenuDTO.getRoleId());
        roleMenuService.remove(deleteWrapper);

        Long[] menuIds = roleMenuDTO.getMenuIds();
        if (menuIds != null && menuIds.length > 0) {
            List<SysRoleMenu> list = new ArrayList<>();
            for (Long menuId : menuIds) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleMenuDTO.getRoleId());
                roleMenu.setMenuId(menuId);
                list.add(roleMenu);
            }
            roleMenuService.saveBatch(list);
        }

    }

    @Override
    public Integer getMaxDataScope(List<Long> roleIds) {
        List<SysRole> sysRoles = this.listByIds(roleIds);
        // 获取最大权限
        SysRole sysRole = sysRoles.stream().min(Comparator.comparing(x -> x.getDataScope())).orElse(null);
        return sysRole != null ? sysRole.getDataScope() : null;
    }

    @Override
    public Page<SysRole> queryRolesByPage(SysRoleDTO sysRoleDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysRoleDTO.getRoleName()), SysRole::getRoleName, sysRoleDTO.getRoleName());
        queryWrapper.like(StrUtil.isNotBlank(sysRoleDTO.getRoleKey()), SysRole::getRoleKey, sysRoleDTO.getRoleKey());
        Page<SysRole> page = page(new Page<SysRole>(pageNum, pageSize), queryWrapper);
        return page;
    }

    @Override
    public void delRoleByRoleId(Long roleId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, roleId);
        List<SysUserRole> list = userRoleService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new RuntimeException("有用户配置了该角色导致无法删除，请先让用户删除该角色");
        }

        this.removeById(roleId);

        //删除角色之后还要删除角色和菜单关联表中的数据
        LambdaQueryWrapper<SysRoleMenu> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SysRoleMenu::getRoleId, roleId);
        roleMenuService.remove(deleteWrapper);
    }
}
