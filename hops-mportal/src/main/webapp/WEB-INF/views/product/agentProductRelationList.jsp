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
<form id="listForm" method="get">
<input id='page' type=hidden name='page'> 
<div class="line_bar">
<div style="float:right;">
	<input id="merchantId" type="hidden" name="merchantId" class="ipt" value="${merchantId}"> 
	<shiro:hasPermission name="merchantProduct:add_show">
	<input type="button" class="button"  value="创建商户产品信息" onclick="toSaveProductIdentityRelation()"/>
	</shiro:hasPermission>
	<shiro:hasPermission name="merchantProduct:adddefault_show">
	<input type="button" class="button"  value="商户默认产品" onclick="showProductIdentityRelation(${merchantId})"/>
	</shiro:hasPermission>
</div>
</div>
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><span>序号</span></th>
			<th><span>编号</span></th>
			<th><span>会员名称</span></th>
			<th><span>产品名称</span></th>
			<th><span>产品折扣</span></th>
			<th><span>折扣权重</span></th>
			<th><span>质量</span></th>
			<th><span>质量权重</span></th>
			<th><span>销售金额</span></th>
			<th><span>状态</span></th>
			<th><span>省份</span></th>
			<th><span>面值(元/M)</span></th>
			<th><span>面额(元)</span></th>
			<th><span>运营商</span></th>
			<th><span>城市</span></th>
			<th><span>外部编号</span> </th>
			<th><span>操作</span></th>
		</tr>
	<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
		<c:forEach items="${mlist}" var="DownProductRelation" varStatus="status">
			<tr>
				<td>${(page-1)*pageSize+status.index+1}</td>
				<td>${DownProductRelation.id}</td>
				<td>${DownProductRelation.identityName}</td>
				<td>${DownProductRelation.productName}</td>
				<td>${DownProductRelation.discount}</td>
				<td>${DownProductRelation.discountWeight}</td>
				<td>${DownProductRelation.quality}</td>
				<td>${DownProductRelation.qualityWeight}</td>
				<td>${DownProductRelation.price}</td>
					<c:if test="${DownProductRelation.status=='0'}">
					<td class="tdgb">
				          	关闭
					</c:if>
					<c:if test="${DownProductRelation.status=='1'}">
					<td>
				          	开启
					</c:if>
				</td>
				<td>${DownProductRelation.province}</td>
				<td>${DownProductRelation.parValue}
				<c:choose>
			       <c:when test="${DownProductRelation.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${DownProductRelation.businessType==1}">
			             	 M
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
				</td>
				<td>${DownProductRelation.displayValue}</td>
				<td>${DownProductRelation.carrierName}</td>
				<td>${DownProductRelation.city}</td>
				<td>${DownProductRelation.agentProdId}</td>
				<td>
<%-- 					<a href="${ctx}/product/toShowProductIdentityPrice?id=${DownProductRelation.id}">[查看]</a> --%>
<%-- 					<a href="${ctx}/product/toEditProductIdentityPrice?id=${DownProductRelation.id}">[修改]</a> --%>
				<shiro:hasPermission name="merchantProduct:edit_show">
					<a href="${ctx}/product/toEditAgentProductRelation?id=${DownProductRelation.id}&merchantId=${merchantId}&source=merchant">[修改]</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="merchantProduct:changestatus_show">	
					<c:if test="${DownProductRelation.status=='0'}">
						<a href="javascript:void(0)" onClick="updateProductStatus(${DownProductRelation.id},1,${merchantId},'merchant')">
						[开启]</a>
					</c:if>
					<c:if test="${DownProductRelation.status=='1'}">
						<a href="javascript:void(0)" onClick="updateProductStatus(${DownProductRelation.id},0,${merchantId},'merchant')">
                        [关闭]</a>
					</c:if>
				</shiro:hasPermission>
				<shiro:hasPermission name="merchantProduct:delete">
					<a href="javascript:void(0)" onClick="delProduct(${DownProductRelation.id},${merchantId})">[删除]</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</c:when>
			<c:otherwise>
					<tr>
						<td colspan="17">没数据</td>
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
<div class="line_bar">
<div style="float:right;padding:10px;">
		<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
	</div>
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
			
		function toSaveProductIdentityRelation(){
			var merchantId = $("#merchantId").val();
			window.location.href="${ctx}/product/toSaveAgentProductRelation?merchantId="+merchantId;
		}
		
		function showProductIdentityRelation(merchantId){
			window.location.href="${ctx}/product/showProductIdentityRelation?merchantId="+merchantId;
		}

		function delProduct(id,merchantid)
		{
			if (!confirm("确认要删除该产品吗?")) {
		        return false;
		    }
			window.location.href="${ctx}/product/deleteAgentProductRelationById?id="+id+"&merchantId="+merchantid+"&source=merchant";
		}
		
		function updateProductStatus(id,status,merchantId,source){
            $.ajax({
                url:'${ctx}/product/updateAgentProductRelationStatus',
                type: 'GET',
                data: "id="+id+"&status="+status+"&merchantId="+merchantId+"&source="+source, 
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

