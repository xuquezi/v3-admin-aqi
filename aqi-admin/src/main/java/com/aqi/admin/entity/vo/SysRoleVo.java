package com.aqi.admin.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysRoleVo对象", description = "")
public class SysRoleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色key")
    private String roleKey;

    @ApiModelProperty(value = "角色状态")
    private String status;

    @ApiModelProperty(value = "数据权限")
    private String dataScope;
}
