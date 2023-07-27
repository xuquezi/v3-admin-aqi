package com.aqi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AQIGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AQIGatewayApplication.class, args);
    }
}
