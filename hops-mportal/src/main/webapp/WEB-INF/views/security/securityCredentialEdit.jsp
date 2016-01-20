<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>账户日志列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<form id="inputForm" method="post" action="${ctx}/security/ ">
	<br/>
	<label>用户名称:</label>
	<input type="text" id='identityId' name="identityId" value="${securityCredential.identityId}" />
	<label>${securityCredential.identityId}</label>
	
	&nbsp;&nbsp;&nbsp;&nbsp;
	<label>用户类型:</label>
	<label>${securityCredential.identityType}</label>
	<input type="text" id='identityType' name="identityType" value="${securityCredential.identityType}" />
	<br/>
	
	<label>密钥名称:</label>
	<input type="text" id='securityName' name="securityName" value="${securityCredential.securityName}"  maxlength="20"/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	
	<label>密钥类型:</label>
	<input type="text" id='securityTypeId' name="securityTypeId" value="${securityCredential.securityType.securitytypeid}" />
	
	
	&nbsp;&nbsp;
	
	<label>到期时间：:</label>
	<label> </label>
	<br/>
	<label>密钥源码:</label>
	<input type="text" id='securityValue' readonly="true" class="ipt" disabled="true" name="securityValue" value="${securityCredential.securityValue}" />
	&nbsp;
  	<br/>
  	<label>状态:</label>
  	<select name="status" disabled="true" class="select">
			<c:choose>
				<c:when test="${securityCredential.status==0}">
					<option value="0" selected="selected">启用</option>
				</c:when>
				<c:otherwise>
					<option value="0">启用</option>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${securityCredential.status==1}">
					<option value="1"  selected="selected">禁用</option>
				</c:when>
				<c:otherwise>
					<option value="1">禁用</option>
				</c:otherwise>
			</c:choose>
		</select>
	<br/>
	&nbsp;&nbsp;
	<input type="button" class="button"  value="更新" onclick="  "/>
		&nbsp;&nbsp;
	<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
		
 	<script type="text/javascript" language="javascript">
			function queryProfit(){
				
			}
	</script> 
</form>
</body>
</html>

