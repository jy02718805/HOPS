<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>实体卡列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<form id="listForm" method="get" action="${ctx}/account/listCurrencyAccount">
	<input id=orderStatus type=hidden name=orderStatus> 
	<input id=paymentStatus type=hidden name=paymentStatus> 
	<input id=shippingStatus type=hidden name=shippingStatus> 
	<input id=page type=hidden name=page> 
	<input id=hasExpired type=hidden name=hasExpired> 
	<table id=listTable class=list>
		<tbody>
			<tr>
				<th><SPAN>序号</SPAN> </th>
				<th><SPAN>账户ID</SPAN> </th>
				<th><SPAN>账户状态</SPAN> </th>
				<th><SPAN>余额详情</SPAN> </th>
				<th><SPAN>操作</SPAN> </th>
			</tr>
			<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
			
			<c:forEach items="${mlist}" var="currencyAccount" varStatus="count">
						<tr>
							<td>${(page-1)*pageSize+count.index+1}</td>
					<td>${currencyAccount.accountId}</td>
					<td>${currencyAccount.status}</td>
					<td>
					IS: ${currencyAccount.availableBalance}<br/>
					US: ${currencyAccount.unavailableBanlance}<br/>
					CS: ${currencyAccount.creditableBanlance}</td>
					<td>
						<a href="${ctx}/account/toCurrencyAccountDebit?accountId=${currencyAccount.accountId}&accountTypeId=${(currencyAccount.accountType.accountTypeId)!""}">[加款]</a>
					   |<a href="${ctx}/account/toCurrencyAccountCredit?accountId=${currencyAccount.accountId}&accountTypeId=${(currencyAccount.accountType.accountTypeId)!""}">[减款]</a>
					</td>
				</tr>
			</c:forEach>
			</c:when>
			<c:otherwise>
					<tr>
						<td colspan="5">没数据</td>
					</tr>
			</c:otherwise>
		</c:choose>
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
	</script> 
</form>
</body>
</html>

