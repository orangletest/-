package com.aaa.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseController extends HttpServlet {
    String projPath = null;//项目路径
    String visitPath = "/upimg";//访问路径
    String tmp = "D:\\workspace\\upload_img\\";

    @Override
    public void init() throws ServletException {
        ServletContext application = this.getServletContext();
        projPath = application.getContextPath();// 例如：/hotel
        application.setAttribute("visitPath",visitPath);

    }
}
