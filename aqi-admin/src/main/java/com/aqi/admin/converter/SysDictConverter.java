package com.aqi.admin.converter;

import com.aqi.admin.entity.base.SysDict;
import com.aqi.admin.entity.dto.SysDictDTO;
import com.aqi.admin.entity.vo.SysDictVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysDictConverter {

    Page<SysDictVo> baseToVo(Page<SysDict> page);

    List<SysDictVo> baseToVo(List<SysDict> list);

    SysDict dtoToBase(SysDictDTO sysDictDTO);
}
