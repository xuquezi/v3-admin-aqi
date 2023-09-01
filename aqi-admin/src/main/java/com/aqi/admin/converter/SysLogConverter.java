package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysLog;
import com.aqi.admin.entity.vo.SysLogVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysLogConverter {

    Page<SysLogVo> baseToVo(Page<SysLog> page);
}
