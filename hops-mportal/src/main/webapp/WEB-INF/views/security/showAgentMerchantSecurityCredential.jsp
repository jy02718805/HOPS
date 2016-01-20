<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>密钥属性列表 </title>
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
<DIV class=path>
		<A href="#">首页</A> » 代理商密钥 <SPAN>
</DIV>
<form id="inputForm" action="${ctx}/security/securityPropertyList">
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><a class=sort href="javascript:;" name=id>ID</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyName>密钥属性名称</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyValue>密钥</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>操作</a> </th>
		</tr>
		<c:forEach items="${securityCredentials}" var="SecurityCredential">
			<tr>
				<td>${SecurityCredential.id}</td>
				<td>${SecurityCredential.securityProperty.securityPropertyName}</td>
				<td>${SecurityCredential.securityPropertyValue}</td>
				<td>
					<a href="${ctx}/security/updateSecurityCredential?merchantId=${merchantId}&id=${SecurityCredential.id}">[更新]</a>
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
			$("#inputForm").submit();
			}
		
		function toSaveSecurityProperty(){
			window.location.href="${ctx}/security/refreshSaveSecurityProperty";
		}
		
	</script> 
</form>
</body>
</html>

