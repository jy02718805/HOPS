<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加商户</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#assign base=request.contextPath>
<#setting number_format="#">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="organization_view"  method="post">
		<table class="input">
			<tr>
				<th>
					机构名称:
				</th>
				<td> ${(organization.organizationName)!""}</td>
				<th>
					工商注册号:
				</th>
				<td> ${(organization.organizationRegistrationNo)!""}</td>
			</tr>
			<tr>
				<th>
				注册地址:
				</th>
				<td> ${(organization.organizationRegistrationAddr)!""}</td>
				<th>
					所属于行业:
				</th>
				<td> ${(organization.organizationIndustry)!""}</td>
			</tr>
			<tr>
				<th>
					网站地址:
				</th>
				<td> ${(organization.organizationWebsite)!""}</td>
				<th>
					法人:
				</th>
				<td> ${(organization.legal)!""}</td>
			</tr>
			
			<tr>
				<th>
					邮编:
				</th>
				<td> ${(organization.postcode)!""}</td>
				<th>
				</th>
				<td></td>
			</tr>
		</table>
<div class="mg10"></div>
		<table id=listtable class=list>
			  <tbody>
				  <tr>
					    <th><SPAN>商户名称</SPAN> </th>
					    <th><SPAN>商户编号</SPAN> </th>
					    <th><SPAN>商户类型</SPAN></th>
					    <th><SPAN>状态</SPAN> </th>
				   </tr>
			    <#list mlist as list>
				  <tr>
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
						<#if list.identityStatus??>
							<#if list.identityStatus.status =='2'>
							<td>
							              初始化			        
							<#elseif list.identityStatus.status =='1'>
							<td style="background-color: #FF8040;">
							             禁用
							<#elseif list.identityStatus.status =='0'>
							<td>
							             启用
							</#if>
						</#if>
					    </td>
				   </tr>
			</#list>
				</tbody>
			</table>
			<div style="float:right;padding:10px;">
						<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
			</div>
	</form>
</body>

</HTML>
