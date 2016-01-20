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
    
    $().ready(function() {
 
		var $inputForm = $("#inputForm");
	// 表单验证
		$inputForm.validate({
			rules: {
						"pageResourceName": {
						       required: true ,
								minlength: 2,
								maxlength: 30
						},
						"pageUrl": {
							   required: true ,
								minlength: 2,
								maxlength: 200
						}
					}
			
		});
				
});
 function check()
    {
		var pageResourceName=$("#pageResourceName").val();
		if(jmz.GetLength(pageResourceName) > 30){
			   alert("页面资源长度不能超过30位字符！"); 
			   return false;
		}
		var pageUrl=$("#pageUrl").val();
		if(jmz.GetLength(pageUrl) > 200){
			   alert("资源地址长度不能超过200位字符！"); 
			   return false;
		}
		$("#inputForm").submit();
	}
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="add"  method="post" >
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>页面名称:
				</th>
				<td>
					<input type="text" id="pageResourceName" name="pageResourceName" class="ipt" maxlength="30" />	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>资源地址（URL）:
				</th>
				<td>
					<input type="text" id="pageUrl" name="pageUrl" class="ipt" maxlength="200" />	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="check();"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
