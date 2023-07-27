package com.aqi.provider.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.aqi.api.admin.SysClientProvider;
import com.aqi.api.response.SysClientRes;
import com.aqi.common.redis.constant.CacheConstant;
import com.aqi.common.core.entity.R;
import com.aqi.common.core.enums.HttpStatusEnum;
import com.aqi.common.core.utils.SignUtil;
import com.aqi.common.redis.utils.RedisUtils;
import com.aqi.provider.constant.ApiConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(ApiInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {
            String appKey = request.getHeader(ApiConstant.APP_KEY);
            if (StrUtil.isBlank(appKey)) {
                this.handleException(response, "appKey不能为空");
                return false;
            }
            String sign = request.getHeader(ApiConstant.SIGN);
            if (StrUtil.isBlank(sign)) {
                this.handleException(response, "sign不能为空");
                return false;
            }
            String nonce = request.getHeader(ApiConstant.NONCE);
            if (StrUtil.isBlank(nonce)) {
                this.handleException(response, "nonce不能为空");
                return false;
            }
            RedisUtils redisUtils = SpringUtil.getBean(RedisUtils.class);
            String nonceKey = CacheConstant.NONCE_KEY + nonce;
            boolean exists = redisUtils.exists(nonceKey);
            if (exists) {
                this.handleException(response, "非法请求");
                return false;
            } else {
                redisUtils.set(nonceKey, nonce, 60l);
            }
            String timestamp = request.getHeader(ApiConstant.TIMESTAMP);
            if (StrUtil.isBlank(timestamp)) {
                this.handleException(response, "timestamp不能为空");
                return false;
            }
            long requestTime = Long.parseLong(timestamp);
            long nowTime = System.currentTimeMillis();
            long interval = nowTime - requestTime;
            // 时间超过60s为非法请求
            if (interval > 60000l) {
                this.handleException(response, "非法请求");
                return false;
            }
            if (!validSign(sign, appKey, nonce, timestamp)) {
                this.handleException(response, "鉴权失败");
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("鉴权失败 {}", e);
            return false;
        }
    }

    private boolean validSign(String sign, String appKey, String nonce, String timestamp) {
        SysClientProvider sysClientProvider = SpringUtil.getBean(SysClientProvider.class);
        SysClientRes clientRes = sysClientProvider.getClientByKey(appKey);
        if (clientRes == null || StrUtil.isBlank(clientRes.getClientSecret())) {
            return false;
        }
        String appSecret = clientRes.getClientSecret();
        String content = "appSecret=" + appSecret + "&nonce=" + nonce + "&timestamp=" + timestamp;
        try {
            String checkSign = SignUtil.sha(content);
            if (checkSign.equalsIgnoreCase(sign)) {
                return true;
            }
        } catch (Exception e) {
            log.error("解析异常 {}", e);
        }
        return false;
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
