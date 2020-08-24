package com.aaa.service;

import com.aaa.entity.Message;

import java.util.List;
import java.util.Map;

public interface IStarffService {
    //添加员工
    Message addStarff(Map map);

    //修改员工
    Message updateStarff(Map map);

    //根据员工编号查找
    Message findById(int sid);

    //删除员工
    Message delStarff(int sid);

    //查询所有的员工的个数
    Message starffNum();


    //分页查找员工
    //index--》当前页
    Message findByPage(int index, int page);

    //分页条件查找员工
    Message findPageTiaojian(int index,int page,Map map);

    //批量删除
    //批量删除
    Message batchDel(int[] ids);
}
