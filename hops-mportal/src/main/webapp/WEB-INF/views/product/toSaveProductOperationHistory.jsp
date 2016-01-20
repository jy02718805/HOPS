﻿﻿﻿﻿﻿﻿﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>

<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>创建产品批量操作</title>
	
	<script>
	function doSaveProductOperationHistory(){
		var carrierName = $("#carrierName").val();
		if(carrierName == ''){
			alert("请选择运营商！");
			return;
		}
		var merchantType = $("#merchantType").val();
		if(merchantType == ''){
			alert("请选择商户类型！");
			return;
		}
		var operationFlag = $("#operationFlag").val();
		if(operationFlag == ''){
			alert("请选择操作！");
			return;
		}
		
		merchantIds="";
		var r=document.getElementsByName("objectMerchantId");
	    for(var i=0;i<r.length;i++){
	         if(r[i].checked){
	        	 merchantIds=merchantIds+r[i].value+"|";
	       }
	    }
	    if(merchantIds == ''&&merchantType!='ALL'){
			alert("请选择商户！");
			return;
		}
		$('#inputForm').attr("action", "${ctx}/productOperation/doSaveProductOperationHistory?merchantIds="+merchantIds);
		$('#inputForm').submit();
	}
	
	function getCityByProvince(obj){
		var provinceId = $(obj).val();
	    if(provinceId != -1){
			$.ajax({
					url:"${ctx}/product/getCityByProvince?provinceId="+provinceId, 
					type: "post",
			        data: null,
			        async: false,
					success:function(data) {
						$('#city').html(data);
					}
			});
	    }else{
	    	$('#city').html("");
	    }
	}
	
	function checkProducts(allCheckbox){
		var bl = allCheckbox.checked;	
		
		$('input[name="objectMerchantId"]').each(function(){
			this.checked=bl;
		});
	}
	
	function unCheckProducts(cbox){
		var bl = cbox.checked;	
		var flag=true;
		$('input[name="objectMerchantId"]').each(function(){
			if(this.checked!=bl)
			{
				flag=false;
			}
		});
		if(flag||(!flag&&!bl)){
			document.getElementById('checkproduct').checked=bl;
		}
	}
	
	function getMerchantByMType(type){
		if(type==""||type==null||type=="ALL"){
			$("#merchantlist").css("display","none");
		}else{
			$("#merchantlist").css("display","");
			
		    $.ajax({
		        type: "post",
		        data: null,
		        url: "getMerchantByMType?merchantType="+type,
				async: false,
		        success: function (data) {
			        var merchantlist=data.split("|");
			        var strhtml=""; 
			        var i=0;
			        while(i<merchantlist.length-1)
			        {
			        	if(i==0)
		        		{
			        		strhtml=strhtml+"<input type='checkbox' id='checkproduct' name='checkproduct' onclick='checkProducts(this);'/>全部<br/>";
		        		}
			        	var merchant=merchantlist[i].split("*");
						strhtml=strhtml+"<input type='checkbox' name='objectMerchantId' value='"+merchant[0]+"' onclick='unCheckProducts(this);'/>"+merchant[1];
			        	i++;
			        }
			        document.getElementById("divshelflist").innerHTML=strhtml;
		        },
		        error: function () {
		            alert("操作失败，请重试");
		        }
		    }); 
		}
	}
	
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/productOperation/doSaveProductOperationHistory" method="post" class="form-horizontal">
	<div class="mg10"></div>	
	<table id="productProperty" class="input">
		    <tr>
				<th><span class="requiredField">*</span>运营商:</th>
				<td>
					<select name = "carrierName" id="carrierName" class="select w80">
						<option value="">请选择</option>
						<c:forEach items="${carrierInfos}" var="carrierInfo">
							<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
						</c:forEach>
					</select>
			   </td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>省份:
				</th>
				<td>
					<select name="province" id="province" onchange="getCityByProvince(this)" class="select w80">
						<option value="">请选择</option>
						<c:forEach items="${provinces}" var="province">
							<option value="${province.provinceId}">${province.provinceName}</option>
						</c:forEach>
					 </select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>城市:
				</th>
				<td>
					<select name="city" id="city" class="select w80">
						<div id="city">
							<option value="">请选择</option>
						</div>
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>面值:</th>
				<td>
					<input id="parValue" name="parValue" type="text" class="ipt w80"/>
			   </td>
			</tr>
			<tr>
				<th style="width:150px;"><span class="requiredField">*</span>商户类型:</th>
				<td>
					<select name="merchantType" id="merchantType" class="select w80" onchange="getMerchantByMType(this.value)">
						<option value="">请选择</option>
						<option value="ALL">全部商户</option>
						<option value="SUPPLY">供货商</option>
						<option value="AGENT">代理商</option>
					 </select>
			   </td>
			</tr>
			<tr id="merchantlist" style="display:none;">
				<th >
					<span class="requiredField">*</span>商户列表:
				</th>
				<td>
					<div id="divshelflist">
					</div> 
				</td>
			</tr>	
			<tr>
				<th><span class="requiredField">*</span>操作:</th>
				<td>
					<select name="operationFlag" id="operationFlag" class="select w80">
						<option value="">请选择</option>
						<option value="1">打开</option>
						<option value="0">关闭</option>
					 </select>
			   </td>
			</tr>
		</table>
		<table class="input">
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="button" type="button" onclick="doSaveProductOperationHistory()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
