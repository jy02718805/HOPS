<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加商户</TITLE>
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
<SCRIPT type=text/javascript src="${base}/template/admin/js/yuecheng/rebaterule.js"></SCRIPT>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script  type="text/javascript">

		$(document).ready(function(){
			start('${tree}',"0","","0","","1");
			
    		checkForm();
		});
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="editRebateRule"  method="post">
		<table class="input" id="merchant">
			<tr>
				<th><span class="requiredField">*</span>发生商户:</th>
				<td>
				<input type="hidden" name="rebateRuleId" id="rebateRuleId" class="ipt" value="${(rebateRule.rebateRule.rebateRuleId)!""}"/>
				${(rebateRule.merchantName)!""}
			   </td>
			</tr>
			<tr>
				<th style="vertical-align:top"><span class="requiredField">*</span>产品:</th>
				<td>
					<div class="content_wrap">
						<div class="zTreeDemoBackground left">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
						<input type="hidden" name="changeNodes" id="changeNodes" class="ipt" />
					</div>
			   </td>
			</tr>
		   <tr>
				<th>
					<span class="requiredField">*</span>返佣周期:
				</th>
				<td>
					<select name="rebateTimeType" id="rebateTimeType" class="select">
						<option value="4" <#if rebateRule.rebateRule.rebateTimeType==4>selected</#if>>按天返</option>
					</select>
			   </td>
			</tr>
			</table>
		<table class="input" id="merchantRebate0">
			<tr>
				<th>
					<b>返佣商户:</b>
				</th>
				<td>
					<div id="merchantName0">${(rebateRule.rebateMerchantName)!""}</div>
				</td>
			</tr>	
			<tr>
				<th>
					返佣状态：
				</th>
				<td>
					<INPUT id="status0" name="status0"  type="radio" value="0" <#if rebateRule.rebateRule.status=="0">checked</#if>>开启
					<INPUT id="status0" name="status0"  type="radio" value="1" <#if rebateRule.rebateRule.status=="1">checked</#if> >关闭
				</td>
			</tr>	
			<tr>
				<th>
					返佣方式：
				</th>
				<td>
					<INPUT id="rebateType0" name="rebateType0"  type="radio" value="0" <#if rebateRule.rebateRule.rebateType==0>checked</#if>  onclick="showMerchantRebateType('0','0')" >定比返佣(比例为：0~1之间，单位：%)
					<INPUT id="rebateType0" name="rebateType0"  type="radio" value="1" <#if rebateRule.rebateRule.rebateType==1>checked</#if>  onclick="showMerchantRebateType('1','0')">定额返佣
				</td>
			</tr>	
			<tr>
				<th style="vertical-align:top">
					返佣规则：
				</th>
				<td>
					<table id="merchantRebateDiscount0" class="input" >
						<tr>
							<td>交易量下限</td>
							<td>交易量上限</td>
							<td><div id="rebateDiscount0"><#if rebateRule.rebateRule.rebateType==0>定比返佣（%）<#else>定额返佣</#if></div></td>
							<td>操作</td>
						</tr>
						<#list tradingVolumeList as list>
						<tr id="tv${(list.rebateTradingVolumeID)!""}">
							<td><input type="text" id="tradingVolumeLow0" name="tradingVolumeLow0" class="ipt" maxlength="200" value="${list.tradingVolumeLow}"  disabled="disabled" /></td>
							<td><input type="text" id="tradingVolumeHigh0" name="tradingVolumeHigh0" class="ipt" maxlength="200"  onkeyup="changeVolumeLow('tv${list.rebateTradingVolumeID}',0)" value="<#if list.tradingVolumeHigh==999999999999999999>无穷<#else>${list.tradingVolumeHigh}</#if>"  /></td>
							<td><input type="text" id="discount0" name="discount0" class="ipt" maxlength="200" value="<#if rebateRule.rebateRule.rebateType==0>${list.discount?string("0.0000")}<#else>${list.discount?string("0.0000")}</#if>"  /></td>
							<td><input type="button" class="button" id="add0" name="add0" value="添加" onclick="addMerchantRebate('tv${list.rebateTradingVolumeID}',0)" /><a onclick="deleteMerchantRebate('tv${list.rebateTradingVolumeID}','0')">[删除]</a></td>
						</tr>
						</#list>
					</table>
				</td>
			</tr>	
		</table>
		<table class="input" >	
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="editRR()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
