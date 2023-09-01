package com.aqi.admin.service;


import com.aqi.admin.entity.vo.SysClientVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysClient;
import com.aqi.admin.entity.dto.SysClientDTO;

public interface ISysClientService extends IService<SysClient> {

    Page<SysClientVo> queryClientByPage(SysClientDTO sysClientDTO, Integer pageSize, Integer pageNum);

    SysClient addClient(SysClientDTO sysClientDTO);

    SysClient updateClient(SysClientDTO sysClientDTO);

    SysClient getClientByKey(String clientKey);

    void delClientByKey(String clientKey);
}
