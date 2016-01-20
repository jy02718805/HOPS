<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>实体卡列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.pager.js"></script>
	<script type="text/javascript" src="${ctx}/template/common/js/provinceCity.js"></script>
<LINK rel=stylesheet type=text/css
	href="${ctx}/template/admin/css/common.css">
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
	<form id="listForm" method="post"
		action="${ctx}/product/agentProductRelationInfoList">
		<div class="line_bar">
			<input id='page' type='hidden' name='page' /> 
			运营商 :<select name="carrierInfo" id="carrierInfo" class="select w80">
				<option value="">请选择</option>
				<c:forEach items="${carrierInfos}" var="carrierInfo">
					<c:choose>
						<c:when test="${carrierInfo.carrierNo==carrierInfoVal}">
							<option value="${carrierInfo.carrierNo}" selected="selected">${carrierInfo.carrierName}</option>
						</c:when>
						<c:otherwise>
							<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
			省份:<select name="province" id="province" class="select w80">
				<option value="">请选择</option>
				<c:forEach items="${provinces}" var="province">
					<c:choose>
						<c:when test="${province.provinceId eq provinceVal}">
							<option value="${province.provinceId}" selected="selected">${province.provinceName}</option>
						</c:when>
						<c:otherwise>
							<option value="${province.provinceId}">${province.provinceName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			面值:<input type="text"
				id="faceValue" name="faceValue" class="ipt w80" value="${faceValue}" />
			<div style="float: right; padding-top: 10px;">
				<input type="button" class="button" value="查询产品"
					onclick="queryDownProductRelation()" />
			</div>

		</div>
		<table id=listTable class=list>
				<tr>
					<th><SPAN>序号</SPAN></th>
					<th><SPAN>产品名称</SPAN></th>
					<th><SPAN>面值</SPAN></th>
					<th><SPAN>运营商</SPAN></th>
					<th><SPAN>省份</SPAN></th>
					<th><SPAN>城市</SPAN></th>
					<th><SPAN>折扣</SPAN></th>
					<th><SPAN>产品状态</SPAN></th>
				</tr>
				<tbody class="listTable">
				<c:choose>
					<c:when test="${fn:length(mlist) > 0}">
						<c:forEach items="${mlist}" var="downProductRelation"  varStatus="status">
							<tr>
								<td>${(page-1)*pageSize+status.index+1}</td>
								<td>${downProductRelation.productName}</td>
								<td>${downProductRelation.parValue}</td>
								<td>
								<span class="carrierInfo_${status.count}">${downProductRelation.carrierName}</span>
								</td>
								<td>
								<c:if test="${downProductRelation.province==null}">
								--
								</c:if>
								
								<c:if test="${downProductRelation.province!=null}">
								<span class="p_${status.count}">${downProductRelation.province}</span>
								</c:if>
								</td>
								<td>
								
								<c:if test="${downProductRelation.city==null}">
								--
								</c:if>
								
								<c:if test="${downProductRelation.city!=null}">
								<span class="c_${status.count}">${downProductRelation.city}</span>
								</c:if>
								</td>
								<td>${downProductRelation.discount}</td>
								<c:choose>
									<c:when test="${downProductRelation.status == '1'}">
										<td>可用
									</c:when>
									<c:when test="${downProductRelation.status == '0'}">
										<td class="tdgb">不可用
									</c:when>
									<c:otherwise>
										<td>未知
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="8">没数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
		</table>

		<div class="line_pages">
			<div style="float: left;">
				&nbsp; 显示条数： <select name="pageSize" id="pageSize" class="select">
					<option value="10"
						<c:choose><c:when test="pageSize==10">selected=selected</c:when></c:choose>>10</option>
					<option value="20"
						<c:choose><c:when test="pageSize==20">selected=selected</c:when></c:choose>>20</option>
					<option value="30"
						<c:choose><c:when test="pageSize==30">selected=selected</c:when></c:choose>>30</option>
					<option value="50"
						<c:choose><c:when test="pageSize==50">selected=selected</c:when></c:choose>>50</option>
					<option value="100"
						<c:choose><c:when test="pageSize==100">selected=selected</c:when></c:choose>>100</option>
				</select>&nbsp; 条
			</div>
			<div id="pager" style="float: right;"></div>
			<div class="pages_menber">
				(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)
			</div>
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
			
		function toSaveProduct(){
			window.location.href="${ctx}/product/toSaveProduct";
		}
		
		function showProductTrees(){
			window.location.href="${ctx}/product/showProductTrees";
		}
		
		function updateProductStatus(status,productId){
			var actionStr = "";
			if(status == 0){
				//关闭
				actionStr = "关闭";
			}else if(status == 1){
				//开启
				actionStr = "开启";
			}
			if(window.confirm("确实要"+actionStr+"此产品吗？")){
				window.location.href="${ctx}/product/updateProductStatus?productId="+productId+"&productStatus="+status;
		    }else{
		    	
		    }
		}
		
		function toEditProduct(productId){
			window.location.href="${ctx}/product/toEditProduct?productId="+productId;
		}
		
		function deleteProduct(productId){
			if(window.confirm("确实要删除吗？")){
				window.location.href="${ctx}/product/deleteProudct?productId="+productId;
			}else{
			    	
			}
		}
		
		function queryDownProductRelation(){
			$("#listForm").submit();
		}
		
		
		
	</script>
	</form>
</body>
</html>

