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
	<title>修改密钥类型</title>
</head>


<body>
	<form id="inputForm" action="${ctx}/securitytype/editsecuritytype" method="post" class="form-horizontal">
		<input type="hidden" id="securitytypeid" name="securitytypeid" value="${securitytype.securityTypeId }" class='ipt'/>
		<div class="mg10"></div>
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>密钥类型名称:</th>
				<td>
					<input type="text" class="ipt" id="securitytypename" name="securitytypename" value="${securitytype.securityTypeName }" class='ipt' maxlength="30"/>
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
			<tr id="minlengthid" style="display:none;">
				<th><span class="requiredField">*</span>最小长度:</th>
				<td>
					<input type="text" id="minlength" name="minlength" class="ipt" value="${securitytype.minLength }" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
			   </td>
			</tr>
			<tr id="maxlengthid" style="display:none;">
				<th><span class="requiredField">*</span>最大长度:</th>
				<td>
					<input type="text" id="maxlength" name="maxlength" class="ipt" value="${securitytype.maxLength }" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
			   </td>
			</tr>
			<tr id="validityid" >
				<th><span class="requiredField">*</span>有效期（天）:</th>
				<td>
					<input type="text" id="validity" class="ipt w60" name="validity" value="${securitytype.validity }" readonly="true"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<span>说明: 0为无限期，到期将自动停止</span>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>可以用用户类型:</th>
				<td>
				<input type="checkbox" id="identitytype1" name="identitytypes" value="OPERATOR" />OPERATOR
				<input type="checkbox" id="identitytype2" name="identitytypes" value="AGENTMERCHANT"/>AGENTMERCHANT
				<input type="checkbox" id="identitytype3" name="identitytypes" value="SUPPLYMERCHANT"/>SUPPLYMERCHANT<br>
				<input type="checkbox" id="identitytype4" name="identitytypes" value="CUSTOMER"/>CUSTOMER
				<input type="checkbox" id="identitytype5" name="identitytypes" value="SP"/>SP
					<input type="hidden" id="identitytype" name="identitytype" value=""/>
			   </td>
			</tr>
			<tr id="securityruleidid" style="display:none;">
				<th><span class="requiredField">*</span>密钥规则:</th>
				<td>
				<div id="securityruleidValue" style="display:inline-block;"></div>
					<select name="securityruleid" id="securityruleid" class="select">
					<option value="" >--请选择--</option>
					<c:forEach items="${securityRuleList}" var="securityRule">
					<option value="${securityRule.securityRuleId}" 
						<c:if test="${securityRule.securityRuleId==securitytype.securityRule.securityRuleId}"> selected="selected"</c:if>
						>${securityRule.securityRuleName}</option>
					</c:forEach>
				   </select>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>状态:</th>
				<td>
				<div id="statusValue" style="display:inline-block;"></div>
					<input type="radio" id="on" name="status" value="0"/>启用
					<input type="radio" id="off" name="status" value="1"/>禁用
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
			
			var maxlength=$('#maxlength').val();
			var minlength=$('#minlength').val();
			var securityruleid =$('#securityruleid').val();
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
			$('#inputForm').submit();
		}

		//条件选择
		 function selectmodeltype(va) {
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


		//提交前校验


	</script>
</body>
</html>
<script>
if("${securitytype.modelType}"=="Password"){
	document.getElementById("Password").checked=true;
	 $("#minlengthid").show(); 
	 $("#maxlengthid").show(); 
	 $("#securityruleidid").show();
    
}
if("${securitytype.modelType}"=="MD5Key"){
	document.getElementById("MD5Key").checked=true;
}
	///document.getElementById("MD5Key").checked=true;
if("${securitytype.modelType}"=="RSA公钥"){
	document.getElementById("RSAp").checked=true;
}
if("${securitytype.modelType}"=="3DES"){
	document.getElementById("3DESp").checked=true;
}
	//document.getElementById("RSA").checked=true;
if("${securitytype.encryptType}"=="MD5"){
	document.getElementById("MD5").checked=true;
}
	//document.getElementById("MD5").checked=true;
if("${securitytype.encryptType}"=="3DES"){
	document.getElementById("3DES").checked=true;
}
if("${securitytype.encryptType}"=="RSA"){
	document.getElementById("RSA").checked=true;
}
	//document.getElementById("3DES").checked=true;
if("${securitytype.status}"=="0"){
	document.getElementById("on").checked=true;
}
	///document.getElementById("on").checked=true;
if("${securitytype.status}"=="1"){
	document.getElementById("off").checked=true;
}

var identitytypes = "${securitytype.identityType}";
var strs= new Array(); //定义一数组
strs=identitytypes.split(","); //字符分割
for (i=0;i<strs.length ;i++ ){
	if(strs[i]=="OPERATOR") document.getElementById("identitytype1").checked="checked";
	if(strs[i]=="AGENTMERCHANT") document.getElementById("identitytype2").checked="checked";
	if(strs[i]=="SUPPLYMERCHANT") document.getElementById("identitytype3").checked="checked";
	if(strs[i]=="CUSTOMER") document.getElementById("identitytype4").checked="checked";
	if(strs[i]=="SP") document.getElementById("identitytype5").checked="checked";

}
</script>
