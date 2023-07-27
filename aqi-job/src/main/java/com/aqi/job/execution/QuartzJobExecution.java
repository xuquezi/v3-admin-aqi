package com.aqi.job.execution;

import com.aqi.job.entity.base.SysJob;
import com.aqi.job.utils.JobInvokeUtils;
import org.quartz.JobExecutionContext;

public class QuartzJobExecution extends AbstractQuartzExecution {

    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtils.invokeMethod(sysJob);
    }
}
