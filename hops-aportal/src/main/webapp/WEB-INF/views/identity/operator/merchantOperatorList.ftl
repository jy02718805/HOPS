<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>操作员列表</title>
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
<div class="line_bar">
<form id=listForm method=get action=merchantOperatorList >
	    <input id=page type=hidden name=page> 
									操作员名称：<input type="text" id="username" name="username" class="text" maxlength="200" value="${username!""}" />
	    <div style="float:right;">
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  <input type="button" class="button" onclick="self.location.href='addMerchantOperator';"  value="新增商户操作员" />
		</div>	
	</div>
<table id=listtable class=list>
  <tbody>
	  <tr>
	  
	      <th><span>序号</span> </th>
		    <th><SPAN>所属商户</SPAN> </th>
		    <th><SPAN>类型</SPAN> </th>
		    <th><SPAN>用户名</SPAN> </th>
			<th><SPAN>姓名</SPAN> </th>
		    <th><SPAN>状态</SPAN> </th>
		    <th><SPAN>操作</SPAN></th>
		    
	   </tr>
	<#if (mlist?size>0)>
    <#list mlist as list>
	  <tr>
			<td>${(page-1)*pageSize+list_index+1}</td>
		    <td>${list.ownerIdentityName}</td>
			<td>商户操作员  </td>
		     <td>${(list.operatorName)!""}</td>
		    <td>${(list.displayName)!""}</td>
				    <#if list.identityStatus??>
				      <#if list.identityStatus.status =='1'>
				           <td class="tdgb">  禁用
				         <#elseif list.identityStatus.status =='0'>
				           <td>  启用
				         <#else>
							<td>
									未知
				        </#if>
					<#else>
					<td>
							未知
					</#if>
		    </td>
		 
		    <td>
		    <#if list.identityStatus??>
				          <#if list.identityStatus.status =='1'>
				               <a href="javascript:void(0);" onclick="enable_function('${list.id}')">[启用]</a> 
				         <#elseif list.identityStatus.status =='0'>
				               <a href="javascript:void(0);"  onclick="disable_function('${list.id}')">[禁用]</a> 
				        </#if>
				    </#if>
	    	<A href="${base}/Operator/edit?oid=${list.id}">[编辑]</A>
			<A href="toModifyPassword?id=${list.id}">[修改用户密码]</A>
		   	<A href="tosetrole?oid=${list.id}">[设置角色]</A>
		     </td>
	   </tr>
</#list>
<#else>
			<tr>
				<td colspan="7">没数据</td>
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
		
		//启用操作员
		var enable_function = function(operatorId){
		                                 		var cfg =     {
												        url:'enable',
												        type: 'GET',
                                                        data: "id="+operatorId+"&t="+ new Date().getTime(),
                                                         dataType: 'json',
                                                         contentType:'application/json;charset=UTF-8', 
                                                         success: function(result) {
                                                                    alert(result.msg);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
   	      //禁用操作员                         
   	      var disable_function = function(operatorId){
   	      	                            	var cfg = {
												        url:'disable',
												        type: 'GET',
                                                        data: 'id='+operatorId+"&t="+ new Date().getTime(), 
                                                        dataType: 'json',
                                                         contentType:'application/json;charset=UTF-8', 
                                                         success: function(result) {
                                                                       alert(result.msg);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
   	         var reset_function = function(operatorId){
   	              
   	      	                            	var cfg = {
												        url:'reset',
												        type: 'GET',
                                                        data: 'id='+operatorId+"&t="+ new Date().getTime(), 
                                                        dataType: 'json',
                                                         contentType:'application/json;charset=UTF-8', 
                                                         success: function(result) {
                                                                       alert(result.msg);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
</script> 
  </form>
  </body>
  </html>
