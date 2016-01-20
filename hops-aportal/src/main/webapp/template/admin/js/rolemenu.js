
//菜单js
$(function () {
	
    $("span.hand").click(function () { //点击节点（收起,隐藏）
        $(this).next('ul').toggle();
    });
    
    //如果大菜单下有选择的页面，则选中
    $("input[name='menuIds']").each(function () {
        if ($(this).parent("li").find("input:checked[name='pageIds']").length > 0) {
            $(this).attr("checked", true);
        }
    });

    //顶级选择项
    $("input[name='chkTop']").click(function () {
        var checkedValue = this.checked;
        $(this).parent("li").find("input[name='functionIds']").attr("checked", checkedValue);
        $(this).parent("li").find("input[name='menuIds']").attr("checked", checkedValue);
        $(this).parent("li").find("input[name='pageIds']").attr("checked", checkedValue);
    });
    //大菜单选择
    $("input[name='menuIds']").click(function () {
        var checkedValue = this.checked;
        $(this).parent("li").find("input[name='functionIds']").attr("checked", checkedValue);
        $(this).parent("li").find("input[name='pageIds']").attr("checked", checkedValue);
    });
    //页面page选择
    $("input[name='pageIds']").each(function () {
        $(this).click(function () {
            //如果选中则父级也选中
            if ($(this).attr("checked") == true) {
                var lastLevel = $(this).parents("ul").parents("li").find("input[name='menuIds']").eq(0);
                $(lastLevel).attr("checked", true);
                $(this).parent("li").find("input[name='functionIds']").attr("checked", true);
            }
            else {  //如果所有功能都取消，父亲节点也取消
                $(this).parent("li").find("input[name='functionIds']").attr("checked", false);
                var hasPerSet = false;
                $(this).parents("ul").eq(0).find("input[name='pageIds']").each(function () {
                    if ($(this).attr("checked") == true) {
                        hasPerSet = true;
                    }
                });
                if (!hasPerSet && !$(this).parent("li").hasClass("last")) {
                    $(this).parents("ul").parents("li").find("input[name='menuIds']").eq(0).attr("checked", false);
                }
            }
        });
    });

    //功能项选择
    $("input[name='functionIds']").each(function () {
        $(this).click(function () {
            //如果选中则父级也选中
            if ($(this).attr("checked") == true) {
                var lastLevel = $(this).parents("ul").parents("li").find("input[name='menuIds']").eq(0);
                $(lastLevel).attr("checked", true);
                $(this).parents("ul").parents("li").find("input[name='pageIds']").eq(0).attr("checked", true);
            }
            else {  //如果所有功能都取消，父亲节点也取消
                //                    var hasPerSet = false;
                //                    $(this).parents("ul").eq(0).find("input[name='menuIds']").each(function () {
                //                        if ($(this).attr("checked") == true) {
                //                            hasPerSet = true;
                //                        }
                //                    });
                //                    if (!hasPerSet && !$(this).parent("li").hasClass("last")) {
                //                        //debugger;
                //                        //$(this).parents(".collapsable").find("input[name='functionIds']").eq(0).attr("checked", false);
                //                        $(this).parents("ul").parents("li").find("input[name='menuIds']").eq(0).attr("checked", false);
                //                        $(this).parents("ul").parents("li").find("input[name='pageIds']").eq(0).attr("checked", false);
                //                    }
            }
        });
    });
    //标题下面没有内容时隐藏标签
    $(".treeL1,.treeL2,.treeL3,.treeL4").each(function () {
        if ($(this).children("li").length <= 0) $(this).remove();
    });
    $(".treeL3").css("display", "none");
});