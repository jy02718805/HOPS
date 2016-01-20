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
	<form id="inputForm" action="pageresource_view"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>页面名称:
				</th>
				<td>
					${(pageResource.pageResourceName)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>资源地址（URL）:
				</th>
				<td>
					${(pageResource.pageUrl)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>创建时间:
				</th>
				<td>
					${(pageResource.createTime?string("yyyy-MM-dd HH:mm:ss"))!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>更新人:
				</th>
				<td>
					${(pageResource.updateName)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>更新时间:
				</th>
				<td>
					${(pageResource.updateTime?string("yyyy-MM-dd HH:mm:ss"))!""}	 
				</td>
			</tr>
			
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
