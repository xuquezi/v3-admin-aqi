package com.aqi.admin.provider;

import com.aqi.admin.entity.base.SysLog;
import com.aqi.admin.service.ISysLogService;
import com.aqi.api.admin.SysLogProvider;
import com.aqi.api.request.LogSaveReq;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.Service;

@Service
@RequiredArgsConstructor
public class SysLogProviderImpl implements SysLogProvider {

    private final ISysLogService sysLogService;

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
