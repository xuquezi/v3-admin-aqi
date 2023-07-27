package com.aqi.admin.service;



import com.aqi.admin.entity.vo.Option;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysMenu;
import com.aqi.admin.entity.dto.SysMenuDTO;
import com.aqi.admin.entity.vo.Route;
import com.aqi.admin.entity.vo.SysMenuVo;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {
    List<Route> queryMenusByRoleIds(Long[] roleIds);

    List<Option> menuTreeSelect();

    List<String> getRoleMenuCheckedIds(Long roleId);

    List<SysMenuVo> queryMenus(SysMenuDTO sysMenuDTO);

    void saveMenu(SysMenuDTO sysMenu);

    void delMenuByMenuId(Long menuId);
}
