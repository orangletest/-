<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    request.setAttribute("path", path);
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>表单测试</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="${path}/resources/layui/css/layui.css"/>
    <script src="${path}/resources/js/jquery-2.1.3.js" charset="utf-8"></script>
    <script src="${path}/resources/layui/layui.js" charset="utf-8"></script>
    <style type="text/css">
        #frmContent{
            border:1px solid yellow;
            width:800px;
            height: 450px;
            margin:50px auto;
            padding:20px;
        }


    </style>
</head>
<body>
<div id="frmContent">
    <form class="layui-form" action="" id="regFrm" lay-filter="regFrm">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-block">
                <input type="text" id="username"  name="username" required  lay-verify="required|lenCheck" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">长度在3-6位之间</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码框</label>
            <div class="layui-input-inline">
                <input type="password"  name="password" required lay-verify="required|pwdCheck" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">长度在6-8位之间</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">城市</label>
            <div class="layui-input-block">
                <select name="city" lay-verify="required" id="citys" lay-filter="citys">
                    <option value="请选择城市"></option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">爱好：</label>
            <div class="layui-input-block">
                <input type="checkbox" name="like" value="write" title="写作">
                <input type="checkbox" name="like" value="read" title="阅读" checked>
                <input type="checkbox" name="like" value="dai" title="发呆">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否在职</label>
            <div class="layui-input-block">
                <input type="checkbox" name="isNot" lay-skin="switch">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <input type="radio" name="sex" value="男" title="男">
                <input type="radio" name="sex" value="女" title="女" checked>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">自我介绍</label>
            <div class="layui-input-block">
                <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>

    </form>
    <table id="info" class="layui-table">
        <tr>
            <td>姓名</td>
            <td>密码</td>
            <td>城市</td>
            <td>爱好</td>
            <td>是否在职</td>
            <td>描述</td>
            <td>性别</td>
        </tr>
    </table>
</div>

    <script>
        var citys ;
$(function(){
    $.ajax({
        url:"${path}/test?op=doCitys",
        type:"post",
        dataType:"json",
        success:function(result){
           console.log(result['data']);
        },
        error:function(msg){
            console.log(msg);
        }
    });
});
        //Demo
        layui.use(['jquery','form','layer'], function(){
            var $ = layui.jquery;
            var form = layui.form;
            var layer = layui.layer;
            $("#citys").html("<option value='-1'>请选择城市</option>");
            console.log(citys);
            // $.each(citys,function(k,v){
            //     var op = $("<option>").val(k).text(v);
            //     $("#citys").append(op);
            //
            // });
            // form.render("select");






            form.verify({
                lenCheck:function(value){
                    if(value.length<3||value.length>6){
                        return '3--6';
                    }
                },
                pwdCheck:function(value){
                    if(value.length<6||value.length>8){
                        return '6--8';
                    }
                }

            });
            form.on("submit(formDemo)",function(){
                $.ajax({
                    url:"${path}/test?op=doTest",
                    data:$('#regFrm').serialize(),
                    dataType:'json',
                    type:'post',
                    success:function(data){
                        $('#regFrm')[0].reset();
                        var obj = data.data;
                        var content = $("#info").html();
                        var info = "<tr>";
                        info += "<td>"+obj.username+"</td>";
                        info += "<td>"+obj.userpwd+"</td>";
                        info += "<td>"+obj.city+"</td>";
                        info += "<td>"+obj.like+"</td>";
                        info += "<td>"+obj.switchname+"</td>";
                        info += "<td>"+obj.desc+"</td>";
                        info += "<td>"+obj.sex+"</td>";
                        info += "</tr>";
                        $("#info").append(info);

                    },
                    error:function(msg){
                        layer.alert(msg);
                    }
                });
                return false;
            });

        });

    </script>

</body>
</html>