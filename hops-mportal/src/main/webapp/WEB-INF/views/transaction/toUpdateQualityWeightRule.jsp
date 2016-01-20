<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加角色</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
<html>
<head>
	<title>产品注册</title>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script>
	function updateQualityWeightRule(){
		var speedWeight=$('#speedWeight').val();
		var successWeight=$('#successWeight').val();
		
		var type="^[0-9]*[0-9][0-9]*$";
        var re=new RegExp(type);
       if(speedWeight.match(re)==null)
        {
         alert( "[速度比重] 请输入0~100的正整数！");
         return;
        } 
		
       if(successWeight.match(re)==null)
       {
    	   alert('[成功率比重] 请输入0~100的数字！');
    	   return;
       } 
		
		if((Number(speedWeight)+Number(successWeight))!=100){
			alert('[速度比重]与[成功率比重]和 必须是100 ！');
			return;
		}
		
		$('#inputForm').attr("action", "${ctx}/transaction/doUpdateQualityWeightRule");
		$('#inputForm').submit();
	}
	
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/transaction/doUpdateQualityWeightRule" method="post" class="form-horizontal">
			<legend><small>质量比重设置</small></legend>
			<input type="hidden" name="id" id="id" value="${qwr.id}">
			<table class="input">
				<tr>
					<th>
						<span class="requiredField">*</span>速度比重:
					</th>
					<td>
						<input type="text" id="speedWeight" name="speedWeight" class="ipt" value="${qwr.speedWeight}" minlength="0"/>
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>成功率比重:
					</th>
					<td>
						<input type="text" id="successWeight" name="successWeight" class="ipt" value="${qwr.successWeight}" minlength="0"/>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="updateQualityWeightRule()"/>
						<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
					</td>
				</tr>
			</table>
	</form>
</body>
</html>
