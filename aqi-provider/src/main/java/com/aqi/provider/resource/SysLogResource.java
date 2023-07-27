package com.aqi.provider.resource;

import com.aqi.api.admin.SysLogProvider;
import com.aqi.common.core.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operateLog")
@Api(value = "操作日志", tags = "操作日志接口")
public class SysLogResource {
    @Reference
    private SysLogProvider sysLogProvider;

    @ApiOperation(value = "清空所有日志")
    @DeleteMapping("/clearLog")
    public R clearLog() {
        sysLogProvider.clearSysLog();
        return R.ok();
    }
}
