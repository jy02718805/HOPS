<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>确认返佣</TITLE>
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
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" language="javascript">

</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="acturlRebate"  method="post" onsubmit="return checkActurlRebate('${(rebateRecordHistory.rebateRecordHistory.rebateAmt?string("0.0000"))!""}');">
		<table class="input">
			<tr>
				<th>发生商户:</th>
				<td style="width:220px;">
				<input type="hidden"  id="id" name="id" class="ipt" maxlength="200" value="${(rebateRecordHistory.rebateRecordHistory.id)!""}" />
				${(rebateRecordHistory.merchantName)!""}
			   </td>
				<th>返佣商户:</th>
				<td style="width:220px;">
				${(rebateRecordHistory.rebateMerchantName)!""}					
			   </td>
			</tr>
			<tr>
				<th>返佣产品:</th>
				<td>
				${(rebateRecordHistory.productNames)!""} <a title="${(rebateRecordHistory.productNamesAlt)!""}" href="javascript:void(0);">>></a>	
			   </td>
				<th>商户类型:</th>
				<td>
					<#if rebateRecordHistory.rebateRecordHistory.merchantType=='AGENT'>
					代理商
					<#else>
					供货商
					</#if>
			   </td>
			</tr>
			<tr>
				<th>清算周期:</th>
				<td>
				${rebateRecordHistory.rebateRecordHistory.rebateStartDate?string("yyyy-MM-dd")}~${rebateRecordHistory.rebateRecordHistory.rebateEndDate?string("yyyy-MM-dd")}
			   </td>
				<th>总交易量:</th>
				<td>
					${(rebateRecordHistory.rebateRecordHistory.transactionVolume)!""}
			   </td>
			</tr>
			<tr>
				<th>应返金额:</th>
				<td>
				${(rebateRecordHistory.rebateRecordHistory.rebateAmt?string("0.0000"))!""}
			   </td>
				<th>返佣方式:</th>
				<td>
					<#if rebateRecordHistory.rebateRecordHistory.rebateType==0>
					定比返
					<#elseif rebateRecordHistory.rebateRecordHistory.rebateType==1>
					定额返
					<#else>
					未知
					</#if>
			   </td>
			</tr>
			<tr>
				<th>实际金额:</th>
				<td>
				<input type="text"  id="actulAmount" name="actulAmount" class="ipt w80" maxlength="200" value="${(rebateRecordHistory.rebateRecordHistory.rebateAmt?string("0.0000"))!""}"/>
			   </td>
				<th></th>
				<td>
				
			   </td>
			</tr>
		</table>
		<table class="input" >	
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="确定返佣"/>
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
