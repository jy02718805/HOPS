<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html4019991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>订单信息</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/provinceCity.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/DateUtil.js"></script>
<LINK rel=stylesheet type=text/css
	href="${ctx}/template/admin/css/common.css">
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>

<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		var uri = "${deliveryResult}";
		var decodeDeliveryResult = decodeURI(uri);
		$("#decodeDeliveryResult").val(decodeDeliveryResult);
	});
</script>
<textarea rows="100%" cols="100%" id="decodeDeliveryResult"
	name="decodeDeliveryResult">
		${deliveryResult}
	</textarea>
</html>