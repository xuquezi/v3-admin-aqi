package com.aqi.admin.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysMenuDTO对象", description = "")
public class SysMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    private Long menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "上级菜单id")
    private Long menuParentId;

    @ApiModelProperty(value = "菜单显示顺序")
    private Integer menuOrderNum;

    @ApiModelProperty(value = "菜单路由地址")
    private String menuPath;

    @ApiModelProperty(value = "菜单组件路径")
    private String menuComponent;

    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    @ApiModelProperty(value = "菜单可视状态")
    private String menuVisible;

    @ApiModelProperty(value = "菜单权限标识")
    private String menuPerms;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "跳转路径")
    private String redirect;
}
