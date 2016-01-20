<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>参数配置管理</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
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
	<form id='listForm' method='post' action="reportTypeList">
		<input id='page' type=hidden name='page'> 
		<div class="line_bar">
			报表名称:<input type="text"  id='reportFileName' name='reportFileName'  value="" class="ipt"/>
				
				<div style="float:right;">
				<@shiro.user>
				<input type="button" class="button" onclick="queryOrder();"  value="查询" class="button"/>
				</@shiro.user>
				 &nbsp;
				 <@shiro.hasPermission name="reportConfig:add_show">
				<input type="button" class="button" onclick="addReprotType()"  value="新增报表配置" class="button"/>
				</@shiro.hasPermission>
		 	</div>
		</div>
	
	<table  id='listtable' class='list'>
		<thead>
		<tr>
			<th>序列</th>
			<th>报表名称</th>
			<th>报表元数据类型</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<#if (reportTypetList?size>0)>
		<#list reportTypetList as rt>
			<tr>
				<td>${rt_index+1}</td>
				<td>${rt.reportFileName!""}</td>
				<td>${rt.reportMetadataTypeName!""}</td>
				<td>
				<@shiro.hasPermission name="reportConfig:edit_show">
				 <a onclick="showReportType(${rt.reportTypeId})">[修改]</a>
				 </@shiro.hasPermission>
				</td>
			</tr>
			</#list>
			 <#else>
			<tr>
				<td colspan="4">没数据</td>
			</tr>
	</#if>
		</tbody>
	</table>
<div class="line_pages">
		<div style="float:left;">
	  	显示条数：
	  	<select name="pageSize" id="pageSize" >
			<option value="10" <#if pageSize==10>selected=selected</#if>>10</option>
			<option value="20" <#if pageSize==20>selected=selected</#if>>20</option>
			<option value="30" <#if pageSize==30>selected=selected</#if>>30</option>
			<option value="50" <#if pageSize==50>selected=selected</#if>>50</option>
			<option value="100"<#if pageSize==100>selected=selected</#if>>100</option>	
			<option value="550" <#if pageSize==505>selected=selected</#if>>500</option>
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
		
		
		
		function showReportType(id){
			window.location.href="${base}/report/showReportType?reportTypeId="+id;
		}
		
		function addReprotType(){
			window.location.href="${base}/report/showReportType?reportTypeId="+"";
		}
		
		function queryOrder(){
		 	$("#listForm").submit();
			//window.location.href="${base}/report/reportTypeList?reportFileName="+$("#reportFileName").val;
		}
		
		</script>
		</form>
</body>
</HTML>
