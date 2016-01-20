<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"></head>
<title>菜单列表 </title>
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


</HEAD>

	<BODY bgColor=#487dd9 scroll=no style="MARGIN: 0px">
			<input type="hidden" name="id" id="id" value="${id }">
			<input type="hidden" name="pid" id="pid" value="${pid }">
			<input type="button" style="float:left;" class="button" value="添&nbsp;&nbsp;加&nbsp;&nbsp;子&nbsp;&nbsp;级" onclick="location.href='add?id=${id }'" />
			<input type="button" style="float:left;" class="button" value="添&nbsp;&nbsp;加&nbsp;&nbsp;同&nbsp;&nbsp;级" onclick="location.href='add?parentMenuId=${pid }'" />
			<input type="button" style="float:left;" class="button" value="修&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;改" onclick="location.href='edit?id=${id }'" />
			<input type="button" style="float:left;" class="button" value="删&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除" onclick="location.href='deletemenuh?id=${id }'" />
		
	</BODY>

</HTML>