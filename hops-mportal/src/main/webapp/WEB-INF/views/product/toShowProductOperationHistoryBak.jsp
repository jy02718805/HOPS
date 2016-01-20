﻿﻿﻿﻿﻿﻿﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>

<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>查看产品批量操作</title>
	
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="" method="post" class="form-horizontal">
	<div class="mg10"></div>	
	<table id="productProperty" class="input">
		    <tr>
				<th>运营商:</th>
				<td>
					${carrierInfo.carrierName}
			   </td>
				<th>
					省份:
				</th>
				<td>
					${province.provinceName}
				</td>
			</tr>
			<tr>
				<th>
					城市:
				</th>
				<td>
					${city.cityName}
				</td>
				<th>面值:</th>
				<td>
					${poHistory.parValue }
			   </td>
			</tr>
			<tr>
				<th>商户类型:</th>
				<td>
					<c:choose>
						<c:when test="${poHistory.merchantType == 'ALL'}">
							全部商户
						</c:when>
						<c:when test="${poHistory.merchantType == 'SUPPLY'}">
							供货商
						</c:when>
						<c:when test="${poHistory.merchantType == 'AGENT'}">
							代理商
						</c:when>
						<c:otherwise>
							其他
						</c:otherwise>
					</c:choose>
			   </td>
				<th>操作:</th>
				<td>
					<c:choose>
						<c:when test="${poHistory.operationFlag == 0}">
							关闭
						</c:when>
						<c:when test="${poHistory.operationFlag == 1}">
							打开
						</c:when>
						<c:otherwise>
							其他
						</c:otherwise>
					</c:choose>
			   </td>
			</tr>
			<tr>
				<th>
					商户列表:
				</th>
				<td colspan="3">
					<c:forEach items="${poRuleAssists}" var="poRuleAssist">
						<div style="width:100px;float:left;">${poRuleAssist.merchantName }</div>
					</c:forEach> 
				</td>
			</tr>
		</table>
		<table class="input">
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
