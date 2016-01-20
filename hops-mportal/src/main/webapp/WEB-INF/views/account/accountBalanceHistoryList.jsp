<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>账户日志列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/admin/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>

<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.easyui.min.js"></script>

<LINK rel='stylesheet' type='text/css'
	href="${ctx}/template/admin/css/common.css">

<LINK rel='stylesheet' type='text/css'
	href="${ctx}/template/admin/css/easyui.css">
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
<body>
	<form id="listForm"
		action="${ctx}/accountHistory/accountBalanceHistoryList" method="post">
		<input id="page" type='hidden' name="page" />
		<input id="hasExpired" type=hidden name="hasExpired" /> <input id="spId" type='hidden'
			name="spId" value='${sp.id}' /> <input id="spName" type='hidden'
			name="spName" value='${sp.spName}' />

		<div class="line_bar">
			<span class="requiredField">*</span> 账户类型:<select
				name="accountTypeId" id="accountTypeId"
				onChange='onChangeAccountType();' class="select w150">
				<option value="">请选择</option>
				<c:forEach items="${accountTypelist}" var="accountType">
					<c:choose>
						<c:when
							test="${accountType.accountTypeId==currencyAccountBalanceHistoryVo.accountTypeId}">
							<option selected="selected" value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
						</c:when>
						<c:otherwise>
							<option value="${accountType.accountTypeId}">${accountType.accountTypeName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 用户名称: <input type="hidden" id="identityId" name="identityId"
				value='${currencyAccountBalanceHistoryVo.identityId}' /> 
			<select name="identityName" id="identityName" class="select" onChange='onChangeIdentity();'>
				<option value="">请选择</option>
			</select> 关系:<select id="relation" class="select w80" name="relation"
				onChange='onChangeRelation();'>
				<option value="own"
					<c:if test="${currencyAccountBalanceHistoryVo.relation=='own'}">selected='selected'</c:if>>所属</option>
				<option value="use"
					<c:if test="${currencyAccountBalanceHistoryVo.relation=='use'}">selected='selected'</c:if>>使用</option>
			</select>
			<input type="hidden" id="accountIdStr" name="accountIdStr" class="ipt w150" value="${currencyAccountBalanceHistoryVo.accountId}" /> 
			账户ID: <input type="text" class="easyui-combobox" id="accountId" name="accountId"  data-options="cache: false,valueField:'accountId',textField:'accountId'"  
				value="${currencyAccountBalanceHistoryVo.accountId}" /> 
		</div>

		<div class="line_bar">
			时间段: <input id="beginDate" class="ipt w150" type="text"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-{%M-3}-%d',maxDate:'#F{$dp.$D(\'endDate\')|| \'%y-%M-%d\'}'});"
				name="beginDate"
				value='${currencyAccountBalanceHistoryVo.beginDate}'></input> 至 <input
				id="endDate" class="ipt w150" type="text"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginDate\') || \'%y-{%M-3}-%d\'}',maxDate:'%y-%M-%d'});"
				name="endDate" value='${currencyAccountBalanceHistoryVo.endDate}'></input>
			账户操作类型: <select name="type" id="type" class="select">
				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type==''}">
						<option value="" selected="selected">请选择</option>
					</c:when>
					<c:otherwise>
						<option value="">请选择</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='2'}">
						<option value="2" selected="selected">借记</option>
					</c:when>
					<c:otherwise>
						<option value="2">借记</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='1'}">
						<option value="1" selected="selected">贷记</option>
					</c:when>
					<c:otherwise>
						<option value="1">贷记</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='3'}">
						<option value="3" selected="selected">冻结</option>
					</c:when>
					<c:otherwise>
						<option value="3">冻结</option>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='4'}">
						<option value="4" selected="selected">解冻</option>
					</c:when>
					<c:otherwise>
						<option value="4">解冻</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='7'}">
						<option value="7" selected="selected">加款</option>
					</c:when>
					<c:otherwise>
						<option value="7">加款</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='8'}">
						<option value="8" selected="selected">减款</option>
					</c:when>
					<c:otherwise>
						<option value="8">减款</option>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='5'}">
						<option value="5" selected="selected">授信加款</option>
					</c:when>
					<c:otherwise>
						<option value="5">授信加款</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${currencyAccountBalanceHistoryVo.type=='6'}">
						<option value="6" selected="selected">授信减款</option>
					</c:when>
					<c:otherwise>
						<option value="6">授信减款</option>
					</c:otherwise>
				</c:choose>
			</select> 交易编号: <input type="text" class="ipt w100" id="transactionNo"
				name="transactionNo"
				value="${currencyAccountBalanceHistoryVo.transactionNo}" />
			<div style="float: right;">
				<shiro:user>
					<input type="button" class="button" value="查询"
						onclick="queryLog();" />
				</shiro:user>
			</div>

		</div>
		<table id='listTable' class='list'>
			<tbody>
				<tr>
					<th><a class=sort href="#" name=identityName>序列</a></th>
					<th><a class=sort href="#" name=identityName>用户名称</a></th>
					<th><a class=sort href="#" name=identityName>账户ID</a></th>
					<th><a class=sort href="#" name=identityName>账户类型</a></th>
					<th><a class=sort href="javascript:;" name=identityName>余额详情</a>
					</th>
					<th><a class=sort href="javascript:;" name=identityName>变动金额</a>
					</th>
					<th><a class=sort href="#" name=identityName>账户操作类型</a></th>
					<th><a class=sort href="#" name=identityName>交易编号</a></th>
					<th><a class=sort href="#" name=identityName>交易描述</a></th>
					<th><a class=sort href="#" name=identityName>创建时间</a></th>
					<th><a class=sort href="#" name=identityName>备注</a></th>

				</tr>
				<c:choose>
					<c:when test="${fn:length(ccylist) > 0}">
						<c:forEach items="${ccylist}" var="currencyAccountBalanceHistory"
							varStatus="count">
							<tr>
								<td>${(page-1)*pageSize+count.index+1}</td>
								<td>${currencyAccountBalanceHistory.IDENTITYNAME}</td>
								<td>${currencyAccountBalanceHistory.ACCOUNTID}</td>
								<td>${currencyAccountBalanceHistory.ACCOUNTTYPENAME}</td>
								<td>AB:
									${currencyAccountBalanceHistory.NEWAVAILABLEBALANCE}<br /> UB:
									${currencyAccountBalanceHistory.NEWUNAVAILABLEBANLANCE}<br />
									CB: ${currencyAccountBalanceHistory.NEWCREDITABLEBANLANCE}
								</td>
								<td>${currencyAccountBalanceHistory.CHANGEAMOUNT}</td>
								<td><c:choose>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='2'}">
							             	借记
							       		</c:when>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='1'}">
							             	贷记
							       </c:when>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='3'}">
							        		冻结
							       </c:when>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='4'}">
							             	解冻
							       </c:when>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='5'}">
							             	授信加款
							       </c:when>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='6'}">
							             	授信减款
							       </c:when>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='7'}">
							             	加款
							       </c:when>
										<c:when test="${currencyAccountBalanceHistory.TYPE=='8'}">
							             	减款
							       </c:when>
										<c:otherwise>
							              	--
							       </c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when
											test="${currencyAccountBalanceHistory.TRANSACTIONNO!=null}">
											<a href="javascript:void(0);"
												onclick="clickId('${currencyAccountBalanceHistory.TRANSACTIONNO}');">
												${currencyAccountBalanceHistory.TRANSACTIONNO}</a>
										</c:when>
										<c:otherwise>
							              	--
							       		</c:otherwise>
									</c:choose></td>
								<td>${currencyAccountBalanceHistory.DESCSTR}</td>
								<td>${currencyAccountBalanceHistory.CREATEDATE}</td>
								<td>${currencyAccountBalanceHistory.REMARK}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="11">没数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
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
			
// $('#accountIdStr').val();
			
			
			$('#listForm').submit();
		}
		
		
		function clickId(transactionNo){
			var beginDate=$("#beginDate").val();
			var endDate=$("#endDate").val();
			window.location.href="${ctx}/accountHistory/accountTransactionLogList?transactionNo="+transactionNo+"&beginDate="+beginDate+"&endDate="+endDate;
		}
		
		function clickIdOrder(transactionId)
		{
			window.location.href="${ctx}/transaction/showOrderDetail?orderNo="+transactionId;
			
		}
		
		
		
		function onChangeAccountType(){
			$('#accountId').combobox('reload', ''); 
			$("#accountId").combobox('setValue',null);
            $("#accountId").val(null);
            $("#accountId").combobox('setText', "");
			$('#identityId').val('');
			$('#identityName').val('');
			
			var identityId=$('#identityName').val();
			var identityId2=$('#identityId').val();
			var relation=$('#relation').val();
			var accountTypeId=$('#accountTypeId').val();
			if(accountTypeId=='149001'  || accountTypeId=='148001'){
				 $("#relation").val("use");
				 $("#relation").attr('disabled',true);
				changeIdentitys('AGENT','');
			}else if(accountTypeId=='149001'||accountTypeId=='148000'){
				 $("#relation").val("own");
				 $("#relation").attr('disabled',false);
				changeIdentitys('AGENT','');
			}
			else if(accountTypeId=='149000'){
				 $("#relation").val("own");
				 $("#relation").attr('disabled',false);
				changeIdentitys('SUPPLY','');
			}else if(accountTypeId=='204000'|| accountTypeId=='204001' || accountTypeId=='45000' || accountTypeId=='45001' || accountTypeId=='149003' ){
				// sys
				$("#relation").val("own");
				$("#relation").attr('disabled',false);
				$('#identityName').empty();
				var spName=$('#spName').val();
				var spId=$('#spId').val();
				$("#identityName").prepend("<option value=''>请选择</option>");
				$("#identityName").append("<option value='"+spId+"'>"+spName+"</option>");
				// sp
			}
			$('#identityId').val('');
			$('#identityName').val('');
			getAccountIds();
			$('#accountId').combobox('clear');
		}
		
		function loadAccountType(){
// $('#accountId').combobox('setValue',accountId);
// $('#accountId').combobox('setText',accountId);
			var identityId=$('#identityName').val();
			var identityId2=$('#identityId').val();
			var relation=$('#relation').val();
			var accountTypeId=$('#accountTypeId').val();
			if(accountTypeId=='149001' || accountTypeId=='148001'){
				 $("#relation").val("use");
				 $("#relation").attr('disabled',true);
				changeIdentitys('AGENT',identityId2);
			}else if(accountTypeId=='149001'||accountTypeId=='148000'){
				 $("#relation").val("own");
				 $("#relation").attr('disabled',false);
				changeIdentitys('AGENT',identityId2);
			}
			else if(accountTypeId=='149000'){
				 $("#relation").val("own");
				 $("#relation").attr('disabled',false);
				changeIdentitys('SUPPLY',identityId2);
			}else if(accountTypeId=='204000'|| accountTypeId=='204001' || accountTypeId=='45000' || accountTypeId=='45001' || accountTypeId=='149003'){
				// sys
				$('#identityName').empty();
				var spName=$('#spName').val();
				var spId=$('#spId').val();
				$("#identityName").append("<option value='"+spId+"' selected='selected'>"+spName+"</option>");
				// sp
			}
			getAccountIds();
		}
		
		
		$(document).ready(function(){  
			loadAccountType();
		});
		
		function changeIdentitys(merchantType,identityId){
				$.ajax({
						url:"${ctx}/Merchant/getAgentMerchant?merchantType="+merchantType+"&identityId="+identityId, 
						type: "post",
				        data: null,
				        async: false,
						success:function(data) {
							$('#identityName').html(data);
						}
				});
		}
		
		function onChangeRelation(){
			$("#accountId").combobox('setValue', null);
            $("#accountId").val(null);
            $("#accountId").combobox('setText', "");
			var identityId=$('#identityName').val();
			var accountTypeId=$('#accountTypeId').val();
			var relation=$('#relation').val();
			getAccountIds(); 
		}
		
		function onChangeIdentity(){
			$("#accountId").combobox('setValue', null);
            $("#accountId").val(null);
            $("#accountId").combobox('setText', "");
			var identityId=$('#identityName').val();
			var accountTypeId=$('#accountTypeId').val();
			var relation=$('#relation').val();
			$('#identityId').val(identityId);
			getAccountIds(); 
			
		}
		
		function getAccountIds(){
			var identityId=$('#identityName').val();
			var accountTypeId=$('#accountTypeId').val();
			var relation=$('#relation').val();
			var accountId=$('#accountId').val();
// $.ajax({
// url:'${ctx}/account/queryCCYAccounts?accountTypeId='+accountTypeId+'&identityId='+identityId+'&relation='+relation+'&accountId='+accountId,
// type: "post",
// data: null,
// async: false,
// success:function(data) {
// var dataJs=$.parseJSON(data);
// $('#accountId').combobox({
// url:dataJs,
// valueField:'accountId',
// textField:'accountId'
// });
// }
// });
// $('#accountId').combobox({
// url:'${ctx}/account/queryCCYAccounts?accountTypeId='+accountTypeId+'&identityId='+identityId+'&relation='+relation+'&accountId='+accountId,
// valueField:'accountId',
// textField:'accountId'
// });
			url='${ctx}/account/queryCCYAccounts?accountTypeId='+accountTypeId+'&identityId='+identityId+'&relation='+relation+'&accountId='+accountId;
			$('#accountId').combobox('reload', url); 
			
		}
		
		function clickAccountSelect(){
			$('#accountIdSelect').hide();
			$('#accountId').show();
		}
		
		
		function changeAccountId(){
			var accountId=$('#accountId').val();
			$('#accountIdStr').val(accountId);
		}
	</script>
	</form>
</body>
</html>

