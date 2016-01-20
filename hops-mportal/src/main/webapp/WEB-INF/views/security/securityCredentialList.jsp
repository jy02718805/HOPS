﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>密钥属性列表 </title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
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
</head>
<body>
<form id="inputForm" action="${ctx}/security/securityCredentialList" method="post">
<input id='page' type=hidden name='page'/>
<div class="line_bar"> 
<span class="requiredField">*</span>密钥类型:
	<select name="securityTypeId" id="securityTypeId" class='select w150'>
			<option value="">请选择</option>
			<c:forEach items="${securityTypes}" var="securityType">
				<option value="${securityType.securityTypeId}" 
					<c:if test="${securityType.securityTypeId==securityTypeId}">selected="selected"</c:if>
					>${securityType.securityTypeName}</option>
			</c:forEach>
		</select>
		<span class="requiredField">*</span>用户类型:<select name="identityType" id="identityType"  class='select w80'>
			<option value="">请选择</option>
			<option value="OPERATOR" 
				<c:if test="${identityType=='OPERATOR'}">selected="selected"</c:if>
				>操作员</option>
			<option value="CUSTOMER"
				<c:if test="${identityType=='CUSTOMER'}">selected="selected"</c:if>
				>客户</option>
			<option value="SP" 
				<c:if test="${identityType=='SP'}">selected="selected"</c:if>
				>系统</option>
			<option value="MERCHANT" 
				<c:if test="${identityType=='MERCHANT'}">selected="selected"</c:if>
				>商户</option>
		</select>&nbsp;
	状态:<select name="status" class='select w80'>
				<c:choose>
				<c:when test="${status==''}">
					<option value="" selected="selected">请选择</option>
				</c:when>
				<c:otherwise>
				<option value="">请选择</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${status=='0'}">
					<option value="0" selected="selected">启用</option>
				</c:when>
				<c:otherwise>
					<option value="0">启用</option>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${status=='1'}">
					<option value="1"  selected="selected">禁用</option>
				</c:when>
				<c:otherwise>
					<option value="1">禁用</option>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
			<c:when test="${status=='3'}">
				<option value="3"  selected="selected">过期</option>
			</c:when>
			<c:otherwise>
				<option value="3">过期</option>
			</c:otherwise>
		</c:choose>
		</select>
	用户名称:	<input type="text" id='identityName' name="identityName" value="${identityName}" class="ipt" />
		<div style="float:right;">
		<shiro:user>
	<input type="button" class="button"  value="查询" onclick="querySecurityList()"/>
	</shiro:user>
	<shiro:hasPermission name="security:add_show">
	<input type="button" class="button"  value="添加密钥" onclick="addSecurity()"/>
	</shiro:hasPermission>
	</div>
	</div>
	<table id='listTable' class='list'>
		<tbody>
		<tr>
			<th><a class=sort href="javascript:;" name=securityPropertyId>序号</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyId>密钥名称</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyId>用户名称</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyName>用户类型</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>密钥类型</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>到期时间</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>状态</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>创建时间</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>更新人</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>更新时间</a> </th>
			<th><a class=sort href="javascript:;" name=securityPropertyMaxLength>操作</a> </th>
		</tr>
		<c:choose>
		<c:when test="${fn:length(mlist) > 0}">
		<c:forEach items="${mlist}" var="securityCredential" varStatus="count">
			<tr>
				<td>${(page-1)*pageSize+count.index+1}</td>
				<td>${securityCredential.SECURITYNAME}</td>
				<td>${securityCredential.IDENTITYNAME}</td>
				<td>
				 <c:choose>
							<c:when test="${securityCredential.IDENTITYTYPE=='SP'}">
								管理员
							</c:when>
							<c:when test="${securityCredential.IDENTITYTYPE=='MERCHANT'}">
								商户
							</c:when>
							<c:when test="${securityCredential.IDENTITYTYPE=='OPERATOR'}">
								操作员
							</c:when>
							<c:when test="${securityCredential.IDENTITYTYPE=='CUSTOMER'}">
								用户
							</c:when>
							<c:otherwise>
								其他
							</c:otherwise>
						</c:choose> 
				
				</td>
				<td>
				${securityCredential.SECURITYTYPENAME}
				</td>
				<td>			
				${securityCredential.VALIDITYDATE}
				</td>
					<c:choose>
					       <c:when test="${securityCredential.STATUS==0}">
					       <td>      	 启用
					       </c:when>
					       <c:when test="${securityCredential.STATUS==1}">
					       <td class="tdgb">      	禁用
					       </c:when>
					       <c:when test="${securityCredential.STATUS==2}">
					       <td class="tdgb">      	禁用
					       </c:when>
					       <c:when test="${securityCredential.STATUS==3}">
					       <td class="tdgb">      	过期
					       </c:when>
					       <c:otherwise>
							<td>
							             	未知
							</c:otherwise>
					</c:choose>
				</td>
				<td>
				${securityCredential.CREATEDATE}
				</td>
				<td>${securityCredential.UPDATEUSER}</td>
				<td>
				${securityCredential.UPDATEDATE}
				</td>
				<td>
				<shiro:hasPermission name="security:changestatus">
					 <c:if test="${securityCredential.STATUS==0}">
				           <a href="javascript:void(0);"   onclick="disable_function('${securityCredential.SECURITYID}')">[禁用]</a>   |   
				      </c:if>
				      <c:if test="${securityCredential.STATUS==1}">
				              <a href="javascript:void(0);"  onclick="enable_function('${securityCredential.SECURITYID}')">[启用]</a>  | 
				      </c:if>
				  </shiro:hasPermission>
				  <shiro:hasPermission name="security:update_show">
				     <a href="${ctx}/security/securityCredentialDetail?securityId=${securityCredential.SECURITYID}">[查看源码]</a> |
				      </shiro:hasPermission>
				      
				      <shiro:hasPermission name="security:delete">
				      <a href="javascript:void(0);" onclick="delete_function(${securityCredential.SECURITYID})">[删除]</a>
				      	</shiro:hasPermission>
				      	 <shiro:hasPermission name="security:init">
					      	 <c:if test="${securityCredential.SECURITYTYPENAME=='PASSWORD'}">
					      		| <a href="javascript:void(0);" onclick="initializationCode('${securityCredential.SECURITYID}')" >[重置]</a>
					      	</c:if>
					      	</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
				<tr>
					<td colspan="16">没数据</td>
				</tr>
		</c:otherwise>
	</c:choose>
		</tbody>
	</table>
	<div class="line_pages">
	<div style="float:left;">
	  	显示条数：
	  	<select name="pageSize" id="pageSize" >
	  		<c:choose>
				<c:when test="${pageSize==10}">
					<option value="10" selected="selected">10</option>
				</c:when>
				<c:otherwise>
					<option value="10" >10</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==20}">
					<option value="20" selected="selected">20</option>
				</c:when>
				<c:otherwise>
					<option value="20" >20</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==30}">
					<option value="30" selected="selected">30</option>
				</c:when>
				<c:otherwise>
					<option value="30" >30</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==50}">
					<option value="50" selected="selected">50</option>
				</c:when>
				<c:otherwise>
					<option value="50" >50</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==100}">
					<option value="100" selected="selected">100</option>
				</c:when>
				<c:otherwise>
					<option value="100" >100</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
			<c:when test="${pageSize==500}">
				<option value="500" selected="selected">500</option>
			</c:when>
			<c:otherwise>
				<option value="500" >500</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${pageSize==1000}">
				<option value="1000" selected="selected">1000</option>
			</c:when>
			<c:otherwise>
				<option value="1000" >1000</option>
			</c:otherwise>
		</c:choose>
		</select>&nbsp; 条
	  </div>
	<div id="pager" style="float:right;"></div>  
	<div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
  	</div>


  	<br/>
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
			
		function querySecurityList(){
			var securityTypeId=$('#securityTypeId').val();
			var identityType=$('#identityType').val();
			
			if(identityType==""){
				alert('请输入查询条件!');
				return;
			}
			if(securityTypeId==""){
				alert('请输入查询条件!');
				return;
			} 
		
			$('#inputForm').submit();
		}
		
		//禁用
		function disable_function(id){
			window.location.href="${ctx}/security/closeSecurityCredential?securityId="+id;
		}
		//启用
		function enable_function(id){
			window.location.href="${ctx}/security/openSecurityCredential?securityId="+id;
		}
		//删除
		function delete_function(id){
			if(confirm('是否确定删除？')){
		    	window.location.href="${ctx}/security/deleteSecurityCredential?securityId="+id;
      		}
			
		}
		
		//添加
		function addSecurity(){
			window.location.href="${ctx}/security/securityCredentialAdd";
		}
		
		//初始化密码
		function initializationCode(id){
			if(confirm('是否确定重置？')){
				window.location.href="${ctx}/security/initializationCode?securityId="+id;
			}
		}
		
	</script> 
</form>
</body>
</html>

