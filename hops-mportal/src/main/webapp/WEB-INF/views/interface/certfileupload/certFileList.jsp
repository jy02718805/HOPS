<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>证书列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type=text/javascript src="${ctx}/template/admin/js/common.js"></script>
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
<body>
	<form id='listForm' method='post' action=''>
		<input id=hasExpired type=hidden name=hasExpired/> <input
			id='page' type=hidden name='page'/>
		<div class="line_bar">
			证书别名: <input type="text" name="alias" id="alias" class="ipt" />
			<shiro:user>
				<input type="submit" class="button" value="查&nbsp;&nbsp;询"
					onclick="certfilelist();" />
			</shiro:user>
			<shiro:hasPermission name="certfile:add_show">
				<input type="button" class="button" value="证书导入"
					onclick="addCertFile();" />
			</shiro:hasPermission>
		</div>
		<table id=listtable class=list>
			<tbody>
				<tr>
					<th><SPAN>序号</SPAN></th>
					<th><SPAN>别名</SPAN></th>
					<th><SPAN>版本号</SPAN></th>
					<th><SPAN>序列号</SPAN></th>
					<th><SPAN>有效期起始日</SPAN></th>
					<th><SPAN>有效期截至日</SPAN></th>
					<th><SPAN>签名算法</SPAN></th>
					<th><SPAN>操作</SPAN></th>
				</tr>

				<c:choose>
					<c:when test="${fn:length(certFileList) > 0}">
						<c:forEach items="${certFileList}" var="certFile"
							varStatus="status">
							<tr>
								<td>${(page-1)*pageSize+status.index+1}</td>
								<td>${certFile.alias}</td>
								<td>${certFile.version}</td>
								<td>${certFile.serialNumber}</td>
								<td><fmt:formatDate value="${certFile.notBefore}"
										type="both" dateStyle="medium" /></td>
								<td><fmt:formatDate value="${certFile.notAfter}"
										type="both" dateStyle="medium" /></td>
								<td>${certFile.sigAlgName}</td>
								<td><shiro:hasPermission name="certfile:delete_show">
										<a href="javascript:void(0);"  onclick="deleteFile('${certFile.alias}')">[删除]</a>
									</shiro:hasPermission></td>
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

		<script type="text/javascript" language="javascript">
			function certfilelist() {
				var alias = $("#alias").val();
				window.location.href = "${ctx}/certfile/certfilelist?alias="
						+ alias;
			}

			//证书导入
			function addCertFile() {
				var html = $.ajax({
					url : "addCertFile",
					async : false
				}).responseText;
				art.dialog({
					content : html,
					title : '导入证书文件',
					lock : true,
					ok : false
				});
			}

			function certfileunload(fileId) {
				var fileUrl = $("#" + fileId).val();
				if (fileUrl == "") {
					alert("请输入需要导入的证书。");
					return false;
				}
				var alias = $("#alias").val();
				if (alias == "") {
					alert("请输入证书别名。");
					return false;
				}
				$('#uploadform').submit();
			}

			function deleteFile(alias) {
				if (confirm("确定删除证书？")) {
					window.location.href = "${ctx}/certfile/deleteCertFile?alias="+alias;
				}

			}
		</script>
		</div>
	</form>
</BODY>
</HTML>

