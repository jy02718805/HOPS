<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>实体卡列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
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
<form id="listForm" method="get" action="list">
	<input id="orderStatus" type="hidden" name="orderStatus"> 
	<input id="paymentStatus" type="hidden" name="paymentStatus"> 
	<input id="shippingStatus" type="hidden" name="shippingStatus"> 
	<input id="page" type="hidden" name="page"> 
	<input id="hasExpired" type="hidden" name="hasExpired"> 
	<table id="listTable" class="list">
		<tr>
			<td>账户ID</td>
			<td>账户状态</td>
			<td>张数</td>
			<td>价值</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${mlist}" var="cardAccount">
			<tr>
				<td>${cardAccount.accountId}</td>
				<td>${cardAccount.status}</td>
				<td>${cardAccount.cardNum}</td>
				<td>${cardAccount.balance}</td>
				<td>7788</td>
			</tr>
		</c:forEach>
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
	</script> 
</form>
</body>
</html>

