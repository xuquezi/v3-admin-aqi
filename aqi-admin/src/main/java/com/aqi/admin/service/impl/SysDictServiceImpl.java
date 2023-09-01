package com.aqi.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aqi.admin.converter.SysDictConverter;
import com.aqi.admin.entity.base.SysDict;
import com.aqi.admin.entity.dto.SysDictDTO;
import com.aqi.admin.entity.vo.SysDictVo;
import com.aqi.admin.mapper.SysDictMapper;
import com.aqi.admin.service.ISysDictService;
import com.aqi.common.redis.constant.CacheConstant;
import com.aqi.common.redis.utils.RedisUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private final SysDictConverter sysDictConverter;

    private final RedisUtils redisUtils;

    @Override
    public List<SysDictVo> getDict(String dictType) {
        List<SysDict> list = null;
        List<Object> objects = redisUtils.batchGet(CacheConstant.DICT_KEY + dictType);
        if (CollectionUtil.isNotEmpty(objects)) {
            list = new ArrayList<>();
            for (Object object : objects) {
                SysDict sysDict = (SysDict) object;
                list.add(sysDict);
            }
        } else {
            LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysDict::getDictType, dictType);
            list = list(queryWrapper);
            for (SysDict sysDict : list) {
                redisUtils.set(CacheConstant.DICT_KEY + sysDict.getDictType() + sysDict.getDictCode(), sysDict);
            }
        }
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
        SysDict sysDict = this.getById(dictCode);
        redisUtils.del(CacheConstant.DICT_KEY + sysDict.getDictType() + dictCode);
        this.removeById(dictCode);
    }

    @Override
    public void saveDict(SysDictDTO sysDictDTO) {
        SysDict sysDict = sysDictConverter.dtoToBase(sysDictDTO);
        if (sysDictDTO.getDictCode() == null) {
            sysDict.setDictCode(IdWorker.getId());
        } else {
            redisUtils.del(CacheConstant.DICT_KEY + sysDictDTO.getDictType() + sysDictDTO.getDictCode());
        }
        this.saveOrUpdate(sysDict);
        redisUtils.set(CacheConstant.DICT_KEY + sysDict.getDictType() + sysDict.getDictCode(), sysDict);
    }
}
