<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>商户产品列表 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctx}/template/admin/css/manage.css" type="text/css">
<link rel="stylesheet" href="${ctx}/template/admin/css/zTreeStyle-2014_11_7.css" type="text/css">
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.ztree.excheck-3.5.js"></script>

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
			data: {
				key: {
					title:"t"
				},
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};

		var zNodes =[
			${tree}
		];

		var log, className = "dark";
		function beforeClick(treeId, treeNode, clickFlag) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.name );
			return (treeNode.click != false);
		}
		function onClick(event, treeId, treeNode, clickFlag) {
			window.location.href="${ctx}/product/toEditProduct?productId="+treeNode.id;
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

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
	</SCRIPT>
</HEAD>

<BODY style="background:#fff;">
	<div class="treewrap fl">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div style="float:right;clear: both;" class='bar'>
	<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()" />
	</div>
</BODY>
</HTML>