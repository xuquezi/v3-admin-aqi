package com.aqi.common.log.aspect;


import cn.hutool.extra.spring.SpringUtil;
import com.aqi.api.admin.SysLogProvider;
import com.aqi.api.request.LogSaveReq;
import com.aqi.common.secure.entity.SystemUser;
import com.aqi.common.secure.utils.SecureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class SysLogAop {


    private final HttpServletRequest request;
    /**
     * 开始时间
     */
    private Date visitTime;
    /**
     * 访问的类
     */
    private Class clazz;
    /**
     * 访问的方法
     */
    private Method method;

    @Pointcut("@annotation(com.aqi.common.log.annotation.SysLog)")
    public void pointcut() {
    }

    // 前置通知  主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
    @Before("pointcut()")
    public void doBefore(JoinPoint jp) {
        // 当前时间就是开始访问的时间
        visitTime = new Date();
        // 具体要访问的类
        clazz = jp.getTarget().getClass();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        method = signature.getMethod();

    }

    // 后置通知
    @After("pointcut()")
    public void doAfter(JoinPoint jp) {
        // 获取访问的时长
        long time = new Date().getTime() - visitTime.getTime();
        String url = "";
        // 获取url
        // LogAop放在controller包内所以要排除
        if (clazz != null && method != null && clazz != SysLogAop.class) {
            // 获取类上的@RequestMapping("/orders")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();
                // 获取方法上的@RequestMapping(xxx)
                RequestMapping requestMethodAnnotation = method.getAnnotation(RequestMapping.class);
                GetMapping getMethodAnnotation = method.getAnnotation(GetMapping.class);
                PostMapping postMethodAnnotation = method.getAnnotation(PostMapping.class);
                DeleteMapping deleteMethodAnnotation = method.getAnnotation(DeleteMapping.class);
                PutMapping putMethodAnnotation = method.getAnnotation(PutMapping.class);
                String methodUrl = getUrlFromMethodAnnotation(requestMethodAnnotation, getMethodAnnotation, postMethodAnnotation, deleteMethodAnnotation, putMethodAnnotation);
                url = classValue[0] + methodUrl;
                // 获取访问的ip
                String ip = this.getIpAddr(request);
                // 获取当前操作的用户
                SystemUser systemUser = SecureUtil.getSystemUser();
                String username = systemUser.getUserName();
                log.info("操作用户名为：" + systemUser.getUserName());
                // 将日志相关信息封装到SysLog对象
                LogSaveReq logSaveReq = new LogSaveReq();
                logSaveReq.setExecutionTime(time);
                logSaveReq.setIp(ip);
                logSaveReq.setMethod("[类名] " + clazz.getName() + "[方法名] " + method.getName());
                logSaveReq.setUrl(url);
                logSaveReq.setUsername(username);
                logSaveReq.setVisitTime(visitTime);
                SpringUtil.getBean(SysLogProvider.class).saveLog(logSaveReq);
            }
        }
    }

    private String getUrlFromMethodAnnotation(RequestMapping requestMethodAnnotation, GetMapping getMethodAnnotation, PostMapping postMethodAnnotation, DeleteMapping deleteMethodAnnotation, PutMapping putMethodAnnotation) {
        String methodUrl = "";
        if (getMethodAnnotation != null) {
            return getMethodAnnotation.value()[0];
        } else if (postMethodAnnotation != null) {
            return postMethodAnnotation.value()[0];
        } else if (putMethodAnnotation != null) {
            return putMethodAnnotation.value()[0];
        } else if (deleteMethodAnnotation != null) {
            return deleteMethodAnnotation.value()[0];
        } else if (requestMethodAnnotation != null) {
            return requestMethodAnnotation.value()[0];
        }
        // 方法上没有映射地址的注解返回空字符串
        return methodUrl;
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}