package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysMenu;
import com.aqi.admin.entity.dto.SysMenuDTO;
import com.aqi.admin.entity.vo.SysMenuVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysMenuConverter {

    SysMenu dtoToBase(SysMenuDTO sysDeptDTO);

    SysMenuVo baseToVo(SysMenu sysMenu);
}
