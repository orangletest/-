<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!--添加或者修改的弹出层start-->
<div style="display: none;padding: 20px;" id="saveOrUpdateDiv" >
    <form class="layui-form" action="" id="dataFrm" lay-filter="dataFrm">
        <div class="layui-form-item" style="display: none" id="fid">
            <label class="layui-form-label">员工编号</label>
            <div class="layui-input-block">
                <input id="ocId" type="text" name="ocId" required   readonly placeholder="员工编号" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input type="text" id="ocName" name="ocName" required  lay-verify="required" placeholder="请输入员工名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">真实姓名</label>
            <div class="layui-input-block">
                <input type="text" id="oczsName" name="oczsName" required  lay-verify="required" placeholder="请输入员工账号" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">员工密码</label>
            <div class="layui-input-block">
                <input type="password" id="ocPassword" name="ocPassword" required  lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">入职日期</label>
            <div class="layui-input-block">
                <input type="text" id="ocEntryTime" name="ocEntryTime" required  lay-verify="required" placeholder="请选择日期" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <input type="radio" name="ocSex" value="男" title="男">
                <input type="radio" name="ocSex" value="女" title="女" checked>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">出生日期</label>
            <div class="layui-input-block">
                <input type="text" id="ocBrithday" name="ocBrithday" required  lay-verify="required|nameCheck" placeholder="请选择日期" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-block">
                <input type="text" id="ocAddress" name="ocAddress" required  lay-verify="required|nameCheck" placeholder="请输入家庭地址" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系方式</label>
            <div class="layui-input-block">
                <input type="text" id="ocPhone" name="ocPhone" required  lay-verify="required|phone" placeholder="请输入联系方式" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">部门</label>
            <div class="layui-input-block">
                <select name="dpId" lay-filter="dpId" id="dpId">
                    <option value="-1">请选择</option>
                </select>
            </div>
        </div>
        <div class="layui-upload">
            <label class="layui-form-label">上传头像</label>
            <input type="hidden" name="ocImg" id="ocImg"/>
            <button type="button" class="layui-btn" id="test1">
                <i class="layui-icon">&#xe67c;</i>上传图片
            </button>
            <div class="layui-upload-list">
                <img class="layui-upload-img" width="60" height="60" id="demo1" style="text-align: center;"/>
                <p id="demoText"></p>

            </div>
        </div>
        <div id="showImg" width="60px" height="60px" style="border-radius: 15px;text-align: center;"></div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo" id="bt1">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary" id="bt2">重置</button>
            </div>
        </div>

    </form>
</div>
<!--添加或者修改的弹出层end-->