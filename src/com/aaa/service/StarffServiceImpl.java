package com.aaa.service;

import com.aaa.dao.IStarffDao;
import com.aaa.dao.StarffDaoImpl;
import com.aaa.entity.Message;

import java.util.List;
import java.util.Map;

public class StarffServiceImpl implements IStarffService {
    IStarffDao starffDao = new StarffDaoImpl();
    @Override
    public Message addStarff(Map map) {
        int result = starffDao.addStarff(map);
        return new Message(0,"",0,result);
    }

    @Override
    public Message updateStarff(Map map) {
        int result = starffDao.updateStarff(map);
        return new Message(0,"",0,result);
    }

    @Override
    public Message findById(int sid) {
        Map map = starffDao.findById(sid);
        return new Message(0,"",0,map);
    }

    @Override
    public Message delStarff(int sid) {
        int result = starffDao.delStarff(sid);
        return new Message(0,"",0,result);
    }

    @Override
    public Message starffNum() {
        int num = starffDao.starffNum();
        return new Message(0,"",0,num);
    }

    /**
     *
     * @param index-->当前页码
     * @param page-->每页条数
     * @return
     */
    @Override
    public Message findByPage(int index, int page) {
        int start = (index-1)*page;
        List<Map> list = starffDao.findByPage(start,page);//查出当页的数据
        int num = starffDao.starffNum();//总条数
        return new Message(0,"",num,list);
    }

    /**
     *
     * @param index ->当前页码
     * @param page->每页条数
     * @param map-->封装了条件
     * @return
     */
    @Override
    public Message findPageTiaojian(int index, int page, Map map) {
        int start = (index-1)*page;
        List<Map> list = starffDao.findPageTiaojian(start,page,map);
        int num = starffDao.findPageTiaojianNum(map);//条件查找的总条目
        return new Message(0,"",num,list);
    }

    @Override
    public Message batchDel(int[] ids) {
        boolean flag = starffDao.batchDel(ids);
        return new Message(0,"",0,flag);
    }
}
