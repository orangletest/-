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
    <title>员工管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="${path}/resources/layui/css/layui.css"/>
    <script src="${path}/resources/js/jquery-2.1.3.js" charset="utf-8"></script>
    <script src="${path}/resources/layui/layui.js" charset="utf-8"></script>
</head>
<body>
<!--条件筛选-->
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>查询条件</legend>
</fieldset>
<jsp:include page="starff_search.jsp"></jsp:include>
<!--表格-->
<table class="layui-hide" id="test" lay-filter="info"></table>
<!--表头-->
<script id="toolbarDemo" type="text/html">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="addObj">添加员工</button>
        <button class="layui-btn layui-btn-sm" lay-event="delBatch">批量删除</button>
        <button class="layui-btn layui-btn-sm" lay-event="refresh">刷新</button>
    </div>
</script>
<!--每行的操作-->
<script id="barDemo" type="text/html">
    <a class="layui-btn layui-btn-xs" lay-event="detail">详情</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!--弹出层-->
<%@ include file="starffadd.jsp"%>

<!--layui的操作-->
<script type="text/javascript">
    //在页面加载的时候把部门加载进来
    var depts;
    $(function(){
        $.post("${path}/starff?op=allDepts","",function(result){
           depts = result.data;
        },"json"
        );
    });

    layui.use(["jquery","table","form",'upload','laydate'],function(){
        var $ = layui.jquery;
        var table = layui.table;
        var form = layui.form;
        var upload = layui.upload;
        var laydate = layui.laydate;



        //绑定日期控件
        laydate.render({
            elem: '#ocEntryTime' //指定元素
        });
        laydate.render({
            elem: '#ocBrithday' //指定元素
        });
        laydate.render({
            elem: '#startTime' //指定元素
        });
        laydate.render({
            elem: '#endTime' //指定元素
        });


        //表格的渲染
        var tableIndex = table.render({//把渲染后的表格放到一个tableIndex的变量里
            elem:'#test'
            ,url:"${path}/starff?op=allStarff"
            ,toolbar:'#toolbarDemo'
            ,cellMinWidth:100 //设置列的最小默认宽度
            ,page: true  //是否启用分页
            ,limit:5 //设置每页显示条数 默认为10
            ,limits:[2,5,10,15]
            ,text:{
                none: '暂无相关数据' //默认：无数据的时候显示
            },
            done:function(res){
                console.log(res.data);
            }
            ,id:'testid'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'ocId', title:'ID', width:100, fixed: 'left',  sort: true}
                ,{field:'ocName', title:'用户名', width:120}
                ,{field:'oczsName', title:'姓名', width:120}
                ,{field:'ocEntryTime', title:'入职日期', width:120}
                ,{field:'ocSex', title:'性别', width:120}
                ,{field:'ocBrithday', title:'生日', width:120,hide:true}
                ,{field:'dpName', title:'部门名称', width:120}
                ,{field:'dpId', title:'部门编号', width:120,hide:true}
                ,{field:'ocPhone', title:'联系方式', width:120}
                ,{field:'ocAddress', title:'地址', width:120}
                ,{field:'ocImg', title:'头像', width:120,hide:true}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
        });
        //多条件查询
        function refresh(){
            var ocId = $("#ocId").val();
            var ocName = $("#ocName").val();
            var oczsName = $("#oczsName").val();
            var startTime= $("#startTime").val();
            var endTime = $("#endTime").val();
            tableIndex.reload(//重载，where后面设置的是传给后台的参数
                {
                    where:{
                        ocId:ocId,
                        ocName:ocName,
                        oczsName:oczsName,
                        startTime:startTime,
                        endTime:endTime
                    }
                    ,page:{
                        curr:1,
                        limit:5
                    }
                    ,url:'${path}/starff?op=findByTj'
                    ,method:'post'
                });
        }
        $("#doSearch").click(function(){
            refresh();
        });
        var mainindex;//这个变量用来保存打开的添加和修改
        //表头的添加和批量删除,info-->对应表格的lay-filter
        table.on('toolbar(info)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch(obj.event){
                case 'addObj':
                    add();
                    break;
                case 'delBatch':
                    var data = checkStatus.data;
                    if(data.length == 0){
                        layer.msg("必须选中才可以删除!");
                        return;
                    }
                    var ids = "";
                    if(data.length>0){
                        for (var i = 0; i <data.length ; i++) {
                            ids += data[i].ocId+',';//10,20,30,
                        }
                    }

                    ids =ids.substring(0,ids.length-1);
                    delBatch(ids);
                    break;
                case "refresh":
                    refresh();
                    break;
            };
        });

        //每行的操作，使用表格的监听事件
        table.on('tool(info)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent === 'detail'){ //查看
                mainIndex=layer.open({
                    type:1,
                    title:'详情',
                    content:$("#saveOrUpdateDiv"),
                    area:['800px','400px'],
                    success:function(index){
                        $('#dpId').empty();//清空列表框
                        $.each(depts,function(i,o){//i是下标  o--》是循环对象
                            console.log(o.dpId+"-->"+o.dpName);
                            var op = "<option value='"+o.dpId+"'>"+o.dpName+"</option>";
                            $("#dpId").append(op);
                        });
                        form.val("dataFrm",data);//把data给dataFrm表单赋值
                        var fmdata =form.val('dataFrm');//取值
                        var imgsrc = "<img src='${visitPath}/"+fmdata.ocImg+"' width='60px' height='60px' style='border-radius:15px;'/>";
                        $(".layui-upload").hide();
                        $("#showImg").show();
                        $("#showImg").html(imgsrc);
                        $("#bt1").hide();
                        $("#bt2").hide();
                    }
                });
            } else if(layEvent === 'del'){ //删除
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令
                    var id = data.ocId;
                    del(id);

                });
            } else if(layEvent === 'edit'){ //编辑
                $("#bt1").show();
                $("#bt2").show();
                mainIndex=layer.open({
                    type:1,
                    title:'详情',
                    content:$("#saveOrUpdateDiv"),
                    area:['800px','400px'],
                    success:function(index){
                        $('#dpId').empty();
                        $.each(depts,function(i,o){//i是下标  o--》是循环对象
                            console.log(o.dpId+"-->"+o.dpName);
                            var op = "<option value='"+o.dpId+"'>"+o.dpName+"</option>";
                            $("#dpId").append(op);
                        });
                        form.val("dataFrm",data);//给表单赋值
                        var fmdata =form.val('dataFrm');
                        var imgsrc = "<img src='${visitPath}/"+fmdata.ocImg+"' width='60px' height='60px' style='border-radius:15px;'/>";
                        $(".layui-upload").show();
                        $("#showImg").html(imgsrc);
                        form.on('submit(formDemo)',function(){
                            $.ajax({
                                url:"${path}/starff?op=doUpdate",
                                data:form.val('dataFrm'),
                                dataType:"json",
                                type:'post',
                                success:function(result){
                                    if(result.data == 1){
                                        layer.msg("修改成功！");
                                        $("#dataFrm")[0].reset();
                                        refresh();
                                    }
                                },
                                error:function(msg){
                                    console.log('修改出问题'+msg);
                                }
                            });
                            return false;
                        });


                    }
                });
            } else if(layEvent === 'LAYTABLE_TIPS'){
                layer.alert('Hi，头部工具栏扩展的右侧图标。');
            }
        });
        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            ,url: '${path}/starff?op=doUpload'
            ,accept:'images'
            ,acceptMime:'image/*'
            ,auto:true//是否选择文件之后自动上传
            ,field:'mf' //表单的name值
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            ,done: function(res){
                //如果上传失败
                if(res.code > 0){
                    return layer.msg('上传失败');
                }
               var mf = res.data[0];//把图片名称返回回来；
                //上传成功
                $("#ocImg").val(mf);
                form.render("","dataFrm");
                layer.msg("上传成功");
            }
            ,error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });
        //给部门下拉列表框赋值
        function add(){
            $('#dpId').empty();//下拉列表框清空
            $("#bt1").show();
            $("#bt2").show();
            $.each(depts,function(i,o){//i是下标  o--》是循环对象
                console.log(o.dpId+"-->"+o.dpName);
                var op = "<option value='"+o.dpId+"'>"+o.dpName+"</option>";
                $("#dpId").append(op);
            });
            form.render('select','dataFrm');//form 表单的lay-filter
            mainindex = layer.open({
                type:1,
                title:'添加用户',
                content:$("#saveOrUpdateDiv"),
                area:["800","500"],
                success:function(index){//当前层的索引
                    //执行添加
                    $("#dataFrm")[0].reset();//清空表单数据
                    $("#showImg").hide();//显示图片的div隐藏
                    $(".layui-upload").show();
                    form.on("submit(formDemo)",function(){

                        var params = form.val("dataFrm");
                        //alert($("#ocImg").val());
                        $.ajax({
                            url:'${path}/starff?op=doAdd',
                            data:params,
                            dataType:"json",
                            type:'post',
                            success:function(result){
                                if(result.data == 1){
                                    console.log(result.data);
                                    layer.msg("添加成功！");
                                    $("#dataFrm")[0].reset();
                                    refresh();
                                }

                            },
                            error:function(msg){
                                console.log(msg);
                            }
                        });
                        return false;
                    });
                }
            });
        }
        //添加结束
        //批量删除
        function delBatch(ids){
            $.ajax({
                url:"${path}/starff?op=batchDel",
                data:{"ids":ids},
                dataType:"json",
                type:"post",
                success:function(data){
                    if(data.data){
                        layer.msg("删除成功");
                        refresh();
                    }else{
                        layer.msg("删除失败");
                    }
                }

            });

        }
        //单个删除
        function del(id){
            $.ajax({
                url:"${path}/starff?op=doDel",
                type:'post',
                data:{'ocId':id},
                dataType:"json",
                success:function(data){
                    if(data.data == 1){
                        layer.msg('删除成功！');
                        refresh();
                    }else{
                        layer.msg('删除失败！');
                    }
                }
            });
        }

    });

</script>
</body>
</html>