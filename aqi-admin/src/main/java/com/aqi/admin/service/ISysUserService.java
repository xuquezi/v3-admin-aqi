package com.aqi.admin.service;


import com.aqi.admin.entity.vo.SysUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysUser;
import com.aqi.admin.entity.dto.LoginDTO;
import com.aqi.admin.entity.dto.SysUserDTO;
import com.aqi.admin.entity.vo.CaptchaData;

public interface ISysUserService extends IService<SysUser> {
    String login(LoginDTO loginDTO);

    Page<SysUserVo> queryUsersByPage(SysUserDTO sysUserDTO, Integer pageSize, Integer pageNum);

    void delUserByUserId(Long userId);

    void changeUserStatus(Long userId, String userStatus);

    void updateUser(SysUserDTO sysUserDTO);

    void createUser(SysUserDTO sysUserDTO);

    void resetPassword(Long userId, String userPassword);

    void updateUserPassword(Long userId, String oldPassword, String newPassword);

    CaptchaData getCaptcha();
}
