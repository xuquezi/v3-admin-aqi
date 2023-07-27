package com.aqi.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqi.admin.entity.base.SysConfig;
import com.aqi.admin.entity.dto.SysConfigDTO;
import com.aqi.admin.mapper.SysConfigMapper;
import com.aqi.admin.service.ISysConfigService;
import com.aqi.common.core.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {
    @Override
    public SysConfig getConfigByKey(String configKey) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysConfig::getConfigKey, configKey);
        SysConfig sysConfig = getOne(queryWrapper);
        return sysConfig;
    }

    @Override
    public Page<SysConfig> queryConfigByPage(SysConfigDTO sysConfigDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysConfigDTO.getConfigName()), SysConfig::getConfigName, sysConfigDTO.getConfigName());
        Page<SysConfig> page = page(new Page<SysConfig>(pageNum, pageSize), queryWrapper);
        return page;
    }

    @Override
    public void saveConfig(SysConfigDTO sysConfigDTO) {
        SysConfig copy = BeanCopyUtils.copy(sysConfigDTO, SysConfig.class);
        if (sysConfigDTO.getConfigId() == null) {
            copy.setConfigId(IdWorker.getId());
        }
        this.saveOrUpdate(copy);
    }
}
