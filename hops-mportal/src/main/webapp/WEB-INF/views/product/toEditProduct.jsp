﻿﻿﻿﻿﻿﻿﻿﻿﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>

<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>修改产品信息</title>
	<script>
// 	$(document).ready(function() {
// 		//getProductPropertyByProductType($("#typeId_select"));
// 		var html = ${productPropertys};
// 		$('#productPropertys').html(html);
// 	});
	$(document).ready(function() {
		//getParentProductIds();
	});
	
	function editProduct(){
		var productName = $("#productName").val();
		if(productName == '' || productName==' '){
			alert("产品名称不能为空！");
			return false;
		}
		if(jmz.GetLength(productName) > 50){
			   alert("产品名称长度不能超过50位字符！"); 
			   return false;
		}
		
		var typeId = $("#typeId_select").val();
		if(typeId == -1){
			alert("请选择产品类型！");
			return false;
		}
		
		var parValue = $("#parValue").val();
		if(parValue.length <= 0){
			   alert("面值有误，请输入整数！"); 
			   return false;
		}else{
			if(parValue.search("^-?\\d+$") != 0 ){
				alert("面值有误，请输入整数！"); 
				return false;
			}
		}
		var displayValue = $("#displayValue").val();
		if (displayValue != undefined) {
			if (displayValue.length <= 0) {
				alert("面额有误，请输入整数！");
				return false;
			} else {
				if (displayValue.search("^-?\\d+$") != 0) {
					alert("面额有误，请输入整数！");
					return false;
				}
			}
		}
		var carrierName = $("#carrierName").val();
		if(carrierName != undefined && carrierName.length <= 0){
			alert("请选择运营商！");
			return false;
		}
		
		var province = $("#province").val();
		if(province != undefined && province.length <= 0){
			alert("请选择省份！");
			return false;
		}
		
		var city = $("#city").val();
		if(city != undefined && city.length <= 0){
			alert("请选择城市！");
			return false;
		}
		
		var productStatus = $('input[name="productStatus_radio"]:checked').val();
		$("#typeId").val(typeId);
		$("#productStatus").val(productStatus);
		
		$('#inputForm').attr("action", "${ctx}/product/doEditProduct");
		$('#inputForm').submit();
	}
	
	function getProductPropertyByProductType(obj) {
		cleanProductSelect();
	    var productTypeId = $(obj).val();
	    
	    var table = $("#productProperty");
	    table.children().children(".add").remove();
	    
	    if(productTypeId != -1){
			$.ajax({
					url:"${ctx}/product/getHtmlElement?productTypeId="+productTypeId, 
					type: "post",
			        data: null,
			        async: false,
					success:function(data) {
						var table = $("#productProperty");
						table.append(data);
					}
			});
	    }else{
	    	var table = $("#productProperty");
	    	table.children().children(".add").remove();
	    }
	}
	
	function getCityByProvince(obj){
		var provinceId = $(obj).val();
	    if(provinceId != -1){
			$.ajax({
					url:"${ctx}/product/getCityByProvince?provinceId="+provinceId, 
					type: "post",
			        data: null,
			        async: false,
					success:function(data) {
						$('#city').html(data);
					}
			});
	    }else{
	    	$('#city').html("");
	    }
	}
	
	function cleanProductSelect(){
		$("#parentProductId").empty();
	}
	
	function getParentProductIds(){
		var parValue = $("#query_parValue").val();//面值
		if(parValue == undefined){
			parValue = "";
		}
		var carrierName = $("#query_carrierName").val();//运营商
		if(carrierName == undefined){
			carrierName = "";
		}
		var province = $("#query_province").val();//省份
		if(province == undefined){
			province = "";
		}
		
		$.ajax({
				url:"${ctx}/product/getParentProductIds?parValue="+parValue+"&carrierName="+carrierName+"&province="+province+"&parentProductId="+${product.parentProductId}, 
				type: "post",
		        data: null,
		        async: false,
				success:function(data) {
					cleanProductSelect();
					var parentProduct_params = data.split("|");
					for(var i=0;i<parentProduct_params.length;i++){ 
						var parentProduct = parentProduct_params[i];
						$("#parentProductId").append(parentProduct);
					}
					if(data.length <= 0){
						cleanProductSelect();
					}
				}
		});
	}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/product/doEditProduct" method="post" class="form-horizontal">
		<input type="hidden" name="productId" id="productId" value="${product.productId}">
		<table id="productProperty" class="input">
		    <tr>
				<th><span class="requiredField">*</span>产品名称:</th>
				<td>
					<input type="text" class="ipt w120" name="productName" id="productName" value="${product.productName}" maxlength="64"/>
			   </td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>产品类型:
				</th>
				<td>
					<input id="typeId" name="typeId" type="hidden" />
					<select name = "typeId_select" id="typeId_select" class="select required"  disabled="disabled" onchange="getProductPropertyByProductType(this)" >
						<option value="-1">请选择</option>
						<c:forEach items="${productTypes}" var="productType">
							<c:choose>
								<c:when test="${productType.typeId == product.typeId}">
									<option value="${productType.typeId}" selected="selected">${productType.productTypeName}</option>
								</c:when>
								<c:otherwise>
									<option value="${productType.typeId}">${productType.productTypeName}</option>
								</c:otherwise>
							</c:choose>
					    </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>父产品:
				</th>
				<td>
					<select name = "parentProductId" id="parentProductId" class="select required" >
						<c:forEach items="${parentProducts}" var="parentProduct">
						<c:choose>
						<c:when test="${parentProduct.productId == product.parentProductId}">
							<option value="${parentProduct.productId}" selected="selected">${parentProduct.productName}</option>
						</c:when>
						<c:otherwise>
							<option value="${parentProduct.productId}">${parentProduct.productName}</option>
						</c:otherwise>
					</c:choose>
					    </c:forEach>
					
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品状态:</th>
				<td>
					<input id="productStatus" name="productStatus" type="hidden" />
					<input type="radio" id="productStatus_radio" name="productStatus_radio" value="1" <c:if test='${product.productStatus=="1"}'>checked="checked"</c:if>/>可用<br/>
					<input type="radio" id="productStatus_radio" name="productStatus_radio" value="0" <c:if test='${product.productStatus=="0"}'>checked="checked"</c:if>/>不可用<br/>
			   </td>
			</tr>
			${productPropertys}
			
		</table>
		
		<table class="input">
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="editProduct()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
