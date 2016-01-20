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
<script language="javascript" type="text/javascript" src="${base}/template/admin/My97DatePicker/WdatePicker.js"></script>

<script>
    $().ready(function() {
 
				var $inputForm = $("#inputForm");
				
			// 表单验证
				$inputForm.validate({
					rules: {
										displayName:{
											       required:true,
											       minlength: 2,
												   maxlength: 20
										}
							}
					
				});
				
});
    function check()
    {
		var displayName=$("#displayName").val();
		if(jmz.GetLength(displayName) > 20){
			   alert("姓名长度不能超过20位字符！"); 
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
				<th>所属商户名称:</th>
				<td>
                          ${(operator.ownerIdentityId)!""}
			   </td>
			</tr>
			
			<tr>
				<th>所属商户类别:</th>
				<td>
                          ${(operator.ownerIdentityType)!""}
			   </td>
			</tr>
			
			<tr>
				<th>操作员类型:</th>
				<td>
                          <#switch operator.operatorType>
						          <#case "SP_OPERATOR">
						              系统操作员
						             <#break>
						           <#case "MERCHANT_OPERATOR">
						              商户操作员
						             <#break>
						           <#default>
						           	  <#break>
		   					</#switch>
			   </td>
			</tr>
			
			<tr>
				<th>用户名:</th>
				<td>
					  ${(operator.operatorName)!""}
			   </td>
			</tr>
	        <tr>
				<th><span class="requiredField">*</span>姓名:</th>
				<td>
					<input type="text" id="displayName" name="displayName" class="ipt" maxlength="20" value="${(operator.displayName)!""}" />	
			   </td>
			</tr>
			<tr>
					<th>性别:</th>
					<td>
					<input type="hidden" id="person.personId" name="person.personId" class="ipt" maxlength="200" value="${(operator.person.personId)!""}" />	
					     <select  name="person.sex" id="person.sex" class="select">
					     		<option value="0" <#if operator.person.sex=='0'>selected</#if>>男</option>
					     		<option value="1" <#if operator.person.sex=='1'>selected</#if>>女</option>
					     </select>
						
				   </td>
				</tr>
			<tr>
				<th>
					出生日期:
				</th>
				<td>
				
				<#if (operator.person.birthday!)=="">
				<input  class="Wdate" type="text" onClick="WdatePicker()"  id="birthday" name="person.birthday"  class="ipt" maxlength="20" />
				<#else>
				<input  class="Wdate" type="text" onClick="WdatePicker()"  id="birthday" name="person.birthday" value="${(operator.person.birthday)!""}" class="ipt" maxlength="200" />
				</#if>
						
				</td>
			</tr>
				<tr>
					<th>手机号码:</th>
					<td>
						<input type="text" id="person.phone" name="person.phone" class="ipt" maxlength="11" value="${(operator.person.phone)!""}"/>	
				   </td>
				</tr>
				<tr>
					<th>E-Mail:</th>
					<td>
						<input type="text" id="person.email" name="person.email" class="ipt" maxlength="100" value="${(operator.person.email)!""}"/>	
				   </td>
				</tr>
					<tr>
				<th>
					&nbsp;
				</th>
				<td>
				   <input type=hidden name="id" value="${(operator.id)!""}"/>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="check();"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back();" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
