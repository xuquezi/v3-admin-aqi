package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "sys_config")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseEntity {

    @TableId(value = "config_id", type = IdType.INPUT)
    private Long configId;

    private String configName;

    private String configKey;

    private String configValue;
}
