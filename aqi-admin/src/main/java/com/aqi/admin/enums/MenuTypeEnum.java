package com.aqi.admin.enums;

public enum MenuTypeEnum {
    TYPE_MENU("C", "菜单"),
    TYPE_BUTTON("F", "按钮"),
    TYPE_DIR("M", "目录"),
    TYPE_LINK("L", "外链"),
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    MenuTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
