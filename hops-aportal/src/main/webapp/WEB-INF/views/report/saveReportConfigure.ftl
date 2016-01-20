<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>更新参数配置</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<body>
	<form id="inputForm" action="${base}/report/saveReportType"  method="post">
	<div class="mg10"></div>
		<table class="input">
		<tr>
				<th>
					<span class="requiredField">*</span>报表业务类型名称:
				</th>
				<td>
				<input type="hidden" id="reportMetadataTypeName" name="reportMetadataTypeName" value="${reportMetadataTypeName!""}"/>
				<input type="hidden" id="reportMetadata" name="reportMetadata" />
				<input type="hidden" id="resetReportMetadata" name="resetReportMetadata" />
				<input type="hidden" id="reportMetadataType" name="reportMetadataType" value="${reportMetadataType!""}"/>
				<input type="hidden" id="reportTypeId" name="reportTypeId" class="text" value="${rt.reportTypeId!""}"/>
				<input type="hidden" id="reportPropertyStr" name="reportPropertyStr" class="text" value="${reportPropertyStr!""}"/>
					<select  id='reportMetadataType_select' name='reportMetadataType_select' onchange="onchangeReportMetadata()" class="select">
					<option value="">请选择</option>
						<option value="order_reports" 
						<#if reportMetadataType=="order_reports">
							selected
						</#if>
						>订单</option>
						<option value="ccy_account_balance_history_reports" 
						<#if reportMetadataType=="ccy_account_balance_history_reports">
							selected
						</#if>
						>资金变动</option>
					</select>	</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span> 报表文件名称:
				</th>
				<td>
				<input type="text" id="reportFileName" name="reportFileName" class="text" maxlength="200" value="${(rt.reportFileName)!""}" class="ipt"/>	 
				</td>
			</tr>
			
			<tr>
				<th>
					报表条件:
				</th>
				<td>
				<#list rmList as rm>
					<input type="checkbox" id='checkbox_${rm_index}'
					name='checkbox_${rm_index}'
					<#list reportTermList as term>
						<#if rm.reportMetadataId+""==term.reportMetadata.reportMetadataId+"">
							checked="checked"
						</#if>
					</#list>
					value="${rm.reportMetadataId!""}">${rm.metadataFieldName!""}</input>
				</#list>
				
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>报表属性设置:
					<@shiro.hasPermission name="reportType:add_show">
					<input type="button" class="button" onclick="addReprotType()"  value="添加报表属性" />
					</@shiro.hasPermission>
				</th>
				<td>
			<input type="button" id="submitReportType" name="submitReportType" class="button" value="确&nbsp;&nbsp;定" />
			<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="history.back();" />
					</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				</tr>
				<tr>
				<td colspan='2'>
					<table id='rpTable'>
					<tbody id='rptbody'>
						<tr style='width:300xp'>
							<#list rpList as rp>
							<td class='w80'>
								<input type="hidden" id="reportPropertyId_${rp_index}"  class="text" maxlength="200" value="${rp.reportPropertyId!""}"/>
								<select  id='reportPropertyName_${rp_index}' name='reportPropertyName_${rp_index}' class="select w80">
								<#list rmSt as rm>
									<option value='${rm.metadataEntityField!""}' <#if rm.metadataEntityField==rp.reportPropertyFieldName>selected</#if>>${rm.metadataFieldName!""}</option>
								</#list>
								</select>
								<@shiro.hasPermission name="reportType:delete">
								<a onclick="delProperty(${rp.reportPropertyId!""},${rp_index})">[删除]</a>
								</@shiro.hasPermission>
							</td>
							</#list>
						</tr>
						</tbody>
						<tbody id='rptbody'>
					</table>
				</td>
			</tr>
		</table>
		</form>
</body>

	<script>
	function updateProperty(){
		var tbl=$("#rptbody");
	    var trlist=tbl.find("td");
	    var reportTypeId=$("#reportTypeId").val();
	    var reportPropertyStr="[";
	    for(var i=0;i<trlist.length;i++){
	    	reportPropertyStr+="{";
	    	var reportPropertyId=$("#reportPropertyId_"+i).val();
	    	if(reportPropertyId==undefined){
	    		reportPropertyId='';
	    	}else{
	    		reportPropertyStr+="'reportPropertyId':"+reportPropertyId+",";
	    	}
	    	
	    	var reportPropertyFieldstr= new Array();
	    	var reportPropertyFieldName=$("#reportPropertyName_"+i).val();
	    	var temp="";
	    	if(reportPropertyFieldName!=""){
	    		reportPropertyStr+="'reportPropertyFieldName':"+reportPropertyFieldName;
	    	}
	    	
	    	
	    	var reportPropertyName=$("#reportPropertyName_"+i).find('option:selected').text();
	    	if(reportPropertyName!=""){
	    		reportPropertyStr+=","+"'reportPropertyName':"+reportPropertyName;
	    		reportPropertyStr+=","+"'reportPropertyNum':"+i;
	    	}
	    	
	    	
	    	
	   <#--注释内容	var reportPropertyNum=$("#reportPropertyNum_"+i).val();
	    	if(reportPropertyNum!=""){
	    		reportPropertyStr+=","+"'reportPropertyNum':"+reportPropertyNum;
	    	}
	    	-->
	    	if(i==trlist.length-1){
	    		reportPropertyStr+="}";
	    	}else{
	    		reportPropertyStr+="},";
	    	}
	    	
	    }
	    reportPropertyStr+="]";
	    $("#reportPropertyStr").val(reportPropertyStr);
    }
    
		function saveProperty(){
    		var num=$("#rpTable").find("tr").length;
    		num=Number(num)-2;
    		var reportTypeId=$("#reportTypeId").val();
    		var reportPropertyFieldname=$("#reportPropertyName_"+num).val();
    		var reportPropertyId=$("#reportPropertyId_"+num).val();
    		if(reportPropertyId=="" || reportPropertyId==undefined){
    		}else{
    			$("#reportPropertyId").val(reportPropertyId);
    		}
    		var reportPropertyName=$("#reportPropertyName_"+num).find('option:selected').text();
    		$("#reportPropertyType").val(reportPropertyType);
    		$("#reportPropertyName").val(reportPropertyName);
    		$("#reportPropertyFieldname").val(reportPropertyFieldname);
    		
    		
    		if(reportTypeId=="" || reportTypeId==undefined){
    			alert("请先保存报表类型！");
    			return;
    		}else{
		    	//	document.forms[1].submit();
		    		$("#inputForm2").submit();
    		}
    		
    	//	$.ajax({
     //           type: "POST",
      //          contentType: "application/json",
     //          data: {"reportTypeId":reportTypeId,"reportPropertyName":reportPropertyName,"reportPropertyType":reportPropertyType},
     //           dataType: "json",
     //          	url: "toSaveReportProperty",
      //          success: function (data) {
     //          	$(".updateProperty_"+num).show();
       //         }
        //      });
              
    		}


			function addReprotType(){
				var reportMetadataType=$("#reportMetadataType_select").val();
				if(reportMetadataType!=""){
					var num=$("#rpTable").find("td").length;
	    		//	num=Number(num)-1;
	    		//	var num2=num-1;
	    		if(Number(num)>10){
					alert('超出报表属性限制!');
					return;
				}
	    		
	    			$("#rpTable>tbody>tr").append("<td id='rpTd_"+num+"' class='w80'><select id='reportPropertyName_"+num+"' value='' class='select w80'>"
	    						<#list rmSt as rs>
									+"<option value='${rs.metadataEntityField!""}' >${rs.metadataFieldName!""}</option>"
									</#list>
									+"</select>"+
	            		"<a onclick='delPropertyNum("+num+")'>[删除]</a></td>"); 
				}else{
					alert('报表业务类型不能为空');
					return;
				}
    		}

		function delProperty(reportPropertyId,pnum){
			var num=Number(pnum);
	    	if(confirm('是否确定删除？')){
			    	window.location.href="${base}/report/toDelReportProperty?reportPropertyId="+reportPropertyId+"&reportTypeId="+$("#reportTypeId").val();
			                //	$('#rptbody tr:eq('+num+')').remove(); //删除指定行
	      	}
    }

    
    function delPropertyNum(num){
	    if(confirm('是否确定删除？')){
	    	$("#rpTd_"+num).remove();
	    }
    }
    
    $(document).ready(function(){
			//'input[name="aihao"]:
			var reportTypeId=$("#reportTypeId").val();
			if(reportTypeId=="" || reportTypeId==undefined){
    			$("#reportMetadataType_select").attr("disabled",false);
    		}else{
    			$("#reportMetadataType_select").attr("disabled",true);
    		}
			
			 var reportMetadata="";
			 var resetReportMetadata="";
			   $('#submitReportType').click(function(){
				   $(":checkbox").each(function(){
				   var reportMetadataId = $(this).val();
				   	   if (this.checked==true){
				   	   		reportMetadata+=reportMetadataId+"_";
				   	   }else{
				   	   		resetReportMetadata+=reportMetadataId+"_";
				   	   }
				   });
				  	var reportMetadataTypeName=$('#reportMetadataType_select').find('option:selected').text();
				   	var reportMetadataType=$("#reportMetadataType_select").val();
				   	var reportFileName=$("#reportFileName").val();
				   	
				   	 $("reportMetadataType").val(reportMetadataType);
				   	 if(reportMetadataTypeName!=null && reportMetadataTypeName!=""){
				   	 	 $('#reportMetadataTypeName').val(reportMetadataTypeName);
				   	 }
				   	 $('#reportMetadata').val(reportMetadata);
				   	 $('#resetReportMetadata').val(resetReportMetadata);
				  if(reportMetadataType==""){
				  	alert('请选择业务类型！');
				  	return;
				  }
				  
				  if(reportFileName.trim()==""){
				  	alert('报表文件名不能为空！');
				  	return;
				  }
				  
					updateProperty();
					$("#inputForm").submit();
			   });
		});
    
    function onchangeReportMetadata(){
    	var reportMetadataTypeName=$('#reportMetadataType').find('option:selected').text();
    	var reportMetadataType=$("#reportMetadataType_select").val();
    	$("#reportMetadataTypeName").val(reportMetadataTypeName);
    	$("#reportMetadataType").val(reportMetadataType);
    	$("#inputForm").attr("action","${base}/report/showReportType").submit();
    }
    
    function changeNum(obj){
	    if(!/^[0-9]*$/.test(obj.value)){  
	        alert("请输入数字!"); 
	        obj.value="";
	    }  
    }
</script>


</HTML>