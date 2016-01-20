<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加商户</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#setting number_format="#">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script>
    $().ready(function() {
 
				var $inputForm = $("#inputForm");
			// 表单验证
				$inputForm.validate({
					rules: {
										merchantName: {
												   required: true,
													pattern: /^[0-9a-z_A-Z\u4e00-\u9fa5]+$/,
													minlength: 2,
													maxlength: 20
										},
										merchantCode:{
											       required:true,
											       minlength: 4,
													maxlength: 20
										},
										organizationNO:{
											       required:true,
											       minlength: 1,
													maxlength: 20
										},
										type:{
												   required:true
										}
							},
						     messages: {
										mer_name: {
											pattern: "非法字符"
										}
							}
					
				});
				
});
	
	function check()
	{
		var merchantName=$("#merchantName").val();
		if(jmz.GetLength(merchantName) > 20){
			   alert("商户名称长度不能超过20位字符！"); 
			   return false;
		}
		var merchantCode=$("#merchantCode").val();
		if(jmz.GetLength(merchantCode) > 20){
			   alert("商户编号长度不能超过20位字符！"); 
			   return false;
		}
		$("#inputForm").submit();
	}
    
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="addUpMerchant"  method="post">
		<table class="input">
		    <tr>
				<th><span class="requiredField">*</span>所属机构:</th>
				<td>
					<select name="organizationNO" id = "organizationNO" class="select">
				        <option value="">--请选择--</option>
					     <#list organizationlist  as organization>
					      		<option value="${organization.organizationId}">${(organization.organizationName)!""}</option>
					      </#list>
					</select>
					&nbsp;&nbsp;
					<a href="${base}/Organization/organization_add"> >>添加组织机构</a>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>商户名称:</th>
				<td>
					<input type="text" id="merchantName" name="merchantName" class="ipt" maxlength="200" />	
			   </td>
			</tr>
			
				<tr>
				<th><span class="requiredField">*</span>商户编号:</th>
				<td>
					<input type="text" id="merchantCode" name="merchantCode" class="ipt" maxlength="200" />	
					<input type="hidden" value="SUPPLY" name="type" id = "type" />
			   </td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="check();"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
