package com.aqi.job.enums;

public enum TaskStatusEnum {
    PAUSE("1", "任务状态停用"),
    NORMAL("0", "任务状态正常"),
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    TaskStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
