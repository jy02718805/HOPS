//***系统账号管理***//

//删除用户
function deleteUser(obj,userName) {
    var tr = $(obj).parent().parent();
    var color = tr.css("background-color");
    tr.css("background-color", "#ffff80");
    if (!confirm("你确认要删除该用户吗?")) {
        tr.css("background-color", color);
        return false;
    }
    $.ajax({
        type: "post",
        data: { userName: userName },
        url: "/UserAjax/DeleteUser",
        success: function (data) {
            judgeLoginStatus(data);
            if (data.toLowerCase() == "true") {
                alert('用户删除成功');
                $.paging.submit();
                return true;
            }
            else {
                alert("操作失败，请重试");
            }
        },
        error: function () {
            tr.css("background-color", color);
            alert("操作失败，请重试");
        }
    });
}

//更新用户信息
function updateUserInfo(obj, userName) {
    var email = $(obj).parent().parent().find("input[name='Email']").val();
    if (email != "" && !$.isEmail(email)) {
        alert('请输入正确的邮箱地址');
        return false;
    }
    if (!confirm("你确认要更新该用户吗?")) {
       
        return false;
    }
    var data = $(obj).parent().parent().serialize();
    $.ajax({
        type: "post",
        data: data,
        url: "/UserAjax/UpdateUserInfo",
        success: function (data) {
            judgeLoginStatus(data);
            if (data.toLowerCase() == "true") {
                alert('用户信息更新成功');
                $.paging.submit();
                return true;
            }
            else {
                alert("操作失败，请重试");
            }
        },
        error: function () {
            alert("操作失败，请重试");
        }
    });
}

/*********** 权限管理页面 ***********/

//更新权限项
function updateFunction() {
    var data = $("#mainForm").serialize();
    $.ajax({
        type: "post",
        data: data,
        url: "/UserAjax/UpdateSysFunction",
        success: function (data) {
            judgeLoginStatus(data);
            if (data.toLowerCase() == "true") {
                alert('权限编辑成功');
                window.location = "/usercenter/functionmanage";
                return true;
            }
            else {
                alert("操作失败，请重试");
            }
        },
        error: function () {
            alert("操作失败，请重试");
        }
    });
}

//添加一个新权限
function createFunction() {
    var data = $("#mainForm").serialize();
    $.ajax({
        type: "post",
        data: data,
        url: "/UserAjax/CreateSysFunction",
        success: function (data) {
            judgeLoginStatus(data);
            if (data == "repeat") {
                alert("添加失败，该权限编号已经存在");
                return;
            }
            if (data.toLowerCase() == "true") {
                alert('权限添加成功');
                window.location = "/usercenter/functionmanage";
                return true;
            }
            else {
                alert("操作失败，请重试");
            }
        },
        error: function () {
            alert("操作失败，请重试");
        }
    });
}

/*********** 权限管理页面的window.load事件 **********/
function initFunctionManage() {
    $("span.hand").click(function () { //点击节点（收起,隐藏）
        $(this).next('ul').toggle();
        if ($(this).next().is(":hidden")) {
            $(this).removeClass("span_up").addClass("span_down");
        }
        else {
            $(this).removeClass("span_down").addClass("span_up");
        }
    }).contextMenu('funcMenu', { //右键菜单
        itemStyle: { //项样式
            fontFamily: 'Microsoft YaHei',
            backgroundColor: '#FFFFFF',
            color: '#000000',
            paddingLeft: '25px',
            cursor: 'pointer',
            fontWeight: 'bold',
            fontSize: '12px'
        },
        itemHoverStyle: { //经过样式
            color: '#055586',
            backgroundColor: '#FFFFFF',
            border: '1px solid #FFFFFF',
            paddingLeft: '28px'
        },
        onShowMenu: function (e, menu) { //显示菜单之前执行的事件
            if ($(e.target).attr('level') == 'top') {
                $('#addSibling', menu).remove();
            }
            if ($(e.target).attr('level') == 'end') {
                $('#addChild', menu).remove();
            }
            return menu;
        },
        bindings: { //每项菜单的点击事件
            'addSibling': function (t) {
                var html = $.ajax({ url: "add?id="+$(t).attr('parentId'), async: false }).responseText;
                var title = "添加 - " + $(t).text() + "的同级节点";
                art.dialog({
                    content: html,
                    title: title,
                    lock: true,
                    ok: function (data) {
                        return false;
                    }
                });
            },
            'addChild': function (t) {
                var html = $.ajax({ url: "add?id="+$(t).attr('funcId'), async: false }).responseText;
                var title = "添加 - " + $(t).text() + "的子节点";
                art.dialog({
                    content: html,
                    title: title,
                    lock: true,
                    ok: function () {
                        return false;
                    }
                });
            },
            'edit': function (t) {
                var html = $.ajax({ url: "edit?id="+$(t).attr('funcId'), async: false }).responseText;
                var title = "编辑 - " + $(t).text();
                art.dialog({
                    content: html,
                    title: title,
                    lock: true,
                    ok: function () {
                        return false;
                    }
                });
            },
            'delete': function (t) {
                art.dialog.confirm('你确定要删除权限<strong>' + $(t).text() + '</strong>吗？', function () {
                    $.ajax({
                        type: "post",
                        url: "delete?id="+$(t).attr("funcId"),
                        success: function (data) {
                        	if(data=="true"){
	                            judgeLoginStatus(data);
	                            window.location = "list";
	                            return true;
                        	}else if(data=="child"){
                        		alert("删除失败，原因：该权限含有子权限，必须先删除所有子权限。");
                        	}else{
                                alert("操作失败，请重试");
                        	}
                        },
                        error: function () {
                            alert("操作失败，请重试");
                        }
                    });
                }, function () {
                    art.dialog.tips('你撤销了删除操作');
                });

            },
            'close': function (t) { }
        }
    });
    //没有下一级子权限时，隐藏ul空标签
    $(".treeL1,.treeL2,.treeL3,.treeL4").each(function () {
        if ($(this).children("li").length <= 0) {
            $(this).prev("span").removeClass("span_down").addClass("span_up");
            $(this).remove();
        }
    });
    //第三层级和第四层级，默认隐藏
    $(".treeL3,.treeL4").css("display", "none");
    //灰色[表示禁用的功能项]
    $("span[enabled='1']").css("color", "#CCCCCC");
}

//++++++++++++++++++ 公共方法 +++++++++++++++++++//

//判断用户登录状态
function judgeLoginStatus(data) {
    if (data.toLowerCase() == "nologin") {
        alert("您的登录已超时，请重新登录");
        return false;
    }
}