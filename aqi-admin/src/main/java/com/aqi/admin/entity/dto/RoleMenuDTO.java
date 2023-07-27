package com.aqi.admin.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "RoleMenuDTO对象", description = "")
public class RoleMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id列表")
    private Long[] menuIds;

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
