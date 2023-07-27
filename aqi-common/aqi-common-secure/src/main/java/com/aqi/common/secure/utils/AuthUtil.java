package com.aqi.common.secure.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.aqi.common.core.exception.NotLoginException;
import com.aqi.common.core.exception.NotPermissionException;
import com.aqi.common.core.exception.NotRoleException;
import com.aqi.common.secure.annotation.Logical;
import com.aqi.common.secure.annotation.RequiresPermissions;
import com.aqi.common.secure.annotation.RequiresRoles;
import com.aqi.common.secure.constant.SecurityConstants;
import com.aqi.common.secure.entity.SystemUser;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class AuthUtil {
    private static final Logger log = LoggerFactory.getLogger(AuthUtil.class);

    /**
     * 验证token是否合法
     */
    public static boolean verify(SignedJWT jwt) {
        try {
            JWSVerifier verifier = new MACVerifier(SecurityConstants.AUTH_SECRET);
            return jwt.verify(verifier);
        } catch (Exception e) {
            log.error("检测token是否合法异常 {}", e);
            return false;
        }
    }

    /**
     * 验证token是否过期
     */
    public static boolean checkExpire(SignedJWT jwt) {
        try {
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            return new Date().after(expirationTime);
        } catch (Exception e) {
            log.error("检测token是否超时异常 {}", e);
            return true;
        }
    }

    public static void checkRole(RequiresRoles requiresRoles) {
        if (requiresRoles.logical() == Logical.AND) {
            checkRoleAnd(requiresRoles.value());
        } else {
            checkRoleOr(requiresRoles.value());
        }
    }

    public static void checkRoleAnd(String... roles) {
        SystemUser systemUser = SecureUtil.getSystemUser();
        if (systemUser == null) {
            throw new NotLoginException("无效的token");
        }
        Set<String> roleList = systemUser.getRoles();
        for (String role : roles) {
            if (!hasRole(roleList, role)) {
                throw new NotRoleException(role);
            }
        }
    }

    public static void checkRoleOr(String... roles) {
        SystemUser systemUser = SecureUtil.getSystemUser();
        if (systemUser == null) {
            throw new NotLoginException("无效的token");
        }
        Set<String> roleList = systemUser.getRoles();
        for (String role : roles) {
            if (hasRole(roleList, role)) {
                return;
            }
        }
        if (roles.length > 0) {
            throw new NotRoleException(roles);
        }
    }

    public static boolean hasRole(Collection<String> roles, String role) {
        if (CollectionUtil.isNotEmpty(roles)) {
            for (String s : roles) {
                if (s.equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkPermissions(RequiresPermissions requiresPermissions) {
        if (requiresPermissions.logical() == Logical.AND) {
            checkPermissionsAnd(requiresPermissions.value());
        } else {
            checkPermissionsOr(requiresPermissions.value());
        }

    }

    public static void checkPermissionsOr(String... permissions) {
        SystemUser systemUser = SecureUtil.getSystemUser();
        if (systemUser == null) {
            throw new NotLoginException("无效的token");
        }
        Set<String> permissionList = systemUser.getPermissions();
        for (String permission : permissions) {
            if (hasPermission(permissionList, permission)) {
                return;
            }
        }
        if (permissions.length > 0) {
            throw new NotPermissionException(permissions);
        }
    }

    public static void checkPermissionsAnd(String... permissions) {
        SystemUser systemUser = SecureUtil.getSystemUser();
        if (systemUser == null) {
            throw new NotLoginException("无效的token");
        }
        Set<String> permissionList = systemUser.getPermissions();
        for (String permission : permissions) {
            if (!hasPermission(permissionList, permission)) {
                throw new NotPermissionException(permission);
            }
        }
    }

    public static boolean hasPermission(Collection<String> permissionList, String permission) {
        if (CollectionUtil.isNotEmpty(permissionList)) {
            for (String s : permissionList) {
                if (s.equals(permission)) {
                    return true;
                }
            }
        }
        return false;
    }
}
