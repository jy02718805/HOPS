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
<script>
    $().ready(function() {
 
				var $inputForm = $("#inputForm");
			// 表单验证
				$inputForm.validate({
					rules: {
									
									newpwd:{
											        required:true,
											        minlength: 6,
													maxlength: 20
										},
										repassword:{
											       required:true,
											       minlength: 6,
												   maxlength: 20,
												   equalTo: "#newpwd"
										},
									
							},
						     messages: {
										repassword:{
										   equalTo:"两次输入的密码不相同"
										}
							}
					
				});
				
});
    
    function rsalogin(){
		var oldpwd=$("#oldpwd").val();
		var newpwd=$("#newpwd").val();
		var modulus=$("#modulus").val();
		var exponent=$("#exponent").val();
		
		setMaxDigits(130);
		var key = new RSAKeyPair(exponent,"",modulus);
		var rsaOldpwd = encryptedString(key, encodeURIComponent(oldpwd));
		var rsaNewpwd = encryptedString(key, encodeURIComponent(newpwd));
		$('#rsaOldpwd').val(rsaOldpwd);
		$('#rsaNewpwd').val(rsaNewpwd);
		$("#inputForm").submit();
	}
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="modifyPasswordDO"  method="post">
	 	<input type="hidden" id="exponent" name="exponent" class="ipt" value="${exponent!""}"/>
		<input type="hidden" id="modulus" name="modulus" class="ipt" value="${modulus!""}"/>
		<input type="hidden" id="rsaOldpwd" class="ipt" name="rsaOldpwd" />
		<input type="hidden" id="rsaNewpwd" class="ipt" name="rsaNewpwd" />
		<table class="input">
		    <tr>
				<th><span class="requiredField">*</span>用户名：</th>
				<td>
				    ${(operator.displayName)!''}
				 </td>
			</tr>
		    <tr>
				<th><span class="requiredField">*</span>原始密码</th>
				<td>
				     <input type="hidden" id="id" name="id" class="ipt" maxlength="200" value="${(operator.id)!''}" />	
					 <input type="password" id="oldpwd" name="oldpwd" class="ipt" maxlength="200" />
					
				   </td>
			</tr>
			
			<tr>
				<th>
					<span class="requiredField">*</span>新密码：
				</th>
				<td>
						<input type="password" id="newpwd" name="newpwd" class="ipt" maxlength="200" />	
				</td>
			</tr>
			
			<tr>
				<th><span class="requiredField">*</span>再次输入新密码：</th>
				<td>
					<input type="password" id="repassword" name="repassword" class="ipt" maxlength="200" />	
			   </td>
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="rsalogin();"/>
				
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.go(-1);"  />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
