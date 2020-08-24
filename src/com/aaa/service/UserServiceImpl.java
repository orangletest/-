package com.aaa.service;

import com.aaa.dao.UserDao;
import com.aaa.dao.UserDaoImpl;
import com.aaa.entity.Message;

import java.util.Map;

public class UserServiceImpl implements  UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public Message login(Map map) {
       int result =  userDao.findUserBynamePwd(map);
       return new Message(0,"",0,result);
    }

    @Override
    public Message reg(Map map) {
        int result = userDao.addUser(map);
        return new Message(0,"",0,result);
    }
}
