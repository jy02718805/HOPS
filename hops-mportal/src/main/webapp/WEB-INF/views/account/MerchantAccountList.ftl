<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>商户列表</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" language="javascript">
		function addMerchantAccount(){
			window.location.href="${base}/account/addAccount?identityId=${merchant.id}&identityName=${merchant.merchantName}";
		}
</script> 
<style>
#pager ul.pages { 
display:block; 
border:none; 
text-transform:uppercase; 
font-size:10px; 
margin:10px 0 50px; 
padding:0; 
} 
#pager ul.pages li { 
list-style:none; 
float:left; 
border:1px solid #ccc; 
text-decoration:none; 
margin:0 5px 0 0; 
padding:5px; 
} 
#pager ul.pages li:hover { 
border:1px solid #003f7e; 
} 
#pager ul.pages li.pgEmpty { 
border:1px solid #eee; 
color:#eee; 
} 
#pager ul.pages li.pgCurrent { 
border:1px solid #003f7e; 
color:#000; 
font-weight:700; 
background-color:#eee; 
}
</style>
<body>
<!--<#if merchant.identityStatus.status=='0'>
&nbsp;<input type="button" class="button"  value="创建商户账户" onclick="addMerchantAccount()"/>
</#if>-->
<div class="mg10"></div>
<form id="listForm" method="get" action="${base}/account/addAccount">
	<input id="identityId" type="hidden" class="ipt" name="identityId" value="${merchant.id}">
	<input id="identityName" type="hidden" class="ipt" name="identityName" value="${merchant.merchantName}">
	<table id=listTable class=list>
		<tbody>
			<tr>
				<th><SPAN>商户名称</SPAN> </th>
				<th><SPAN>账户ID</SPAN> </th>
				<th><SPAN>账户类型名称</SPAN> </th>
				<th><SPAN>详情</SPAN> </th>
				<th><SPAN>币种</SPAN> </th>
				<th><SPAN>账户状态</SPAN> </th>
				<th><SPAN>备注</SPAN> </th>
				<th><SPAN>操作</SPAN> </th>
			</tr>
			<#if (accounts?size>0)>
			<#list accounts as account>
				<tr>
					<td>${(merchant.merchantName)!""}</td>
					<td>${(account.accountId)!""}</td>
					<td>${(account.accountType.accountTypeName)!""}</td>
					<#if account.accountType.typeModel=="FUNDS">
				   		<td>
				   		<p>AB(元)：${account.availableBalance?string("0.0000")} <br/>
							 UB(元)：${account.unavailableBanlance?string("0.0000")} <br/>
							 CB(元)：${account.creditableBanlance?string("0.0000")}</td>
					<#else>
				   		<td>总数量：${(account.cardNum)!""}  <br/>
				   		总价值：${(account.balance)!""}</td>
					</#if>
					<td>${(account.accountType.ccy)!""}</td><!--// 币种-->
						<#if account.status =='1'>
						<td>
				          	 已启用
						<#elseif account.status =='-1'>
						<td class="tdgb">
				          	 未启用
						</#if>
					</td>
					<td>${(account.rmk)!""}</td>
					<td>
						
						<#if account.accountType.accountTypeId == SYSTEM_DEBIT>
							<@shiro.hasPermission name="merchantAccount:view">
								<a href="${base}/account/toShowAccount?typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${merchant.id}&identityName=${merchant.merchantName}">[详情]</a>
							</@shiro.hasPermission>
						<#elseif account.accountType.accountTypeId == SYSTEM_PROFIT>
							<@shiro.hasPermission name="merchantAccount:view">
								<a href="${base}/account/toShowAccount?typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${merchant.id}&identityName=${merchant.merchantName}">[详情]</a>
							</@shiro.hasPermission>
						<#else>
							<@shiro.hasPermission name="merchantAccount:view">
								<a href="${base}/account/toShowAccount?typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${merchant.id}&identityName=${merchant.merchantName}&accountTypeId=${account.accountType.accountTypeId}">[详情]</a>
							</@shiro.hasPermission>
							<#if merchant.identityStatus.status=="0">
							<@shiro.hasPermission name="merchantAccount:block_show">
								|
								<a href="${base}/account/updateAccountStatus?typeModel=${account.accountType.typeModel}&accountTypeId=${account.accountType.accountTypeId}&accountId=${(account.accountId)!""}&identityId=${merchant.id}&identityName=${merchant.merchantName}">
									<#if account.status =='1'>
								    	[锁定]
								    <#elseif account.status =='-1'>
								        [解锁]
								    </#if>
								</a>
							</@shiro.hasPermission>
								<#if account.status =='1'>
								<@shiro.hasPermission name="merchantAccount:debitCurrency_show">
									|<a href="${base}/account/toCurrencyAccountDebit?accountId=${(account.accountId)!""}&identityId=${(merchant.id)!""}&identityName=${(merchant.merchantName)!""}&accountTypeId=${(account.accountType.accountTypeId)!""}&typeModel=${account.accountType.typeModel}">[加款]</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="merchantAccount:creditCurrency_show">
									|<a href="${base}/account/toCurrencyAccountCredit?accountId=${(account.accountId)!""}&identityId=${(merchant.id)!""}&identityName=${(merchant.merchantName)!""}&accountTypeId=${(account.accountType.accountTypeId)!""}&typeModel=${account.accountType.typeModel}">[减款]</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="merchantAccount:debitCreditable_show">
									|<a href="${base}/account/toAddCreditableBanlance?accountId=${(account.accountId)!""}&identityId=${(merchant.id)!""}&identityName=${(merchant.merchantName)!""}&accountTypeId=${(account.accountType.accountTypeId)!""}&typeModel=${account.accountType.typeModel}">[授信加款]</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="merchantAccount:creditCreditable_show">
									|<a href="${base}/account/toSubCreditableBanlance?accountId=${(account.accountId)!""}&identityId=${(merchant.id)!""}&identityName=${(merchant.merchantName)!""}&accountTypeId=${(account.accountType.accountTypeId)!""}&typeModel=${account.accountType.typeModel}">[授信减款]</a>
								</@shiro.hasPermission>
								</#if>
							</#if>
						</#if>
					</td>
				</tr>
			</#list>
			<#else>
			<tr>
				<td colspan="7">没数据</td>
			</tr>
	</#if>
			
		</tbody>
	</table>
	<div style="float:right;padding:10px;">
		<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
	</div>
</form>
</body>
</html>

