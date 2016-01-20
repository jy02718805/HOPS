<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>订单详细信息</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<form id="listForm" method="get" action="${ctx}/transaction/orderList">
	<input id='page' type='hidden' name='page'>
	<div class="mg10"></div>
	<table class="input">
		<tr>
			<th>
				<span class="requiredField">*</span>订单号:
			</th>
			<td>
				${order.orderNo} 
			</td>
			<th>
				<span class="requiredField">*</span>下单时间:
			</th>
			<td>
				<fmt:formatDate value="${order.orderRequestTime}" type="both" dateStyle="medium"/>
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>订单完成时间:
			</th>
			<td>
				<fmt:formatDate value="${order.orderFinishTime}" type="both" dateStyle="medium"/>
			</td>
			<th>
				<span class="requiredField">*</span>商家名称:
			</th>
			<td>
				${order.merchantName}
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>商家订单号:
			</th>
			<td>
				${order.merchantOrderNo} 
			</td>
			<th>
				<span class="requiredField">*</span>产品编号:
			</th>
			<td>
				${order.productNo}
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>运营商/省/市:
			</th>
			<td>
				${order.ext1}/${order.ext2}/${order.ext3}
			</td>
			<th>
				<span class="requiredField">*</span>充值账号:
			</th>
			<td>
				${order.userCode}
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>面值（元）:
			</th>
			<td>
				${order.orderFee}
			</td>
			<th>
				<span class="requiredField">*</span>销售折扣/金额（元）:
			</th>
			<td>
				${order.productSaleDiscount}/${order.orderSalesFee}
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>待充值金额（元）:
			</th>
			<td>
				${order.orderWaitFee}	 
			</td>
			<th>
				<span class="requiredField">*</span>订单成功金额（元）:
			</th>
			<td>
				${order.orderSuccessFee}
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>订单状态:
			</th>
			<td>
				<c:choose>
			       <c:when test="${order.orderStatus==0}">
			             	 待付款
			       </c:when>
			       <c:when test="${order.orderStatus==1}">
			             	 待发货
			       </c:when>
			       <c:when test="${order.orderStatus==2}">
			             	 处理中
			       </c:when>
			       <c:when test="${order.orderStatus==3}">
			             	 成功
			       </c:when>
			       <c:when test="${order.orderStatus==5}">
			             	 部分成功
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
			<th>
				<span class="requiredField">*</span>通知状态:
			</th>
			<td>
				<c:choose>
				   <c:when test="${order.notifyStatus==0}">
			             	 无需通知
			       </c:when>
			       <c:when test="${order.notifyStatus==1}">
			             	等待通知
			       </c:when>
			       <c:when test="${order.notifyStatus==2}">
			             	 正在通知
			       </c:when>
			       <c:when test="${order.notifyStatus==3}">
			             	通知成功
			       </c:when>
			       <c:when test="${order.notifyStatus==4}">
			             	通知失败
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>					
				</c:choose>
			</td>
		</tr>
		<tr>
			<th>
				<span class="requiredField">*</span>手工处理工状态:
			</th>
			<td>
				<c:choose>
				   <c:when test="${order.manualFlag==0}">
			             	 无需手工处理
			       </c:when>
			       <c:when test="${order.manualFlag==1}">
			             	已转手工处理
			       </c:when>
			       <c:when test="${order.manualFlag==2}">
			             	手工处理完成
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>					
				</c:choose>
			</td>
			<th>
				<span class="requiredField">*</span>错误码:
			</th>
			<td>
				${order.errorCode}
			</td>
		</tr>
		<c:choose>
	       <c:when test="${order.orderStatus==4}">
	            <tr>
					<th>
						<span class="requiredField">*</span>关闭原因:
					</th>
					<td>
						${order.closeReason}
					</td>
				</tr>
	       </c:when>
		</c:choose>
	</table>
	<br/>
<div class="line_bar">	
	<div style="float:right;">
	<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
	</div>
</div>
</form>
</body>
</html>