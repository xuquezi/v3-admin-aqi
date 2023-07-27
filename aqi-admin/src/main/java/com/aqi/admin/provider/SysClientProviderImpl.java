package com.aqi.admin.provider;

import com.aqi.admin.entity.base.SysClient;
import com.aqi.admin.service.ISysClientService;
import com.aqi.api.admin.SysClientProvider;
import com.aqi.api.response.SysClientRes;
import com.aqi.common.core.utils.BeanCopyUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SysClientProviderImpl implements SysClientProvider {
    @Autowired
    private ISysClientService sysClientService;

    @Override
    public SysClientRes getClientByKey(String clientKey) {
        SysClient sysClient = sysClientService.getClientByKey(clientKey);
        return BeanCopyUtils.copy(sysClient, SysClientRes.class);
    }
}
