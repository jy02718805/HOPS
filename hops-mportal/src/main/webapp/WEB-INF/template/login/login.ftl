<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0038)http://demo.shopxx.net/admin/login.jsp -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE>管理中心</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<META content=0 http-equiv=expires>
<META content=no-cache http-equiv=Pragma>
<META content=no-cache http-equiv=Cache-Control>
<META name=author content="identity">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/login.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/jsbn.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/prng4.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/rng.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/rsa.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/base64.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>

<SCRIPT type=text/javascript>
	$().ready( function() {
		
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $isRememberUsername = $("#isRememberUsername");
		
		// 记住用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "/admin/common/captcha.jhtml?captchaId=0227fc72-466a-47dc-94c4-af8fd4e08c16&timestamp=" + (new Date()).valueOf());
		});
		
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.message("warn", "请输入您的用户名");
				return false;
			}
			if ($password.val() == "") {
				$.message("warn", "请输入您的密码");
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("adminUsername");
			}
			
			var rsaKey = new RSAKey();
			rsaKey.setPublic(b64tohex("AI5C2+iG+5NLZDbrgS4ifpjPcYldOKm7DhD9GqA/7AbY5F74CG60TwlY/PmWWbLuB/2hZ8Gu94XLRc/lPWyUh9XTPkeij7070JHShKAKUvF6c96mqBV51cfsDZiP3sT2vPxFt/NntS42wRlt5FaroC0FtAuW4m3d6QZWF1pKQegv"), b64tohex("AQAB"));
			var enPassword = hex2b64(rsaKey.encrypt($password.val()));
			$enPassword.val(enPassword);
		});
		
		
	});
</SCRIPT>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
</HEAD>
<BODY>
<DIV class=login>
<FORM id=loginForm method=post action=login><INPUT id=enPassword type=hidden 
name=enPassword> <INPUT value=0227fc72-466a-47dc-94c4-af8fd4e08c16 type=hidden 
name=captchaId> 
<TABLE>
  <TBODY>
  <TR>
    <TD vAlign=bottom rowSpan=2 width=190 align=middle><IMG alt=SHOP++ 
      src="${base}/template/admin/images/login_logo.gif"> </TD>
    <TH>用户名: </TH>
    <TD><INPUT id=username class=text value=admin maxLength=20 name=username> 
    </TD></TR>
  <TR>
    <TH>密&nbsp;&nbsp;&nbsp;码: </TH>
    <TD><INPUT id=password class=text value=admin maxLength=20 type=password 
      autocomplete="off"> </TD></TR>
  <TR>
    <TD>&nbsp; </TD>
    <TH>验证码: </TH>
    <TD><INPUT id=captcha class="text captcha" maxLength=4 name=captcha 
      autocomplete="off"><!--<IMG id=captchaImage class=captchaImage title=点击更换验证码 
      src="/captcha.jpg"> --></TD></TR>
  <TR>
    <TD>&nbsp; </TD>
    <TH>&nbsp; </TH>
    <TD><LABEL><INPUT id=isRememberUsername value=true type=checkbox> 记住用户名 
      </LABEL></TD></TR>
  <TR>
    <TD>&nbsp; </TD>
    <TH>&nbsp; </TH>
    <TD><INPUT class=homeButton onClick="location.href='/'" type=button><INPUT class=loginButton value=登录 type=submit> 
    </TD></TR></TBODY></TABLE>
<DIV class=powered>COPYRIGHT © 2005-2013 .</DIV>
<!--<DIV class=link><A href=""></A> | <A 
href=""></A> | <A 
href=""></A> | <A 
href=""></A> | <A 
href=""></A> | <A 
href=""></A> 
</DIV>--></FORM></DIV></BODY></HTML>
