package com.aqi.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.aqi.admin.entity.base.SysClient;
import com.aqi.admin.entity.dto.SysClientDTO;
import com.aqi.admin.mapper.SysClientMapper;
import com.aqi.admin.service.ISysClientService;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.redis.annotation.MyRedisCache;
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

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SysClientServiceImpl extends ServiceImpl<SysClientMapper, SysClient> implements ISysClientService {
    private final RedisUtils redisUtils;


    @Override
    public Page<SysClient> queryClientByPage(SysClientDTO sysClientDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysClient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysClientDTO.getClientName()), SysClient::getClientName, sysClientDTO.getClientName());
        Page<SysClient> page = page(new Page<SysClient>(pageNum, pageSize), queryWrapper);
        return page;
    }

    @Override
    public void addClient(SysClientDTO sysClientDTO) {
        LambdaQueryWrapper<SysClient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysClient::getClientKey, sysClientDTO.getClientKey());
        SysClient one = getOne(queryWrapper);
        if (one != null) {
            throw new RuntimeException("secretKey 已存在");
        }
        SysClient sysClient = BeanCopyUtils.copy(sysClientDTO, SysClient.class);
        long id = IdWorker.getId();
        sysClient.setClientId(id);
        save(sysClient);
    }

    @Override
    public void updateClient(SysClientDTO sysClientDTO) {
        // 清除缓存
        redisUtils.del(CacheConstant.CLIENT_KEY + sysClientDTO.getClientKey());
        SysClient sysClient = BeanCopyUtils.copy(sysClientDTO, SysClient.class);
        updateById(sysClient);
    }

    @Override
    public void delClientById(Long clientId) {
        SysClient sysClient = this.getById(clientId);
        // 清除缓存
        redisUtils.del(CacheConstant.CLIENT_KEY + sysClient.getClientKey());
        removeById(clientId);
    }

    @MyRedisCache(keyPrefix = CacheConstant.CLIENT_KEY, matchValue = "#clientKey")
    @Override
    public SysClient getClientByKey(String clientKey) {
        LambdaQueryWrapper<SysClient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysClient::getClientKey, clientKey);
        SysClient sysClient = getOne(queryWrapper);
        return sysClient;
    }
}
