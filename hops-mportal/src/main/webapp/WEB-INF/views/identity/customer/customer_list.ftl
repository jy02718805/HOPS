<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<#setting number_format="#">
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<SCRIPT type=text/javascript>
</SCRIPT>
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

<BODY>
<FORM id=listForm method=get action=list>
<div class="line_bar">
		<INPUT id=page type=hidden name=page> 
		<INPUT id=hasExpired type=hidden name=hasExpired> 
								 用户名称:<INPUT id=searchValue maxLength=200 name=person.userName class="ipt" value="${userName!""}"/>
								显示名称:<INPUT id=person.displayName maxLength=200 name=person.displayName class="ipt" value="${displayName!""}"/>
								性别:<select name="person.sex" id="person.sex" class="select w80">
										<option value="">所有</option>
										<option value="0">男</option>
										<option value="1">女</option>
									</select>
								<@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								</@shiro.user>
								<@shiro.hasPermission name="customer:add_show">
								  <input type="button" class="button" onclick="self.location.href='add?menuid=${menuid!""}';"  value="添加用户" />
								</@shiro.hasPermission>
</div>			
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
   <TH><SPAN>序号</SPAN> </TH>
    <TH><SPAN>用户名称</SPAN> </TH>
    <TH><SPAN>显示名称</SPAN> </TH>
    <TH><SPAN>性别</SPAN> </TH>
    <TH><SPAN>生日</SPAN> </TH>
    <TH><SPAN>状态</SPAN> </TH>
    <TH><SPAN>创建时间</SPAN> </TH>
    <TH><SPAN>更新时间</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
    <#if (mlist?size>0)>
    <#list mlist as list>
	    <#if list.identityStatus.status=="0">
		  <TR>
		  <td>${(page-1)*pageSize+list_index+1}</td>
			    <TD>${(list.customerName)!""}</TD>
			    <TD>${(list.displayName)!""}</TD>
			    <TD><#if list.person.sex=="0"> 男<#else>女</#if></TD>
			    <TD>${(list.person.birthday)!""}</TD>
			    <#if list.identityStatus.status=="0">
			    	<td>
				     启用
				<#else>
				     <td class="tdgb">
				    禁用
				</#if></TD>
				<TD>${list.createTime?string("yyyy-MM-dd HH:mm:ss")}</TD>
			    <TD>${list.updateTime?string("yyyy-MM-dd HH:mm:ss")}</TD>
			    <TD>
			    <@shiro.hasPermission name="customer:view">
			    	<A href="customer_view?id=${list.id}">[查看]</A>
			    </@shiro.hasPermission>
			    <@shiro.hasPermission name="customer:edit_show">
			    	<A href="customer_edit?id=${list.id}">[编辑]</A>
			    </@shiro.hasPermission>
			    <@shiro.hasPermission name="customer:delete">
			    	<A href="javascript:void(0);" onClick="delData(${list.id})">[删除]</A>
			    </@shiro.hasPermission>
			    <@shiro.hasPermission name="customer:addroles_show">
			    	<A href="addCustomerRole?id=${list.id}">[分配角色]</A>
			    </@shiro.hasPermission>
				</TD>
		   </TR>
	   </#if>
</#list>
<#else>
			<tr>
				<td colspan="8">没数据</td>
			</tr>
	</#if>

</TBODY></TABLE>
<div class="line_pages">
<div style="float:left;">
  	显示条数：
  	<select name="pageSize" id="pageSize" >
		<option value="10" <#if pageSize==10>selected=selected</#if>>10</option>
		<option value="20" <#if pageSize==20>selected=selected</#if>>20</option>
		<option value="30" <#if pageSize==30>selected=selected</#if>>30</option>
		<option value="50" <#if pageSize==50>selected=selected</#if>>50</option>
		<option value="100"<#if pageSize==100>selected=selected</#if>>100</option>	
		<option value="500" <#if pageSize==500>selected=selected</#if>>500</option>
		<option value="1000"<#if pageSize==1000>selected=selected</#if>>1000</option>
	</select>&nbsp; 条
  </div>
<div id="pager" style="float:right;"></div>
<div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
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
			window.location.href="customer_delete?id="+id;
		}
</script> 
</FORM></BODY></HTML>

