package com.aqi.job.enums;

public enum MisfireEnum {
    DEFAULT("0", "默认"),
    IGNORE_MISFIRES("1", "立即触发执行"),
    FIRE_AND_PROCEED("2", "触发一次执行"),
    DO_NOTHING("3", "不触发立即执行"),
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    MisfireEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
