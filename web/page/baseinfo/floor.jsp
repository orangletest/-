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
    <title>楼层管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="${path}/resources/layui/css/layui.css"/>
    <script src="${path}/resources/layui/layui.js" charset="utf-8"></script>
</head>
<body>
<table class="layui-hide" id="test" lay-filter="test"></table>
<!--对于表头的操作-->
<script id="toolbarDemo" type="text/html">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="addFloor">添加楼层</button>
        <button class="layui-btn layui-btn-sm" lay-event="delBatch">批量删除</button>
    </div>
</script>
<!--对于每行的操作-->
<script id="barDemo" type="text/html">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!--添加或者修改的弹出层start-->
<div style="display: none;padding: 20px;" id="saveOrUpdateDiv" >
    <form class="layui-form" action="" id="dataFrm">
        <div class="layui-form-item" style="display: none" id="fid">
            <label class="layui-form-label">楼层编号</label>
            <div class="layui-input-block">
                <input id="floorId" type="text" name="floorId" required   readonly placeholder="楼层编号" autocomplete="off" class="layui-input">
            </div>
        </div>
     <div class="layui-form-item">
        <label class="layui-form-label">楼层名称</label>
       <div class="layui-input-block">
          <input type="text" id="floorName" name="floorName" required  lay-verify="required|nameCheck" placeholder="请输入楼层名称" autocomplete="off" class="layui-input">
        </div>
      </div>
        <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                  <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
        </div>

    </form>
</div>
<!--添加或者修改的弹出层end-->



<script>
    layui.use(['jquery','table','layer','form'] ,function(){
        var $ = layui.jquery;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
       //表单校验
        form.verify({
            nameCheck:function(value){
                //必须是以大写F结尾
                //前面必须是数字,数字长度是两位
                var reg =/^\d+$/;
                var len = value.length;
                var end = value.substr(len-1,1);

                if(end != "F"){
                    return "必须以F结尾";
                }
                var nums = value.substring(0,len-1);
                console.log(nums);
                if(!reg.test(nums)){
                    return "F前面必须是数字";
                }
                if(value.length>3){
                    return "楼层数不合理";
                }


            }
        });


        //渲染数据表格
        var tableIns=table.render({
            elem: '#test'   //渲染的目标对象
            ,url:'${path}/Baseinfo?op=allFloors' //数据接口
            ,title: '用户数据表'//数据导出来的标题
            ,toolbar:"#toolbarDemo"   //表格的工具条
            ,cellMinWidth:100 //设置列的最小默认宽度
            ,page: true  //是否启用分页
            ,limit:2 //设置每页显示条数 默认为10
            ,limits:[2,5,10,15]
            ,text:{
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
            //
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'floorId', title:'ID', width:100, fixed: 'left',  sort: true}
                ,{field:'floorName', title:'楼层名', width:120}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]

        })

        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'addFloor':
                    addFloor();
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
                            ids += data[i].floorId+',';
                        }
                    }

                    ids =ids.substring(0,ids.length-1);
                    delBatch(ids);
                    tableIns.reload({url:'${path}/Baseinfo?op=allFloors'});
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    layer.close(index);
                   var floorid = data.floorId;
                   $.ajax({
                       url:"${path}/Baseinfo?op=doDel",
                       type:'post',
                       data:{'floorid':floorid},
                       dataType:"json",
                       success:function(data){
                           if(data.data == 1){
                               layer.msg('删除成功！');
                               tableIns.reload();
                           }else{
                               layer.msg('删除失败！');
                           }
                       }
                   });

                });


            } else if(obj.event === 'edit'){
                openUpdateUser(data);
            }
        });
        var mainindex;
        //添加方法
        function addFloor() {
            mainindex = layer.open({

                    type:1,
                    title:"添加楼层",
                    content:$("#saveOrUpdateDiv"),
                    area:['400px','200px'],
                    success:function(index){
                        $("#fid").hide();
                        $("#dataFrm")[0].reset();
                        form.on("submit(formDemo)",function(){
                            layer.msg("正在提交");
                                $.ajax({
                                    url:"${path}/Baseinfo?op=doAdd",
                                    data:$("#dataFrm").serialize(),
                                    dataType:"json",
                                    type:"post",
                                    success:function(data){
                                        if(data.data == 1){
                                            layer.msg("添加成功！")
                                            tableIns.reload();
                                        }else{
                                            layer.msg("添加失败！")
                                        }

                                    },error:function(msg){
                                        alert(msg);
                                    }
                                });
                            return false;

                        });
                    }

        });

        }
        //修改
        //打开修改页面
        function openUpdateUser(data){
            mainIndex=layer.open({
                type:1,
                title:'修改楼层',
                content:$("#saveOrUpdateDiv"),
                area:['400px','300px'],

                success:function(index){
                    $("#fid").show();
                   $("#floorId").val(data.floorId);
                    $('#floorName').val(data.floorName);
                    form.on("submit(formDemo)",function(){
                            $.ajax({
                                url:"${path}/Baseinfo?op=doUpdate",
                                data:$("#dataFrm").serialize(),
                                dataType:"json",
                                type:"post",
                                success:function(data){
                                    if(data.data == 1){
                                        layer.msg("修改成功！")
                                        tableIns.reload();

                                    }else{
                                        layer.msg("修改失败！")
                                    }

                                }
                            });
                        return false;
                    });


                }

            });
        }
        //批量删除
        function delBatch(ids){
            $.ajax({
                url:"${path}/Baseinfo?op=batchDel",
                data:{"ids":ids},
                dataType:"json",
                type:"post",
                success:function(data){
                    if(data.data){
                        layer.msg("删除成功");

                    }else{
                        layer.msg("删除失败");
                    }
                }

            });

        }
    });
</script>


</body>
</html>