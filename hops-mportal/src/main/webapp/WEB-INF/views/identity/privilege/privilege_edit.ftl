<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">

<TITLE>编辑权限</TITLE>
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
					"privilegeName": {
				       	required: true ,
						minlength: 2,
						maxlength: 50
					},
					"permissionName":{
				   		required: true,
						minlength: 2,
						maxlength: 50
					}
					}
			
		});
				
});
		function check()
		{
			var privilegeName=$("#privilegeName").val();
			if(jmz.GetLength(privilegeName) > 50){
				   alert("功能标识长度不能超过50位字符！"); 
				   return false;
			}
			var permissionName=$("#permissionName").val();
			if(jmz.GetLength(permissionName) > 50){
				   alert("权限名称长度不能超过50位字符！"); 
				   return false;
			}
			$("#inputForm").submit();
		}
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="privilege_edit"  method="post" >
		<table class="input">
		<input type="hidden"  id="privilegeId" name="privilegeId" class="ipt" maxlength="200" value="${(privilege.privilegeId)!""}" />
			<tr>
				<th>
					<span class="requiredField">*</span>父级权限:
				</th>
				<td>
					<select name="parentPrivilegeId" id="parentPrivilegeId" class="select">
						<#if privilege.parentPrivilegeId==0>
							<option value="0" selected="selected">--顶级--</option>
						</#if>
						<#list parentList as list>
							<option value="${list.privilegeId}" <#if privilege.parentPrivilegeId==list.privilegeId>selected="selected"</#if>>
								${list.privilegeName}
							</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>功能标识名称:
				</th>
				<td>
					<input type="text" id="privilegeName" name="privilegeName" class="ipt" maxlength="50" value="${(privilege.privilegeName)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>权限名称:
				</th>
				<td>
					<input type="text" id="permissionName" name="permissionName" class="ipt" maxlength="50" value="${(privilege.permissionName)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					功能描述:
				</th>
				<td>
					<textarea id="remark" name="remark" class="text" maxlength="200" >${(privilege.remark)!""}</textarea>	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="check();"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
