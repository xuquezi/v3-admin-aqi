package com.aqi.api.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogSaveReq implements Serializable {
    private static final long serialVersionUID = 1L;

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
