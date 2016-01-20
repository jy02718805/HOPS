<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加商户</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#setting number_format="#">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script language="javascript" type="text/javascript" src="${base}/template/admin/My97DatePicker/WdatePicker.js"></script>


<body>
<div class="mg10"></div>
	<form id="inputForm" action="edit"  method="post">
		<table class="input">
		   <!-- <tr>
				<th style="text-align: right; word-break:break-all;">所属商户名称:</th>
				<td>
                          ${(operator.ownerIdentityId)!""}
			   </td>
			</tr>
			
			<tr>
				<th style="text-align: right; word-break:break-all;">所属商户类别:</th>
				<td>
                          ${(operator.ownerIdentityType)!""}
			   </td>
			</tr>
			-->
			<tr>
				<th style="text-align: right; word-break:break-all;">操作员类型:</th>
				<td>
                          <#switch operator.operatorType>
						          <#case "SP_OPERATOR">
						              系统操作员
						             <#break>
						           <#case "MERCHANT_ADMIN">
						              商户管理员
						             <#break>
						           <#case "MERCHANT_USER">
						              商户操作员
						             <#break>
						           <#default>
						           	  <#break>
		   					</#switch>
			   </td>
			</tr>
			
			<tr>
				<th style="text-align: right; word-break:break-all;">用户名:</th>
				<td>
					  ${(operator.operatorName)!""}
			   </td>
			</tr>
	        <tr>
				<th style="text-align: right; word-break:break-all;">姓名:</th>
				<td>
					${(operator.displayName)!""}
			   </td>
			</tr>
			
			<tr>
				<th style="text-align: right; word-break:break-all;">
					生日:
				</th>
				<td>
						${(operator.person.birthday)!""}
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
