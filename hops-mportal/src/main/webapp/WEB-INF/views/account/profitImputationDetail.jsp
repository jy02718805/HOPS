<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>账户日志列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
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
<DIV class=path>
		<A href="#">首页</A> »利润归集明细: <SPAN>(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</SPAN>
</DIV>
<form id="listForm" method="get" action="">
	<input id='page' type=hidden name='page'/>
	<!-- <label>利润归集明细:(归集时间  <fmt:formatDate value="${transactionHistory.createDate}" type="both" dateStyle="full"/>)</label>
	<br/>
	<label>商户名称:</label>
	<input type="text" id='orderNo' name="orderNo" value="${transaction.orderNo}" />
	&nbsp;&nbsp;
	<label>系统商户利润账户:</label>
	<input type="text" id='profit_tatal' readonly="true"  disabled="true" name="profit_tatal" value="" />
	<br/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<label>当前归集利润:</label>
	<input type="text" id='orderNo' name="orderNo" value="${transaction.orderNo}" />
	&nbsp;&nbsp;
	<label>系统商户利润账户余额:</label>
	<input type="text" id='profit_tatal' readonly="true"  disabled="true" name="profit_tatal" value="" />
	
	<label>时间区间:</label>
	至
	&nbsp;
	<label>付款账户:</label> -->
<%-- 	<input type="text" id="payerAccountId" name="payerAccountId" value="${transaction.payerAccountId}"/> --%>
<!-- 	&nbsp; -->
<!-- 	<label>收款账户:</label> -->
<%-- 	<input type="text" id="payeeAccountId" name="payeeAccountId" value="${transaction.payeeAccountId}"/> --%>
	&nbsp;
	<label>对比交易利润：: (（总利润：     元）)</label>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
	
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><a class=sort href="javascript:;" name=orderRequestTime>下单时间</a> </th>
			<th><a class=sort href="javascript:;" name=orderNo>订单号</a> </th>
			<th><a class=sort href="javascript:;" name=merchantName>代理商名称</a> </th>
			<th><a class=sort href="javascript:;" name=merchantName>销售金额</a> </th>
			<th><a class=sort href="javascript:;" name=ext1>成本金额</a> </th>
			<th><a class=sort href="javascript:;" name=orderStatus>订单状态</a> </th>
			<th><a class=sort href="javascript:;" name=orderFinishTime>完成时间</a> </th>
		</tr>

			<c:forEach items="${mlist}" var="order" varStatus="status">
				<tr>
					<td><fmt:formatDate value="${order.orderRequestTime}" type="both" dateStyle="medium"/></td>
					<td>
					<a href="${ctx}/transaction/showOrderDetail?orderNo=${order.orderNo}">${order.orderNo}</a>
				</td>
					<td>${order.merchantName}</td>
					<td>${order.order_sales_fee}</td>
					<c:forEach var="delivery" items="${order.deliverys}">
						<td>${delivery.costFee}</td>
					</c:forEach>
					<td>
					<c:choose>
					       <c:when test="${order.orderStatus==0}">
					             	 待付款
					       </c:when>
					       <c:when test="${order.orderStatus==1}">
					             	 待发货
					       </c:when>
					       <c:when test="${order.orderStatus==2}">
					             	 发货中
					       </c:when>
					       <c:when test="${order.orderStatus==3}">
					             	 成功
					       </c:when>
					       <c:when test="${order.orderStatus==5}">
					             	 部分成功
					       </c:when>
					       <c:when test="${order.orderStatus==6}">
					             	 部分成功,发货中
					       </c:when>
					       <c:when test="${order.orderStatus==4}">
					             	 失败
					       </c:when>
					       <c:when test="${order.orderStatus==91}">
					             	 部分失败
					       </c:when>
					       <c:otherwise>
					              	未知
					       </c:otherwise>
					</c:choose>
				</td>
				<td><fmt:formatDate value="${order.orderFinishTime}" type="both" dateStyle="medium"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pager" style="float:right;"></div>  
  	<br/>
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
			
			function queryProfit(){
				
			}
	</script> 
</form>
</body>
</html>

