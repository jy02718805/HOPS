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
<script>
    function addinterfaceconstant(){
    	var identityId = $("#identityId").val();
    	if(identityId == 0){
    		alert("请选择商户！");
    		return false;
    	}
    	
    	var identityType = $("#identityType").val();
    	if(identityType == 0){
    		alert("请选择用户类型！");
    		return false;
    	}
    	
    	var key = $("#key").val();
    	if(key == ''){
    		alert("关键字不能为空！");
    		return false;
    	}
    	if(jmz.GetLength(key) >64){
    		alert("关键字长度不能超过64位！");
    		return false;
    	}
    	
    	var value = $("#value").val();
    	if(value == ''){
    		alert("常量值不能为空！");
    		return false;
    	}
    	if(jmz.GetLength(value) >256){
    		alert("常量值长度不能超过256位！");
    		return false;
    	}
    	$('#inputForm').submit();
    }
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="addinterfaceconstant"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>商户名称:
				</th>
				<td>
					<select name="identityId" id = "identityId" class="select">
						<option value="0" selected=selected>--请选择--</option>	
						<#list merchantList as list>
						<option value="${list.id}">${list.merchantName}</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>用户类型:
				</th>
				<td>
					<select name="identityType" id = "identityType" class="select">
						<option value="0" selected=selected>--请选择--</option>	
						<option value="MERCHANT">商户</option>
						<option value="CUSTOMER">用户</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>关键字:
				</th>
				<td>
					<input type="text" id="key" name="key" class="ipt" maxlength="64" value=""/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>常量值:
				</th>
				<td>
					<input type="text" id="value" name="value" class="ipt" maxlength="256" value=""/>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="addinterfaceconstant()" />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
