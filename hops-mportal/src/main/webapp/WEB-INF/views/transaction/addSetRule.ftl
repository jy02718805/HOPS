<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>更新上游商户查询规则</TITLE>
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
<script>
    
</script>
<body>
<div class="path">
		<a href="/admin/common/index.jhtml">首页</a> &raquo; 更新供货商查询规则
	</div>
	<form id="inputForm" action="toAddSetRule"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>商户号:
				</th>
				<td>
					<input type="hidden" id="id" name="id" class="text" maxlength="200" value="${(uqt.id)!""}"/>
					
					<#if merchantId==0>
						<select name="merchantId" id="merchantId">
							<option value="-1">请选择</option>
							<#list upMerchants as merchant>
								<option value="${merchant.id}">${merchant.merchantName}</option>
							</#list>
						 </select>
					<#else>
						<select name="merchantId" id="merchantId" disabled="disabled" >
							<option value="-1">请选择</option>
							<#list upMerchants as merchant>
								<#if merchantId==merchant.id>
									<option selected="selected" value="${merchant.id}">${merchant.merchantName}</option>
								<#else>
									<option value="${merchant.id}">${merchant.merchantName}</option>
								</#if>
							</#list>
						 </select>
					</#if>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>时间间隔量:
				</th>
				<td>
					<input type="text" id="intervalTime" name="intervalTime" class="text" maxlength="200" value="${(uqt.intervalTime)!""}"/>	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>时间间隔单位:
				</th>
				<td>
				<select name="intervalUnit" id="intervalUnit">
				<option value="">清选择</option>
						<option value="s">秒</option>
						<option value="m">分</option>
						<option value="h">时</option>
						<option value="d">天</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>最低时间差（秒）:
				</th>
				<td>
					<input type="text" id="timeDifferenceLow" name="timeDifferenceLow" class="text" maxlength="200" value="${(uqt.timeDifferenceLow)!""}"/>	 
				<span class='msg'></span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>最高时间差（秒）:
				</th>
				<td>
					<input type="text" id="timeDifferenceHigh" name="timeDifferenceHigh" class="text" maxlength="200" value="${(uqt.timeDifferenceHigh)!""}"/>	 
				<span class='msg2'></span>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" id="submitRule" name="submitRule" value="确&nbsp;&nbsp;定" />
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
		
		<script>
			function shosMsg(){
				var msg=$("#intervalUnit").find("option:selected").text();
				var intervalUnit=document.getElementById('intervalUnit').value;
				if(intervalUnit==-1||intervalUnit==null){
					$(".msg2").text("");
					$(".msg").text("");
				}else{
					$(".msg2").text(msg);
					$(".msg").text(msg);
				}
				
			}
			
			
	//比较		
			jQuery.validator.addMethod("toCompare", function(value, element,param) {   
					    var timeDifferenceLow=$(param).val();
					    return this.optional(element) || (value>=timeDifferenceLow);
					}, "最高时间差不能小于最低时间差！");
			
			
			function toRuleTime(){
			var msg=true;
				var timeDifferenceLow=document.getElementById('timeDifferenceLow').value;
				var intervalUnit=document.getElementById('intervalUnit').value;
				var timeDifferenceHigh=document.getElementById('timeDifferenceHigh').value;
				var id=document.getElementById('id').value;
				var merchantId=$('#merchantId').val();
				$.ajax({
		    	type: "post",
		        data: null,
		        url: "toRuleTime?id="+id+"&merchantId="+merchantId+"&intervalUnit="+intervalUnit+"&timeDifferenceLow="+timeDifferenceLow+"&timeDifferenceHigh="+timeDifferenceHigh,
		        async: false,
		        success: function (data) {
				    if(data=="false"){
				        alert('时差区已存在');
				        msg=false;
				     }else{
				     	msg=true;
				     }
		        },
		        error: function () {
		            alert("操作失败，请重试");
		        }
		    }); 
				
				return msg;
			}
			
			// 表单验证
				$(document).ready(function() { 
				     var validator = $("#inputForm").validate({
					rules: {
										intervalTime: {
												   required: true,
												   number: true,
													maxlength: 20
										},
										
										intervalUnit:{
											       required:true
										},
										
										timeDifferenceLow:{
											       required:true,
											       number: true,
											      maxlength: 20
										},
										timeDifferenceHigh:{
												   required:true,
												   number: true,
												   maxlength: 20
										}
							},
							messages: {
										intervalUnit: {
											required: "请选择!"
										}
							}   
    });
				     // 按钮时先验证
				     $("#submitRule").click(function() {
				     	if(validator.form()){ 
				           var msg=toRuleTime();
				          if(msg){
				          	 $("#inputForm").submit();
				          }else{
				          }
				        }
     				})
 			}); 
		</script>
	</form>
</body>
</HTML>
