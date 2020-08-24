package com.aaa.service;

import com.aaa.dao.FloorDaoImpl;
import com.aaa.dao.IFloorDao;
import com.aaa.entity.Message;

import java.util.List;
import java.util.Map;

public class FloorServiceImpl implements IFloorService {
    IFloorDao floorDao = new FloorDaoImpl();
    @Override
    public Message addFloor(Map map) {
        int result = floorDao.addFloor(map);
        return new Message(0,"",0,result);
    }

    @Override
    public Message delFloor(int floorid) {
        int result = floorDao.delFloor(floorid);
        return new Message(0,"",0,result);
    }

    @Override
    public Message upFloor(Map map) {
        int result = floorDao.upFloor(map);
        return new Message(0,"",0,result);
    }

    @Override
    public Message getFloorById(int floorid) {
        Map map = floorDao.getFloorById(floorid);
        return new Message(0,"",0,map);
    }

    @Override
    public Message getNum() {
        long num = floorDao.getNum();
        return new Message(0,"",0,num);
    }

    @Override
    public Message findAll(int index, int page) {
        int start = (index-1)*page;
        List<Map> list = floorDao.findAll(start,page);
        long num = floorDao.getNum();
        int intnum = Integer.parseInt(num+"");
        return new Message(0,"",intnum,list);
    }

    @Override
    public Message batchDel(int[] ids) {
        boolean flag = floorDao.batchDel(ids);
        return new Message(0,"",0,flag);
    }
}
