package com.aqi.admin.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "Meta对象", description = "")
public class Meta implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "在侧边栏和面包屑中展示的名字")
    private String title;

    @ApiModelProperty(value = "路由的图标")
    private String svgIcon;


    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "是否开启缓存")
    private Boolean keepAlive;

    public Meta() {
    }
}
