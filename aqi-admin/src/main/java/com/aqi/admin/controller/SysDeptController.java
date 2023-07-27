package com.aqi.admin.controller;

import com.aqi.admin.entity.dto.SysDeptDTO;
import com.aqi.admin.entity.vo.Option;
import com.aqi.admin.entity.vo.SysDeptVo;
import com.aqi.admin.service.ISysDeptService;
import com.aqi.common.core.entity.R;
import com.aqi.common.log.annotation.SysLog;
import com.aqi.common.secure.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
@Api(value = "部门接口", tags = "部门接口")
public class SysDeptController {
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 获取部门下拉树列表
     */
    @ApiOperation(value = "查询部门树列表")
    @GetMapping("/getDepartTreeSelect")
    public R<List<Option>> departTreeSelect() {
        List<Option> list = sysDeptService.departTreeSelect();
        return R.data(list);
    }

    @RequiresPermissions("system:dept:list")
    @ApiOperation(value = "查询部门列表")
    @GetMapping("/queryDeptList")
    public R<List<SysDeptVo>> queryDeptList(SysDeptDTO sysDeptDTO) {
        List<SysDeptVo> sysDeptVoList = sysDeptService.queryDeptList(sysDeptDTO);
        return R.data(sysDeptVoList);
    }

    @ApiOperation(value = "获取部门列表")
    @GetMapping("/getDepartList")
    public R<List<SysDeptVo>> getDepartList() {
        List<SysDeptVo> deptVoList = sysDeptService.getDepartList();
        return R.data(deptVoList);
    }

    @RequiresPermissions("system:dept:update")
    @ApiOperation(value = "部门更新")
    @PutMapping("/updateDept")
    @SysLog
    public R updateDept(@RequestBody SysDeptDTO sysDeptDTO) {
        sysDeptService.saveDept(sysDeptDTO);
        return R.ok();
    }

    @RequiresPermissions("system:dept:add")
    @ApiOperation(value = "部门新增")
    @PostMapping("/addDept")
    @SysLog
    public R addDept(@RequestBody SysDeptDTO sysDeptDTO) {
        sysDeptService.saveDept(sysDeptDTO);
        return R.ok();
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/delDeptById")
    @RequiresPermissions("system:dept:delete")
    @SysLog
    public R delDeptById(@ApiParam(value = "部门id", required = true) @RequestParam(value = "deptId") Long deptId) {
        sysDeptService.delDeptById(deptId);
        return R.ok();
    }
}
