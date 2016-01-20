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
		<INPUT id=page type=hidden name=page> 
		<INPUT id=hasExpired type=hidden name=hasExpired> 
		<div class="line_bar">
				商户:<select name="merchantId" id="merchantId" class="select" >
						<option value="" selected=selected>--请选择--</option>	
							<#list merchantList as list>
								<option value="${list.id}" <#if merchantId==list.id>selected=selected</#if>>${list.merchantName}</option>
							</#list>
					</select>
				用户类型:<select name="identityType" id = "identityType" class="select w80">
						<option value="" >--请选择--</option>	
						<option value="MERCHANT" <#if identityType=='MERCHANT'>selected=selected</#if>>商户</option>
						<option value="CUSTOMER" <#if identityType=='CUSTOMER'>selected=selected</#if>>用户</option>
					</select>
				关键字:<INPUT id=searchValue maxLength=200 name=key value="${key}" class="ipt">
				<@shiro.user>
				  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
				</@shiro.user>
				<@shiro.hasPermission name="interfaceConstant:add_show">
				  <input type="button" class="button" onclick="self.location.href='addinterfaceconstant';"  value="添加" />
				</@shiro.hasPermission>
		</DIV>
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
  	<TH><SPAN>序号</SPAN> </TH>
    <TH><SPAN>商户名称</SPAN> </TH>
    <TH><SPAN>关键字</SPAN> </TH>
    <TH><SPAN>常量值</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
    <#if (mlist?size>0)>
	    <#list mlist as list>
	      <TR>
	  		<td>${(page-1)*pageSize+list_index+1}</td>
		    <TD>${list.identityName}</TD>
		    <TD>${(list.key)!""}</TD>
		    <TD>${(list.value)!""}</TD>
		    <TD>
		    <@shiro.hasPermission name="interfaceConstant:edit_show">
			    <A href="editinterfaceconstant?id=${list.id}">[编辑]</A>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="interfaceConstant:delete">
				<A href="javascript:void(0);" onclick='deleteInterfaceConstant(${list.id})'>[删除]</A>
		    </@shiro.hasPermission>
	      </TR>
	    </#list>
	    <#else>
			<tr>
				<td colspan="5">没数据</td>
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
		
		function deleteInterfaceConstant(constatn){
			if (confirm("确定删除接口常量参数？")) {
					window.location.href = "deleteinterfaceconstant?id="+constatn;
				}
		}
		
</script> 
</FORM></BODY></HTML>

