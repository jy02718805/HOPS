<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>实体卡列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
<form id="listForm" method="get" action="${ctx}/transaction/orderList">
	<input id='page' type='hidden' name='page'>
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
				<span class="requiredField">*</span>面值（元/M）:
			</th>
			<td>
				${order.productFace}
				<c:choose>
			       <c:when test="${order.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${order.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
			</td>
			<th>
				<span class="requiredField">*</span>业务类型:
			</th>
			<td>
					<c:choose>
			       <c:when test="${order.businessType==0}">
			             	 话费
			       </c:when>
			       <c:when test="${order.businessType==1}">
			             	 流量
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	固话
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
			</td>
		</tr>
		
		<tr>
			<th>
				<span class="requiredField">*</span>面额（元）:
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
				<span class="requiredField">*</span>待充面值（M/元）:
			</th>
			<td>
				<c:choose>
						 <c:when test="${order.orderStatus==4}">
						 0
						 </c:when>
						 <c:otherwise>
						 <c:choose>
						 	  <c:when test="${order.businessType==0}">
						 	   ${order.orderWaitFee}
						 	  </c:when>
						  	  <c:when test="${order.businessType==1 and order.orderStatus ==3}">
			             		0
			     			 </c:when>
			     			 <c:otherwise>
			              	 ${order.productFace}
			   			    </c:otherwise>
						 </c:choose>
						 </c:otherwise>
						</c:choose>
						<c:choose>
			       <c:when test="${order.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${order.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
			</td>
			<th>
				<span class="requiredField">*</span>订单成功面值（元/M）:
			</th>
			<td>
					<c:choose>
						 <c:when test="${order.businessType==0}">
						${order.orderSuccessFee}
						 </c:when>
						<c:otherwise>
							<c:choose>
							 <c:when test="${order.orderStatus==3}">
							${order.productFace}
							 </c:when>
							<c:otherwise>
							0
							</c:otherwise>
							</c:choose>
						</c:otherwise>
						
						 </c:choose>
						 <c:choose>
			       <c:when test="${order.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${order.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
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
				<span class="requiredField">*</span>绑定次数:
			</th>
			<td>
				${order.bindTimes}	 
			</td>
		</tr>
		<tr>
			<c:choose>
		       <c:when test="${order.orderStatus==4}">
					<th>
						<span class="requiredField">*</span>关闭原因:
					</th>
					<td>
						${order.closeReason}
					</td>
		       </c:when>
			</c:choose>
		</tr>
	</table>
	
<div class="mg10"></div>
	<table id='listTable' class='list'>
		<tbody>
<!-- 		下单时间	 充值订单号	 商家编号	 运营商	 省份	 充值账号	 金额	 成功	 待充	 订单状态	 通知状态	 完成时间	 操作 -->
		<tr>
			<th><a class=sort href="javascript:;" name=userCode>发货编号</a> </th>
			<th><a class=sort href="javascript:;" name=userCode>手机号码</a> </th>
			<th><a class=sort href="javascript:;" name=productName>产品名称</a> </th>
			<th><a class=sort href="javascript:;" name=costDiscount>成本折扣</a> </th>
			<th><a class=sort href="javascript:;" name=costFee>成本金额</a> </th>
			<th><a class=sort href="javascript:;" name=merchantId>供货商</a> </th>
			<th><a class=sort href="javascript:;" name=merchantId>供货商订单号</a> </th>
			<th><a class=sort href="javascript:;" name=deliveryStartTime>开始发货时间</a> </th>
			<th><a class=sort href="javascript:;" name=nextQueryTime>下次查询时间</a> </th>
			<th><a class=sort href="javascript:;" name=queryFlag>查询标示</a> </th>
			<th><a class=sort href="javascript:;" name=queryTimes>查询次数</a> </th>
			<th><a class=sort href="javascript:;" name=deliveryStatus>发货状态</a> </th>
			<th><a class=sort href="javascript:;" name=deliveryResult>发货结果</a> </th>
		</tr>
		<c:forEach items="${order.deliverys}" var="delivery">
			<tr>
				<td>${delivery.deliveryId}</td>
				<td>${delivery.userCode}</td>
				<td>${delivery.productName}</td>
				<td>${delivery.costDiscount}</td>
				<td>${delivery.costFee}</td>
				<td>${delivery.merchantName}</td>
				<td>${delivery.supplyMerchantOrderNo}</td>
				<td><fmt:formatDate value="${delivery.deliveryStartTime}" type="both" dateStyle="medium"/></td>
				<td><fmt:formatDate value="${delivery.nextQueryTime}" type="both" dateStyle="medium"/></td>
				<td>
					<c:choose>
						<c:when test="${delivery.queryFlag==0}">
				             	 待查询
				       </c:when>
					   <c:when test="${delivery.queryFlag==1}">
				             	 待查询
				       </c:when>
				       <c:when test="${delivery.queryFlag==2}">
				             	 查询中
				       </c:when>
				       <c:when test="${delivery.queryFlag==3}">
				             	 查询结束
				       </c:when>
				       <c:when test="${delivery.queryFlag==4}">
				             	    需要进行手工触发查询
				       </c:when>
				       <c:otherwise>
				              	未知
				       </c:otherwise>
					</c:choose>
				</td>
				<td>${delivery.queryTimes}</td>
				<td>
					<c:choose>
					   <c:when test="${delivery.deliveryStatus==0}">
				             	 等待发货中
				       </c:when>
				       <c:when test="${delivery.deliveryStatus==1}">
				             	准备发货
				       </c:when>
				       <c:when test="${delivery.deliveryStatus==2}">
				             	已经发货
				       </c:when>
				       <c:when test="${delivery.deliveryStatus==3}">
				             	发货失败
				       </c:when>
				       <c:when test="${delivery.deliveryStatus==4}">
				             	发货成功
				       </c:when>
				       <c:otherwise>
				              	未知
				       </c:otherwise>					
					</c:choose>
				</td>
				<td>
				<c:if test="${not empty delivery.deliveryResult}">
					<a href="javascript:void(0);" onclick="deliveryRecrod(${delivery.deliveryId})">
					详细信息
					</a>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<div class="line_bar">
	<SPAN>资金流向</SPAN>
</div>
<table id=listTable class=list>
		<tbody>
<!-- 		创建时间  类型  金额    付款方   收款方  备注   -->
		<tr>
			<th><a class=sort href="javascript:;" name=transactionId>交易ID</a> </th>
			<th><a class=sort href="javascript:;" name=merchantId>创建时间 </a> </th>
			<th><a class=sort href="javascript:;" name=deliveryStatus>金额(元)</a> </th>
			<th><a class=sort href="javascript:;" name=costDiscount>付款方</a> </th>
			<th><a class=sort href="javascript:;" name=costDiscount>收款方</a> </th>
			<th><a class=sort href="javascript:;" name=costFee>备注</a> </th>
		</tr>
		
			
			<c:forEach items="${transactionHistoryList}" var="transactionHistory">	
			<tr>
				<td>
				<shiro:hasPermission name="order:transactionHistory_view">
					<a href="javascript:void(0);"
									onclick="clickId(${transactionHistory.transactionId},
									'<fmt:formatDate value="${transactionHistory.createDate}" type="both" dateStyle="medium" pattern="yyyy-MM-dd HH:00:00"/>',
									'<fmt:formatDate value="${transactionHistory.createDate}" type="both" dateStyle="medium" pattern="yyyy-MM-dd HH:59:59"/>');">
										${transactionHistory.transactionId}</a>
				</shiro:hasPermission>
				<shiro:lacksPermission name="order:transactionHistory_view">	
					${transactionHistory.transactionId}
				</shiro:lacksPermission>					
				</td>
				<td><fmt:formatDate value="${transactionHistory.createDate}" type="both" dateStyle="medium"/></td>
				<td>${transactionHistory.amt}</td>
				<td>
				<c:choose>
					<c:when test="${transactionHistory.payerAccountType=='148000'}">
						<shiro:hasPermission name="agentAccount:view">
							<a href="${ctx}/account/showAccount?accountId=${transactionHistory.payerAccountId}&accountTypeId=${transactionHistory.payerAccountType}&transactionNo=${transactionHistory.transactionNo}">
							${transactionHistory.payerName}（${transactionHistory.payerTypeName}）</a>
						</shiro:hasPermission>
						<shiro:lacksPermission name="agentAccount:view">	
							${transactionHistory.payerName}（${transactionHistory.payerTypeName}）
						</shiro:lacksPermission>		
					</c:when>
					<c:when test="${transactionHistory.payerAccountType=='149000'}">
						<shiro:hasPermission name="supplyAccount:view">
							<a href="${ctx}/account/showAccount?accountId=${transactionHistory.payerAccountId}&accountTypeId=${transactionHistory.payerAccountType}&transactionNo=${transactionHistory.transactionNo}">
							${transactionHistory.payerName}（${transactionHistory.payerTypeName}）</a>
						</shiro:hasPermission>
						<shiro:lacksPermission name="supplyAccount:view">	
							${transactionHistory.payerName}（${transactionHistory.payerTypeName}）
						</shiro:lacksPermission>
					</c:when>
					<c:otherwise>
			            <shiro:hasPermission name="sysAccount:view">
							<a href="${ctx}/account/showAccount?accountId=${transactionHistory.payerAccountId}&accountTypeId=${transactionHistory.payerAccountType}&transactionNo=${transactionHistory.transactionNo}">
							${transactionHistory.payerName}（${transactionHistory.payerTypeName}）</a>
						</shiro:hasPermission>
						<shiro:lacksPermission name="sysAccount:view">	
							${transactionHistory.payerName}（${transactionHistory.payerTypeName}）
						</shiro:lacksPermission>
			        </c:otherwise>
				</c:choose>
				</td>
				<td>
				<c:choose>
					<c:when test="${transactionHistory.payeeAccountType=='148000'}">
						<shiro:hasPermission name="agentAccount:view">
							<a href="${ctx}/account/showAccount?accountId=${transactionHistory.payeeAccountId}&accountTypeId=${transactionHistory.payeeAccountType}&transactionNo=${transactionHistory.transactionNo}">
							${transactionHistory.payeeName}（${transactionHistory.payeeTypeName}）</a>
						</shiro:hasPermission>
						<shiro:lacksPermission name="agentAccount:view">	
							${transactionHistory.payeeName}（${transactionHistory.payeeTypeName}）
						</shiro:lacksPermission>		
					</c:when>
					<c:when test="${transactionHistory.payeeAccountType=='149000'}">
						<shiro:hasPermission name="supplyAccount:view">
							<a href="${ctx}/account/showAccount?accountId=${transactionHistory.payeeAccountId}&accountTypeId=${transactionHistory.payeeAccountType}&transactionNo=${transactionHistory.transactionNo}">
							${transactionHistory.payeeName}（${transactionHistory.payeeTypeName}）</a>
						</shiro:hasPermission>
						<shiro:lacksPermission name="supplyAccount:view">	
							${transactionHistory.payeeName}（${transactionHistory.payeeTypeName}）
						</shiro:lacksPermission>
					</c:when>
					<c:otherwise>
			            <shiro:hasPermission name="sysAccount:view">
							<a href="${ctx}/account/showAccount?accountId=${transactionHistory.payeeAccountId}&accountTypeId=${transactionHistory.payeeAccountType}&transactionNo=${transactionHistory.transactionNo}">
							${transactionHistory.payeeName}（${transactionHistory.payeeTypeName}）</a>
						</shiro:hasPermission>
						<shiro:lacksPermission name="sysAccount:view">	
							${transactionHistory.payeeName}（${transactionHistory.payeeTypeName}）
						</shiro:lacksPermission>
			        </c:otherwise>
				</c:choose>
				</td>
				<td>${transactionHistory.descStr}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<c:choose>
			<c:when test="${fn:length(oaohList) > 0}">
			<div class="line_bar">
	<SPAN>人工处理记录</SPAN>
	</div>
	
	<table  class='list'>
		<tbody>
		<tr>
			<th><a class=sort href="javascript:;">操作人 </a> </th>
			<th><a class=sort href="javascript:;">更新时间</a> </th>
			<th><a class=sort href="javascript:;">订单号</a> </th>
			<th><a class=sort href="javascript:;">处理原因</a> </th>
		</tr>
		<c:forEach items="${oaohList}" var="orderApplyOperateHistory">
			<tr>
				<td>
				${orderApplyOperateHistory.operatorName}
				</td>
				<td>
					<fmt:formatDate value="${orderApplyOperateHistory.createDate}" type="both" dateStyle="medium"/>
				</td>
				<td>
					${orderApplyOperateHistory.orderNo}
				</td>
				<td>
					<c:choose>
					   <c:when test="${orderApplyOperateHistory.action=='1'}">
				             	 订单超时
				       </c:when>
				       <c:when test="${orderApplyOperateHistory.action=='2'}">
				             	订单超过最大绑定次数
				       </c:when>
				       <c:when test="${orderApplyOperateHistory.action=='3'}">
				             	预成功订单超时
				       </c:when>
				       <c:when test="${orderApplyOperateHistory.action=='4'}">
				             	成功订单审核失败
				       </c:when>
				       <c:when test="${orderApplyOperateHistory.action=='5'}">
				             	有未完成的发货记录
				       </c:when>
				       <c:when test="${orderApplyOperateHistory.action=='6'}">
				             	无未完成的发货记录
				       </c:when>
				       <c:when test="${orderApplyOperateHistory.action=='7'}">
				             	强制审核失败
				       </c:when>
				       <c:when test="${orderApplyOperateHistory.action=='8'}">
				             	换号补充
				       </c:when>
				       <c:otherwise>
				              	${orderApplyOperateHistory.action}
				       </c:otherwise>					
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
			</c:when>
		</c:choose>
	
<div class="line_bar">	
	<div style="float:right;">
	<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
	</div>
</div>
 	<script type="text/javascript" language="javascript">
 		function deliveryRecrod(deliveryId){
 			//window.location.href =';
 			var temp='false';
 			var height='500';
 			var width='800';
 			var iTop = (window.screen.availHeight-30-height)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-width)/2;
			window.open ('${ctx}/transaction/deliveryresult?deliveryId='+deliveryId,'newwindow','height='+height+',width='+width+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
 		}
 		
 		function clickId(transactionId,beginDate,endDate){
			window.location.href="${ctx}/accountHistory/accountTransactionLogList?transactionId="+transactionId+"&beginDate="+beginDate+"&endDate="+endDate;
		}
	</script> 
</form>
</body>
</html>