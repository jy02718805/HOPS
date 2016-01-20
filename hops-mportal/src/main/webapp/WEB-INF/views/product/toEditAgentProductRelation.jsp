<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/admin/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>代理商商户产品定价页面</title>
	<script>
	$(document).ready(function(){
		$('#inputForm').validate({ 
			onsubmit:true,
		    /* 设置验证规则 */  
		    rules: {   
		    	discount: {   
		    		required:true,
					pattern: /^(\d+$)|([0,1]\.\d{1,4})/,
					minlength: 1,
					maxlength: 6
		        }
		    },   
		       
		    /* 设置错误信息 */  
		    messages: {
		    	discount: {
	            	required:"折扣不能为空或存在空格",
					pattern: "折扣格式错误，请输入4位小数",
					maxlength: "折扣格式错误，请输入4位小数"
		        }
		    }, 
		       
		    /* 设置验证触发事件 */  
		    focusInvalid: false,   
		    onkeyup: false,   
		       
		    /* 设置错误信息提示DOM */  
		    errorPlacement: function(error, element) {       
		        error.appendTo( element.parent());       
		    },     
		       
		});
	});
	
	function editProductRelation()
	{
		if($("#inputForm").valid()){ 
			editDownProductRelation();     	
	    }
	}
	function editDownProductRelation(){
		
		var busi_type ='';
		var product_select = $("#product_select").val();
		if(product_select != null && product_select != -1){
			var product_params = product_select.split("|");
			$("#productId").val(product_params[0]);
			$("#productName").val(product_params[1]);
			busi_type = product_params[2];
		}else{
			alert("请选择产品！");
			return;
		}
		
		var discount = $("#discount").val();
		if(isNaN(discount)){
			alert("[产品折扣] 请输入数字或数字与小数点组合的数字！"); 
			return false;
		}else{
			if(busi_type=='0' && (parseFloat(discount) > 1.2 || parseFloat(discount) < 0.9||discount.length>6)){
				alert("[产品折扣] 折扣不能为空或存在空格，话费产品请输入0.9~1.2之间4位小数！！"); 
				return;
			}
			if(busi_type=='1' && (parseFloat(discount) > 1.9 || parseFloat(discount) < 0.1||discount.length>6)){
				alert("[产品折扣] 折扣不能为空或存在空格，流量请输入0.1~1.9之间4位小数！！"); 
				return;
			}
		}

		var discountWeight = $("#discountWeight").val();
		var qualityWeight = $("#qualityWeight").val();
		if(isNaN(discountWeight)){
			alert("[产品折扣权重] 请输入0~100的数字！"); 
			return false;
		}else{
			if(parseFloat(discountWeight) > 100 || parseFloat(discountWeight) < 0){
				alert("[产品折扣权重] 请输入0~100的数字！"); 
				return false;
			}
		}
		
		var quality = $("#quality").val();
		if(isNaN(quality)){
			alert("[产品质量] 请输入0~100的数字！"); 
			return false;
		}else{
			if(parseFloat(quality) > 100 || parseFloat(quality) < 0){
				alert("[产品质量] 请输入0~100的数字！"); 
				return false;
			}
		}
		
		var qualityWeight = $("#qualityWeight").val();
		if(isNaN(qualityWeight)){
			alert("[产品质量权重] 请输入0~100的数字！"); 
			return;
		}else{
			if(parseFloat(qualityWeight) > 100 || parseFloat(qualityWeight) < 0){
				alert("[产品质量权重] 请输入0~100的数字！"); 
				return false;
			}
		}
		
		var weightSumFlag = parseFloat(discountWeight) + parseFloat(qualityWeight);
		if(weightSumFlag != 100){
			alert("检查有误，产品折扣权重与产品质量权重之和不为100！");
			return false;
		}
		

		sumbitAgentProductRelation();

	}
	
	function sumbitAgentProductRelation(){
		$.ajax({  
            type: "post",  
            url: "${ctx}/product/doEditAgentProductRelation",       
            data: $("#inputForm").serialize(),      
            success: function(data) {
            	if(data=='true'){
                  	alert('操作成功!');
                 }else{
                 	alert('操作失败!');
              }
            },  
            error: function(data) {  
            }  
        });
	}
	function goback()
	{
		window.location.href="${ctx}/${backUrl}";
	}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" name="inputForm" action="${ctx}/product/doEditAgentProductRelation" method="post" class="form-horizontal">
		<table class="input">
			<input id="merchantId" name="merchantId" type="hidden"  value="${merchant.id}" class="ipt"/>
			<input id="id" name="id" type="hidden"  value="${dpr.id}" class="ipt"/>
			<input id="identityType" name="identityType" type="hidden"  value="${dpr.identityType}" class="ipt"/>
			<input id="status" name="status" type="hidden"  value="${dpr.status}" class="ipt"/>
			<input id="source" name="source" type="hidden"  value="${source}" class="ipt"/>
			<input id="displayValue" name="displayValue" type="hidden"  value="${dpr.displayValue}" class="ipt"/>
			<tr>
				<th><span class="requiredField">*</span>商户名称:</th>
				<td>
					<input id="identityId" name="identityId" type="hidden"  value="${merchant.id}" class="ipt"/>
					<input id="identityName" name="identityName" type="hidden"  value="${merchant.merchantName}" class="ipt"/>
					<input type="text" disabled="disabled" name="identityName" id="identityName" value="${merchant.merchantName}" class="ipt"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>选择产品:</th>
				<td>
					<input id="productId" name="productId" type="hidden" />
					<input id="productName" name="productName" type="hidden" />
					<select name = "product_select" id="product_select" disabled="disabled" class="select required">
						<option value="-1">请选择</option>
						<c:forEach items="${products}" var="product">
							<c:choose>
								<c:when test="${dpr.productId == product.productId}">
									<option value="${product.productId}|${product.productName}|${product.businessType}" selected="selected">${product.productName}</option>
								</c:when>
								<c:otherwise>
									<option value="${product.productId}|${product.productName}|${product.businessType}">${product.productName}</option>
								</c:otherwise>
							</c:choose>
					    </c:forEach>
					</select>
			   </td>
			</tr>
				<tr>
				<th>代理商方产品编号:</th>
				<td>
					<input id="agentProdId" name="agentProdId" type="hidden" value="${dpr.agentProdId}" class="ipt"/>
					<input type="text" id="agentProdId" name="agentProdId" class="ipt required" value="${dpr.agentProdId}" disabled="disabled" />
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品折扣:</th>
				<td>
					<input type="text" id="discount" name="discount" class="ipt required" value="${dpr.discount}"/>(话费：0.9到1.2之间；流量：0.1到1.9之间)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品折扣权重:</th>
				<td>
					<input type="text" id="discountWeight" name="discountWeight" class="ipt required" value="${dpr.discountWeight}"/>(请输入0到100之间数字)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品质量:</th>
				<td>
					<input type="text" id="quality" name="quality" class="ipt required" value="${dpr.quality}"/>(请输入0~100的数字)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品质量权重:</th>
				<td>
					<input type="text" id="qualityWeight" name="qualityWeight" class="ipt required" value="${dpr.qualityWeight}" />(请输入0~100的数字)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>是否默认:</th>
				<td>
					<select name = "defValue" id="defValue" class="select w80 required">
						<c:choose>
							<c:when test="${dpr.defValue == '1'}">
								<option selected="selected" value="1">默认</option>
							</c:when>
							<c:otherwise>
								<option value="1">默认</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${airtimeProduct.productId!=airtimeProduct.parentProductId}">
								<c:choose>
									<c:when test="${dpr.defValue == '0' && airtimeProduct.productId!=airtimeProduct.parentProductId}">
										<option selected="selected" value="0">不默认</option>
									</c:when>
									<c:otherwise>
										<option value="0">不默认</option>
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose>
					</select>
			   </td>
			</tr>
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="editProductRelation()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="goback();"/>  <!--  onclick="history.back();location.reload();"/> -->
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
