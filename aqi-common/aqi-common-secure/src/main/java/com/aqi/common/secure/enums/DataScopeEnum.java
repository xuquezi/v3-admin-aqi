package com.aqi.common.secure.enums;

import lombok.Getter;

public enum DataScopeEnum {
    /**
     * value 越小，数据权限范围越大
     */
    ALL(0, "所有数据"),
    DEPT_AND_SUB(1, "部门及子部门数据"),
    DEPT(2, "本部门数据"),
    SELF(3, "本人数据");

    @Getter
    private int value;

    @Getter
    private String label;

    DataScopeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public static DataScopeEnum getDataScopeEnum(int code) {
        for (DataScopeEnum e : DataScopeEnum.values()) {
            if (e.getValue() == code) {
                return e;
            }
        }
        return null;
    }
}
