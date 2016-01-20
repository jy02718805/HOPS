<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>账户类型列表 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<html>
<head>
	<title>用户管理</title>
</head>

<body>
<!--  	&nbsp;<input type="button" class="button"  value="创建账户类型" onclick="addAccountType()"/> -->
	
<div class="mg10"></div>
<table id="contentTable" class=list>
		<tbody>
			<tr>
				<th><SPAN>序号</SPAN> </th>
				<th><SPAN>账户类型名称</SPAN> </th>
				<th><SPAN>账户支持类型</SPAN> </th>
				<th><SPAN>账户作用范围</SPAN> </th>
				<th><SPAN>账户方向</SPAN> </th>
				<th><SPAN>账户支持种类</SPAN> </th>
				<th><SPAN>账户支持币种</SPAN> </th>
				<th><SPAN>账户支持会员类型</SPAN> </th>
			</tr>
			
			<c:choose>
				<c:when test="${fn:length(accountTypeList) > 0}">
					<c:forEach items="${accountTypeList}" var="accountType" varStatus="status">
						<tr>
							<td>
								${status.index+1}
							</td>
							<td>${accountType.accountTypeName}</a></td>
							<td>${accountType.type}</td>
							<td><c:choose>
								   <c:when test="${accountType.scope=='normol'}">
							             	 一般
							       </c:when>
							       <c:otherwise>
							              	结算
							       </c:otherwise>					
							</c:choose></td>
							<td><c:choose>
								   <c:when test="${accountType.directory=='CREDIT'}">
							             	 贷记
							       </c:when>
							       <c:otherwise>
							              	借记
							       </c:otherwise>					
							</c:choose></td>
							<td><c:choose>
								   <c:when test="${accountType.typeModel=='FUNDS'}">
							             	 资金
							       </c:when>
							       <c:otherwise>
							              	实体卡
							       </c:otherwise>					
							</c:choose></td>
							<td>${accountType.ccy}</td>
							<td>${accountType.identityType}</td>
						</tr>
					</c:forEach>
				</c:when>
			<c:otherwise>
					<tr>
						<td colspan="8">没数据</td>
					</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
</body>
<script type="text/javascript" language="javascript">
		function addAccountType(){
			window.location.href="${ctx}/accountType/addAccountType";
		}
</script>
</html>
