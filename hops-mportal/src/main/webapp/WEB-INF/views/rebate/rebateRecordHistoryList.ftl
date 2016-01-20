<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>返佣数据统计列表</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<SCRIPT type=text/javascript src="${base}/template/admin/js/yuecheng/rebaterule.js"></SCRIPT>
<script type="text/javascript" src="${base}/template/common/js/DatePicker/WdatePicker.js"></script>
<script src="${base}/template/admin/js/ArtDialog/artDialog.js?skin=chrome" type="text/javascript"></script>
<script src="${base}/template/admin/js/ArtDialog/plugins/iframeTools.js" type="text/javascript"></script>
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
<script language="javascript">
     
    function checkActurlRebate(amt)
    {
    	var acturlAmt=$("#actulAmount").val();
    	if(acturlAmt.trim()==""){
    		alert("实际返佣金额不能为空或空格！");
			return false;
		}
    	
    	if(!checkPoint(acturlAmt))
    	{
    		alert("实际返佣金额格式错误，请重新输入！");
			return false;
    	}
    	
    	if(parseFloat(amt) < parseFloat(acturlAmt)){
    		alert("实际返佣金额不能大于应返金额！");
			return false;
		}
    }
</script>
</head>
<body>
<form id=listForm method=get action=rebateRecordHistoryList>
			<input id=hasExpired type=hidden name=hasExpired> 
			<input id=page type=hidden name=page> 
		<div class="line_bar">
								 发生商户:<select name="merchantId" id="merchantId" class="select">
											<option value="" selected>--请选择--</option>	
											<#list rebateMerchants as list>
											<option value="${list.id}" 
											<#if (rebateRecordHistory.merchantId)??>
												<#if list.id==rebateRecordHistory.merchantId>selected</#if>
											</#if>
											>${list.merchantName}</option>
											</#list>
										</select>
								   返佣商户:<select name="rebateMerchantId" id="rebateMerchantId" class="select">
											<option value="" selected>--请选择--</option>	
											<#list merchants as list>
											<option value="${list.id}" 
											<#if (rebateRecordHistory.rebateMerchantId)??>
												<#if list.id==rebateRecordHistory.rebateMerchantId>selected</#if>
											</#if>
											>${list.merchantName}</option>
											</#list>
										</select>
										 清算时间:
											<input type="text" class="ipt" name="rebateStartDate" id="rebateStartDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" 
											value="<#if (rebateRecordHistory.rebateStartDate)??>${rebateRecordHistory.rebateStartDate}</#if>"/>
								                   	至
								            <input type="text" class="ipt" name="rebateEndDate" id="rebateEndDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"  
											value="<#if (rebateRecordHistory.rebateEndDate)??>${rebateRecordHistory.rebateEndDate}</#if>"/>
									<br/>返佣状态:<select name="rebateStatus" id="rebateStatus" class="select w80">
										<option value="" selected>请选择</option>
										<#if (rebateRecordHistory.rebateStatus)??>
											<#if rebateRecordHistory.rebateStatus=='1'>
												<option value="1" selected>未返</option>
												<option value="0" >已返</option>
											<#elseif rebateRecordHistory.rebateStatus=='0'>
												<option value="0" selected>已返</option>
												<option value="1" >未返</option>
											<#else>
												<option value="1" >未返</option>
												<option value="0" >已返</option>
											</#if>
										<#else>
											<option value="1" >未返</option>
											<option value="0" >已返</option>
										</#if>
									 </select>
									 余款状态:<select name="balanceStatus" id="balanceStatus" class="select w80">
										<option value="" selected>请选择</option>
										<#if (rebateRecordHistory.balanceStatus)??>
											<#if rebateRecordHistory.balanceStatus=='1'>
												<option value="1" selected>未收</option>
												<option value="0" >已收</option>
											<#elseif rebateRecordHistory.balanceStatus=='0'>
												<option value="0" selected>已收</option>
												<option value="1" >未收</option>
											<#else>
												<option value="1" >未收</option>
												<option value="0" >已收</option>
											</#if>
										<#else>
											<option value="1" >未收</option>
											<option value="0" >已收</option>
										</#if>
									 </select>
								<@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  </@shiro.user>
		</div>
<table id=listtable class=list>
  <tbody>
	  <tr>
	  
		    <th><SPAN>序号</SPAN> </th>
		    <th><SPAN>交易ID</SPAN> </th>
		    <th><SPAN>发生商户</SPAN> </th>
		    <th><SPAN>返佣商户</SPAN> </th>
		    <th><SPAN>商户类型</SPAN> </th>
		    <th><SPAN>返佣产品</SPAN> </th>
		    <th><SPAN>开始时间</SPAN> </th>
		    <th><SPAN>结束时间</SPAN> </th>
		    <th><SPAN>总交易量</SPAN> </th>
		    <th><SPAN>应返金额</SPAN> </th>
		    <th><SPAN>已返金额</SPAN> </th>
		    <th><SPAN>余额</SPAN> </th>
		    <th><SPAN>返佣方式</SPAN> </th>
		    <th><SPAN>返佣状态</SPAN> </th>
		    <th><SPAN>余款状态</SPAN></th>
		    <th><SPAN>更新人</SPAN> </th>
		    <th><SPAN>更新时间</SPAN> </th>
		    <th><SPAN>操作</SPAN></th>
		    
	   </tr>
   <#if (mlist?size>0)>
    <#list mlist as list>
	  <tr>
	  		<td>${(page-1)*pageSize+list_index+1}</td>
		    <td><a href="javascript:void(0);" 
		    onclick="clickId('${list.rebateRecordHistory.id}','${list.rebateRecordHistory.rebateStartDate?string("yyyy-MM-dd 00:00:00")}','${list.rebateRecordHistory.updateDate?string("yyyy-MM-dd 23:59:59")}');">
		    ${(list.rebateRecordHistory.id)!""}</a></td>
		    <td>${(list.merchantName)!""}</td>
		    <td>${(list.rebateMerchantName)!""}</td>
		    <td><#if list.rebateRecordHistory.merchantType =='AGENT'>
		    	代理商
		    	<#elseif list.rebateRecordHistory.merchantType =='SUPPLY'>
		    	供货商
		    	<#else>
		    	其他
		    	</#if></td>
		   
		   <#if (list.productNames)??>
		   <td title="全部：${(list.productNamesAlt)!""}" style="color:blue;">${(list.productNames)!""}</td>
		   <#else>
		   <td title="产品未开通或暂无产品"  style="color:blue;">/</td>
		   </#if>
		   <td>${list.rebateRecordHistory.rebateStartDate?string("yyyy-MM-dd")}</td>
		    <td>${list.rebateRecordHistory.rebateEndDate?string("yyyy-MM-dd")}</td>
		     <td>${(list.rebateRecordHistory.transactionVolume)!""}</td>
		     <td>${list.rebateRecordHistory.rebateAmt?string("0.0000")}</td>
		     <td>${list.rebateRecordHistory.actulAmount?string("0.0000")}</td>
		     <td>${list.rebateRecordHistory.balance?string("0.0000")}</td>
		    <td><#if list.rebateRecordHistory.rebateType ==0>
				             定比返佣
				         <#elseif list.rebateRecordHistory.rebateType ==1>
				   定额返佣         
				        </#if></td>
		    <#if list.rebateRecordHistory.rebateStatus =='1'>
				          <td class="tdgb">
				          未返
				         <#elseif list.rebateRecordHistory.rebateStatus =='0'>
				        <td>
				        已返     
				        </#if>
		    </td>
				        <#if list.rebateRecordHistory.balanceStatus =='1'>
				            <td class="tdgb">
				          未收
				         <#elseif list.rebateRecordHistory.balanceStatus =='0'>
				       <td>
				           已收
				        </#if>
		    </td>
		 
		    <td>${(list.rebateRecordHistory.updateUser)!""}</td>
		    <td>${list.rebateRecordHistory.updateDate?string("yyyy-MM-dd HH:mm:ss")}</td>
		    <td>
		    <#if list.rebateRecordHistory.rebateStatus =='1'>
		    <@shiro.hasPermission name="rebateHistory:del_show">
			    <a href="javascript:delData(${list.rebateRecordHistory.id});">[删除]</a>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="rebateHistory:actrulRebate_show">
			    <a href="javascript:acturlRebate(${list.rebateRecordHistory.id});">[确认返佣]</a>
			</@shiro.hasPermission>
			<#elseif list.rebateRecordHistory.rebateStatus=='0'>
				 <#if list.rebateRecordHistory.balanceStatus =='1'>
				 	<#if list.rebateRecordHistory.merchantType=='AGENT'>
				 	<@shiro.hasPermission name="rebateHistory:getBalance_show">
       					<a href="javascript:getBalance(${list.rebateRecordHistory.id});">[余款收回]</a>
       				</@shiro.hasPermission>
       				<#elseif list.rebateRecordHistory.merchantType=='SUPPLY'>
       				<@shiro.hasPermission name="rebateHistory:getRebateBalance_show">
       					<a href="javascript:getBalance(${list.rebateRecordHistory.id});">[佣金收回]</a>
       				</@shiro.hasPermission>
       				</#if>
	   			 </#if>
		    </#if>
		     </td>
	   </tr>
</#list>
<#else>
			<tr>
				<td colspan="18">没数据</td>
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
		
		function delData(id)
		{
			if (confirm("确认要删除该数据吗?")) {
				window.location.href="deleteRebateRecordHistory?id="+id;
		    }
		}
		
		function acturlRebate(id)
		{
		 	var url="acturlRebate?id="+id;
		    var html = $.ajax({ url: url, async: false }).responseText;
            var title = "返佣信息确认";
            art.dialog({
                content: html,
                title: title,
                lock: true,
                ok: false
            });
		}
		
		function getBalance(id)
		{
			var url="getBalance?id="+id;
		    var html = $.ajax({ url: url, async: false }).responseText;
            var title = "余款或佣金收回信息确认";
            art.dialog({
                content: html,
                title: title,
                lock: true,
                ok: false
            });
		}
		
		function clickId(transactionId,beginDate,endDate){
			window.location.href="${base}/accountHistory/accountTransactionLogList?transactionNo="+transactionId+"&beginDate="+beginDate+"&endDate="+endDate;
		}
</script> 
  </form>
  </BODY>
  </HTML>

