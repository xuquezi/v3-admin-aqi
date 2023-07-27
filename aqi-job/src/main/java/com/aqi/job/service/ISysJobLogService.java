package com.aqi.job.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.job.entity.base.SysJobLog;
import com.aqi.job.entity.dto.SysJobLogDTO;

public interface ISysJobLogService extends IService<SysJobLog> {
    Page<SysJobLog> queryJobLogByPage(SysJobLogDTO sysJobLogDTO, Integer pageSize, Integer pageNum);

    void clearLog();

    void deleteSelectedLog(String[] logIds);
}
