package com.aqi.admin.controller;

import com.aqi.admin.entity.dto.SysLogDTO;
import com.aqi.admin.entity.vo.SysLogVo;
import com.aqi.admin.service.ISysLogService;
import com.aqi.common.core.entity.R;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operateLog")
@Api(value = "操作日志", tags = "操作日志接口")
@RequiredArgsConstructor
public class SysLogController {
    private final ISysLogService sysLogService;

    @ApiOperation(value = "分页查询日志")
    @GetMapping("/queryLogByPage")
    @RequiresPermissions("system:operateLog:list")
    public R<Page<SysLogVo>> queryLogByPage(SysLogDTO sysLogDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysLogVo> page = sysLogService.queryLogByPage(sysLogDTO, pageSize, pageNum);
        return R.data(page);
    }

    @ApiOperation(value = "删除日志")
    @DeleteMapping("/delLogByLogId")
    @RequiresPermissions("system:operateLog:delete")
    public R delLogByLogId(@ApiParam(value = "日志id", required = true) @RequestParam(value = "logId") Long logId) {
        sysLogService.removeById(logId);
        return R.ok();
    }

    @ApiOperation(value = "清空所有日志")
    @DeleteMapping("/clearLog")
    @RequiresPermissions("system:operateLog:clear")
    public R clearLog() {
        sysLogService.clearLog();
        return R.ok();
    }

    @ApiOperation(value = "删除选中的日志")
    @DeleteMapping("/deleteSelectedLog")
    @RequiresPermissions("system:operateLog:delete")
    public R deleteSelectedLog(@RequestBody String[] logIds) {
        sysLogService.deleteSelectedLog(logIds);
        return R.ok();
    }
}
