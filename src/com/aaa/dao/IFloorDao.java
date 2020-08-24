package com.aaa.dao;

import java.util.List;
import java.util.Map;

public interface IFloorDao {
    int addFloor(Map map);
    //删除
    int delFloor(int floorid);

    //修改
    int upFloor(Map map);

    //查询单个
    Map getFloorById(int floorid);

    //查询总的个数
    long getNum();
    //查询所有分页
    List<Map> findAll(int start,int page);

    //批量删除
    boolean batchDel(int[] ids);

}
