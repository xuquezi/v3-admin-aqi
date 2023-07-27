package com.aqi.common.doc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "knife4")
public class Knife4jProperties {
    /**
     * api题目
     */
    private String title;
    /**
     * api描述
     */
    private String description;
    /**
     * api版本
     */
    private String version;


}
