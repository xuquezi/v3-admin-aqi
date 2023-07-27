package com.aqi.admin.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "SysDictDTO对象", description = "")
public class SysDictDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    private Long dictCode;

    @ApiModelProperty(value = "字典label")
    private String dictLabel;

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典名称")
    private String dictName;
}
