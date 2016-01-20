<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加商户</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#assign base=request.contextPath>
<#setting number_format="#">
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
										organizationName: {
												   required: true,
													pattern: /^[0-9a-z_A-Z\u4e00-\u9fa5]+$/,
													minlength: 2,
													maxlength: 20
										},
										organizationRegistrationNo:{
											       required:true,
											       minlength: 4,
													maxlength: 64
										},
										organizationRegistrationAddr:{
												   required:true,
											       minlength: 0,
												  maxlength: 200
										},
									  organizationWebsite:{
									  		 required:true,
									  		  minlength: 3,
											  maxlength: 64
									  },
									  organizationIndustry:{
									  		 required:false,
									  		  minlength: 0,
											  maxlength: 64
									  },
									  legal:{
									  		 required:true,
									  		  minlength: 2,
											  maxlength: 20
									  },
									  postcode:{
									  		 required:true,
									  		 pattern: /^[1-9]\d{5}(?!\d)$/,
									  		  minlength: 6,
											  maxlength: 6
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
		var organizationName=$("#organizationName").val();
		if(jmz.GetLength(organizationName) > 20){
			   alert("组织机构名称长度不能超过20位字符！"); 
			   return false;
		}
		var organizationRegistrationNo=$("#organizationRegistrationNo").val();
		if(jmz.GetLength(organizationRegistrationNo) > 64){
			   alert("工商注册号长度不能超过64位字符！"); 
			   return false;
		}
		var organizationRegistrationAddr=$("#organizationRegistrationAddr").val();
		if(jmz.GetLength(organizationRegistrationAddr) > 200){
			   alert("注册地址名称长度不能超过200位字符！"); 
			   return false;
		}
		var organizationIndustry=$("#organizationIndustry").val();
		if(jmz.GetLength(organizationIndustry) > 64){
			   alert("所属行业长度不能超过64位字符！"); 
			   return false;
		}
		var organizationWebsite=$("#organizationWebsite").val();
		if(jmz.GetLength(organizationWebsite) > 64){
			   alert("网站地址长度不能超过64位字符！"); 
			   return false;
		}
		$("#inputForm").submit();
    }
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="organization_edit"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>机构名称:
				</th>
				<td>
				   <input type="hidden"  id="organizationId" name="organizationId" class="ipt" maxlength="20" value="${(organization.organizationId)!""}" />	 
					<input type="text" id="organizationName" name="organizationName" class="ipt" maxlength="20" value="${(organization.organizationName)!""}" />	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>工商注册号:</th>
				<td>
					<input type="text" id="organizationRegistrationNo" value="${(organization.organizationRegistrationNo)!""}"   name="organizationRegistrationNo" class="ipt" maxlength="64" />	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>注册地址:
				</th>
				<td>
					<input type="text" id="organizationRegistrationAddr"  value="${(organization.organizationRegistrationAddr)!""}"    name="organizationRegistrationAddr" class="ipt" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					所属于行业:
				</th>
				<td>
					 <input type="text" id="organizationIndustry" value="${(organization.organizationIndustry)!""}"  name="organizationIndustry" class="ipt" maxlength="64" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>网站地址:
				</th>
				<td>
					 <input type="text" id="organizationWebsite" value="${(organization.organizationWebsite)!""}"   name="organizationWebsite" class="ipt" maxlength="64" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>法人:
				</th>
				<td>
					<input type="text" name="legal" id="legal" value="${(organization.legal)!""}"    class="ipt" maxlength="9" />
				</td>
			</tr>
			
			<tr>
				<th>
					<span class="requiredField">*</span>邮编:
				</th>
				<td>
					<input type="text" name="postcode" id="postcode" value="${(organization.postcode)!""}"   class="ipt" maxlength="9" />
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
