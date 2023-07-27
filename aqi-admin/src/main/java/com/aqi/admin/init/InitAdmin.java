package com.aqi.admin.init;

import cn.hutool.core.collection.CollectionUtil;
import com.aqi.admin.entity.base.SysClient;
import com.aqi.admin.service.ISysClientService;
import com.aqi.common.redis.constant.CacheConstant;
import com.aqi.common.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitAdmin implements ApplicationRunner {
    @Autowired
    private ISysClientService sysClientService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run(ApplicationArguments args) {
        /**
         * 加载 客户端数据进入缓存
         */
        List<SysClient> list = sysClientService.list();
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for (SysClient sysClient : list) {
            redisUtils.set(CacheConstant.CLIENT_KEY + sysClient.getClientKey(), sysClient);
        }
    }
}