<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>实体卡修改</title>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
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
	<form id="inputForm" method="post" class="form-horizontal">
	<div class="mg10"></div>
		<fieldset>
			<legend><small>账户注册</small></legend>
			<div class="control-group">
				<label for="accountType" class="control-label">账户类型:</label>
				<div class="controls">
					<select name = "accountType_select" id="accountType_select" class="select required">
						<c:forEach items="${accountTypeList}" var="accountType">
						    <option value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
					    </c:forEach>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label for="rmk" class="control-label">备注:</label>
				<div class="controls">
					<input type="text" id="rmk" name="rmk" class="ipt required" minlength="3"/>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="button" type="button" onclick="saveAccount()" value="提交"/>	
				<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
