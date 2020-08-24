package com.aaa.dao;

import com.aaa.util.BaseDao;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StarffDaoImpl implements  IStarffDao {
    @Override
    public int addStarff(Map map) {
        String sql = "insert into officeclerk(ocName,oczsName,ocPassword,ocEntryTime,ocSex," +
                "ocBrithday,dpId,ocPhone,ocAddress,ocImg) values(?,?,?,?,?,?,?,?,?,?)";

        return BaseDao.executeUpd(sql,map.get("ocName"),map.get("oczsName"),
                map.get("ocPassword"),map.get("ocEntryTime"),map.get("ocSex"),map.get("ocBrithday"),
                map.get("dpId"),map.get("ocPhone"),map.get("ocAddress"),map.get("ocImg"));
    }

    @Override
    public int updateStarff(Map map) {
        String sql = "update officeclerk set ocName = ?,oczsName = ?," +
                "ocPassword =?,ocEntryTime = ?,ocSex = ?,\n" +
                "ocBrithday = ?,dpId = ?,ocPhone= ?,ocAddress = ?,ocImg = ?\n" +
                "where ocId = ?";
        return BaseDao.executeUpd(sql,map.get("ocName"),map.get("oczsName"),
                map.get("ocPassword"),map.get("ocEntryTime"),map.get("ocSex"),map.get("ocBrithday"),
                map.get("dpId"),map.get("ocPhone"),map.get("ocAddress"),map.get("ocImg"),map.get("ocId"));
    }

    @Override
    public Map findById(int sid) {
        Map map = null;
        String sql = "select o.ocId,o.ocName,o.oczsName,o.ocPassword,o.ocEntryTime,\n" +
                "o.ocSex,o.ocBrithday,o.ocPhone,o.ocAddress,o.ocImg,d.dpId,d.dpName " +
                "from officeclerk o \n" +
                "inner join department d on o.dpId = d.dpId where o.ocId = ?";
        List<Map> list = BaseDao.selectAll(sql,sid);
        if(null != list && list.size()>0){
            map = list.get(0);
        }
        return map;
    }

    @Override
    public int delStarff(int sid) {
        String sql = "delete from officeclerk where ocId = ?";
        return BaseDao.executeUpd(sql,sid);
    }

    @Override
    public int starffNum() {
        String sql = "select count(ocId) num from officeclerk";
        List<Map> list = BaseDao.selectAll(sql,null);
        long num = (Long)list.get(0).get("num");
        int result = Integer.parseInt(num+"");
        return result;
    }

    /**
     *
     * @param start-->开始的位置
     * @param page--》每页显示的条数
     * @return
     */
    @Override
    public List<Map> findByPage(int start, int page) {
        String sql = "select o.ocId,o.ocName,o.oczsName,o.ocPassword,o.ocEntryTime,\n" +
                "o.ocSex,o.ocBrithday,o.ocPhone,o.ocAddress,o.ocImg,d.dpId,d.dpName " +
                "from officeclerk o \n" +
                "inner join department d on o.dpId = d.dpId order by o.ocId desc limit ?,? ";
        return BaseDao.selectAll(sql,start,page);
    }

    /**
     * 注意拼接的时候，and前面要留下空格
     * @param start
     * @param page
     * @param map-->查询的条件
     * @return
     */
    @Override
    public List<Map> findPageTiaojian(int start, int page, Map map) {
        //根据编号，账号，时间开始和结束，以及部门名称来查询

        String sql = "select  o.ocId,o.ocName,o.oczsName,o.ocPassword,o.ocEntryTime,\n" +
                " o.ocSex,o.ocBrithday,o.ocPhone,o.ocAddress,o.ocImg,d.dpId,d.dpName from officeclerk o,department d\n" +
                "where o.dpId = d.dpId";
        StringBuilder strsql = new StringBuilder(sql);//方便SQL语句的拼接
        if(null != map && map.size()>0){
            Set set = map.keySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                String key = (String)it.next();

                if(key.equals("ocId")){
                    strsql.append(" and o.ocId ="+map.get(key));
                }
                if(key.equals("ocName")){
                    strsql.append(" and o.ocName like '%"+map.get(key)+"%'");
                }
                if(key.equals("oczsName")){
                    strsql.append(" and o.oczsName like '%"+map.get(key)+"%'");
                }
                if(key.equals("startTime")){
                    String endTime = (String)map.get("endTime");
                    strsql.append(" and o.ocEntryTime between '"+map.get(key)+"' and '"+endTime+"'");
                }


            }

        }
        strsql.append(" order by o.ocId desc limit ?,?");
        List<Map> list = BaseDao.selectAll(strsql.toString(),start,page);
        return list;
    }

    /**日期要加单引号
     * and前后要留空格
     * @param map
     * @return
     */
    @Override
    public int findPageTiaojianNum(Map map) {
        String sql = "select  count(o.ocId) num from officeclerk o,department d\n" +
                "where o.dpId = d.dpId";
        StringBuilder strsql = new StringBuilder(sql);
        if(null != map && map.size()>0){
            Set set = map.keySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                String key = (String)it.next();

                if(key.equals("ocId")){
                    strsql.append(" and o.ocId ="+map.get(key));
                }
                if(key.equals("ocName")){
                    strsql.append(" and o.ocName like '%"+map.get(key)+"%'");
                }
                if(key.equals("oczsName")){
                    strsql.append(" and o.oczsName like '%"+map.get(key)+"%'");
                }
                if(key.equals("startTime")){
                    String endTime = (String)map.get("endTime");
                    strsql.append(" and o.ocEntryTime between '"+map.get(key)+"' and '"+endTime+"'");
                }


            }

        }
        strsql.append(" order by o.ocId desc");
        List<Map> list = BaseDao.selectAll(strsql.toString(),null);
        long num = (Long)list.get(0).get("num");
        int result = Integer.parseInt(num+"");
        return result;
    }

    @Override
    public boolean batchDel(int[] ids) {
        String sql  ="delete from officeclerk where ocId = ?";
        return BaseDao.batchDel(sql,ids);
    }
}
