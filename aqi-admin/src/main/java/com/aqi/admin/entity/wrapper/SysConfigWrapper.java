package com.aqi.admin.entity.wrapper;


import com.aqi.admin.entity.base.SysConfig;
import com.aqi.admin.entity.vo.SysConfigVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysConfigWrapper extends BaseEntityWrapper<SysConfig, SysConfigVo> {
    @Override
    public SysConfigVo entityVO(SysConfig sysConfig) {
        SysConfigVo copy = BeanCopyUtils.copy(sysConfig, SysConfigVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setConfigId(String.valueOf(sysConfig.getConfigId()));
        return copy;
    }

    public static SysConfigWrapper build() {
        return new SysConfigWrapper();
    }
}
