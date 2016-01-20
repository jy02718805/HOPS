<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>更新参数配置</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script type="text/javascript" language="javascript">  
     function doSubmit(){
  		 
      /******  参数值  ******/  
      var constantValue = $("#constantValue").val().trim();
      if(constantValue==""){  
         alert('参数值 不能为空！');  
         return false;  
      }  
      if(jmz.GetLength(constantValue)>50){  
         alert('参数值长度不能超过50！');  
         return false;  
      }  
      /******  参数名称 ******/  
      var constantName = $("#constantName").val().trim();
      if(constantName==""){  
         alert('参数名称 不能为空！');  
         return false;  
      } 
      if(jmz.GetLength(constantName)>50){  
         alert('参数名称长度不能超过50！');  
         return false;  
      }  
      /******  参数单位  ******/  
      var constantUnitValue = $("#constantUnitValue").val().trim();
      if(constantUnitValue==""){  
         alert('单位 不能为空！');  
         return false;  
      }  
      if(jmz.GetLength(constantUnitValue)>30){  
         alert('单位长度不能超过30！');  
         return false;  
      }  
      /****** 参数单位名称  ******/  
      var constantUnitName = $("#constantUnitName").val().trim();
      if(constantUnitName==""){  
         alert('单位名称不能为空！');  
         return false;  
      }   
      if(jmz.GetLength(constantUnitName)>50){  
         alert('单位名称长度不能超过50！');  
         return false;  
      }  
     	$('#inputForm').submit();
     }
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="updateParameterConfiguration"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>参数值:<input type="hidden" id="id" name="id" class="ipt" maxlength="200" value="${(hc.id)!""}"/>
				</th>
				<td>
					<input type="text" id="constantValue" name="constantValue" class="ipt" maxlength="200" value="${(hc.constantValue)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					 <span class="requiredField">*</span>参数名称:
				</th>
				<td>
				<input type="text" id="constantName" name="constantName" class="ipt" maxlength="200" value="${(hc.constantName)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>参数单位:
				</th>
				<td>
					<input type="text" id="constantUnitValue" name="constantUnitValue" class="ipt" maxlength="200" value="${(hc.constantUnitValue)!""}"/>	 
				
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>参数单位名称:
				</th>
				<td>
					<input type="text" id="constantUnitName" name="constantUnitName" class="ipt" maxlength="200" value="${(hc.constantUnitName)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					扩展字段(1):
				</th>
				<td>
					<input type="text" id="ext1" name="ext1" class="ipt" maxlength="20" value="${(hc.ext1)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					扩展字段(2):
				</th>
				<td>
					<input type="text" id="ext2" name="ext2" class="ipt" maxlength="20" value="${(hc.ext2)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" id="submitRule" name="submitRule" class="button" value="确&nbsp;&nbsp;定" onclick="doSubmit();"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back();" />
				</td>
			</tr>
		</table>
	</form>
</body>
</HTML>