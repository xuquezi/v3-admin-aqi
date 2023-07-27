package com.aqi.admin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.aqi.admin", "com.aqi.common","com.aqi.api"})
@MapperScan("com.aqi.admin.mapper")
@EnableDubbo
public class AQIAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AQIAdminApplication.class, args);
    }
}
