package com.aqi.admin.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
// 字段为空或空字符串，不序列化，忽略
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "Route对象", description = "")
public class Route implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路由名字")
    private String name;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "是否隐藏路由")
    private boolean hidden;
    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "组件地址")
    private String component;
    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    @ApiModelProperty(value = "当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式")
    private Boolean alwaysShow;

    @ApiModelProperty(value = "其他元素")
    private Meta meta;

    @ApiModelProperty(value = "子路由")
    private List<Route> children;
}
