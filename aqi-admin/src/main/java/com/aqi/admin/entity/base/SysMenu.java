package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aqi.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "sys_menu")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    @TableId(value = "menu_id", type = IdType.INPUT)
    private Long menuId;

    private String menuName;

    private Long menuParentId;

    private Integer menuOrderNum;

    private String menuPath;

    private String menuComponent;

    private String menuType;

    private String menuVisible;

    private String menuPerms;

    private String menuIcon;

    private String redirect;
}
