package com.aqi.job.service.impl;


import cn.hutool.core.util.StrUtil;
import com.aqi.job.constant.ScheduleConstants;
import com.aqi.job.converter.SysJobConverter;
import com.aqi.job.entity.base.SysJob;
import com.aqi.job.entity.dto.SysJobDTO;
import com.aqi.job.entity.vo.SysJobVo;
import com.aqi.job.enums.TaskStatusEnum;
import com.aqi.job.mapper.SysJobMapper;
import com.aqi.job.service.ISysJobService;
import com.aqi.job.utils.ScheduleUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements ISysJobService {

    private final Scheduler scheduler;

    private final SysJobConverter sysJobConverter;

    @Override
    public void createJob(SysJobDTO sysJobDTO) throws SchedulerException {
        // 如果任务已经存在，判断一下删除
        if (ScheduleUtils.checkExists(scheduler, sysJobDTO.getJobId())) {
            this.removeById(sysJobDTO.getJobId());
        }
        SysJob sysJob = sysJobConverter.dtoToBase(sysJobDTO);
        if (StrUtil.isEmpty(sysJob.getJobId())) {
            sysJob.setJobId(IdWorker.getIdStr());
        }
        this.save(sysJob);
        ScheduleUtils.createScheduleJob(scheduler, sysJob);
    }

    @Override
    public void run(String jobId) throws SchedulerException {
        SysJob sysJob = this.getById(jobId);
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, sysJob);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId), dataMap);
    }

    @Override
    public void deleteJobById(String jobId) throws SchedulerException {
        removeById(jobId);
        scheduler.deleteJob(ScheduleUtils.getJobKey(jobId));
    }

    @Override
    public void updateJob(SysJobDTO sysJobDTO) throws SchedulerException {
        SysJob sysJob = sysJobConverter.dtoToBase(sysJobDTO);
        updateById(sysJob);

        String jobId = sysJob.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, sysJob);
    }

    @Override
    public void changeStatus(String jobId, String jobStatus) throws SchedulerException {
        SysJob sysJob = new SysJob();
        sysJob.setJobId(jobId);
        sysJob.setStatus(jobStatus);
        updateById(sysJob);
        if (TaskStatusEnum.NORMAL.getCode().equals(jobStatus)) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId));
        } else if (TaskStatusEnum.PAUSE.getCode().equals(jobStatus)) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId));
        }
    }

    @Override
    public Page<SysJobVo> queryJobByPage(SysJobDTO sysJobDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysJobDTO.getJobName()), SysJob::getJobName, sysJobDTO.getJobName());
        Page<SysJob> page = page(new Page<SysJob>(pageNum, pageSize), queryWrapper);
        Page<SysJobVo> sysJobVoPage = sysJobConverter.baseToVo(page);
        return sysJobVoPage;
    }
}
