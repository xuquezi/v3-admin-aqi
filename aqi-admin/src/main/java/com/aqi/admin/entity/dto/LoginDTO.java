package com.aqi.admin.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel(value = "用户登录对象", description = "")
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "登陆账号不能为空")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "用户密码")
    private String password;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    @NotEmpty(message = "验证码key不能为空")
    @ApiModelProperty(value = "验证码key")
    private String key;
}
