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
    $().ready(function dd() {
 
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
    
    
    function getParentMerchant(){
    	var merchantName=document.getElementById("merchantName1").value;
	    $.ajax({
	        type: "post",
	        data: null,
	        url: "getParentMerchant?merchantName="+merchantName,
			async: false,
	        success: function (data) {
		        var parentMerchantlist=data.split("|");
		        document.getElementById("parentIdentityId").options.length=0; 
		        document.getElementById("parentIdentityId").options.add(new Option('--顶级--','0'));
		        var i=0;
		        var length=parentMerchantlist.length-1;
		        if(length!=0)
		        {
			        while(i<length)
			        {
			        	var merchant=parentMerchantlist[i].split("*");
			        	document.getElementById("parentIdentityId").options.add(new Option(merchant[1],merchant[0])); 
			        	i++;
			        }
		        }else{
	            alert("暂无此商户:"+merchantName);
		        }
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
}
     function addDownMerchant(){
     	var merchantName=$("#merchantName").val();
     	var parentName=document.getElementById("parentIdentityId").options[document.getElementById("parentIdentityId").selectedIndex].text
	   
		if(jmz.GetLength(merchantName) > 20){
			   alert("商户名称长度不能超过20位字符！"); 
			   return false;
		}
		var merchantCode=$("#merchantCode").val();
		if(jmz.GetLength(merchantCode) > 20){
			   alert("商户编号长度不能超过20位字符！"); 
			   return false;
		}
	    if (confirm("确认创建："+merchantName+"，父级是："+parentName+" 的代理商吗?")) {
     		$("#inputForm").submit();
		}
     }
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="addDownMerchant"  method="post">
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>父级商户名:</th>
				<td>
					<input type="text" id="merchantName1" name="merchantName1" class="ipt" maxlength="200" />&nbsp;&nbsp;
					<input type="button" class="button" value="搜&nbsp;&nbsp;索" onclick="getParentMerchant()" />
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>父级商户:</th>
				<td>
					<select name="parentIdentityId" id = "parentIdentityId" class="select">
					<option value="0">--顶级--</option>
					</select>
			   </td>
			</tr>
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
					<input type="hidden" value="AGENT" name="type" id = "type" class="ipt"/>
			   </td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="addDownMerchant()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
