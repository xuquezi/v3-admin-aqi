package com.aqi.common.mybatis.encrypt;

import com.aqi.common.mybatis.entity.Encrypted;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

@Slf4j
@Intercepts({
        @Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class}),
})
public class EncryptionSaveInterceptor extends EncryptionBaseInterceptor implements Interceptor {

    public EncryptionSaveInterceptor(IEncryptor encryptor) {
        super(encryptor);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object entity = args[1];
        if (isInsertOrUpdate(mappedStatement)) {
            if (entity instanceof Encrypted) {
                this.encrypt((Encrypted) entity);
            } else if (entity instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) entity;
                if (paramMap.containsKey("et") && paramMap.get("et") instanceof Encrypted) {
                    this.encrypt((Encrypted) paramMap.get("et"));
                }
            }
        }
        return invocation.proceed();
    }
}
