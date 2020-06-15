package com.qfedu.service;/*
 *@InterfaceName:IUserService
 *@Author:lg
 *@Description:
 *@Date:2020/6/1514:13
 */

import com.qfedu.entity.User;

public interface IUserService {

    User login(String username, String password);

    Integer register(User user);

}
