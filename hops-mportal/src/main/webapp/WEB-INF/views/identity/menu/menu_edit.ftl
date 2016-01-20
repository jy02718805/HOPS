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
	<form id="inputForm" action="edit"  method="post">
		<table class="input">
		<input type="hidden"  id="menuId" name="menuId" class="ipt" maxlength="200" value="${(menu.menuId)!""}" />
		<#if parentid!="">
			<tr>
				<th>
					<span class="requiredField">*</span>上级菜单:
				</th>
				<td>
				<input type="hidden"  id="parentMenuId" name="parentMenuId" class="ipt" maxlength="200" value="${(parentid)!""}" />
					${(parentname)!""}
				</td>
			</tr>
		<#else>
			<input type="hidden"  id="parentMenuId" name="parentMenuId" class="ipt" maxlength="200" value="0" />
		</#if>
			<tr>
				<th>
					<span class="requiredField">*</span>菜单名称:
				</th>
				<td>
					<input type="text" id="menuName" name="menuName" class="ipt" maxlength="20" value="${(menu.menuName)!""}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>后台类型:
				</th>
				<td>
					<select name="portaltype"  id="portaltype" class="select">
						<option value="">请选择</option>
						<#list portaltypelist as list>
								<#if menu.portaltype?exists>
									<option value="${list}" <#if menu.portaltype==list>selected=selected</#if>>${list}</option>
								<#else>
									<option value="${list}">${list}</option>
								</#if>

						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>显示顺序:
				</th>
				<td>
					<input type="text" id="displayOrder" name="displayOrder" class="ipt" maxlength="5" value="${(menu.displayOrder)!""}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>页面资源:
				</th>
				<td>
					<select name="pageResource.resourceId" id="pageResource.resourceId" class="select">
						<option value="${pageResource.resourceId}" selected="selected">${pageResource.pageUrl}
						</option>
					<#list pageResourcelist as list>
						<option value="${list.resourceId}">
							${list.pageUrl}
						</option>
					</#list>
				</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>状态:
				</th>
				<td>
					<INPUT id="status" name="status"  type="radio" value="0" <#if menu.status=="0">checked</#if>  >启用
					<INPUT id="status" name="status"  type="radio" value="1" <#if menu.status=="1">checked</#if>  >禁用
				</td>
			</tr>
			<tr>
				<th>
					功能描述:
				</th>
				<td>
					<textarea id="remark" name="remark" class="text" maxlength="200" >${(menu.remark)!""}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="editMenu();"/>
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
