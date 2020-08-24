package com.aaa.service;

import com.aaa.entity.Message;

import java.util.List;
import java.util.Map;

public interface IFloorService {
    Message addFloor(Map map);
    //删除
    Message delFloor(int floorid);

    //修改
    Message upFloor(Map map);

    //查询单个
    Message getFloorById(int floorid);

    //查询总的个数
    Message getNum();
    //查询所有分页
    Message findAll(int index, int page);

    //批量删除
    Message batchDel(int[] ids);
}
