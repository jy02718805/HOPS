<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>编辑信息</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#setting number_format="#">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<script src="${base}/template/admin/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/jquery.serialize.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/jquery.validate.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/usercenter.js" type="text/javascript"></script>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/user_layout.css">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/comm.css">
<script type="text/javascript">
    //菜单js
    $(function () {
        initFunctionManage();
    });
</script>
<body style="padding-top:10px;">
<div class="mg10"></div>
	<form id="inputForm" action="edit"  method="post">
	<table class="input">	
		<tr>
		<th width="15%">商户名称</th><td width="35%">${merchant.merchantName}</td>
		<th width="15%">商户编号：</th><td width="35%">${merchant.merchantCode.code}</td>
		</tr>
		<tr>
		<th>所属机构：</th><td>${merchant.organization.organizationName}</td>
		<th>商户密匙：</th><td><#if key??&&key!="">${key}<#else>暂无</#if></td>
		
		</tr>
		<tr>
		<th>商户类型：</th><td>供货商</td>
		<th>商户状态：</th><td><#if merchant.identityStatus.status??&&merchant.identityStatus.status!="">
							<#if merchant.identityStatus.status=='0'>
								启用
							<#elseif merchant.identityStatus.status=='1'>
								禁用
							<#elseif merchant.identityStatus.status=='2'>
								初始化
							<#else>
								未知
							</#if>
							</#if></td>
		
		</tr>
		
		<tr>
		<th>返佣状态：</th><td><#if merchant.isRebate??>
							<#if merchant.isRebate==0>
								已配置
							<#else>
								未配置
							</#if>
							</#if></td>
		<th></th><td></td>
		
		</tr>
		</table>
		<div class="line_bar">
	<SPAN>商户返佣信息列表:</SPAN><a href="${base}/RebateRule/list">[查看更多]</a>
</div>
		<table class="input">		   
			<tr>
				<td>
					<table id=listtable class=list>
					  <tbody>
						  <tr>						  
						      	<th class="aligncenter">发生商户 </th>
							    <th class="aligncenter">返佣商户 </th>
							    <th class="aligncenter">返佣周期 </th>
							    <th class="aligncenter">返佣方式 </th>
							    <th class="aligncenter">返佣产品 </th>
							    <th width="30%" class="aligncenter">返佣规则 </th>
							    <th class="aligncenter">状态</th>
							    <th class="aligncenter">更新人 </th>
							    <th class="aligncenter">更新时间 </th>						    
						   </tr>
						   <#list rebateRuleList as list>
						  <tr>
							    <td>${(list.merchantName)!""}</td>
							    <td>${(list.rebateMerchantName)!""}</td>
							    <td> <#if list.rebateRule.rebateTimeType ==1>
									             按月返
									         <#elseif list.rebateRule.rebateTimeType ==2>
									           按季返
									           <#elseif list.rebateRule.rebateTimeType ==3>
									           按年返
									           <#elseif list.rebateRule.rebateTimeType ==4>
									   按天返
									   		<#else>
									   		暂无
									        </#if></td>
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
							    <td width="30%">${(list.ruleName)!""}</td>
							    <td>
									        <#if list.rebateRule.status =='1'>
									             关闭
									         <#elseif list.rebateRule.status =='0'>
									             开启
									        </#if>
							    </td>
							 
							    <td>${(list.rebateRule.updateUser)!""}</td>
							    <td>${list.rebateRule.updateDate?string("yyyy-MM-dd HH:mm:ss")}</td>
						   </tr>
						   </#list>
						</tbody>
					</table>	
			   </td>
			</tr>
		</table>
		<div style="float:right;padding:10px;">
						<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
			</div>
	</form>
</body>

</HTML>
