package com.aqi.common.log.init;

import com.aqi.api.admin.SysLogProvider;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.context.annotation.Configuration;

/**
 * 解决SpringUtil获取不到dubbo注册类问题
 */
@Configuration
public class DubboReference {
    @Reference
    private SysLogProvider sysLogProvider;
}
