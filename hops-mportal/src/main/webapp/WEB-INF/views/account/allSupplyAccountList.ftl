<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script language="javascript">
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

<BODY>
<FORM id=listForm method='post' action=allSupplyAccountList>
		<INPUT id=page type=hidden name=page> 
		<INPUT id=hasExpired type=hidden name=hasExpired> 
		<DIV class=line_bar>
				商户名称:<input type='text' name="merchantName" id="merchantName" value="${merchantName}" class="ipt"/>
				账户类型:<select name="accountTypeId" id="accountTypeId"  class="select">
							<option value="" selected=selected>--请选择--</option>
							<#list accountType as list>
							<option value="${list.accountTypeId}" <#if accountTypeId==list.accountTypeId>selected=selected</#if>>${list.accountTypeName}</option>
							</#list>
						</select>
				<@shiro.user>
				  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
				</@shiro.user>
		</DIV>
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
  	<TH><SPAN>序号</SPAN> </TH>
    <TH><SPAN>商户</SPAN> </TH>
    <TH><SPAN>账户ID</SPAN> </TH>
    <TH><SPAN>账户类型名称</SPAN> </TH>
    <TH><SPAN>详情</SPAN> </TH>
    <TH><SPAN>币种</SPAN> </TH>
    <TH><SPAN>账户状态</SPAN> </TH>
    <TH><SPAN>备注</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
    
    <#if (accountlist?size>0)>
    		<#list accountlist as account>
				<tr>
					<td>${(page-1)*pageSize+account_index+1}</td>
					<td>${(account.merchant.merchantName)!""}</td>
					<td>${(account.accountId)!""}</td>
					<td>${(account.accountType.accountTypeName)!""}</td>
					
					<#if account.accountType.typeModel??>
				        <#if account.accountType.typeModel == "FUNDS">
				             	<td>
						   		<p>可用余额(元)：${account.availableBalance?string("0.0000")} <br/>
									不可用余额(元)：${account.unavailableBanlance?string("0.0000")} <br/>
									 授信余额(元)：${account.creditableBanlance?string("0.0000")}
							    </td>
				        <#else>
				        	    <td>总数量：${(account.cardNum)!""}  <br/>
				   				总价值：${(account.balance)!""}</td>
				        </#if>
				    </#if>
					
					<td>${(account.accountType.ccy)!""}</td><!--// 币种-->
					<#if account.status =='1'>
						<td>
							 已启用
						</td>
					<#elseif account.status =='-1'>
						<td class="tdgb">
							 未启用
						</td>
					</#if>
					<td>${(account.rmk)!""}</td>
					<td>
					<@shiro.hasPermission name="supplyAccount:view">
						<a href="${base}/account/toShowAccount?pageType=list&typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${account.merchant.id}&identityName=${account.merchant.merchantName}&accountTypeId=${account.accountType.accountTypeId}">[详情]</a>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="supplyAccount:block_show">
					    <a href="${base}/account/updateAccountStatus?pageType=list&typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${account.merchant.id}&identityName=${account.merchant.merchantName}&accountTypeId=${account.accountType.accountTypeId}">					    
						    	<#if account.status =='1'>
						    	[锁定]
						    	<#elseif account.status =='-1'>
						        [解锁]
						    	</#if>
						</a>
					</@shiro.hasPermission>
						<#if account.status =='1'>
							<@shiro.hasPermission name="supplyAccount:debitCurrency_show">
								<a href="${base}/account/toCurrencyAccountDebit?pageType=list&typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${account.merchant.id}&identityName=${account.merchant.merchantName}&accountTypeId=${(account.accountType.accountTypeId)!""}">[加款]</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="supplyAccount:creditCurrency_show">
								<a href="${base}/account/toCurrencyAccountCredit?pageType=list&typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${account.merchant.id}&identityName=${account.merchant.merchantName}&accountTypeId=${(account.accountType.accountTypeId)!""}">[减款]</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="supplyAccount:debitCreditable_show">
								<a href="${base}/account/toAddCreditableBanlance?pageType=list&typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${account.merchant.id}&identityName=${account.merchant.merchantName}&accountTypeId=${(account.accountType.accountTypeId)!""}">[授信加款]</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="supplyAccount:creditCreditable_show">
								<a href="${base}/account/toSubCreditableBanlance?pageType=list&typeModel=${account.accountType.typeModel}&accountId=${(account.accountId)!""}&identityId=${account.merchant.id}&identityName=${account.merchant.merchantName}&accountTypeId=${(account.accountType.accountTypeId)!""}">[授信减款]</a>
							</@shiro.hasPermission>
						</#if>
					<@shiro.hasPermission name="supplyAccount:view_logs">
						<a href="${base}/accountHistory/accountBalanceHistoryList?accountTypeId=${account.accountType.accountTypeId}&accountId=${(account.accountId)!""}">[查看日志]</a>
					</@shiro.hasPermission>	
					</td>
				</tr>
			</#list>
			<#else>
			<tr>
				<td colspan="9">没数据</td>
			</tr>
	</#if>
</TBODY></TABLE>
<div class="line_pages">
<div style="float:left;">
  	显示条数：
  	<select name="pageSize" id="pageSize" >
		<option value="10" <#if pageSize==10>selected=selected</#if>>10</option>
		<option value="20" <#if pageSize==20>selected=selected</#if>>20</option>
		<option value="30" <#if pageSize==30>selected=selected</#if>>30</option>
		<option value="50" <#if pageSize==50>selected=selected</#if>>50</option>
		<option value="100"<#if pageSize==100>selected=selected</#if>>100</option>	
		<option value="500" <#if pageSize==500>selected=selected</#if>>500</option>
		<option value="1000"<#if pageSize==1000>selected=selected</#if>>1000</option>
	</select>&nbsp; 条
  </div>
<div id="pager" style="float:right;"></div>
<div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
</div>
  <script type="text/javascript" language="javascript"> 
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
		}); 
		PageClick = function(pageclickednumber) { 
			  $("#pager").pager({ 
			       pagenumber: pageclickednumber,
				   pagecount: ${pagetotal}, 
				   buttonClickCallback: PageClick 
			});
			
			$("#page").val(pageclickednumber);
		   $("#listForm").submit();
		
		}
</script> 
</FORM></BODY></HTML>

