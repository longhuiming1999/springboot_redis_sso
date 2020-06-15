package com.qfedu.aop;/*
 *@ClassName:LoginAop
 *@Author:lg
 *@Description:
 *@Date:2020/6/1515:00
 */

import com.alibaba.fastjson.JSON;
import com.qfedu.commons.isLoginAnno;
import com.qfedu.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Aspect
public class LoginAop {


    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录验证
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(com.qfedu.commons.isLoginAnno)")
    public String isLogin(ProceedingJoinPoint proceedingJoinPoint) {

        Object[] args = proceedingJoinPoint.getArgs();

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        Method method = methodSignature.getMethod();

        isLoginAnno annotation = method.getAnnotation(isLoginAnno.class);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;

        HttpServletRequest request = sra.getRequest();

        Cookie[] cookies = request.getCookies();

        String token = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }

        String result = null;

        if (StringUtils.isEmpty(token) == false) {
            result = redisTemplate.opsForValue().get(token);
        }

        // 已经登录
        if (result != null) {
            User user = (User) JSON.parseObject(result, User.class);
            System.out.println(user);
        } else {

            // 没有登录
            if (annotation.mustLogin() == true) {
                // 必须登录
                return "请您登录!!!";
            }
        }

        try {
            return (String) proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

}
