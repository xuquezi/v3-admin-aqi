package com.aqi.admin.controller;

import com.aqi.admin.entity.base.SysConfig;
import com.aqi.admin.entity.dto.SysConfigDTO;
import com.aqi.admin.entity.vo.SysConfigVo;
import com.aqi.admin.entity.wrapper.SysConfigWrapper;
import com.aqi.admin.service.ISysConfigService;
import com.aqi.common.core.entity.R;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
@Api(value = "系统配置表", tags = "系统配置接口")
public class SysConfigController {
    @Autowired
    private ISysConfigService sysConfigService;

    @ApiOperation(value = "根据configKey获取数据")
    @GetMapping("/getConfigByKey/{configKey}")
    public R<String> getConfigByKey(@ApiParam(value = "配置Key", required = true) @PathVariable String configKey) {
        SysConfig sysConfig = sysConfigService.getConfigByKey(configKey);
        String configValue = sysConfig.getConfigValue();
        return R.data(configValue);
    }

    @RequiresPermissions("system:config:list")
    @ApiOperation(value = "分页查询")
    @GetMapping("/queryConfigByPage")
    public R<Page<SysConfigVo>> queryConfigByPage(SysConfigDTO sysConfigDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysConfig> page = sysConfigService.queryConfigByPage(sysConfigDTO, pageSize, pageNum);
        Page<SysConfigVo> sysConfigVoPage = SysConfigWrapper.build().pageVO(page);
        return R.data(sysConfigVoPage);
    }

    @RequiresPermissions("system:config:delete")
    @ApiOperation(value = "删除配置")
    @DeleteMapping("/delConfigById")
    @SysLog
    public R delConfigById(@ApiParam(value = "配置id", required = true) @RequestParam(value = "configId") Long configId) {
        sysConfigService.removeById(configId);
        return R.ok();
    }

    @RequiresPermissions("system:config:update")
    @ApiOperation(value = "更新系统配置")
    @PutMapping("/updateConfig")
    @SysLog
    public R updateConfig(@RequestBody SysConfigDTO sysConfigDTO) {
        sysConfigService.saveConfig(sysConfigDTO);
        return R.ok();
    }

    @RequiresPermissions("system:config:add")
    @ApiOperation(value = "新增配置")
    @PostMapping("/createConfig")
    @SysLog
    public R createConfig(@RequestBody SysConfigDTO sysConfigDTO) {
        sysConfigService.saveConfig(sysConfigDTO);
        return R.ok();
    }
}
