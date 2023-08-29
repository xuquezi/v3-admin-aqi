package com.aqi.common.mybatis.encrypt;

import cn.hutool.core.collection.CollUtil;
import com.aqi.common.mybatis.entity.Encrypted;
import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;

@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class EncryptionResultInterceptor extends EncryptionBaseInterceptor implements Interceptor {

    public EncryptionResultInterceptor(IEncryptor encryptor) {
        super(encryptor);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
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
}
