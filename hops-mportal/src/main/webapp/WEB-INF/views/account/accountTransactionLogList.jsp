<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>账户日志列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<form id="listForm" method="post" action="${ctx}/accountHistory/accountTransactionLogList">
	<input id='page' type=hidden name='page'/>
	<div class="line_bar">
	<label>时间段:</label> <input id="beginDate" class="ipt w150" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-{%M-3}-%d',maxDate:'#F{$dp.$D(\'endDate\')|| \'%y-%M-%d\'}'});" name="beginDate" value='${transaction.beginDate}'></input>
		 至
		 <input id="endDate" class="ipt w150" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginDate\') || \'%y-{%M-3}-%d\'}',maxDate:'%y-%M-%d'});" name="endDate" value='${transaction.endDate}'></input>
	付款账户类型:<select name="payerAccountType" id="payerAccountType"  class="select w150">
<option value="">请选择</option>
<c:forEach items="${accountTypelist}" var="accountType">
	<c:choose>
       <c:when test="${accountType.accountTypeId==transaction.payerAccountType}">
             	<option selected="selected" value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
       </c:when>
       <c:otherwise>
              	<option value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
       </c:otherwise>
	</c:choose>
</c:forEach>
</select>
收款账户类型:<select name="payeeAccountType" id="payeeAccountType"  class="select w150">
<option value="">请选择</option>
<c:forEach items="${accountTypelist}" var="accountType">
	<c:choose>
       <c:when test="${accountType.accountTypeId==transaction.payeeAccountType}">
             	<option selected="selected" value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
       </c:when>
       <c:otherwise>
              	<option value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
       </c:otherwise>
	</c:choose>
</c:forEach>
</select>
	</div>
	<div class="line_bar">
<!-- 	<label>付款账户:</label> -->
<%-- 	<input type="text" id="payerAccountId" name="payerAccountId" value="${transaction.payerAccountId}"/> --%>
<!-- 	&nbsp; -->
<!-- 	<label>收款账户:</label> -->
<%-- 	<input type="text" id="payeeAccountId" name="payeeAccountId" value="${transaction.payeeAccountId}"/> --%>
 <label>交易类型:</label><select name="type" id="type" class="select">
				<c:choose>
					<c:when test="${transaction.type==''}">
						<option value="" selected="selected">请选择</option>
					</c:when>
					<c:otherwise>
						<option value="">请选择</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='1'}">
						<option value="1" selected="selected">代理商下单</option>
					</c:when>
					<c:otherwise>
						<option value="1">代理商下单</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='2'}">
						<option value="2" selected="selected">订单成功，供货商扣款</option>
					</c:when>
					<c:otherwise>
						<option value="2">订单成功，供货商扣款</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='3'}">
						<option value="3" selected="selected">订单成功转利润</option>
					</c:when>
					<c:otherwise>
						<option value="3">订单成功转利润</option>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${transaction.type=='4'}">
						<option value="4" selected="selected">利润归集</option>
					</c:when>
					<c:otherwise>
						<option value="4">利润归集</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='5'}">
						<option value="5" selected="selected"> 返佣清算</option>
					</c:when>
					<c:otherwise>
						<option value="5"> 返佣清算</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='6'}">
						<option value="6" selected="selected">返佣资金回滚</option>
					</c:when>
					<c:otherwise>
						<option value="6">返佣资金回滚</option>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${transaction.type=='7'}">
						<option value="7" selected="selected">确认利润</option>
					</c:when>
					<c:otherwise>
						<option value="7">确认利润</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='8'}">
						<option value="8" selected="selected">确认返佣</option>
					</c:when>
					<c:otherwise>
						<option value="8">确认返佣</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='9'}">
						<option value="9" selected="selected">余款收回</option>
					</c:when>
					<c:otherwise>
						<option value="9">余款收回</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='10'}">
						<option value="10" selected="selected">退款</option>
					</c:when>
					<c:otherwise>
						<option value="10">退款</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='13'}">
						<option value="13" selected="selected">佣金收回</option>
					</c:when>
					<c:otherwise>
						<option value="13">佣金收回</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transaction.type=='14'}">
						<option value="14" selected="selected">信用平账</option>
					</c:when>
					<c:otherwise>
						<option value="14">信用平账</option>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${transaction.type=='15'}">
						<option value="15" selected="selected">加款</option>
					</c:when>
					<c:otherwise>
						<option value="15">加款</option>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${transaction.type=='16'}">
						<option value="16" selected="selected">减款</option>
					</c:when>
					<c:otherwise>
						<option value="16">减款</option>
					</c:otherwise>
				</c:choose>
				
				
			</select>
	<label>交易编号:</label>
	<input type="text" id='transactionNo' name="transactionNo" value="${transaction.transactionNo}"  class="ipt w100"/>
	<label>付款人:</label>
	<input type="text" id='payerIdentityName' name="payerIdentityName" value="${transaction.payerIdentityName}" class="ipt w100" />
	<label>收款人:</label>
	<input type="text" id='payeeIdentityName' name="payeeIdentityName" value="${transaction.payeeIdentityName}" class="ipt w100"/>

<div style="float:right;">
<shiro:user>
	&nbsp;<input type="button" class="button" value="查询" onclick="queryLog()" class="button"/>
</shiro:user>
</div>

</div>
	<table id=listTable class=list>
		<tbody>
			<tr>
			<th s><a class=sort href="javascript:;" name=identityName>序列</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>交易类型</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>交易ID</a> </th>
				<th style='width: 50px'><a class=sort href="javascript:;" name=identityName>付款人</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>付款账户</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>付款账户类型</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>付款账户种类</a> </th>
			<!--	<th><a class=sort href="javascript:;" name=identityName>付款账户状态</a> </th>-->
				<th style='width: 50px'><a class=sort href="javascript:;" name=identityName>收款人</a></th>
				<th><a class=sort href="javascript:;" name=identityName>收款账户</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>收款账户类型</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>收款账户种类</a> </th>
				<!--	<th><a class=sort href="javascript:;" name=identityName>收款账户状态</a> </th>-->
				<th><a class=sort href="javascript:;" name=identityName>交易编号</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>交易金额</a> </th>
				
				<th><a class=sort href="javascript:;" name=identityName>创建时间</a> </th>
			</tr>
			<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
			<c:forEach items="${mlist}" var="transactionHistory"  varStatus="count">
			<tr>
			<td>${(page-1)*pageSize+count.index+1}</td>
			
			<td>
				<c:choose>
					   <c:when test="${transactionHistory.TYPE=='1'}">
				             	 代理商下单
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='2'}">
				             	订单成功，供货商扣款
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='3'}">
				             	 订单成功转利润
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='4'}">
				             	利润归集
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='5'}">
				             	 返佣清算
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='6'}">
				             	返佣资金回滚
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='7'}">
				             	 确认利润
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='8'}">
				             	 确认返佣
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='9'}">
				             	 余款收回
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='10'}">
				             	 退款
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='13'}">
				             	 佣金收回
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='14'}">
				             	信用平账
				       </c:when>
				        <c:when test="${transactionHistory.TYPE=='15'}">
				             	加款
				       </c:when>
				        <c:when test="${transactionHistory.TYPE=='16'}">
				             	减款
				       </c:when>
				       <c:otherwise>
				              	--
				       </c:otherwise>					
				</c:choose>
				</td>
				<td>
				<a href="javascript:void(0);" onclick="clickId('${transactionHistory.TRANSACTIONNO}','${transactionHistory.PAYERACCOUNTID}');">${transactionHistory.TRANSACTIONID}</a>
				</td>
				<td>${transactionHistory.PAYERIDENTITYNAME}</td>
				<td>${transactionHistory.PAYERACCOUNTID}</td>
				<td>${transactionHistory.PAYERACCOUNTTYPENAME}</td>
				<td> 
				<c:choose>
					   <c:when test="${transactionHistory.PAYERTYPEMODEL=='FUNDS'}">
				             	 资金
				       </c:when>
				       <c:otherwise>
				              	实体卡
				       </c:otherwise>					
				</c:choose>
				</td>
				<td>${transactionHistory.PAYEEIDENTITYNAME}</td>
				<td>${transactionHistory.PAYEEACCOUNTID}</td>
				<td>${transactionHistory.PAYEEACCOUNTTYPENAME}</td>
				<td> 
				<c:choose>
					   <c:when test="${transactionHistory.PAYEETYPEMODEL=='FUNDS'}">
				             	 资金
				       </c:when>
				       <c:otherwise>
				              	实体卡
				       </c:otherwise>					
				</c:choose>
				</td>
				<td>
				<c:choose>
					   <c:when test="${transactionHistory.TYPE=='1'||transactionHistory.TYPE=='2'||transactionHistory.TYPE=='3'||transactionHistory.TYPE=='10'}">
				             	<a href="javascript:void(0);" onclick="clickIdOrder('${transactionHistory.TRANSACTIONNO}','order');">
									${transactionHistory.TRANSACTIONNO}</a>
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='5'||transactionHistory.TYPE=='6'||transactionHistory.TYPE=='8'||transactionHistory.TYPE=='9'||transactionHistory.TYPE=='13'}">
				             	<a href="javascript:void(0);" onclick="clickIdOrder('${transactionHistory.TRANSACTIONNO}','rebate');">
									${transactionHistory.TRANSACTIONNO}</a>
				       </c:when>
				       <c:when test="${transactionHistory.TYPE=='4' || transactionHistory.TYPE=='14'}">
				             	<a href="javascript:void(0);" onclick="clickIdOrder('${transactionHistory.TRANSACTIONNO}','profit');">
									${transactionHistory.TRANSACTIONNO}</a>
				       </c:when>
				       <c:otherwise>
				              	${transactionHistory.TRANSACTIONNO}
				       </c:otherwise>					
				</c:choose>
				</td>
				<td>${transactionHistory.AMT}</td>
				<td>
					${transactionHistory.CREATEDATE}
				</td>
			</tr>
		</c:forEach>
			</c:when>
			<c:otherwise>
					<tr>
						<td colspan="14">没数据</td>
					</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	<div class="line_pages">
	<div style="float:left;">
  	&nbsp; 显示条数：
  	<select name="pageSize" id="pageSize" class="select">
		<option value="10" <c:choose><c:when test="${pageSize==10}">selected=selected</c:when></c:choose>>10</option>
		<option value="20" <c:choose><c:when test="${pageSize==20}">selected=selected</c:when></c:choose>>20</option>
		<option value="30" <c:choose><c:when test="${pageSize==30}">selected=selected</c:when></c:choose>>30</option>
		<option value="50" <c:choose><c:when test="${pageSize==50}">selected=selected</c:when></c:choose>>50</option>
		<option value="100" <c:choose><c:when test="${pageSize==100}">selected=selected</c:when></c:choose>>100</option>
		<option value="500" <c:choose><c:when test="${pageSize==500}">selected=selected</c:when></c:choose>>500</option>
		<option value="1000" <c:choose><c:when test="${pageSize==1000}">selected=selected</c:when></c:choose>>1000</option>	
	</select>&nbsp; 条
  </div>
  	<div id="pager" style="float:right;"></div>  
  	<div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
  	</div>
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
</script> 
<script type="text/javascript" language="javascript">	
			function queryLog(){
				var beginDate=$("#beginDate").val();
				if(beginDate==""||beginDate==null)
				{
		    		alert("开始时间不能为空！");
		    		document.getElementById("beginDate").focus(); 
		    		return false;
				}
				var endDate=$("#endDate").val();
				if(endDate==""||endDate==null)
				{
		    		alert("结束时间不能为空！");
		    		document.getElementById("endDate").focus(); 
		    		return false;
				}
				$("#listForm").submit();
			}
			
			function clickId(transactionNo,accountId){
				var beginDate=$("#beginDate").val();
				var endDate=$("#endDate").val();
				window.location.href="${ctx}/accountHistory/accountBalanceHistoryList?transactionNo="+transactionNo+"&accountId="+accountId+"&beginDate="+beginDate+"&endDate="+endDate;
			}
			
			function clickIdOrder(transactionId,type)
			{
				if(type=='order')
				{
					window.location.href="${ctx}/transaction/showOrderDetail?orderNo="+transactionId;
					
				}else if(type=='rebate')
				{
					window.location.href="${ctx}/rebateRecordHistory/rebateRecordHistoryList?rebateRecordHistoryId="+transactionId;
					
				}else if(type=='profit')
				{
					window.location.href="${ctx}/profitImputation/profitImputationList?profitImputationId="+transactionId;
					
				}else{
					alert("暂时没有连接地址。");
				}
			}
	</script> 
</form>
</body>
</html>

