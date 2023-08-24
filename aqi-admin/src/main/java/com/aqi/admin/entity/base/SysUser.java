package com.aqi.admin.entity.base;

import com.aqi.common.mybatis.annotation.FieldEncrypt;
import com.aqi.common.mybatis.encrypt.Encrypted;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user")
public class SysUser extends BaseEntity implements Encrypted {

    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    private String userName;

    private String userRealName;

    @FieldEncrypt
    private String userEmail;

    private String userSex;

    private String userAvatar;

    @FieldEncrypt
    private String phone;

    private Long deptId;

    private String userPassword;

    private String status;
}
