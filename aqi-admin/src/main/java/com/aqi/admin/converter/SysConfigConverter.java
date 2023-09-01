package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysConfig;
import com.aqi.admin.entity.dto.SysConfigDTO;
import com.aqi.admin.entity.vo.SysConfigVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysConfigConverter {

    Page<SysConfigVo> baseToVo(Page<SysConfig> page);


    SysConfig dtoToBase(SysConfigDTO sysConfigDTO);
}
