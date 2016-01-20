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
	<title>供货商商户产品定价页面</title>
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
			editUpProductRelation();     	
	    }
	}
	
	function editUpProductRelation(){
		
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
		   alert("请输入数字或数字与小数点组合的数字！"); 
		   return;
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
		
		var quality = $("#quality").val();
		if(isNaN(quality)){
			alert("[产品质量] 请输入0~100的数字！"); 
			return;
		}else{
			if(parseFloat(quality) > 100 || parseFloat(quality) < 0){
				alert("[产品质量] 请输入0~100的数字！"); 
				return;
			}
		}
		
		
		
		var merchantLevel_radio = $('input[id="merchantLevel_radio"]:checked').val();
		if(merchantLevel_radio == undefined){
			alert("请选择级别信息！");
			return;
		}
		
		if(product_select != null && product_select != -1){
			$("#merchantLevel").val(merchantLevel_radio);
			sumbitSupplyProductRelation();
		}else{
			alert("请选择产品！");
		}
	}
	
	function sumbitSupplyProductRelation(){
		$.ajax({  
            type: "post",  
            url: "${ctx}/product/doEditSupplyProductRelation",       
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
		//window.location.href="${ctx}/Merchant/supplyProductRelationList?merchantId=${merchant.id}";
		window.location.href="${ctx}/${backUrl}";
	}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" name="inputForm" action="${ctx}/product/doEditSupplyProductRelation" method="post" class="form-horizontal">
		<table class="input">
			<input id="merchantId" name="merchantId" type="hidden"  value="${merchant.id}" class="ipt"/>
			<input id="id" name="id" type="hidden"  value="${upr.id}" class="ipt"/>
			<input id="identityType" name="identityType" type="hidden"  value="${upr.identityType}" class="ipt"/>
			<input id="status" name="status" type="hidden"  value="${upr.status}" class="ipt"/>
			<input id="source" name="source" type="hidden"  value="${source}" class="ipt"/>
			<input id="supplyProdId" name="supplyProdId" type="hidden"  value="${upr.supplyProdId}" class="ipt"/>
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
								<c:when test="${upr.productId == product.productId}">
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
				<th>供货商方产品编号:</th>
				<td>
					<input type="text" id="supplyProdId" name="supplyProdId"  style="display:inline-block;"  class="input-large required ipt" value="${upr.supplyProdId}"  disabled="disabled" />(如果没有,请留空)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品折扣:</th>
				<td>
					<input type="text" id="discount" name="discount" class="ipt required" value="${upr.discount}" />(话费：0.9到1.2之间；流量：0.1到1.9之间)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品质量:</th>
				<td>
					<input type="text" id="quality" name="quality" class="ipt required" value="${upr.quality}" />(请输入0~100的数字)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>供货商级别:</th>
				<td>
					<input type="hidden" id="merchantLevel" name="merchantLevel" class="select"/>
					<c:forEach items="${merchantLevels}" var="merchantLevel">
						<c:choose>
							<c:when test="${upr.merchantLevel == merchantLevel.merchantLevel}">
								<input type="radio" id="merchantLevel_radio" name="merchantLevel_radio" value="${merchantLevel.merchantLevel}" checked="checked"/>级别：${merchantLevel.merchantName} 【${merchantLevel.orderPercentagelow}~${merchantLevel.orderPercentageHigh}】<br/>
							</c:when>
							<c:otherwise>
								<input type="radio" id="merchantLevel_radio" name="merchantLevel_radio" value="${merchantLevel.merchantLevel}" />级别：${merchantLevel.merchantName} 【${merchantLevel.orderPercentagelow}~${merchantLevel.orderPercentageHigh}】<br/>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			   </td>
			</tr>
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="editProductRelation()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="goback();"/> <!-- onclick="history.back();location.reload();"/> -->
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
