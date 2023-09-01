package com.aqi.admin.service;


import com.aqi.admin.entity.vo.SysConfigVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysConfig;
import com.aqi.admin.entity.dto.SysConfigDTO;

public interface ISysConfigService extends IService<SysConfig> {
    SysConfig getConfigByKey(String configKey);

    Page<SysConfigVo> queryConfigByPage(SysConfigDTO sysConfigDTO, Integer pageSize, Integer pageNum);

    void saveConfig(SysConfigDTO sysConfigDTO);
}
