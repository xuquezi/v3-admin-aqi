package com.aqi.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqi.admin.entity.base.SysLog;
import com.aqi.admin.entity.dto.SysLogDTO;
import com.aqi.admin.mapper.SysLogMapper;
import com.aqi.admin.service.ISysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {
    @Override
    public Page<SysLog> queryLogByPage(SysLogDTO sysLogDTO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(sysLogDTO.getUsername()), SysLog::getUsername, sysLogDTO.getUsername());
        Page<SysLog> page = page(new Page<SysLog>(pageNum, pageSize), queryWrapper);
        return page;
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
