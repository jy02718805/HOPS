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
<SCRIPT type=text/javascript src="${base}/template/admin/js/json2.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/yuecheng/rebaterule.js"></SCRIPT>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script  type="text/javascript">
 
		$(document).ready(function(){
			start('${tree}',"${(merchantOne.id)!"0"}","${(merchantOne.merchantName)!""}","${(merchantTwo.id)!"0"}","${(merchantTwo.merchantName)!""}","0");
			
    		checkForm();
		});
		
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="addRebateRule"  method="post">
		<table class="input" id="merchant">
			<tr>
				<th><span class="requiredField">*</span>发生商户:</th>
				<td>
				<#if merchant.id != 0 >
						<select name="merchantId" id = "merchantId" class="select">
							<option value="${(merchant.id)!""}">${(merchant.merchantName)!""}</option>
						</select>
						<span style="color:blue;">
							<#if merchant.merchantLevel=="0">
								一级商户
							<#elseif merchant.merchantLevel=="1">
							             二级商户
							<#elseif merchant.merchantLevel=="2">
							    三级商户
							<#else>
							</#if>
						</span>
					<#else>
						<input type="text" id="merchantName" name="merchantName" class="ipt" maxlength="200" />&nbsp;&nbsp;
						<input type="button" class="button" value="查询" onclick="getMerchant()" />
						<select name="merchantId" id = "merchantId" style="display:none" onchange="getParentMerchant(this.value)" class="select">
						<option value="0">--请选择--</option>
						</select>
						<div style="color:blue;" id="merchantlevel"></div>
					</#if>
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
					<span class="requiredField">*</span>清算周期:
				</th>
				<td>
					<select name="rebateTimeType" id="rebateTimeType" class="select">
					<!--	
						<option value="1" >按月返</option>
						<option value="2" >按季返</option>
						<option value="3" >按年返</option> 
					-->
						<option value="4" selected>按天返</option>
					</select>
			   </td>
			</tr>
			</table>
		<table class="input" id="merchantRebate0">
			<tr>
				<th>
					<b>本级返佣:</b>
				</th>
				<td>
					<div id="merchantName0"><#if merchant.id != 0 >${(merchant.merchantName)!""}</#if>(
					<#if merchant.MerchantType??>
						<#if merchant.merchantType ==MerchantType.AGENT>
				    	代理商
				    	<#elseif merchant.merchantType ==MerchantType.SUPPLY>
				    	供货商
				    	<#else>
				    	其他
				    	</#if>
			    	</#if>)</div>
				</td>
			</tr>	
			<tr>
				<th>
					是否设置：
				</th>
				<td>
					<INPUT id="status0" name="status0" type="radio" value="0" onclick="showMerchantRebate('0','0')" >是
					<INPUT id="status0" name="status0" type="radio" value="1" onclick="showMerchantRebate('1','0')" checked >否
				</td>
			</tr>	
			<tr>
				<th>
					返佣方式：
				</th>
				<td>
					<INPUT id="rebateType0" name="rebateType0" type="radio" value="0" checked  onclick="showMerchantRebateType('0','0')" >定比返佣
					<INPUT id="rebateType0" name="rebateType0" type="radio" value="1"   onclick="showMerchantRebateType('1','0')">定额返佣
				</td>
			</tr>	
			<tr>
				<th style="vertical-align:top">
					返佣规则：
				</th>
				<td>
					<table id="merchantRebateDiscount0" class="input" disabled="disabled">
						<tr>
							<td>交易量下限</td>
							<td>交易量上限</td>
							<td><div id="rebateDiscount0">定比返佣（比例为：0~1之间，单位：%）</div></td>
							<td>操作</td>
						</tr>
						<tr id="tr0">
							<td><input type="text" id="tradingVolumeLow0" name="tradingVolumeLow0" class="ipt" maxlength="200" value="0"  disabled="disabled"/></td>
							<td><input type="text" id="tradingVolumeHigh0" name="tradingVolumeHigh0" class="ipt" maxlength="200" value="无穷" onkeyup="changeVolumeLow('tr0',0)" disabled="disabled"/></td>
							<td><input type="text" id="discount0" name="discount0" class="ipt" maxlength="200" value="0"  disabled="disabled"/></td>
							<td><input type="button" class="button" id="add0" name="add0" value="添加" onclick="addMerchantRebate('tr0',0)"  disabled="disabled"/></td>
						</tr>
					</table>
				</td>
			</tr>	
		</table>
		
		<table class="input" id="merchantRebate1" style="display:none;">
			<tr>
				<th>
					<b>一级返佣:</b>
				</th>
				<td>
				<input type="hidden" name="merchantId1" id="merchantId1" value="0"/>
					<div id="merchantName1"></div>
				</td>
			</tr>	
			<tr>
				<th>
					是否返佣：
				</th>
				<td>
					<INPUT id="status1" name="status1"  type="radio" value="0"  onclick="showMerchantRebate('0','1')" >是
					<INPUT id="status1" name="status1"  type="radio" value="1"  onclick="showMerchantRebate('1','1')"  checked >否
				</td>
			</tr>	
			<tr>
				<th>
					返佣方式：
				</th>
				<td>
					<INPUT id="rebateType1" name="rebateType1"  type="radio" value="0"  onclick="showMerchantRebateType('0','1')" checked  >定比返佣
					<INPUT id="rebateType1" name="rebateType1"  type="radio" value="1"  onclick="showMerchantRebateType('1','1')" >定额返佣
				</td>
			</tr>		
			<tr>
				<th style="vertical-align:top">
					返佣规则：
				</th>
				<td>
					<table id="merchantRebateDiscount1" class="input" disabled="disabled">
						<tr>
							<td>交易量下限</td>
							<td>交易量上限</td>
							<td><div id="rebateDiscount1">定比返佣（%）</div></td>
							<td>操作</td>
						</tr>
						<tr id="tr1">
							<td><input type="text" id="tradingVolumeLow1" name="tradingVolumeLow1" class="ipt" maxlength="200" value="0" disabled="disabled"/></td>
							<td><input type="text" id="tradingVolumeHigh1" name="tradingVolumeHigh1" class="ipt" maxlength="200" value="无穷" onkeyup="changeVolumeLow('tr1',1)" disabled="disabled"/></td>
							<td><input type="text" id="discount1" name="discount1" class="ipt" maxlength="200" value="0" disabled="disabled"/></td>
							<td><input type="button" class="button" id="add1" name="add1" value="添加" onclick="addMerchantRebate('tr1',1)"  disabled="disabled"/></td>
						</tr>
					</table>
				</td>
			</tr>	
		</table>
		<table class="input" id="merchantRebate2" style="display:none;">
			<tr>
				<th>
					<b>二级返佣:</b>
				</th>
				<td>
				<input type="hidden" name="merchantId2" id="merchantId2"  value="0"/>
					<div id="merchantName2"></div>
				</td>
			</tr>	
			<tr>
				<th>
					是否返佣：
				</th>
				<td>
					<INPUT id="status2" name="status2"  type="radio" value="0"  onclick="showMerchantRebate('0','2')"  >是
					<INPUT id="status2" name="status2"  type="radio" value="1"   onclick="showMerchantRebate('1','2')" checked >否
				</td>
			</tr>	
			<tr>
				<th>
					返佣方式：
				</th>
				<td>
					<INPUT id="rebateType2" name="rebateType2"  type="radio" value="0"  onclick="showMerchantRebateType('0','2')"checked  >定比返佣
					<INPUT id="rebateType2" name="rebateType2"  type="radio" value="1"   onclick="showMerchantRebateType('1','2')">定额返佣
				</td>
			</tr>		
			<tr>
				<th style="vertical-align:top">
					返佣规则：
				</th>
				<td>
					<table id="merchantRebateDiscount2" class="input" disabled="disabled">
						<tr>
							<td>交易量下限</td>
							<td>交易量上限</td>
							<td><div id="rebateDiscount2">定比返佣（%）</div></td>
							<td>操作</td>
						</tr>
						<tr id="tr2">
							<td><input type="text" id="tradingVolumeLow2" name="tradingVolumeLow2" class="ipt" maxlength="200" value="0" disabled="disabled"/></td>
							<td><input type="text" id="tradingVolumeHigh2" name="tradingVolumeHigh2" class="ipt" maxlength="200" value="无穷" onkeyup="changeVolumeLow('tr2',2)" disabled="disabled"/></td>
							<td><input type="text" id="discount2" name="discount2" class="ipt" maxlength="200" value="0" disabled="disabled"/></td>
							<td><input type="button" class="button" id="add2" name="add2" value="添加" onclick="addMerchantRebate('tr2',2)"  disabled="disabled"/></td>
						</tr>
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
					<input id="addRebateRule" type="button" class="button" value="确&nbsp;&nbsp;定" onclick="addRR()" />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
				</td>
			</tr>	
		</table>
	</form>
</body>

</HTML>
