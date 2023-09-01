package com.aqi.job.controller;

import com.aqi.common.core.entity.R;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.aqi.job.entity.dto.SysJobDTO;
import com.aqi.job.entity.vo.SysJobVo;
import com.aqi.job.service.ISysJobService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
@Api(value = "定时任务", tags = "定时任务接口")
@RequiredArgsConstructor
public class SysJobController {

    private final ISysJobService jobService;

    @RequiresPermissions("system:job:add")
    @ApiOperation(value = "新增定时任务")
    @PostMapping("/createJob")
    @SysLog
    public R createJob(@RequestBody SysJobDTO sysJobDTO) throws SchedulerException {
        jobService.createJob(sysJobDTO);
        return R.ok();
    }

    @RequiresPermissions("system:job:run")
    @ApiOperation(value = "任务执行一次")
    @GetMapping("/run")
    @SysLog
    public R run(@ApiParam(value = "任务id", required = true) @RequestParam(value = "jobId") String jobId) throws SchedulerException {
        jobService.run(jobId);
        return R.ok();
    }

    @RequiresPermissions("system:job:delete")
    @ApiOperation(value = "删除定时任务")
    @DeleteMapping("/delJobById")
    @SysLog
    public R remove(@ApiParam(value = "任务id", required = true) @RequestParam(value = "jobId") String jobId) throws SchedulerException {
        jobService.deleteJobById(jobId);
        return R.ok();
    }

    @RequiresPermissions("system:job:update")
    @ApiOperation(value = "更新定时任务")
    @PutMapping("/updateJob")
    @SysLog
    public R updateJob(@RequestBody SysJobDTO sysJobDTO) throws SchedulerException {
        jobService.updateJob(sysJobDTO);
        return R.ok();
    }

    @RequiresPermissions("system:job:stop")
    @ApiOperation(value = "定时任务状态修改")
    @PutMapping("/changeStatus")
    @SysLog
    public R changeStatus(@ApiParam(value = "任务id", required = true) @RequestParam(value = "jobId") String jobId, @ApiParam(value = "任务状态", required = true) @RequestParam(value = "jobStatus") String jobStatus) throws SchedulerException {
        jobService.changeStatus(jobId, jobStatus);
        return R.ok();
    }

    @RequiresPermissions("system:job:list")
    @ApiOperation(value = "分页查询")
    @GetMapping("/queryJobByPage")
    public R<Page<SysJobVo>> queryJobByPage(SysJobDTO sysJobDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysJobVo> page = jobService.queryJobByPage(sysJobDTO, pageSize, pageNum);
        return R.data(page);
    }
}
