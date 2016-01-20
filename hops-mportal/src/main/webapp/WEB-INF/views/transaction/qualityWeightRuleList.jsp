<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>质量权重列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<style>
#pager ul.pages { 
display:block; 
border:none; 
text-transform:uppercase; 
font-size:10px; 
margin:10px 0 50px; 
padding:0; 
} 
#pager ul.pages li { 
list-style:none; 
float:left; 
border:1px solid #ccc; 
text-decoration:none; 
margin:0 5px 0 0; 
padding:5px; 
} 
#pager ul.pages li:hover { 
border:1px solid #003f7e; 
} 
#pager ul.pages li.pgEmpty { 
border:1px solid #eee; 
color:#eee; 
} 
#pager ul.pages li.pgCurrent { 
border:1px solid #003f7e; 
color:#000; 
font-weight:700; 
background-color:#eee; 
}
</style>
<body>
<div class="mg10"></div>
<form id="listForm" method="get" action="${ctx}/qualityWeightRule/qualityWeightRuleList">
	<input id=page type=hidden name=page> 
	
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><a class=sort href="javascript:;" name=identityName>速度比重</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>成功率比重</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>操作</a> </th>
		</tr>
		<tr>
			<td>${qwr.speedWeight}</td>
			<td>${qwr.successWeight}</td>
			<td>
			<shiro:hasPermission name="qualityWeightRule:edit_show">
				<a onclick="toUpdateQualityWeightRule()">[修改]</a>
			</shiro:hasPermission>
			</td>
		</tr>
		</tbody>
	</table>
	<div id="pager" style="float:right;"></div>  
  	<br/>
 	<script type="text/javascript" language="javascript">
		function toUpdateQualityWeightRule(){
			window.location.href="${ctx}/transaction/toUpdateQualityWeightRule";
		}
	</script>
</form>
</body>
</html>

