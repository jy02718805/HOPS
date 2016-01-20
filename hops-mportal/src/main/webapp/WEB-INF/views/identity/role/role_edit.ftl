<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加角色</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#assign base=request.contextPath>
<#setting number_format="#">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script>
    
$().ready(function() {
 
	var $inputForm = $("#inputForm");
// 表单验证
	$inputForm.validate({
		rules: {
					"roleName": {
					       	required: true,
							minlength: 2,
							maxlength: 20
					},
					"roleType": {
						   required: true
					}
				}
	});
});
function check()
		{
			var roleName=$("#roleName").val();
			if(jmz.GetLength(roleName) > 20){
				   alert("角色名称长度不能超过20位字符！"); 
				   return;
			}
			$("#inputForm").submit();
		}
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="role_edit"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>角色名称:
				</th>
				<td>
				<input type="hidden"  id="roleId" name="roleId" class="ipt" maxlength="200" value="${(role.roleId)!""}" />	
					<input type="text" id="roleName" name="roleName" class="ipt" maxlength="200" value="${(role.roleName)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>角色状态:
				</th>
				<td>
					<INPUT id="status" name="status"  type="radio" value="0" <#if role.status='0'>checked</#if>  >启用
					<INPUT id="status" name="status"  type="radio" value="1" <#if role.status='1'>checked</#if>>禁用
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>角色类型:
				</th>
				<td>
					<INPUT id="roleType" name="roleType"  type="radio" value="MERCHANT" <#if role.roleType='MERCHANT'>checked</#if>  >商户角色
					<INPUT id="roleType" name="roleType"  type="radio" value="SP" <#if role.roleType='SP'>checked</#if>  >系统角色
					<INPUT id="roleType" name="roleType"  type="radio" value="CUSTOMER" <#if role.roleType='CUSTOMER'>checked</#if>  >用户角色
				</td>
			</tr>
			<tr>
				<th>
					备注:
				</th>
				<td>
					<TEXTAREA id="remark" name="remark" rows="6" cols="40" maxlength="200">${(role.remark)!""}</TEXTAREA>
				</td>
			</tr>
			
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="check();"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='list'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
