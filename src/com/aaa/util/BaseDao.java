package com.aaa.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao {
    private static DataSource dataSource = null;
    static {
        try {
            Context initCtx = new InitialContext();
            //检索命名空间的根目录
            Context envCtx = (Context)initCtx.lookup("java:comp/env/");

            //根据context.xml中配置的<Resource>元素的name属性值
//到JNDI容器中检索连接池对象
            dataSource = (DataSource) envCtx.lookup("yyh/mysql");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public static Connection getCon(){
        Connection con =null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    public static  void closeAll(Connection con, Statement stmt, ResultSet rs){
        try {
            if(null != rs){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(null != stmt){
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(null != con){
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询封装方法
    public  static List<Map> selectAll(String sql, Object...obs){
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        con = getCon(); //必须赋值
        List<Map> list = new ArrayList<>();
        try {
            psmt = con.prepareStatement(sql);//带问号的SQL语句
            //给问号赋值
            setParams(psmt,obs);//SQL语句就完整了
            rs =  psmt.executeQuery();
            //遍历么一行的数据，然后把每一行都封装到一个map里面
            //然后把每一行放到list里面
            //要想知道有几列，必须使用结果集元数据
            ResultSetMetaData rsmt = rs.getMetaData();
            //获取列数
            int num = rsmt.getColumnCount();//5
            while(rs.next()){
                //问题如何把每一行放到map<列名,列值>
                Map<String,Object> row = new HashMap<>();
                for (int i = 1; i <=num ; i++) {
                    row.put(rsmt.getColumnName(i),rs.getObject(i));
                }
                list.add(row);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con,psmt,rs);

        }
        return list;

    }
    //用来给SQL语句的问号赋值，如果没有问号则不用赋值
    //可变参数本质就是一个数组
    public static  void setParams(PreparedStatement psmt,Object...obs){
        if(null == psmt){
            throw new RuntimeException("psmt为null");
        }
        //是否有问号决定于传过来的可变参数，obs传过来几个值就有几个问号
        if(null != obs && obs.length != 0){
            for (int i = 0; i < obs.length; i++) {
                //问号是从1开始的
                //数组的下标从零开始的
                //问号和数组的值一一对应
                try {
                    psmt.setObject(i+1,obs[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //增删改
    public  static  int  executeUpd(String sql,Object...obs){
        int result = -1;
        Connection con = null;
        PreparedStatement psmt = null;
        con =getCon();
        try {
            psmt = con.prepareStatement(sql);
            //给问号赋值
            setParams(psmt,obs);
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con,psmt,null);
        }

        return result;
    }
    //批量删除
    //update floor set state = 0 where id = ?
    public static boolean batchDel(String sql,int[] ids){//[1,2,3,4,7,8,9]
        boolean flag = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getCon();
            con.setAutoCommit(false);//设置事务的提交方式为手动提交
            pstmt = con.prepareStatement(sql);
            for(int i =0 ;i<ids.length;i++){
                pstmt.setObject(1,ids[i]);
                pstmt.addBatch();//批量存储到预编译对象里
            }
            pstmt.executeBatch(); //批量执行
            con.commit();//提交事务
            flag = true;
        } catch (SQLException e) {
            try {
                con.rollback(); //进行事务回滚
            } catch (SQLException ex) {
            }
        }finally {
           closeAll(con,pstmt,null);
        }
        return flag;


    }

}

