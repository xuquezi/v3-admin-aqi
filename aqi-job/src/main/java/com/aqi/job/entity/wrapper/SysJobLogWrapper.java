package com.aqi.job.entity.wrapper;


import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;
import com.aqi.job.entity.base.SysJobLog;
import com.aqi.job.entity.vo.SysJobLogVo;

public class SysJobLogWrapper extends BaseEntityWrapper<SysJobLog, SysJobLogVo> {
    @Override
    public SysJobLogVo entityVO(SysJobLog sysJobLog) {
        SysJobLogVo copy = BeanCopyUtils.copy(sysJobLog, SysJobLogVo.class);
        // long数据前端会缺失进度，需要转化为string类型在返回
        copy.setJobLogId(String.valueOf(sysJobLog.getJobLogId()));
        return copy;
    }

    public static SysJobLogWrapper build() {
        return new SysJobLogWrapper();
    }
}
