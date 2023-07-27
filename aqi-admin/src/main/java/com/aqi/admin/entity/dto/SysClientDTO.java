package com.aqi.admin.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysClientDTO对象", description = "")
public class SysClientDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "clientId")
    private Long clientId;

    @ApiModelProperty(value = "clientName")
    private String clientName;

    @ApiModelProperty(value = "clientKey")
    private String clientKey;

    @ApiModelProperty(value = "clientSecret")
    private String clientSecret;
}
