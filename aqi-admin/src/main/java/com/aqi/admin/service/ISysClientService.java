package com.aqi.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aqi.admin.entity.base.SysClient;
import com.aqi.admin.entity.dto.SysClientDTO;

public interface ISysClientService extends IService<SysClient> {

    Page<SysClient> queryClientByPage(SysClientDTO sysClientDTO, Integer pageSize, Integer pageNum);

    void addClient(SysClientDTO sysClientDTO);

    void updateClient(SysClientDTO sysClientDTO);

    void delClientById(Long clientId);

    SysClient getClientByKey(String clientKey);
}
