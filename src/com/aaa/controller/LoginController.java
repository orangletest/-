package com.aaa.controller;

import com.aaa.entity.Message;
import com.aaa.service.UserService;
import com.aaa.service.UserServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/Login/*")
public class LoginController extends  BaseController {
    UserService userService = new UserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        //text/html
        resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");//post 方式请求的编码方式
        //get-->tomcat-->config-->server.xml中加入URIEncoding=utf-8
        PrintWriter out = resp.getWriter();
        String op = req.getParameter("op");
        if(null == op){
            //官方宣传页
            resp.sendRedirect(this.projPath+"/index.jsp");
        }else{
            switch (op){
                case "doLogin":
                    doLogin(req,resp,out);
                    break;

            }
        }
        out.flush();
        out.close();


    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
        Map<String,String[]> map = req.getParameterMap();//直接把请求里的数据封装称为map
        Message msg = userService.login(map);
        out.println(JSON.toJSONString(msg));//把message对象转为json数据格式；

    }
}
