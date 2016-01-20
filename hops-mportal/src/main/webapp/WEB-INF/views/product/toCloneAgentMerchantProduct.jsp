﻿﻿﻿﻿﻿﻿﻿﻿﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>

<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
<link rel="stylesheet" href="${ctx}/template/admin/css/zTreeStyle-2014_11_7.css" type="text/css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/ztree/jquery.popupSmallMenu.js"></script>
	<title>商户产品批量创建</title>
	
	<script>
	function cloneDownMerchantProduct(){
		var newMerchantId = $("#newMerchantId").val();
		if(newMerchantId == -1){
			alert("请选择待复制代理商！");
			return;
		}
		
		var oldMerchantId = $("#oldMerchantId").val();
		if(oldMerchantId == -1){
			alert("请选择原代理商！");
			return;
		}
		
		var ids="";
        $("input[name='ids']:checkbox").each(function(){ 
            if($(this).attr("checked")){
            	ids = ids + $(this).val()+",";
            }
        });
        ids = ids.substring(0, ids.length-1);
        if(ids.length == 0){
        	alert("所选产品为空，不能克隆产品！");
			return;
        }
        
        if(newMerchantId==oldMerchantId){
        	alert('原商户不能与待复制商户相同!');
        	return;
        }
        $("#productIds").val(ids);
		$('#inputForm').submit();
	}
	
	function changeOldMerchantSelect(obj) {
		$("#oldMerchantId").empty();
		$("#newMerchantId option").each(function(){
			var oValue = $(this).val().toString();	
			var oText = $(this).text().toString();	
			var option = $("<option>").val(oValue).text(oText);
			$("#oldMerchantId").append(option);
		});
		
	    var newMerchantId = $(obj).val();
	    $("#oldMerchantId option[value="+newMerchantId+"]").remove();
	}
	
	function getDownMerchantProductList(){
		$('#product_ids').html("");
		var oldMerchantId = $("#oldMerchantId").val();
		var newMerchantId = $("#newMerchantId").val();
		var businessType = $("#businessTypeQuery").val();
		var isCommonUse='';
		var carrierInfo=$('#carrierInfo').val();
		var province=$('#province').val();
		var city=$('#city').val();
		var faceValue=$('#faceValue').val();
		var faceValue2=$('#faceValue2').val();
		
		if(faceValue!='' || faceValue2!=''){
			if(faceValue2=='' || faceValue==''){
				alert('面值范围请填写完整!');
				return;
			}
		}
		
		
		$('input[name="inCommon"]:checked').each(function(){
			isCommonUse=$(this).val();
		});
		
	    if(oldMerchantId != -1){
			$.ajax({
					url:"${ctx}/product/getAgentMerchantProductList?merchantId="+oldMerchantId +"&newMerchantId="+newMerchantId+"&carrierInfo="+carrierInfo+"&businessType="+businessType+"&province="+province
					+"&city="+city+"&faceValue="+faceValue+"&faceValue2="+faceValue2+"&isCommonUse="+isCommonUse, 
					type: "post",
			        data: null,
			        async: false,
					success:function(data) {
						$('#product_ids').append(data);
					}
			});
	    }else{
	    	$('#product_ids').html("");
	    }
	    
	    $('input[name="inCommon"]:checked').each(function(){
			var bl = this.checked;	
			$('input[name="ids"]').each(function(){
				this.checked=bl;
			});
		});
	    
	    $('input[name="to_all"]:checked').each(function(){
			var bl = this.checked;	
			$('input[name="ids"]').each(function(){
				this.checked=bl;
			});
		});
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
	
	
	$(document).ready(function(){
		   $('#to_all').click(function(){
			   var bl = this.checked;	
				if(bl){
					$('#inCommon').attr('checked',false);
					getDownMerchantProductList();
				}
				$('input[name="ids"]').each(function(){
					this.checked=bl;
				});
		});
		   
		   
		   $('#inCommon').click(function(){
			   $('#to_all').attr('checked',false);
				var inCommon_bl = this.checked;
				getDownMerchantProductList();
				$('input[name="ids"]').each(function(){
					this.checked=inCommon_bl;
				});
		   });	
	});
	
	
	 
	function changeMerchant(){
//		 $('input[name="inCommon"]:checked').each(function(){
//					getDownMerchantProductList();
//		 });
//		 $('input[name="to_all"]:checked').each(function(){
//					getDownMerchantProductList();
//		 });	
		getDownMerchantProductList();
	}
	</script>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/product/cloneAgentMerchantProduct" method="post" class="form-horizontal">
		<input type="hidden" id="productIds" name="productIds" class="ipt"/>
		<table id="merchant" class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>原代理商:
				</th>
				<td>
					<select name = "oldMerchantId" id="oldMerchantId" class="select required" onChange='changeMerchant();'>
						<option id="oldMerchantIds" value="-1">请选择</option>
						<c:forEach items="${downMerchants}" var="merchant">
						    <option id="oldMerchantIds" value="${merchant.id}">${merchant.merchantName}</option>
					    </c:forEach>
					</select>
				</td>
			</tr>
		    <tr>
				<th>
					<span class="requiredField">*</span>待复制代理商:
				</th>
				<td>
					<select name = "newMerchantId" id="newMerchantId" class="select required" onChange='changeMerchant();'>
						<option value="-1">请选择</option>
						<c:forEach items="${downMerchants}" var="merchant">
						    <option value="${merchant.id}">${merchant.merchantName}</option>
					    </c:forEach>
					</select>
			   </td>
			</tr>
		</table>
		<table class="input">
			<tr>
				<th>
				操作:
				</th>
				<td>
				&nbsp&nbsp业务类型 : <select id="businessTypeQuery" name="businessTypeQuery"  class="select w80" >
					    <option value="" >请选择</option>
					    <option value="0" >话费</option>
					    <option value="1" >流量</option>
					    </select>
				&nbsp&nbsp运营商 :<select name="carrierInfo" id="carrierInfo" class="select w80">
									<option value="">请选择</option>
									<c:forEach items="${carrierInfos}" var="carrierInfo">
									<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
									</c:forEach>
									</select> 
				&nbsp&nbsp省份:	<select name="province" id="province" onchange="getCityByProvince(this)" class="select w80">
									<option value="">请选择</option>
									<c:forEach items="${provinces}" var="province">
									<option value="${province.provinceId}">${province.provinceName}</option>
									</c:forEach>
									</select>
				&nbsp&nbsp城市:	<select name="city" id="city" class="select w80">
									<option value="">请选择</option>
									<c:forEach items="${provinces}" var="province">
									<option value="${ct.cityId}">${ct.cityName}</option>
									</c:forEach>
									</select>
				&nbsp&nbsp面值:  <input type='text' id='faceValue' name='faceValue' value=''  class="ipt w80" onkeyup="value=value.replace(/[^\d]/g,'')"/>
									&nbsp	至  &nbsp 
									<input type='text' id='faceValue2' name='faceValue2' value=''  class="ipt w80" onkeyup="value=value.replace(/[^\d]/g,'')"/>
									
					 <input type='button' value='查询' class="button"  onclick="getDownMerchantProductList();" />
				<br/>
				&nbsp&nbsp<input type="checkbox" name="to_all" id="to_all">全选</input>
				&nbsp&nbsp
				<input type="checkbox" name="inCommon" id='inCommon' value='0'>常用</input> 
				<br/>
				<div id="product_ids"></div></td>
			</tr>
		</table>
		<table class="input">
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="button" type="button" onclick="cloneDownMerchantProduct()" value="提交"/>	
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
