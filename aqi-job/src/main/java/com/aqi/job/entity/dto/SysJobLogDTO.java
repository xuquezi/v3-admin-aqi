package com.aqi.job.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysJobLogDTO对象", description = "")
public class SysJobLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    private Long jobLogId;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "调用目标")
    private String invokeTarget;

    @ApiModelProperty(value = "调用目标方法")
    private String invokeMethod;

    @ApiModelProperty(value = "调用目标参数")
    private String invokeParam;
}
