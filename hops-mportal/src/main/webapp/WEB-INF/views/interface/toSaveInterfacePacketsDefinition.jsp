﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>

<head>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<LINK rel=stylesheet type=text/css
	href="${ctx}/template/admin/css/common.css">
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<SCRIPT type=text/javascript
	src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript
	src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
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
	        merchantRequestParam = merchantRequestParam.substring(0, merchantRequestParam.length-1);
	        merchantRequestParam = merchantRequestParam + "}";
	        requestMerchantRequestParams = requestMerchantRequestParams + merchantRequestParam + ",";
		}
		if(requestMerchantRequestParams.length > 1 ){
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
	       	var connectionModule = "request";
	       	interfaceParam = interfaceParam + "'connectionModule':"+connectionModule+",";
	       	
	       	interfaceParam = interfaceParam.substring(0, interfaceParam.length-1);
	       	interfaceParam = interfaceParam + "}";
	       	requestInterfaceParams = requestInterfaceParams + interfaceParam + ",";
	       	
	       	

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
	var outParamNameInput_bl=false;
	var outParamNameInput_str='';
	function doSaveInterfacePacketsDefinition(){
		buildRequestInterfaceParams();
		buildResponseInterfaceParams();
		buildMerchantRequest();
		buildMerchantResponse();
		if(outParamNameInput_bl=='true'){
			alert('提交失败[参数名称:'+outParamNameInput_str+'必须与常量key相同]');
			outParamNameInput_str='';
			return;
		}
// 		alert($("#requestInterfaceParamsStr").val());
// 		alert($("#responseInterfaceParamsStr").val());
// 		alert($("#requestMerchantRequestParams").val());
// 		alert($("#responseMerchantResponseParams").val());
// 		alert($("#interfaceDefinitionId").val());
		var interfaceType = $("#interfaceType_select").val();
		$("#interfaceType").val(interfaceType);
	    $('#inputForm').submit();
	}
	
	function judgeSide(){
		var side = $("#side").val();
		var inOrOut = $("#inOrOut").val();// in out '''
		if(side == "request"){
			showRequest();
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
			showResponse();
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
	
	function judgeSideByInterfaceType(){
		var interfaceType = "${interfaceType}";
		if(interfaceType == "send_order"){
			showRequest();
			$("#inOrOut").val("out");
			judgeSide();
		}else if(interfaceType == "agent_notify_order"){
			showRequest();
			$("#inOrOut").val("out");
			judgeSide();
		}
		else if(interfaceType == "agent_notify_TBorder"){
			showRequest();
			$("#inOrOut").val("out");
			judgeSide();
		}else if(interfaceType == "query_order"){
			showRequest();
			$("#inOrOut").val("out");
			judgeSide();
		}else if(interfaceType == "supply_notify_order"){
			showRequest();
			$("#inOrOut").val("in");
			$("#side").val("request");
			judgeSide();
		}else if(interfaceType == "supply_notify_order_success"){
			showResponse();
			$("#inOrOut").val("out");
			$("#side").val("response");
			$("#side").attr("disabled","true");
			$("#merchantRequest_div").hide();
			$("#merchantResponse_div").hide();
		}else if(interfaceType == "supply_notify_order_fail"){
			showResponse();
			$("#inOrOut").val("out");
			$("#side").val("response");
			$("#side").attr("disabled","true");
			$("#merchantRequest_div").hide();
			$("#merchantResponse_div").hide();
		}else if(interfaceType == "agent_notify_order_flow"){
			showRequest();
			$("#inOrOut").val("out");
			judgeSide();
		}else if(interfaceType == "supply_notify_order_flow"){
			showRequest();
			$("#inOrOut").val("in");
			$("#side").val("request");
			judgeSide();
		}else if(interfaceType == "supply_query_order_flow"){
			showRequest();
			$("#inOrOut").val("out");
			judgeSide();
		}else if(interfaceType == "supply_send_order_flow"){
			showRequest();
			$("#inOrOut").val("out");
			judgeSide();
		}
	}
	
	function showRequest(){
		$("#request").show();
		$("#requestInterfaceParams_div").show();
		$("#requestPacketType_tr").show();
		$("#response").hide();
		$("#responseInterfaceParams_div").hide();
		$("#responsePacketType_tr").hide();
	}
	
	function showResponse(){
		$("#request").hide();
		$("#requestInterfaceParams_div").hide();
		$("#requestPacketType_tr").hide();
		$("#response").show();
		$("#responseInterfaceParams_div").show();
		$("#responsePacketType_tr").show();
	}
	

	//-----------------------------------------------------------页面接口变量动态配置JS--------------------------------------------------------------------------
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
	//----------------------------------------------------------------------------------------------------------------------------------
	
	
	</script>
</head>

<body onload="judgeSideByInterfaceType()">
<div class="mg10"></div>
	<form id="inputForm"
		action="${ctx}/interface/doSaveInterfacePacketsDefinition"
		method="post" class="form-horizontal">
		<input type="hidden" id="requestInterfaceParamsStr" name="requestInterfaceParamsStr" /> 
		<input type="hidden" id="responseInterfaceParamsStr" name="responseInterfaceParamsStr" />
		<input type="hidden" id="requestMerchantRequestParams" name="requestMerchantRequestParams" /> 
		<input type="hidden" id="responseMerchantResponseParams" name="responseMerchantResponseParams" /> 
		<input type="hidden" id="isConf" name="isConf" value="${isConf}" /> 
		<input type="hidden" id="interfaceType" name="interfaceType" /> 
		<input type="hidden" id="merchantId" name="merchantId" value="${merchantId}" />
		<table id="interfacePacketsDefinition" class="input">
			<tr>
				<th><span class="requiredField">*</span>接口类型:</th>
				<td><select name="interfaceType_select" id="interfaceType_select" disabled="disabled" class="select">
						<c:choose>
							<c:when test="${interfaceType==''}">
								<option value="" selected="selected">请选择</option>
							</c:when>
							<c:otherwise>
								<option value="">请选择</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfaceType=='send_order'}">
								<option value="send_order" selected="selected">发送订单</option>
							</c:when>
							<c:otherwise>
								<option value="send_order">发送订单</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfaceType=='agent_notify_order'}">
								<option value="agent_notify_order" selected="selected">通知代理商</option>
							</c:when>
							<c:otherwise>
								<option value="agent_notify_order">通知代理商</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfaceType=='agent_notify_TBorder'}">
								<option value="agent_notify_TBorder" selected="selected">通知淘宝代理商</option>
							</c:when>
							<c:otherwise>
								<option value="agent_notify_TBorder">通知淘宝代理商</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfaceType=='query_order'}">
								<option value="query_order" selected="selected">查询订单</option>
							</c:when>
							<c:otherwise>
								<option value="query_order">查询订单</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${interfaceType=='supply_notify_order'}">
								<option value="supply_notify_order" selected="selected">供货商通知订单</option>
							</c:when>
							<c:otherwise>
								<option value="supply_notify_order">供货商通知订单</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
		 					<c:when test="${interfaceType=='supply_send_order_flow'}">
				             	<option value="supply_send_order_flow" selected="selected">供货商流量下单</option>
					        </c:when>
					        <c:otherwise>
								<option value="supply_send_order_flow">供货商流量下单</option>
					        </c:otherwise>
				        </c:choose>
						<c:choose>
		 					<c:when test="${interfaceType=='supply_query_order_flow'}">
				             	<option value="supply_query_order_flow" selected="selected">供货商流量查询</option>
					        </c:when>
					        <c:otherwise>
								<option value="supply_query_order_flow">供货商流量查询</option>
					        </c:otherwise>
				        </c:choose>
						<c:choose>
		 					<c:when test="${interfaceType=='supply_notify_order_flow'}">
				             	<option value="supply_notify_order_flow" selected="selected">供货商流量通知</option>
					        </c:when>
					        <c:otherwise>
								<option value="supply_notify_order_flow">供货商流量通知</option>
					        </c:otherwise>
				        </c:choose>
						<c:choose>
		 					<c:when test="${interfaceType=='agent_notify_order_flow'}">
				             	<option value="agent_notify_order_flow" selected="selected">代理商流量通知</option>
					        </c:when>
					        <c:otherwise>
								<option value="agent_notify_order_flow">代理商流量通知</option>
					        </c:otherwise>
				        </c:choose>
				</select></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>接入接出标示:</th>
				<td><select name="inOrOut" id="inOrOut" onchange="judgeSide()" class="select">
						<option value="">请选择</option>
						<option value="in">接入</option>
						<option value="out">接出</option>
				</select></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>接口编码:</th>
				<td><select name="encoding" id="encoding" class="select">
						<option value="">请选择</option>
						<option value="gbk">gbk</option>
						<option value="utf-8">utf-8</option>
						<option value="gb2312">gb2312</option>
				</select></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>连接方式:</th>
				<td><select name="connectionType" id="connectionType" class="select">
						<option value="">请选择</option>
						<option value="http">http</option>
						<option value="https">https</option>
						<option value="socket">socket</option>
						<option value="webservice">webservice</option>
						<option value="ssh_socket">ssh_socket</option>
				</select></td>
			</tr>
			
			<tr>
				<th><span class="requiredField"></span>请求方式:</th>
				<td><select name="methodType" id="methodType" class="select">
						<option value="">请选择</option>
						<option value="post">post</option>
						<option value="get">get</option>
						
				</select></td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>请求地址(url):</th>
				<td>
					<input type="text" name="requestUrl" id="requestUrl" size="50" class="ipt"/>
				</td>
			</tr>
			<tr>
				<th><span class="requiredField">*</span>选择request/response:</th>
				<td><select name="side" id="side" onclick="judgeSide()" class="select" >
						<option value="request" selected="selected">request</option>
						<option value="response">response</option>
				</select></td>
			</tr>
			<!-- request packet_type -->
			<tr id="requestPacketType_tr">
				<th><span class="requiredField">*</span>request 报文格式:</th>
				<td><select name="requestPacketType" id="requestPacketType" class="select">
						<option value="text/xml" selected="selected">text/xml</option>
						<option value="text/plain">text/plain</option>
						<option value="application/json">application/json</option>
						<option value="application/x-www-form-urlencoded">application/x-www-form-urlencoded</option>
				</select></td>
			</tr>
			<!-- response packet_type -->
			<tr id="responsePacketType_tr" >
				<th><span class="requiredField">*</span>response 报文格式:</th>
				<td><select name="responsePacketType" id="responsePacketType" class="select">
						<option value="text/xml" selected="selected">text/xml</option>
						<option value="text/plain">text/plain</option>
						<option value="application/json">application/json</option>
				</select></td>
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
			<div id="requestInterfaceParams_div" >
			<div class="bar">
			<div style="float:right;">
				<input type="button" class="button" value="添加接口参数"
					onclick="addRequestInterfaceParam()" />
			</div>
			</div>
				<table id="requestInterfaceParams" class=list>
					<tbody>
						<tr>
							<th><SPAN>request配置信息</SPAN>
							</th>
							<th><SPAN>操作</SPAN>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- response配置 -->
		<div id="response" >
			<div id="responseInterfaceParams_div">
		<div class="bar">
			<div style="float:right;">
				<input type="button" class="button" value="添加接口参数"
					onclick="addResponseInterfaceParam()" />
			</div>
			</div>
				<table id="responseInterfaceParams" class=list>
					<tbody>
						<tr>
							<th><SPAN>response配置信息</SPAN>
							</th>
							<th><SPAN>操作</SPAN>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div id="merchantRequest_div">
		<div class="bar">
			<div style="float:right;">
			<input type="button" class="button" value="添加发送配置"
				onclick="addMerchantRequest()" />
			</div>
			</div>
			<table id="merchantRequest" class=list>
				<tbody>
					<tr>
						<th><a class=sort href="javascript:;" name=sequence>最低时间差（秒）</SPAN>
						</th>
						<th><SPAN>最高时间差（秒）</SPAN>
						</th>
						<th><SPAN>时间间隔量</SPAN>
						</th>
						<th><SPAN>时间间隔单位</SPAN>
						</th>
						<th><SPAN>操作</SPAN>
						</th>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="merchantResponse_div">
		<div class="bar">
			<div style="float:right;">
			<input type="button" class="button" value="添加返回码配置"
				onclick="addMerchantResponse()" />
			</div>
			</div>
			<table id="merchantResponse" class=list>
				<tbody>
					<tr>
						<th><SPAN>商户服务结果码</SPAN>
						</th>
						<th><SPAN>商户订单状态</SPAN>
						</th>
						<th><SPAN>商户返回码描述</SPAN>
						</th>
						<th><SPAN>系统状态</SPAN>
						</th>
						<th><SPAN>操作</SPAN>
						</th>
					</tr>
				</tbody>
			</table>
		</div>
		<table class="input">
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input id="submit_btn" class="btn btn-primary"
					type="button" onclick="doSaveInterfacePacketsDefinition()"
					value="提交" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="返回" onclick="history.back()" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
