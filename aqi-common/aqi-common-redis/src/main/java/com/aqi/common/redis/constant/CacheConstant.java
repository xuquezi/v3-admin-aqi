package com.aqi.common.redis.constant;

public interface CacheConstant {
    String NONCE_KEY = "nonce:";

    String CAPTCHA_CACHE_PRE = "Captcha:";

    Long CAPTCHA_CACHE_EXPIRE_TIME = 120l;

    String CLIENT_KEY = "client";

    String USER_LOCK = "user-lock:";

    String USER_LOCK_TIMES = "user-lock-times:";
}
