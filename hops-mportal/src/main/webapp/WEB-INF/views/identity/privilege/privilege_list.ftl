<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>权限信息</title>
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
<FORM id=listForm method=get action=privilege_list>
<div class="line_bar">
			<input id=hasExpired type=hidden name=hasExpired> 
			<input id=page type=hidden name=page> 
								功能标识名称:<INPUT id=privilegeName maxLength=200 name=privilegeName class="ipt" value=${privilegeName}>
								  权限名称:<INPUT id=permissionName maxLength=200 name=permissionName class="ipt" value=${permissionName}>
								 父级权限:<select name="parentPId" id="parentPId" class="select">
								 			<option value="" selected="selected">--请选择--</option>
											<#list parentList as list>
												<option value="${list.privilegeId}" <#if parentPId==list.privilegeId>selected="selected"</#if>>
													${list.privilegeName}
												</option>
											</#list>
										</select>
									<@shiro.user>
								  		<input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								    </@shiro.user>
								    <@shiro.hasPermission name="privilege:add_show">
								  <input type="button" class="button" onclick="self.location.href='privilege_add?menuid=${menuid!""}';"  value="添加权限" />
									</@shiro.hasPermission>
									<@shiro.hasPermission name="privilege:tree_show">
									<input type="button" class="button" onclick="self.location.href='privilege_list_tree';"  value="树形图" />
									</@shiro.hasPermission>
				
					 </DIV>
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
    <TH><SPAN>序号</SPAN> </TH>
    <TH><SPAN>功能标识名称</SPAN> </TH>
    <TH><SPAN>权限名称</SPAN> </TH>
    <TH><SPAN>父级功能名称</SPAN> </TH>
    <TH><SPAN>创建人</SPAN> </TH>
    <TH><SPAN>创建时间</SPAN> </TH>
    <TH><SPAN>更新人</SPAN> </TH> 
    <TH><SPAN>更新时间</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
   <#if (plist?size>0)>
    <#list plist as list>
  <TR>
  <td>${(page-1)*pageSize+list_index+1}</td>
	    <TD>${(list.privilegeName)!""}</TD>
	    <TD>${(list.permissionName)!""}</TD>
	    <TD>${(list.parentPrivilegeName)!""}</TD>
	    <TD>${(list.createUser)!""}</TD>
	    <TD>${list.createTime?string("yyyy-MM-dd HH:mm:ss")}</TD>
	    <TD>${(list.updateUser)!""}</TD>
	    <TD>${list.updateTime?string("yyyy-MM-dd HH:mm:ss")}</TD>
	    <TD>
	    
	    <@shiro.hasPermission name="privilege:edit_show">
	    	<A href="privilege_edit?id=${list.privilegeId}">[编辑]</A>
	    </@shiro.hasPermission>
	    <@shiro.hasPermission name="privilege:delete">
			<A href="javascript:void(0);" onClick="delData(${list.privilegeId})">[删除]</A>
		</@shiro.hasPermission>
	    </TD>
   </TR>
</#list>
<#else>
			<tr>
				<td colspan="9">没数据</td>
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
			window.location.href="privilege_delete_tree?id="+id;
		}
</script> 
  </FORM></BODY></HTML>

