package com.aqi.common.doc.config;

import com.aqi.common.doc.properties.Knife4jProperties;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4j配置
 */
@Configuration
@EnableSwagger2WebMvc
@EnableConfigurationProperties(Knife4jProperties.class)
public class Knife4jConfig {

    @Autowired
    private Knife4jProperties knife4jProperties;

    @Bean
    public Docket adminApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(knife4jProperties.getTitle())
                        .description(knife4jProperties.getDescription())
                        .version(knife4jProperties.getVersion())
                        .build())
                .select()
                //这里指定扫描添加了Api注解的
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
