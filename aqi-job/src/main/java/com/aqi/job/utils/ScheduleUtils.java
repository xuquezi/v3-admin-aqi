package com.aqi.job.utils;

import com.aqi.job.constant.ScheduleConstants;
import com.aqi.job.entity.base.SysJob;
import com.aqi.job.enums.MisfireEnum;
import com.aqi.job.enums.TaskStatusEnum;
import com.aqi.job.execution.QuartzDisallowConcurrentExecution;
import com.aqi.job.execution.QuartzJobExecution;
import org.quartz.*;

public class ScheduleUtils {

    public static boolean checkExists(Scheduler scheduler, String jobId) throws SchedulerException {
        return scheduler.checkExists(getJobKey(jobId));
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException {
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        // 构建job信息
        String jobId = job.getJobId();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId)).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId))
                .withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(jobId))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(jobId));
        }
        scheduler.scheduleJob(jobDetail, trigger);
        // 暂停任务
        if (job.getStatus().equals(TaskStatusEnum.PAUSE.getCode())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId));
        }
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(String jobId) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(String jobId) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 得到quartz任务类
     */
    private static Class<? extends Job> getQuartzJobClass(SysJob sysJob) {
        boolean isConcurrent = "0".equals(sysJob.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb) {
        String misfirePolicy = job.getMisfirePolicy();

        if (MisfireEnum.DEFAULT.getCode().equals(misfirePolicy)) {
            return cb;
        } else if (MisfireEnum.IGNORE_MISFIRES.getCode().equals(misfirePolicy)) {
            return cb.withMisfireHandlingInstructionIgnoreMisfires();
        } else if (MisfireEnum.FIRE_AND_PROCEED.getCode().equals(misfirePolicy)) {
            return cb.withMisfireHandlingInstructionFireAndProceed();
        } else if (MisfireEnum.DO_NOTHING.getCode().equals(misfirePolicy)) {
            return cb.withMisfireHandlingInstructionDoNothing();
        } else {
            throw new RuntimeException("没有匹配到定时任务策略");
        }
    }
}
