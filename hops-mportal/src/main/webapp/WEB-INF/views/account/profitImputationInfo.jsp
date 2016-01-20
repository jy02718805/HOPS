<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>归集详情</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
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
<form id="inputForm" method="post" action="">
<div class="mg10"></div>
	<table class="input">
	<tr>
				<th>
					归集时间:
				</th>
				<td>
				<fmt:formatDate value="${profitImputationVo.imputationBeginDate}" type="date" dateStyle="medium" />
				
				</td>
				
			</tr>
		<tr>
				<th>
					商户名称:
				</th>
				<td>
				${profitImputationVo.merchantName}
				</td>
				
			</tr>
			<tr>
				<th>
					账户类型:
				</th>
				<td>
				${profitImputationVo.profitAccountType.accountTypeName}
				</td>
				
			</tr>
			<tr>
				<th>
					归集利润(元):
				</th>
				<td>
				${profitImputationVo.imputationProfit}	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" class="button"/>
				</td>
			</tr>
		</table>
</form>
</body>
</html>

