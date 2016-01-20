<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>账户详情</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" language="javascript">
<script>
	
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="${base}/account/doCurrencyAccountCredit"  method="post">
		<input type="hidden" id="identityId" name="identityId" value="${identityId!""}"/>
		<input type="hidden" id="availableBalance" name="availableBalance"/>
		<table class="input">
		<tr>
				<th>
					<span class="requiredField">*</span>所属商户:
				</th>
				<td>
					<#if !ca.merchant??>
					<input type="text" id="merchantName" name="merchantName" disabled="disabled"  class="ipt" maxlength="200" value="${ca.sp.spName!""}"/>	 
					</#if>
					
					<#if ca.merchant??>
					<input type="text" id="merchantName" name="merchantName" disabled="disabled"  class="ipt" maxlength="200" value="${ca.merchant.merchantName!""}"/>	 
					</#if>
				</td>
			</tr>
		<tr >
			<tr>
				<th>
					<span class="requiredField">*</span>账户ID:
				</th>
				<td>
					<input type="text" id="accountId" name="accountId" disabled="disabled" class="ipt" maxlength="200" value="${ca.accountId!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>账户类型:
				</th>
				<td>
					<input type="text" id="identityName" name="identityName" disabled="disabled" class="ipt" maxlength="200" value="${ca.accountType.accountTypeName!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>账户状态:
				</th>
				<td>
					<#if ca.status =='1'>
						 已启用
					<#elseif ca.status =='-1'>
						 未启用
					</#if>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>可用余额(元):
				</th>
				<td>
					<input type="text" id="accountId" name="accountId" disabled="disabled" class="ipt" maxlength="200" value="${availableBalance}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>不可用余额(元):
				</th>
				<td>
					<input type="text" id="accountId" name="accountId" disabled="disabled" class="ipt" maxlength="200" value="${unavailableBanlance}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>授信余额(元):
				</th>
				<td>
					<input type="text" id="accountId" name="accountId" disabled="disabled" class="ipt" maxlength="200" value="${creditableBanlance}"/>	 
					
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<#if ca.status =='1'>
				<!--	<input type="button" class="button" value="加&nbsp;&nbsp;款" onclick="location.href='${base}/account/toCurrencyAccountDebit?accountId=${ca.accountId}&identityId=${identityId!""}&identityName=${identityName!""}'"/> -->
				<!--	<input type="button" class="button" value="减&nbsp;&nbsp;款" onclick="location.href='${base}/account/toCurrencyAccountCredit?accountId=${ca.accountId}&identityId=${identityId!""}&identityName=${identityName!""}'"/> -->
					</#if>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
