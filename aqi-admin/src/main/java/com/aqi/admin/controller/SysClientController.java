package com.aqi.admin.controller;

import com.aqi.admin.entity.dto.SysClientDTO;
import com.aqi.admin.entity.vo.SysClientVo;
import com.aqi.admin.service.ISysClientService;
import com.aqi.common.core.entity.R;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@Api(value = "客户端接口", tags = "客户端接口")
@RequiredArgsConstructor
public class SysClientController {

    private final ISysClientService sysClientService;

    @ApiOperation(value = "分页查询")
    @GetMapping("/queryClientByPage")
    @RequiresPermissions("system:client:list")
    public R<Page<SysClientVo>> queryClientByPage(SysClientDTO sysClientDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysClientVo> page = sysClientService.queryClientByPage(sysClientDTO, pageSize, pageNum);
        return R.data(page);
    }

    @ApiOperation(value = "客户端新增")
    @PostMapping("/addClient")
    @RequiresPermissions("system:client:add")
    @SysLog
    public R addClient(@RequestBody SysClientDTO sysClientDTO) {
        sysClientService.addClient(sysClientDTO);
        return R.ok();
    }

    @ApiOperation(value = "客户端修改")
    @PutMapping("/updateClient")
    @RequiresPermissions("system:client:update")
    @SysLog
    public R updateClient(@RequestBody SysClientDTO sysClientDTO) {
        sysClientService.updateClient(sysClientDTO);
        return R.ok();
    }

    @ApiOperation(value = "删除客户端")
    @DeleteMapping("/delClientByKey")
    @RequiresPermissions("system:client:delete")
    @SysLog
    public R delClientByKey(@ApiParam(value = "客户端key", required = true) @RequestParam(value = "clientKey") String clientKey) {
        sysClientService.delClientByKey(clientKey);
        return R.ok();
    }
}
