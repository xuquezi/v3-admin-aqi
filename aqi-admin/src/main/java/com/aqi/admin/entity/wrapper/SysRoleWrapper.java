package com.aqi.admin.entity.wrapper;


import com.aqi.admin.entity.base.SysRole;
import com.aqi.admin.entity.vo.SysRoleVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysRoleWrapper extends BaseEntityWrapper<SysRole, SysRoleVo> {
    @Override
    public SysRoleVo entityVO(SysRole sysRole) {
        SysRoleVo copy = BeanCopyUtils.copy(sysRole, SysRoleVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setRoleId(String.valueOf(sysRole.getRoleId()));
        copy.setDataScope(String.valueOf(sysRole.getDataScope()));
        return copy;
    }

    public static SysRoleWrapper build() {
        return new SysRoleWrapper();
    }
}
