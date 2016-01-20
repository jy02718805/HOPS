<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>实体卡列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<form id="listForm" method="get" action="${ctx}/interface/interfaceConfList">
	<input id=page type=hidden name=page> 
	<input id=hasExpired type=hidden name=hasExpired> 
	<div class="line_bar">
		商户:<select name="merchantId" id="merchantId" class="select">
					<option value="">请选择</option>
					<c:forEach items="${merchants}" var="merchant">
						<c:choose>
		 					<c:when test="${merchant.id==merchantId}">
				             	<option value="${merchant.id}" selected="selected">${merchant.merchantName}</option>
					        </c:when>
					        <c:otherwise>
								<option value="${merchant.id}">${merchant.merchantName}</option>
					        </c:otherwise>
				        </c:choose>
					</c:forEach>
				 </select>
	接口类型:<select name="interfaceType" id="interfaceType" class="select w80">
					<option value="">请选择</option>
					<c:choose>
	 					<c:when test="${interfaceType=='send_order'}">
			             	<option value="send_order" selected="selected">发送订单</option>
				        </c:when>
				        <c:otherwise>
							<option value="send_order">发送订单</option>
				        </c:otherwise>
			        </c:choose>
					<c:choose>
	 					<c:when test="${interfaceType=='agent_notify_order'}">
			             	<option value="agent_notify_order" selected="selected">通知代理商</option>
				        </c:when>
				        <c:otherwise>
							<option value="agent_notify_order">通知代理商</option>
				        </c:otherwise>
			        </c:choose>
			        <c:choose>
							<c:when test="${interfaceType=='agent_notify_TBorder'}">
								<option value="agent_notify_TBorder" selected="selected">通知淘宝代理商</option>
							</c:when>
							<c:otherwise>
								<option value="agent_notify_TBorder">通知淘宝代理商</option>
							</c:otherwise>
						</c:choose>
			        <c:choose>
	 					<c:when test="${interfaceType=='query_order'}">
			             	<option value="query_order" selected="selected">查询订单</option>
				        </c:when>
				        <c:otherwise>
							<option value="query_order">查询订单</option>
				        </c:otherwise>
			        </c:choose>
					<c:choose>
	 					<c:when test="${interfaceType=='supply_notify_order'}">
			             	<option value="supply_notify_order" selected="selected">供货商通知订单</option>
				        </c:when>
				        <c:otherwise>
							<option value="supply_notify_order">供货商通知订单</option>
				        </c:otherwise>
			        </c:choose>
					<c:choose>
	 					<c:when test="${interfaceType=='supply_send_order_flow'}">
			             	<option value="supply_send_order_flow" selected="selected">供货商流量下单</option>
				        </c:when>
				        <c:otherwise>
							<option value="supply_send_order_flow">供货商流量下单</option>
				        </c:otherwise>
			        </c:choose>
					<c:choose>
	 					<c:when test="${interfaceType=='supply_query_order_flow'}">
			             	<option value="supply_query_order_flow" selected="selected">供货商流量查询</option>
				        </c:when>
				        <c:otherwise>
							<option value="supply_query_order_flow">供货商流量查询</option>
				        </c:otherwise>
			        </c:choose>
					<c:choose>
	 					<c:when test="${interfaceType=='supply_notify_order_flow'}">
			             	<option value="supply_notify_order_flow" selected="selected">供货商流量通知</option>
				        </c:when>
				        <c:otherwise>
							<option value="supply_notify_order_flow">供货商流量通知</option>
				        </c:otherwise>
			        </c:choose>
					<c:choose>
	 					<c:when test="${interfaceType=='agent_notify_order_flow'}">
			             	<option value="agent_notify_order_flow" selected="selected">代理商流量通知</option>
				        </c:when>
				        <c:otherwise>
							<option value="agent_notify_order_flow">代理商流量通知</option>
				        </c:otherwise>
			        </c:choose>
				 </select>
	接口编码:<select name="encoding" id="encoding" class="select w80">
					<option value="">请选择</option>
					
					<c:choose>
	 					<c:when test="${encoding=='gbk'}">
			             	<option value="gbk" selected="selected">gbk</option>
				        </c:when>
				        <c:otherwise>
							<option value="gbk">gbk</option>
				        </c:otherwise>
			        </c:choose>
			        <c:choose>
	 					<c:when test="${encoding=='utf-8'}">
			             	<option value="utf-8" selected="selected">utf-8</option>
				        </c:when>
				        <c:otherwise>
							<option value="utf-8">utf-8</option>
				        </c:otherwise>
			        </c:choose>
				 </select>
	请求方式:<select name="connectionType" id="connectionType" class="select w80">
					<option value="">请选择</option>
					<c:choose>
	 					<c:when test="${connectionType=='http'}">
			             	<option value="http" selected="selected">http</option>
				        </c:when>
				        <c:otherwise>
							<option value="http">http</option>
				        </c:otherwise>
			        </c:choose>
			        <c:choose>
	 					<c:when test="${connectionType=='https'}">
			             	<option value="https" selected="selected">https</option>
				        </c:when>
				        <c:otherwise>
							<option value="https">https</option>
				        </c:otherwise>
			        </c:choose>
			        <c:choose>
	 					<c:when test="${connectionType=='socket'}">
			             	<option value="socket" selected="selected">socket</option>
				        </c:when>
				        <c:otherwise>
							<option value="socket">socket</option>
				        </c:otherwise>
			        </c:choose>
			        <c:choose>
	 					<c:when test="${connectionType=='ssh_socket'}">
			             	<option value="ssh_socket" selected="selected">ssh_socket</option>
				        </c:when>
				        <c:otherwise>
							<option value="ssh_socket">ssh_socket</option>
				        </c:otherwise>
			        </c:choose>
				 </select>
		<shiro:user>
			<input type="button" class="button" value="查询" onclick="merchantInterfaceConfList()"/>
		</shiro:user>
		<shiro:hasPermission name="interfacePackets:add_show">
			<input type="button" class="button" value="创建" onclick="toSaveInterface()"/>
		</shiro:hasPermission>	
	</div>
	<table id=listTable class=list>
		<tbody>
		<tr>
			<th><SPAN> 序号 </SPAN> </th>
			<th><SPAN> 接口类型 </SPAN> </th>
			<th><SPAN> 接口编码 </SPAN> </th>
			<th><SPAN> 连接方式 </SPAN> </th>
			<th><SPAN> 请求地址 </SPAN> </th>
			<th><SPAN> 状态 </SPAN> </th>
			<th><SPAN> 操作 </SPAN> </th>
		</tr>
		<c:choose>
			<c:when test="${fn:length(interfacePacketsDefinitions) > 0}">
				<c:forEach items="${interfacePacketsDefinitions}" var="interfacePacketsDefinition" varStatus="status">
					<tr>
						<td>${(page-1)*pageSize+status.index+1}</td>
						<td>${interfacePacketsDefinition.interfaceType}</td>
						<td>${interfacePacketsDefinition.encoding}</td>
						<td>${interfacePacketsDefinition.connectionType}</td>
						<td>${interfacePacketsDefinition.requestUrl}</td>
						<td>
							<c:choose>
							<c:when test="${interfacePacketsDefinition.status=='open'}">
								开启
							</c:when>
							<c:otherwise>
				              	关闭
				       		</c:otherwise>
						</c:choose>
						</td>
						<td>
						<shiro:hasPermission name="interfacePackets:edit_show">
							<a href="${ctx}/interface/toEditInterfacePacketsDefinition?id=${interfacePacketsDefinition.id}&merchantId=${interfacePacketsDefinition.merchantId}">[修改]</a>
						</shiro:hasPermission>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.status=='open'}">
								<a onclick="closeInterface(${interfacePacketsDefinition.id})">[关闭]</a>
							</c:when>
							<c:otherwise>
								<a onclick="openInterface(${interfacePacketsDefinition.id})">[开启]</a>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
					<tr>
						<td colspan="7">没数据</td>
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
			
		function merchantInterfaceConfList(){
			$('#listForm').submit();
		}
		
		function toSaveInterface(){
			var merchantId = $("#merchantId").val();
			if(merchantId.length<=0){
				alert("请选择商户再进行添加操作！");
				return;
			}
			//window.location.href="${ctx}/interface/toSaveInterfacePacketsDefinition?merchantId="+merchantId;
			window.location.href="${ctx}/interface/createInterfaceDefinition?merchantId="+merchantId;
		}
		
		function closeInterface(id){
			var merchantId = $("#merchantId").val();
			var status = "close";
			window.location.href="${ctx}/interface/updateInterfacePacketsDefinitionStatus?merchantId="+merchantId+"&id="+id+"&status="+status;
		}
		
		function openInterface(id){
			var merchantId = $("#merchantId").val();
			var status = "open";
			window.location.href="${ctx}/interface/updateInterfacePacketsDefinitionStatus?merchantId="+merchantId+"&id="+id+"&status="+status;
		}
	</script> 
</form>
</body>
</html>

