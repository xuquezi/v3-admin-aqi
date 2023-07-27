package com.aqi.admin.entity.wrapper;

import com.aqi.admin.entity.base.SysClient;
import com.aqi.admin.entity.vo.SysClientVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysClientWrapper extends BaseEntityWrapper<SysClient, SysClientVo> {
    @Override
    public SysClientVo entityVO(SysClient sysClient) {
        SysClientVo copy = BeanCopyUtils.copy(sysClient, SysClientVo.class);
        copy.setClientId(String.valueOf(sysClient.getClientId()));
        return copy;
    }

    public static SysClientWrapper build() {
        return new SysClientWrapper();
    }
}
