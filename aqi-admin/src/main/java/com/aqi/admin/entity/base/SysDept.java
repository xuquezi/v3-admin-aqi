package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "sys_dept")
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseEntity {

    @TableId(value = "dept_id", type = IdType.INPUT)
    private Long deptId;

    private Long parentId;

    private String deptName;

    private Integer orderNum;

    private String treePath;
}
