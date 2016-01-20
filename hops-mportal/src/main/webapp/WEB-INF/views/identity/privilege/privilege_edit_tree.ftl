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
    
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="privilege_edit_tree"  method="post">
		<table class="input">
		<input type="hidden"  id="id" name="id" class="ipt" maxlength="200" value="${(privilege.id)!""}" />
		<input type="hidden"  id="parentPrivilegeId" name="parentPrivilegeId" class="ipt" maxlength="200" value="${(privilege.parentPrivilegeId)!""}" />
			<tr>
				<th>
					<span class="requiredField">*</span>父级权限:
				</th>
				<td>
					<#if privilegename == "0">	
							顶级 
			        <#elseif privilegename != "0">
							 ${privilegename}
			        </#if>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>功能标识名称:
				</th>
				<td>
					<input type="text" id="privilegeName" name="privilegeName" class="ipt" maxlength="200" value="${(privilege.privilegeName)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>权限名称:
				</th>
				<td>
					<input type="text" id="permissionName" name="permissionName" class="ipt" maxlength="200" value="${(privilege.permissionName)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>功能描述:
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
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='privilege_list_tree'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
