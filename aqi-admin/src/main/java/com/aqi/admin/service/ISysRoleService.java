package com.aqi.admin.service;


import com.aqi.admin.entity.dto.RoleMenuDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysRole;
import com.aqi.admin.entity.dto.SysRoleDTO;
import com.aqi.admin.entity.vo.SysRoleVo;

import java.util.List;
import java.util.Set;

public interface ISysRoleService extends IService<SysRole> {
    Set<String> queryUserPermissions(List<Long> roleIds);

    Page<SysRoleVo> queryRolesByPage(SysRoleDTO sysRoleDTO, Integer pageSize, Integer pageNum);

    void delRoleByRoleId(Long roleId);

    List<SysRoleVo> queryAllRoles();

    List<String> queryRoleIdsByUserId(Long userId);

    void addRole(SysRoleDTO sysRoleDTO);

    void updateRole(SysRoleDTO sysRoleDTO);

    void changeRoleStatus(Long roleId, String roleStatus);

    void updateRoleMenus(RoleMenuDTO roleMenuDTO);

    Integer getMaxDataScope(List<Long> roleIds);
}
