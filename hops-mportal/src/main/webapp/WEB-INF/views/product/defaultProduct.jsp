<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>商户产品列表 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctx}/template/admin/css/common.css" type="text/css">
<link rel="stylesheet" href="${ctx}/template/common/css/demo.css" type="text/css">
<link rel="stylesheet" href="${ctx}/template/admin/css/zTreeStyle-2014_11_7.css" type="text/css">
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

		var zNodes =[
			${tree}
		];
		
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = { "Y" : "", "N" : "" };
			showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		function submit(){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var changeNodes = "";
			var nodes = treeObj.getChangeCheckedNodes(true);
			for(var i=0;i<nodes.length;i++){ 
				var node =nodes[i];
				changeNodes = changeNodes + "|" + node.id;
			}
			$("#changeNodes").val(changeNodes);
			window.location.href="${ctx}/product/updateDefValueByIds?merchantId="+${merchantId}+"&changeNodes="+changeNodes;
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
		<!--//-->
	</SCRIPT>
</HEAD>

<BODY>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</div>
<div class="line_bar">
	<input type="hidden" name="merchantId" id="merchantId" value="${merchantId}" />
	<input type="hidden" name="changeNodes" id="changeNodes" />
	<div style="float:right;padding:10px;">
	<input type="button" class="button" value="提交" onclick="submit()" />
	<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
</div>
</div>
</BODY>
</HTML>