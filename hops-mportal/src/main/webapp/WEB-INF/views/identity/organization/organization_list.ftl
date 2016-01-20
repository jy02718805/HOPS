<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>组织机构信息</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
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

<body>
<form id='listForm' method='post' action='${base}/Organization/organizationList'>
	<div class="line_bar">
			<input id='hasExpired' type='hidden' name='hasExpired' /> 
			<input id='page' type=hidden name='page'/> 
							  机构名称：<input id='searchValue' class="ipt" maxLength=200 name='organizationName' value=${organizationName}>
								  <@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  </@shiro.user>
								    <@shiro.hasPermission name="organization:add_show">
								  <input type="button" class="button" onclick="self.location.href='organization_add';"  value="新增组织机构" />
								  </@shiro.hasPermission>
						
	</div>
<table id=listtable class=list>
  <tbody>
  <tr>
    <th><SPAN>序号</SPAN> </th>
    <th><SPAN>组织名称</SPAN> </th>
    <th><SPAN>工商注册号</SPAN> </th>
    <th><SPAN>注册地址</SPAN> </th>
    <th><SPAN>所属行业</SPAN></th>
    <th><SPAN>网址</SPAN> </th>
    <th><SPAN>法人</SPAN> </th>
    <th><SPAN>邮编</SPAN> </th>
    <th><SPAN>操作</SPAN> </th></tr>
   <#if (mlist?size>0)>
   <#list mlist as list>
  <tr>
  		<td>${(page-1)*pageSize+list_index+1}</td>
	    <td>${list.organizationName!""}</td>
	    <td>${(list.organizationRegistrationNo)!""}</td>
	    <td>${(list.organizationRegistrationAddr)!""}</td>
	    <td>${(list.organizationIndustry)!""}</td>
	    <td>${(list.organizationWebsite)!""}</td>
	    <td> ${(list.legal)!""}</td>
	      <td> ${(list.postcode)!""}</td>
	  
	    <td>
		<@shiro.hasPermission name="organization:view">
				<A href="organization_view?id=${list.organizationId}">[查看]</A>
		</@shiro.hasPermission>	
		<@shiro.hasPermission name="organization:edit_show">
				<A href="organization_edit?id=${list.organizationId}">[编辑]</A>
		</@shiro.hasPermission>	
		</td>
   </tr>
</#list>
<#else>
			<tr>
				<td colspan="9">没数据</td>
			</tr>
	</#if>
</tbody></table>
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
		
		
		function changePageSize(){
			$("#listForm").submit();
		}
</script> 
  </form>
  </BODY>
  </HTML>

