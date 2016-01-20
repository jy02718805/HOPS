<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.jslider.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>供货商密钥信息</title>
	
	<style type="text/css">
	
		#range_securityPropertyMaxLength {width:150px;margin-top: 10px;height: 10px;background-color: #FFFFE0;border: 1px solid #A9C9E2;position: relative;}             
		.ui-slider { position:relative; text-align:left;}
		.ui-slider .ui-slider-range {position:absolute; z-index:1; display:block; border:0; background:#f90}
		.ui-slider-horizontal {height:10px; }
		.ui-slider-horizontal .ui-slider-handle {top:14px; margin-left:0; }
		.ui-slider-horizontal .ui-slider-range {top:20px; height:4px; }
		.ui-slider-horizontal .ui-slider-range-min {left:0; }
		.ui-slider-horizontal .ui-slider-range-max {right:0; }
		.ui-slider .ui-slider-handle {width:10px;height: 15px;background-color: #E6E6FA;border: 1px solid #A5B6C8;top: -6px;display: block;
		cursor: pointer; position: absolute;}
	</style>
</head>

<body>
	<div class="path">
		<a href="">首页</a> &raquo; 供货商密钥信息
	</div>
	<form id="inputForm" action="${ctx}/security/showSupplyMerchantSecurityCredential" method="post" class="form-horizontal">
		<div class=bar>
			<div class=buttonWrap>
				<input type="button"  class="button" onclick="toSaveSecurityCredential()" value="添&nbsp;&nbsp;加" />
		 	</div>
		</div>
		<table id=listTable class=list>
			<tbody>
			<tr>
				<th><a class=sort href="javascript:;" name=securityPropertyName>密钥属性名称</a> </th>
				<th><a class=sort href="javascript:;" name=securityPropertyValue>密钥</a> </th>
				<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>操作</a> </th>
			</tr>
			<c:forEach items="${securityCredentials}" var="SecurityCredential">
				<tr>
					<td>${SecurityCredential.securityProperty.securityPropertyName}</td>
					<td>${SecurityCredential.securityPropertyValue}</td>
					<td>
						<a href="${ctx}/security/toEditSecurityCredential?merchantId=${merchantId}&id=${SecurityCredential.id}">[修改]</a>
						<a href="${ctx}/security/deleteSecurityCredential?merchantId=${merchantId}&id=${SecurityCredential.id}">[删除]</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form>
	
	<script>
		function toSaveSecurityCredential(){
			window.location.href="${ctx}/security/toSaveSecurityCredential?merchantId="+${merchantId};
		}
	</script>
</body>
</html>
