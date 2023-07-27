package com.aqi.provider.init;

import com.aqi.api.admin.SysClientProvider;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Configuration;

/**
 * 解决SpringUtil获取不到dubbo注册类问题
 */
@Configuration
public class DubboReference {
    @Reference
    private SysClientProvider sysClientProvider;
}
