<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
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
	<title>产品注册</title>
	
	<script>
	function saveProduct(){
		var productName = $("#productName").val();
		if(productName.length <= 0){
			alert("请输入产品名称！");
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
		
		var unitType = $("#unitType").val();
		if (unitType != undefined) {
			if(unitType =="1")
				{
				$("#parValue").val(parValue * 1024);
				}
		
		}
		var productStatus = $('input[name="productStatus_radio"]:checked').val();
		$("#typeId").val(typeId);
		$("#productStatus").val(productStatus);
		
		$('#inputForm').attr("action", "${ctx}/product/doSaveProduct");
		$('#inputForm').submit();
	}
	
	$(document).ready(function(){
		getProductPropertyByProductType();
		$('#query_parValue').validate({
		    /* 设置验证规则 */  
		    rules: {   
		    	query_parValue: {   
		            digits:true,   
		            byteRangeLength:[0,10]   
		        }
		    },   
		       
		    /* 设置错误信息 */  
		    messages: {   
		    	query_parValue: {
		            digits: "面值只能是数字",   
		            byteRangeLength: "面值必须在0~10个字符之间"       
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
	
	function getProductPropertyByProductType() {
		cleanProductSelect();
	    var productTypeId = $('#typeId_select').val();
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
		$("#parentProductId").append("<option value='-1'>自身为父节点</option>");
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
				url:"${ctx}/product/getParentProductIds?parValue="+parValue+"&carrierName="+carrierName+"&province="+province, 
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
	<form id="inputForm" action="${ctx}/product/doSaveProduct" method="post" class="form-horizontal">
		<table id="productProperty" class="input">
		    <tr>
				<th><span class="requiredField">*</span>产品名称:</th>
				<td>
					<input type="text" name="productName" id="productName" maxlength="64" class="ipt w120"/>
			   </td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>产品类型:
				</th>
				<td>
					<input id="typeId" name="typeId" type="hidden" />
					<select name = "typeId_select" id="typeId_select" class="select required" onchange="getProductPropertyByProductType()" >
						<option value="-1">请选择</option>
						<c:forEach items="${productTypes}" var="productType">
						    <option value="${productType.typeId}">${productType.productTypeName}</option>
					    </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品状态:</th>
				<td>
					<input id="productStatus" name="productStatus" type="hidden" />
					<input type="radio" id="productStatus_radio" name="productStatus_radio" value="1" checked="checked"/>可用<br/>
					<input type="radio" id="productStatus_radio" name="productStatus_radio" value="0" />不可用<br/>
			   </td>
			</tr>
		</table>
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>父产品:
				</th>
				<td>
					运营商：<select name="query_carrierName" id="query_carrierName" class="select w80 required" >
						<option value="">请选择</option>
						<c:forEach items="${carrierInfos}" var="carrierInfo">
						    <option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
					    </c:forEach>
					</select>
					省份：<select name = "query_province" id="query_province" class="select w80 required" >
						<option value="">请选择</option>
						<c:forEach items="${provinces}" var="province">
						    <option value="${province.provinceId}">${province.provinceName}</option>
					    </c:forEach>
					</select>
					面额：<input type="text" name="query_parValue" id="query_parValue" value="" class="ipt"/>
					<input id="cancel_btn" class="btn" type="button" value="查询" onclick="getParentProductIds()"/>
					<select name = "parentProductId" id="parentProductId" class="select required" >
						<option value="-1">自身为父节点</option>
						<c:forEach items="${products}" var="product">
						    <option value="${product.productId}">${product.productName}</option>
					    </c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<table class="input">
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="saveProduct()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
