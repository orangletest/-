$().ready(function () {//准备函数

    //on绑定可以对动态生成的做绑定，因为绑定给了父框，点击时会判断是不是子框
    $("#tb").on("click","input[value='修改']",function () {
        //alert(1);
        $("#empPhoneWarning").html("");
        $("#saveBtn").prop("disabled",false);
        //加载部门
        loadDep();

        var empId=this.id;
        $("#empId").val(empId);
        $.ajax({
            url:"/jsp004/emp/selectEmpById",
            data:{empId:empId},
            dataType:"json",
            type:"post",
            success:function (returnData) {
                $("#empId").val(returnData.data.emp_id);
                $("#empName").val(returnData.data.emp_name);
                $("#empPhone").val(returnData.data.emp_phone);
                $("#empSal").val(returnData.data.emp_sal);
                $("#empHiredate").val(returnData.data.emphiredate);
                $("#empSex").val(returnData.data.emp_sex);
                $("#dep").val(returnData.data.emp_dep);
                $("#empAddress").val(returnData.data.emp_address);
                $("#empDist").val(returnData.data.emp_dist);
                $("#shop").val(returnData.data.emp_shop);
                $("#myModal").modal('show');
            }
        });
    });


    $("#tb").on("click","input[value='启用']",function () {
        var empId=this.id;
        $("#empId").val(empId);
        $.ajax({
            url:"/jsp004/emp/updateState",
            data:{empId:empId,empState:0},
            dataType:"json",
            type:"post",
            success:function (returnData) {
                loadInfo(1);
            }
        });
    })
    $("#tb").on("click","input[value='禁用']",function () {
        var empId=this.id;
        $("#empId").val(empId);
        $.ajax({
            url:"/jsp004/emp/updateState",
            data:{empId:empId,empState:1},
            dataType:"json",
            type:"post",
            success:function (returnData) {
                loadInfo(1);
            }
        });
    })

    //分配账号
    $("#tb").on("click","input[value='分配账号']",function () {
        var empId=this.id;
        $("#usernameWarning").html("");
        $("#accountForm")[0].reset();
        $("#a_empId").val(empId);
        $("#account_Modal").modal("show");
    });

    $("#accountBtn").click(function () {
        $.ajax({
            url:"/jsp004/user/fpAccount",
            data:$("#accountForm").serialize(),
            dataType:"json",
            type:"get",
            success:function (Data) {
                if (Data.state==true){
                    $("#account_Modal").modal("hide");
                    loadInfo(1);
                }else {
                    alert(Data.message);
                }
            }
        })
    })

    //分配角色
    $("#tb").on("click","input[value='分配角色']",function () {
        var accountId=this.id;
        $("#accountId").val(accountId);
        $.ajax({
            url:"/jsp004/user/selectAccountRoles",
            data:{accountId:accountId},
            dataType:"json",
            type:"get",
            success:function (Data) {
                $("#roleDiv").html("");
                console.log("分配角色"+Data[0]);
                for (let i = 0; i < Data.length; i++) {
                    var obj=$("<input type='checkbox' name='roles' value='"+Data[i].role_id+"'/>");
                    if (Data[i].account_id!=null&&Data[i].account_id!=""){
                        obj.prop("checked",true);//如果accountid存在，那么该角色已经被选中  复选框状态
                    }
                    //复选框添加到div
                    $("#roleDiv").append(obj);
                    //复选框名字添加到div
                    $("#roleDiv").append(Data[i].role_name);
                }
                //显示模态框
                $("#roleModal").modal("show");
            }
        })
    })

    $("#roleBtn").click(function () {
        $.ajax({
            url:"/jsp004/user/fenPeiRole",
            data:$("#roleForm").serialize(),
            dataType:"json",
            type:"post",
            success:function (Data) {
                if (Data.state){
                    loadInfo(1);
                    $("#roleModal").modal("hide");
                }else {
                    alert(Data.message);
                }
            }
        })
    })


    //加载部门信息
    function loadDep(){
        $.ajax({
            url:"/jsp004/emp/selectDepName",
            data:{},
            dataType:"json",
            type:"post",
            async:false,
            success:function (Data) {
                $("#dep").html("");
                $("#dep").append("<option >请选择部门</option>");
                //console.log("部门信息："+Data.data[0]);
                for (let i = 0; i < Data.data.length; i++) {
                    $("#dep").append("<option value="+Data.data[i].dep_id+">"+Data.data[i].dep_name+"</option>")
                }

            }
        })
    }

    //新增员工
    $("#addBtn").click(function () {
        $("#empForm")[0].reset();//表单重置，因为修改后或者之前录入的内容都在，需要清空
        //加载部门
        loadDep();
        $("#myModal").modal('show');
    })

    $("#saveBtn").click(function () {
        $.ajax({
            url:"/jsp004/emp/insertOrUpdateEmp",
            data:$("#empForm").serialize(),
            dataType:"json",
            type:"post",
            success:function (returnData) {
                if(returnData.state==false){
                    alert(returnData.message);
                }else{
                    $("#myModal").modal('hide');
                    loadInfo(1);
                }
            }
        });
    });

    //上一页
    $("#prevBtn").click(function () {
        var pageNow=Number($("#pageNow").html());
        if(pageNow<=1){
            pageNow=1;
        }else{
            pageNow=pageNow-1;
        }
        loadInfo(pageNow);
    })
    //下一页：如果当前是尾页就不动了
    $("#nextBtn").click(function () {
        var pageNow=Number($("#pageNow").html());
        var allPages=Number($("#allPages").html());
        if(pageNow>=allPages){
            pageNow=allPages;
        }else{
            pageNow=pageNow+1;
        }
        loadInfo(pageNow);
    })


    $("#searchBtn").click(function () {
        loadInfo(1);
    })

})