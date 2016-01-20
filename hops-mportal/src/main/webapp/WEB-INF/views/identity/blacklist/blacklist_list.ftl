<#assign base=request.contextPath> <#setting number_format="#"> <#assign
shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>黑名单页面资源信息</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<LINK rel=stylesheet type=text/css
	href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript
	src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
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
<BODY>
	<FORM id=listForm method=get action=list>
		<div class="line_bar">
			<input id=hasExpired type=hidden name=hasExpired> <input
				id=page type=hidden name=page> 黑名单号码:<INPUT id="blacklistNo"
				maxLength="200" name="blacklistNo" value="${blacklistNo}" class="ipt" autocomplete="off">
				<th>
					业务类型:
				</th>
				<td>
					<select id="businessType" name="businessType" class="select">
					   <option value="" selected="selected"></option>
						<option value="0">话费业务</option>
						<option value="1">流量业务</option>
					</select>
				</td>
			<@shiro.user> <input type="submit" class="button"
				value="查&nbsp;&nbsp;询" /> </@shiro.user> <@shiro.hasPermission
			name="blacklist:add_show"> <input type="button" class="button"
				onclick="self.location.href='add?menuid=${menuid!""}';"  value="添加黑名单" /> </@shiro.hasPermission>
		</div>
		<TABLE id=listTable class=list>
			<TBODY>
				<TR>
					<TH><span>序号</span></TH>
					<TH><span>号码</span></TH>
					<TH><SPAN>业务类型</SPAN></TH>
					<TH><SPAN>创建时间</SPAN></TH>
					<TH><SPAN>备注</SPAN></TH>
					<TH><SPAN>操作</SPAN></TH>
				</TR>
				<#if (mlist?size>0)> <#list mlist as list>
				<TR>
					<td>${(page-1)*pageSize+list_index+1}</td>
					<TD>${(list.blacklistNo)!""}</TD> 
					<#if list.businessType == "0"> <td>话费业务</td> </#if>
					<#if list.businessType == "1"> <td>流量业务</td></#if>

					<TD>${list.createTime?string("yyyy-MM-dd HH:mm:ss")}</TD>
					<TD>${(list.remark)!""}</TD>
					<TD><@shiro.hasPermission name="blacklist:edit_show"> <A
						href="blacklist_edit?id=${list.blacklistId}">[编辑]</A>
						</@shiro.hasPermission> <@shiro.hasPermission
						name="blacklist:delete_show"> <A href="javascript:void(0);"
						onClick="delData(${list.blacklistId})">[删除]</A>
						</@shiro.hasPermission>
					</TD>
				</TR>
				</#list> <#else>
				<tr>
					<td colspan="6">没数据</td>
				</tr>
				</#if>
			</TBODY>
		</TABLE>
		<div class="line_pages">
			<div style="float: left;">
				显示条数： <select name="pageSize" id="pageSize">
					<option value="10"<#if
						pageSize==10>selected=selected</#if>>10</option>
					<option value="20"<#if
						pageSize==20>selected=selected</#if>>20</option>
					<option value="30"<#if
						pageSize==30>selected=selected</#if>>30</option>
					<option value="50"<#if
						pageSize==50>selected=selected</#if>>50</option>
					<option value="100"<#if
						pageSize==100>selected=selected</#if>>100</option>
					<option value="500"<#if
						pageSize==500>selected=selected</#if>>500</option>
					<option value="1000"<#if
						pageSize==1000>selected=selected</#if>>1000</option>
				</select>&nbsp; 条
			</div>
			<div id="pager" style="float: right;"></div>
			<div class="pages_menber">
				(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)
			</div>
		</div>
		<script type="text/javascript" language="javascript"> 
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
		}); 
		PageClick = function(pageclickednumber) { 
			  $("#pager").pager({ 
			       pagenumber: pageclickednumber,
				   pagecount: ${pagetotal}, 
				   buttonClickCallback: PageClick 
			});
			
			$("#page").val(pageclickednumber);
		   $("#listForm").submit();
		
		}
		
		function delData(id)
		{
			if (!confirm("确认要删除该数据吗?")) {
		        return false;
		    }
			window.location.href="blacklist_delete?id="+id;
		}
</script>
	</FORM>
</BODY>
</HTML>

