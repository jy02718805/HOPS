<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>供货商号码规则列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form id="inputForm"  action="${ctx}/supplyDupNumRule/supplyDupNumRuleList" method="post">
<div class="line_bar">
	供货商商户名称（全称）:<input type='text' id='' name='merchantName' value='${merchantName}' class="ipt" />
	<shiro:user>
	<input type="button" class="button"  value="查询" onclick="supplyDupNumRuleList()"/>
	</shiro:user>
	<shiro:hasPermission name="supplyDupNumRule:add_show">
	<input type="button" class="button"  value="创建" onclick="toSaveSupplyDupNumRule()"/>
	</shiro:hasPermission>
	
	<input id='page' type='hidden' name='page'/> 
	<input id=hasExpired type=hidden name=hasExpired/> 
</div>
	<table id=listTable class=list>
		<tbody>
			<tr>
				<th><span>序号</span> </th>
				<th><span>供货商名称</span> </th>
				<th><span>时间间隔量</span> </th>
				<th><span>时间间隔单位</span> </th>
				<th><span>限制次数</span> </th>
				<th><span>操作</span> </th>
			</tr>
			<c:choose>
				<c:when test="${fn:length(mlist) > 0}">
					<c:forEach items="${mlist}" var="supplyDupNumRule"  varStatus="status">
						<tr>
							<td>${(page-1)*pageSize+status.index+1}</td>
							<td>${supplyDupNumRule.merchantName}</td>
							<td>${supplyDupNumRule.dateInterval}</td>
							<td>
								<c:choose>
									<c:when test="${supplyDupNumRule.dateUnit=='s'}">
										秒
									</c:when>
									<c:when test="${supplyDupNumRule.dateUnit=='m'}">
										分
									</c:when>
									<c:when test="${supplyDupNumRule.dateUnit=='h'}">
										小时
									</c:when>
									<c:when test="${supplyDupNumRule.dateUnit=='d'}">
										天
									</c:when>
									<c:otherwise>
										未知
									</c:otherwise>
								</c:choose>
							</td>
							<td>${supplyDupNumRule.sendTimes}次</td>
							<td>
								<shiro:hasPermission name="supplyDupNumRule:edit_show">
									<A href="toEditSupplyDupNumRule?merchantId=${supplyDupNumRule.merchantId}">[编辑]</A>
								</shiro:hasPermission>
								<shiro:hasPermission name="supplyDupNumRule:delete">
									<A href="#"  onclick="doDeleteSupplyDupNumRule(${supplyDupNumRule.id})">[刪除]</A>
								</shiro:hasPermission>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
						<tr>
							<td colspan="5">没数据</td>
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
			$("#inputForm").submit();
			}
			
			function supplyDupNumRuleList(){
				$('#inputForm').submit();
			}
			
			function toSaveSupplyDupNumRule(){
				window.location.href="${ctx}/supplyDupNumRule/toSaveSupplyDupNumRule";
			}
			function doDeleteSupplyDupNumRule(id){
				if(confirm("确认删除！"))
				{
					window.location.href="${ctx}/supplyDupNumRule/doDeleteSupplyDupNumRule?id="+id;
				}
				else{
					return ;
				}
			}
	</script> 
</form>
</body>
</html>

