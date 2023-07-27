package com.aqi.admin.controller;

import com.aqi.admin.entity.dto.RoleMenuDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aqi.admin.entity.base.SysRole;
import com.aqi.admin.entity.dto.SysRoleDTO;
import com.aqi.admin.entity.vo.SysRoleVo;
import com.aqi.admin.entity.wrapper.SysRoleWrapper;
import com.aqi.admin.service.ISysRoleService;
import com.aqi.common.core.entity.R;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "系统角色表", tags = "系统角色接口")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/queryAllRoles")
    public R<List<SysRoleVo>> queryAllRoles() {
        List<SysRoleVo> list = sysRoleService.queryAllRoles();
        return R.data(list);
    }

    @RequiresPermissions("system:role:list")
    @ApiOperation(value = "角色分页查询")
    @GetMapping("/page")
    public R<Page<SysRoleVo>> queryRolesByPage(SysRoleDTO sysRoleDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysRole> page = sysRoleService.queryRolesByPage(sysRoleDTO, pageSize, pageNum);
        Page<SysRoleVo> sysRoleVoPage = SysRoleWrapper.build().pageVO(page);
        return R.data(sysRoleVoPage);
    }

    @RequiresPermissions("system:role:delete")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete")
    @SysLog
    public R delRoleByRoleId(@ApiParam(value = "角色id", required = true) @RequestParam(value = "roleId") Long roleId) {
        sysRoleService.delRoleByRoleId(roleId);
        return R.ok();
    }

    @ApiOperation(value = "根据用户id获取角色ids")
    @GetMapping("/queryRoleIdsByUserId")
    public R<List<String>> queryRoleIdsByUserId(@ApiParam(value = "用户id", required = true) @RequestParam(name = "userId", defaultValue = "") Long userId) {
        List<String> roleIds = sysRoleService.queryRoleIdsByUserId(userId);
        return R.data(roleIds);
    }

    @RequiresPermissions("system:role:add")
    @ApiOperation(value = "角色新增")
    @PostMapping("/addRole")
    @SysLog
    public R addRole(@RequestBody SysRoleDTO sysRoleDTO) {
        sysRoleService.addRole(sysRoleDTO);
        return R.ok();
    }

    @RequiresPermissions("system:role:update")
    @ApiOperation(value = "角色修改")
    @PutMapping("/updateRole")
    @SysLog
    public R updateRole(@RequestBody SysRoleDTO sysRoleDTO) {
        sysRoleService.updateRole(sysRoleDTO);
        return R.ok();
    }

    @RequiresPermissions("system:role:auth")
    @ApiOperation(value = "角色权限配置")
    @PutMapping("/updateRoleMenus")
    @SysLog
    public R updateRoleMenus(@RequestBody RoleMenuDTO roleMenuDTO) {
        sysRoleService.updateRoleMenus(roleMenuDTO);
        return R.ok();
    }

    @RequiresPermissions("system:role:stop")
    @ApiOperation(value = "修改角色状态")
    @PutMapping("/changeRoleStatus")
    @SysLog
    public R changeRoleStatus(@ApiParam(value = "角色id", required = true) @RequestParam(value = "roleId") Long roleId, @ApiParam(value = "角色状态", required = true) @RequestParam(value = "roleStatus") String roleStatus) {
        sysRoleService.changeRoleStatus(roleId, roleStatus);
        return R.ok();
    }
}
