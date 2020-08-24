package com.aaa.service;

import com.aaa.dao.DepartDaoImpl;
import com.aaa.dao.IDepartDao;
import com.aaa.entity.Message;

import java.util.List;
import java.util.Map;

public class DepartServiceImpl implements IDepartService {
    IDepartDao departDao = new DepartDaoImpl();
    @Override
    public Message depts() {
        List<Map> list = departDao.depts();
        return new Message(0,"",list.size(),list);
    }
}
