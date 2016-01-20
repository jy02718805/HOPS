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
    
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="role_view"  method="post">
		<table class="input">
			<tr>
				<th>
					角色ID:
				</th>
				<td>
					${(role.roleId)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					角色名称:
				</th>
				<td>
					${(role.roleName)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					创建时间:
				</th>
				<td>
					${role.createTime?string("yyyy-MM-dd HH:mm:ss")}
				</td>
			</tr>
			<tr>
				<th>
					更新人名称:
				</th>
				<td>
					${(role.updateUser)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					更新时间:
				</th>
				<td>
					${role.updateTime?string("yyyy-MM-dd HH:mm:ss")}
				</td>
			</tr>
			<tr>
				<th>
					角色状态:
				</th>
				<td>
					<#if role.status=='0'>
						启用
					<#else>
						禁用
					</#if>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>角色类型:
				</th>
				<td>
					<#if role.roleType=="MERCHANT">
						商户角色
					<#else>
						系统角色
					</#if>
				</td>
			</tr>
			<tr>
				<th>
					备注:
				</th>
				<td>
					${(role.remark)!""}
				</td>
			</tr>
			
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='list'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
