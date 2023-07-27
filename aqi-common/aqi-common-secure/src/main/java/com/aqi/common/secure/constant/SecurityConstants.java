package com.aqi.common.secure.constant;

/**
 * 权限认证相关通用常量
 */
public interface SecurityConstants {

    /**
     * 登录用户
     */
    String LOGIN_USER = "login_user";
    /**
     * 用户标识
     */
    String USER_KEY = "user_key";
    /**
     * 用户ID字段
     */
    String USER_ID = "user_id";

    /**
     * 用户名字段
     */
    String USERNAME = "username";
    /**
     * 携带token的请求头
     */
    String AUTH_HEADER = "Authorization";
    /**
     * 生成token秘钥
     */
    byte[] AUTH_SECRET = "52d56ghu74065f79ocf2cf485tcc6y36".getBytes();
    /**
     * 加密数据荷载
     */
    String AUTH_PAYLOAD = "payload";

    /**
     * token过期时间
     */
    long EXPIRE_TIME = 1000 * 60 * 60;

    /**
     * 超级管理员角色编码
     */
    String ADMIN = "admin";
}
