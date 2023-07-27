package com.aqi.provider.config;

import com.aqi.provider.interceptor.ApiInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecureConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getApiInterceptor());
    }

    public ApiInterceptor getApiInterceptor() {
        return new ApiInterceptor();
    }
}
