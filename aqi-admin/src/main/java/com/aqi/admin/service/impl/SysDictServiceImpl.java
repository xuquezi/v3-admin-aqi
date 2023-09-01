package com.aqi.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aqi.admin.converter.SysDictConverter;
import com.aqi.admin.entity.base.SysDict;
import com.aqi.admin.entity.dto.SysDictDTO;
import com.aqi.admin.entity.vo.SysDictVo;
import com.aqi.admin.mapper.SysDictMapper;
import com.aqi.admin.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private final SysDictConverter sysDictConverter;

    @Override
    public List<SysDictVo> getDict(String dictType) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDict::getDictType, dictType);
        List<SysDict> list = list(queryWrapper);
        List<SysDictVo> sysDictVos = sysDictConverter.baseToVo(list);
        return sysDictVos;
    }

    @Override
    public Page<SysDictVo> queryDictByPage(SysDictDTO sysDictDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysDictDTO.getDictName()), SysDict::getDictName, sysDictDTO.getDictName());
        Page<SysDict> page = page(new Page<SysDict>(pageNum, pageSize), queryWrapper);
        Page<SysDictVo> sysDictVoPage = sysDictConverter.baseToVo(page);
        return sysDictVoPage;
    }

    @Override
    public void delDictByDictCode(Long dictCode) {
        this.removeById(dictCode);
    }

    @Override
    public void saveDict(SysDictDTO sysDictDTO) {
        SysDict sysDict = sysDictConverter.dtoToBase(sysDictDTO);
        if (sysDictDTO.getDictCode() == null) {
            sysDict.setDictCode(IdWorker.getId());
        }
        this.saveOrUpdate(sysDict);
    }
}
