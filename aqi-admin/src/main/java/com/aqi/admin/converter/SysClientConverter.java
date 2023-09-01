package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysClient;
import com.aqi.admin.entity.dto.SysClientDTO;
import com.aqi.admin.entity.vo.SysClientVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysClientConverter {
    Page<SysClientVo> baseToVo(Page<SysClient> page);

    SysClient dtoToBase(SysClientDTO sysClientDTO);
}
