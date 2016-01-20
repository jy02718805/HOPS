<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>加款申请</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
<script>
	function addCash(){
		var amt = $("#amt").val();
		if(amt.trim()==""){
			alert("金额不能为空或空格，请重新输入");
			return;
		}
		if(!check(amt)){
			alert("输入金额格式异常！请重新输入");
			return;
		}
		if(amt > 100000000 || amt < 0){
			alert("输入金额超出界限（0-100000000)！请重新输入");
			return;
		}
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
	<form id="inputForm" action="${ctx}/account/doSaveAddCashRecord"  method="post">
	<div class="mg10"></div>
		<input type="hidden" id="accountId" name="accountId" value="${currencyAccount.accountId}"/>
		<input type="hidden" id="identityId" name="identityId" value="${identityId}"/>
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>金额:
				</th>
				<td>
					<input type="text" id="amt" name="amt" class="text" maxlength="10" class="ipt"/>（金额区间：0-100000000）
				</td>
			</tr>
			<tr>
				<th>
					备注:
				</th>
				<td>
					<input type="text" id="rmk" name="rmk" class="text" maxlength="200" class="ipt"/>	
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="addCash()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
