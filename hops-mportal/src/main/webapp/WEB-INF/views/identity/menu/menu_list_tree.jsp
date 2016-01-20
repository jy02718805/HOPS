<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>菜单列表 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctx}/template/admin/css/manage.css" type="text/css">
<link rel="stylesheet" href="${ctx}/template/admin/css/zTreeStyle-2014_11_7.css" type="text/css">
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.popupSmallMenu.js"></script>
<script src="${ctx}/template/admin/js/ArtDialog/artDialog.js?skin=chrome" type="text/javascript"></script>
<script src="${ctx}/template/admin/js/ArtDialog/plugins/iframeTools.js" type="text/javascript"></script>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
<style>
.small-menu {
    position: absolute;
	width: 120px;
	z-index: 99999;
	border: solid 1px #CCC;
	background: #EEE;
	padding: 0px;
	margin: 0px;
	display: none;
}

.small-menu li {
   list-style: none;
	padding: 0px;
	margin: 0px;
}
.small-menu li A {
	color: #333;
	text-decoration: none;
	display: block;
	line-height: 20px;
	height: 20px;
	background-position: 6px center;
	background-repeat: no-repeat;
	outline: none;
	padding: 1px 5px;
	padding-left: 28px;
}

.small-menu li a:hover {
	color: #FFF;
	background-color: #3399FF;
}

.small-menu-separator {
    padding-bottom:0;
    border-bottom: 1px solid #DDD;
}
.small-menu LI.addChildren A { background-image: url(/hops-mportal/template/common/ztree/page_white_paste.png); }
.small-menu LI.add A { background-image: url(/hops-mportal/template/common/ztree/page_white_copy.png); }
.small-menu LI.edit A { background-image: url(/hops-mportal/template/common/ztree/page_white_edit.png); }
.small-menu LI.cut A { background-image: url(/hops-mportal/template/common/ztree/cut.png); }
.small-menu LI.copy A { background-image: url(/hops-mportal/template/common/ztree/page_white_copy.png); }
.small-menu LI.paste A { background-image: url(/hops-mportal/template/common/ztree/page_white_paste.png); }
.small-menu LI.delete A { background-image: url(/hops-mportal/template/common/ztree/page_white_delete.png); }
.small-menu LI.quit A { background-image: url(/hops-mportal/template/common/ztree/door.png); }
</style>


	<SCRIPT type="text/javascript">
	var setting = {
			check: {
				enable: true
			},
			data: {
				key: {
					checked: "isChecked",
					title:"title",
					name:"name"
				},
				simpleData: {
					enable: true,
					idKey :"key",
					pIdKey:"pkey"
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick,
				onNodeCreated:OnNodeCreated,
				onRightClick:OnRightClick

			}
		};


		var zNodes =[${tree}];
		//////alert("tree="+'${tree}');
		var log, className = "dark";
		function beforeClick(treeId, treeNode, clickFlag) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.nnnn );
			return (treeNode.click != false);
		}
		function onClick(event, treeId, treeNode, clickFlag) {
			////showLog("[ "+getTime()+" onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
		}		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds();
			return (h+":"+m+":"+s);
		}
		
		var ztree;
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			ztree = $.fn.zTree.getZTreeObj("treeDemo");
		});

		function OnRightClick(event, treeId, treeNode) {
			ztree.selectNode(treeNode);
			if(treeNode) {
				//console.log(treeNode.tId);
				//console.dir($('#'+treeNode.tId));

				$("#menu").popupSmallMenu({
					event : event,
					onClickItem  : function(item) {
						chuli(treeNode,item);
					}
				});
			}	 
		}

		//右键菜单触发
		function chuli(treeNode,item){
			//通过simpleData中定义参数得到值
			///alert("menu_list_tree.jsp----chuli---item="+item+" ; id="+treeNode.key+" ; pid="+treeNode.pkey);
			//添加[子级]=addChildren
			if(item=='addChildren'){

				var html = $.ajax({ 
					url: "add?id="+treeNode.key, 
					async : false,
					cache: false }).responseText;
                var title = "添加 - " + treeNode.name + "子菜单";
                art.dialog({
                    content: html,
                    title: title,
                    lock: true,
                    ok: false
                });
				 
				/* window.location.replace("add?id="+treeNode.key); */
			}
			//添加[同级]=add
			else if(item=='add'){
				var html = $.ajax({ 
					url: "${ctx}/Menu/add?id="+treeNode.pkey, 
					async : false,
					cache: false}).responseText;
                var title = "添加 - " + treeNode.name + "的同级菜单";
                art.dialog({
                    content: html,
                    title: title,
                    lock: true,
                    ok: false
                });
				
				/* window.location.replace("add?parentMenuId="+treeNode.pkey); */
			}
			//编辑=edit
			else if(item=='edit'){
				var html = $.ajax({ 
					url: "edit?id="+treeNode.key,
					async : false,
					cache: false}).responseText;
                var title = "编辑 - " + treeNode.name + "菜单";
                art.dialog({
                    content: html,
                    title: title,
                    lock: true,
                    ok: false
                });
				
				/* window.location.replace("edit?id="+treeNode.key); */
			}
			//删除=cut
			else if(item=='cut'){
				if(confirm("准备删除:["+treeNode.name+"]?")){
					window.location.replace("deletemenuh?id="+treeNode.key);
				}
			}
			else alert("操作错误，请重新选择~~！");
		}
		
		function OnNodeCreated(event, treeId, treeNode) {
			
		}
		var jmz = {};
		jmz.GetLength = function(str) {
		    var realLength = 0, len = str.length, charCode = -1;
		    for (var i = 0; i < len; i++) {
		        charCode = str.charCodeAt(i);
		        if (charCode >= 0 && charCode <= 128) realLength += 1;
		        else realLength += 2;
		    }
		    return realLength;
		};
		function addMenu() {
			var menuName=$("#menuName").val();
			if(menuName == null ||menuName==""){
				alert("请选输入菜单名称！");
				return false;
			}
			if(jmz.GetLength(menuName) > 20){
				   alert("菜单名称长度不能超过20位字符！"); 
				   return false;
			}
			var portaltype=document.getElementById("portaltype").options[document.getElementById("portaltype").selectedIndex].value;
			if(portaltype==null||portaltype==""){
				alert("请选择后台类型！");
				return false;
			}
			var displayOrder=$("#displayOrder").val();
			if(displayOrder==null||displayOrder==""){
				   alert("显示顺序有误，请输入整数！"); 
				   return false;
			}else{
				if(displayOrder.search("^-?\\d+$") != 0 ){
					alert("显示顺序有误，请输入整数！"); 
					return false;
				}
			}
			var pageResourceId=document.getElementById("pageResource.resourceId").options[document.getElementById("pageResource.resourceId").selectedIndex].value;
			if(pageResourceId==null||pageResourceId==""){
				alert("请选择页面资源！");
				return false;
			}
			var status=$("#status").val();
			if(status==null||status==""){
				alert("请选择状态！");
				return false;
			}
			$('#inputForm').submit();
		}
		
		function editMenu() {
			var menuName=$("#menuName").val();
			if(menuName == null ||menuName==""){
				alert("请选输入菜单名称！");
				return false;
			}
			if(jmz.GetLength(menuName) > 20){
				   alert("菜单名称长度不能超过20位字符！"); 
				   return false;
			}
			var portaltype=document.getElementById("portaltype").options[document.getElementById("portaltype").selectedIndex].value;
			if(portaltype==null||portaltype==""){
				alert("请选择后台类型！");
				return false;
			}
			var displayOrder=$("#displayOrder").val();
			if(displayOrder==null||displayOrder==""){
				   alert("显示顺序有误，请输入整数！"); 
				   return false;
			}else{
				if(displayOrder.search("^-?\\d+$") != 0 ){
					alert("显示顺序有误，请输入整数！"); 
					return false;
				}
			}
			var pageResourceId=document.getElementById("pageResource.resourceId").options[document.getElementById("pageResource.resourceId").selectedIndex].value;
			if(pageResourceId==null||pageResourceId==""){
				alert("请选择页面资源！");
				return false;
			}
			var status=$("#status").val();
			if(status==null||status==""){
				alert("请选择状态！");
				return false;
			}
			$('#inputForm').submit();
		}
		
	</SCRIPT>
</HEAD>

<BODY style="background:#fff;">
<div class="treewrap fl">
        <ul id="treeDemo" class="ztree"></ul>
<div>
 	<ul id="menu" class="small-menu">
 		<shiro:hasPermission name="menu:add_childmenu">
 		<li class="addChildren">
			<a href="#">添加[子级]</a>
		</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="menu:add_menu">
		<li class="add">
			<a href="#">添加[同级]</a>
		</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="menu:edit_menu">
		<li class="edit">
			<a href="#">编辑</a>
		</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="menu:del_menu">
		<li class="cut">
			<a href="#">删除</a>
		</li>
		</shiro:hasPermission>
	</ul>
    
</div>
</div>

</BODY>
</HTML>
