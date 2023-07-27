package com.aqi.admin.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysClientVo对象", description = "")
public class SysClientVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "clientName")
    private String clientName;

    @ApiModelProperty(value = "clientKey")
    private String clientKey;

    @ApiModelProperty(value = "clientSecret")
    private String clientSecret;

    @ApiModelProperty(value = "clientId")
    private String clientId;
}
