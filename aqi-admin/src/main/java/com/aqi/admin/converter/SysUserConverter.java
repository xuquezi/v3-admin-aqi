package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysUser;
import com.aqi.admin.entity.dto.SysUserDTO;
import com.aqi.admin.entity.vo.SysUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysUserConverter {
    Page<SysUserVo> baseToVo(Page<SysUser> page);

    SysUser dtoToBase(SysUserDTO sysUserDTO);
}
