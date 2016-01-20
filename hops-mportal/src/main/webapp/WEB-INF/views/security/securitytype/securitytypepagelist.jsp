<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>密钥类型列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
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
	<form id="inputForm" action="${ctx}/securitytype/securitytypepagelist" method="post">
	<input type="hidden" id='page' name ='page'/>
		<div class="line_bar">
			密钥类型名称:<input type="text" id="securitytypename"
				name="securitytypename" value="${securitytypename}" class="ipt w150" />
			密钥属性:<select name="modeltype" id="modeltype" class="select w80">
				<option value="" selected="selected">请选择</option>
				<option value="MD5Key"
					<c:if test='${modeltype=="MD5Key"}'>selected='selected'</c:if>>MD5Key</option>
				<option value="Password"
					<c:if test='${modeltype=="Password"}'>selected='selected'</c:if>>Password</option>
				<option value="RSA公钥"
					<c:if test='${modeltype=="RSA公钥"}'>selected='selected'</c:if>>RSA公钥</option>
			</select> 加密类型:<select name="encrypttype" id="encrypttype" class="select w80">
				<option value="" selected="selected">请选择</option>
				<option value="MD5"
					<c:if test='${encrypttype=="MD5"}'>selected='selected'</c:if>>MD5</option>
				<option value="3DES"
					<c:if test='${encrypttype=="3DES"}'>selected='selected'</c:if>>3DES</option>
				<option value="RSA"
					<c:if test='${encrypttype=="RSA"}'>selected='selected'</c:if>>RSA</option>
			</select> 状态:<select name="status" id="status" class="select w80">
				<option value="" selected="selected">请选择</option>
				<option value="0" 
					<c:if test='${status=="0"}'>selected='selected'</c:if>>启用</option>
				<option value="1" 
					<c:if test='${status=="1"}'>selected='selected'</c:if>>禁用</option>
			</select> 密钥规则:<select name="securityruleid" id="securityruleid"
				class="select w100">
				<option value="" selected="selected">请选择</option>
				<c:forEach items="${securityRuleList}" var="securityRule">
					<option value="${securityRule.securityRuleId}"
						<option value="1" <c:if test='${securityRule.securityRuleId==securityruleid}'>selected='selected'</c:if>
					>${securityRule.securityRuleName}</option>
				</c:forEach>
			</select>
			<div style="float: right;">
				<shiro:user>
					<input type="button" class="button" value="查询"
						onclick="securitytypeList()" />
				</shiro:user>
				<shiro:hasPermission name="securitytype:add_show">
					<input type="button" class="button" value="添加类型"
						onclick="addsecuritytypey()" />
				</shiro:hasPermission>
			</div>
		</div>
		<table id=listTable class=list>
			<tbody>
				<tr>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>序号</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>密钥类型名称</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>密钥属性</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>加密类型</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>最大长度</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>最小长度</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>有效期（天）</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>状态</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>可用用户类型</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyName>密钥规则</a></th>
					<th><a class=sort href="javascript:;"
						name=securityPropertyMaxLength>操作</a></th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(securitytypelist) > 0}">
						<c:forEach items="${securitytypelist}" var="securitytype"
							varStatus="count">
							<tr>
								<td>${(page-1)*pageSize+count.index+1}</td>
								<td>${securitytype.securityTypeName}</td>
								<td>${securitytype.modelType}</td>
								<td>${securitytype.encryptType}</td>
								<td>${securitytype.maxLength}</td>
								<td>${securitytype.minLength}</td>
								<td>${securitytype.validity}</td>
								<c:choose>
									<c:when test="${securitytype.status==0}">
										<td>启用
									</c:when>
									<c:when test="${securitytype.status==1}">
										<td class="tdgb">禁用
									</c:when>
								</c:choose>
								</td>
								<td>${securitytype.identityType}</td>
								<td>${securitytype.securityRule.securityRuleName}</td>
								<td><shiro:hasPermission name="securitytype:changestatus">
										<c:choose>
											<c:when test="${securitytype.status==0}">
												<a
													href="${ctx}/securitytype/editstatussecuritytype?securitytypeid=${securitytype.securityTypeId}&status=1"
													onclick="return donostatus();">[禁用]</a>
											</c:when>
											<c:when test="${securitytype.status==1}">
												<a
													href="${ctx}/securitytype/editstatussecuritytype?securitytypeid=${securitytype.securityTypeId}&status=0"
													onclick="return dostatus();">[启用]</a>
											</c:when>
										</c:choose>
									</shiro:hasPermission> <shiro:hasPermission name="securitytype:edit_show">
										<a
											href="${ctx}/securitytype/editpagesecuritytype?securitytypeid=${securitytype.securityTypeId}">[编辑]</a>
									</shiro:hasPermission> <shiro:hasPermission name="securitytype:delete">
										<a
											href="${ctx}/securitytype/delsecuritytype?securitytypeid=${securitytype.securityTypeId}"
											onclick="return dodelete();">[删除]</a>
									</shiro:hasPermission></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="11">没数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="line_pages">
			<div style="float: left;">
				&nbsp; 显示条数： <select name="pageSize" id="pageSize" class="select">
					<option value="10"
						<c:choose><c:when test="${pageSize==10}">selected=selected</c:when></c:choose>>10</option>
					<option value="20"
						<c:choose><c:when test="${pageSize==20}">selected=selected</c:when></c:choose>>20</option>
					<option value="30"
						<c:choose><c:when test="${pageSize==30}">selected=selected</c:when></c:choose>>30</option>
					<option value="50"
						<c:choose><c:when test="${pageSize==50}">selected=selected</c:when></c:choose>>50</option>
					<option value="100"
						<c:choose><c:when test="${pageSize==100}">selected=selected</c:when></c:choose>>100</option>
					<option value="500"
						<c:choose><c:when test="${pageSize==500}">selected=selected</c:when></c:choose>>500</option>
					<option value="1000"
						<c:choose><c:when test="${pageSize==1000}">selected=selected</c:when></c:choose>>1000</option>
				</select>&nbsp; 条
			</div>
			<div id="pager" style="float: right;"></div>
			<div class="pages_menber">
				(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)
			</div>
			<br />
		</div>
		<br />
		<script type="text/javascript" language="javascript">
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
			});
		PageClick = function(pageclickednumber) { 
		$("#pager").pager({ pagenumber: pageclickednumber, pagecount: ${pagetotal}, buttonClickCallback: PageClick 
		}); 
		$("#page").val(pageclickednumber);
		$("#inputForm").submit();
		}
			
		function securitytypeList(){
			$('#inputForm').submit();
		}
		
		function addsecuritytypey(){
			window.location.href="${ctx}/securitytype/addpagesecuritytype";
		}
		
		//确认删除
		function dodelete(){
		    return confirm("确认删除吗？");
		}
		
		//确认禁用
		function donostatus(){
		    return confirm("确认禁用吗？");
		}
		//确认启用
		function dostatus(){
		    return confirm("确认启用吗？");
		}
		
	</script>
	</form>
</body>
</html>

