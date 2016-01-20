<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商户产品定价页面</title>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script>
	function saveProduct(){
		var percentage = $("#percentage").val();
		if(isNaN(percentage)){
		   alert("请输入数字或数字与小数点组合的数字！"); 
		   return;
		}
		
		var merchant_select = $("#merchant_select").val();
		var merchant_params = merchant_select.split("|");
		var identityId = merchant_params[0];
		var identityName = merchant_params[1];
		var identityTpye = merchant_params[2];
		$("#identityId").val(identityId);
		$("#identityName").val(identityName);
		$("#identityTpye").val(identityTpye);
		
		var product_select = $("#product_select").val();
		var product_params = product_select.split("|");
		$("#productId").val(product_params[0]);
		$("#productName").val(product_params[1]);
		var priceStrategy = $('input[name="priceStrategy_radio"]:checked').val();
		$("#priceStrategy").val(priceStrategy);
		
		$('#inputForm').submit();
	}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/product/doSaveProductIdentityRelation" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>产品注册</small></legend>
			
			<div class="control-group">
				<label for="accountType" class="control-label">选择会员:</label>
				<input id="identityId" name="identityId" type="hidden" />
				<input id="identityTpye" name="identityTpye" type="hidden" />
				<input id="identityName" name="identityName" type="hidden" />
				<div class="controls">
					<select name = "merchant_select" id="merchant_select" class="select required">
						<c:forEach items="${merchants}" var="merchant">
						    <option value="${merchant.id}|${merchant.merchantName}|Merchant">${merchant.merchantName}</option>
					    </c:forEach>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label for="accountType" class="control-label">选择产品:</label>
				<input id="productId" name="productId" type="hidden" />
				<input id="productName" name="productName" type="hidden" />
				
				<div class="controls">
					<select name = "product_select" id="product_select" class="select required">
						<c:forEach items="${products}" var="product">
						    <option value="${product.productId}|${product.productName}">${product.productName}</option>
					    </c:forEach>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label for="priceStrategy" class="control-label">定价策略:</label>
				<input id="priceStrategy" name="priceStrategy" type="hidden" /> 
				<div class="controls">
					<input type="radio" id="priceStrategy_radio" name="priceStrategy_radio" value="1" checked="checked"/>成本优先<br/>
					<input type="radio" id="priceStrategy_radio" name="priceStrategy_radio" value="0" />指定运营商<br/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="percentage" class="control-label">百分比:</label>
				<div class="controls">
					<input type="text" id="percentage" name="percentage" class="ipt required"/>
				</div>
			</div>
			
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="button" onclick="saveProduct()" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
