package com.aqi.common.secure.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.aqi.common.secure.constant.SecurityConstants;
import com.aqi.common.secure.context.SecurityContextHolder;
import com.aqi.common.core.utils.PasswordUtil;
import com.aqi.common.secure.entity.SystemUser;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

public class SecureUtil {
    private static final Logger log = LoggerFactory.getLogger(SecureUtil.class);

    /**
     * 获取登录用户信息
     */
    public static SystemUser getSystemUser() {
        return SecurityContextHolder.get(SecurityConstants.LOGIN_USER, SystemUser.class);
    }

    public static String createToken(SystemUser systemUser) {
        try {
            //1、创建密钥
            MACSigner macSigner = new MACSigner(SecurityConstants.AUTH_SECRET);
            //2、payload
            String payload = JSONObject.toJSONString(systemUser);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("subject")
                    .claim(SecurityConstants.AUTH_PAYLOAD, payload)
                    .expirationTime(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRE_TIME))
                    .build();
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            //3、创建签名的JWT
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
            signedJWT.sign(macSigner);
            String token = signedJWT.serialize();
            return token;
        } catch (Exception e) {
            log.error("生成token异常 {}", e);
            return null;
        }
    }


    /**
     * 密码校验
     *
     * @param password     输入密码
     * @param userPassword 数据库密码
     */
    public static boolean matchesPassword(String password, String userPassword) {
        String s = PasswordUtil.encryptFromString(password);
        if (s.equals(userPassword)) {
            return true;
        }
        return false;
    }

    public static boolean isAdmin() {
        SystemUser systemUser = getSystemUser();
        if (systemUser == null) {
            return false;
        }
        Set<String> roles = getSystemUser().getRoles();
        if (CollectionUtil.isNotEmpty(roles) && roles.contains(SecurityConstants.ADMIN)) {
            return true;
        } else {
            return false;
        }
    }

    public static Long getUserId() {
        return Long.parseLong(getSystemUser().getUserId());
    }

    public static Long getDeptId() {
        return Long.parseLong(getSystemUser().getDeptId());
    }

    public static Integer getDataScope() {
        return getSystemUser().getDataScope();
    }
}
