package com.aqi.admin.controller;

import com.aqi.admin.entity.dto.SysMenuDTO;
import com.aqi.admin.entity.vo.Option;
import com.aqi.admin.entity.vo.Route;
import com.aqi.admin.entity.vo.SysMenuVo;
import com.aqi.admin.service.ISysMenuService;
import com.aqi.common.core.entity.R;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/menu")
@Api(value = "系统菜单表", tags = "系统菜单接口")
@RequiredArgsConstructor
public class SysMenuController {
    private final ISysMenuService sysMenuService;

    @ApiOperation(value = "根据角色id数组获取菜单数据")
    @PostMapping("/queryMenusByRoleIds")
    public R<List<Route>> queryMenusByRoleIds(@RequestBody Long[] roleIds) {
        List<Route> routes = sysMenuService.queryMenusByRoleIds(roleIds);
        return R.data(routes);
    }

    @ApiOperation(value = "获取菜单下拉树列表")
    @GetMapping("/menuTreeSelect")
    public R<List<Option>> menuTreeSelect() {
        List<Option> treeSelectList = sysMenuService.menuTreeSelect();
        return R.data(treeSelectList);
    }


    @ApiOperation(value = "根据角色id获取菜单数据")
    @GetMapping("/getRoleMenuCheckedIds")
    public R<List<String>> getRoleMenuCheckedIds(@ApiParam(value = "角色id", required = true) @RequestParam(name = "roleId", defaultValue = "") Long roleId) {
        List<String> list = sysMenuService.getRoleMenuCheckedIds(roleId);
        return R.data(list);
    }

    @RequiresPermissions("system:menu:list")
    @ApiOperation(value = "查询菜单列表")
    @GetMapping("/queryMenus")
    public R<List<SysMenuVo>> queryMenus(SysMenuDTO sysMenuDTO) {
        List<SysMenuVo> sysMenus = sysMenuService.queryMenus(sysMenuDTO);
        return R.data(sysMenus);
    }

    @RequiresPermissions("system:menu:add")
    @ApiOperation(value = "菜单新增")
    @PostMapping("/addMenu")
    @SysLog
    public R addMenu(@RequestBody SysMenuDTO sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return R.ok();
    }

    @RequiresPermissions("system:menu:delete")
    @ApiOperation(value = "删除菜单")
    @SysLog
    @DeleteMapping("/delMenuByMenuId")
    public R delMenuByMenuId(@ApiParam(value = "菜单id", required = true) @RequestParam(value = "menuId") Long menuId) {
        sysMenuService.delMenuByMenuId(menuId);
        return R.ok();
    }

    @RequiresPermissions("system:menu:update")
    @ApiOperation(value = "菜单更新")
    @PutMapping("/updateMenu")
    @SysLog
    public R updateMenu(@RequestBody SysMenuDTO sysMenuDTO) {
        sysMenuService.saveMenu(sysMenuDTO);
        return R.ok();
    }
}
