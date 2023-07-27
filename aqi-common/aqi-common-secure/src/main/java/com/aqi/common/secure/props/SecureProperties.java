package com.aqi.common.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "aqi.secure")
public class SecureProperties {

    private List<String> skipUrl;

}

