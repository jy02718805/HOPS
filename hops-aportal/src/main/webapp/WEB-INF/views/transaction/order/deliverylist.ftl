<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>发货列表</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<script language="javascript" type="text/javascript" src="${base}/template/admin/My97DatePicker/WdatePicker.js"></script>
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
<div class=path>
		<a href="">首页</a> »发货列表 <span>(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</SPAN>
</div>
<form id=listForm method=get action=deliveryRecordList>
	    <input id=page type=hidden name=page> 
<div class=bar style="HEIGHT: 65px;">
				<div class=buttonWrap>
					发货时间：<input class="Wdate" type="text" onClick="WdatePicker()" id="delivery_time" name="delivery_time" value=${(delivery_time)!""}>
					&nbsp;<input type="checkbox" onclick="enablePartTime(this)" <#if parttime!="" && parttime=="1">checked</#if> name="parttime" id="parttime" value="1"/>最近半小时 
					&nbsp;&nbsp;&nbsp;
					发货状态：<select name = "delivery_status" id = "delivery_status">
					 		    <option value=""></option>
								<option value="${Delivery.DELIVERY_STATUS_WAIT}"
									<#if delivery_status!="" && (delivery_status?number == Delivery.DELIVERY_STATUS_WAIT)>
										selected
									</#if>
								>等待发货</option>
								<option value="${Delivery.DELIVERY_STATUS_SENDING}"
									<#if delivery_status!="" && (delivery_status?number == Delivery.DELIVERY_STATUS_SENDING)>
										selected
									</#if>
								>发货采购中</option>
								<option value="${Delivery.DELIVERY_STATUS_SENDED}"
									<#if delivery_status!="" && (delivery_status?number == Delivery.DELIVERY_STATUS_SENDED)>
										selected
									</#if>
								>已经发货</option>
								<option value="${Delivery.DELIVERY_STATUS_FAIL}"
									<#if delivery_status!="" && (delivery_status?number == Delivery.DELIVERY_STATUS_FAIL)>
										selected
									</#if>
								>发货失败</option>
								<option value="${Delivery.DELIVERY_STATUS_SUCCESS}"
									<#if delivery_status!="" && (delivery_status?number == Delivery.DELIVERY_STATUS_SUCCESS)>
										selected
									</#if>
								>发货成功</option>
							   </select>
			       &nbsp;&nbsp;&nbsp;
				      运营商：<select name = "carrier_no" id = "carrier_no">
				      			<option value=""></option>
					                 <#list carrierlist  as carrier>
										<option value="${carrier.carrierNo}"
										  <#if carrier_no!="" && carrier_no == carrier.carrierNo>
											selected
										  </#if>
										>${carrier.carrierName}</option>
									 </#list>
								 </select>
				  &nbsp;&nbsp;&nbsp;
				      省 份：<select name = "province_id" id = "province_id">
				      			<option value=""></option>
					                 <#list provincelist  as province>
										<option  value="${province.provinceId}" 
										<#if province_id!="" && province_id == province.provinceId>
											selected
										  </#if>
										>${province.provinceName}</option>
									 </#list>
								 </select>
				  &nbsp;&nbsp;&nbsp;
				    供货商：<select name = "supplymerchant" id = "supplymerchant">
				      			<option value=""></option>
					                 <#list supplylist  as merchant>
										<option  value="${merchant.id}" 
											<#if supplymerchant!="" && supplymerchant?number == merchant.id>
											selected
										  </#if>
										>${merchant.merchantName}[${merchant.id}]</option>
									 </#list>
								 </select>
			      
					        
					        <br/>
					        <BR/>
						    代理商：<select name="agentmerchant" id="agentmerchant">
						      			<option value=""></option>
							                 <#list agentlist  as merchant>
							                 		
													<option  value="${merchant.id}" 
													<#if agentmerchant!="" && agentmerchant?number == merchant.id>
														selected
										  			</#if>
													>${merchant.merchantName}[${merchant.id}]</option>
											  </#list>
								  </select> 
							&nbsp;&nbsp;&nbsp;
							<input type="radio" name="numbertype" <#if numbertype!="" && numbertype == "supply_no">checked</#if> value="supply_no">供货商单号
							<input type="radio" name="numbertype" <#if numbertype!="" && numbertype == "agent_no">checked</#if> value="agent_no">代理商单号
							<input type="radio" name="numbertype" <#if numbertype!="" && numbertype == "order_no">checked</#if> value="order_no">订单编号
							<input type="text" name="numbervalue" value="${(numbervalue)!""}" />	 
							&nbsp;&nbsp;&nbsp; 
							 <input type="submit" class="button" value="查&nbsp;&nbsp;询" />	  
					
						   
						   
						   </div>
						  
								   
		 			</div>
</div>
<BR>
<table id=listtable class=list>
  <tbody>
	  <tr>
	  
	        <th><SPAN>发货编号</SPAN> </th>
		    <th><SPAN>收单时间</SPAN> </th>
		    <th><SPAN>订单编号</SPAN> </th>
		    <th><SPAN>运营商</SPAN> </th>
			<th><SPAN>省份</SPAN> </th>
			<th><SPAN>代理商</SPAN></th>
		    <th><SPAN>供货商</SPAN> </th>
		    <th><SPAN>供货商订单编号</SPAN></th>
		    <th><SPAN>开始时间</SPAN> </th>
		    <th><SPAN>结束时间</SPAN> </th>
		    <th><SPAN>充值账号</SPAN></th>
		  
		    <th><SPAN>进货折扣</SPAN></th>
		    <th><SPAN>成本</SPAN></th>
		    <th><SPAN>发货状态</SPAN></th>
		 
		   
	   </tr>
	
    <#list deliverylist as list>
	  <tr>
	  
		   <td>${(list.DELIVERY_ID)!""}</td>
		   <td>${(list.ORDER_REQUEST_TIME)!""}</td>
		   <td>${(list.ORDER_NO)!""}</td>
		   <td>${(list.CARRIERNO)!""}</td>
		   <td>${(list.PROVINCENO)!""}</td>
		   <td>${(list.AGENT_ID)!""}</td>
		   <td>${(list.SUPPY_ID)!""}</td>
		   <td>${(list.SUPPLY_ORDER_NO)!""}</td>
		   <td>${(list.DELIVERY_START_TIME)!""}</td>
		   <td>${(list.DELIVERY_FINISH_TIME)!""}</td>
		   <td>${(list.USER_CODE)!""}</td>
		   
		   <td>${(list.COST_DISCOUNT)!""}</td>
		   <td>${(list.COST_FEE)!""}</td>
		   <td>
		    <#if (list.DELIVERY_STATUS)??>
		        <#if list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_WAIT>
		        	等待发货
		        </#if>
		        
		         <#if list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_SENDING>
		        	发货采购中
		        </#if>
		        
		         <#if list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_SENDED>
		        	已经发货
		        </#if>
		        
		         <#if list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_FAIL>
		        	发货失败
		        </#if>
		        
		         <#if list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_SUCCESS>
		        	发货成功
		        </#if>
		     </#if>
		   </td>
	   </tr>
   </#list>
	</tbody>
</table>
  <div id="pager" style="float:right;"></div> <BR>
  <script type="text/javascript" language="javascript"> 
  
  
		$(document).ready(function() {
		
		   if($("#parttime")[0].checked){
		         $("#delivery_time").css("background", "#CDCDCD");
		   }else if($("#delivery_time").val()==""){
		   		$("#delivery_time").val(new Date().Format("yyyy-MM-dd"));
		   }
		  
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
		
		function enablePartTime(obj)
		{
			if(obj.checked){
			  $("#delivery_time").disabled=true;
			  $("#delivery_time").val("");
			  $("#delivery_time").css("background", "#CDCDCD");
			}else{
			  $("#delivery_time").disabled=false;
			  $("#delivery_time").css("background", "white");
			}
		}
		
		
</script> 
  </div>
  </form>
  </body>
  </html>

