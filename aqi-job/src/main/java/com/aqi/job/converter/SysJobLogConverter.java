package com.aqi.job.converter;


import com.aqi.job.entity.base.SysJobLog;
import com.aqi.job.entity.vo.SysJobLogVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysJobLogConverter {

    Page<SysJobLogVo> baseToVo(Page<SysJobLog> page);
}
