package com.aqi.job.execution;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.aqi.job.constant.ScheduleConstants;
import com.aqi.job.enums.SaveLogEnum;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.aqi.common.core.constant.CommonConstant;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.job.entity.base.SysJob;
import com.aqi.job.entity.base.SysJobLog;
import com.aqi.job.service.ISysJobLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public abstract class AbstractQuartzExecution implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzExecution.class);

    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        SysJob sysJob = new SysJob();
        BeanCopyUtils.copy(jobExecutionContext.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysJob);
        try {
            before(jobExecutionContext, sysJob);
            doExecute(jobExecutionContext, sysJob);
            after(jobExecutionContext, sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常 {}", e);
            after(jobExecutionContext, sysJob, e);
        }
    }

    /**
     * 执行方法，由子类重载
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;

    protected void before(JobExecutionContext context, SysJob sysJob) {
        threadLocal.set(new Date());
    }

    protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
        if (SaveLogEnum.SAVE.getCode().equals(sysJob.getSaveLog())) {
            this.saveLog(sysJob, e);
        }
    }

    private void saveLog(SysJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();
        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobLogId(IdWorker.getId());
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setInvokeMethod(sysJob.getInvokeMethod());
        sysJobLog.setInvokeParam(sysJob.getInvokeParam());
        sysJobLog.setStartTime(startTime);
        sysJobLog.setEndTime(new Date());
        long runMs = sysJobLog.getEndTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            sysJobLog.setStatus(CommonConstant.FAIL);
            String errorMsg = StrUtil.sub(getExceptionMessage(e), 0, 256);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus(CommonConstant.SUCCESS);
        }
        // 保存日志
        SpringUtil.getBean(ISysJobLogService.class).save(sysJobLog);
    }


    private String getExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }
}
