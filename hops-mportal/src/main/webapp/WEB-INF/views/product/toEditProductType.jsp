<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>实体卡列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<LINK rel=stylesheet type=text/css
	href="${ctx}/template/admin/css/common.css">
<body>	
<div class="mg10"></div>
	<form action="${ctx}/product/doEditProductType" id="inputForm" method="post">
		<input type="hidden" name="beans" id="beans">
		<input type="hidden" name="typeId" id="typeId" value="${typeId}">
		<div>
			产品类型名称：&nbsp; <input type="text" name="productTypeName" id="productTypeName" value="${name}"/>
		</div>
		<div>
		    <table id="tab" class="list">
		    	<tbody>
		        <tr>
		            <!--td>产品属性名称</td-->
		            <td>属性</td>
		            <td>属性对应值</td>
		            <td>规则描述</td>
		        </tr>
		        <c:forEach items="${productPropertys}" var="productProperty">
					<tr>
						<c:if test="${productProperty.flag == true}">
							<td><input type="checkbox" name="cbox" value="${productProperty.productPropertyId}" checked="checked"></td>
						</c:if>
						<c:if test="${productProperty.flag == false}">
							<td><input type="checkbox" name="cbox" value="${productProperty.productPropertyId}" ></td>
						</c:if>
						<td>${productProperty.paramName}</td>
						<td>
							<c:if test="${productProperty.attribute == 'select'}">
<%-- 								<textarea rows="2" cols="100" readonly="readonly">${productProperty.value}</textarea> --%>
							</c:if>
							<c:if test="${productProperty.attribute == 'word'}">
								最低长度限制:${productProperty.minLength}|最大长度限制:${productProperty.maxLength}
							</c:if>
						</td>
					</tr>
				</c:forEach>
		        <tbody>
	    	</table>
		    <div style="border:2px; 
		                border-color:#00CC00; 
		                margin-left:20%;
		                margin-top:20px">
		        <input type="button" value="提交" onclick="result()"/>
		    </div>
	    </div>
	</form>
		<script type="text/javascript" language="javascript">
		    function result(){
		    	var checks = "";
				$("input[name='cbox']").each(function(){
					if($(this).attr("checked") == true){
						checks += $(this).val() + "|";            //动态拼取选中的checkbox的值，用“|”符号分隔
					}
				});
				if(checks.length == 0){
					alert("请选择属性！");
					return;
				}
				checks = checks.substring(0, checks.length-1);
				$("#beans").val(checks);
		    	$('#inputForm').submit();
		    }
		</script>
</body>
</html>