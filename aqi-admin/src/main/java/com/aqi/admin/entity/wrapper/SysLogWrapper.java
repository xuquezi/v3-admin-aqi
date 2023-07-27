package com.aqi.admin.entity.wrapper;


import com.aqi.admin.entity.base.SysLog;
import com.aqi.admin.entity.vo.SysLogVo;
import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;

public class SysLogWrapper extends BaseEntityWrapper<SysLog, SysLogVo> {
    @Override
    public SysLogVo entityVO(SysLog sysLog) {
        SysLogVo copy = BeanCopyUtils.copy(sysLog, SysLogVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setLogId(String.valueOf(sysLog.getLogId()));
        return copy;
    }

    public static SysLogWrapper build() {
        return new SysLogWrapper();
    }
}
