<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE>管理中心</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<META content=0 http-equiv=expires>
<META content=no-cache http-equiv=Pragma>
<META content=no-cache http-equiv=Cache-Control>
<META name=author content="identity">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/base.css">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/login_yc.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/jsbn.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/prng4.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/rng.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/rsa.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/base64.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/RSA/RSA.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/RSA/Barrett.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/RSA/BigInt.js"></SCRIPT>
<SCRIPT type=text/javascript>
	$().ready( function() {
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
	
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				alert("请输入您的用户名");
				return false;
			}
			if ($password.val() == "") {
				alert("请输入您的密码");
				return false;
			}
	
		});
		
		<#if errmsg??>
			alert("${errmsg}");
			location.href="${base}/login";
		</#if>
		
		if( window.top != window.self ){
		   top.location="${base}/login";
		}
		
	});
	
	
	function rsalogin(){
		var password=$("#password").val();
		var modulus=$("#modulus").val();
		var exponent=$("#exponent").val();
		
		setMaxDigits(130);
		var key = new RSAKeyPair(exponent,"",modulus);
		var result = encryptedString(key, encodeURIComponent(password));
		$('#rsaPassword').val(result);
		$("#loginForm").submit();
	}
</SCRIPT>
<body>
<FORM id='loginForm' method='post' action='login'>
			<INPUT id=enPassword type=hidden name=enPassword>
		  	<input type="hidden" id="exponent" name="exponent" value="${exponent!""}"/>
		  	<input type="hidden" id="modulus" name="modulus" value="${modulus!""}"/>
		  	<input type="hidden" id="rsaPassword" name="rsaPassword"/>
<div class="login-header container">
<h1 class="logo"><a href="#"><img src="${base}/template/admin/images/pt-login-logo.png" width="126" height="53"/></a></h1>
</div>

<div class="login-box container">
<div class="login-form">
<fieldset>
<legend>用户登录表单</legend>
<p>
<label class="login-ico-user" for="name">账号：</label><input class="input" value="${(username)!""}" type="text" name="username" id="username" />
</p>
</FORM>
<p>
<label class="login-ico-pass" for="password">密码：</label><input class="input" value="" type="password" name="password" id="password" autocomplete="off"/>
</p>
<input type="button" value="登&nbsp;录" class="btn-block btn-big"  onclick="rsalogin();" />
</fieldset>
</div>
</div>

<div class="login-footer container">
<p class="login-footer-company">湖南跃程网络科技有限公司</p>
COPYRIGHT © 2005-2014 .
</div>

<div class="bg"><img src="${base}/template/admin/css/bg/login_bg_user.jpg" /></div>
</body>
</html>
</HTML>
