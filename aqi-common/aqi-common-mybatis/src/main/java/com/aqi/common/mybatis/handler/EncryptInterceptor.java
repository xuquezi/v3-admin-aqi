package com.aqi.common.mybatis.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.aqi.common.mybatis.entity.Encrypted;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        }
)
@Slf4j
@Lazy
@Configuration(
        proxyBeanMethods = false
)
public class EncryptInterceptor implements Interceptor {

    public static final byte[] AES_KEY = "VH2B9DB95VONPA5C".getBytes();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        if (StrUtil.equalsIgnoreCase("update", method.getName())) {
            return updateHandle(invocation);
        } else if (StrUtil.equalsIgnoreCase("query", method.getName())) {
            return selectHandle(invocation);
        } else {
            return invocation.proceed();
        }
    }

    private Object updateHandle(Invocation invocation) throws Exception {
        Object[] args = invocation.getArgs();
        Object entity = args[1];
        if (entity instanceof Encrypted) {
            this.encrypt((Encrypted) entity);
        } else if (entity instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) entity;
            if (paramMap.containsKey("et") && paramMap.get("et") instanceof Encrypted) {
                this.encrypt((Encrypted) paramMap.get("et"));
            }
        }
        return invocation.proceed();
    }

    private void encrypt(Field field, Object object) throws IllegalAccessException {
        String fieldStr = getFieldStr(field, object);
        if (fieldStr != null) {
            field.setAccessible(true);
            field.set(object, SecureUtil.aes(AES_KEY).encryptHex(fieldStr));
        }
    }

    private void encrypt(Encrypted object) throws IllegalAccessException {
        for (Field field : object.getEncryptFields()) {
            this.encrypt(field, object);
        }
    }


    private Object selectHandle(Invocation invocation) throws Exception {
        Object result = invocation.proceed();
        if (result instanceof ArrayList) {
            ArrayList list = (ArrayList) result;
            if (CollUtil.isNotEmpty(list) && list.get(0) instanceof Encrypted) {
                for (Object item : list) {
                    this.decrypt((Encrypted) item);
                }
            }
        } else if (result instanceof Encrypted) {
            this.decrypt((Encrypted) result);
        }
        return result;
    }

    private void decrypt(Encrypted item) {
        for (Field field : item.getEncryptFields()) {
            this.decrypt(field, item);
        }
    }

    private String getFieldStr(Field field, Object object) throws IllegalAccessException {
        Object val = field.get(object);
        if (val == null || StrUtil.isBlank(val.toString())) {
            return null;
        }
        return val.toString();
    }

    private void decrypt(Field field, Object object) {
        try {
            String fieldStr = getFieldStr(field, object);
            if (fieldStr != null) {
                field.setAccessible(true);
                field.set(object, SecureUtil.aes(AES_KEY).decryptStr(fieldStr));
            }
        } catch (Exception e) {
            log.error("解密失败 {}", e);
        }
    }
}
