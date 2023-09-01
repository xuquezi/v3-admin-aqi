package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysRole;
import com.aqi.admin.entity.dto.SysRoleDTO;
import com.aqi.admin.entity.vo.SysRoleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysRoleConverter {

    Page<SysRoleVo> baseToVo(Page<SysRole> page);

    List<SysRoleVo> baseToVo(List<SysRole> list);

    SysRole dtoToBase(SysRoleDTO sysRoleDTO);
}
