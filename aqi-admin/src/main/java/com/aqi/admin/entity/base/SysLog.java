package com.aqi.admin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_log")
public class SysLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    @TableId(value = "log_id", type = IdType.INPUT)
    private Long logId;
    /**
     * 执行时长
     */
    private Long executionTime;
    /**
     * ip
     */
    private String ip;
    /**
     * method
     */
    private String method;
    /**
     * url
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 访问时间
     */
    private Date visitTime;
}
