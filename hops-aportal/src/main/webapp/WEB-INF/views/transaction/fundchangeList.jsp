<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>实体卡列表</title>
</head>

<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<LINK rel=stylesheet type=text/css
	href="${ctx}/template/admin/css/common.css">
<script type="text/javascript" src="${ctx}/template/common/js/DateUtil.js"></script>
<style>
#pager ul.pages {
	display: block;
	border: none;
	text-transform: uppercase;
	font-size: 10px;
	margin: 10px 0 50px;
	padding: 0;
}

#pager ul.pages li {
	list-style: none;
	float: left;
	border: 1px solid #ccc;
	text-decoration: none;
	margin: 0 5px 0 0;
	padding: 5px;
}

#pager ul.pages li:hover {
	border: 1px solid #003f7e;
}

#pager ul.pages li.pgEmpty {
	border: 1px solid #eee;
	color: #eee;
}

#pager ul.pages li.pgCurrent {
	border: 1px solid #003f7e;
	color: #000;
	font-weight: 700;
	background-color: #eee;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	logTypeChange();
});


function queryFundChange(){
	changeDate();
	$("#listForm").attr("action","${ctx}/transaction/fundchangelist").submit();
}
function exportFundChange(){
	changeDate();
	$("#listForm").attr("action","${ctx}/report/fundchangeExport").submit();
}

	function changeDate(){
		$('#beginDate').val("");
		$('#endDate').val("");
		var changeDate= $("#changeDate").val();
			var date = new Date(changeDate);
			var date2 = new Date(changeDate);
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			
			date2.setHours(23);
			date2.setMinutes(59);
			date2.setSeconds(59);
			$('#beginDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
			$('#endDate').val(date2.Format("yyyy-MM-dd hh:mm:ss"));
		}
	
	function logTypeChange(){
		var logType=$('#logType').val();
		if(logType=='1'){
			$('#transactionSpan').show();
			$('#accountSpan').hide();
		}else{
			$('#transactionSpan').hide();
			$('#accountSpan').show();
		}
	}
</script>
<body>
	<form id="listForm" method="post"
		action="${ctx}/transaction/fundchangelist">
		<div class="line_bar">
			<input id='page' type='hidden' name='page' /> 
			<input id='countTotal' type='hidden' name='countTotal' value='${counttotal}'/> 
			
			<input
				id='reportTypeName' type=hidden name='reportTypeName'
				value="fundchange" />
 <!-- 变动时间: <select name="changeDate"
 id="changeDate" class="select w100" onchange="onchangeDate(this);">
 <c:choose>
 <c:when test="${changeDate=='-1'}">
 <option value="-1" selected="selected">今天</option>
 </c:when>
 <c:otherwise>
 <option value="-1">今天</option>
 </c:otherwise>
 </c:choose>
 <c:choose>
 <c:when test="${changeDate=='0'}">
 <option value="0" selected="selected">最近一个月</option>
 </c:when>
 <c:otherwise>
 <option value="0">最近一个月</option>
 </c:otherwise>
 </c:choose>
 <c:choose>
 <c:when test="${changeDate=='1'}">
 <option value="1" selected="selected">历史记录</option>
 </c:when>
 <c:otherwise>
 <option value="1">历史记录</option>
 </c:otherwise>
 </c:choose>
 </select> -->
			
			<input id='beginDate' type='hidden' name='beginDate' /> 
			<input id='endDate' type='hidden' name='endDate' /> 
			变动日期: <input id="changeDate" class="ipt w100" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"	name="changeDate" value='${changeDate}'></input> 
			日志类型:
		<select id="logType" name="logType" class="select w80" onchange='logTypeChange();'>
			<c:choose>
				<c:when test="${logType=='1'}">
					<option value="1" selected="selected">交易</option>
				</c:when>
				<c:otherwise>
					<option value="1">交易</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${logType=='2'}">
					<option value="2" selected="selected">账户</option>
				</c:when>
				<c:otherwise>
					<option value="2">账户</option>
				</c:otherwise>
			</c:choose>
		</select>
			
			
			<span id='transactionSpan'> 	
			&nbsp;交易类型: <select
				id="transactionChangeType" name="transactionChangeType"
				class="select w80">
				<option value="">请选择</option>
				<c:choose>
					<c:when test="${transactionChangeType=='1'}">
						<option value="1" selected="selected">下单</option>
					</c:when>
					<c:otherwise>
						<option value="1">下单</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transactionChangeType=='10'}">
						<option value="10" selected="selected">退款</option>
					</c:when>
					<c:otherwise>
						<option value="10">退款</option>
					</c:otherwise>
				</c:choose>
			</select> &nbsp;
			</span>
			<span id='accountSpan'>
			账户操作类型: <select id="accountChangeType"
				name="accountChangeType" class="select w80">
				<option value="">请选择</option>
				<c:choose>
					<c:when test="${transactionChangeType=='15'}">
						<option value="15" selected="selected">加款</option>
					</c:when>
					<c:otherwise>
						<option value="15">加款</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transactionChangeType=='16'}">
						<option value="16" selected="selected">减款</option>
					</c:when>
					<c:otherwise>
						<option value="16">减款</option>
					</c:otherwise>
				</c:choose>
				<!--
				<c:choose>
					<c:when test="${transactionChangeType=='5'}">
						<option value="5" selected="selected">授信加款</option>
					</c:when>
					<c:otherwise>
						<option value="5">授信加款</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${transactionChangeType=='6'}">
						<option value="6" selected="selected">授信减款</option>
					</c:when>
					<c:otherwise>
						<option value="6">授信减款</option>
					</c:otherwise>
				</c:choose>
				-->
			</select>
			</span>
			订单编号:<input type="text" id="orderNo" name="orderNo"
				value="${orderNo}" class="ipt w100" />
			<div style="float: right;">
				<input type="button" class="button" value="查询"
					onclick="queryFundChange()" /> <input type="button" class="button"
					value="导出" onclick="exportFundChange()" />
			</div>
		</div>
		<table id='listTable' class='list'>
			<tbody>
				<tr>
					<th><SPAN>序号</SPAN></th>
					<th><SPAN>产品</SPAN></th>
					<th><SPAN>订单编号</SPAN></th>
					<th><SPAN>商家订单号</SPAN></th>
					<th><SPAN>充值号码</SPAN></th>
					<th><SPAN>变动类型</SPAN></th>
					<th><SPAN>变动金额</SPAN></th>
					<th><SPAN>余额</SPAN></th>
					<th><SPAN>操作时间</SPAN></th>
				</tr>
			</tbody>

			<c:choose>
				<c:when test="${fn:length(mlist) > 0}">
					<c:forEach items="${mlist}" var="currencyAccountBalanceHistory"
						varStatus="status">
						<tr>
							<td>${(page-1)*pageSize+status.index+1}</td>
							<td><c:if
									test="${currencyAccountBalanceHistory.PRODUCTNO==''||currencyAccountBalanceHistory.PRODUCTNO==null}">
						--
						</c:if> <c:if test="${currencyAccountBalanceHistory.PRODUCTNO!=''}">
							${currencyAccountBalanceHistory.PRODUCTNO}
						</c:if></td>
							<td>
							<c:if
									test="${currencyAccountBalanceHistory.TRANSACTIONNO==''||currencyAccountBalanceHistory.TRANSACTIONNO==null}">
						--
						</c:if> <c:if test="${currencyAccountBalanceHistory.TRANSACTIONNO!=''}">
							<a href="${ctx}/transaction/showOrderDetail?orderNo=${currencyAccountBalanceHistory.TRANSACTIONNO}">${currencyAccountBalanceHistory.TRANSACTIONNO}</a>
						</c:if></td>
							<td><c:if
									test="${currencyAccountBalanceHistory.MERCHANTORDERNO==null || currencyAccountBalanceHistory.MERCHANTORDERNO==''}">
						--
						</c:if> <c:if
									test="${currencyAccountBalanceHistory.MERCHANTORDERNO!=null}">
							${currencyAccountBalanceHistory.MERCHANTORDERNO}
						</c:if></td>
							<td><c:if
									test="${currencyAccountBalanceHistory.USERCODE==''||currencyAccountBalanceHistory.USERCODE==null}">
						--
						</c:if> <c:if test="${currencyAccountBalanceHistory.USERCODE!=''}">
							${currencyAccountBalanceHistory.USERCODE}
						</c:if></td>
							<td><c:choose>
									<c:when
										test="${currencyAccountBalanceHistory.TYPE=='1'}">
							下单
						</c:when>
									<c:when
										test="${currencyAccountBalanceHistory.TYPE=='15'}">
							加款
						</c:when>
									<c:when
										test="${currencyAccountBalanceHistory.TYPE=='16'}">
							减款
						</c:when>
									<c:when
										test="${currencyAccountBalanceHistory.TYPE=='5'}">
							授信加款
						</c:when>
									<c:when
										test="${currencyAccountBalanceHistory.TYPE=='6'}">
							授信减款
						</c:when>
									<c:when
										test="${currencyAccountBalanceHistory.TYPE=='10'}">
							退款
						</c:when>
									<c:when
										test="${currencyAccountBalanceHistory.TYPE=='8'}">
							返佣
						</c:when>
									<c:otherwise>
							--
						</c:otherwise>
								</c:choose></td>
							<td>${currencyAccountBalanceHistory.CHANGEAMOUNT}</td>
							<td>${currencyAccountBalanceHistory.NEWAVAILABLEBALANCE}</td>
							<td>
							${currencyAccountBalanceHistory.CREATEDATE}
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
		</table>

		<div class="line_pages">
			<div style="float: left;">
				&nbsp; 显示条数： <select name="pageSize" id="pageSize" class="select">
					<option value="10"
						<c:choose><c:when test="${pageSize==10}">selected=selected</c:when></c:choose>>10</option>
					<option value="20"
						<c:choose><c:when test="${pageSize==20}">selected=selected</c:when></c:choose>>20</option>
					<option value="30"
						<c:choose><c:when test="${pageSize==30}">selected=selected</c:when></c:choose>>30</option>
					<option value="50"
						<c:choose><c:when test="${pageSize==50}">selected=selected</c:when></c:choose>>50</option>
					<option value="100"
						<c:choose><c:when test="${pageSize==100}">selected=selected</c:when></c:choose>>100</option>
					<option value="500"
						<c:choose><c:when test="${pageSize==500}">selected=selected</c:when></c:choose>>500</option>
					<option value="1000"
						<c:choose><c:when test="${pageSize==1000}">selected=selected</c:when></c:choose>>1000</option>
				</select>&nbsp; 条
			</div>

			<div id="pager" style="float: right;"></div>
			<div class="pages_menber">
				(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)
			</div>
		</div>
		<br />

		<script type="text/javascript" language="javascript">
 	$(document).ready(function() { 
		$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
		});
		PageClick = function(pageclickednumber) { 
		$("#pager").pager({ pagenumber: pageclickednumber, pagecount: ${pagetotal}, buttonClickCallback: PageClick 
		}); 
		$("#page").val(pageclickednumber);
		queryFundChange();
		// $("#listForm").submit();
		}	
		
		
		
	</script>
	</form>
</body>
</html>