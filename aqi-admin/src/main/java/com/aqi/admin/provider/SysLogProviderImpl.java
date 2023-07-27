package com.aqi.admin.provider;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.aqi.admin.entity.base.SysLog;
import com.aqi.admin.service.ISysLogService;
import com.aqi.api.admin.SysLogProvider;
import com.aqi.api.request.LogSaveReq;
import com.aqi.common.core.utils.BeanCopyUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SysLogProviderImpl implements SysLogProvider {
    @Autowired
    private ISysLogService sysLogService;

    @Override
    public void clearSysLog() {
        sysLogService.clearLog();
    }

    @Override
    public void saveLog(LogSaveReq logSaveReq) {
        SysLog sysLog = BeanCopyUtils.copy(logSaveReq, SysLog.class);
        sysLog.setLogId(IdWorker.getId());
        sysLogService.save(sysLog);
    }
}
