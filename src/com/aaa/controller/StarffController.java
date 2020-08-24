package com.aaa.controller;

import com.aaa.entity.Message;
import com.aaa.service.DepartServiceImpl;
import com.aaa.service.IDepartService;
import com.aaa.service.IStarffService;
import com.aaa.service.StarffServiceImpl;
import com.aaa.util.BaseUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/starff/*")
public class StarffController extends BaseController {
    IStarffService starffService = new StarffServiceImpl();
    IDepartService departService = new DepartServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        String op = req.getParameter("op");
        PrintWriter out = resp.getWriter();
        if(null == op){
            resp.sendRedirect(this.projPath+"/welcome.jsp");

        }else{
            switch (op){
                case "allStarff":
                    allStarff(req,resp,out);
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
                case "findByTj":
                    findByTj(req,resp,out);
                    break;
                case "doUpload":
                    doUpload(req,resp,out);
                    break;
                case "allDepts":
                    allDepts(req,resp,out);
                    break;
            }
        }
        out.flush();
        out.close();

    }

    private void allDepts(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
       Message msg =  departService.depts();
       BaseUtil.printJson(out,msg);

    }

    private void doUpload(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
       File tempPath = new File(tmp);//存放目录
        DiskFileItemFactory factory=new DiskFileItemFactory();//上传的主要处理工具
        factory.setRepository(tempPath);
        factory.setSizeThreshold(4096);

        ServletFileUpload upload=new ServletFileUpload(factory);
        upload.setSizeMax(1000000);
        List<?> fileitems=null;//获取上传文件的列表
        try{
            fileitems=upload.parseRequest(req);//从请求里取到上传的文件
            Iterator<?> iterator=fileitems.iterator();
            String regex=".+\\\\(.+)$";
            String[] errortype={".exe",".com",".cgi",".asp"};
            Pattern p= Pattern.compile(regex);
            List<String> imgs = new ArrayList<>();//上传图片的名字
            while(iterator.hasNext()){
                FileItem item=(FileItem) iterator.next();
                if(!item.isFormField()){//如果不是普通的文本域
                    String name=item.getName();//获取原来的文件路径C:\Users\Administrator\Pictures\11.png
                    //获取后缀
                    String suffix =name.substring(name.indexOf("."),name.length());
                    //对文件进行重命名
                    String filename =System.currentTimeMillis()+""+suffix;
                    //组合新的路径
                   String imgname = tempPath+File.separator+filename;
                    long size=item.getSize();
                    if(name==null||name.equals("")&&size==0)
                        continue;
                    Matcher m=p.matcher(imgname);
                    if(m.find()){
                        for(int temp=0;temp<errortype.length;temp++){
                            if(m.group(1).endsWith(errortype[temp]))
                                throw new IOException(name+":wrong type");
                        }
                        try{
                            File f = new File(imgname);
                            item.write(f);
                            imgs.add(filename);
                        }catch(Exception e){
                            out.println("333"+e);
                        }
                    }
                    else{
                        throw new IOException("fail to upload");
                    }

                }
            }
            Message msg = new Message(0,"",imgs.size(),imgs);
            BaseUtil.printJson(out,msg);
        }catch(IOException e){
            e.printStackTrace();
        }
        catch(FileUploadException e1){
            e1.printStackTrace();
        }

    }

    private void findByTj(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
        String index = req.getParameter("page")==null?"1":req.getParameter("page");
        String limit = req.getParameter("limit") ==  null?"5":req.getParameter("limit");
        String ocId = req.getParameter("ocId");
        String oczsName = req.getParameter("oczsName");
        String ocName = req.getParameter("ocName");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        Map map = new HashMap();
        if(null != ocId&&ocId!=""){
            map.put("ocId",ocId);
        }
        if(null != oczsName&&oczsName!=""){
            map.put("oczsName",oczsName);
        }
        if(null != ocName&&ocName!=""){
            map.put("ocName",ocName);
        }
        if(null != startTime&&startTime!=""){
            map.put("startTime",startTime);
        }
        if(null != endTime&&endTime!=""){
            map.put("endTime",endTime);
        }
        Message msg = starffService.findPageTiaojian(BaseUtil.transFromStr(index),BaseUtil.transFromStr(limit),map);
        BaseUtil.printJson(out,msg);

    }

    private void batchDel(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
        String strids = req.getParameter("ids");
        String[] sids = strids.split(",");
        int[] ids = BaseUtil.fromStrArr(sids);
        Message msg = starffService.batchDel(ids);
        out.println(JSON.toJSONString(msg));
    }

    private void doDel(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {

        String sid = req.getParameter("ocId");
        int ocId = BaseUtil.transFromStr(sid);
        Message msg = starffService.delStarff(ocId);
        out.println(JSON.toJSONString(msg));
    }

    private void doUpdate(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {

        String ocId = req.getParameter("ocId");
        String ocName = req.getParameter("ocName");
        String oczsName = req.getParameter("oczsName");
        String ocPassword = req.getParameter("ocPassword");
        String ocEntryTime = req.getParameter("ocEntryTime");
        String ocSex = req.getParameter("ocSex");
        String ocBrithday = req.getParameter("ocBrithday");
        String ocAddress = req.getParameter("ocAddress");
        String ocPhone = req.getParameter("ocPhone");
        String dpId = req.getParameter("dpId");
        String ocImg = req.getParameter("ocImg");
        Map map = new HashMap();
        map.put("ocId",ocId);
        map.put("ocName",ocName);
        map.put("oczsName",oczsName);
        map.put("ocPassword",ocPassword);
        map.put("ocEntryTime",ocEntryTime);
        map.put("ocSex",ocSex);
        map.put("ocBrithday",ocBrithday);
        map.put("ocAddress",ocAddress);
        map.put("ocPhone",ocPhone);
        map.put("dpId",dpId);
        map.put("ocImg",ocImg);
     Message msg = starffService.updateStarff(map);
     BaseUtil.printJson(out,msg);
    }

    private void doAdd(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {

        String ocName = req.getParameter("ocName");
        String oczsName = req.getParameter("oczsName");
        String ocPassword = req.getParameter("ocPassword");
        String ocEntryTime = req.getParameter("ocEntryTime");
        String ocSex = req.getParameter("ocSex");
        String ocBrithday = req.getParameter("ocBrithday");
        String ocAddress = req.getParameter("ocAddress");
        String ocPhone = req.getParameter("ocPhone");
        String dpId = req.getParameter("dpId");
        String ocImg = req.getParameter("ocImg");
        Map map = new HashMap();
        map.put("ocName",ocName);
        map.put("ocPassword",ocPassword);
        map.put("oczsName",oczsName);
        map.put("ocEntryTime",ocEntryTime);
        map.put("ocSex",ocSex);
        map.put("ocBrithday",ocBrithday);
        map.put("ocAddress",ocAddress);
        map.put("ocPhone",ocPhone);
        map.put("dpId",dpId);
        map.put("ocImg",ocImg);
       Message msg =  starffService.addStarff(map);
       BaseUtil.printJson(out,msg);


    }


    private void allStarff(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) {
       //page-->第几页  limit-->每页的条数
        String index = req.getParameter("page")==null?"1":req.getParameter("page");
       String limit = req.getParameter("limit") ==  null?"5":req.getParameter("limit");
       Message msg = starffService.findByPage(BaseUtil.transFromStr(index),BaseUtil.transFromStr(limit));
       BaseUtil.printJson(out,msg);
    }

}
