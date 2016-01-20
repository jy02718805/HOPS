<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>加款申请审核列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
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
<form id="listForm" method="post" action="${ctx}/account/accountAddCashRecordList">
	<input id=page type=hidden name=page> 
	<input id=hasExpired type=hidden name=hasExpired> 
	<div class=line_bar>
	申请开始时间:<input id="beginApplyTime" name="beginApplyTime" type="text"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-{%M-3}-%d',maxDate:'#F{$dp.$D(\'endApplyTime\')|| \'%y-%M-%d\'}'});" 
			value="${beginApplyTime}" class="ipt"/> 
	申请结束时间:<input id="endApplyTime" name="endApplyTime" type="text"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginApplyTime\') || \'%y-{%M-3}-%d\'}',maxDate:'%y-%M-%d'});"
			value="${endApplyTime}" class="ipt"/> 
	审核状态:<select name="verifyStatus" id="verifyStatus" class="select w80">
			<c:choose>
				<c:when test="${verifyStatus==''}">
					<option value="" selected="selected">请选择</option>
				</c:when>
				<c:otherwise>
					<option value="">请选择</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${verifyStatus=='1'}">
					<option value="1" selected="selected">待审核</option>
				</c:when>
				<c:otherwise>
					<option value="1">待审核</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${verifyStatus=='2'}">
					<option value="2" selected="selected">审核成功</option>
				</c:when>
				<c:otherwise>
					<option value="2">审核成功</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${verifyStatus=='3'}">
					<option value="3" selected="selected">审核失败</option>
				</c:when>
				<c:otherwise>
					<option value="3">审核失败</option>
				</c:otherwise>
			</c:choose>
		</select>
		商户名称:<input id="merchantName" name="merchantName" type="text" value="${merchantName}" class="ipt"/>
		<shiro:user>
		<input type="button" class="button" value="查询" onclick="accountAddCashRecordList()"/>
		</shiro:user>
	</div>
	
	<table id=listTable class=list>
		<tbody>
			<tr>
				<th><SPAN>序号</SPAN></th>
				<th><SPAN>商户名称</SPAN></th>
				<th><SPAN>操作员名称</SPAN></th>
				<th><SPAN>申请金额</SPAN></th>
				<th><SPAN>审核成功金额</SPAN></th>
				<th><SPAN>审核状态</SPAN></th>
				<th><SPAN>申请时间</SPAN></th>
				<th><SPAN>审核时间</SPAN></th>
				<th><SPAN>操作</SPAN></th>
			</tr>
			<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
				<c:forEach items="${mlist}" var="currencyAccountAddCashRecord" varStatus="status">
					<tr>
						<td>${(page-1)*pageSize+status.index+1}</td>
						<td>${currencyAccountAddCashRecord.merchantName}</td>
						<td>${currencyAccountAddCashRecord.operatorName}</td>
						
					    <td>${currencyAccountAddCashRecord.applyAmt}</td>
					    <td>${currencyAccountAddCashRecord.successAmt}</td>

					    	<c:choose>
								<c:when test="${currencyAccountAddCashRecord.verifyStatus=='1'}">
								<td>
					    	待审核
								</td>
							</c:when>
							<c:when test="${currencyAccountAddCashRecord.verifyStatus=='2'}">
								<td>
					    	审核成功
								</td>
							</c:when>
							<c:when test="${currencyAccountAddCashRecord.verifyStatus=='3'}">
								<td class="tdgb">
					    	审核失败
								</td>
							</c:when>
							<c:otherwise>
								<td>
					    	未知
								</td>
							</c:otherwise>
						</c:choose>
						<td>
						<fmt:formatDate value="${currencyAccountAddCashRecord.applyTime}" type="both" dateStyle="medium"/>
						</td>
					    <td><fmt:formatDate value="${currencyAccountAddCashRecord.verifyTime}" type="both" dateStyle="medium"/></td>
					    <td>
					    	<c:choose>
								<c:when test="${currencyAccountAddCashRecord.verifyStatus=='1'}">
									<shiro:hasPermission name="agentAccount:addAccountCheck_show">
									<a href="${ctx}/account/toAddCashVerify?id=${currencyAccountAddCashRecord.id}">[审核]</a>
								</shiro:hasPermission>
								</c:when>
							</c:choose>
					    </td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
					<tr>
						<td colspan="9">没数据</td>
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
			
	function accountAddCashRecordList(){
		var beginDate=$("#beginApplyTime").val();
		if(beginDate==""||beginDate==null)
		{
    		alert("开始时间不能为空！");
    		document.getElementById("beginApplyTime").focus(); 
    		return false;
		}
		var endDate=$("#endApplyTime").val();
		if(endDate==""||endDate==null)
		{
    		alert("结束时间不能为空！");
    		document.getElementById("endApplyTime").focus(); 
    		return false;
		}
		$('#listForm').submit();
	}
	</script> 
</form>
</body>
</html>

