<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加角色</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/yuecheng/assignexclude.js"></SCRIPT>


<script language="javascript">

	function checkProducts(allCheckbox){
		var bl = allCheckbox.checked;	
		
		$('input[name="objectMerchantId"]').each(function(){
			this.checked=bl;
		});
	}
	
	function unCheckProducts(cbox){
		var bl = cbox.checked;	
		var flag=true;
		$('input[name="objectMerchantId"]').each(function(){
			if(this.checked!=bl)
			{
				flag=false;
			}
		});
		if(flag||(!flag&&!bl)){
			document.getElementById('checkproduct').checked=bl;
		}
	}
function getMerchantType(mtype){
	document.getElementById("merchantId").options.length=0; 
	document.getElementById("merchantId").options.add(new Option('--请选择--','0'));
	if(mtype=="SUPPLY")
	{
		<#list upMerchant as list>						
				document.getElementById("merchantId").options.add(new Option('${list.merchantName}','${list.id}'));
		</#list>
	}else if(mtype=="AGENT")
	{
		<#list downMerchant as list>						
				document.getElementById("merchantId").options.add(new Option('${list.merchantName}','${list.id}')); 
		</#list>
	} 
}


</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action=""  method="get">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>业务编号:
				</th>
				<td>
					<select name="businessNo" id="businessNo"   onchange="getProduct('','1')" class="select">
						<option value="HF" selected=selected>话费业务</option>
						<option value="LL" >流量业务</option>
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>商户类型:
				</th>
				<td>
					<select name="merchantType" id="merchantType"  onchange="getMerchantType(this.value)" class="select">
						<option value="" selected=selected>--请选择--</option>
						<option value="SUPPLY">供货商</option>
						<option value="AGENT">代理商</option>
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>商户名称:
				</th>
				<td>
					<select name="merchantId" id="merchantId"  onchange="getProduct(this.value,'0')" class="select">
						<option value="" selected=selected>--请选择--</option>
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>指定排除类型:
				</th>
				<td>
					<select name="ruleType" id="ruleType" class="select">
						<option value="1" selected=selected>指定</option>
						<option value="2" >排除</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>运营商:
				</th>
				<td>
					<select name="carrierId" id="carrierId" onchange="getProduct('','')" class="select">
					<option value="" selected=selected>-请选择-</option>
					<#list carrierInfo as list>
						<option value="${list.carrierNo}">
							${list.carrierName}
						</option>
					</#list>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>省份:
				</th>
				<td>
					<select name="provinceId" id="provinceId" onchange="getCtiy(this.value,'1')" class="select">
					<option value="" selected=selected>-请选择-</option>
					<#list province as list>
						<option value="${list.provinceId}">
							${list.provinceName}
						</option>
					</#list>
				</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>城市:
				</th>
				<td>
					<select name="cityId" id="cityId" onchange="getProduct(this.value,'1')" class="select">
						<option value="" selected=selected>--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>产品:
				</th>
				<td>
					<select name="productNo" id="productNo"  onchange="getMerchant(this.value)" class="select">
						<option value="" selected=selected>--请选择--</option>
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>被作用商户:
				</th>
				<td>
				<div id="divshelflist">
				</div> 
				</td>
			</tr>		
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="addAssignExclude()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='listAssignExclude'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
