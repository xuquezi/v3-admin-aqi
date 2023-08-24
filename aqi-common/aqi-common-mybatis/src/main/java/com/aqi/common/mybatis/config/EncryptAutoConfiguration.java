package com.aqi.common.mybatis.config;

import com.aqi.common.mybatis.encrypt.DefaultEncryptor;
import com.aqi.common.mybatis.encrypt.EncryptionQueryInterceptor;
import com.aqi.common.mybatis.encrypt.EncryptionResultInterceptor;
import com.aqi.common.mybatis.encrypt.EncryptionSaveInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration(
        proxyBeanMethods = false
)
public class EncryptAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public DefaultEncryptor defaultEncryptor() {
        return new DefaultEncryptor();
    }


    @Bean
    public EncryptionResultInterceptor encryptionResultInterceptor(DefaultEncryptor defaultEncryptor) {
        return new EncryptionResultInterceptor(defaultEncryptor);
    }

    @Bean
    public EncryptionQueryInterceptor encryptionQueryInterceptor(DefaultEncryptor defaultEncryptor) {
        return new EncryptionQueryInterceptor(defaultEncryptor);
    }

    @Bean
    public EncryptionSaveInterceptor encryptionSaveInterceptor(DefaultEncryptor defaultEncryptor) {
        return new EncryptionSaveInterceptor(defaultEncryptor);
    }
}
