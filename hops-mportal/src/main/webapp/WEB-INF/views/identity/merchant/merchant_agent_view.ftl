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
		<th width="15%">商户名称：</th><td width="35%">${merchant.merchantName}</td>
		<th width="15%">商户编号：</th><td width="35%">${merchant.merchantCode.code}</td>
		</tr>
		<tr>
		<th>所属机构：</th><td>${merchant.organization.organizationName}</td>
		<th>级别：</th><td><#if merchant.merchantLevel??&&merchant.merchantLevel!="">
							<#if merchant.merchantLevel == "0">
					                              一级代理商  			        
					        <#elseif merchant.merchantLevel == "1">
					                               二级代理商  
					        <#elseif merchant.merchantLevel == "2">
					                             三级代理商  
					        <#elseif merchant.merchantLevel == "3">
					                              四级代理商  
					        </#if>
				        </#if></td>
		</tr>
		<tr>
		<th>商户类型：</th><td>代理商</td>
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
		<th>父级商户：</th><td><#if parentMerchant.merchantName??&&parentMerchant.merchantName!="">
							${parentMerchant.merchantName}
							<#else>
								顶级
							</#if></td>
		
		</tr>
		<tr>
		<th>商户密匙：</th><td>${key}</td>
		<th>层级信息展示:</th><td>
		<div class="user_text">
        <ul id='functionTree' class="treeL1">
        	<#if merchant1.id==0&&merchant0.id==0>
	        	<li class="clear">
	        		<span class="hand treeL1_li_span span_up" style="color:red;" funcId="${merchant.id}" parentId="0" enabled="0">${merchant.merchantName}</span>
                    <ul class="treeL2" style="padding-left:10px;">
        				<#list merchantlist1 as list1>	        				
    						<#if (list1.identityStatus.status=="0" && list1.parentIdentityId==merchant.id)>
                            <li class="clear"><span class="hand treeL2_li_span span_down" funcId="${list1.id}" parentId="${merchant.id}" enabled="0">${list1.merchantName}</span>
                                <ul class="treeL3" style="padding-left:10px;">
        							<#list merchantlist2 as list2>
    									<#if (list2.identityStatus.status=="0" && list2.parentIdentityId==list1.id)>
                                        <li class="clear"><span class="treeL3_li_span hand span_down" funcId="${list2.id}" parentId="${list1.id}" level="end" enabled="0">${list2.merchantName}</span>
                                            
                                            <ul class="treeL4 clearfix" style="padding-left:10px;">
        										<#list merchantlist3 as list3>
    												<#if (list3.identityStatus.status=="0" && list3.parentIdentityId==list2.id)>
                                                    <li><span class="hand treeL4_li_span span_up" funcId="${list3.id}" parentId="${list2.id}" level="end" enabled="0">${list3.merchantName}</span></li>	                                                
            										</#if>
            									</#list>                     
                                            </ul>
                                        </li> 
            							</#if>
                                    </#list>                    
                                </ul>
                            </li>
            				</#if>
                        </#list>
                    </ul>
	            </li>
	        <#elseif merchant1.id==0&&merchant0.id!=0>
	        	<li class="clear">
	        		<span class="hand treeL1_li_span span_up" funcId="${merchant0.id}" parentId="0" enabled="0">${merchant0.merchantName}</span>
	            	<ul class="treeL2" style="padding-left:10px;">
	                	<li class="clear">
	                		<span class="hand treeL2_li_span span_down" style="color:red;" funcId="${merchant.id}" parentId="${merchant0.id}" enabled="0">${merchant.merchantName}</span>
	                		<ul class="treeL3" style="padding-left:10px;">
    							<#list merchantlist2 as list2>
									<#if (list2.identityStatus.status=="0" && list2.parentIdentityId==merchant.id)>
                                    <li class="clear"><span class="treeL3_li_span hand span_down" funcId="${list2.id}" parentId="${merchant.id}" level="end" enabled="0">${list2.merchantName}</span>
                                        <ul class="treeL4 clearfix" style="padding-left:10px;">
    										<#list merchantlist3 as list3>
												<#if (list3.identityStatus.status=="0" && list3.parentIdentityId==list2.id)>
                                                <li><span class="hand treeL4_li_span span_up" funcId="${list3.id}" parentId="${list2.id}" level="end" enabled="0">${list3.merchantName}</span></li>	                                                
        										</#if>
        									</#list>                     
                                        </ul>
                                    </li> 
        							</#if>
                                </#list>                    
                            </ul>
	                    </li>
	                </ul>
	            </li>
	        <#elseif merchant1.id!=0&&merchant0.id!=0>
	        	<li class="clear">
	        		<span class="hand treeL1_li_span span_up" funcId="${merchant0.id}" parentId="0" enabled="0">${merchant0.merchantName}</span>
	            	<ul class="treeL2" style="padding-left:10px;">
	                	<li class="clear">
	                		<span class="hand treeL2_li_span span_down" funcId="${merchant1.id}" parentId="${merchant0.id}" enabled="0">${merchant1.merchantName}</span>
	                    	<ul class="treeL3" style="padding-left:10px;">
	                        	<li class="clear">
	                        		<span class="treeL3_li_span hand span_down" style="color:red;" funcId="${merchant.id}" parentId="${merchant1.id}" level="end" enabled="0">${merchant.merchantName}</span>
	                        		<ul class="treeL4 clearfix" style="padding-left:10px;">
										<#list merchantlist3 as list3>
											<#if (list3.identityStatus.status=="0" && list3.parentIdentityId==merchant.id)>
                                            <li><span class="hand treeL4_li_span span_up" funcId="${list3.id}" parentId="${merchant.id}" level="end" enabled="0">${list3.merchantName}</span></li>	                                                
    										</#if>
    									</#list>                     
                                    </ul>
	                            </li> 
	                        </ul>
	                    </li>
	                </ul>
	            </li>
	        </#if>        
        </ul>
    </div>
						<a href="merchanttreelist">[查看所有商户树形结构]</a>
		</td>
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
