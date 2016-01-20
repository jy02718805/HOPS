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
	<form id="inputForm" action="${ctx}/product/doEditProductIdentityPrice"  method="post">
		<input type="hidden" id="id" name="id" value="${productIdentityRelation.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>百分比:
				</th>
				<td>
					<input type="text" id="percentage" name="percentage" class="ipt" maxlength="200" value="${productIdentityRelation.percentage}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="确&nbsp;&nbsp;定" />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='${ctx}/product/productIdentityRelationList'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
