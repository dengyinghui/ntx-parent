package com.ntx.base.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ntx.base.util.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(public * com.ntx.*.controller..*.*(..))")
    public void log() {

    }

    @AfterThrowing(pointcut = "log()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("异常日志:" + throwable);
        StringBuilder exception = new StringBuilder();
        exception.append(throwable).append("\n");
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for(StackTraceElement stackTraceElement : stackTraceElements){
            log.info(stackTraceElement.toString());
            exception.append(stackTraceElement.toString()).append("\n");
        }
    }


    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("============================ Start(" + request.getRequestURL() + ") =======================================");
        log.info("= 请求方法:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("= 请求参数:" + getParams(request, joinPoint));
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(JoinPoint joinPoint, Object object){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //getParams(request, joinPoint);
        if(object instanceof BaseResponse){
            log.info("= 返回数据:" + JSONObject.toJSONString(object));
        } else if(object instanceof String){
            log.info("= 返回数据:" + object);
        } else{
            log.info("= 返回数据:" + JSONObject.toJSONString(object));
        }
        log.info("============================ End(" + request.getRequestURL() + ") ==========================================");
    }


    private String getParams(HttpServletRequest request, JoinPoint joinPoint){
        StringBuilder stringBuilder = new StringBuilder();
        String contentType = request.getContentType();
        if("application/json".equalsIgnoreCase(contentType) || "application/json;utf-8".equalsIgnoreCase(contentType)){
            Object[] objects = joinPoint.getArgs();
            if(objects.length > 0){
                for(Object o : objects){
                    if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                        continue;
                    }
                    stringBuilder.append(JSONObject.toJSONString(o));
                }
            }
        } else{
            Map<String, String[]> params = request.getParameterMap();
            stringBuilder.append(JSONObject.toJSONString(params));
        }
        log.info(stringBuilder.toString());
        return stringBuilder.toString();
    }




}
