<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加角色</TITLE>
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
<script>
    
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="customer_view"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>用户名称:
				</th>
				<td>
					${(customer.customerName)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>显示名称:
				</th>
				<td>
					${(customer.displayName)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>性别:
				</th>
				<td>
					<INPUT id="person.sex" name="person.sex"  type="radio" value="0" <#if customer.person.sex=="0">checked</#if>  >男
					<INPUT id="person.sex" name="person.sex"  type="radio" value="1" <#if customer.person.sex=="1">checked</#if>   >女
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>出生日期:
				</th>
				<td>
					${(customer.person.birthday)!""}
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>用户类型:
				</th>
				<td>
					<select name="userType" id="userType" class="select">
					<option value="HuFei">
						跃程缴费
					</option>
				</select>
				</td>
			</tr>
			
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='list?menuid=${menuid!""}'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
