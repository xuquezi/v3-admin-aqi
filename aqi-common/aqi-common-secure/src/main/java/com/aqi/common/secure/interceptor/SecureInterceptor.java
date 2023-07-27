package com.aqi.common.secure.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aqi.common.secure.constant.SecurityConstants;
import com.aqi.common.secure.context.SecurityContextHolder;
import com.aqi.common.core.entity.R;
import com.aqi.common.core.enums.HttpStatusEnum;
import com.aqi.common.secure.entity.SystemUser;
import com.aqi.common.secure.utils.AuthUtil;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecureInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(SecureInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求头中的token
        String authToken = request.getHeader(SecurityConstants.AUTH_HEADER);
        try {
            if (StrUtil.isBlank(authToken)) {
                this.handleException(response, "用户没有登录");
                return false;
            }
            SignedJWT jwt = SignedJWT.parse(authToken);
            //校验是否有效
            if (!AuthUtil.verify(jwt)) {
                this.handleException(response, "Token无效");
                return false;
            }
            //校验超时
            if (AuthUtil.checkExpire(jwt)) {
                this.handleException(response, "Token已过期");
                return false;
            }
            Object payload = jwt.getJWTClaimsSet().getClaim(SecurityConstants.AUTH_PAYLOAD);
            if (payload == null) {
                this.handleException(response, "解析token失败");
                return false;
            }
            SystemUser systemUser = JSONObject.parseObject(payload.toString(), SystemUser.class);
            if (systemUser == null) {
                this.handleException(response, "解析token失败");
                return false;
            }
            // 设置SecurityContextHolder
            SecurityContextHolder.set(SecurityConstants.LOGIN_USER, systemUser);
            return true;
        } catch (Exception e) {
            log.error("解析token失败 {}", e);
            return false;
        }
    }

    private void handleException(HttpServletResponse response, String message) throws IOException {
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        String str = JSONObject.toJSONString(R.fail(HttpStatusEnum.UNAUTHORIZED.getCode(), message));
        response.getWriter().write(str);
    }
}
