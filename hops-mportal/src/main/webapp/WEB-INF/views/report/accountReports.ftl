<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>每日账户统计</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type="text/javascript" src="${base}/template/common/js/DatePicker/WdatePicker.js"></script>
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript"
	src="${base}/template/common/js/jquery.easyui.min.js"></script>
	<LINK rel='stylesheet' type='text/css'
	href="${base}/template/admin/css/easyui.css">
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
	<form id='listForm' method='post' action="${base}/report/accountReports">
		<input id='page' type=hidden name='page'> 
		<input type="hidden"  id='reportTypeName' name='reportTypeName'  value="everyDayAccout" />
		<div class="line_bar"> 
			开始时间:<input id="beginTime" name="beginTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${accountReportVo.beginTime!""}" class='ipt w80' />
			结束时间:<input id="endTime" name="endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${accountReportVo.endTime!""}" class='ipt w80'/>
			报表类型:<select  id='reportType' name='reportType' onchange="submitReportType();" class="select w100">
			<option value="">请选择</option>
				<#list reportTypeList as rt>
					<option value="${rt.reportTypeId!""}"  <#if (rt.reportTypeId+""==(accountReportVo.reportType!))>selected='selected'</#if>>${rt.reportFileName}</option>
				</#list>
			</select>
			
			<div style="float:right;">
			<@shiro.user>
			<input type="button" class="button" onclick="queryAccoutList();"  value="查询" class="button"/>
			</@shiro.user>
			<@shiro.hasPermission name="accountReport:export_show">
			<input type="button" class="button" onclick="exportAccout();"  value="导出Excel" class="button"/>
			</@shiro.hasPermission>
			</div>
			</div>
			
			<div class="line_bar"> 
			<#list reportTermList as reprtterm>
			<#if reprtterm.reportMetadata.metadataField=="ACCOUNT_ID">
			账户编号:<input type="text"  id='accountId' name='accountId'  value="${accountReportVo.accountId!""}" class='ipt w80'/>
			</#if>
			</#list>
			<#list reportTermList as reprtterm>
			<#if reprtterm.reportMetadata.metadataField=="ACCOUNT_TYPE_ID">
			账户类型:<select  id='accountTypeId' name='accountTypeId' class="select"/>
			<option value="">请选择</option>
			<#list atList as at>
				<option value="${at.accountTypeId!""}"  <#if (at.accountTypeId+""==(accountReportVo.accountTypeId!))>selected='selected'</#if>>${at.accountTypeName}</option>
			</#list>
			</select>
			</#if>
			</#list>
			<#list reportTermList as reprtterm>
			<#if reprtterm.reportMetadata.metadataField=="IDENTITY_NAME">
					用户类型:<select  id='identityType' name='identityType' class="select" onChange='onChangeIdentityType();'>
			<option value="">请选择</option>
				<option value="SP"  <#if (accountReportVo.identityType!)=="SP">selected='selected'</#if>>系统</option>
				<option value="CUSTOMER"  <#if (accountReportVo.identityType!)=="CUSTOMER">selected='selected'</#if>>客户</option>
				<option value="MERCHANT"  <#if (accountReportVo.identityType!)=="MERCHANT">selected='selected'</#if>>商户</option>
				<option value="OPERATOR"  <#if (accountReportVo.identityType!)=="OPERATOR">selected='selected'</#if>>操作员</option>
			</select>
			用户名称:<input type="text" class="easyui-combobox" id='identityName' name='identityName'  value="${accountReportVo.identityName!}" class="ipt" data-options="cache:true,valueField:'id',textField:'text'"/>
	
			</#if>
			</#list>
		</div>
	<table  id='listtable' class='list'>
	<#if (accountReportVo.reportType!)=="">
	<thead>
		<tr>
			<th>统计时间</th>
			<th>所属商户名称</th>
			<th>上期余额</th>
			<th>本期入账</th>
			<th>本期支出</th>
			<th>本期余额</th>
		</tr>
		</thead>
		<tbody>
		<tr>
		<td colspan="6">没数据</td>
		</tr>
		</tbody>
	<#else>
	${accountReportHtml!""}
	</#if>
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
  <BR>
	 
	 
	 
	 <script type="text/javascript" language="javascript"> 
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
			getUsers();
		}); 
		PageClick = function(pageclickednumber) { 
			  $("#pager").pager({ 
			       pagenumber: pageclickednumber,
				   pagecount: ${pagetotal}, 
				   buttonClickCallback: PageClick 
			});
			
			$("#page").val(pageclickednumber);
		   $("#listForm").attr("action","${base}/report/accountReports").submit();
		
		}
		
		
		function queryAccoutList(){
			var beginDate=$("#beginTime").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginTime").focus(); 
	    		return false;
			}
			var endDate=$("#endTime").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endTime").focus(); 
	    		return false;
			}
			var s = $('#identityName').combobox('getText');
			$('#identityName').combobox('setValue',s);
			 $("#listForm").attr("action","${base}/report/accountReports").submit();
		}
		
		function exportAccout(){
			var beginDate=$("#beginTime").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginTime").focus(); 
	    		return false;
			}
			var endDate=$("#endTime").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endTime").focus(); 
	    		return false;
			}
			var reportType = $("#reportType").val();
			if(reportType.length <= 0){
				alert("请选择 报表类型！");
				return;
			}
			var s = $('#identityName').combobox('getText');
			$('#identityName').combobox('setValue',s);
			$("#listForm").attr("action","${base}/report/accountExport").submit();
		}
		
		function submitReportType(){
			$("#listForm").attr("action","${base}/report/accountReports").submit();
		}
		
		function getUsers(){
			
			var identityType=$('#identityType').val();
			var url='${base}/report/getUsers?identityType='+identityType;
			$('#identityName').combobox('reload', url);
			
		}
		
		function onChangeIdentityType(){
		
			$('#identityName').combobox('clear');
			getUsers();
			
		}
		</script>
		</form>
</body>

</HTML>
