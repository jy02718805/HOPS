<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>商户列表</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
	<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script type="text/javascript" language="javascript">
		$(document).ready(function() {
			var attribute = $("#attribute_select").val();
			if(attribute=="word"){
				$("#div_word_minLength").show();
				$("#div_word_maxLength").show();
				$("#div_value").hide();
			}else{
				$("#div_word_minLength").hide();
				$("#div_word_maxLength").hide();
				$("#div_value").show();
			}
		});
		
		function attributeChange(){
			var attribute = $("#attribute_select").val();
			if(attribute=="word"){
				$("#div_word_minLength").show();
				$("#div_word_maxLength").show();
				$("#div_value").hide();
			}else{
				$("#div_word_minLength").hide();
				$("#div_word_maxLength").hide();
				$("#div_value").show();
			}
		}
		
		function doProductPropertyRegister(){
			var paramName = $("#paramName").val();
			var attribute = $("#attribute_select").val();
			$("#attribute").val(attribute);
			var minLength = $("#minLength").val();
			var maxLength = $("#maxLength").val();
			var value     = $("#value").val();
			$('#inputForm').attr("action", "${base}/product/doProductPropertyRegister");
			$('#inputForm').submit();
		}
</script>
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
<div class="mg10"></div>
	<form id="inputForm" action="${base}/product/doProductPropertyRegister" method="post" class="form-horizontal">
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>属性名称:</th>
				<td>
					<input type="text" name="paramName" id="paramName" class="ipt"/>
			   </td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>属性英文名称:</th>
				<td>
					<input type="text" name="paramEnglishName" id="paramEnglishName" class="ipt"/>
			   </td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>字段类型:
				</th>
				<td>
					<input type="hidden" id="attribute" name="attribute" class="ipt"/>
					<select name = "attribute_select" id="attribute_select"  class="select w80" onchange="attributeChange()">
						<option value="word">word</option>
						<option value="select">select</option>
						<option value="radio">radio</option>
					</select>
				</td>
			</tr>
			<tr id="div_word_minLength">
				<th><span class="requiredField">*</span>字段最小长度:</th>
				<td>
					<input type="text" id="minLength" name="minLength" class="ipt"/>
			   </td>
			</tr>
			<tr id="div_word_maxLength">
				<th><span class="requiredField">*</span>字段最大长度:</th>
				<td>
					<input type="text" id="maxLength" name="maxLength" class="ipt"/>
			   </td>
			</tr>
			<tr id="div_value">
				<th><span class="requiredField">*</span>默认值:</th>
				<td>
					<textarea name="value" id="value" cols ="50" rows = "8"></textarea>
					(用'|'号分隔)，比如"长沙|湘潭|株洲"
			   </td>
			</tr>
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="doProductPropertyRegister()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
