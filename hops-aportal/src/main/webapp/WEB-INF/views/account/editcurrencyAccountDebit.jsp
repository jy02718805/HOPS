<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>修改</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
<script>
	function doCurrencyAccountDebit(){
		var amount = $("#amount").val();
		if(!check(amount)){
			alert("输入金额格式异常！请重新输入");
			return;
		}
		if(amount > 100000000 || amount < 0){
			alert("输入金额超出界限（0-100000000)！请重新输入");
			return;
		}
		$('#availableBalance').val(amount);
		$('#inputForm').submit();
	}
	
	function check(v){
		var a=/^[0-9]*(\.[0-9]{1,4})?$/;
		if(!a.test(v)){
			return false;
		}else{
			return true;
		}
	}
</script>
<body>
<div class="path">
		<a href="#">首页</a> &raquo; 申请加款
	</div>
	<form id="inputForm" action="${ctx}/account/editsaveaccountv"  method="post">
		<input id='id' type=hidden name='id' value='${account.id}'/> 
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>账户ID:
				</th>
				<td>
					<%-- <input type="text" id="accountId" name="accountId" disabled="disabled" class="text" maxlength="200" value="${currencyAccount.accountId}"/> --%>	 
					<input type="text" id="accountId" name="accountId" class="text" maxlength="200" value="${account.accountId}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>申请加款金额(元):
				</th>
				<td>
					<input type="text" id="amount" name="amount" class="text" maxlength="200" value="${account.accountId}"/>（金额区间：0-100000000）	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>备注:
				</th>
				<td>
					<input type="text" id="rmk" name="rmk" class="text" maxlength="200" value="${account.rmk}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="doCurrencyAccountDebit()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
