package com.aqi.admin.entity.wrapper;


import com.aqi.admin.entity.base.SysDict;
import com.aqi.admin.entity.vo.SysDictVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysDictWrapper extends BaseEntityWrapper<SysDict, SysDictVo> {

    @Override
    public SysDictVo entityVO(SysDict sysDict) {
        SysDictVo copy = BeanCopyUtils.copy(sysDict, SysDictVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setDictCode(String.valueOf(sysDict.getDictCode()));
        return copy;
    }

    public static SysDictWrapper build() {
        return new SysDictWrapper();
    }
}
