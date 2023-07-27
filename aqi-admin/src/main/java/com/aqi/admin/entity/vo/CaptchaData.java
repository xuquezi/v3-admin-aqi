package com.aqi.admin.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "验证码信息")
public class CaptchaData implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 验证码信息
     */
    @ApiModelProperty(value = "验证码Key")
    private String key;


    /**
     * 验证码image信息
     */
    @ApiModelProperty(value = "验证码iamge信息 base64编码")
    private String img;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间，单位秒")
    private Integer expired;

}
