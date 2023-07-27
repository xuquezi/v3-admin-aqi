package com.aqi.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysLog;
import com.aqi.admin.entity.dto.SysLogDTO;

public interface ISysLogService extends IService<SysLog> {
    Page<SysLog> queryLogByPage(SysLogDTO sysLogDTO, Integer pageSize, Integer pageNum);

    void clearLog();

    void deleteSelectedLog(String[] logIds);
}
