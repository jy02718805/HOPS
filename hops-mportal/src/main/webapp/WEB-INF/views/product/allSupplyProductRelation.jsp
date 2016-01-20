<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>商户产品列表 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
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
<form id="inputForm" action="${ctx}/product/allSupplyProductRelation">
<div class="line_bar">
	<input id='page' type=hidden name='page'/> 
	供货商商户:<select name="identityId" id="identityId" class="select w100">
			<option value="-1">请选择</option>
			<c:forEach items="${upMerchants}" var="merchant">
				<c:choose>
					<c:when test="${productRelationVO.identityId==merchant.id}">
			             	<option value="${merchant.id}" selected="selected">${merchant.merchantName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${merchant.id}">${merchant.merchantName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		 </select>
	运营商 :<select name="carrierInfo" id="carrierInfo" class="select w80">
					<option value="-1">请选择</option>
					<c:forEach items="${carrierInfos}" var="carrierInfo">
						<c:choose>
							<c:when test="${productRelationVO.carrierInfo==carrierInfo.carrierNo}">
					             	<option value="${carrierInfo.carrierNo}" selected="selected">${carrierInfo.carrierName}</option>
					        </c:when>
					        <c:otherwise>
					              	<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
					        </c:otherwise>
					    </c:choose>
					</c:forEach>
				 </select>
	省份:<select name="province" id="province" class="select w80">
			<option value="-1">请选择</option>
			<c:forEach items="${provinces}" var="province">
				<c:choose>
					<c:when test="${province.provinceId==productRelationVO.province}">
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
	面     值:<input type="text" id="parValue" name="parValue" class="ipt w80" value="${productRelationVO.parValue}"/>
    
    折扣范围:<input type="text" id="discount" name="discount" value="${productRelationVO.discount}" class="ipt w80" />-
	 <input type="text" id="discount2" name="discount2" value="${productRelationVO.discount2}" class="ipt w80"/>

    
	<div style="float:right;">
	
	<shiro:user>
	<input type="button" class="button"  value="查询" onclick="queryUpProductRelation()"/>
	</shiro:user>
	<shiro:hasPermission name="supplyProduct:add_show">
	<input type="button" class="button"  value="创建商户产品" onclick="toSaveUpProductRelation()"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="supplyProduct:addlist_show">
	<input type="button" class="button"  value="商户产品批量创建" onclick="toCloneUpMerchantProduct()"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="supplyProduct:editlist_show">
	<input type="button" class="button"  value="批量修改" onclick="changesUpProductRelation()"/>
	</shiro:hasPermission>
	</div>
</div>
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><a class=sort href="javascript:;" name=identityName>序列</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>商户名称</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>产品名称</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>产品折扣</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>产品质量</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>销售金额</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>状态</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>产品等级</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>面值(元/M)</a></th>
			<th><a class=sort href="javascript:;" name=identityName>面额(元)</a></th>
			<th><a class=sort href="javascript:;" name=identityName>供货商产品编号</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>操作</a> </th>
		</tr>
		<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
		<c:forEach items="${mlist}" var="UpProductRelation"  varStatus="count">
			<tr>
				<td>${(page-1)*pageSize+count.index+1}</td>
				<td>${UpProductRelation.identityName}</td>
				<td>${UpProductRelation.productName}</td>
				<td>${UpProductRelation.discount}</td>
				<td>${UpProductRelation.quality}</td>
				<td>${UpProductRelation.price}</td>
				<c:if test="${UpProductRelation.status=='0'}">
					<td  class="tdgb">
						关闭
					</td>
				</c:if>
				<c:if test="${UpProductRelation.status=='1'}">
					<td>
						开启
					</td>
				</c:if>
				<td>
					<c:choose>
					       <c:when test="${UpProductRelation.merchantLevel==1}">
					             	优质产品
					       </c:when>
					       <c:when test="${UpProductRelation.merchantLevel==2}">
					             	中等产品
					       </c:when>
					       <c:when test="${UpProductRelation.merchantLevel==3}">
					             	普通产品
					       </c:when>
					       <c:otherwise>
					       			未知
					       </c:otherwise>
					</c:choose>
				</td>
				
				<td>
				${UpProductRelation.parValue}
					<c:choose>
			       <c:when test="${UpProductRelation.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${UpProductRelation.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${UpProductRelation.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
				</td>
				<td>
				${UpProductRelation.displayValue}
				</td>
				
				<td>${UpProductRelation.supplyProdId}</td>
				<td>
<%-- 					<a href="${ctx}/product/toShowProductIdentityPrice?id=${UpProductRelation.id}">[查看]</a> --%>
 				<shiro:hasPermission name="supplyProduct:edit_show">
 					<a href="${ctx}/product/toEditSupplyProductRelation?id=${UpProductRelation.id}&merchantId=${UpProductRelation.identityId}&source=list">[修改]</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="supplyProduct:changestatus_show">
					<c:if test="${UpProductRelation.status=='0'}">
						<a href="javascript:void(0)" onClick="updateProductStatus(${UpProductRelation.id},1,${UpProductRelation.productId},${UpProductRelation.identityId},'list')">
						[开启]</a>
					</c:if>
					<c:if test="${UpProductRelation.status=='1'}">
						<a 
						href="javascript:void(0)" 
						onClick="updateProductStatus(${UpProductRelation.id},0,${UpProductRelation.productId},${UpProductRelation.identityId},'list')" >[关闭]</a>
					</c:if>
				</shiro:hasPermission>
				<shiro:hasPermission name="supplyProduct:delete">
					<a href="javascript:void(0)" onClick="delProduct(${UpProductRelation.id},${UpProductRelation.identityId})">[删除]</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</c:when>
			<c:otherwise>
					<tr>
						<td colspan="12">没数据</td>
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
			$("#inputForm").submit();
			}
			
		function queryUpProductRelation(){
			 var parValue=$("#parValue").val();
	            if(parValue!=""&&parValue!=null)
	            {
	                if(!checkPoint(parValue)){
	                    alert("面值格式错误，只能是数字！");
	                    document.getElementById("parValue").focus(); 
	                    return false;
	                }
	            }
	            
	            var discount=$("#discount").val();
	            if(discount!=""&&discount!=null)
	            {
	                if(!checkPoint(discount)){
	                    alert("折扣范围格式错误，只能是数字！");
	                    document.getElementById("discount").focus(); 
	                    return false;
	                }
	            }
	            
	            var discount2=$("#discount2").val();
	            if(discount2!=""&&discount2!=null)
	            {
	                if(!checkPoint(discount2)){
	                    alert("折扣范围格式错误，只能是数字！");
	                    document.getElementById("discount2").focus(); 
	                    return false;
	                }
	            }
			$('#inputForm').submit();
		}
		

		function checkPoint(str){
            // var reg=/^(\d)*(\.\d{1,2})?$/;
			 var reg=/^(\d)*(\.\d{1,4})?$/;
            if(reg.test(str)){
             return true;
            }else{
             return false;
            }
        }
		
		
		function toSaveUpProductRelation(){
			window.location.href="${ctx}/product/toSaveSupplyProductRelation";
		}
		
		function toCloneUpMerchantProduct(){
			window.location.href="${ctx}/product/toCloneSupplyMerchantProduct";
		}
		
		function changesUpProductRelation(){
			$("#inputForm").attr("action","${ctx}/product/changesSupplyProductRelation");
			$('#inputForm').submit();
		}

		function delProduct(id,merchantid)
		{
			if (!confirm("确认要删除该产品吗?")) {
		        return false;
		    }
			window.location.href="${ctx}/product/deleteSupplyProductRelationById?id="+id+"&merchantId="+merchantid+"&source=list";
		}
		
		function updateProductStatus(id,status,productId,merchantId,source){
			$.ajax({
				url:'updateSupplyProductRelationStatus',
				type: 'GET',
                data: "id="+id+"&status="+status+"&productId="+productId+"&merchantId="+merchantId+"&source="+source, 
                contentType:'application/text;charset=UTF-8', 
                success: function(result) {
                      if(result=='true'){
                      	alert('操作成功!');
                      	window.location.reload();
                     }else{
                     	alert('操作失败!');
                  }
               }
			 });
		}
	</script> 
</form>
</body>
</html>

