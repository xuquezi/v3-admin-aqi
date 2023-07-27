package com.aqi.job.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aqi.job.entity.base.SysJobLog;
import org.apache.ibatis.annotations.Update;

public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

    @Update("truncate table sys_job_log")
    void clearLog();
}
