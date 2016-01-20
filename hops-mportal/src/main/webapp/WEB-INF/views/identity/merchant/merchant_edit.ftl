<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>编辑信息</TITLE>
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
											       minlength: 2,
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
		$("#inputForm").submit();
	}
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="edit"  method="post">
		<table class="input">
		   <tr>
				<th>所属机构:</th>
				<td>
					${(merchant.organization.organizationName)!""}
					<input type="hidden" name="merchantId" value="${merchant.id}" class="ipt"/>
			   </td>
			</tr>
			<#if merchant.merchantType == 'AGENT'>
				<tr>
					<th><span class="requiredField">*</span>父级商户:</th>
					<td>
						<#if merchant.parentIdentityId==0>
							顶级
						</#if>
						<#list merchantList as list>
							<#if list.id==merchant.parentIdentityId>
								${list.merchantName}
							</#if>
						</#list>
				   </td>
				</tr>
				<tr>
					<th><span class="requiredField">*</span>商户级别:</th>
					<td>
						<#if merchant.merchantLevel=="0">
							一级商户
						<#elseif merchant.merchantLevel=="1">
							二级商户
						<#elseif merchant.merchantLevel=="2">
							三级商户
						</#if>
				   </td>
				</tr>
			</#if>
			<tr>
				<th><span class="requiredField">*</span>商户编号:</th>
				<td>
				${merchant.merchantCode.code}
					<input type="hidden" id="merchantCode" name="merchantCode" class="ipt" maxlength="200" value="${merchant.merchantCode.code}"  />	
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>商户名称:</th>
				<td>
					<input type="text" id="merchantName" name="merchantName" class="ipt" maxlength="200" value="${merchant.merchantName}" />	
			   </td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>商户类型:
				</th>
				<td>
						      <#if merchant.merchantType == 'AGENT'>代理商</#if> 
						       <#if merchant.merchantType == 'SUPPLY'>供货商</#if>
						<input type="hidden" name="type" id="type" class="ipt" value="${merchant.merchantType}"/>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="check();"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.go(-1);" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
