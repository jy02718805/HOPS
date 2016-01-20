<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>页面资源信息</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
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
			<input id=hasExpired type=hidden name=hasExpired> 
			<input id=page type=hidden name=page> 
								  页面名称:<INPUT id=searchValue maxLength=200 name=pageResourceName class="ipt" value=${pageResourceName}>
								 状态:<select name="status" id="status" class="select w80">
											<option value="" selected=selected>--请选择--</option>	
											<option value="0" <#if status=="0">selected=selected</#if>>未配</option>
											<option value="1" <#if status=="1">selected=selected</#if>>已配</option>
										</select>
								<@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								</@shiro.user>
								     <@shiro.hasPermission name="pageresource:add_view">
								  <input type="button" class="button" onclick="self.location.href='add?menuid=${menuid!""}';"  value="添加页面" />
									</@shiro.hasPermission>
		 			</DIV>
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
    <TH><SPAN>序号</SPAN> </TH>
    <TH><SPAN>页面名称</SPAN> </TH>
    <TH><SPAN>页面路径</SPAN> </TH>
    <TH><SPAN>状态</SPAN> </TH>
    <TH><SPAN>创建时间</SPAN> </TH>
    <TH><SPAN>更新人</SPAN> </TH>
    <TH><SPAN>更新时间</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
    <#if (mlist?size>0)>
    <#list mlist as list>
  <TR>
			<td>${(page-1)*pageSize+list_index+1}</td>
	    <TD>${list.pageResourceName}</TD>
	    <TD>${list.pageUrl}</TD>
	     <#if list.status=="0"> <td class="tdgb">未配</span><#else><td>已配</#if></TD>
	    <TD>${list.createTime?string("yyyy-MM-dd HH:mm:ss")}</TD>
	    <TD>${(list.updateName)!""}</TD>
	    <TD>${list.updateTime?string("yyyy-MM-dd HH:mm:ss")}</TD>
	   <TD>
	    
	    <@shiro.hasPermission name="pageresource:view">
	    	<A href="pageresource_view?id=${list.resourceId}">[查看]</A>
	    </@shiro.hasPermission>
	    <@shiro.hasPermission name="pageresource:edit_view">
			<A href="pageresource_edit?id=${list.resourceId}">[编辑]</A>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="pageresource:delete">
			<A href="javascript:void(0);" onClick="delData(${list.resourceId})">[删除]</A>
		</@shiro.hasPermission>
			</TD>
   </TR>
</#list>
<#else>
			<tr>
				<td colspan="7">没数据</td>
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
			window.location.href="pageresource_delete?id="+id;
		}
</script> 
  </FORM></BODY></HTML>

