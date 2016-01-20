<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>用户注册</title>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script>
	function saveAccountType(){
		var support_str="";
		var identityType_str="";
		//账户属性
		$("input[name='support']:checkbox:checked").each(function(){ 
			support_str+=$(this).val()+",";
		})
		support_str = support_str.substring(0,support_str.length-1);
		$("#type").val(support_str);
		//适用人群
		$("input[name='identityType_cbox']:checkbox:checked").each(function(){ 
			identityType_str+=$(this).val()+",";
		})
		identityType_str = identityType_str.substring(0,identityType_str.length-1);
		$("#identityType").val(identityType_str);
		
		$('#inputForm').submit();
	}
// 		$(document).ready(function() {
// 			//聚焦第一个输入框
// 			$("#loginName").focus();
// 			//为inputForm注册validate函数
// 			$("#inputForm").validate({
// 				rules: {
// 					loginName: {
// 						remote: "${ctx}/register/checkLoginName"
// 					}
// 				},
// 				messages: {
// 					loginName: {
// 						remote: "用户登录名已存在"
// 					}
// 				}
// 			});
// 		});
	</script>
</head>

<body><div class="mg10"></div>

	<form id="inputForm" action="${ctx}/accountType/saveAccountType" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>新增账户类型</small></legend>
<!-- 			account_type_name -->
			<div class="control-group">
				<label for="AccountTypeName" class="control-label">账户类型名称:</label>
				<div class="controls">
					<input type="text" id="AccountTypeName" name="AccountTypeName" class="ipt required" minlength="3"/>
				</div>
			</div>
<!-- 			type 可用(isavaible support)、不可用(unavaible support)、授信(creditable support) -->
			<div class="control-group">
				<label for="type" class="control-label">账户属性:</label>
				<div class="controls">
					<input type="checkbox" name="support" value="IS">可用
				    <input type="checkbox" name="support" value="US">不可用
				    <input type="checkbox" name="support" value="CS">授信
					<input type="hidden" name="type" id="type">
				</div>
			</div>
<!-- 			identityType  适用人群 -->
			<div class="control-group">
				<label for="identityType" class="control-label">账户属性:</label>
				<div class="controls">
					<input type="checkbox" name="identityType_cbox" value="MERCHANT">商户
				    <input type="checkbox" name="identityType_cbox" value="SP">系统管理员
				    <input type="checkbox" name="identityType_cbox" value="CUSTOMER">顾客
				    <input type="checkbox" name="identityType_cbox" value="OPERATOR">操作员
					<input type="hidden" name="identityType" id="identityType">
				</div>
			</div>
<!-- 			scope 一般、结算 -->
			<div class="control-group">
				<label for="scope" class="control-label">账户范围:</label>
				<div class="controls">
					<select name = "scope" id="scope" class="select w80 required">
					    <option value="normol">一般</option>
					    <option value="settle">结算</option>
					</select>
				</div>
			</div>
<!-- 			type_model ccy card -->
			<div class="control-group">
				<label for="typeModel" class="control-label">账户类型:</label>
				<div class="controls">
					<select name = "typeModel" id="typeModel" class="select w80 required">
					    <option value="funds">资金</option>
					    <option value="card">实体卡</option>
					</select>
				</div>
			</div>
<!-- 			ccy 币种 -->
			<div class="control-group">
				<label for="ccy" class="control-label">币种:</label>
				<div class="controls">
					<input type="text" id="ccy" name="ccy" class="ipt" minlength="3"/>
				</div>
			</div>
<!-- 			directory 进、出 -->
			<div class="control-group">
				<label for="directory" class="control-label">备注:</label>
				<div class="controls">
					<select name = "directory" id="directory" class="select w80 required">
					    <option value="debit">进(借记)</option>
					    <option value="credit">出(贷记)</option>
					</select>
				</div>
			</div>
			
			<div class="form-actions">
				<input id="submit_btn" class="button" type="button" value="提交" onclick="saveAccountType()" />&nbsp;	
				<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
