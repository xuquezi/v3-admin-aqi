package com.aqi.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.aqi.admin.constant.AdminCommonConstant;
import com.aqi.admin.entity.vo.*;
import com.aqi.admin.enums.MenuTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqi.admin.entity.base.SysMenu;
import com.aqi.admin.entity.base.SysRoleMenu;
import com.aqi.admin.entity.dto.SysMenuDTO;
import com.aqi.admin.entity.wrapper.SysMenuWrapper;
import com.aqi.admin.mapper.SysMenuMapper;
import com.aqi.admin.service.ISysMenuService;
import com.aqi.admin.service.ISysRoleMenuService;
import com.aqi.common.core.constant.CommonConstant;
import com.aqi.common.core.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private ISysRoleMenuService roleMenuService;

    @Override
    public List<Route> queryMenusByRoleIds(Long[] roleIds) {
        List<Route> routes = new ArrayList<>();
        List<SysMenu> sysMenuList = new ArrayList<>();
        if (roleIds != null && roleIds.length > 0) {
            for (Long roleId : roleIds) {
                LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
                List<SysRoleMenu> list = roleMenuService.list(queryWrapper);
                if (CollectionUtil.isNotEmpty(list)) {
                    List<Long> menuIds = list.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
                    LambdaQueryWrapper<SysMenu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    menuLambdaQueryWrapper.ne(SysMenu::getMenuType, MenuTypeEnum.TYPE_BUTTON.getCode());
                    menuLambdaQueryWrapper.in(SysMenu::getMenuId, menuIds);
                    List<SysMenu> sysMenus = list(menuLambdaQueryWrapper);
                    if (CollectionUtil.isNotEmpty(sysMenus)) {
                        sysMenuList.addAll(sysMenus);
                    }
                }
            }
        }
        if (CollectionUtil.isNotEmpty(sysMenuList)) {
            // 去重
            sysMenuList = sysMenuList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SysMenu::getMenuId))), ArrayList::new));
            routes = recurRoutes(AdminCommonConstant.TOP_MENU, sysMenuList);
        }
        return routes;
    }

    /**
     * 递归生成菜单路由层级列表
     */
    private List<Route> recurRoutes(Long parentId, List<SysMenu> menuList) {
        return CollectionUtil.emptyIfNull(menuList).stream()
                .filter(menu -> menu.getMenuParentId().equals(parentId))
                .map(menu -> {
                    Route route = new Route();
                    if (MenuTypeEnum.TYPE_MENU.getCode().equals(menu.getMenuType())) {
                        String routeName = StringUtils.capitalize(StrUtil.toCamelCase(menu.getMenuPath(), '-')); // 路由 name 需要驼峰，首字母大写
                        route.setName(routeName); //  根据name路由跳转 this.$router.push({name:xxx})
                    }

                    if (MenuTypeEnum.TYPE_DIR.getCode().equals(menu.getMenuType()) && NumberUtil.equals(menu.getMenuParentId(), AdminCommonConstant.TOP_MENU)) {
                        route.setComponent(AdminCommonConstant.LAYOUT);
                    } else if (MenuTypeEnum.TYPE_DIR.getCode().equals(menu.getMenuType()) && !NumberUtil.equals(menu.getMenuParentId(), AdminCommonConstant.TOP_MENU)) {
                        route.setComponent(AdminCommonConstant.PARENT_VIEW);
                    } else {
                        route.setComponent(menu.getMenuComponent());
                    }
                    route.setPath(menu.getMenuPath());
                    route.setRedirect(menu.getRedirect());
                    Meta meta = new Meta();
                    meta.setTitle(menu.getMenuName());
                    meta.setIcon(menu.getMenuIcon());
                    meta.setHidden(CommonConstant.HIDDEN.equals(menu.getMenuVisible()));
                    meta.setKeepAlive(true);
                    route.setMeta(meta);
                    List<Route> children = recurRoutes(menu.getMenuId(), menuList);
                    route.setChildren(children);
                    return route;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Option> menuTreeSelect() {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysMenu::getMenuOrderNum);
        List<SysMenu> menuList = list(queryWrapper);
        return recurMenuOptions(AdminCommonConstant.TOP_MENU, menuList);
    }

    /**
     * 递归生成菜单下拉层级列表
     */
    private static List<Option> recurMenuOptions(Long parentId, List<SysMenu> menuList) {
        List<Option> menus = CollectionUtil.emptyIfNull(menuList).stream()
                .filter(menu -> menu.getMenuParentId().equals(parentId))
                .map(menu -> new Option(String.valueOf(menu.getMenuId()), menu.getMenuName(), recurMenuOptions(menu.getMenuId(), menuList)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return menus;
    }

    @Override
    public List<String> getRoleMenuCheckedIds(Long roleId) {
        List<String> resultList = new ArrayList<>();
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> list = roleMenuService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            resultList = list.stream().map(x -> String.valueOf(x.getMenuId())).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<SysMenuVo> queryMenus(SysMenuDTO sysMenuDTO) {
        List<SysMenuVo> resultList = new ArrayList<>();
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysMenuDTO.getMenuName()), SysMenu::getMenuName, sysMenuDTO.getMenuName());
        List<SysMenu> list = list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            Set<Long> parentIds = list.stream()
                    .map(SysMenu::getMenuParentId)
                    .collect(Collectors.toSet());

            Set<Long> menuIds = list.stream()
                    .map(SysMenu::getMenuId)
                    .collect(Collectors.toSet());
            // 求差集，得到 parentIds 中 menuIds 没有的元素
            List<Long> rootIds = CollectionUtil.subtractToList(parentIds, menuIds);
            for (Long rootId : rootIds) {
                // 递归
                resultList.addAll(recurMenus(rootId, list));
            }
        }
        return resultList;
    }

    /**
     * 递归生成菜单列表
     */
    private List<SysMenuVo> recurMenus(Long parentId, List<SysMenu> menuList) {
        return CollectionUtil.emptyIfNull(menuList)
                .stream()
                .filter(menu -> menu.getMenuParentId().equals(parentId))
                .map(entity -> {
                    SysMenuVo sysMenuVo = SysMenuWrapper.build().entityVO(entity);
                    List<SysMenuVo> children = recurMenus(entity.getMenuId(), menuList);
                    sysMenuVo.setChildren(children);
                    return sysMenuVo;
                }).collect(Collectors.toList());
    }

    @Override
    public void saveMenu(SysMenuDTO sysMenuDto) {
        SysMenu sysMenu = BeanCopyUtils.copy(sysMenuDto, SysMenu.class);

        if (MenuTypeEnum.TYPE_DIR.getCode().equals(sysMenuDto.getMenuType())) {
            if (NumberUtil.equals(sysMenuDto.getMenuParentId(), 0) && !sysMenuDto.getMenuPath().startsWith("/")) {
                // 一级目录需以 / 开头
                sysMenu.setMenuPath("/" + sysMenu.getMenuPath());
            }
        } else if (MenuTypeEnum.TYPE_LINK.getCode().equals(sysMenuDto.getMenuType())) {
            sysMenu.setMenuComponent(null);
        }

        if (sysMenuDto.getMenuId() == null) {
            long menuId = IdWorker.getId();
            sysMenu.setMenuId(menuId);
        }
        saveOrUpdate(sysMenu);
    }

    @Override
    public void delMenuByMenuId(Long menuId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getMenuId, menuId);
        List<SysRoleMenu> list = roleMenuService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new RuntimeException("有角色配置了该菜单导致无法删除，请先让角色删除该菜单");
        }

        LambdaQueryWrapper<SysMenu> menuQueryWrapper = new LambdaQueryWrapper<>();
        menuQueryWrapper.eq(SysMenu::getMenuParentId, menuId);
        long count = count(menuQueryWrapper);
        if (count > 0) {
            throw new RuntimeException("该菜单下还有子菜单，请先删除子菜单");
        }
        removeById(menuId);
    }
}
