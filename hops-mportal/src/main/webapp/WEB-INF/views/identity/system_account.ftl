<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>系统账户展示</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
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
<div class="path">
		<a href="/admin/common/index.jhtml">首页</a> &raquo; 系统账户展示
	</div>
	<form id="inputForm" action=""  method="post">
		<table class="input">
			<tr>
				<th>
					<b>系统中间利润账户:</b>
				</th>
				<td></td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>可用余额:
				</th>
				<td>
					${(middleAccount.availableBalance)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>不可用余额:
				</th>
				<td>
					${(middleAccount.unavailableBanlance)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>授信余额:
				</th>
				<td>
					${(middleAccount.creditableBanlance)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<b>系统纯利润账户:</b>
				</th>
				<td></td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>可用余额:
				</th>
				<td>
					${(profitAccount.availableBalance)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>不可用余额:
				</th>
				<td>
					${(profitAccount.unavailableBanlance)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>授信余额:
				</th>
				<td>
					${(profitAccount.creditableBanlance)!""}	 
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
