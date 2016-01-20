<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
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
	<title>创建预成功配置</title>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/agentQueryFakeRule/doSaveAgentQueryFakeRule" class="form-horizontal">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>代理商商户:
				</th>
				<td>
					<select name="merchantId" id="merchantId" class="select">
						<option value="-1">请选择</option>
						<c:forEach items="${downMerchants}" var="merchant">
							<option value="${merchant.id}">${merchant.merchantName}</option>
						</c:forEach>
					 </select>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>时间间隔量:</th>
				<td>
					<input type="text" name="intervalTime" id="intervalTime" class="ipt" onKeyUp="this.value=this.value.replace(/\D/g,'')"  maxlength="5"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>时间间隔单位:</th>
				<td>
					<select name="intervalUnit" id="intervalUnit" class="select w80">
						<option value="-1">请选择</option>
						<option value="s">秒</option>
						<option value="m">分</option>
						<option value="h">时</option>
						<option value="d">天</option>
					</select>
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
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="saveFakeRule()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
		<script type="text/javascript" language="javascript">
			function saveFakeRule(){
				var merchantId = $("#merchantId").val();
				if(merchantId == -1){
					alert("请选择代理商商户！");
					return;
				}
				var intervalUnit = $("#intervalUnit").val();
				if(intervalUnit == -1){
					alert("请选择时间间隔单位！");
					return;
				}
				var intervalTime = $("#intervalTime").val().trim();
				if(intervalTime == ""){
					alert("请输入时间间隔量！");
					return;
				}
				
				$('#inputForm').submit();
			}
		</script>
	</form>
</body>
</html>
