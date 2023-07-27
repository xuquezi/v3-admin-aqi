package com.aqi.job.enums;

public enum SaveLogEnum {
    SAVE("0", "保存日志"),
    IGNORE("1", "忽略日志"),
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    SaveLogEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
