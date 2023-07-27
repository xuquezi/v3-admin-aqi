package com.aqi.admin.entity.wrapper;


import com.aqi.admin.entity.base.SysUser;
import com.aqi.admin.entity.vo.SysUserVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysUserWrapper extends BaseEntityWrapper<SysUser, SysUserVo> {
    @Override
    public SysUserVo entityVO(SysUser sysUser) {
        SysUserVo copy = BeanCopyUtils.copy(sysUser, SysUserVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setUserId(String.valueOf(sysUser.getUserId()));
        copy.setDeptId(String.valueOf(sysUser.getDeptId()));
        return copy;
    }

    public static SysUserWrapper build() {
        return new SysUserWrapper();
    }
}
