package com.aqi.admin.entity.wrapper;

import com.aqi.admin.entity.base.SysDept;
import com.aqi.admin.entity.vo.SysDeptVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysDeptWrapper extends BaseEntityWrapper<SysDept, SysDeptVo> {
    @Override
    public SysDeptVo entityVO(SysDept sysDept) {
        SysDeptVo copy = BeanCopyUtils.copy(sysDept, SysDeptVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setDeptId(String.valueOf(sysDept.getDeptId()));
        copy.setParentId(String.valueOf(sysDept.getParentId()));
        return copy;
    }

    public static SysDeptWrapper build() {
        return new SysDeptWrapper();
    }
}
