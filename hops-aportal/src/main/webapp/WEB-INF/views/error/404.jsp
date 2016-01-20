<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>页面不存在</title>
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
    <li><img src="${ctx}/template/admin/css/bg/error_bg_03.png" width="162" height="160" /> </li>
    <li>
      <h2 class="title1">非常抱歉！！</h2>
      <p>对不起，您访问的资源暂时找不到了。</p>
      <div class="btns"><a class="btn01" href="javascript:changeurl();">返回首页</a><a class="btn02" href="javascript:void(0);">联系客服</a></div>
    </li>
  </ul>
</div>
</body>
</html>