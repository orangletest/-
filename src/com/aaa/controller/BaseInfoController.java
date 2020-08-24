package com.aaa.controller;

import com.aaa.entity.Message;
import com.aaa.service.FloorServiceImpl;
import com.aaa.service.IFloorService;
import com.aaa.util.BaseUtil;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/Baseinfo/*")
public class BaseInfoController extends BaseController {
    IFloorService floorService = new FloorServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        String op = req.getParameter("op");
        PrintWriter out = resp.getWriter();
        if(null == op){
            resp.sendRedirect(this.projPath+"/welcome.jsp");

        }else{
            switch (op){
                case "allFloors":
                    allFloors(req,resp,out);
                    break;
                case "doAdd":
                    doAdd(req,resp,out);
                    break;
                case "doUpdate":
                    doUpdate(req,resp,out);
                    break;
                case "doDel":
                    doDel(req,resp,out);
                    break;
                case "batchDel":
                    batchDel(req,resp,out);
                    break;

            }
        }
        out.flush();
        out.close();

    }

    private void batchDel(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
    String strids = req.getParameter("ids");
    String[] sids = strids.split(",");
    int[] ids = BaseUtil.fromStrArr(sids);
    Message msg = floorService.batchDel(ids);
    out.println(JSON.toJSONString(msg));
    }

    private void doDel(HttpServletRequest req, HttpServletResponse resp,PrintWriter out) {
        String sid = req.getParameter("floorid");
        int floorid = BaseUtil.transFromStr(sid);
        Message msg = floorService.delFloor(floorid);
        out.println(JSON.toJSONString(msg));
    }



    private void doUpdate(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
        String sfloorid = req.getParameter("floorId");
        String floorname = req.getParameter("floorName");
        int floorid = BaseUtil.transFromStr(sfloorid);
        Map map = new HashMap();
        map.put("floorid",floorid);
        map.put("floorname",floorname);
        Message msg = floorService.upFloor(map);
        out.println(JSON.toJSONString(msg));
    }

    private void doAdd(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {

        String floorname = req.getParameter("floorName");
        Map map = new HashMap<>();
        map.put("floorname",floorname);
        Message msg = floorService.addFloor(map);
        out.println(JSON.toJSONString(msg));
    }

    private void allFloors(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
        String limit = req.getParameter("limit");
        String page = req.getParameter("page");
       if(null == limit){
           limit = "2";
       }
       if(null == page){
           page = "1";
       }
        Message msg = floorService.findAll(BaseUtil.transFromStr(page),BaseUtil.transFromStr(limit));
        out.println(JSON.toJSONString(msg));

    }
}
