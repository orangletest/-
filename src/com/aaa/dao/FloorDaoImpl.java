package com.aaa.dao;

import com.aaa.util.BaseDao;

import java.util.List;
import java.util.Map;

public class FloorDaoImpl implements IFloorDao  {
    @Override
    public int addFloor(Map map) {
        String sql = "insert into floor(floorname) values(?)";
        return BaseDao.executeUpd(sql,map.get("floorname"));
    }

    @Override
    public int delFloor(int floorid) {
        String sql = "delete from floor where floorid = ?";
        return BaseDao.executeUpd(sql,floorid);
    }

    @Override
    public int upFloor(Map map) {
        String sql = "update floor set floorname = ? where floorid = ?";

        return BaseDao.executeUpd(sql,map.get("floorname"),map.get("floorid"));
    }

    @Override
    public Map getFloorById(int floorid) {
        Map map = null;
        String sql = "select floorid,floorname from floor where floorid = ?";
        List<Map> list = BaseDao.selectAll(sql,floorid);
        if(null != list && list.size()>0){
            map = list.get(0);
        }
        return map;
    }

    @Override
    public long getNum() {
        List<Map>  list= BaseDao.selectAll("select count(floorid) num from floor",null);
        return (Long)list.get(0).get("num");
    }

    @Override
    public List<Map> findAll(int start, int page) {
        String sql = "select floorid, floorname from floor order by floorid desc limit ?,?";
        return BaseDao.selectAll(sql,start,page);
    }

    @Override
    public boolean batchDel(int[] ids) {
        String sql  ="delete from floor where floorid = ?";
        return BaseDao.batchDel(sql,ids);
    }
}
