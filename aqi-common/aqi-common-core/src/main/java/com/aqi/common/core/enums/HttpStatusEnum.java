package com.aqi.common.core.enums;

public enum HttpStatusEnum {
    UNAUTHORIZED(401, "未授权"),
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    NO_PERMISSION(403, "无权限"),
    ;

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    HttpStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
