package com.aqi.common.core.handler;

import com.aqi.common.core.entity.R;
import com.aqi.common.core.enums.HttpStatusEnum;
import com.aqi.common.core.exception.NotLoginException;
import com.aqi.common.core.exception.NotPermissionException;
import com.aqi.common.core.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public R exceptionHandler(NotLoginException e) {
        log.error("登录验证失败 {}", e);
        return R.fail(HttpStatusEnum.UNAUTHORIZED.getCode(), "登录验证失败 " + e.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    public R exceptionHandler(NotRoleException e) {
        log.error("没有配置角色 {}", e);
        return R.fail(HttpStatusEnum.NO_PERMISSION.getCode(),"没有配置角色" + e.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    public R exceptionHandler(NotPermissionException e) {
        log.error("没有配置权限 {}", e);
        return R.fail(HttpStatusEnum.NO_PERMISSION.getCode(),"没有配置权限" + e.getMessage());
    }

    // 捕捉所有异常，例如IOException不属于RuntimeException，在这里处理
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        log.error("系统内部异常，异常信息 {}", e);
        return R.fail(e.getMessage());
    }
}