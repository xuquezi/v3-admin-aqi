package com.aqi.api.admin;

import com.aqi.api.request.LogSaveReq;

public interface SysLogProvider {

    void clearSysLog();

    void saveLog(LogSaveReq logSaveReq);
}
