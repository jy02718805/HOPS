<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>实体卡列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.jslider.js"></script>
<script type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></script>
<script type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/provinceCity.js"></script>
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
<form id="listForm" method="get" action="${ctx}/product/productList">

	<input type="hidden" name="city" id="city" value="" />

	<input id=orderStatus type=hidden name=orderStatus> 
	<input id=paymentStatus type=hidden name=paymentStatus> 
	<input id=shippingStatus type=hidden name=shippingStatus> 
	<input id=page type=hidden name=page> 
	<input id=hasExpired type=hidden name=hasExpired> 
	<div class="line_bar">
	运营商:<select name="carrierInfo" id="carrierInfo" class='select w80'>
			<option value="">请选择</option>
			<c:forEach items="${carrierInfos}" var="carrierInfo">
				<c:choose>
					<c:when test="${carrierInfoVal==carrierInfo.carrierNo}">
			             	<option value="${carrierInfo.carrierNo}" selected="selected">${carrierInfo.carrierName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		 </select>
	省份:<select name="province" id="province" class='select w80'>
			<option value="">请选择</option>
			<c:forEach items="${provinces}" var="province">
				<c:choose>
					<c:when test="${province.provinceId==provinceVal}">
			             	<option value="${province.provinceId}" selected="selected">${province.provinceName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${province.provinceId}">${province.provinceName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		 </select>
	业务类型: <select id='businessType' name='businessType'  class="select w80" >
    <option value='' <c:if test='${businessType==""}'>selected='selected'</c:if>>请选择</option>
    <option value='0' <c:if test='${businessType=="0"}'>selected='selected'</c:if>>话费</option>
    <option value='1' <c:if test='${businessType=="1"}'>selected='selected'</c:if>>流量</option>
    <option value='2' <c:if test='${businessType=="2"}'>selected='selected'</c:if>>固话</option>
    </select>
	面值:<input type="text" id="faceValue" name="faceValue" value="${faceValueVal}" class='ipt w80' onKeyUp="this.value=this.value.replace(/\D/g,'')"/>
		 <div style="float:right;">
		 <shiro:user>
	<input type="submit" class="button" value="查询产品"/>
		</shiro:user>
		<shiro:hasPermission name="product:add_show">
	<input type="button" class="button" value="创建产品" onclick="toSaveProduct()"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="product:tree_show">
	<input type="button" class="button" value="产品树形图" onclick="showProductTrees()"/>
		</shiro:hasPermission>
		</div>
	</div>
	
	<table id=listTable class=list>
		<tr>
		<th><SPAN>序列</SPAN> </th>
			<th><SPAN>产品名称</SPAN> </th>
			<th><SPAN>产品状态</SPAN> </th>
			<th><SPAN>运营商</SPAN> </th>
			<th><SPAN>省份</SPAN> </th>
			<th><SPAN>城市</SPAN> </th>
			<th><SPAN>面值(元/M)</SPAN> </th>
			<th><SPAN>面额(元)</SPAN> </th>
			<th><SPAN>业务类型</SPAN> </th>
			<th><SPAN>操作</SPAN> </th>
		</tr>
		<tbody class="listTable">
		<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
		<c:forEach items="${mlist}" var="airtimeProduct" varStatus="status">
			<tr>
				<td>${(page-1)*pageSize+status.index+1}</td>
				<td>${airtimeProduct.productName}</td>
				<c:choose>
       				<c:when test="${airtimeProduct.productStatus == '1'}">
       					<td>可用</td>
       				</c:when>
       				<c:when test="${airtimeProduct.productStatus == '0'}">
       					<td class="tdgb">不可用</td>
       				</c:when>
       				<c:otherwise>
       					<td>未知</td>
       				</c:otherwise>
     			</c:choose>
				<td>
				<span class="carrierInfo_${status.count}">${airtimeProduct.carrierName}</span>
				</td>
				<td>
				<span class="p_${status.count}">${airtimeProduct.province}</span>
				</td>
				<td>
				<span class="c_${status.count}">${airtimeProduct.city}</span>
				</td>
				<td>${airtimeProduct.parValue}
				<c:choose>
			       <c:when test="${airtimeProduct.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${airtimeProduct.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${airtimeProduct.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
				</td>
				<td>${airtimeProduct.displayValue}</td>
				<td>
				<c:if test="${airtimeProduct.businessType == 0}">话费</c:if>
				<c:if test="${airtimeProduct.businessType == 1}">流量</c:if>
				<c:if test="${airtimeProduct.businessType == 2}">固话</c:if>
				</td>
				<td>
					<shiro:hasPermission name="product:updateStatus">
						<c:if test="${airtimeProduct.productStatus == 0}">
							<a onclick="updateProductStatus(1,${airtimeProduct.productId})">[开启]</a>
						</c:if>
						<c:if test="${airtimeProduct.productStatus == 1}">
							<a onclick="updateProductStatus(0,${airtimeProduct.productId})">[关闭]</a>
						</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="product:delete">
					<a onclick="deleteProduct(${airtimeProduct.productId})">[删除]</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="product:edit_show">
					<a onclick="toEditProduct(${airtimeProduct.productId})">[修改]</a>
					</shiro:hasPermission>
					
				</td>
			</tr>
		</c:forEach>
		</c:when>
			<c:otherwise>
					<tr>
						<td colspan="10">没数据</td>
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
			$("#pager").pager({ pagenumber: pageclickednumber, pagecount: ${pagetotal}, buttonClickCallback: PageClick 
			}); 
			$("#page").val(pageclickednumber);
			$("#listForm").submit();
			}
			
		function toSaveProduct(){
			window.location.href="${ctx}/product/toSaveProduct";
		}
		
		function showProductTrees(){
			window.location.href="${ctx}/product/showProductTrees";
		}
		
		function updateProductStatus(status,productId){
			var actionStr = "";
			if(status == 0){
				//关闭
				actionStr = "关闭";
			}else if(status == 1){
				//开启
				actionStr = "开启";
			}
			if(window.confirm("确实要"+actionStr+"此产品吗？")){
				window.location.href="${ctx}/product/updateProductStatus?productId="+productId+"&productStatus="+status;
		    }else{
		    	
		    }
		}
		
		function toEditProduct(productId){
			window.location.href="${ctx}/product/toEditProduct?productId="+productId;
		}
		
		function deleteProduct(productId){
			if(window.confirm("确实要删除吗？")){
				window.location.href="${ctx}/product/deleteProudct?productId="+productId;
			}else{
			    	
			}
		}
		
		
	</script> 
</form>
</body>
</html>

