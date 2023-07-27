package com.aqi.job.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.job.entity.base.SysJob;
import com.aqi.job.entity.dto.SysJobDTO;
import org.quartz.SchedulerException;

public interface ISysJobService extends IService<SysJob> {
    void createJob(SysJobDTO sysJobDTO) throws SchedulerException;

    void run(String jobId) throws SchedulerException;

    void deleteJobById(String jobId) throws SchedulerException;

    void updateJob(SysJobDTO sysJobDTO) throws SchedulerException;

    void changeStatus(String jobId, String jobStatus) throws SchedulerException;

    Page<SysJob> queryJobByPage(SysJobDTO sysJobDTO, Integer pageSize, Integer pageNum);
}
