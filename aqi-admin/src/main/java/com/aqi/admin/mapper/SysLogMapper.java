package com.aqi.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aqi.admin.entity.base.SysLog;
import org.apache.ibatis.annotations.Update;

public interface SysLogMapper extends BaseMapper<SysLog> {
    @Update("truncate table sys_log")
    void clearLog();
}
