package com.aqi.job.init;

import cn.hutool.core.collection.CollectionUtil;
import com.aqi.job.entity.base.SysJob;
import com.aqi.job.service.ISysJobService;
import com.aqi.job.utils.ScheduleUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 重启的时候加载数据库中的定时任务
 */
@Component
@RequiredArgsConstructor
public class InitJob implements ApplicationRunner {

    private final ISysJobService sysJobService;

    private final Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SysJob> list = sysJobService.list();
        if (CollectionUtil.isNotEmpty(list)) {
            for (SysJob sysJob : list) {
                ScheduleUtils.createScheduleJob(scheduler, sysJob);
            }
        }
    }
}