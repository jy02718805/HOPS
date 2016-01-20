﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
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
	<title>接口定义</title>
	
	<script>
	function addRequestInterfaceParam(){
		var _len = $("#requestInterfaceParams tr").length;
		var inOrOut = $("#inOrOut").val();
		if(_len > 1){
			var tr=$("#requestInterfaceParams tr:last");
			var sequence =tr.find("INPUT[id='sequence']").val();
			_len = parseInt(sequence)+parseInt(1);
		}
		$.ajax({
			url:"${ctx}/interface/addInterfaceParam?id="+_len+"&type=request"+"&inOrOut="+inOrOut, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#requestInterfaceParams").append(data);
			}
		});
	}
	
	function addResponseInterfaceParam(){
		var _len = $("#responseInterfaceParams tr").length;
		var inOrOut = $("#inOrOut").val();
		if(_len > 1){
			var tr=$("#responseInterfaceParams tr:last");
			var sequence =tr.find("INPUT[id='sequence']").val();
			_len = parseInt(sequence)+parseInt(1);
		}
		$.ajax({
			url:"${ctx}/interface/addInterfaceParam?id="+_len+"&type=response"+"&inOrOut="+inOrOut, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#responseInterfaceParams").append(data);
			}
		});
	}
	
	function addMerchantRequest(){
		var _len = $("#merchantRequest tr").length;
		$.ajax({
			url:"${ctx}/interface/addMerchantRequest?id="+_len, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#merchantRequest").append(data);
			}
		});
	}
	
	function addMerchantResponse(){
		var _len = $("#merchantResponse tr").length;
		var interfaceType = $("#interfaceType_select").val();
		if(interfaceType.length <= 0){
			alert("请选择接口类型，再设置商户返回码配置！");
			return;
		}
		$.ajax({
			url:"${ctx}/interface/addMerchantResponse?id="+_len+"&interfaceType="+interfaceType, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#merchantResponse").append(data);
			}
		});
	}
	
	function deleteRequestInterfaceParams(index){
		outParamNameInput_bl='false';
		outParamNameInput_str='';
		$("#requestInterfaceParams tr[id='"+index+"']").remove();
	}
	
	function deleteMerchantRequest(index){
		$("#merchantRequest tr[id='"+index+"']").remove();
	}
	
	function deleteResponseInterfaceParams(index){
		outParamNameInput_bl='false';
		outParamNameInput_str='';
		$("#responseInterfaceParams tr[id='"+index+"']").remove();
	}
	
	function deleteMerchantResponse(index){
		$("#merchantResponse tr[id='"+index+"']").remove();
	}
	
	function buildMerchantRequest(){
		var tbl=$("#merchantRequest");
		var trlist=tbl.find("tr");
		var requestMerchantRequestParams = "";
		requestMerchantRequestParams = requestMerchantRequestParams + "[";
		for(var i=1;i<trlist.length;i++){
			var tr=$(trlist[i]);
	        var merchantRequestParam = "{";
	        var merchantId = ${merchantId};
	        var interfaceType = $("#interfaceType_select").val();
	        merchantRequestParam = merchantRequestParam + "'merchantId':"+merchantId+",";
	        merchantRequestParam = merchantRequestParam + "'interfaceType':"+interfaceType+",";
	        
	        var timeDifferenceLow =tr.find("INPUT[id='timeDifferenceLow']").val();
	        if(timeDifferenceLow != undefined && timeDifferenceLow.length > 0){
	        	merchantRequestParam = merchantRequestParam + "'timeDifferenceLow':"+timeDifferenceLow+",";
	        }
	        var timeDifferenceHigh =tr.find("INPUT[id='timeDifferenceHigh']").val();
	        if(timeDifferenceHigh != undefined && timeDifferenceHigh.length > 0){
	        	merchantRequestParam = merchantRequestParam + "'timeDifferenceHigh':"+timeDifferenceHigh+",";
	        }
	        var intervalTime =tr.find("INPUT[id='intervalTime']").val();
	        if(intervalTime != undefined && intervalTime.length > 0){
	        	merchantRequestParam = merchantRequestParam + "'intervalTime':"+intervalTime+",";
	        }
	        var intervalUnit =tr.find("select[id='intervalUnit']").val();
	        if(intervalUnit != undefined && intervalUnit.length > 0){
	        	merchantRequestParam = merchantRequestParam + "'intervalUnit':"+intervalUnit+",";
	        }
	        var id =tr.find("INPUT[id='id']").val();
	        if(id != undefined && id.length > 0){
	        	merchantRequestParam = merchantRequestParam + "'id':"+id+",";
	        }
	        merchantRequestParam = merchantRequestParam.substring(0, merchantRequestParam.length-1);
	        merchantRequestParam = merchantRequestParam + "}";
	        requestMerchantRequestParams = requestMerchantRequestParams + merchantRequestParam + ",";
		}
		if(requestMerchantRequestParams.length > 1){
			requestMerchantRequestParams = requestMerchantRequestParams.substring(0, requestMerchantRequestParams.length-1);
			requestMerchantRequestParams = requestMerchantRequestParams + "]";	
		}else{
			requestMerchantRequestParams = "";
		}
	    $("#requestMerchantRequestParams").val(requestMerchantRequestParams);
	}
	
	function buildMerchantResponse(){
		var tbl=$("#merchantResponse");
		var trlist=tbl.find("tr");
		var responseMerchantResponseParams = "";
		responseMerchantResponseParams = responseMerchantResponseParams + "[";
		
		for(var i=1;i<trlist.length;i++){
			var tr=$(trlist[i]);
	        var merchantResponseParam = "{";
	        var merchantId = ${merchantId};
	        var interfaceType = $("#interfaceType_select").val();
	        merchantResponseParam = merchantResponseParam + "'merchantId':"+merchantId+",";
	        merchantResponseParam = merchantResponseParam + "'interfaceType':"+interfaceType+",";
	        
	        var errorCode =tr.find("INPUT[id='errorCode']").val();
	        if(errorCode != undefined && errorCode.length > 0){
	        	merchantResponseParam = merchantResponseParam + "'errorCode':"+errorCode+",";
	        }
	        var merchantStatus =tr.find("INPUT[id='merchantStatus']").val();
	        if(merchantStatus != undefined && merchantStatus.length > 0){
	        	merchantResponseParam = merchantResponseParam + "'merchantStatus':"+merchantStatus+",";
	        }
	        var merchantStatusInfo =tr.find("INPUT[id='merchantStatusInfo']").val();
	        if(merchantStatusInfo != undefined && merchantStatusInfo.length > 0){
	        	merchantResponseParam = merchantResponseParam + "'merchantStatusInfo':"+merchantStatusInfo+",";
	        }
	        var status =tr.find("select[id='status']").val();
	        if(status != undefined && status.length > 0){
	        	merchantResponseParam = merchantResponseParam + "'status':"+status+",";
	        }
	        var id =tr.find("INPUT[id='id']").val();
	        if(id != undefined && id.length > 0){
	        	merchantResponseParam = merchantResponseParam + "'id':"+id+",";
	        }
	        merchantResponseParam = merchantResponseParam.substring(0, merchantResponseParam.length-1);
	        merchantResponseParam = merchantResponseParam + "}";
	        responseMerchantResponseParams = responseMerchantResponseParams + merchantResponseParam + ",";
		}
		if(responseMerchantResponseParams.length > 1){
			responseMerchantResponseParams = responseMerchantResponseParams.substring(0, responseMerchantResponseParams.length-1);
			responseMerchantResponseParams = responseMerchantResponseParams + "]";	
		}else{
			responseMerchantResponseParams = "";
		}
	    $("#responseMerchantResponseParams").val(responseMerchantResponseParams);
	}
	
	function buildRequestInterfaceParams(){
		var tbl=$("#requestInterfaceParams");
	    var trlist=tbl.find("tr");
	    var requestInterfaceParams = "";
	    requestInterfaceParams = requestInterfaceParams + "[";
	    for(var i=1;i<trlist.length;i++){
	    	var tr=$(trlist[i]);
	        var interfaceParam = "{";
	        var sequence =tr.find("INPUT[id='sequence']").val();
	        if(sequence != undefined && sequence.length > 0){
	        	interfaceParam = interfaceParam + "'sequence':"+sequence+",";
	        }
	        var outParamName =tr.find("INPUT[id='outParamName']").val();
	        if(outParamName != undefined && outParamName.length > 0){
	        	interfaceParam = interfaceParam + "'outParamName':"+outParamName+",";
	        }
	        var dataType = tr.find("select[id='dataType']").val();
	       	if(dataType != undefined && dataType.length > 0){
	        	interfaceParam = interfaceParam + "'dataType':"+dataType+",";
	        }
	        var paramType = tr.find("select[id='paramType']").val();
	       	if(paramType != undefined && paramType.length > 0){
	        	interfaceParam = interfaceParam + "'paramType':"+paramType+",";
	        }
	       	
	       	if(paramType=='constant'){
	       		var outParamNameSelect =tr.find("select[id='outParamName']").find("option:selected").text();
		       	var outParamNameInput =tr.find("INPUT[id='outParamName']").val();
		       	outParamNameSelect = outParamNameSelect.substring(0, outParamNameSelect.indexOf('='));
		       	if(outParamNameSelect!=outParamNameInput){
		       		outParamNameInput_str+="("+outParamNameInput+") ";
		       		outParamNameInput_bl='true';
		       	}else{
		       		outParamNameInput_bl='false';
		       	}
	       	}
	       	
	       	
	        var inBody =tr.find("select[id='inBody']").val();
	        if(inBody != undefined && inBody.length > 0){
	        	interfaceParam = interfaceParam + "'inBody':"+inBody+",";
	        }
	        var encryptionFunction =tr.find("select[id='encryptionFunction']").val();
	        if(encryptionFunction != undefined && encryptionFunction.length > 0){
	        	interfaceParam = interfaceParam + "'encryptionFunction':"+encryptionFunction+",";
	        }
	        var encryptionParamNames =tr.find("textarea[id='encryptionParamNames']").val();
	        if(encryptionParamNames != undefined && encryptionParamNames.length > 0){
	        	interfaceParam = interfaceParam + "'encryptionParamNames':"+encryptionParamNames+",";
	        }
	        var inputParamName =tr.find("select[id='inputParamName']").val();
	        if(inputParamName != undefined && inputParamName.length > 0){
	        	interfaceParam = interfaceParam + "'inputParamName':"+inputParamName+",";
	        }
	        var isCapital = tr.find("select[id='isCapital']").val();
	        if(isCapital != undefined && isCapital.length > 0){
	        	interfaceParam = interfaceParam + "'isCapital':"+isCapital+",";
	        }
	        var formatType =  tr.find("INPUT[id='formatType']").val();
	        if(formatType != undefined && formatType.length > 0){
	        	interfaceParam = interfaceParam + "'formatType':\""+formatType+"\",";
	        }
	        
	        var id =tr.find("INPUT[id='id']").val();
	        if(id != undefined && id.length > 0){
	        	interfaceParam = interfaceParam + "'id':"+id+",";
	        }
	        
	        var interfaceDefinitionId = ${interfacePacketsDefinition.id};
	        interfaceParam = interfaceParam + "'interfaceDefinitionId':"+interfaceDefinitionId+",";
	        
	       	var connectionModule = "request";
	       	interfaceParam = interfaceParam + "'connectionModule':"+connectionModule+",";
	       	
	       	interfaceParam = interfaceParam.substring(0, interfaceParam.length-1);
	       	interfaceParam = interfaceParam + "}";
	       	requestInterfaceParams = requestInterfaceParams + interfaceParam + ",";
	    }
	    if(requestInterfaceParams.length > 1){
	    	requestInterfaceParams = requestInterfaceParams.substring(0, requestInterfaceParams.length-1);
		    requestInterfaceParams = requestInterfaceParams + "]";
		}else{
			requestInterfaceParams = "";
		}
	    $("#requestInterfaceParamsStr").val(requestInterfaceParams);
	}
	
	function buildResponseInterfaceParams(){
		var tbl=$("#responseInterfaceParams");
	    var trlist=tbl.find("tr");
	    var responseInterfaceParamsStr = "";
	    responseInterfaceParamsStr = responseInterfaceParamsStr + "[";
	    for(var i=1;i<trlist.length;i++){
	    	var tr=$(trlist[i]);
	        var interfaceParam = "{";
	        var sequence =tr.find("INPUT[id='sequence']").val();
	        if(sequence != undefined && sequence.length > 0){
	        	interfaceParam = interfaceParam + "'sequence':"+sequence+",";
	        }
	        var outParamName =tr.find("INPUT[id='outParamName']").val();
	        if(outParamName != undefined && outParamName.length > 0){
	        	interfaceParam = interfaceParam + "'outParamName':"+outParamName+",";
	        }
	        var dataType = tr.find("select[id='dataType']").val();
	       	if(dataType != undefined && dataType.length > 0){
	        	interfaceParam = interfaceParam + "'dataType':"+dataType+",";
	        }
	        var paramType = tr.find("select[id='paramType']").val();
	       	if(paramType != undefined && paramType.length > 0){
	        	interfaceParam = interfaceParam + "'paramType':"+paramType+",";
	        }
	        var inBody =tr.find("select[id='inBody']").val();
	        if(inBody != undefined && inBody.length > 0){
	        	interfaceParam = interfaceParam + "'inBody':"+inBody+",";
	        }
	        var encryptionFunction =tr.find("select[id='encryptionFunction']").val();
	        if(encryptionFunction != undefined && encryptionFunction.length > 0){
	        	interfaceParam = interfaceParam + "'encryptionFunction':"+encryptionFunction+",";
	        }

	        var encryptionParamNames =tr.find("textarea[id='encryptionParamNames']").val();
	        if(encryptionParamNames != undefined && encryptionParamNames.length > 0){
	        	interfaceParam = interfaceParam + "'encryptionParamNames':"+encryptionParamNames+",";
	        }
	        var inputParamName =tr.find("select[id='inputParamName']").val();
	        if(inputParamName != undefined && inputParamName.length > 0){
	        	interfaceParam = interfaceParam + "'inputParamName':"+inputParamName+",";
	        }
	        if(inputParamName != undefined && inputParamName.length > 0){
	        	interfaceParam = interfaceParam + "'inputParamName':"+inputParamName+",";
	        }
	        var isCapital = tr.find("select[id='isCapital']").val();
	        if(isCapital != undefined && isCapital.length > 0){
	        	interfaceParam = interfaceParam + "'isCapital':"+isCapital+",";
	        }
	        var formatType =  tr.find("INPUT[id='formatType']").val();
	        if(formatType != undefined && formatType.length > 0){
	        	interfaceParam = interfaceParam + "'formatType':\""+formatType+"\",";
	        }
	        var id =tr.find("INPUT[id='id']").val();
	        if(id != undefined && id.length > 0){
	        	interfaceParam = interfaceParam + "'id':"+id+",";
	        }
	        var interfaceDefinitionId = ${interfacePacketsDefinition.id};
	        interfaceParam = interfaceParam + "'interfaceDefinitionId':"+interfaceDefinitionId+",";
	        
	       	var connectionModule = "response";
	       	interfaceParam = interfaceParam + "'connectionModule':"+connectionModule+",";
	       	
	       	interfaceParam = interfaceParam.substring(0, interfaceParam.length-1);
	       	interfaceParam = interfaceParam + "}";
	       	responseInterfaceParamsStr = responseInterfaceParamsStr + interfaceParam + ",";
	    }
	    if(responseInterfaceParamsStr.length > 1){
	    	responseInterfaceParamsStr = responseInterfaceParamsStr.substring(0, responseInterfaceParamsStr.length-1);
		    responseInterfaceParamsStr = responseInterfaceParamsStr + "]";
		}else{
			responseInterfaceParamsStr = "";
		}
	    $("#responseInterfaceParamsStr").val(responseInterfaceParamsStr);
	}
	
	var outParamNameInput_bl='false';
	var outParamNameInput_str='';
	function doEditInterfacePacketsDefinition(){
		buildRequestInterfaceParams();
		buildResponseInterfaceParams();
		buildMerchantRequest();
		buildMerchantResponse();
		if(outParamNameInput_bl=='true'){
			alert('提交失败[参数名称:'+outParamNameInput_str+'必须与常量key相同]');
			outParamNameInput_str='';
			return;
		}
		var interfaceType = $("#interfaceType_select").val();
		$("#interfaceType").val(interfaceType);
// 		alert($("#requestInterfaceParamsStr").val());
// 		alert($("#responseInterfaceParamsStr").val());
// 		alert($("#requestMerchantRequestParams").val());
// 		alert($("#responseMerchantResponseParams").val());
// 		alert($("#interfaceDefinitionId").val());
	    $('#inputForm').submit();
	}
	
	function judgeSide(){
		var side = $("#side").val();
		var inOrOut = $("#inOrOut").val();// in out '''
		if(side == "request"){
			$("#request").show();
			$("#requestInterfaceParams_div").show();
			$("#requestPacketType_tr").show();
			$("#response").hide();
			$("#responseInterfaceParams_div").hide();
			$("#responsePacketType_tr").hide();
			if(inOrOut == "in"){
				$("#merchantRequest_div").hide();
				$("#merchantResponse_div").show();
			}else if(inOrOut == "out"){
				$("#merchantRequest_div").show();
				$("#merchantResponse_div").hide();
			}else{
				$("#merchantRequest_div").hide();
				$("#merchantResponse_div").hide();
			}
		}else if(side == "response"){
			$("#request").hide();
			$("#requestInterfaceParams_div").hide();
			$("#requestPacketType_tr").hide();
			$("#response").show();
			$("#responseInterfaceParams_div").show();
			$("#responsePacketType_tr").show();
			if(inOrOut == "in"){
				$("#merchantRequest_div").show();
				$("#merchantResponse_div").hide();
			}else if(inOrOut == "out"){
				$("#merchantRequest_div").hide();
				$("#merchantResponse_div").show();
			}else{
				$("#merchantRequest_div").hide();
				$("#merchantResponse_div").hide();
			}
		}
	}
	
// 	function getRequestInterfaceParamByParamType(id){
// 		var merchantRequest_div = $("#merchantRequest"+id);
// 		var paramType = $("#requestInterfaceParams tr:eq("+id+") select[id='paramType']").val();
// 		$.ajax({
// 			url:"${ctx}/interface/getInterfaceParamByParamType?paramType="+paramType+"&merchantId="+${merchantId}+"&connectionModule=request",
// 			type: "post",
// 	        data: null,
// 	        async: false,
// 			success:function(data) {
// 				$("#merchantRequest"+id).html(data);
// 			}
// 		});
// 	}
	
// 	function getResponseMerchantResponseByParamType(id){
// 		var merchantRequest_div = $("#merchantResponse"+id);
// 		var paramType = $("#responseInterfaceParams tr:eq("+id+") select[id='paramType']").val();
// 		$.ajax({
// 			url:"${ctx}/interface/getInterfaceParamByParamType?paramType="+paramType+"&merchantId="+${merchantId}+"&connectionModule=response",
// 			type: "post",
// 	        data: null,
// 	        async: false,
// 			success:function(data) {
// 				$("#merchantResponse"+id).html(data);
// 			}
// 		});
// 	}
	
	$(document).ready(function(){ 
		initRequestParams();
		initResponseParam();
		initMerchantRequest();
		initMerchantResponse();
	}); 
	
	function initRequestParams(){
		$.ajax({
			url:"${ctx}/interface/initRequestParam?id="+${interfacePacketsDefinition.id}, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#requestInterfaceParams").html(data);
			}
		});
	}
	
	function initResponseParam(){
		$.ajax({
			url:"${ctx}/interface/initResponseParam?id="+${interfacePacketsDefinition.id}, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#responseInterfaceParams").html(data);
			}
		});
	}
	
	function initMerchantRequest(){
		$.ajax({
			url:"${ctx}/interface/initMerchantRequest?id="+${interfacePacketsDefinition.id}, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#merchantRequest").append(data);
			}
		});
	}
	
	function initMerchantResponse(){
		$.ajax({
			url:"${ctx}/interface/initMerchantResponse?id="+${interfacePacketsDefinition.id}, 
			type: "post",
	        data: null,
	        async: false,
			success:function(data) {
				$("#merchantResponse").append(data);
			}
		});
	}
	
	//-------------------------------------------------------------页面接口变量动态配置JS-----------------------------------------------------------------------------
	function addRequestParamTypeByDataType(id){
		var dataType = $("#requestInterfaceParams tr[id='"+id+"'] select[id='dataType']").val();
		var inOrOut = $("#inOrOut").val();
		if(dataType.length > 0){
			$.ajax({
				url:"${ctx}/interface/addParamTypeByDataType?id="+id+"&dataType="+dataType+"&type=request"+"&inOrOut="+inOrOut, 
				type: "post",
		        data: null,
		        async: false,
				success:function(data) {
					$("#requestParamTypeDiv"+id).html(data);
				}
			});
		}else{
			$("#requestParamTypeDiv"+id).html("");
		}
	}
	
	function addResponseParamTypeByDataType(id){
		var dataType = $("#responseInterfaceParams tr[id='"+id+"'] select[id='dataType']").val();
		var inOrOut = $("#inOrOut").val();
		if(dataType.length > 0){
			$.ajax({
				url:"${ctx}/interface/addParamTypeByDataType?id="+id+"&dataType="+dataType+"&type=response"+"&inOrOut="+inOrOut, 
				type: "post",
		        data: null,
		        async: false,
				success:function(data) {
					$("#responseParamTypeDiv"+id).html(data);
				}
			});
		}else{
			$("#responseParamTypeDiv"+id).html("");
		}
	}
	
	function addRequestInterfaceParamPropertyByTypes(id){
		var paramType = $("#requestInterfaceParams tr[id='"+id+"'] select[id='paramType']").val();
		var dataType = $("#requestInterfaceParams tr[id='"+id+"'] select[id='dataType']").val();
		var inOrOut = $("#inOrOut").val();
		var interfaceType = $("#interfaceType_select").val();
		if(dataType.length > 0 && paramType.length > 0){
			$.ajax({
				url:"${ctx}/interface/addInterfaceParamPropertyByTypes?paramType="+paramType+"&inOrOut="+inOrOut+"&merchantId="+${merchantId}+"&dataType="+dataType+"&id="+id+"&type=request&interfaceType="+interfaceType, 
				type: "post",
		        data: null,
		        async: false,
				success:function(data) {
					$("#requestParams"+id).html(data);
				}
			});
		}else{
			$("#requestParams"+id).html("");
		}
	}
	
	function addResponseInterfaceParamPropertyByTypes(id){
		var paramType = $("#responseInterfaceParams tr[id='"+id+"'] select[id='paramType']").val();
		var dataType = $("#responseInterfaceParams tr[id='"+id+"'] select[id='dataType']").val();
		var inOrOut = $("#inOrOut").val();
		var interfaceType = $("#interfaceType_select").val();
		if(dataType.length > 0 && paramType.length > 0){
			$.ajax({
				url:"${ctx}/interface/addInterfaceParamPropertyByTypes?paramType="+paramType+"&merchantId="+${merchantId}+"&dataType="+dataType+"&id="+id+"&type=response"+"&inOrOut="+inOrOut+"&interfaceType="+interfaceType, 
				type: "post",
		        data: null,
		        async: false,
				success:function(data) {
					$("#responseParams"+id).html(data);
				}
			});
		}else{
			$("#responseParams"+id).html("");
		}
	}
	
	
	function addRequestInterfaceParamPropertyByEncryptionFunction(id){
		var interfaceType = $("#interfaceType_select").val();
		var encryptionFunction = $("#requestInterfaceParams tr[id='"+id+"'] select[id='encryptionFunction']").val();
		var inOrOut = $("#inOrOut").val();
		if(encryptionFunction.length > 0){
			$.ajax({
				url:"${ctx}/interface/addInterfaceParamPropertyByEncryptionFunction?id="+id+"&encryptionFunction="+encryptionFunction+"&interfaceType="+interfaceType+"&type=request"+"&inOrOut="+inOrOut, 
				type: "post",
		        data: null,
		        async: false,
				success:function(data) {
					$("#requestEncryptionFunction"+id).html(data);
				}
			});
		}else{
			$("#requestEncryptionFunction"+id).html("");
		}
	}
	
	function addResponseInterfaceParamPropertyByEncryptionFunction(id){
		var interfaceType = $("#interfaceType_select").val();
		var encryptionFunction = $("#responseInterfaceParams tr[id='"+id+"'] select[id='encryptionFunction']").val();
		var inOrOut = $("#inOrOut").val();
		if(encryptionFunction.length > 0){
			$.ajax({
				url:"${ctx}/interface/addInterfaceParamPropertyByEncryptionFunction?id="+id+"&encryptionFunction="+encryptionFunction+"&interfaceType="+interfaceType+"&type=response"+"&inOrOut="+inOrOut,
				type: "post",
		        data: null,
		        async: false,
				success:function(data) {
					$("#responseEncryptionFunction"+id).html(data);
				}
			});
		}else{
			$("#responseEncryptionFunction"+id).html("");
		}
	}
	//------------------------------------------------------------------------------------------------------------------------------------------
	
	</script>
</head>

<body onload="judgeSide()">
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/interface/doEditInterfacePacketsDefinition" method="post" class="form-horizontal">
		<input type="hidden" id="interfaceDefinitionId" name="interfaceDefinitionId" value="${interfacePacketsDefinition.id}" />
		<input type="hidden" id="requestInterfaceParamsStr" name="requestInterfaceParamsStr" />
		<input type="hidden" id="responseInterfaceParamsStr" name="responseInterfaceParamsStr" />
		<input type="hidden" id="requestMerchantRequestParams" name="requestMerchantRequestParams" />
		<input type="hidden" id="responseMerchantResponseParams" name="responseMerchantResponseParams" />
		<input type="hidden" id="isConf" name="isConf" value="${interfacePacketsDefinition.isConf}" />
		<input type="hidden" id="interfaceType" name="interfaceType" />
		
		<input type="hidden" id="merchantId" name="merchantId" value="${merchantId}"/>
		<table id="interfacePacketsDefinition" class="input">
		    <tr>
				<th><span class="requiredField">*</span>接口类型:</th>
				<td><select name="interfaceType_select" id="interfaceType_select" disabled="disabled" class="select">
						<c:choose>
							<c:when test="${interfacePacketsDefinition.interfaceType==''}">
								<option value="" selected="selected">请选择</option>
							</c:when>
							<c:otherwise>
								<option value="">请选择</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.interfaceType=='send_order'}">
								<option value="send_order" selected="selected">发送订单</option>
							</c:when>
							<c:otherwise>
								<option value="send_order">发送订单</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.interfaceType=='agent_notify_order'}">
								<option value="agent_notify_order" selected="selected">通知代理商</option>
							</c:when>
							<c:otherwise>
								<option value="agent_notify_order">通知代理商</option>
							</c:otherwise>
						</c:choose>
						 <c:choose>
							<c:when test="${interfacePacketsDefinition.interfaceType=='agent_notify_TBorder'}">
								<option value="agent_notify_TBorder" selected="selected">通知淘宝代理商</option>
							</c:when>
							<c:otherwise>
								<option value="agent_notify_TBorder">通知淘宝代理商</option>
							</c:otherwise>
						</c:choose> 
						<c:choose>
							<c:when test="${interfacePacketsDefinition.interfaceType=='query_order'}">
								<option value="query_order" selected="selected">查询订单</option>
							</c:when>
							<c:otherwise>
								<option value="query_order">查询订单</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.interfaceType=='supply_notify_order'}">
								<option value="supply_notify_order" selected="selected">供货商通知订单</option>
							</c:when>
							<c:otherwise>
								<option value="supply_notify_order">供货商通知订单</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
		 					<c:when test="${interfacePacketsDefinition.interfaceType=='supply_send_order_flow'}">
				             	<option value="supply_send_order_flow" selected="selected">供货商流量下单</option>
					        </c:when>
					        <c:otherwise>
								<option value="supply_send_order_flow">供货商流量下单</option>
					        </c:otherwise>
				        </c:choose>
						<c:choose>
		 					<c:when test="${interfacePacketsDefinition.interfaceType=='supply_query_order_flow'}">
				             	<option value="supply_query_order_flow" selected="selected">供货商流量查询</option>
					        </c:when>
					        <c:otherwise>
								<option value="supply_query_order_flow">供货商流量查询</option>
					        </c:otherwise>
				        </c:choose>
						<c:choose>
		 					<c:when test="${interfacePacketsDefinition.interfaceType=='supply_notify_order_flow'}">
				             	<option value="supply_notify_order_flow" selected="selected">供货商流量通知</option>
					        </c:when>
					        <c:otherwise>
								<option value="supply_notify_order_flow">供货商流量通知</option>
					        </c:otherwise>
				        </c:choose>
						<c:choose>
		 					<c:when test="${interfacePacketsDefinition.interfaceType=='agent_notify_order_flow'}">
				             	<option value="agent_notify_order_flow" selected="selected">代理商流量通知</option>
					        </c:when>
					        <c:otherwise>
								<option value="agent_notify_order_flow">代理商流量通知</option>
					        </c:otherwise>
				        </c:choose>
				</select></td>
			</tr>
			<tr>
			<th>
					<span class="requiredField">*</span>接入接出标示:
				</th>
				<td>
					<select name="inOrOut" id="inOrOut" onchange="judgeSide()"  class="select">
						<c:choose>
							<c:when test="${interfacePacketsDefinition.inOrOut==''}">
								<option value="" selected="selected" >请选择</option>
							</c:when>
							<c:otherwise>
								<option value="">请选择</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.inOrOut=='in'}">
								<option value="in" selected="selected" >接入</option>
							</c:when>
							<c:otherwise>
								<option value="in">接入</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.inOrOut=='out'}">
								<option value="out" selected="selected" >接出</option>
							</c:when>
							<c:otherwise>
								<option value="out">接出</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>接口编码:
				</th>
				<td>
					<select name="encoding" id="encoding"  class="select">
						<c:choose>
							<c:when test="${interfacePacketsDefinition.encoding==''}">
								<option value="" selected="selected" >请选择</option>
							</c:when>
							<c:otherwise>
								<option value="">请选择</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.encoding=='gbk'}">
								<option value="gbk" selected="selected" >gbk</option>
							</c:when>
							<c:otherwise>
								<option value="gbk">gbk</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.encoding=='utf-8'}">
								<option value="utf-8" selected="selected" >utf-8</option>
							</c:when>
							<c:otherwise>
								<option value="utf-8">utf-8</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.encoding=='gb2312'}">
								<option value="gb2312" selected="selected" >gb2312</option>
							</c:when>
							<c:otherwise>
								<option value="gb2312">gb2312</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>连接方式:
				</th>
				<td>
					<select name="connectionType" id="connectionType"  class="select">
						<c:choose>
							<c:when test="${interfacePacketsDefinition.connectionType==''}">
								<option value="" selected="selected" >请选择</option>
							</c:when>
							<c:otherwise>
								<option value="">请选择</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.connectionType=='http'}">
								<option value="http" selected="selected" >http</option>
							</c:when>
							<c:otherwise>
								<option value="http">http</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.connectionType=='https'}">
								<option value="https" selected="selected" >https</option>
							</c:when>
							<c:otherwise>
								<option value="https">https</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.connectionType=='socket'}">
								<option value="socket" selected="selected" >socket</option>
							</c:when>
							<c:otherwise>
								<option value="socket">socket</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.connectionType=='webservice'}">
								<option value="webservice" selected="selected" >webservice</option>
							</c:when>
							<c:otherwise>
								<option value="webservice">webservice</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.connectionType=='ssh_socket'}">
								<option value="ssh_socket" selected="selected" >ssh_socket</option>
							</c:when>
							<c:otherwise>
								<option value="ssh_socket">ssh_socket</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>请求方式:</th>
				<td><select name="methodType" id="methodType" class="select">
						<c:choose>
							<c:when test="${interfacePacketsDefinition.methodType==''}">
								<option value="" selected="selected" >请选择</option>
							</c:when>
							<c:otherwise>
								<option value="">请选择</option>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${interfacePacketsDefinition.methodType=='post'}">
								<option value="post" selected="selected" >post</option>
							</c:when>
							<c:otherwise>
								<option value="post">post</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfacePacketsDefinition.methodType=='get'}">
								<option value="get" selected="selected" >get</option>
							</c:when>
							<c:otherwise>
								<option value="get">get</option>
							</c:otherwise>
						</c:choose>
				</select></td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>请求地址(url):
				</th>
				<td>
					<input type="text" name="requestUrl" id="requestUrl" size="50" value="${interfacePacketsDefinition.requestUrl}"  class="ipt"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>选择request/response:
				</th>
				<td>
					<select name="side" id="side" onclick="judgeSide()"  class="select">
						<option value="request" selected="selected">request</option>
						<option value="response">response</option>
					</select>
				</td>
			</tr>
			<!-- request packet_type -->
			<tr id="requestPacketType_tr">
				<th>
					<span class="requiredField">*</span>request 报文格式:
				</th>
				<td>
					<input type="hidden" id="requestInterfacePacketTypeConfId" name="requestInterfacePacketTypeConfId" value="${request_interfacePacketTypeConf.id}" />
					<select name="requestPacketType" id="requestPacketType" class="select">
						<c:choose>
							<c:when test="${request_interfacePacketTypeConf.packetType=='text/xml'}">
								<option value="text/xml" selected="selected">text/xml</option>
							</c:when>
							<c:otherwise>
								<option value="text/xml">text/xml</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${request_interfacePacketTypeConf.packetType=='text/plain'}">
								<option value="text/plain" selected="selected">text/plain</option>
							</c:when>
							<c:otherwise>
								<option value="text/plain">text/plain</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
						<c:when test="${request_interfacePacketTypeConf.packetType=='application/json'}">
							<option value="application/json" selected="selected">application/json</option>
						</c:when>
						<c:otherwise>
							<option value="application/json">application/json</option>
						</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${request_interfacePacketTypeConf.packetType=='application/x-www-form-urlencoded'}">
								<option value="application/x-www-form-urlencoded" selected="selected">application/x-www-form-urlencoded</option>
							</c:when>
							<c:otherwise>
								<option value="application/x-www-form-urlencoded">application/x-www-form-urlencoded</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<!-- response packet_type -->
			<tr id="responsePacketType_tr" style="display:none">
				<th>
					<span class="requiredField">*</span>response 报文格式:
				</th>
				<td>
					<input type="hidden" id="responseInterfacePacketTypeConfId" name="responseInterfacePacketTypeConfId" value="${response_interfacePacketTypeConf.id}" />
					<select name="responsePacketType" id="responsePacketType" class="select">
						<c:choose>
							<c:when test="${response_interfacePacketTypeConf.packetType=='text/xml'}">
								<option value="text/xml" selected="selected">text/xml</option>
							</c:when>
							<c:otherwise>
								<option value="text/xml">text/xml</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${response_interfacePacketTypeConf.packetType=='text/plain'}">
								<option value="text/plain" selected="selected">text/plain</option>
							</c:when>
							<c:otherwise>
								<option value="text/plain">text/plain</option>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
						<c:when test="${response_interfacePacketTypeConf.packetType=='application/json'}">
							<option value="application/json" selected="selected">application/json</option>
						</c:when>
						<c:otherwise>
							<option value="application/json">application/json</option>
						</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
		</table>
<!-- 		<table id="interfaceConstants" class="input"> -->
<%-- 			<c:forEach items="${interfaceConstants}" var="interfaceConstant" varStatus="id"> --%>
<%-- 				${interfaceConstant.key}=${interfaceConstant.value} --%>
<%-- 			    <c:if test="${id.count % 4 == 0}"> --%>
<!-- 					<br/> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 		</table> -->
		<!-- request配置 -->
		<div id="request">
			<div id="requestInterfaceParams_div" style="display:none">
			
		<div class="bar">
			<div style="float:right;">
				<input type="button" class="button" value="添加接口参数" onclick="addRequestInterfaceParam()"/>
			</div>
			</div>
				<table id="requestInterfaceParams" class=list>
					<tbody>
					<tr>
						<th><SPAN>配置信息</SPAN> </th>
						<th><SPAN>操作</SPAN> </th>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<!-- response配置 -->
		<div id="response" style="display:none">
			<div id="responseInterfaceParams_div">
		<div class="bar">
			<div style="float:right;">
				<input type="button" class="button" value="添加接口参数" onclick="addResponseInterfaceParam()"/>
			</div>
			</div>
				<table id="responseInterfaceParams" class=list>
					<tbody>
					<tr>
						<th><SPAN>配置信息</SPAN> </th>
						<th><SPAN>操作</SPAN> </th>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div id="merchantRequest_div">
		<div class="bar">
			<div style="float:right;">
			<input type="button" class="button" value="添加发送配置" onclick="addMerchantRequest()"/>
			</div>
			</div>
			<table id="merchantRequest" class=list>
				<tbody>
				<tr>
					<th><SPAN>最低时间差（秒）</SPAN> </th>
					<th><SPAN>最高时间差（秒）</SPAN> </th>
					<th><SPAN>时间间隔量</SPAN> </th>
					<th><SPAN>时间间隔单位</SPAN> </th>
					<th><SPAN>操作</SPAN> </th>
				</tr>
				</tbody>
			</table>
		</div>
		
		<div id="merchantResponse_div">
		<div class="bar">
			<div style="float:right;">
			<input type="button" class="button" value="添加返回码配置" onclick="addMerchantResponse()"/>
			</div>
			</div>
			<table id="merchantResponse" class=list>
				<tbody>
				<tr>
					<th><SPAN>商户服务结果码</SPAN> </th>
					<th><SPAN>商户订单状态</SPAN> </th>
					<th><SPAN>商户返回码描述</SPAN> </th>
					<th><SPAN>系统状态</SPAN> </th>
					<th><SPAN>操作</SPAN> </th>
				</tr>
				</tbody>
			</table>
		</div>
		
		<table class="input">
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="btn btn-primary" type="button" onclick="doEditInterfacePacketsDefinition()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
