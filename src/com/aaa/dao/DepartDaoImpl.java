package com.aaa.dao;

import com.aaa.util.BaseDao;

import java.util.List;
import java.util.Map;

public class DepartDaoImpl implements IDepartDao  {
    @Override
    public List<Map> depts() {
        String sql = "select dpId,dpName from department";
        return BaseDao.selectAll(sql,null);
    }
}
