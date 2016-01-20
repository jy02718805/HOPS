<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<SCRIPT type=text/javascript>
</SCRIPT>
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
<FORM id=listForm method=get action=list>
<div class="line_bar">
		<INPUT id=page type=hidden name=page> 
		<INPUT id=hasExpired type=hidden name=hasExpired> 
								供货商:<select name="merchantId" id="merchantId" class="select">
											<option value="" selected=selected>--请选择--</option>	
											<#list upMerchant as list>
												<option value="${list.id}" <#if merchantId != 0 && list.id == merchantId>selected=selected</#if>>${list.merchantName}</option>
											</#list>				
										</select>
								服务类型:	<select name="serviceType" id="serviceType" class="select w100">
										<option value="" selected=selected>-请选择-</option>
										<option value="send_order_response" <#if serviceType=="send_order_response">selected=selected</#if>>send_order_response</option>
										<option value="query_order_response" <#if serviceType=="query_order_response">selected=selected</#if>>query_order_response</option>
									</select>	
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  <input type="button" class="button" onclick="self.location.href='add?merchantId=${merchantId!""}&serviceType=${serviceType!""}&menuid=${menuid!""}';"  value="添加" />
</div>
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
   
    <TH><SPAN>序号</SPAN> </TH>
    <TH><SPAN>供货商编号</SPAN> </TH>
    <TH><SPAN>服务类型</SPAN> </TH>
    <TH><SPAN>错误码</SPAN> </TH>
    <TH><SPAN>错误码描述</SPAN> </TH>
    <TH><SPAN>系统订单状态</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
    
		<#if (mlist?size>0)>
    <#list mlist as list>
		  <TR>
		  <td>${(page-1)*pageSize+list_index+1}</td>
			    <TD>${(list.merchantId)!""}</TD>
			    <TD>${(list.serviceType)!""}</TD>
			    <TD>${(list.errorCode)!""}</TD>
			    <TD>${(list.merchantOrderStatus)!""}</TD>
			    <TD>
			    <#if list.serviceType == "send_order_response">
			    	<#if list.orderStatus == 1>重试
			    	<#elseif list.orderStatus == 2>重绑
			    	<#elseif list.orderStatus == 3>强制关闭
					<#elseif list.orderStatus == 4>正常成功
			    	<#elseif list.orderStatus == 5>正常失败
			    	</#if>
			    <#else>
			    	<#if list.orderStatus == 0>待付款
			    	<#elseif list.orderStatus == 1>待发货
			    	<#elseif list.orderStatus == 2>发货中
			    	<#elseif list.orderStatus == 3>成功
					<#elseif list.orderStatus == 4>失败
			    	<#elseif list.orderStatus == 5>部分失败
			    	</#if>
			    </#if>
			    </TD>
			    <TD>
 
				</TD>
		   </TR>
</#list>
		<#else>
			<tr>
				<td colspan="7">没数据</td>
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

