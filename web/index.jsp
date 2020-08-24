<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    String path=request.getContextPath();
    request.setAttribute("path",path);
%>
<html>
<head>
    <title>首页</title>
    <script src="${path}/resources/jquery-3.3.1.js"></script>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${path}/resources/layui/css/layui.css"  media="all"/>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-bg-green layui-layout-admin">
    <div class="layui-header layui-bg-green">
        <div class="layui-logo layui-bg-green">酒店后台管理</div>

        <ul class="layui-nav layui-bg-green layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    欢迎您，yyh
                </a>
            </li>
            <li class="layui-nav-item"><a href="${path}/user/loginOut">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-green layui-bg-black">
        <div class="layui-side-scroll layui-bg-green">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->


            <ul class="layui-nav layui-nav-tree layui-bg-green"   lay-filter="test">
                <li class="layui-nav-item " id="baseinfo">
                    <a href="javascript:;">基础数据</a>

                    <dl class="layui-nav-child" id="base1">
                            <dd><a href="javascript:;" class="funinfo" url="/page/baseinfo/floor.jsp">楼层管理</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="base2">
                        <dd><a href="javascript:;" class="funinfo" url="/page/baseinfo/roottype.jsp">房间类型管理</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="base3">
                        <dd><a href="javascript:;" class="funinfo" url="">客房管理</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="base4">
                        <dd><a href="javascript:;" class="funinfo" url="/page/baseinfo/starff.jsp">员工管理</a></dd>
                    </dl>

                </li>
                <li class="layui-nav-item " id="powerinfo">
                    <a href="javascript:;">权限管理</a>

                    <dl class="layui-nav-child" id="roleinfo">
                    <dd><a href="javascript:;" class="funinfo" url="">角色管理</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="rolepowerinfo">
                        <dd><a href="javascript:;" class="funinfo" url="">角色权限管理</a></dd>
                    </dl>

                </li>
                <li class="layui-nav-item " id="ordersinfo">
                    <a href="javascript:;">订单管理</a>

                    <dl class="layui-nav-child" id="addOrder">
                        <dd><a href="javascript:;" class="funinfo" url="">预定客房</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="updateOrder">
                        <dd><a href="javascript:;" class="funinfo" url="">修改订单</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="continueOrder">
                        <dd><a href="javascript:;" class="funinfo" url="">续住客房</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="exitOrder">
                        <dd><a href="javascript:;" class="funinfo" url="">退换客房</a></dd>
                    </dl>

                </li>
                <li class="layui-nav-item " id="customerinfo">
                    <a href="javascript:;">会员管理</a>

                    <dl class="layui-nav-child" id="addMoney">
                        <dd><a href="javascript:;" class="funinfo" url="">充值管理</a></dd>
                    </dl>
                    <dl class="layui-nav-child" id="selectCust">
                        <dd><a href="javascript:;" class="funinfo" url="">查看会员</a></dd>
                    </dl>

                </li>
                <li class="layui-nav-item " id="commentinfo">
                    <a href="javascript:;">评论管理</a>

                    <dl class="layui-nav-child" id="comments">
                        <dd><a href="javascript:;" class="funinfo" url="">管理评论</a></dd>
                    </dl>


                </li>

            </ul>


        </div>
    </div>

    <div class="layui-body">

        <iframe id="main" src="${path}/page/baseinfo/floor.jsp" style="width:100%;height:99%;border:none;margin: 5px 0px 0px 5px">

        </iframe>
    </div>


</div>

<script src="${path}/resources/layui/layui.js" charset="utf-8"></script>


<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });

    $(".funinfo").click(function () {
        var url=$(this).attr("url");
        console.log(url);
        $("#main").attr("src","${path}"+url);
    })
</script>
</body>

</html>
