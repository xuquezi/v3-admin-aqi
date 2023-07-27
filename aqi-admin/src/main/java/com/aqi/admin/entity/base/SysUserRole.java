package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_user_role")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;
}
