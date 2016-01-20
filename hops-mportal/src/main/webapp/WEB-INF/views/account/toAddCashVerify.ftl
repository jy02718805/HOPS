<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>加款审核</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" language="javascript">
	function doAddCashVerify(){
		var verifyStatus = $("#verifyStatus").val();
		if(verifyStatus == 0){
			alert("请选择审核状态");
			return;
		}
		var amt = $("#amt").val();
		if(!validate(amt)){
			alert("审核金额异常，应为正数！");
			return;
		}
		
		var successAmt=$("#successAmt").val();
		if(successAmt>amt)
		{
			alert("审核成功金额大于审核金额！");
			return;
		}
		
		var externalAccountId = $("#externalAccounts").val();
		if(externalAccountId.length <= 0){
			alert("请选择外部账户！");
			return;
		}
		
		window.location.href="${base}/account/doAddCashVerify?id=${id!""}&verifyStatus="+verifyStatus+"&amt="+amt+"&externalAccountId="+externalAccountId;
	}
	function getapplyAmt(status,applyAmt)
	{
		if(status=='2')
		{
			$("#amt").val(applyAmt);
		}else{
			$("#amt").val(0);
		}
	}
	function validate(num)
	{
	  var reg = /^\d+(?=\.{0,1}\d+$|$)/
	  if(reg.test(num)){ 
	  	return true;
	  }else{
	  	return false ;  
	  }
	}
</script>
<body>

<div class="mg10"></div>
	<form id="inputForm" action="${base}/account/doCurrencyAccountCredit"  method="post">
		<input type="hidden" id="id" name="id" value="${caar.id}"/>
		<input type="hidden" id="externalAccountId" name="externalAccountId"/>
		<table class="input">
		<tr>
				<th>
					<span class="requiredField">*</span>商户名称:
				</th>
				<td>
					<input type="text" id="merchantName" name="merchantName" disabled="disabled" class="ipt" maxlength="200" value="${caar.merchantName!""}"/>	 
				</td>
				<th>
					<span class="requiredField">*</span>操作员名称:
				</th>
				<td>
					<input type="text" id="operatorName" name="operatorName" disabled="disabled" class="ipt" maxlength="200" value="${caar.operatorName!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>账户详情:
				</th>
				<td>
					AB:${ca.availableBalance?string("0.0000")}<br/>
					UB: ${ca.unavailableBanlance?string("0.0000")}<br/>
					CB: ${ca.creditableBanlance?string("0.0000")}</td>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>申请加款金额:
				</th>
				<td>
					<input type="text" id="showAmt" name="showAmt" disabled="disabled" class="ipt" maxlength="200" value="${caar.applyAmt!""}"/>	 
				</td>
				<th>
					<span class="requiredField">*</span>审核备注:
				</th>
				<td>
					<input type="text" id="rmk" name="rmk" disabled="disabled" class="ipt" maxlength="200" value="${caar.rmk!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>外部账户:
				</th>
				<td>
					<select name="externalAccounts" id="externalAccounts" select="select">
						<option value="" selected=selected>--请选择--</option>	
						<#list mlist as list>
							<option value="${(list.accountId)!""}">${(list.rmk)!""}</option>
						</#list>
					</select>
				</td>
				<th>
					<span class="requiredField">*</span>审核金额:
				</th>
				<td>
					<input type="text" id="amt" name="amt"  class="ipt" maxlength="200" value="${caar.applyAmt!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>审核结果:
				</th>
				<td>
					<select name="verifyStatus" id="verifyStatus" select="select" onchange="getapplyAmt(this.value,'${caar.applyAmt!""}')" >
						<option value="" selected=selected>--请选择--</option>	
						<option value="2">--审核成功--</option>
						<option value="3">--审核失败--</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;认" onclick="doAddCashVerify()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
