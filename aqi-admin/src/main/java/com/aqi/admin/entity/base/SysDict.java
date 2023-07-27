package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "sys_dict")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseEntity {

    @TableId(value = "dict_code", type = IdType.INPUT)
    private Long dictCode;

    private String dictLabel;

    private String dictValue;

    private String dictType;

    private String dictName;
}
