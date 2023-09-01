package com.aqi.job.controller;

import com.aqi.common.core.entity.R;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.aqi.job.entity.dto.SysJobLogDTO;
import com.aqi.job.entity.vo.SysJobLogVo;
import com.aqi.job.service.ISysJobLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobLog")
@Api(value = "定时任务日志", tags = "定时任务日志接口")
@RequiredArgsConstructor
public class SysJobLogController {

    private final ISysJobLogService sysJobLogService;

    @ApiOperation(value = "分页查询日志")
    @GetMapping("/queryJobLogByPage")
    @RequiresPermissions("system:jobLog:list")
    public R<Page<SysJobLogVo>> queryJobLogByPage(SysJobLogDTO sysJobLogDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysJobLogVo> page = sysJobLogService.queryJobLogByPage(sysJobLogDTO, pageSize, pageNum);
        return R.data(page);
    }

    @ApiOperation(value = "删除日志")
    @DeleteMapping("/delLogByLogId")
    @RequiresPermissions("system:jobLog:delete")
    public R delLogByLogId(@ApiParam(value = "定时日志id", required = true) @RequestParam(value = "jobLogId") Long jobLogId) {
        sysJobLogService.removeById(jobLogId);
        return R.ok();
    }

    @ApiOperation(value = "清空所有日志")
    @DeleteMapping("/clearLog")
    @RequiresPermissions("system:jobLog:clear")
    public R clearLog() {
        sysJobLogService.clearLog();
        return R.ok();
    }

    @ApiOperation(value = "删除选中的日志")
    @DeleteMapping("/deleteSelectedLog")
    @RequiresPermissions("system:jobLog:delete")
    public R deleteSelectedLog(@RequestBody String[] logIds) {
        sysJobLogService.deleteSelectedLog(logIds);
        return R.ok();
    }
}
