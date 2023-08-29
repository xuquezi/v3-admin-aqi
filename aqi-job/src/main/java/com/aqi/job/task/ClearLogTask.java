package com.aqi.job.task;

import com.aqi.api.admin.SysLogProvider;
import com.aqi.job.service.ISysJobLogService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component("clearLogTask")
@RequiredArgsConstructor
public class ClearLogTask {

    @Reference
    private SysLogProvider sysLogProvider;

    private final ISysJobLogService sysJobLogService;

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
