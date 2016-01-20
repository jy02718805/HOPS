<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加黑名单</TITLE>
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
<script language="javascript">
function check(){  
	  var blacklistNo = $("#blacklistNo").val().trim();
      if(blacklistNo=="" || blacklistNo == null || blacklistNo.length != 11){  
         alert('黑名单号码不能为空，且必须为11位数字！');  
         return false;  
      } 
      
      var businessType = $("#businessType").val().trim();
      if(businessType==""){  
         alert('业务类型不能为空！');  
         return false;  
      } 
 }  
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="add"  method="post" onsubmit="return check()" >
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>黑名单号码:
				</th>
				<td>
					<input type="text" id="blacklistNo" name="blacklistNo" class="ipt" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" autocomplete="off"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>业务类型:
				</th>
				<td>
					<select id="businessType" name="businessType" class="select">
						<option value="0">话费业务</option>
						<option value="1">流量业务</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					备注:
				</th>
				<td>
					<input type="text" id="remark" name="remark" class="ipt" maxlength="30" autocomplete="off"/>	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="确&nbsp;&nbsp;定" />
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='list'" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
