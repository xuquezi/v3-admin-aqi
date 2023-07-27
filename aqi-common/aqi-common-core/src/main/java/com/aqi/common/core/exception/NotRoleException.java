package com.aqi.common.core.exception;

import cn.hutool.core.util.StrUtil;

public class NotRoleException extends RuntimeException {
    public NotRoleException(String role) {
        super(role);
    }

    public NotRoleException(String[] roles) {
        super(StrUtil.join(",", roles));
    }
}
