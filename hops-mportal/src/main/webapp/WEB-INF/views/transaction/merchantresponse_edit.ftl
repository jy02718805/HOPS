<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加商户分组</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script language="javascript">
$().ready( function() {
	getStatus('${merchantResponse.serviceType}');
});

function getStatus(type){
	document.getElementById("orderStatus").options.length=0; 
	document.getElementById("orderStatus").options.add(new Option('--请选择--',''));
	if(type=="send_order_response")
	{	
		document.getElementById("orderStatus").options.add(new Option('重试','1'));
		document.getElementById("orderStatus").options.add(new Option('重绑','2'));
		document.getElementById("orderStatus").options.add(new Option('强制关闭','3'));
		document.getElementById("orderStatus").options.add(new Option('正常成功','4'));
		document.getElementById("orderStatus").options.add(new Option('正常失败','5'));
	}else
	{					
		document.getElementById("orderStatus").options.add(new Option('待付款','0'));
		document.getElementById("orderStatus").options.add(new Option('待发货','1'));
		document.getElementById("orderStatus").options.add(new Option('发货中','2'));
		document.getElementById("orderStatus").options.add(new Option('成功','3'));
		document.getElementById("orderStatus").options.add(new Option('失败','4'));
		document.getElementById("orderStatus").options.add(new Option('部分失败','5'));
	} 
	var select = document.getElementById("orderStatus");   
	for(var i=0; i<select.options.length; i++){   
	    if(select.options[i].value == "${merchantResponse.orderStatus}"){   
	        select.options[i].selected = true;   
	        break;   
	    }   
	}  
}

$(document).ready(function(){ 
   getStatus($("#serviceType").val());
});

function editMerchantResponse(){
	var merchantId = $("#merchantId_select").val();
	var serviceType = $("#serviceType_select").val();
	var orderStatus = $("#orderStatus").val();
	
	$("#merchantId").val(merchantId);
	$("#serviceType").val(serviceType);
	
	if(orderStatus.length <= 0){
		alert("请选择状态再提交！");
		return;
	}
	$('#inputForm').submit();
}
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="merchantresponse_edit"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>供货商编号:
				</th>
				<td>
					<input type="hidden" id="id" name="id" class="text" maxlength="200" value="${(merchantResponse.id)!""}"/>
					<input type="hidden" id="merchantId" name="merchantId" />
					<select name="merchantId_select" id="merchantId_select" disabled="disabled">
							<option value="${merchant.id}" >${merchant.merchantName}</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>服务类型:
				</th>
				<td>
					<input type="hidden" id="serviceType" name="serviceType" />
					<select name="serviceType_select" id="serviceType_select" onchange="getStatus(this.value)" disabled="disabled">
						<option value="send_order_response" <#if merchantResponse.serviceType=="send_order_response">selected=selected</#if>>send_order_response</option>
						<option value="query_order_response" <#if merchantResponse.serviceType=="query_order_response">selected=selected</#if>>query_order_response</option>
					</select>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>错误码:
				</th>
				<td>
					<input type="text" id="errorCode" name="errorCode" class="text" maxlength="200" value="${(merchantResponse.errorCode)!""}" />	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>错误码描述:
				</th>
				<td>
					<input type="text" id="merchantOrderStatus" name="merchantOrderStatus" class="text" maxlength="200"  value="${(merchantResponse.merchantOrderStatus)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>系统订单状态:
				</th>
				<td>
					<select name="orderStatus" id="orderStatus" >
						<option value="" selected=selected>-请选择-</option>
					</select>	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" onclick="editMerchantResponse()" class="button" value="确&nbsp;&nbsp;定" />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
