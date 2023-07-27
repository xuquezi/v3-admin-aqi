package com.aqi.job.entity.wrapper;


import com.aqi.common.core.utils.BeanCopyUtils;
import com.aqi.common.mybatis.entity.BaseEntityWrapper;
import com.aqi.job.entity.base.SysJob;
import com.aqi.job.entity.vo.SysJobVo;

public class SysJobWrapper extends BaseEntityWrapper<SysJob, SysJobVo> {
    @Override
    public SysJobVo entityVO(SysJob sysJob) {
        SysJobVo copy = BeanCopyUtils.copy(sysJob, SysJobVo.class);
        return copy;
    }

    public static SysJobWrapper build() {
        return new SysJobWrapper();
    }
}
