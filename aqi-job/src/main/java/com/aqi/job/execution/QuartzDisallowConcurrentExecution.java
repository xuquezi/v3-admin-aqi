package com.aqi.job.execution;

import com.aqi.job.entity.base.SysJob;
import com.aqi.job.utils.JobInvokeUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

// 不允许并发执行
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzExecution {

    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtils.invokeMethod(sysJob);
    }
}
