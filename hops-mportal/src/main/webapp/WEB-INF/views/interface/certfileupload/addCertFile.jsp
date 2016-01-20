<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>证书列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/admin/js/ArtDialog/artDialog.js?skin=chrome" ></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.pager.js"></script>
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
</head>
<body>
<div class="mg10"></div>
		<form id="uploadform" action="uploadFile" onsubmit="return check()"
			method="post" enctype="multipart/form-data">
			<div class="list_head">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr height="30">
							<td width="200" class="textalign">请选择要导入的证书文件：</td>
								<td>
									<input type="file" id="${certfile}" name="${certfile}" size="15"  />
									别名：<input type="text" id="alias" name="alias" class="ipt">
									<shiro:hasPermission name="certfile:upload_show">
									<input id="button1" type="button" value="上传证书文件" onclick="certfileunload('${certfile}');"/>
									</shiro:hasPermission>
								<td>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
</body>
</html>


