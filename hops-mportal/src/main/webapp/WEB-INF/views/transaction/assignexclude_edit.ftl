<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加角色</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/yuecheng/assignexclude.js"></SCRIPT>
<script language="javascript">

</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action=""  method="get">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>业务编号:
				</th>
				<td>
					<select name="businessNo" id="businessNo"  onchange="getProduct('','')" class="select">
						<option value="HF" <#if assignExclude.businessNo=='HF'>selected=selected</#if> >话费业务</option>
						<option value="LL" <#if assignExclude.businessNo=='LL'>selected=selected</#if> >流量业务</option>
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>商户类型:
				</th>
				<td>
					<select name="merchantType" id="merchantType"  class="select">
						<option value="${assignExclude.merchantType!""}"><#if assignExclude.merchantType=="SUPPLY">供货商<#else>代理商</#if></option>
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>商户名称:
				</th>
				<td>
					<select name="merchantId" id="merchantId"  class="select">
						<option value="${assignExclude.merchantId!""}">${assignExclude.merchantName!""}</option>
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>指定排除类型:
				</th>
				<td>
					<select name="ruleType" id="ruleType" class="select">
						<option value="${assignExclude.ruleType!""}"><#if assignExclude.ruleType==1>指定<#else>排除</#if></option>
						
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>产品:
				</th>
				<td>
					<select name="productNo" id="productNo" class="select">
					<option value="${assignExclude.productNo!""}">${assignExclude.productName!""}</option>						
					</select>	
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>被作用商户:
				</th>
				<td>
				<div id="divshelflist">
				<#list objList as list>
					<#if list[2]=="0">
						<input type="checkbox" name="objectMerchantId" value="+${list[0]}" checked/>${list[1]}
					<#else>
						<input type="checkbox" name="objectMerchantId" value="+${list[0]}"/>${list[1]}
					</#if>
				</#list>
				</div> 
				</td>
			</tr>		
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="确&nbsp;&nbsp;定"  onclick="return editAssignExclude()"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='listAssignExclude'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
