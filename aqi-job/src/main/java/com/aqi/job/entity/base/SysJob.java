package com.aqi.job.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "sys_job")
@EqualsAndHashCode(callSuper = true)
public class SysJob extends BaseEntity {

    @TableId(value = "job_id", type = IdType.INPUT)
    private String jobId;

    private String jobName;

    private String invokeTarget;

    private String invokeMethod;

    private String invokeParam;

    private String cronExpression;

    private String misfirePolicy;

    private String concurrent;

    private String status;

    private String saveLog;
}
