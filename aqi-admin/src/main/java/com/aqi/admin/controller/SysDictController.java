package com.aqi.admin.controller;

import com.aqi.admin.entity.dto.SysDictDTO;
import com.aqi.admin.entity.vo.SysDictVo;
import com.aqi.admin.service.ISysDictService;
import com.aqi.common.core.entity.R;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
@Api(value = "系统字典表", tags = "系统字典接口")
@RequiredArgsConstructor
public class SysDictController {

    private final ISysDictService sysDictService;

    @ApiOperation(value = "根据dictType获取数据")
    @GetMapping("/getDict/{dictType}")
    public R<List<SysDictVo>> getDict(@ApiParam(value = "字典类型", required = true) @PathVariable String dictType) {
        List<SysDictVo> list = sysDictService.getDict(dictType);
        return R.data(list);
    }

    @RequiresPermissions("system:dict:list")
    @ApiOperation(value = "分页查询")
    @GetMapping("/queryDictByPage")
    public R<Page<SysDictVo>> queryDictByPage(SysDictDTO sysDictDTO, @ApiParam(value = "每页显示", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, @ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<SysDictVo> page = sysDictService.queryDictByPage(sysDictDTO, pageSize, pageNum);
        return R.data(page);
    }

    @RequiresPermissions("system:dict:update")
    @ApiOperation(value = "更新系统字典")
    @PutMapping("/updateDict")
    @SysLog
    public R updateDict(@RequestBody SysDictDTO sysDictDTO) {
        sysDictService.saveDict(sysDictDTO);
        return R.ok();
    }

    @RequiresPermissions("system:dict:add")
    @ApiOperation(value = "新增系统字典")
    @PostMapping("/createDict")
    @SysLog
    public R createDict(@RequestBody SysDictDTO sysDictDTO) {
        sysDictService.saveDict(sysDictDTO);
        return R.ok();
    }

    @RequiresPermissions("system:dict:delete")
    @ApiOperation(value = "删除字典")
    @DeleteMapping("/delDictByDictCode")
    @SysLog
    public R delDictByDictCode(@ApiParam(value = "字典code", required = true) @RequestParam(value = "dictCode") Long dictCode) {
        sysDictService.delDictByDictCode(dictCode);
        return R.ok();
    }
}
