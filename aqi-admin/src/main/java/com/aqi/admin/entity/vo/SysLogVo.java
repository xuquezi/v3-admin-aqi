package com.aqi.admin.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysLogVo对象", description = "")
public class SysLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    private String logId;

    @ApiModelProperty(value = "执行时长")
    private Long executionTime;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "method")
    private String method;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date visitTime;
}
