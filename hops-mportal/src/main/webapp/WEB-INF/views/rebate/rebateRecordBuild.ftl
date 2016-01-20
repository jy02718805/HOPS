<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>生成返佣数据</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#setting number_format="#">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<link rel="stylesheet" href="${base}/template/common/css/demo.css" type="text/css">
<link rel="stylesheet" href="${base}/template/common/css/zTreeStyle.css" type="text/css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/json2.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/yuecheng/rebaterule.js"></SCRIPT>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" language="javascript">

</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="generateData"  method="post">
		<table class="input" id="merchant">
			<tr>
				<th>发生商户:</th>
				<td style="width:220px;">
				<input type="hidden"  id="ids" name="ids" class="ipt" maxlength="200" value="${(ids)!""}" />
				<input type="hidden"  id="merchantId" name="merchantId" class="ipt" maxlength="200" value="${(rebateRecordAll.rebateRecord.merchantId)!""}" />
				${(rebateRecordAll.merchantName)!""}
			   </td>
				<th>返佣商户:</th>
				<td style="width:220px;">
				<input type="hidden"  id="rebateMerchantId" name="rebateMerchantId" class="ipt" maxlength="200" value="${(rebateRecordAll.rebateRecord.rebateMerchantId)!""}" />
				${(rebateRecordAll.rebateMerchantName)!""}					
			   </td>
			</tr>
			<tr>
				<th>返佣产品:</th>
				<td>
				<input type="hidden"  id="rebateProductId" name="rebateProductId" class="ipt" maxlength="200" value="${(rebateRecordAll.rebateRecord.rebateProductId)!""}" />
				${(rebateRecordAll.productNames)!""} <a title="${(rebateRecordAll.productNamesAlt)!""}" href="javascript:void(0);">>></a>	
			   </td>
				<th>商户类型:</th>
				<td>
				<input type="hidden"  id="merchantType" name="merchantType" class="ipt" maxlength="200" value="${(rebateRecordAll.rebateRecord.merchantType)!""}" />
					<#if rebateRecordAll.rebateRecord.merchantType=='AGENT'>
					代理商
					<#else>
					供货商
					</#if>
			   </td>
			</tr>
			<tr>
				<th>清算周期:</th>
				<td>
				<input type="hidden"  id="beginDate" name="beginDate" class="ipt" maxlength="200" value="${(beginDate)!""}" />
				<input type="hidden"  id="endDate" name="endDate" class="ipt" maxlength="200" value="${(endDate)!""}" />
				
				${(beginDate)!""}~${(endDate)!""}
			   </td>
				<th>总交易量:</th>
				<td>
				<input type="hidden"  id="transactionVolume" name="transactionVolume" class="ipt" maxlength="200" value="${(rebateRecordAll.rebateRecord.transactionVolume)!""}" />
					${(rebateRecordAll.rebateRecord.transactionVolume)!""}
			   </td>
			</tr>
			<tr>
				<th>合计金额:</th>
				<td>
				<input type="hidden"  id="rebateAmt" name="rebateAmt" class="ipt" maxlength="200" value="${(rebateRecordAll.rebateRecord.rebateAmt?string("0.0000"))!""}" />
				${(rebateRecordAll.rebateRecord.rebateAmt?string("0.0000"))!""}
			   </td>
				<th>返佣方式:</th>
				<td>
				<input type="hidden"  id="rebateType" name="rebateType" class="ipt" maxlength="200" value="${(rebateRecordAll.rebateRecord.rebateType)!""}" />
					<#if rebateRecordAll.rebateRecord.rebateType==0>
					定比返
					<#elseif rebateRecordAll.rebateRecord.rebateType==1>
					定额返
					<#else>
					未知
					</#if>
			   </td>
			</tr>
		</table>
		
		<table class="input" >	
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="确&nbsp;&nbsp;定" />
				</td>
			</tr>
		</table>
	</form>
</body>	
<script type="text/javascript" language="javascript"> 
	function backRebate(){
		window.location.href="${base}/rebateRecord/rebateRecordList";
	}
</script>
</HTML>
