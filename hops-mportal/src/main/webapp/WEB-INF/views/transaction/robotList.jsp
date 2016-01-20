<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>商户实体对象列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form id="listForm" method="get" action="${ctx}/product/productList">
<div class="line_bar">
	<input id='page' type='hidden' name='page'/> 
	<label>商户名称:</label>
	<input type="text" id="merchantName" name="merchantName"/>
	<input type="button" class="button" value="查询" onclick="query()"/>
	<input type="button" class="button" value="创建商户实体" onclick="toSaveRobot()"/>
</div>
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><a class=sort href="javascript:;" name=identityName>主键ID</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>商户ID</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>商户名称</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>classPath</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>操作</a> </th>
		</tr>
		<c:forEach items="${mlist}" var="merchantRobot">
			<tr>
				<td>${merchantRobot.id}</td>
				<td>${merchantRobot.merchantId}</td>
				<td>${merchantRobot.merchantName}</td>
				<td>${merchantRobot.classPath}</td>
				<td>
					<a onclick="doDeleteRobot(${merchantRobot.id})">[删除]</a>
					<a onclick="toUpdateRobot(${merchantRobot.id})">[修改]</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div id="pager" style="float:right;"></div>  
  	<br/>
 	<script type="text/javascript" language="javascript">
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
			});
			PageClick = function(pageclickednumber) { 
			$("#pager").pager({ pagenumber: pageclickednumber, pagecount: ${pagetotal}, buttonClickCallback: PageClick 
			}); 
			$("#page").val(pageclickednumber);
			$("#listForm").submit();
			}
			
		function toSaveRobot(){
			window.location.href="${ctx}/transaction/toSaveRobot";
		}
		
		function toUpdateRobot(id){
			window.location.href="${ctx}/transaction/toUpdateRobot?id="+id;
		}
		
		function doDeleteRobot(id){
			window.location.href="${ctx}/transaction/doDeleteRobot?id="+id;
		}
		
		function query(){
			var merchantName=$("#merchantName").val();
			window.location.href="${ctx}/transaction/robotList?merchantName="+merchantName;
		}
		
	</script> 
</form>
</body>
</html>

