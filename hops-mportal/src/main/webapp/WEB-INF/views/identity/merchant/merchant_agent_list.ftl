<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>商户列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
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
<form id=listForm method='post' action='agentlist'>
<div class="line_bar">
			<input id=hasExpired type=hidden name=hasExpired> 
			<input id='page' type=hidden name='page'> 
								  商户名称:<input id='merchantName' maxLength=200 class="ipt" name='merchantName' value=${(merchantName)!''}>
								  <@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								</@shiro.user>
								  <@shiro.hasPermission name="amerchant:add_show">
								  	<input type="button" class="button" onclick="self.location.href='addDownMerchant';"  value="新增代理商" />
								  </@shiro.hasPermission>
		</div>
<table id=listtable class=list>
  <tbody>
	  <tr>
		    <th><SPAN>序号</SPAN> </th>
		    <th><SPAN>所属机构</SPAN> </th>
		    <th><SPAN>商户名称</SPAN> </th>
		    <th><SPAN>商户编号</SPAN> </th>
		    <th><SPAN>商户类型</SPAN></th>
		    <th><SPAN>父级商户编号</SPAN></th>
		    <th><SPAN>商户等级</SPAN></th>
		    <th><SPAN>返佣配置状态</SPAN></th>
		    <th><SPAN>状态</SPAN> </th>
		    <th><SPAN>操作</SPAN></th>
		    
	   </tr>
    <#if (mlist?size>0)>
    <#list mlist as list>
	  <tr>
  			<td>${(page-1)*pageSize+list_index+1}</td>
		    <td>${list.organization.organizationName}</td>
		    <td>${(list.merchantName)!""}</td>
		    <td>${(list.merchantCode.code)!""}</td>
		    <td>
		    	 <#if list.merchantType??>
				        <#if list.merchantType == MerchantType.SUPPLY>
				             	供货商   			        
				        <#elseif list.merchantType == MerchantType.AGENT>
				                  代理商
				         
				        </#if>
				    </#if>
		    
		    </td>
		    <td>
		    	<#if list.parentIdentityId==0>
		    		${(list.merchantName)!""}
		    	<#else>
		    		<#list allMerchant as plist>
		    			<#if plist.id == list.parentIdentityId>
		    				${(plist.merchantName)!""}
		    			</#if>
		    		</#list>
		    	</#if>
		    </td>
		    <td>
			    <#if list.merchantLevel=="0">
			    	一级商户
			    	<#elseif list.merchantLevel=="1">
			    	二级商户
			    	<#elseif list.merchantLevel=="2">
			    	三级商户
			    	<#else>
			    </#if>
		    </td>
		    <td>
		    	<#if list.isRebate==0>
		    		已配置
		    	<#else>
		    		未配置
		    	</#if>
		    </td>
		     <#if list.identityStatus??>
				        <#if list.identityStatus.status =='2'>
			<td>
				              初始化			        
				        <#elseif list.identityStatus.status =='1'>
			<td class="tdgb">
				             禁用
				         <#elseif list.identityStatus.status =='0'>
			<td>
				             启用
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
		    <@shiro.hasPermission name="amerchant:changestatus_show">
		    	<#if list.identityStatus??>
			          <#if list.identityStatus.status =='1' || list.identityStatus.status =='2'>
			               <a href="javascript:void(0);" onclick="enable_function('${list.id}')">[启用]</a> 
			         <#elseif list.identityStatus.status =='0'>
			               <a href="javascript:void(0);" onclick="disable_function('${list.id}')">[禁用]</a> 
			        </#if>
			    </#if>
			</@shiro.hasPermission>
		       <@shiro.hasPermission name="amerchant:view">
		       		<a href="merchantview?merchantId=${list.id}">[详情]</a>
		       </@shiro.hasPermission>
		       <@shiro.hasPermission name="amerchant:edit_show">
		       	<!--  别删	<a href="edit?id=${list.id}">[编辑]</a> -->
		       </@shiro.hasPermission>
		       <@shiro.hasPermission name="amerchant:accountmanage_show">
		       		<a href="toEditMerchantAccount?id=${list.id}">[账户管理]</a>
		       </@shiro.hasPermission>
		       <@shiro.hasPermission name="amerchant:productmanage_show">
		       		<a href="agentProductRelationList?merchantId=${list.id}">[产品管理]</a>
		       </@shiro.hasPermission>
		       <!--<@shiro.hasPermission name="amerchant:viewsecurity">-->
		       	<!--<a href="${base}/security/showAgentMerchantSecurityCredential?merchantId=${list.id}" >[查询密钥]</a>-->
		       <!--</@shiro.hasPermission>-->
		       <@shiro.hasPermission name="amerchant:rebate_show">
			      <#if list.identityStatus??>
				       <#if list.identityStatus.status =='0'>
				       		<a href="${base}/RebateRule/rebateruleadd?merchantId=${list.id}" >[返佣设置]</a>
				       </#if>
			       </#if>
		       </@shiro.hasPermission>
		     </td>
	   </tr>
</#list>
<#else>
			<tr>
				<td colspan="10">没数据</td>
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
		var enable_function = function(merchantId){
		                                 		var cfg =     {
												        url:'enable',
												        type: 'GET',
                                                        data: "id="+merchantId+"&t="+ new Date().getTime(),
                                                         dataType: 'json',
                                                         contentType:'application/text;charset=UTF-8', 
                                                         success: function(result) {
                                                                    alert(result.message);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
   	      //禁用操作员                         
   	      var disable_function = function(merchantId){
   	      	                            	var cfg = {
												        url:'disable',
												        type: 'GET',
                                                        data: 'id='+merchantId+"&t="+ new Date().getTime(), 
                                                        dataType: 'json',
                                                         contentType:'application/text;charset=UTF-8', 
                                                         success: function(result) {
                                                                       alert(result.message);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
</script> 
  </form>
  </BODY>
  </HTML>

