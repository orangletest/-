<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    request.setAttribute("path", path);
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>测试</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

    <link rel="stylesheet" href="${path}/resources/layui/css/layui.css"/>
    <script type="text/javascript" src="${path}/resources/js/jquery-2.1.3.js"></script>
    <script src="${path}/resources/layui/layui.js" charset="utf-8"></script>
    <style type="text/css">
        #content{
            margin:50px auto;
            width:80%;
            height:auto;
            border:1px solid green;
        }
    </style>
</head>
<body>
<div id="content">
    <form class="layui-form" action="" id="regFrm">
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input type="text" name="username" required  lay-verify="required|nameCheck" placeholder="请输入标题" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码框</label>
            <div class="layui-input-inline">
                <input type="password" name="password" required lay-verify="required|lenCheck" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">密码长度必须是6位</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">城市</label>
            <div class="layui-input-block">
                <select name="city" lay-verify="required" >
                    <option value=""></option>
                    <option value="0">北京</option>
                    <option value="1">上海</option>
                    <option value="2">广州</option>
                    <option value="3">深圳</option>
                    <option value="4">杭州</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">爱好</label>
            <div class="layui-input-block">
                <input type="checkbox" name="like" value="write" title="写作">
                <input type="checkbox" name="like" value="read" title="阅读" checked>
                <input type="checkbox" name="like" value="dai" title="发呆">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">在职</label>
            <div class="layui-input-block">
                <input type="checkbox" name="isZai" lay-skin="switch">
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
    <table class="layui-table" id="info">
        <tr>
            <td>用户名</td>
            <td>密码</td>
            <td>性别</td>
            <td>城市</td>
            <td>爱好</td>
            <td>在职状态</td>
            <td>自我介绍</td>

        </tr>
    </table>
</div>

<script>
    //Demo
    //不仅使用layui的校验还有自己的校验
    //提交给后台，后台如何接受
    //从后台再把提交的数据传到前台的表格里
    layui.use(['form','jquery','table'], function(){
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        //对于表单元素进行校验
        form.verify({
            nameCheck:function(value){
                if(value.length<2 || value.length>6){
                    return "用户名的长度必须在2-6位之间";
                }
            },
            lenCheck:function(value){
                if(value.length != 6){
                    return "密码必须是六位";
                }
            }
        });



        //监听提交
        form.on('submit(formDemo)', function(data){
            //使用ajax进行一步提交
            $.ajax({
                url:'${path}/day?op=doAdd',
                data:$("#regFrm").serialize(),//传给servlet的数据
                dataType:"json",//servlet响应给前端的数据的格式
                type:"post",
                success:function(result){//是后台返回来的数据
                    //清空表单数据
                    $('#regFrm')[0].reset();
                    console.log(result);
                    var obj = result.data;
                    var info = "<tr>";
                    info += "<td>"+obj.username+"</td>"
                    info += "<td>"+obj.pwd+"</td>"
                    info += "<td>"+obj.sex+"</td>"
                    info += "<td>"+obj.city+"</td>"
                    info += "<td>"+obj.likes+"</td>"
                    info += "<td>"+obj.isZai+"</td>"
                    info += "<td>"+obj.desc+"</td>"
                    info += "</tr>";
                    $("#info").append(info);


                },
                error:function(msg){
                    layer.msg(msg);
                }
            });


            return false;//用来组织submit重新刷新页面
        });
    });

$(function(){
    $.ajax({

        url:"${path}/test?op=doCitys",
        dataType:"json",
        type:"post",
        success:function(result){
            console.log("result");
            console.log(result);
        },
        error:function(msg){
            console.log(msg);
        }
    });
    layui.use('form',function(){
        var form = layui.form;
        form.render('select');
    });
});
</script>
</body>
</html>