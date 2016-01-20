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
<script type="text/javascript" src="${base}/template/common/js/DateUtil.js"></script>
<script type="text/javascript" src="${base}/template/common/js/provinceCity.js"></script>
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
<form id="listForm" method="get" action="reissueDeliveryRecordList" onsubmit="return check();">
	    <input id='page' type='hidden' name='page'/> 
				<div class="line_bar">
	发货时间: <select id='changeDate' name='changeDate' onChange='changeSetDate(this);' class="select w80">
		<option value=''>请选择</option>
		<option value='m' <#if changeDate=="m">selected='selected'</#if>>最近十分钟</option>
		<option value='h' <#if changeDate=="h">selected='selected'</#if>>最近一小时</option>
		<option value='d' <#if changeDate=="d">selected='selected'</#if>>今天</option>
		<option value='Y' <#if changeDate=="Y">selected='selected'</#if>>前一天</option>
	</select>
	
					开始时间:<input id="beginDate" name="beginDate" type="text" 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-{%M-3}-%d',maxDate:'#F{$dp.$D(\'endDate\')|| \'%y-%M-%d\'}'});changeDateSelect();" 
						value="${beginDate}" class="ipt w150"/>
					结束时间:<input id="endDate" name="endDate" type="text" 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginDate\') || \'%y-{%M-3}-%d\'}',maxDate:'%y-%M-%d'});changeDateSelect();"
							value="${endDate}" 
								class="ipt w150"/>
								<br/>
	业务类型: <select id='businessType' name='businessType'  class="select w80" onChange='changeBusinessType(this);'>
    <option value='' <#if businessType=="">selected='selected'</#if>>请选择</option>
    <option value='0' <#if businessType=="0">selected='selected'</#if>>话费</option>
    <option value='1' <#if businessType=="1">selected='selected'</#if>>流量</option>
    </select>
					发货状态：<select name = "delivery_status" id = "delivery_status" class="select w80">
					 		    <option value="">-请选择-</option>
								<option value="${Delivery.DELIVERY_STATUS_WAIT}"
									<#if delivery_status!="" && (delivery_status?number == Delivery.DELIVERY_STATUS_WAIT)>
										selected
									</#if>
								>等待发货</option>
								<option value="${Delivery.DELIVERY_STATUS_SENDING}"
									<#if delivery_status!="" && (delivery_status?number == Delivery.DELIVERY_STATUS_SENDING)>
										selected
									</#if>
								>准备发货</option>
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
				      运营商：<select name = "carrier_no" id = "carrier_no" class="select w80">
				      			<option value="">-请选择-</option>
					                 <#list carrierlist  as carrier>
										<option value="${carrier.carrierNo}"
										  <#if carrier_no!="" && carrier_no == carrier.carrierNo>
											selected
										  </#if>
										>${carrier.carrierName}</option>
									 </#list>
								 </select>
				      省 份：<select name = "province_id" id = "province_id" class="select w80">
				      			<option value="">-请选择-</option>
					                 <#list provincelist  as province>
										<option  value="${province.provinceId}" 
										<#if province_id!="" && province_id == province.provinceId>
											selected
										  </#if>
										>${province.provinceName}</option>
									 </#list>
								 </select>
								 
		
				    供货商：<select name = "supplymerchant" id = "supplymerchant" class="select w100">
				      			<option value="">-请选择-</option>
					                 <#list supplylist  as merchant>
										<option  value="${merchant.id}" 
											<#if supplymerchant!="" && supplymerchant?number == merchant.id>
											selected
										  </#if>
										>${merchant.merchantName}[${merchant.id}]</option>
									 </#list>
								 </select>		
								 
								   代理商：<select name="agentmerchant" id="agentmerchant" class="select w120">
						      			<option value="">-请选择-</option>
							                 <#list agentlist  as merchant>
							                 		
													<option  value="${merchant.id}" 
													<#if agentmerchant!="" && agentmerchant?number == merchant.id>
														selected
										  			</#if>
													>${merchant.merchantName}[${merchant.id}]</option>
											  </#list>
								  </select> 
								 
								 <br/>
						  
								  面值:<input type="text" id="parValue" name="parValue"  maxlength="5" value="${(parValue)!""}" class="ipt w80"/>
								电话号码:<input id="usercode" name="usercode" type="text"  maxlength="11" value="${(usercode)!""}" class="ipt w100"  onkeyup="value=value.replace(/[^\d]/g,'')"/>
								系统订单号 :<input id="orderNo" name="orderNo" type="text" value="${orderNo}" class="ipt w80" onkeyup="value=value.replace(/[^\d]/g,'')"/>
							<input type="radio" name="numbertype" id="numbertype_supply" <#if numbertype!="" && numbertype == "supply_no">checked</#if> value="supply_no">供货商单号
							<input type="radio" name="numbertype" id="numbertype_agent" <#if numbertype!="" && numbertype == "agent_no">checked</#if> value="agent_no">代理商单号
							<input type="text" id = "numbervalue" name="numbervalue" value="${(numbervalue)!""}"  class="ipt"/>	 
							 <input type="button" onclick='queryDeliverys();' class="button" value="查询" />	  <input type="button" class="button" onclick="manualAuditorders();"  value="补发查询" />  
							 <br />
			<span> 
					
					
 
							  
</div>
<table id=listtable class=list>
  <tbody>
	  <tr>
	  	<TH><input type='checkbox' id='checkorder' name='checkorder' onclick='checkOrders(this);'/></TH>
	  		<th><SPAN>序号</SPAN> </th>
	        <th><SPAN>发货编号</SPAN> </th>
		    <th><SPAN>代理商订单号</SPAN></th>
		    <th><SPAN>系统订单号</SPAN> </th>
			<th><SPAN>代理商</SPAN></th>
		    <th><SPAN>供货商</SPAN> </th>
		    <th><SPAN>充值账号</SPAN></th>
		    <th><SPAN>运营商</SPAN> </th>
			<th><SPAN>省份</SPAN> </th>
			<th><SPAN>面值</SPAN> </th>
		    <th><SPAN>进货折扣</SPAN></th>
		    <th><SPAN>成本</SPAN></th>
		    <th><SPAN>发货状态</SPAN></th>
		     <th><SPAN>关闭原因</SPAN> </th>
		    <th><SPAN>收单时间</SPAN> </th>
		    <th><SPAN>结束时间</SPAN> </th>
		    
		 
		   
	   </tr>
	</tbody> 
	<tbody class="listTable">
	<#if (deliverylist?size>0)>
			<#list deliverylist as list>
			  <tr>
			   	   <td><input type='checkbox' name='manualAuditorder' value='${(list.DELIVERY_ID)!""}' onclick='unCheckOrders(this);'/></td>
			  	   <td>${(page-1)*pageSize+list_index+1}</td>
				   <td>${(list.DELIVERY_ID)!""}</td>
				   <td>${(list.SUPPLY_ORDER_NO)!""}</td>
				   <td>
							<a href="${base}/transaction/showOrderDetail?orderNo=${(list.ORDER_NO)!""}">${(list.ORDER_NO)!""}</a>
				   
				   </td>
				   <td>${(list.AGENT_NAME)!""}</td>
				   <td>${(list.SUPPY_NAME)!""}</td>
				   <td>${(list.USER_CODE)!""}</td>
				   <td><span class="carrierInfo_${list_index+1}">${(list.CARRIERNO)!""}</span></td>
				   <td><span class="p_${list_index+1}">${(list.PROVINCENO)!""}</span></td>
				   <td>
				      <#if list.BUSINESS_TYPE==0>
				   ${(list.PRODUCT_FACE)!""} 元
				    <#else>
				    ${(list.PAR_VALUE)!""} M
				   	</#if>
				   
				   </td>
				   <td>${(list.COST_DISCOUNT?string("0.0000"))!""}</td>
				   <td>${(list.COST_FEE?string("0.0000"))!""}</td>
				    <#if (list.DELIVERY_STATUS)??>
				        <#if list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_WAIT>
				       <td>
							            	等待发货
				         <#elseif list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_SENDING>
				       <td>
							            	准备发货
				         <#elseif list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_SENDED>
				        <td>
							           	已经发货
				         <#elseif list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_FAIL>
				        <td class="tdgb">
							           	发货失败
				         <#elseif list.DELIVERY_STATUS == Delivery.DELIVERY_STATUS_SUCCESS>
				        <td>
							           	发货成功
						 <#else>
						 <td>
							           	未知
				        </#if>
					<#else>
					<td>
							未知
				     </#if>
				   </td>
				   <td>
				   		 <#if list.QUERY_FLAG==1>
				   		  待查询
				   		 <#elseif list.QUERY_FLAG==2>
				   		  查询中
				   		 <#elseif list.QUERY_FLAG==3>
				   		  查询结束
				   		 <#elseif list.QUERY_FLAG==4>
				   		  需要进行手工触发查询
				   		 </#if>
					</td>
				   <td>${(list.ORDER_REQUEST_TIME)!""}</td>
				   <td>${(list.DELIVERY_FINISH_TIME)!""}</td>
			   </tr>
		   </#list>
	<#else>
			<tr>
				<td colspan="17">没数据</td>
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
		
		   if($("#delivery_time").val()==""){
		   		$("#delivery_time").val(new Date().Format("yyyy-MM-dd"));
		   }
		  
			enablePartTime();
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
		
		function deliveryRecrod(deliveryId)
		{
			var temp='false';
 			var height='500';
 			var width='800';
 			var iTop = (window.screen.availHeight-30-height)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-width)/2;
			window.open ('${base}/transaction/deliveryresult?deliveryId='+deliveryId,'newwindow','height='+height+',width='+width+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
		}
		function enablePartTime(obj)
		{
			var now= new Date();
	  		var year=now.getFullYear();
	  		var month=now.getMonth()+1;
	  		var date=now.getDate();
			var deliveryTime=year+"-"+month+"-"+date;
	  		if ($("#delivery_time").val() == deliveryTime) { $("#parttime").parent().show().next('label').show(); }
	        else { $("#parttime").removeAttr("checked").parent().hide().next('label').hide(); }
		}
		function check()
		{
			var beginDate=$("#beginDate").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginDate").focus(); 
	    		return false;
			}
			var endDate=$("#endDate").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endDate").focus(); 
	    		return false;
			}
		}
		
		
		function queryDeliverys(){
			var statisticsOrder=1;
			$('input[name="statisticsOrder"]:checked').each(function(){
				$("#statisticsOrder").val(statisticsOrder);
			});
			
			$("#listForm").submit();
		}
		
		function changeSetDate(obj){
			var changeDate= $(obj).val();
			var date = new Date();
			$('#endDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
			if(changeDate=='m'){
				date.setMinutes(date.getMinutes()-10);
			}else if(changeDate=='h'){
				date.setHours(date.getHours()-1);
			}else if(changeDate=='d'){
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
			}else if (changeDate == 'Y')
			{
				date.setDate(date.getDate()-1);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				$('#endDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
			}
			$('#beginDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
		}
		
		
		$(document).ready(function(){
			 $('#statisticsOrder').click(function(){
				   var bl = this.checked;	
				   if(bl){
					 statisticsOrderInfo();
				   }
			});
			   
			   $('input[name="statisticsOrder"]:checked').each(function(){
				  statisticsOrderInfo();
			   });
		});
		
		function statisticsOrderInfo(){
			var beginDate=$("#beginDate").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginDate").focus(); 
	    		return false;
			}
			var endDate=$("#endDate").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endDate").focus(); 
	    		return false;
			}
			var orderNo=$("#orderNo").val();
			if(orderNo!=""&&orderNo!=null)
			{
				var reg=/^\d+$/;
		    	if(!reg.test(orderNo)){
		    		alert("系统订单号格式错误，只能是数字！");
		    		document.getElementById("orderNo").focus(); 
		    		return false;
		    	}
			}
			var numbertype_param ='';
			if($('#numbertype_supply').is(':checked')) {
				numbertype_param = $("#numbertype_supply").val();
			}
			if($('#numbertype_agent').is(':checked')) {
			numbertype_param = $("#numbertype_agent").val();
			}
				$.ajax({
						url:"${base}/transaction/reissueDeliveryRecordList?carrierInfo="
						+$("#carrier_no").val()
						+"&province="+$("#province_id").val()
						+"&parValue="+$("#parValue").val()
						+"&supplyMerchant="+$("#supplymerchant").val()
						+"&agentMerchant="+$("#agentmerchant").val()
						+"&beginDate="+$("#beginDate").val()
						+"&endDate="+$("#endDate").val()
						+"&deliveryStatus="+$("#delivery_status").val()
						+"&orderNo="+$("#orderNo").val()
						+"&businessType="+$("#businessType").val()
						+"&usercode="+$("#usercode").val()
						+"&numbertype="+numbertype_param
						+"&numbervalue="+$("#numbervalue").val() ,
						type: "post",
				        data: null,
				        async: false,
						success:function(data) {
							var obj =JSON.parse(data);
							if(obj.PRODUCTFACE!=null && obj.PRODUCTFACE!='undefined'){
								$(".msg1").text(obj.PRODUCTFACE);
							}else{
								$(".msg1").text("0");
							}
							
							if(obj.ORDERSUCCESSFEE!=null && obj.ORDERSUCCESSFEE!='undefined'){
								$(".msg2").text(obj.ORDERSUCCESSFEE);
							}else{
								$(".msg2").text("0");
							}
							
							if(obj.COSTFEE!=null && obj.COSTFEE!='undefined'){
								$(".msg3").text(obj.COSTFEE);
							}else{
								$(".msg3").text("0");
							}
							
							if(obj.SALESFEE!=null && obj.SALESFEE!='undefined'){
								$(".msg4").text(obj.SALESFEE);
							}else{
								$(".msg4").text("0");
							}
						}
				});
		}
		
		function changeDateSelect(){
			$('#changeDate').val('');			
		}
		
		 
			function checkOrders(allCheckbox){
				var bl = allCheckbox.checked;	
				
				$('input[name="manualAuditorder"]').each(function(){
					this.checked=bl;
				});
			}
			
			function unCheckOrders(cbox){
				var bl = cbox.checked;	
				var flag=true;
				$('input[name="manualAuditorder"]').each(function(){
					if(this.checked!=bl)
					{
						flag=false;
					}
				});
				if(flag||(!flag&&!bl)){
					document.getElementById('checkorder').checked=bl;
				}
			}
			
	
			function manualAuditorders(){
				var r=document.getElementsByName("manualAuditorder");
			    var result="";
			    var k=0;
			    var supplyMerchantId = $("#supplyMerchants").val();
			    for(var i=0;i<r.length;i++){
			         if(r[i].checked){
			         result=result+r[i].value+"|";
			         k++;
			       }
			    }		
				if(k>500)
				{
					alert("因数据量比较大，可能会导致系统卡死，请选择不超过500条的数据，谢谢。");
			        return false;
				}
			    if(result!=""&&result!=null){
			    	if (!confirm("确认要批量补发查单吗?【共"+k+"条】")) {
				        return false;
				    }
			    	
			    	window.location.href="${base}/transaction/checkReissueDelivery?deliveryId="+result;
				}else{
			 	   alert("请选择需要批量审核的订单！");
			 	   return false;
			    }
			}
			
		
</script> 
  </form>
  </body>
  </html>

