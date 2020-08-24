package com.aaa.service;

import com.aaa.entity.Message;

import java.util.Map;

public interface UserService {
    Message login(Map map);
    Message reg(Map map);//register
}
