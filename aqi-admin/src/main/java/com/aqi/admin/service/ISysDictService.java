package com.aqi.admin.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysDict;
import com.aqi.admin.entity.dto.SysDictDTO;
import com.aqi.admin.entity.vo.SysDictVo;

import java.util.List;

public interface ISysDictService extends IService<SysDict> {

    List<SysDictVo> getDict(String dictType);

    Page<SysDict> queryDictByPage(SysDictDTO sysDictDTO, Integer pageSize, Integer pageNum);

    void delDictByDictCode(Long dictCode);

    void saveDict(SysDictDTO sysDictDTO);
}
