package com.aqi.admin.service;

import com.aqi.admin.entity.base.SysDept;
import com.aqi.admin.entity.dto.SysDeptDTO;
import com.aqi.admin.entity.vo.Option;
import com.aqi.admin.entity.vo.SysDeptVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ISysDeptService extends IService<SysDept> {
    List<Option> departTreeSelect();

    List<SysDeptVo> queryDeptList(SysDeptDTO sysDeptDTO);

    List<SysDeptVo> getDepartList();

    void delDeptById(Long deptId);

    void saveDept(SysDeptDTO sysDeptDTO);
}
