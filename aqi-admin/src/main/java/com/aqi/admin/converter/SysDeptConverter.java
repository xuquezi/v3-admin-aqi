package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysDept;
import com.aqi.admin.entity.dto.SysDeptDTO;
import com.aqi.admin.entity.vo.SysDeptVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysDeptConverter {

    SysDept dtoToBase(SysDeptDTO sysDeptDTO);

    SysDeptVo baseToVo(SysDept sysDept);
}
