<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"></head>
<title>菜单分配 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctx}/template/admin/css/zTreeStyle-2014_11_7.css" type="text/css">
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.ztree.excheck-3.5.js"></script>
	<SCRIPT type="text/javascript">
	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};


		var zNodes =[${tree}];

		var code;

		function setCheck() {
			showCode('setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
		});


	</SCRIPT>
</HEAD>

<BODY>
<form id="inputForm" method="post" action="role_addupdatetreemenu">
<input type="hidden" name="id" id="id" value="${id}" />
<input type="hidden" name="menuidstr" id="menuidstr" value="" />

<div class="line_bar" >
<div class="content_wrap">
	<div class="zTreeDemoBackground left" >
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</div>
</div>
<div class="line_bar" >
        <input type="button" style="float:right;" class="button" value="返回" onclick="location.href='list'" />
        <input type="button" style="float:right;" class="button" value="确定" onclick="addmenu()"/>
        <!-- <input type="button" style="float:left;" class="button" value="test" onclick="selectcheckbox()" /> -->
</div>
</form>
</BODY>
</HTML>
<SCRIPT type="text/javascript">

function addmenu(){
	var Menuid=selectcheckbox();

	if(Menuid==null||Menuid.trim()=="")
	{
		alert("请选择要分配的菜单！");
		return;
	}
	$('#inputForm').submit();
}

function selectcheckbox(){

	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
    var Menuid="";
    for (var i = 0; i < nodes.length; i++) {
        if(Menuid.length==0)Menuid+=nodes[i].id;
        else Menuid+="|"+nodes[i].id;
    }
    $("#menuidstr").val(Menuid);
    return Menuid;
}
</SCRIPT>