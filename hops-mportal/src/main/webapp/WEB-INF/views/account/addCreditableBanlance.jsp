<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加角色</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<LINK rel=stylesheet type=text/css
	href="${ctx}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript
	src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript
	src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
<script>
	function addCreditableBanlance() {
		var amount = $("#amount").val();
		if (amount == null || amount == "") {
			alert("请输入金额！");
			return;
		}
		if (!check(amount)) {
			alert("输入金额格式异常！请重新输入");
			return;
		}
		if (amount > 100000000 || amount < 0) {
			alert("输入金额超出界限（0-100000000)！请重新输入");
			return;
		}
		$('#creditableBanlance').val(amount);

		if (confirm("确认给[${identityName}]授信加款 [" + amount + "]元")) {
			$('#inputForm').submit();
		}
	}

	function check(v) {
		var a = /^[0-9]*(\.[0-9]{1,4})?$/;
		if (!a.test(v)) {
			return false;
		} else {
			return true;
		}
	}
</script>
<body>
	<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/account/doAddCreditableBanlance"
		method="post">
		<input type="hidden" id="accountId" name="accountId"
			value="${currencyAccount.accountId}" /> <input type="hidden"
			id="identityId" name="identityId" value="${identityId}" /> <input
			type="hidden" id="accountType.accountTypeId"
			name="accountType.accountTypeId" value="${accountTypeId}" /> <input
			type="hidden" id="pageType" name="pageType" value="${pageType}" /> <input
			type="hidden" id="creditableBanlance" name="creditableBanlance" />
		<table class="input">

			<tr>
				<th><span class="requiredField">*</span>商户名称:</th>
				<td><input type="text" id="identityName" name="identityName"
					disabled="disabled" class="ipt" maxlength="200"
					value="${identityName}" /></td>
			</tr>

			<tr>
				<th><span class="requiredField">*</span>账户ID:</th>
				<td><input type="text" id="accountId" name="accountId"
					disabled="disabled" class="ipt" maxlength="200"
					value="${currencyAccount.accountId}" /></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>账户状态:</th>
				<td><c:choose>
						<c:when test="${currencyAccount.status==1}">
					             	 已启用
					       </c:when>
						<c:when test="${currencyAccount.status==-1}">
					             	 未启用
					       </c:when>
					</c:choose></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>可用余额(元):</th>
				<td><input type="text" id="accountId" name="accountId"
					disabled="disabled" class="ipt" maxlength="200"
					value="${currencyAccount.availableBalance}" /></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>不可用余额(元):</th>
				<td><input type="text" id="accountId" name="accountId"
					disabled="disabled" class="ipt" maxlength="200"
					value="${currencyAccount.unavailableBanlance}" /></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>授信余额(元):</th>
				<td><input type="text" id="accountId" name="accountId"
					disabled="disabled" class="ipt" maxlength="200"
					value="${currencyAccount.creditableBanlance}" /></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>授信余额加款金额(元):</th>
				<td><input type="text" id="amount" name="amount" class="ipt"
					maxlength="200" value="" autocomplete="off" style="color: red"
					oninput="OnInput (event)" onpropertychange="OnPropChanged (event)"
					onkeyup="value=value.replace(/[^\d|\.]/g,'')" />（金额区间：0-100000000）
					<h2 id="displayVlaue" style="color: red"></h2></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>授信余额加款原因:</th>
				<td><input type="text" id="remark" name="remark" class="ipt" />
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input type="button" class="button" value="确&nbsp;&nbsp;定"
					onclick="addCreditableBanlance()" /> <input type="button"
					class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
