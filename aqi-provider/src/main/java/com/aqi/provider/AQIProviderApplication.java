package com.aqi.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.aqi.provider", "com.aqi.common","com.aqi.api"})
@EnableDubbo
public class AQIProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AQIProviderApplication.class, args);
    }
}
