<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>返佣比例配置列表</title>
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
<script language="javascript">
 function deleteRR(id)
    {
    	if (confirm("你确认要删除该返佣配置吗?")) {
    	 $.ajax({
		        type: "post",
		        data: null,
		        url: "deleteRebateRule?id="+id,
				async: false,
		        success: function (data) {
			        alert('操作成功');
	                self.location.href='list';
		        },
		        error: function () {
		            alert("操作失败，请重试");
		        }
		    });
	    }
    }
</script>
<body>
<form id=listForm method=get action=list>
			<input id=hasExpired type=hidden name=hasExpired> 
			<input id=page type=hidden name=page> 
		<div class="line_bar">
								 发生商户:<select name="merchantId" id="merchantId" class="select">
											<option value="" selected>--请选择--</option>	
											<#list merchantList as list>
											<option value="${list.id}" 
											<#if merchantId == list.id>
									     	 		selected
									     	 	</#if>
									     	 	>${list.merchantName}</option>
											</#list>
										</select>
								   返佣商户:<select name="rebateMerchantId" id="rebateMerchantId" class="select">
											<option value="" selected>--请选择--</option>	
											<#list merchantList as list>
											<option value="${list.id}" <#if rebateMerchantId == list.id>selected</#if>>${list.merchantName}</option>
											</#list>
										</select>
										 清算周期:<select name="rebateTimeType" id="rebateTimeType" class="select w80">
											<option value="" selected>--请选择--</option>	
										<!--	
											<option value="1" >按月返</option>
											<option value="2" >按季返</option>
											<option value="3" >按年返</option> 
										-->
											<option value="4" 
											<#if rebateTimeType == "4">
									     	 		selected
									     	 	</#if>
									     	 	>按天返</option>
										</select>
										 返佣方式:<select name="rebateType" id="rebateType" class="select w80">
											<option value="" selected>--请选择--</option>	
											<option value="0" <#if rebateType == "0">selected</#if>>定比返佣</option>
											<option value="1" <#if rebateType == "1">selected</#if>>定额返佣</option>
										</select>
										
								<@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  </@shiro.user>
								    <@shiro.hasPermission name="rebate:add_show">
								  <input type="button" class="button" onclick="self.location.href='rebateruleadd?merchantId=';"  value="添加返佣比例配置" />
								  </@shiro.hasPermission>
		</div>
<table id=listtable class=list>
  <tbody>
	  <tr>
		    <th><SPAN>序号</SPAN> </th>
		    <th><SPAN>发生商户</SPAN> </th>
		    <th><SPAN>返佣商户</SPAN> </th>
		    <th><SPAN>商户类型</SPAN> </th>
		    <th><SPAN>清算周期</SPAN> </th>
		    <th><SPAN>返佣方式</SPAN> </th>
		    <th><SPAN>返佣产品</SPAN> </th>
		    <th><SPAN>返佣规则</SPAN> </th>
		    <th><SPAN>状态</SPAN></th>
		    <th><SPAN>更新人</SPAN> </th>
		    <th><SPAN>更新时间</SPAN> </th>
		    <th><SPAN>操作</SPAN></th>
		    
	   </tr>
    <#if (mlist?size>0)>
    <#list mlist as list>
	  <tr>
  			<td>${(page-1)*pageSize+list_index+1}</td>
		    <td>${(list.merchantName)!""}</td>
		    <td>${(list.rebateMerchantName)!""}</td>
		    <td><#if list.rebateRule.merchantType =='AGENT'>
		    	代理商
		    	<#elseif list.rebateRule.merchantType =='SUPPLY'>
		    	供货商
		    	<#else>
		    	其他
		    	</#if></td>
		    <td> <#if list.rebateRule.rebateTimeType ==4>按天返</#if></td>
		    <td><#if list.rebateRule.rebateType ==0>
				             定比返佣
				         <#elseif list.rebateRule.rebateType ==1>
				   定额返佣         
				        </#if></td>
		    <#if (list.productNames)??>
		   <td title="全部：${(list.productNamesAlt)!""}" style="color:blue;">${(list.productNames)!""}</td>
		   <#else>
		   <td title="产品未开通或暂无产品"  style="color:blue;">/</td>
		   </#if>
		    <td>${(list.ruleName)!""}</td>
				        <#if list.rebateRule.status =='1'>
				             <td class="tdgb">
				          关闭
				         <#elseif list.rebateRule.status =='0'>
				             <td>
				          开启
				        </#if>
		    </td>
		 
		    <td>${(list.rebateRule.updateUser)!""}</td>
		    <td>${list.rebateRule.updateDate?string("yyyy-MM-dd HH:mm:ss")}</td>
		    <td>
			<@shiro.hasPermission name="rebate:changestatus_show">
				 <#if list.rebateRule.status =='1'>
       				<a href="changeRebateRuleStatus?id=${list.rebateRule.rebateRuleId}&status=0">[开启]</a>
		         <#elseif list.rebateRule.status =='0'>
	   				<a href="changeRebateRuleStatus?id=${list.rebateRule.rebateRuleId}&status=1">[关闭]</a>
	   			 </#if>
			</@shiro.hasPermission>	
			<@shiro.hasPermission name="rebate:edit_show">
					<A href="editRebateRule?id=${list.rebateRule.rebateRuleId}">[编辑]</A>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="rebate:delete">
					<A href="javascript:deleteRR(${list.rebateRule.rebateRuleId});">[删除]</A>
			</@shiro.hasPermission>
		     </td>
	   </tr>
</#list>
<#else>
			<tr>
				<td colspan="12">没数据</td>
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
		
		
</script> 
  </form>
  </BODY>
  </HTML>

