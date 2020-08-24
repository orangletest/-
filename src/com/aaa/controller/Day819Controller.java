package com.aaa.controller;

import com.aaa.entity.Message;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/day/*")
public class Day819Controller extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        String op = req.getParameter("op");
        if(null == op){
            resp.sendRedirect(this.projPath+"/index.jsp");
        }else{
            switch (op){
                case "doAdd":
                    doAdd(req,resp,out);
                    break;
            }
        }
        out.flush();
        out.close();


    }

    private void doAdd(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {

        String username = req.getParameter("username");
        String pwd = req.getParameter("password");
        String sex = req.getParameter("sex");
        String[] likes = req.getParameterValues("like");
        String isZai = req.getParameter("isZai");
        String desc = req.getParameter("desc");
        String city = req.getParameter("city");
        //封装map
        Map map = new HashMap();
        map.put("username",username);
        map.put("pwd",pwd);
        map.put("sex",sex);
        map.put("likes",likes);
        map.put("isZai",isZai);
        map.put("desc",desc);
        map.put("city",city);
        //发给service--》dao-->数据库
        Message msg = new Message(0,"",0,map);
        out.println(JSON.toJSONString(msg));
    }
}
