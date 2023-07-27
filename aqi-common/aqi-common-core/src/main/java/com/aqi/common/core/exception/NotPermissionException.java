package com.aqi.common.core.exception;

import cn.hutool.core.util.StrUtil;

public class NotPermissionException extends RuntimeException {
    public NotPermissionException(String permission) {
        super(permission);
    }

    public NotPermissionException(String[] permissions) {
        super(StrUtil.join(",", permissions));
    }
}
