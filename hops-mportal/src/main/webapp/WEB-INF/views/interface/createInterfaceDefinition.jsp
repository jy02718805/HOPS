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
	<title>接口定义</title>
	
	<script>
		function doCreateInterfaceDefinition(){
			var interfaceType = $("#interfaceType").val();
			var isConf = $("#isConf").val();
			var entityName = $("#entityName").val();
			
			if(interfaceType == ""){
				alert("请选择接口类型!");
				return;
			}
			if(isConf == ""){
				alert("请选择是否配置!");
				return;
			}else{
				if(isConf == "0" && entityName == ""){
					alert("请输入实体名称!");
					return;
				}
			}
			$('#inputForm').submit();
		}
		
		function showEntityName(){
			var isConf = $("#isConf").val();
			if(isConf == "0"){
				$("#entityNameTr").show();
			}else{
				$("#entityNameTr").hide();
			}
		}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/interface/doCreateInterfaceDefinition" method="post" class="form-horizontal">
		<input type="hidden" id="merchantId" name="merchantId" value="${merchantId}"/>
		<table id="interfacePacketsDefinition" class="input">
		    <tr>
				<th><span class="requiredField">*</span>接口类型:</th>
				<td>
					<select name="interfaceType" id="interfaceType" class="select">
						<option value="">请选择</option>
						<option value="send_order">发送订单</option>
						<option value="agent_notify_order">通知代理商</option>
						<option value="agent_notify_TBorder">通知淘宝代理商</option>
						<option value="query_order">查询订单</option>
						<option value="supply_notify_order">供货商通知订单</option>
						<option value="supply_send_order_flow">供货商流量下单</option>
						<option value="supply_query_order_flow">供货商流量查询</option>
						<option value="supply_notify_order_flow">供货商流量通知</option>
						<option value="agent_notify_order_flow">代理商流量通知</option>
					</select>
			   </td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>是否配置:
				</th>
				<td>
					<select name="isConf" id="isConf" onchange="showEntityName()" class="select">
						<option value="">请选择</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
			</tr>
			<tr id="entityNameTr" style="display: none">
				<th>
					<span class="requiredField">*</span>实体名称:
				</th>
				<td>
					<input id="entityName" name="entityName" value="" />
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
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="doCreateInterfaceDefinition()" value="下一步"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
