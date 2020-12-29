package com.ntx.base.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.ntx.base.annotation.RateLimit;
import com.ntx.base.util.ResponseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
@Scope
@Aspect
public class RateLimitAspect {


    RateLimiter rateLimiter = RateLimiter.create(1.0);


    @Pointcut("@annotation(com.ntx.base.annotation.RateLimit)")
    public void serviceLimit() {
    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        rateLimiter.setRate(this.getAnnotation(joinPoint));
        boolean flag = rateLimiter.tryAcquire();
        if(flag){
            return joinPoint.proceed();
        } else{
            return ResponseUtil.abnormal(100, "太频繁啦啦啦啦啦啦");
        }
    }

    private long getAnnotation(ProceedingJoinPoint joinPoint) throws Exception{
        //获取拦截的方法名
        Signature sig = joinPoint.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
        return annotation.rate();
    }

}
