<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>预成功配置列表</title>
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
<form id="inputForm"  action="${ctx}/agentQueryFakeRule/agentQueryFakeRuleList" method="post">
<div class="line_bar">
	代理商商户:<input type='text' id='' name='merchantName' value='${merchantName}' class="ipt" />
	
	<shiro:user>
	<input type="button" class="button"  value="查询" onclick="downQueryFakeRuleList()"/>
	</shiro:user>
	<shiro:hasPermission name="fakerule:add_show">
	<input type="button" class="button"  value="创建" onclick="toSaveDownQueryFakeRule()"/>
	</shiro:hasPermission>
	
	<input id='page' type='hidden' name='page'/> 
	<input id=hasExpired type=hidden name=hasExpired/> 
</div>
	<table id=listTable class=list>
		<tbody>
			<tr>
				<th><span>序号</span> </th>
				<th><span>代理商名称</span> </th>
				<th><span>时间间隔量</span> </th>
				<th><span>时间间隔单位</span> </th>
				<th><span>操作</span> </th>
			</tr>
			<c:choose>
				<c:when test="${fn:length(mlist) > 0}">
					<c:forEach items="${mlist}" var="downQueryFakeRule"  varStatus="status">
						<tr>
							<td>${(page-1)*pageSize+status.index+1}</td>
							<td>${downQueryFakeRule.merchantName}</td>
							<td>${downQueryFakeRule.intervalTime}</td>
							<td>
								<c:choose>
									<c:when test="${downQueryFakeRule.intervalUnit=='s'}">
										秒
									</c:when>
									<c:when test="${downQueryFakeRule.intervalUnit=='m'}">
										分
									</c:when>
									<c:when test="${downQueryFakeRule.intervalUnit=='h'}">
										小时
									</c:when>
									<c:when test="${downQueryFakeRule.intervalUnit=='d'}">
										天
									</c:when>
									<c:otherwise>
										未知
									</c:otherwise>
								</c:choose>
							</td>
							<td>
							<shiro:hasPermission name="fakerule:edit_show">
								<A href="toEditAgentQueryFakeRule?merchantId=${downQueryFakeRule.merchantId}">[编辑]</A>
							</shiro:hasPermission>
							<shiro:hasPermission name="fakerule:delete">
								<%-- <A href="doDeleteAgentQueryFakeRule?merchantId=${downQueryFakeRule.merchantId}" id="${downQueryFakeRule.merchantId }">[刪除]</A> --%>
								<A href="#"  onclick="deleteDownQueryFakeRule(${downQueryFakeRule.merchantId})">[刪除]</A>
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
			
			function downQueryFakeRuleList(){
				$('#inputForm').submit();
			}
			
			function toSaveDownQueryFakeRule(){
				window.location.href="${ctx}/agentQueryFakeRule/toSaveAgentQueryFakeRule";
			}
			function deleteDownQueryFakeRule(merchantId){
				if(confirm("确认删除！"))
				{
					window.location.href="${ctx}/agentQueryFakeRule/doDeleteAgentQueryFakeRule?merchantId="+merchantId;
				}
				else{
					return ;
				}
			}
	</script> 
</form>
</body>
</html>

