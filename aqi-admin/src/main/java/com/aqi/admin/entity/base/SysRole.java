package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    @TableId(value = "role_id", type = IdType.INPUT)
    private Long roleId;

    private String roleName;

    private String roleKey;

    private String status;
    /**
     * 数据权限
     */
    private Integer dataScope;
}
