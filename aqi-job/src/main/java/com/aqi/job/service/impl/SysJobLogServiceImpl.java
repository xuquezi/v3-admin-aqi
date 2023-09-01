package com.aqi.job.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aqi.job.converter.SysJobLogConverter;
import com.aqi.job.entity.vo.SysJobLogVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqi.job.entity.base.SysJobLog;
import com.aqi.job.entity.dto.SysJobLogDTO;
import com.aqi.job.mapper.SysJobLogMapper;
import com.aqi.job.service.ISysJobLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements ISysJobLogService {

    private final SysJobLogConverter sysJobLogConverter;

    @Override
    public Page<SysJobLogVo> queryJobLogByPage(SysJobLogDTO sysJobLogDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysJobLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysJobLogDTO.getJobName()), SysJobLog::getJobName, sysJobLogDTO.getJobName());
        Page<SysJobLog> page = page(new Page<SysJobLog>(pageNum, pageSize), queryWrapper);
        Page<SysJobLogVo> sysJobLogVoPage = sysJobLogConverter.baseToVo(page);
        return sysJobLogVoPage;
    }

    @Override
    public void clearLog() {
        baseMapper.clearLog();
    }

    @Override
    public void deleteSelectedLog(String[] logIds) {
        if (logIds.length > 0) {
            List<Long> collect = Stream.of(logIds).map(x -> Long.valueOf(x)).collect(Collectors.toList());
            removeByIds(collect);
        } else {
            throw new RuntimeException("选择删除的记录数为0");
        }
    }
}
