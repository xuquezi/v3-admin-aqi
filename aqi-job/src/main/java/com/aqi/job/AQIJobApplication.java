package com.aqi.job;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.aqi.job", "com.aqi.common","com.aqi.api"})
@MapperScan("com.aqi.job.mapper")
@EnableDubbo
public class AQIJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(AQIJobApplication.class, args);
    }
}
