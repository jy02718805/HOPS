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
<SCRIPT type=text/javascript src="${base}/template/admin/js/RSA/Barrett.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/RSA/BigInt.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/RSA/RSA.js"></SCRIPT>

<script language="javascript" type="text/javascript" src="${base}/template/admin/My97DatePicker/WdatePicker.js"></script>

<script>
    $().ready(function() {
 
				var $inputForm = $("#inputForm");
			// 表单验证
				$inputForm.validate({
					rules: {
									
										"operatorName": {
											   required: true,
											   minlength: 6,
											   maxlength: 20
										},
										"password":{
											   required: true,
											   minlength: 6,
											   maxlength: 18
										},
										"confimloginPassword":{
											   required: true,
											   minlength: 6,
											   maxlength: 18,
											   equalTo: "#password"
										},
										"displayName": {
											   required: true,
											   minlength: 2,
											   maxlength: 20
										}									
										
							}
					
				});
				
});
    
    
   		function rsalogin(){
   		
			var operatorName=$("#operatorName").val();
			if(jmz.GetLength(operatorName) > 20){
				   alert("用户名长度不能超过20位字符！"); 
				   return false;
			}
			var displayName=$("#displayName").val();
			if(jmz.GetLength(displayName) > 20){
				   alert("姓名长度不能超过20位字符！"); 
				   return false;
			}
			var password=$("#password").val();
			var modulus=$("#modulus").val();
			var exponent=$("#exponent").val();
			
			setMaxDigits(130);
			var key = new RSAKeyPair(exponent,"",modulus);
			var result = encryptedString(key, encodeURIComponent(password));
			$('#loginPassword').val(result);
			
			$("#inputForm").submit();
		}
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="addSystemOperator"  method="post">
	  	<input type=hidden name="ownerIdentityId" value="${(sp.id)!""}"/>
	  	<input type="hidden" id="exponent" name="exponent" value="${exponent!""}"/>
	  	<input type="hidden" id="modulus" name="modulus" value="${modulus!""}"/>
	  	<input type="hidden" id="loginPassword" name="loginPassword"/>
	  	
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>用户名:</th>
				<td>
					<input type="text" id="operatorName" name="operatorName" class="ipt" maxlength="20" />	
			   </td>
			</tr>
			
			<tr>
				<th><span class="requiredField">*</span>密&nbsp;&nbsp码:</th>
				<td>
					<input type="password" id="password" name="password" class="ipt" maxlength="18" />	
			   </td>
			</tr>
			
			<tr>
				<th><span class="requiredField">*</span>确认密码:</th>
				<td>
					<input type="password" id="confimloginPassword" name="confimloginPassword" class="ipt" maxlength="18" />	
			   </td>
			</tr>
			
				<tr>
					<th><span class="requiredField">*</span>姓&nbsp;&nbsp;名:</th>
					<td>
						<input type="text" id="displayName" name="displayName" class="ipt" maxlength="20" />	
				   </td>
				</tr>
				
				<tr>
					<th>性别:</th>
					<td>
					     <select  name="person.sex" id="sex" class="select">
					     		<option value="0">男</option>
					     		<option value="1">女</option>
					     </select>
						
				   </td>
				</tr>
				
				<tr>
				<th>出生日期:
				</th>
				<td>
					<input  class="Wdate" type="text" onClick="WdatePicker()"  id="person.birthday" name="person.birthday" class="ipt" maxlength="20" />
				</td>
			</tr>
			
				<tr>
					<th>手机号码:</th>
					<td>
						<input type="text" id="person.phone" name="person.phone" class="ipt" maxlength="11" />	
				   </td>
				</tr>
				<tr>
					<th>E-Mail:</th>
					<td>
						<input type="text" id="person.email" name="person.email" class="ipt" maxlength="100" />	
				   </td>
				</tr>
			
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="rsalogin();" />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back();" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
