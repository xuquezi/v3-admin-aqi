package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "sys_client")
@EqualsAndHashCode(callSuper = true)
public class SysClient extends BaseEntity {

    @TableId(value = "client_id", type = IdType.INPUT)
    private Long clientId;

    private String clientName;

    private String clientKey;

    private String clientSecret;
}
