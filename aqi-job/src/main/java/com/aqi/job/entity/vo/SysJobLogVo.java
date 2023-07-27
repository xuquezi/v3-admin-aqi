package com.aqi.job.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysJobLogVo对象", description = "")
public class SysJobLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    private String jobLogId;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "调用目标")
    private String invokeTarget;

    @ApiModelProperty(value = "调用目标方法")
    private String invokeMethod;

    @ApiModelProperty(value = "调用目标参数")
    private String invokeParam;

    @ApiModelProperty(value = "任务日志")
    private String jobMessage;

    @ApiModelProperty(value = "任务执行是否成功")
    private String status;

    @ApiModelProperty(value = "任务报错信息")
    private String exceptionInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "任务开始时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "任务结束时间")
    private Date endTime;
}
