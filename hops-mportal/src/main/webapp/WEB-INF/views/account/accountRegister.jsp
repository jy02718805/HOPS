<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>账户注册</title>
	<script>
	function saveAccount(){
		var accountType = $("#accountType_select").val();
		var rmk = $("#rmk").val();
		$('#inputForm').attr("action", "${ctx}/account/saveAccount?accountType="+accountType);
		$('#inputForm').submit();
	}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/account/saveAccount" method="post" class="form-horizontal">
		<input id="identityId" type="hidden" name="identityId" value="${identityId}">
		
		<table id="productProperty" class="input">
		    <tr>
				<th><span class="requiredField">*</span>账户类型:</th>
				<td>
					<select name = "accountType_select" id="accountType_select" class="select required">
						<c:forEach items="${accountTypeList}" var="accountType">
						    <option value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
					    </c:forEach>
					</select>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>备注:</th>
				<td>
					<input type="text" name="rmk" id="rmk" class="ipt" value=""/>
			   </td>
			</tr>
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
				<input id="submit_btn" class="btn btn-primary" type="button" onclick="saveAccount()" value="提交"/>
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
