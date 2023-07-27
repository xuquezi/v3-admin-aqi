package com.aqi.admin.entity.wrapper;


import com.aqi.admin.entity.base.SysMenu;
import com.aqi.admin.entity.vo.SysMenuVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysMenuWrapper extends BaseEntityWrapper<SysMenu, SysMenuVo> {
    @Override
    public SysMenuVo entityVO(SysMenu sysMenu) {
        SysMenuVo copy = BeanCopyUtils.copy(sysMenu, SysMenuVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setMenuId(String.valueOf(sysMenu.getMenuId()));
        copy.setMenuParentId(String.valueOf(sysMenu.getMenuParentId()));
        return copy;
    }

    public static SysMenuWrapper build() {
        return new SysMenuWrapper();
    }
}
