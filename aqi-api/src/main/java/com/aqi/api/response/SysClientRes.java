package com.aqi.api.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysClientRes implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long clientId;

    private String clientName;

    private String clientKey;

    private String clientSecret;
}
