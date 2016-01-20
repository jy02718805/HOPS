<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<SCRIPT type=text/javascript>
	function toProductPropertyRegister(){
		window.location.href="${base}/product/toProductPropertyRegister";
	}
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
<div class="mg10"></div>
<FORM id=listForm method=get action=list>
<!--<@shiro.hasPermission name="productProperty:add_show">
&nbsp;<input type="button" class="button" value="创建产品属性" onclick="toProductPropertyRegister()"/>
</@shiro.hasPermission>-->
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
  <TH><SPAN>序列</SPAN> </TH>
    <TH><SPAN>属性名称</SPAN> </TH>
    <TH><SPAN>属性类型</SPAN> </TH>
    <TH><SPAN>字段最大长度</SPAN></TH>
    <TH><SPAN>字段最小长度</SPAN> </TH>
    <TH><SPAN>默认值</SPAN> </TH>

    <#list mlist as list>
  <TR>
  		<td>${(page-1)*pageSize+list_index+1}</td>
	    <TD>${list.paramName}</TD>
	    <TD>
	    	<#if list.attribute=="select">
				单选框
			</#if>
			<#if list.attribute=="word">
				文本框
			</#if>
	    </TD>
	    <TD>${(list.minLength)!""}</TD>
	    <TD>${(list.maxLength)!""}</TD>
	    <TD>${(list.value)!""}</TD>
   </TR>
</#list>
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
</script> 
</FORM></BODY></HTML>

