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
	<title>添加密钥类型</title>
</head>
   
<body>
	<form id="inputForm" action="${ctx}/securitytype/addsecuritytype" method="post" class="form-horizontal">
	<input type="hidden" id="maxlength" name="maxlength" value=""/>
	<input type="hidden" id="minlength" name="minlength" value=""/>
	<input type="hidden" id="securityruleid" name="securityruleid" value=""/>
	<div class="mg10"></div>
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>密钥类型名称:</th>
				<td>
					<input type="text" id="securitytypename" name="securitytypename" value="" class="ipt" maxlength="30"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>密钥属性:</th>
				<td>
				<input type="radio" id="Password" name="modeltype" value="Password" onclick="selectmodeltype(this)"/>Password
				<input type="radio" id="MD5Key" name="modeltype" value="MD5Key" onclick="selectmodeltype(this)"/>MD5Key
				<input type="radio" id="RSAp" name="modeltype" value="RSA公钥" onclick="selectmodeltype(this)"/>RSA公钥
				<input type="radio" id="3DESp" name="modeltype" value="3DES" onclick="selectmodeltype(this)"/>3DES
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>加密类型:</th>
				<td>
					<input type="radio" id="MD5" name="encrypttype" value="MD5"/>MD5
					<input type="radio" id="3DES" name="encrypttype" value="3DES"/>3DES
					<input type="radio" id="RSA" name="encrypttype" value="RSA"/>RSA
			   </td>
			</tr>
			<tr id="minlengthid">
				<th><span class="requiredField">*</span>最小长度:</th>
				<td>
					<input type="text" id="minlengthIpt" name="minlengthIpt" class="ipt" value="" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
			   </td>
			</tr>
			<tr id="maxlengthid">
				<th><span class="requiredField">*</span>最大长度:</th>
				<td>
					<input type="text" id="maxlengthIpt" name="maxlengthIpt" class="ipt" value="" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>有效期（天）:</th>
				<td>
					<input type="text" id="validity" name="validity" class="ipt w60" value="0" readonly="true" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<span>说明: 0为无限期，到期将自动停止</span>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>可以用用户类型:</th>
				<td>
					<input type="checkbox" id="identitytypes" name="identitytypes" value="OPERATOR"  onclick="selectcheckbox()"/>OPERATOR
					<input type="checkbox" id="identitytypes" name="identitytypes" value="AGENTMERCHANT" onclick="selectcheckbox()"/>AGENTMERCHANT
					<input type="checkbox" id="identitytypes" name="identitytypes" value="SUPPLYMERCHANT" onclick="selectcheckbox()"/>SUPPLYMERCHANT<br>
					<input type="checkbox" id="identitytypes" name="identitytypes" value="CUSTOMER" onclick="selectcheckbox()"/>CUSTOMER
					<input type="checkbox" id="identitytypes" name="identitytypes" value="SP" onclick="selectcheckbox()"/>SP
					<input type="hidden" id="identitytype" name="identitytype" value=""/>
			   </td>
			</tr>
			<tr id="securityruleidid" >
				<th><span class="requiredField">*</span>密钥规则:</th>
				<td>
					<select name="securityruleidIpt" id="securityruleidIpt" class="select" >
					<option value="" selected = "selected">--请选择--</option>
					<c:forEach items="${securityRuleList}" var="securityRule">
						<option value="${securityRule.securityRuleId}">${securityRule.securityRuleName}</option>
					</c:forEach>
				   </select>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>状态:</th>
				<td>
					<input type="radio" id="status" name="status" value="0" checked="checked"/>启用
					<input type="radio" id="status" name="status" value="1"/>禁用
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
			var modeltype = $('input[@name="modeltype"]:checked').val(); 
			var encrypttype = $('input[@name="encrypttype"]:checked').val(); 
			
			var validity =$('#validity').val();
			var securitytypename=$('#securitytypename').val();
			
			var maxlength=$('#maxlengthIpt').val();
			var minlength=$('#minlengthIpt').val();
			var securityruleid =$('#securityruleidIpt').val();
			if(securitytypename==''){
				alert('请输入密钥类型名称!');
				return false;
			}
			var securitytypename=$("#securitytypename").val();
			if(jmz.GetLength(securitytypename) > 20){
				   alert("类型名称长度不能超过20位字符！"); 
				   return false;
			}
			if(encrypttype==''){
				alert('请输入加密类型!');
				return false;
			}
			if(modeltype=='' || modeltype=='0'){
				alert('请选择密钥属性!');
				return false;
			}
			if(modeltype=='Password'){
				if(minlength==''){
					alert('请输入最小长度!');
					return false;
				}
				if(maxlength==''){
					alert('请输入最大长度!');
					return false;
				}
				
				if(maxlength==0 || minlength==0){
					alert('最大长度或最小长度不能为零!');
					return false;
				}
				
				if(Number(maxlength)<Number(minlength)){
					alert('最大长度不能小于最小长度!');
					return false;
				}
			}
			
			
			
			
			if(validity==''){
				alert('请输入有效天数!');
				return false;
			}
			
			 var str="";
			    $("input[name='identitytypes']:checked").each(function(){
	                str += (str==""?"":",") + $(this).val();
	            })
	            if(str==''){
	            	alert('请选择用户类型!');
	            	return false;
	            }
	            $("#identitytype").val(str);
	            
	            $("#maxlength").val(maxlength);
	            $("#minlength").val(minlength);
	            $("#securityruleid").val(securityruleid);
	            
			$('#inputForm').submit();
		}
		
		// 条件选择
		 function selectmodeltype(va){
			 if(va.value=="Password"){
				 // //////登录密码：Password（加密类型为MD5）
				 document.getElementById("MD5").checked=true;
				 document.getElementById("3DES").checked=false;
				 $("#RSA").attr("checked",false);
				 
				 $('#validity').attr("disabled",false);
				 // //当密钥属性选择Password时，最大最小长度和密钥规则显示，否则不展示默认为空
				 $("#minlengthid").show(); 
				 $("#maxlengthid").show(); 
				 $("#securityruleidid").show();
	            
			 }else if(va.value=="MD5Key"){
				 // ///代理商MD5Key、供货商MD5Key和系统MD5Key：MD5Key（加密类型为 3DES）
				 document.getElementById("MD5").checked=false;
				 document.getElementById("3DES").checked=true;
				 $("#RSA").attr("checked",false);
				 $('#validity').val(0);
				 $('#validity').attr("disabled",true);
				// //当密钥属性选择Password时，最大最小长度和密钥规则显示，否则不展示默认为空
				 $("#minlengthid").hide(); 
				 $("#maxlengthid").hide(); 
				 $("#securityruleidid").hide();
				 
			 }else if(va.value=="RSA公钥"){
				 // //供货商公钥：RSA公钥（加密类型为3DES）
				 document.getElementById("MD5").checked=false;
				 document.getElementById("3DES").checked=true;
				 $("#RSA").attr("checked",false);
				 
				// //当密钥属性选择Password时，最大最小长度和密钥规则显示，否则不展示默认为空
				 $("#minlengthid").hide(); 
				 $("#maxlengthid").hide(); 
				 $("#securityruleidid").hide();
			 }else if(va.value=="3DES"){
				 $("#RSA").attr("checked",true);
				 $("#minlengthid").hide(); 
				 $("#maxlengthid").hide(); 
				 $("#securityruleidid").hide();
			 }
			}
		
		
		// 提交前校验
		
		 $().ready(function() {
			 $("#minlengthid").hide(); 
			 $("#maxlengthid").hide(); 
			 $("#securityruleidid").hide(); 
			 
		 });
	</script>
</body>
</html>
