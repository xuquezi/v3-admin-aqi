package com.aqi.common.secure.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String userName;

    private String userRealName;

    private String userEmail;

    private String userSex;

    private String phone;

    private String userAvatar;
    /**
     * 权限列表
     */
    private Set<String> permissions;
    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 角色id列表
     */
    private List<String> roleIds;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 数据权限
     */
    private Integer dataScope;
}
