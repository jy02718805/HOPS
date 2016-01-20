<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>产品注册</title>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script>
	function saveRobot(){
		var merchantId_select = $("#merchantId_select").val();
		var classPath = $("#classPath").val();
		if(merchantId_select != null && classPath.length > 0){
			$("#merchantId").val(merchantId_select);
			$('#inputForm').attr("action", "${ctx}/transaction/doUpdateRobot");
			$('#inputForm').submit();
		}
	}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/robot/doSaveRobot" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>商户实体注册</small></legend>
			
			<input type="hidden" id="id" name="id" value="${robot.id}" />
			
			<div class="control-group">
				<label for="merchantId" class="control-label">商户名称:</label>
				<div class="controls">
					<input type="hidden" id="merchantId" name="merchantId" class="input-large required" minlength="0"/>
					<select name = "merchantId_select" id="merchantId_select" class="select required">
						<c:forEach items="${merchantList}" var="merchant">
						    <option value="${merchant.id}">${merchant.merchantName}</option>
					    </c:forEach>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label for="classPath" class="control-label">classPath:</label>
				<div class="controls">
					<input type="text" id="classPath" name="classPath" class="ipt required" value="${robot.classPath}" minlength="0"/>
				</div>
			</div>
			
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="button" onclick="saveRobot()" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
