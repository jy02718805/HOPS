`<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>账户日志列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<style>
#pager ul.pages { 
display:block; 
border:none; 
text-transform:uppercase; 
font-size:10px; 
margin:10px 0 50px; 
padding:0; 
} 
#pager ul.pages li { 
list-style:none; 
float:left; 
border:1px solid #ccc; 
text-decoration:none; 
margin:0 5px 0 0; 
padding:5px; 
} 
#pager ul.pages li:hover { 
border:1px solid #003f7e; 
} 
#pager ul.pages li.pgEmpty { 
border:1px solid #eee; 
color:#eee; 
} 
#pager ul.pages li.pgCurrent { 
border:1px solid #003f7e; 
color:#000; 
font-weight:700; 
background-color:#eee; 
}
</style>
<body>
<form id="inputForm" method="post" action="${ctx}/security/saveSecurityCredential">
<div class="mg10"></div>
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>用户名称:
	
				</th>
				<td>
				<select id='identityId' name="identityId" class="select">
						<option value="">请选择</option>
						<c:forEach items="${merchants}" var="merchant">
						<option value="${merchant.id}">${merchant.merchantName}</option>
					</c:forEach>
				</select>
				
				</td>
				<th>
					<span class="requiredField">*</span>用户类型 :
				</th>
				<td>
					<select name="identityType"  id="identityType" class='select w80'>
						<option value="MERCHANT">商户</option>
						<!-- <option value="">请选择</option>
						<option value="SP">管理员</option>
						<option value="OPERATOR">操作员</option>
						<option value="CUSTOMER">用户</option> -->
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>密钥名称:
				</th>
				<td>
					<input type="text" id='securityName' name="securityName" value="" class="ipt" maxlength="20"/>
				</td>
				<th>
				<span class="requiredField">*</span>密钥类型 :
				</th>
				<td>
					<select name="securityTypeId" id="securityTypeId" class="select w100">
						<option value="">请选择</option>
						<c:forEach items="${securityTypes}" var="securityType">
							<option value="${securityType.securityTypeId}">${securityType.securityTypeName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>
				<span class="requiredField">*</span>密钥源码:
				</th>
				<td colspan="3">
					<input type="text" id='securityValue'  name="securityValue" value="" class="ipt w600"/>
				</td>
			</tr>
			<tr>
				<th>
					状态:
  	
				</th>
				<td colspan="3">
					<select name="status" class="select w80">
						<c:choose>
							<c:when test="${securityCredential.status==0}">
								<option value="0" selected="selected">启用</option>
							</c:when>
							<c:otherwise>
								<option value="0">启用</option>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${securityCredential.status==1}">
								<option value="1"  selected="selected">禁用</option>
							</c:when>
							<c:otherwise>
								<option value="1">禁用</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" id='submitSecurity' name='submitSecurity'  value="确定" />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
				</td>
			</tr>
		</table>
		
		
		
		
 	<script type="text/javascript" language="javascript">
 	$().ready(function() {
		var $inputForm = $("#inputForm");
	// 表单验证
		var validator = $inputForm.validate({
					rules: {
								"identityId": {
									   required: true
								},
								"identityType":{
									   required: true
								},
								"securityName":{
									   required: true
								},
								"securityTypeId": {
									   required: true
								},
								"securityValue":{
									   required: true
								},
								"status": {
									   required: true
								}
					},
				messages: {
					"identityType": {
						required: "请选择用户类型!"
					},
					"securityTypeId": {
						required: "请选择密钥类型!"
					},
					"status": {
						required: "请选择状态!"
					}
				}   
		});
				
				$("#submitSecurity").click(function() {
			     	if(validator.form()){ 
			     		var securityName=$("#securityName").val();
						if(jmz.GetLength(securityName) > 20){
							   alert("秘钥名称长度不能超过20位字符！"); 
							   return false;
						}
			          	 $("#inputForm").submit();
			        }
 				})
		
});
 	
 	
	</script> 
</form>
</body>
</html>

