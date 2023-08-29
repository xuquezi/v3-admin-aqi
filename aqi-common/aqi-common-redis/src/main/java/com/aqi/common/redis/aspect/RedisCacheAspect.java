package com.aqi.common.redis.aspect;

import com.aqi.common.redis.annotation.MyRedisCache;
import com.aqi.common.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@RequiredArgsConstructor
public class RedisCacheAspect {

    private final RedisUtils redisUtils;

    @Pointcut("@annotation(com.aqi.common.redis.annotation.MyRedisCache)")
    public void cachePointCut() {
    }


    @Around("cachePointCut()")
    public Object doCache(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            //1 获得重载后的方法名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());

            //2 确定方法名后获得该方法上面配置的注解标签MyRedisCache
            MyRedisCache myRedisCacheAnnotation = method.getAnnotation(MyRedisCache.class);

            //3 拿到了MyRedisCache这个注解标签，获得该注解上面配置的参数进行封装和调用
            String keyPrefix = myRedisCacheAnnotation.keyPrefix();
            String matchValueSpringEL = myRedisCacheAnnotation.matchValue();

            //4 SpringEL 解析器
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(matchValueSpringEL);
            EvaluationContext context = new StandardEvaluationContext();

            //5 获得方法里面的形参个数
            Object[] args = joinPoint.getArgs();
            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i].toString());
            }
            //6 通过上述，拼接redis的最终key形式
            String key = keyPrefix + expression.getValue(context).toString();

            //7 先去redis里面查询看有没有
            result = redisUtils.get(key);
            if (result != null) {
                return result;
            }

            //8 redis里面没有，去找数据库查询
            result = joinPoint.proceed();
            if (result != null) {
                //9 数据库查询结束，还需要把结果存入redis一次，缓存补偿
                redisUtils.set(key, result);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
