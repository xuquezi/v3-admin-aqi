package com.aqi.job.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysJobVo对象", description = "")
public class SysJobVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    private String jobId;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "调用目标")
    private String invokeTarget;

    @ApiModelProperty(value = "调用目标方法")
    private String invokeMethod;

    @ApiModelProperty(value = "调用目标参数")
    private String invokeParam;

    @ApiModelProperty(value = "执行表达式")
    private String cronExpression;

    /**
     * 0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行
     */
    @ApiModelProperty(value = "计划策略")
    private String misfirePolicy;

    /**
     * 0=允许,1=禁止
     */
    @ApiModelProperty(value = "并发执行")
    private String concurrent;

    /**
     * 0=正常,1=暂停
     */
    @ApiModelProperty(value = "任务状态")
    private String status;
    /**
     * 0=保存,1=不保存
     */
    @ApiModelProperty(value = "是否保存日志")
    private String saveLog;
}
