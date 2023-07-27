package com.aqi.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aqi.admin.entity.base.SysUser;
import com.aqi.admin.entity.dto.LoginDTO;
import com.aqi.admin.entity.dto.SysUserDTO;
import com.aqi.admin.entity.vo.CaptchaData;
import com.aqi.admin.entity.vo.SysUserVo;
import com.aqi.admin.entity.wrapper.SysUserWrapper;
import com.aqi.admin.service.ISysUserService;
import com.aqi.common.core.entity.R;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.aqi.common.secure.entity.SystemUser;
import com.aqi.common.secure.utils.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "系统用户表", tags = "系统用户接口")
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public R<String> login(@RequestBody LoginDTO loginDTO) {
        String token = sysUserService.login(loginDTO);
        return R.data(token);
    }

    @RequiresPermissions("system:user:list")
    @ApiOperation(value = "用户分页查询")
    @GetMapping("/page")
    public R<Page<SysUserVo>> queryUsersByPage(SysUserDTO sysUserDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysUser> page = sysUserService.queryUsersByPage(sysUserDTO, pageSize, pageNum);
        Page<SysUserVo> sysUserVoPage = SysUserWrapper.build().pageVO(page);
        return R.data(sysUserVoPage);
    }

    @RequiresPermissions("system:user:delete")
    @ApiOperation(value = "用户删除")
    @DeleteMapping("/delete")
    @SysLog
    public R delete(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId") Long userId) {
        sysUserService.delUserByUserId(userId);
        return R.ok();
    }

    @ApiOperation(value = "用户登出")
    @GetMapping("/logout")
    public R logout() {
        return R.ok("登出成功");
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/queryUserInfo")
    public R<SystemUser> queryUserInfo() {
        SystemUser systemUser = SecureUtil.getSystemUser();
        if (systemUser == null) {
            throw new RuntimeException("获取用户信息失败");
        }
        return R.data(systemUser);
    }

    @SysLog
    @RequiresPermissions("system:user:stop")
    @ApiOperation(value = "修改用户状态")
    @PutMapping("/changeUserStatus")
    public R changeUserStatus(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId") Long userId, @ApiParam(value = "用户状态", required = true) @RequestParam(value = "userStatus") String userStatus) {
        sysUserService.changeUserStatus(userId, userStatus);
        return R.ok();
    }

    @RequiresPermissions("system:user:update")
    @ApiOperation(value = "用户更新")
    @PutMapping("/updateUser")
    @SysLog
    public R updateUser(@RequestBody SysUserDTO sysUserDTO) {
        sysUserService.updateUser(sysUserDTO);
        return R.ok();
    }


    @RequiresPermissions("system:user:add")
    @ApiOperation(value = "用户新增")
    @PostMapping("/createUser")
    @SysLog
    public R createUser(@RequestBody SysUserDTO sysUserDTO) {
        sysUserService.createUser(sysUserDTO);
        return R.ok();
    }

    @ApiOperation(value = "用户信息更新")
    @PutMapping("/updateUserProfile")
    @SysLog
    public R updateUserProfile(@RequestBody SysUserDTO sysUserDTO) {
        SysUser sysUser = BeanCopyUtils.copy(sysUserDTO, SysUser.class);
        sysUserService.updateById(sysUser);
        return R.ok();
    }

    @RequiresPermissions("system:user:reset")
    @ApiOperation(value = "密码重置")
    @PutMapping("/resetPassword")
    @SysLog
    public R resetPassword(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId") Long userId, @ApiParam(value = "密码", required = true) @RequestParam(value = "userPassword") String userPassword) {
        sysUserService.resetPassword(userId, userPassword);
        return R.ok();
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping("/updateUserPassword")
    @SysLog
    public R updateUserPassword(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId") Long userId, @ApiParam(value = "原密码", required = true) @RequestParam(value = "oldPassword") String oldPassword, @ApiParam(value = "新密码", required = true) @RequestParam(value = "newPassword") String newPassword) {
        sysUserService.updateUserPassword(userId, oldPassword, newPassword);
        return R.ok();
    }

    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码")
    public R<CaptchaData> getCaptcha() {
        CaptchaData captchaData = sysUserService.getCaptcha();
        return R.data(captchaData);
    }
}
