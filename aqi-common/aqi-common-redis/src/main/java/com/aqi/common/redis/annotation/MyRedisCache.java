package com.aqi.common.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRedisCache {

    //SpringEL表达式，解析占位符对应的匹配value值
    String matchValue();

    //键的前缀prefix,
    String keyPrefix();
}
