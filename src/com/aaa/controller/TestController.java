package com.aaa.controller;

import com.aaa.entity.Message;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/test/*")
public class TestController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        //text/html
       // resp.setContentType("application/json;charset=utf-8");
        req.setCharacterEncoding("utf-8");//post 方式请求的编码方式
        //get-->tomcat-->config-->server.xml中加入URIEncoding=utf-8
        PrintWriter out = resp.getWriter();
        String op = req.getParameter("op");
        if(null == op){
            //官方宣传页
            resp.sendRedirect(this.projPath+"/index.jsp");
        }else{
            switch (op){
                case "doTest":
                    doTest(req,resp,out);
                    break;
                case "doCitys":
                    doCitys(req,resp,out);
                    break;

            }
        }
        out.flush();
        out.close();
    }

    private void doCitys(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
        List<Map> citys = new ArrayList<>();
        String[] cs = {"北京","上海","深圳","广州","郑州","西安"};
        for (int i = 0; i <6 ; i++) {
            Map map = new HashMap();
            map.put(i+1,cs[i]);
            citys.add(map);

        }
        Message msg = new Message(0,"",citys.size(),citys);
        out.println(JSON.toJSONString(msg));
    }

    private void doTest(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
        String username = req.getParameter("username");
        String userpwd = req.getParameter("password");
        String city = req.getParameter("city");
        String[] like = req.getParameterValues("like");
        String switchname = req.getParameter("isNot");
        String desc = req.getParameter("desc");
        String sex = req.getParameter("sex");
        Map map = new HashMap<>();
        if(null !=switchname && switchname.equals("on")){
            switchname = "在职";
        }
        map.put("username",username);
        map.put("userpwd",userpwd);
        map.put("city",city);
        map.put("like",like);
        map.put("switchname",switchname);
        map.put("desc",desc);
        map.put("sex",sex);
        Message msg = new Message(0,"",0,map);
        out.println(JSON.toJSONString(msg));


    }
}
