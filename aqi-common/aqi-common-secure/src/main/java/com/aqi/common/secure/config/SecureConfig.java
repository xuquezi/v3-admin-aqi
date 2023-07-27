package com.aqi.common.secure.config;

import com.aqi.common.secure.interceptor.SecureInterceptor;
import com.aqi.common.secure.props.SecureProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties({SecureProperties.class})
public class SecureConfig implements WebMvcConfigurer {
    @Autowired
    private SecureProperties secureProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSecureInterceptor()).excludePathPatterns(secureProperties.getSkipUrl());
    }

    public SecureInterceptor getSecureInterceptor() {
        return new SecureInterceptor();
    }
}
