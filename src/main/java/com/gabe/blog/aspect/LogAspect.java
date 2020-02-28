package com.gabe.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    //拿到日誌紀錄器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.gabe.blog.web.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //用于存放用户信息的session
        //RequestContextHolder这个类的作用是通过操作RequestAttributes请求属性这个对象（绑定了线程）来间接处理请求相关的一些东西。
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);

    }

    @After("log() ")
    public void doAfter() {
        //logger.info("------doAfter---------");
    }


    @AfterReturning(returning = "result", pointcut = "log()")  //才能透過這個參數去補貨攔截的方法的廢回內容
    //方法執行完，返回後去攔截他
    public void doAfterReturn(Object result) {
        logger.info("Result : {}" + result);
    }


    private class RequestLog {
        private String url;
        private String ip;
        private String classmethod;
        private Object[] args; //請求的參數不一定，所以用object數組，都可以接收

        public RequestLog(String url, String ip, String classmethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classmethod = classmethod;
            this.args = args;
        }

        //好寫入日誌
        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classmethod='" + classmethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
