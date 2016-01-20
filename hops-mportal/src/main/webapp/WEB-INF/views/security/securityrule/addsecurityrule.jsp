<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.jslider.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>添加密钥规则</title>
</head>
   
<body>
	<form id="inputForm" action="${ctx}/securityrule/addsecurityrule" method="post" class="form-horizontal">
	<div class="mg10"></div>
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>密钥规则名称:</th>
				<td>
					<input type="text" id="securityrulename" name="securityrulename" value="" class="ipt" maxlength="30"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>字母:</th>
				<td>
					<input type="radio" id="letter0" name="letter" value="0" onclick="selectChange(this)"/>需要
					<input type="radio" id="letter1" name="letter" value="1" onclick="selectChange(this)"  checked="checked"/>不需要
			   </td>
			</tr>
			<tr id="isupperCaseid" style="display:none;">
				<th><span class="requiredField">*</span>大写字母:</th>
				<td>
					<input type="radio" id="isupperCase1" name="isupperCase" value="0"/>需要
					<input type="radio" id="isupperCase2" name="isupperCase" value="1"  checked="checked"/>不需要
			   </td>
			</tr>
			<tr id="islowercaseid" style="display:none;">
				<th><span class="requiredField">*</span>小写字母:</th>
				<td>
					<input type="radio" id="islowercase1" name="islowercase" value="0"/>需要
					<input type="radio" id="islowercase2" name="islowercase" value="1"  checked="checked"/>不需要
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>数字:</th>
				<td>
					<input type="radio" id="figure" name="figure" value="0"/>需要
					<input type="radio" id="figure" name="figure" value="1"  checked="checked"/>不需要
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>特殊字符:</th>
				<td>
					<input type="radio" id="specialcharacter" name="specialcharacter" value="0"/>需要
					<input type="radio" id="specialcharacter" name="specialcharacter" value="1"  checked="checked"/>不需要
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>状态:</th>
				<td>
					<input type="radio" id="status" name="status" value="0"/>启用
					<input type="radio" id="status" name="status" value="1"  checked="checked"/>禁用
			   </td>
			</tr>
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="button" type="button" onclick="doEditSecurityCredential()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
	
	<script>
		function doEditSecurityCredential(){
			var securityrulename=$("#securityrulename").val();
			
			if(securityrulename==''){
				alert('密钥规则名称不能为空!');
				return;
			}
			if(jmz.GetLength(securityrulename) > 20){
				   alert("规则名称长度不能超过20位字符！"); 
				   return false;
			}
			$('#inputForm').submit();
		}
		
        function selectChange(obj){  
            var value=obj.value; 
            var v1 = document.getElementById("isupperCaseid");  
            var v2 = document.getElementById("islowercaseid");  
            if(value=='0'){  
                $("#isupperCaseid").show();  
                $("#islowercaseid").show(); 
                
                $('input:radio[name=isupperCase]')[0].checked = true;
                $('input:radio[name=islowercase]')[0].checked = true;
            }else if(value=='1'){  
                $("#isupperCaseid").hide(); 
                $("#islowercaseid").hide(); 
                
                $('input:radio[name=isupperCase]')[1].checked = true;
                $('input:radio[name=islowercase]')[1].checked = true;
            }
        }  
        
        
	</script>
</body>
</html>
