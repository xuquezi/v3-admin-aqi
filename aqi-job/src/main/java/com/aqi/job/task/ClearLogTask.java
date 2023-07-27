package com.aqi.job.task;

import com.aqi.api.admin.SysLogProvider;
import com.aqi.job.service.ISysJobLogService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("clearLogTask")
public class ClearLogTask {

    @Reference
    private SysLogProvider sysLogProvider;

    @Autowired
    private ISysJobLogService sysJobLogService;

    /**
     * 定时清理定时任务日志
     */
    public void clearJobLog() {
        sysJobLogService.clearLog();
    }

    /**
     * 定时清理操作日志
     */
    public void clearSysLog() {
        sysLogProvider.clearSysLog();
    }

}
