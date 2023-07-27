package com.aqi.job.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_job_log")
public class SysJobLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "job_log_id", type = IdType.INPUT)
    private Long jobLogId;

    private String jobName;

    private String invokeTarget;

    private String invokeMethod;

    private String invokeParam;

    private String jobMessage;

    private String status;

    private String exceptionInfo;

    private Date startTime;

    private Date endTime;
}
