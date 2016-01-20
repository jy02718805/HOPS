<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>实体卡列表</title>
</head>
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
<div class="mg10"></div>
<div class="line_bar">
	<input id=orderStatus type=hidden name=orderStatus> 
	<input id=paymentStatus type=hidden name=paymentStatus> 
	<input id=shippingStatus type=hidden name=shippingStatus> 
	<input id='page' type='hidden' name='page'> 
	<input id=hasExpired type=hidden name=hasExpired> 
	<div style="float:right;">
	<shiro:hasPermission name="productType:add_show">
<!-- 	&nbsp;<input type="button" class="button" value="创建产品类型" onclick="toSaveProductType()"/> -->
	</shiro:hasPermission>
	</div>
</div>
	<table id=listTable class=list>
		<tbody>
		<tr>
		<th><SPAN>序列</SPAN> </th>
			<th><SPAN>产品类型名称</SPAN> </th>
<!-- 			<th><SPAN>操作</SPAN> </th> -->
		</tr>
		<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
		<c:forEach items="${mlist}" var="productType"  varStatus="count">
			<tr>
			<td>${(page-1)*pageSize+count.index+1}</td>
				<td>${productType.productTypeName}</td>
<!-- 				<td> -->
<%-- 				<shiro:hasPermission name="productType:edit_show"> --%>
<%-- 					<a href="${ctx}/product/toEditProductType?typeId=${productType.typeId}">修改</a> --%>
<%-- 				</shiro:hasPermission> --%>
<%-- 				<shiro:hasPermission name="productType:delete"> --%>
<%-- 					<a onclick="deleteProductType(${productType.typeId})">删除</a> --%>
<%-- 				</shiro:hasPermission> --%>
<!-- 				</td> -->
			</tr>
		</c:forEach>
		</c:when>
			<c:otherwise>
					<tr>
						<td colspan="3">没数据</td>
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
			
		function toSaveProductType(){
			window.location.href="${ctx}/product/toSaveProductType";
		}
		
		function deleteProductType(typeId){
			var gnl=confirm("确定要删除吗?"); 
			if (gnl==true){ 
				window.location.href="${ctx}/product/deleteProductType?typeId="+typeId;
			}
		}
	</script> 
</form>
</body>
</html>

