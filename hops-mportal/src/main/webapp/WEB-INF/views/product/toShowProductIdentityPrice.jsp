<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加角色</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
<script>
    
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="#"  method="post">
	<div class="mg10"></div>
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>会员ID:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.identityId}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>会员类型:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.identityTpye}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>会员名称:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.identityName}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>产品ID:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.productId}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>产品名称:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.productName}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>定价策略:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.priceStrategy}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>百分比:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.percentage}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>销售金额:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class='ipt' disabled="disabled" maxlength="200" value="${productIdentityRelation.price}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='${ctx}/product/productIdentityRelationList'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
