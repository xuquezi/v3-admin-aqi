package com.aqi.job.converter;

import com.aqi.job.entity.base.SysJob;
import com.aqi.job.entity.dto.SysJobDTO;
import com.aqi.job.entity.vo.SysJobVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysJobConverter {

    Page<SysJobVo> baseToVo(Page<SysJob> page);

    SysJob dtoToBase(SysJobDTO sysJobDTO);
}
