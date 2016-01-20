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
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.jslider.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>商户产品定价页面</title>
	<script>

	function saveDownProductRelation(){
		var merchantSelect = $("#merchant_select").val();
		if(merchantSelect != null){
			var merchant_params = merchantSelect.split("|");
			$("#identityId").val(merchant_params[0]);
			$("#identityName").val(merchant_params[1]);
		}else{
			alert("请选择商户！");
			return;
		}
		
		var busi_type ='';
		var product_select = $("#product_select").val();
		if(product_select){
			var product_params = product_select.split("|");
			$("#productId").val(product_params[0]);
			$("#productName").val(product_params[1]);
			busi_type = product_params[2];
		}else{
			alert("请选择产品！");
			return;
		}
		
		var discount = $("#discount").val().trim();
		if(isNaN(discount) || discount==''){
			alert("[产品折扣] 请输入数字或数字与小数点组合的数字！"); 
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
			if(busi_type=='2' && (parseFloat(discount) > 1.5 || parseFloat(discount) < 0.5||discount.length>6)){
				alert("[产品折扣] 折扣不能为空或存在空格，流量请输入0.5~1.5之间4位小数！！"); 
				return;
			}
		}
		
	var agentProd = $("#agentProdId").val().trim();
		
		if(agentProd != ""){
			var   re =/^[a-zA-Z0-9_]{1,}$/;
			var result=  re.test(agentProd);

			if(!result)
			{
				alert("代理商方产品编号只能由字母数字下划线组成！");
				return;
			}
		}

		var discountWeight = $("#discountWeight").val();
		
		if(discountWeight==''){
			alert('[产品折扣权重]不能为空!');
			return;
		}
		
		if(isNaN(discountWeight)){
			alert("[产品折扣权重] 请输入0~100的数字！"); 
			return;
		}else{
			if(parseFloat(discountWeight) > 100 || parseFloat(discountWeight) < 0){
				alert("[产品折扣权重] 请输入0~100的数字！"); 
				return;
			}
		}
		
		var quality = $("#quality").val();
		
		if(quality==""){
			alert("[产品质量] 不能为空！"); 
			return;
		}
		
		if(isNaN(quality)){
			alert("[产品质量] 请输入0~100的数字！"); 
			return;
		}else{
			if(parseFloat(quality) > 100 || parseFloat(quality) < 0){
				alert("[产品质量] 请输入0~100的数字！"); 
				return;
			}
		}
		
		var qualityWeight_input = $("#qualityWeight_input").val();
		
		if(qualityWeight_input==""){
			alert("[产品质量权重] 不能为空！"); 
			return;
		}
		
		if(isNaN(qualityWeight_input)){
			alert("[产品质量权重] 请输入0~100的数字！"); 
			return;
		}else{
			if(parseFloat(qualityWeight_input) > 100 || parseFloat(qualityWeight_input) < 0){
				alert("[产品质量权重] 请输入0~100的数字！"); 
				return;
			}
		}
		
		$("#qualityWeight").val(qualityWeight_input);
		
		var weightSumFlag = parseFloat(discountWeight) + parseFloat(qualityWeight_input);
		if(discountWeight.length > 0 && qualityWeight_input.length > 0){
			weightSumFlag = parseFloat(discountWeight) + parseFloat(qualityWeight_input);
		}else if(discountWeight.length > 0 && qualityWeight_input.length <= 0){
			weightSumFlag = parseFloat(discountWeight);
		}else if(discountWeight.length <= 0 && qualityWeight_input.length > 0){
			weightSumFlag = parseFloat(qualityWeight_input);
		}
		
		if(weightSumFlag != 100){
			alert("检查有误，产品折扣权重与产品质量权重之和不为100！");
			return;
		}
		
		$('#inputForm').submit();
		
	}
	</script>
	<style type="text/css">
	
		#range_discount {width:150px;margin-top: 10px;height: 10px;background-color: #FFFFE0;border: 1px solid #A9C9E2;position: relative;}             
		#range_quality {width:150px;margin-top: 10px;height: 10px;background-color: #FFFFE0;border: 1px solid #A9C9E2;position: relative;}             
		#range_discountWeight {width:150px;margin-top: 10px;height: 10px;background-color: #FFFFE0;border: 1px solid #A9C9E2;position: relative;}             
		#range_qualityWeight{width:150px;margin-top: 10px;height: 10px;background-color: #FFFFE0;border: 1px solid #A9C9E2;position: relative;}             
		.ui-slider { position:relative; text-align:left;}
		.ui-slider .ui-slider-range {position:absolute; z-index:1; display:block; border:0; background:#f90}
		.ui-slider-horizontal {height:10px; }
		.ui-slider-horizontal .ui-slider-handle {top:14px; margin-left:0; }
		.ui-slider-horizontal .ui-slider-range {top:20px; height:4px; }
		.ui-slider-horizontal .ui-slider-range-min {left:0; }
		.ui-slider-horizontal .ui-slider-range-max {right:0; }
		.ui-slider .ui-slider-handle {width:10px;height: 15px;background-color: #E6E6FA;border: 1px solid #A5B6C8;top: -6px;display: block;
		cursor: pointer; position: absolute;}
	</style>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/product/doSaveAgentProductRelation" method="post" class="form-horizontal">
		<table class="input">
			<input id="merchantId" name="merchantId" type="hidden"  value="${merchant.id}" class="ipt"/>
	
			<tr>
				<th><span class="requiredField">*</span>商户名称:</th>
				<td>
					<input id="identityId" name="identityId" type="hidden" class="ipt"/>
					<input id="identityName" name="identityName" type="hidden" class="ipt"/>
					<input id="merchantFlag" name="merchantFlag" type="hidden" value="${merchantFlag}" class="ipt"/>
					<c:choose>
						<c:when test="${merchantFlag=='true'}">
							<select name = "merchant_select" id="merchant_select" class="select required" disabled="disabled">
						</c:when>
						<c:otherwise>
							<select name = "merchant_select" id="merchant_select" class="select required">
						</c:otherwise>
					</c:choose>
						<c:forEach items="${merchants}" var="mer">
							<c:choose>
							    <c:when test="${mer.id==merchantId}">
						             	<option value="${mer.id}|${mer.merchantName}" selected="selected" >${mer.merchantName}</option>
						        </c:when>
						        <c:otherwise>
						              	<option value="${mer.id}|${mer.merchantName}">${mer.merchantName}</option>
						        </c:otherwise>
					        </c:choose>
					    </c:forEach>
					</select>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>选择产品:</th>
				<td>
					业务类型：
					    <select id="businessTypeQuery" name="businessTypeQuery"  class="select w80" >
					    <option value="" <c:if test='${order.businessType==""}'>selected='selected'</c:if>>请选择</option>
					    <option value="0" <c:if test='${order.businessType=="0"}'>selected='selected'</c:if>>话费</option>
					    <option value="1" <c:if test='${order.businessType=="1"}'>selected='selected'</c:if>>流量</option>
					    <option value="2" <c:if test='${order.businessType=="2"}'>selected='selected'</c:if>>固话</option>
					    </select>
				
					<input id="productId" name="productId" type="hidden"  class="ipt"/>
					<input id="productName" name="productName" type="hidden"  class="ipt"/>
					运营商：
					<select id="carrierNameQuery" name="carrierNameQuery" class="select w80">
							<option value="">请选择</option>
							<c:forEach items="${carrierInfos}" var="carrierInfo">
								<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
							</c:forEach>
					    </select>
					
					省份：
					<select id="provinceQuery" name="provinceQuery" class="select w80">
							<option value="">请选择</option>
							<c:forEach items="${provinces}" var="province">
								<option value="${province.provinceId}">${province.provinceName}</option>
							</c:forEach>
					    </select>
					面值：
					<input id="parValueQuery" name="parValueQuery" size="6" type="ipt" />
					<input type="button" class="button" value="查询" onclick="queryProduct();"/>
					
					<select name = "product_select" id="product_select" class="select required" style="display:none; ">
						<option value="">请选择</option>
						<%-- <c:forEach items="${products}" var="product">
						    <option value="${product.productId}|${product.productName}">${product.productName}</option>
					    </c:forEach> --%>
					</select>
					<input type="text" style="display: none" id="select_msg" name="select_msg" disabled="disabled" class="ipt required"/>
			   </td>
			</tr>
			
			<tr>
				<th>代理商方产品编号:</th>
				<td>
					<input type="text" id="agentProdId" name="agentProdId"  style="display:inline-block;"  class="input-large required ipt" />(如果没有,请留空)
			   </td>
			</tr>
			
			<tr>
				<th><span class="requiredField">*</span>产品折扣:</th>
				<td>
					<input type="text" id="discount" name="discount"  style="display:inline-block;" class="ipt required"/>(话费：0.9到1.2之间；流量：0.1到1.9之间；固话：0.5到1.5之间)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品折扣权重:</th>
				<td>
					<div id="range_discountWeight" style="display:inline-block;"></div>
					<input type="text" id="discountWeight" name="discountWeight" style="display:inline-block;"  class="ipt required" onblur="" />(0到100之间)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品质量要求:</th>
				<td>
				<div id="range_quality" style="display:inline-block;"></div>
					<input type="text" id="quality" style="display:inline-block;" name="quality" class="ipt required"/>(正整数)
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>产品质量权重:</th>
				<td>
					<input disabled="disabled" type="text" id="qualityWeight_input" name="qualityWeight_input" style="display:inline-block;" class="ipt required"/>(0到100之间)
					<input type="hidden" id="qualityWeight" name="qualityWeight" class="ipt"/>
			   </td>
			</tr>
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="button" type="button" onclick="saveDownProductRelation()" value="提交"/>
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
	
	<script>
	
	
	function queryProduct(){
		var merchantSelect = $('#merchant_select').val();
		var merchantId = "";
		if(merchantSelect != null){
			var merchant_params = merchantSelect.split("|");
			merchantId = merchant_params[0];
		}else{
			alert("请选择商户！");
			return;
		}
		
		var carrierNameQuery=$('#carrierNameQuery').val(); 
		var provinceQuery=$('#provinceQuery').val(); 
		var parValueQuery=$('#parValueQuery').val(); 
		var businessTypeQuery =$('#businessTypeQuery').val();
		$.ajax({
	    	type: "post",
	      	url: "toShowSelectProduct?merchantId="+merchantId+"&businessTypeQuery="+businessTypeQuery+"&carrierNameQuery="+carrierNameQuery+"&provinceQuery="+provinceQuery+"&parValueQuery="+parValueQuery,
	        async: false,
	        datType:'json',
	        success: function (data) {
	        	var dataObj=eval("("+data+")");
	        	var product_select=$("#product_select");
	        	product_select.empty();
	        	
	        	var select_msg = $("#select_msg");
	        	select_msg.hide();
	        	for(var i=0;i<dataObj.length;i++){
	        		 var option = $("<option>").val(dataObj[i].productId+'|'+dataObj[i].productName+'|'+dataObj[i].businessType).text(dataObj[i].productName);
		             product_select.append(option);
		             $("#product_select").show();
	        	}
	        	if(dataObj.length <= 0){
	        		select_msg.val("未找到匹配产品！");
	        		select_msg.show();
	        		$("#product_select").hide();
	        	}else{
	        		$("#product_select").show();
	        	}
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    });
	}
	
	$(function(){
		/* $("#range_discount").slider({
			//range: true,
			min: 0,
			max: 1,
			step: 0.01,
			values: [0],
			slide: function(event, ui){
				$("#discount").val(ui.values[0]);
			}
		}); */
		
		$("#range_quality").slider({
			//range: true,
			min: 0,
			max: 100,
			step: 1,
			values: [0],
			slide: function(event, ui){
				$("#quality").val(ui.values[0]);
			}
		});
		
		
		$("#range_discountWeight").slider({
			//range: true,
			min: 0,
			max: 100,
			step: 1,
			values: [0],
			slide: function(event, ui){
				$("#discountWeight").val(ui.values[0]);
			},
			stop: function(event,ui){
				var num=100-ui.values[0];
				$('#qualityWeight_input').val(num);
			}
			
			
		});
		
	});
	
	</script>
</body>
</html>
