package com.qfedu.service.impl;/*
 *@ClassName:UserServiceImpl
 *@Author:lg
 *@Description:
 *@Date:2020/6/1514:14
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qfedu.entity.User;
import com.qfedu.mapper.UserMapper;
import com.qfedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);

        User user = userMapper.selectOne(queryWrapper);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public Integer register(User user) {
        return userMapper.insert(user);
    }
}
