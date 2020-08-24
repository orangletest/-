package com.aaa.dao;

import java.util.Map;

public interface UserDao {

    //登录
    int findUserBynamePwd(Map map);

    //注册
    int addUser(Map map);
}
