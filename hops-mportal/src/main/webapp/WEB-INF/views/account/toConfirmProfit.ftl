<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
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
	function doConfirmProfit(){
		var limitAmt = ${spMiddleProfitAccount.availableBalance?string("0.0000")};
		var amt = $("#amt").val();
		if(amt < 0 || isNaN(amt)){
			alert("金额错误!");
			return;
		}
		
		
		if(amt > limitAmt){
			alert("金额错误，确认金额需要小于等于中间利润户可用金额！");
			return;
		}
		window.location.href="${base}/account/doConfirmProfit?amt="+amt+"&spProfitOwnAccountId="+${spProfitOwnAccount.accountId}+"&spMiddleProfitAccountId="+${spMiddleProfitAccount.accountId};
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
	<form id="inputForm" action="${base}/account/doConfirmProfit"  method="post">
		<table class="input">
		<tr>
				<th>
					<span class="requiredField">*</span>账户名称:
				</th>
				<td>
					<input type="text"  disabled="disabled" maxlength="200" value="系统中间利润户" class="ipt"/>	 
				</td>
				<th>
					<span class="requiredField">*</span>账户名称:
				</th>
				<td>
					<input type="text" disabled="disabled" maxlength="200" value="系统利润户" class="ipt"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>账户详情:
				</th>
				<td>
					AB:${spMiddleProfitAccount.availableBalance?string("0.0000")}<br/>
					UB: ${spMiddleProfitAccount.unavailableBanlance?string("0.0000")}<br/>
					CB: ${spMiddleProfitAccount.creditableBanlance?string("0.0000")}</td>
				</td>
				<th>
					<span class="requiredField">*</span>账户详情:
				</th>
				<td>
					AB:${spProfitOwnAccount.availableBalance?string("0.0000")}<br/>
					UB: ${spProfitOwnAccount.unavailableBanlance?string("0.0000")}<br/>
					CB: ${spProfitOwnAccount.creditableBanlance?string("0.0000")}</td>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>确认利润金额:
				</th>
				<td>
					<input type="text" id="amt" name="amt"  maxlength="200" value="" class="ipt"/>	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;认" onclick="doConfirmProfit()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
