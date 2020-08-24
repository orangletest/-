package com.aaa.dao;

import java.util.List;
import java.util.Map;

public interface IStarffDao {

    //添加员工
    int addStarff(Map map);

    //修改员工
    int updateStarff(Map map);

    //根据员工编号查找
    Map findById(int sid);

    //删除员工
    int delStarff(int sid);

    //查询所有的员工的个数
    int starffNum();


    //分页查找员工
    List<Map>  findByPage(int start,int page);

    //分页条件查找员工
    List<Map> findPageTiaojian(int start,int page,Map map);

    //根据条件查找的总数据
    int findPageTiaojianNum(Map map);
    //批量删除
    boolean batchDel(int[] ids);

}
