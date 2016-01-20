<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>商户产品变动历史列表 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/provinceCity.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DateUtil.js"></script>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<style>
#pager ul.pages { 
	display:block; 
	border:none; 
	text-transform:uppercase; 
	font-size:10px; 
	margin:10px 0 50px; 
	padding:0; 
} 
#pager ul.pages li { 
	list-style:none; 
	float:left; 
	border:1px solid #ccc; 
	text-decoration:none; 
	margin:0 5px 0 0; 
	padding:5px; 
} 
#pager ul.pages li:hover { 
	border:1px solid #003f7e; 
} 
#pager ul.pages li.pgEmpty { 
	border:1px solid #eee; 
	color:#eee; 
} 
#pager ul.pages li.pgCurrent { 
	border:1px solid #003f7e; 
	color:#000; 
	font-weight:700; 
	background-color:#eee; 
} 

</style>
<body>
<form id="listForm" action="${ctx}/productOperation/queryProductDiscountHistory">
	<input id='page' type=hidden name='page'/>
	<input id="carrierName" name="carrierName" type="hidden" />
	<input id="province" name="province" type="hidden" />
	<input id="city" name="city" type="hidden" />
	<input id="productSide" name="productSide" type="hidden" />
	<input id="identityId" name="identityId" type="hidden" />
	<input id="businessType" name="businessType" type="hidden" />
	<input id="productId" name="productId" type="hidden" />
	
	<div class="line_bar">
	商户类型:<select name="productSide_select" id="productSide_select" class="select" onchange="getMerchants()">
			<option value="">请选择</option>
			<c:choose>
				<c:when test="${productDiscountHistoryVO.productSide=='AGENT'}">
		             	<option value="AGENT" selected="selected">代理商</option>
		        </c:when>
		        <c:otherwise>
		              	<option value="AGENT" >代理商</option>
		        </c:otherwise>
		    </c:choose>
		    <c:choose>
				<c:when test="${productDiscountHistoryVO.productSide=='SUPPLY'}">
		             	<option value="SUPPLY" selected="selected">供货商</option>
		        </c:when>
		        <c:otherwise>
		              	<option value="SUPPLY" >供货商</option>
		        </c:otherwise>
		    </c:choose>
		 </select>
	商户:<select name="identityId_select" id="identityId_select" class="select">
			<option value="">请选择</option>
			<c:forEach items="${merchants}" var="merchant">
				<c:choose>
					<c:when test="${productDiscountHistoryVO.identityId==merchant.id}">
			             	<option value="${merchant.id}" selected="selected">${merchant.merchantName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${merchant.id}">${merchant.merchantName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		 </select>
		 
	产品:<select name="merchantProducts" id="merchantProducts" class="select" onchange="initProductProperties()">
			<option value="">请选择</option>
			<c:forEach items="${products}" var="product">
				<c:choose>
					<c:when test="${productId==product.id}">
			             	<option value="${product.carrierName}|${product.province}|${product.city}|${product.parValue}|${product.businessType}|${product.id}" selected="selected">${product.productName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${product.carrierName}|${product.province}|${product.city}|${product.parValue}|${product.businessType}|${product.id}">${product.productName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		 </select>
		 
	开始时间:<input id="beginDate" name="beginDate" type="text" 
		onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-{%M-3}-%d'});changeDateSelect();" 
		value="${beginDate}" class="ipt w150"/>
	</div>
	<div class="line_bar">
	运营商 :<select name="carrierInfo_select" id="carrierInfo_select" class="select w80" onchange="initProduct()">
					<option value="">请选择</option>
					<c:forEach items="${carrierInfos}" var="carrierInfo">
						<c:choose>
							<c:when test="${productDiscountHistoryVO.carrierName==carrierInfo.carrierNo}">
					             	<option value="${carrierInfo.carrierNo}" selected="selected">${carrierInfo.carrierName}</option>
					        </c:when>
					        <c:otherwise>
					              	<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
					        </c:otherwise>
					    </c:choose>
					</c:forEach>
		 		</select>
	省份:<select name="province_select" id="province_select" class="select w80" onchange="initProduct(),getCityByProvince(this)">
			<option value="">请选择</option>
			<c:forEach items="${provinces}" var="province">
				<c:choose>
					<c:when test="${province.provinceId==productDiscountHistoryVO.province}">
			             	<option value="${province.provinceId}" selected="selected">${province.provinceName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${province.provinceId}">${province.provinceName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		 </select>
	城市:	<select name="city_select" id="city_select" class="select w80">
			<option value="">请选择</option>
			<c:forEach items="${citys}" var="city">
				<c:choose>
					<c:when test="${city.cityId==productDiscountHistoryVO.city}">
			             	<option value="${city.cityId}" selected="selected">${city.cityName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${city.cityId}">${city.cityName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		</select>
		 
	业务类型:  <select id='businessType_select' name='businessType_select'  class="select w80" onchange="initProduct()">
			    <option value='' <c:if test='${productDiscountHistoryVO.businessType==""}'>selected='selected'</c:if>>请选择</option>
			    <option value='0' <c:if test='${productDiscountHistoryVO.businessType=="0"}'>selected='selected'</c:if>>话费</option>
			    <option value='1' <c:if test='${productDiscountHistoryVO.businessType=="1"}'>selected='selected'</c:if>>流量</option>	
    		</select>
	面     值 :	<input type="text" id="parValue" name="parValue" value="${productDiscountHistoryVO.parValue}" class="ipt w80" onchange="initProduct()" onkeyup="value=value.replace(/[^\d]/g,'')"/>
	
	变更类型:  <select id='action' name='action'  class="select w80">
			    <option value='' <c:if test='${productDiscountHistoryVO.action==""}'>selected='selected'</c:if>>请选择</option>
			    <option value='SAVE' <c:if test='${productDiscountHistoryVO.action=="SAVE"}'>selected='selected'</c:if>>保存产品</option>
			    <option value='UPDATE' <c:if test='${productDiscountHistoryVO.action=="UPDATE"}'>selected='selected'</c:if>>修改产品折扣</option>
			    <option value='DELETE' <c:if test='${productDiscountHistoryVO.action=="DELETE"}'>selected='selected'</c:if>>删除产品</option>	
    		</select>
	
	</div>
	<div class="line_bar">
	<shiro:user>	 
	<input type="button" class="button"  value="查询" onclick="queryProductDiscountHistory()"/>
	</shiro:user>
	</div>
	<table id=listTable class=list>
		
		<tr>
			<th><span>序号</span> </th>
			<th><span>操作员</span> </th>
			<th><span>操作时间</span> </th>
			<th><span>商户名称</span> </th>
			<th><span>产品名称</span> </th>
			<th><span>动作</span> </th>
		</tr>
		<tbody class="listTable">
		<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
				<c:forEach items="${mlist}" var="productDiscountHistory" varStatus="status">
					<tr>
						<td>${(page-1)*pageSize+status.index+1}</td>
						<td>${productDiscountHistory.operatorName}</td>
						<td>
							<fmt:formatDate value="${productDiscountHistory.createDate}" type="both" dateStyle="medium"/>
						</td>
						<td>${productDiscountHistory.identityName}</td>
						<td>${productDiscountHistory.productName}</td>
						<td>
							<c:choose>
								   <c:when test="${productDiscountHistory.action=='UPDATE'}">
							             	 修改产品折扣&nbsp;&nbsp;&nbsp;原始折扣[${productDiscountHistory.oldValue}]&nbsp;&nbsp;&nbsp;变更折扣[${productDiscountHistory.newValue}]
							       </c:when>
							       <c:when test="${productDiscountHistory.action=='DELETE'}">
							             	删除产品
							       </c:when>
							       <c:when test="${productDiscountHistory.action=='SAVE'}">
							       			保存产品
							       </c:when>
							       <c:otherwise>
							              	未知
							       </c:otherwise>					
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:when>
				<c:otherwise>
						<tr>
							<td colspan="16">没数据</td>
						</tr>
				</c:otherwise>
			</c:choose>
		
		</tbody>
	</table>
<div class="line_pages">
	<div style="float:left;">
	  	显示条数：
	  	<select name="pageSize" id="pageSize" >
	  		<c:choose>
				<c:when test="${pageSize==10}">
					<option value="10" selected="selected">10</option>
				</c:when>
				<c:otherwise>
					<option value="10" >10</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==20}">
					<option value="20" selected="selected">20</option>
				</c:when>
				<c:otherwise>
					<option value="20" >20</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==30}">
					<option value="30" selected="selected">30</option>
				</c:when>
				<c:otherwise>
					<option value="30" >30</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==50}">
					<option value="50" selected="selected">50</option>
				</c:when>
				<c:otherwise>
					<option value="50" >50</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==100}">
					<option value="100" selected="selected">100</option>
				</c:when>
				<c:otherwise>
					<option value="100" >100</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==500}">
					<option value="500" selected="selected">500</option>
				</c:when>
				<c:otherwise>
					<option value="500" >500</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==1000}">
					<option value="1000" selected="selected">1000</option>
				</c:when>
				<c:otherwise>
					<option value="1000" >1000</option>
				</c:otherwise>
			</c:choose>
		</select>&nbsp; 条
	  </div>
	<div id="pager" style="float:right;"></div>  
	<div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
  	</div>
 	<script type="text/javascript" language="javascript">
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
		}); 
		PageClick = function(pageclickednumber) { 
		  	$("#pager").pager({ 
		       pagenumber: pageclickednumber,
			   pagecount: ${pagetotal}, 
			   buttonClickCallback: PageClick 
		});
		
		$("#page").val(pageclickednumber);
	    $("#listForm").submit();
	
		}
		
		function getMerchants(){
			var productSide = $("#productSide_select").val();
		    if(productSide != null && productSide != ""){
				$.ajax({
						url:"${ctx}/productOperation/getMerchants?productSide="+productSide, 
						type: "post",
				        data: null,
				        async: false,
						success:function(data) {
							var dataObj=eval("("+data+")");
				        	var identityId_select=$("#identityId_select");
				        	identityId_select.empty();
				        	
				        	for(var i=0;i<dataObj.length;i++){
				        		 var option = $("<option>").val(dataObj[i].identityId).text(dataObj[i].merchantName);
				        		 identityId_select.append(option);
					             $("#identityId_select").show();
				        	}
				        	getProducts();
						}
				});
		    }else{
		    	var identityId_select = $("#identityId_select");
		    	$("#identityId_select").empty();
		    	var option = $("<option>").val("").text("请选择");
       		 	identityId_select.append(option);
	            $("#identityId_select").show();
	            
	            var merchantProducts_select = $("#merchantProducts");
		    	$("#merchantProducts").empty();
		    	var option = $("<option>").val("").text("请选择");
		    	merchantProducts_select.append(option);
	            $("#merchantProducts").show();
		    }
		    
		    initProductProperties();
		}
		
		$("#identityId_select").change(function(){
			getProducts();
		});
		
		function getProducts(){
			var productSide = $("#productSide_select").val();
			var identityId = $("#identityId_select").val();
		    if(productSide != "" && identityId != ""){
				$.ajax({
						url:"${ctx}/productOperation/getProducts?productSide="+productSide+"&identityId="+identityId, 
						type: "post",
				        data: null,
				        async: false,
						success:function(data) {
							var dataObj=eval("("+data+")");
				        	var merchantProducts_select=$("#merchantProducts");
				        	merchantProducts_select.empty();
				        	var option = $("<option>").val("").text("请选择");
					    	merchantProducts_select.append(option);
				        	for(var i=0;i<dataObj.length;i++){
				        		 var option = $("<option>").val(dataObj[i].carrierName+"|"+dataObj[i].province+"|"+dataObj[i].city+"|"+dataObj[i].parValue+"|"+dataObj[i].businessType+"|"+dataObj[i].id).text(dataObj[i].productName);
				        		 merchantProducts_select.append(option);
					             $("#merchantProducts").show();
				        	}
						}
				});
		    }else{
		    	var merchantProducts_select = $("#merchantProducts");
		    	$("#merchantProducts").empty();
		    	var option = $("<option>").val("").text("请选择");
		    	merchantProducts_select.append(option);
	            $("#merchantProducts").show();
		    }
		}
		
		function initProductProperties(){
			$("#carrierInfo_select").val("");
			$("#province_select").val("");
			$("#businessType_select").val("");
			$("#parValue_select").val("");
			$("#parValue").val("");
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
							$('#city_select').html(data);
						}
				});
		    }else{
		    	$('#city_select').html("");
		    }
		}
		
		function initProduct(){
			$("#merchantProducts").val("");
		}
		
		function queryProductDiscountHistory(){
			var productSide = $("#productSide_select").val();
			$("#productSide").val(productSide);
			var identityId = $("#identityId_select").val();
			$("#identityId").val(identityId);
			var merchantProducts = $("#merchantProducts").val();
			if(merchantProducts.length > 0){
				var productProperties = merchantProducts.split("|");
				$("#carrierName").val(productProperties[0]!='undefined'?productProperties[0]:'');
				$("#province").val(productProperties[1]!='undefined'?productProperties[1]:'');
				$("#city").val(productProperties[2]!='undefined'?productProperties[2]:'');
				$("#parValue").val(productProperties[3]!='undefined'?productProperties[3]:'');
				$("#businessType").val(productProperties[4]!='undefined'?productProperties[4]:'');
				$("#productId").val(productProperties[5]!='undefined'?productProperties[5]:'');
			}else{
				$("#carrierName").val($("#carrierInfo_select").val());
				$("#province").val($("#province_select").val());
				$("#city").val($("#city_select").val());
				$("#parValue").val($("#parValue_select").val());
				$("#businessType").val($("#businessType_select").val());
			}
			$('#listForm').submit();
		}
	</script> 
</form>
</body>
</html>

