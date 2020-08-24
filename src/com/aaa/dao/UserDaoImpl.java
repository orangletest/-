package com.aaa.dao;

import com.aaa.util.BaseDao;

import java.util.List;
import java.util.Map;

public class UserDaoImpl implements  UserDao {
    @Override
    public int findUserBynamePwd(Map map) {
        //获取的是long类型
        String sql  = "select count(userid) num from users where username = ? and userpwd = ?";
        String[] username =(String[])map.get("username");
        String[] userpwd =(String[]) map.get("userpwd");
        List<Map> list = BaseDao.selectAll(sql,username[0],userpwd[0]);
        Long count  = (Long) list.get(0).get("num");
        int result = Integer.parseInt(count+"");
        return result;
    }

    @Override
    public int addUser(Map map) {
        String sql = "insert into users(username,userpwd) values(?,?)";
        return BaseDao.executeUpd(sql,map.get("username"),map.get("userpwd"));
    }
}
