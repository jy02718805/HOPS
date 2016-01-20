<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>产品批量操作列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
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
<form id="listForm" method="get" action="${ctx}/productOperation/productOperationBakList">
<div class="mg10"></div>
<div class="bar">
	<input id=page type=hidden name=page> 
	<input id=hasExpired type=hidden name=hasExpired>
	<div style="float:right;">
	</div>
</div>
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><span>序号</span></th>
			<th><span>操作名称</span></th>
			<th><span>开关标示</span></th>
			<th><span>运营商</span></th>
			<th><span>面值</span></th>
			<th><span>省份</span></th>
			<th><span>城市</span></th>
			<th><span>商户类型</span></th>
			<th><span>创建时间</span></th>
			<th><span>操作</span></th>
		</tr>
		<tbody class="listTable">
		<c:forEach items="${mlist}" var="productOperationHistoryBak" varStatus="status">
			<tr>
				<td>${(page-1)*pageSize+status.index+1}</td>
				<td><a onclick="toShowProductOperationHistory(${productOperationHistoryBak.id})">${productOperationHistoryBak.operationName}</a></td>
				<c:if test="${productOperationHistoryBak.operationFlag == '1'}">
					<td>
					打开
					</td>
				</c:if>
				<c:if test="${productOperationHistoryBak.operationFlag == '0'}">
					<td  class="tdgb">
					关闭
					</td>
				</c:if>
				<td>
				<span class="carrierInfo_${status.count}">${productOperationHistoryBak.carrierName}</span>
				</td>
				<td>
				<c:choose>
						<c:when test="${productOperationHistoryBak.parValue == 0}">
							全面额
						</c:when>
						<c:otherwise>
							${productOperationHistoryBak.parValue}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
				<span class="p_${status.count}">${productOperationHistoryBak.province}</span>
				</td>
				<td>
				<span class="c_${status.count}">${productOperationHistoryBak.city}</span></td>
				<td>
				<c:if test="${productOperationHistoryBak.merchantType == 'SUPPLY'}">
				供货商
				</c:if>
				<c:if test="${productOperationHistoryBak.merchantType == 'AGENT'}">
				代理商
				</c:if>
				<c:if test="${productOperationHistoryBak.merchantType == 'ALL'}">
				全部商户
				</c:if>
				</td>
				<td><fmt:formatDate value="${productOperationHistoryBak.createDate}" type="both" dateStyle="medium"/></td>
				<td>
				--
				</td>
			</tr>
		</c:forEach>
			</tbody>
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
			
		function toShowProductOperationHistory(id){
			window.location.href="${ctx}/productOperation/toShowProductOperationHistoryBak?historyId="+id;
		}
	</script> 
</form>
</body>
</html>

