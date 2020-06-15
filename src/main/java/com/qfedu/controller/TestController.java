package com.qfedu.controller;/*
 *@ClassName:TestController
 *@Author:lg
 *@Description:
 *@Date:2020/6/1514:13
 */

import com.alibaba.fastjson.JSON;
import com.qfedu.commons.isLoginAnno;
import com.qfedu.entity.User;
import com.qfedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private IUserService userService;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/register")
    public String register(User user) {

        System.out.println(user.toString());

        Integer result = userService.register(user);

        if (result > 0) {
            return "OK";
        }
        return "fail";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response) {

        User user = userService.login(username, password);

        if (user != null) {
            String token = UUID.randomUUID().toString();

            // 把用户数据保存到Redis中
            String jsonString = JSON.toJSONString(user);

            redisTemplate.opsForValue().set(token, jsonString);

            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);

            return "OK";
        }

        return "fail";
    }

    @RequestMapping("/soucang")
    @isLoginAnno(mustLogin = true)
    public String soucang() {


        return "OK";
    }

}
