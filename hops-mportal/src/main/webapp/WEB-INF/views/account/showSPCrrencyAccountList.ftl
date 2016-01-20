<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>系统类型账户列表</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
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
	<form id="listForm" method="post" action="${base}/account/showSPCrrencyAccountList">
	<input id='page' type='hidden' name='page'/> 
	<div class=line_bar>
	<label>账户种类:</label>
	<select id='typeModel' name='typeModel' class="select w100">
	<option value="">请选择</option>
			<option value="FUNDS"
			
			 <#if typeModel=='FUNDS'>
						selected='selected'
						</#if>
			>资金账户</option>
			<option value="CARD"
			 <#if typeModel=='CARD'>
						selected='selected'
						</#if>
			
			>卡账户</option>
	</select>
	
		<label>账户类型:</label>
		<select id='accountTypeId' name='accountTypeId' class="select">
			<option value="">请选择</option>
			<#list atLIst as at>
				<option value="${at.accountTypeId}" <#if (accountTypeId == at.accountTypeId+"")!> selected </#if>>${at.accountTypeName!""}</option>
			</#list>
		</select>
			<label>用户名称:</label>
			<input type="text" id='merchantName' name="merchantName" value="${merchantName}" class="ipt w100"/>
			
			<label>关系:</label>
			<select id='relation' name='relation' class="select w80">
			<option value="">请选择</option>
			<option value="own" 
			<#if relation=='own'>
						selected='selected'
						</#if>
			>所属</option> 
			<option value="use" 
			<#if relation=='use'>
						selected='selected'
						</#if>
			>使用</option>
			</select>
			
		<@shiro.user>
			&nbsp;<input type="button" class="button" value="查询" onclick="queryCurrencyAccount();"/>
		</@shiro.user>
		&nbsp;<input type="button" class="button" value="创建外部银行卡账户" onclick="createExternalAccount();"/>
	</div>
	<table id='listTable' class='list'>
		<tbody>
			<tr>
				<th><SPAN>序号</SPAN> </th>
				<th><SPAN>账户ID</SPAN> </th>
				<th><SPAN>用户名称</SPAN> </th>
				<th><SPAN>账户类型</SPAN> </th>
				<th><SPAN>可用余额(元)</SPAN> </th>
				<th><SPAN>不可用余额(元)</SPAN> </th>
				<th><SPAN>授信余额(元)</SPAN> </th>
				<th><SPAN>关系描述</SPAN> </th>
				<th><SPAN>备注</SPAN> </th>
				<th><SPAN>操作</SPAN> </th>
			</tr>
			<#if (mlist?size>0)>
				<#list mlist as currencyAccount>
					<tr>
						<td>${(page-1)*pageSize+currencyAccount_index+1}</td>
						<td>${currencyAccount.ACCOUNTID!""}</td>
						<td>
						${currencyAccount.MERCHANTNAME?default(sp.spName)}
						</td>
						<td>${currencyAccount.ACCOUNTTYPENAME!"--"}</td>
						<td>${currencyAccount.AVAILABLEBALANCE!"0"}</td>
						<td>${currencyAccount.UNAVAILABLEBANLANCE!"0"}</td>
						<td>${currencyAccount.CREDITABLEBANLANCE!"0"}</td>
						<td>
						<#if currencyAccount.RELATION=='use'>
						使用
						</#if>
						<#if currencyAccount.RELATION=='own'>
						所属
						</#if>
						</td>
						<td>${currencyAccount.RMK!""}</td>
						<td>
						<@shiro.hasPermission name="sysAccount:view">
							<a href='${base}/account/toShowSpAccount?accountId=${currencyAccount.ACCOUNTID!""}&relation=${currencyAccount.RELATION!""}&accountTypeId=${currencyAccount.ACCOUNTTYPEID}'>[详情]</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="sysAccount:view_logs">
						<a href="${base}/accountHistory/accountBalanceHistoryList?accountTypeId=${currencyAccount.ACCOUNTTYPEID}&accountId=${(currencyAccount.ACCOUNTID)!""}">[查看日志]</a>
					</@shiro.hasPermission>	
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
			
			 <script type="text/javascript" language="javascript"> 
			
		function queryCurrencyAccount(){
				$("#listForm").submit();
			}
			
		function createExternalAccount(){
			window.location.href="${base}/account/toCreateExternalAccount";
		}
	</script> 
	</form>
</body>

</HTML>
