package com.aqi.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aqi.admin.entity.base.SysDict;
import com.aqi.admin.entity.dto.SysDictDTO;
import com.aqi.admin.entity.vo.SysDictVo;
import com.aqi.admin.entity.wrapper.SysDictWrapper;
import com.aqi.admin.mapper.SysDictMapper;
import com.aqi.admin.service.ISysDictService;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {
    @Override
    public List<SysDictVo> getDict(String dictType) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDict::getDictType, dictType);
        List<SysDict> list = list(queryWrapper);
        return SysDictWrapper.build().listVO(list);
    }

    @Override
    public Page<SysDict> queryDictByPage(SysDictDTO sysDictDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysDictDTO.getDictName()), SysDict::getDictName, sysDictDTO.getDictName());
        Page<SysDict> page = page(new Page<SysDict>(pageNum, pageSize), queryWrapper);
        return page;
    }

    @Override
    public void delDictByDictCode(Long dictCode) {
        this.removeById(dictCode);
    }

    @Override
    public void saveDict(SysDictDTO sysDictDTO) {
        SysDict sysDict = BeanCopyUtils.copy(sysDictDTO, SysDict.class);
        if (sysDictDTO.getDictCode() == null) {
            sysDict.setDictCode(IdWorker.getId());
        }
        this.saveOrUpdate(sysDict);
    }
}
