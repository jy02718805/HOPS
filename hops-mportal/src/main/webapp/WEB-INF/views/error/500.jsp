<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
	<title>系统内部错误</title>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<link rel=stylesheet type=text/css href="${ctx}/template/admin/css/manage.css">
<script type="text/javascript">
function changeurl()
{
	top.location="${ctx}/login";
}
</script>
</head>

<body>
	<div class="error_page">
  <ul>
    <li><img src="${ctx}/template/admin/css/bg/error_bg_02.png" width="162" height="160" /> </li>
    <li>
      <h2 class="title2">页面出错了~</h2>
      <p>对不起，您访问的页面出错了。</p>
      <div class="btns"><a class="btn03" href="javascript:changeurl();">返回首页</a><a class="btn02" href="javascript:void(0);">联系客服</a></div>
    </li>
  </ul>
</div>
</body>
</html>
