<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>查看密钥</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/template/admin/js/ArtDialog/artDialog.js?skin=chrome"></script>
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

body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	margin: 0;
}

#main {
	height: 100%;
	padding-top: 90px;
	text-align: center;
}

#fullbg {
	left: 0;
	opacity: 0.5;
	position: absolute;
	top: 0;
	z-index: 3;
	filter: alpha(opacity = 50);
	-moz-opacity: 0.5;
	-khtml-opacity: 0.5;
}

#dialog {
	background-color: #fff;
	border: 5px solid rgba(0, 0, 0, 0.4);
	height: 150px;
	left: 50%;
	margin: -200px 0 0 -200px;
	padding: 1px;
	position: fixed !important; /* 浮动对话框 */
	position: absolute;
	top: 300px;
	width: 500px;
	z-index: 5;
	border-radius: 5px;
	display: none;
}

#dialog p {
	margin: 0 0 12px;
	height: 24px;
	line-height: 24px;
	background: #CCCCCC;
}

#dialog p.close {
	text-align: right;
	padding-right: 10px;
}

#dialog p.close a {
	color: #fff;
	text-decoration: none;
}

.drag {
	border: 2px solid #000;
	width: 100px;
	height: 100px;
	cursor: move;
	position: absolute;
	left: 0;
	top: 0;
}
</style>
<body>
	<form id="inputForm" method="post"
		action="${ctx}/security/updateSecretKey">
		<div class="mg10"></div>
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>用户名称:</th>
				<td>${identityName}</td>
				<th><span class="requiredField">*</span>用户类型:</th>
				<td><c:choose>
						<c:when test="${securityCredential.identityType=='SP'}">
								管理员
							</c:when>
						<c:when test="${securityCredential.identityType=='MERCHANT'}">
								商户
							</c:when>
						<c:when test="${securityCredential.identityType=='OPERATOR'}">
								操作员
							</c:when>
						<c:when test="${securityCredential.identityType=='CUSTOMER'}">
								用户
							</c:when>
						<c:otherwise>
								其他
							</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>密钥类型:</th>
				<td>${securityCredential.securityType.securityTypeName}</td>
				<th>到期时间：:</th>
				<td><fmt:formatDate value="${securityCredential.validityDate}"
						type="both" dateStyle="medium" /></td>
			</tr>
			<tr>
				<th>密钥名称:</th>
				<td>${securityCredential.securityName}</td>
				<th>状态:</th>
				<td><select name="status" disabled="true">
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
								<option value="1" selected="selected">禁用</option>
							</c:when>
							<c:otherwise>
								<option value="1">禁用</option>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${securityCredential.status==3}">
								<option value="3" selected="selected">过期</option>
							</c:when>
							<c:otherwise>
								<option value="3">过期</option>
							</c:otherwise>
						</c:choose>
				</select></td>
			</tr>
			<tr>
				<th>密钥原串:</th>
				<td colspan="3">${securityCredential.securityValue}
					&nbsp;&nbsp;&nbsp;&nbsp; <c:if
						test="${securityCredential.securityType.securityTypeName=='AGENTMD5KEY'}">
						<input type="button" class="button" value="更新" onclick="showBg()" />
					</c:if> <c:if
						test="${securityCredential.securityType.securityTypeName=='SUPPLYMD5KEY'}">
						<input type="button" class="button" value="更新" onclick="showBg()" />
					</c:if> <c:if
						test="${securityCredential.securityType.securityTypeName=='SUPPLYPUBLICKEY'}">
						<input type="button" class="button" value="更新" onclick="showBg()" />
					</c:if>

				</td>
			</tr>
			<tr>
				<th>&nbsp;<input type="hidden" name="securityId"
					id="securityId" value="${securityCredential.securityId}" />
				</th>
				<td><input type="button" class="button" value="返&nbsp;&nbsp;回"
					onclick="history.back();" /></td>
			</tr>
		</table>

		<div id="main">
			<div id="fullbg"></div>
			<div id="dialog" class='drag'>
				<p class="close">
					<a href="#" onclick="closeBg();">关闭</a>
				<div>
					<table class="input">
						<tr>
							<th>密钥:</th>
							<td><input type="text" id='securityValue'
								name="securityValue" size="30" value="${securityValue}" />&nbsp;&nbsp;
								<c:if
									test="${securityCredential.securityType.securityTypeName=='AGENTMD5KEY'}">
									<a href="javascript:void(0);" onclick="updateSecurity();">[自动更新]</a>
								</c:if></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" class="button"
								value="确&nbsp;&nbsp;定" /> &nbsp; &nbsp; <input type="button"
								class="button" value="关&nbsp;&nbsp;闭" onclick="closeBg();" /></td>
						</tr>
					</table>
				</div>
			</div>
		</div>



		<script type="text/javascript" language="javascript">
			function updateSecurity() {
				var securityId = $("#securityId").val();
				var securityValue = $("#securityValue").val();

				$.ajax({
					type : 'post',
					url : "autoUpdateSecurity",
					data : {
						"securityId" : securityId
					},
					dataType : 'html',
					async : false,
					success : function(data) {
						$("#securityValue").val(data);
					},
					error : function() {
						alert("操作失败，请重试");
					}
				}).responseText;
			}

			function refreshWd() {
				var securityId = $("#securityId").val();
				window.location.href = "${ctx}/security/securityCredentialDetail?securityId"
						+ securityId;
			}

			//显示灰色 jQuery 遮罩层 
			function showBg() {
				var bh = $("body").height();
				var bw = $("body").width();
				$("#fullbg").css({
					height : bh,
					width : bw,
					display : "block"
				});
				$("#dialog").show();
			}
			//关闭灰色 jQuery 遮罩 
			function closeBg() {
				$("#fullbg,#dialog").hide();
			}

			$(function() {
				$(".drag").mousedown(function(e) {
					$(".drag").fadeTo(20, 0.5); //点击后开始拖动并透明显示
				});
				$(document).mouseup(function() {
					$(".drag").fadeTo("fast", 1); //松开鼠标后停止移动并恢复成不透明
				});
			});
		</script>

	</form>
</body>
</html>

