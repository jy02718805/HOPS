<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>

<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>手工下单</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/transaction/morderRequestHandler" class="form-horizontal" onsubmit="return check()">
	<div class="mg10"></div>
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>电话号码:</th>
				<td>
					<input type="text" name="phoneNo" id="phoneNo" class="ipt" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>充值金额:</th>
				<td>
					<input type="text" name="faceValue" id="faceValue" class="ipt"  maxlength="10" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>通知地址:</th>
				<td>
					<input type="text" name="notifyUrl" id="notifyUrl" class="ipt" maxlength="256" />
			   </td>
			</tr>
		</table>
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="button" type="submit" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
		<script type="text/javascript" language="javascript">
		function check(){  
	  		 
		      /******  手机号码   ******/  
		      var phoneNo = document.getElementById("phoneNo").value.trim(); 
		      if(phoneNo==""){  
		         alert('请输入手机号码~~~！');   
			     document.getElementById("phoneNo").focus(); 
		         return false;  
		      } 
		      if(phoneNo.length<11){  
			         alert('手机号码长度不能少于11位~~~！');  
				     document.getElementById("phoneNo").focus(); 
			         return false;  
			  }
		      
		      /******  面值   ******/  
		      var faceValue = document.getElementById("faceValue").value.trim(); 
		      if(faceValue==""){  
		         alert('请输入面值~~~！');  
			     document.getElementById("faceValue").focus(); 
		         return false;  
		      }
		      
		      /****** 通知地址   ******/  
		      var notifyUrl = document.getElementById("notifyUrl").value.trim(); 
		      if(notifyUrl==""){  
		         alert('请输入通知地址~~~！'); 
			     document.getElementById("notifyUrl").focus();  
		         return false;  
		      }
		 }  
		
		</script>
	</form>
</body>
</html>
