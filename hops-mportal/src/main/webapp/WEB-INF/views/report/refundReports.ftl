<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>每日利润统计</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/template/common/js/DatePicker/WdatePicker.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
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
	<form id='listForm' method='post' action="${base}/report/refundReports">
		<input id='page' type=hidden name='page'> 
		<div class="line_bar"> 
			开始时间:<input id="beginTime" name="beginTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${refundReportVo.beginTime!""}" class='ipt w80'/>
			结束时间:<input id="endTime" name="endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${refundReportVo.endTime!""}" class='ipt w80'/>
			报表类型:<select  id='reportType' name='reportType' onchange="submitReportType();" class="select w80">
			<option value="">请选择</option>
				<#list reportTypeList as rt>
					<option value="${rt.reportTypeId!""}"  <#if rt.reportTypeId+""==(refundReportVo.reportType!)>selected='selected'</#if>>${rt.reportFileName}</option>
				</#list>
			</select>
			<div style="float:right;">
					 <@shiro.user>
				<input type="button" class="button" onclick="queryProfitList();"  value="查询" class="button"/>
					</@shiro.user>
				 	<@shiro.hasPermission name="profitReport:export_show">
				<input type="button" class="button" onclick="exportProfitReport();"  value="导出Excel" class="button"/>
					</@shiro.hasPermission>
					</div>
			</div>
			
			<div class="line_bar"> 
			<#list reportTermList as reprtterm>
			<#if reprtterm.reportMetadata.metadataField=="PROVINCE_NAME">
			 省份:<select name="province" id="province" onchange="getCityByProvince(this)" class="select w80">
						<option value="">请选择</option>
						<#list provinces as pn>
								<option value="${pn.provinceId}"  <#if pn.provinceId==(refundReportVo.province!)>selected='selected'</#if>>${pn.provinceName}</option>
						</#list>
					 </select>
			</#if>
			</#list>
			
			<#list reportTermList as reprtterm>		 
			<#if reprtterm.reportMetadata.metadataField=="CITY">
			城市:
			<select name="city" id="city" style="width:80px" class="select w80">
						<option value="">请选择</option>
						<#list citys as ct>
								<option value="${ct.cityId!""}" <#if (ct.cityId==(refundReportVo.city!))>selected='selected'</#if>>${ct.cityName!""}</option>
						</#list>
					 </select>
			</#if>
			</#list>
			
			<#list reportTermList as reprtterm>
			<#if reprtterm.reportMetadata.metadataField=="CARRIER_NAME">
			运营商:
			<select name="carrierNo" id="carrierNo" class="select w80">
						<option value="">请选择</option>
						<#list carrierInfos as cafo>
								<option value="${cafo.carrierNo}" <#if (cafo.carrierNo==(refundReportVo.carrierNo!))>selected='selected'</#if>>${cafo.carrierName}</option>
						</#list>
					 </select>
			</#if>
			</#list>
			
			<#list reportTermList as reprtterm>
			<#if reprtterm.reportMetadata.metadataField=="PAR_VALUE">
			面值:<input type="text" id='parValue' name='parValue'  value="${refundReportVo.parValue!""}" class="ipt w80" />
			</#if>
			</#list>
			
			
			<#list reportTermList as reprtterm>
			<#if reprtterm.reportMetadata.metadataField=="BUSINESS_TYPE">
			业务类型:
				<select name="businessType" id="businessType" class='select w80'>
					<option value="">请选择</option>
					<option value="0" <#if (refundReportVo.businessType!)=='0'>selected="selected"</#if>>话费</option>
					<option value="1" <#if (refundReportVo.businessType!)=='1'>selected="selected"</#if>>流量</option>
				</select>
			</#if>
			</#list>
			
			
			<#list reportTermList as reprtterm>	
			<#if reprtterm.reportMetadata.metadataField=="MERCHANT_NAME">
			商户名称:<input type="text"  id='merchantName' name='merchantName'  value="${refundReportVo.merchantName!""}" class="ipt w150"/>
			商户类型:
			<select  id='merchantType' name='merchantType' class="select w80">
			<option value="">请选择</option>
				<option value="SUPPLY"  <#if (refundReportVo.merchantType!)=="SUPPLY">selected='selected'</#if>>供货商</option>
				<option value="AGENT"  <#if (refundReportVo.merchantType!)=="AGENT">selected='selected'</#if>>代理商</option>
			</select>
			</#if>
			</#list>
			
			<#list reportTermList as reprtterm>	
			<#if reprtterm.reportMetadata.metadataField=="REFUND_ORDER_NO">
			<br/>
			退款订单号:<input id="refundOrderNo" name="refundOrderNo" type="text" value="${refundReportVo.refundOrderNo!""}" class="ipt w80" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			</#if>
			</#list>
			
				</div>
	<table  id='listtable' class='list'>
	<#if (refundReportVo.reportType!)=="">
	<thead>
		<tr>
			<th>统计时间</th>
			<th>商户名称</th>
			<th>成功面值</th>
			<th>销售金额</th>
			<th>成本金额</th>
			<th>利润</th>
		</tr>
		</thead>
		<tbody>
		<tr>
		<td colspan="6">没数据</td>
		</tr>
		</tbody>
	<#else>
	${refunds!""}
	</#if>
	</table>
	 <div class="line_pages">
		<div style="float:left;">
	  	显示条数：
	  	<select name="pageSize" id="pageSize" >
			<option value="10" <#if pageSize==10>selected=selected</#if>>10</option>
			<option value="20" <#if pageSize==20>selected=selected</#if>>20</option>
			<option value="30" <#if pageSize==30>selected=selected</#if>>30</option>
			<option value="50" <#if pageSize==50>selected=selected</#if>>50</option>
			<option value="100"<#if pageSize==100>selected=selected</#if>>100</option>	
			<option value="500" <#if pageSize==500>selected=selected</#if>>500</option>
			<option value="1000"<#if pageSize==1000>selected=selected</#if>>1000</option>
		</select>&nbsp; 条
	  </div>
	  <div id="pager" style="float:right;"></div>
	  <div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div> 
  </div>
	 <script type="text/javascript" language="javascript"> 
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
			var obj = $("#province");
			getCityByProvince(obj);
		}); 
		PageClick = function(pageclickednumber) { 
			  $("#pager").pager({ 
			       pagenumber: pageclickednumber,
				   pagecount: ${pagetotal}, 
				   buttonClickCallback: PageClick 
			});
			
			$("#page").val(pageclickednumber);
		   $("#listForm").attr("action","${base}/report/refundReports").submit();
		}
		
		
		
		
		
		function queryProfitList(){
			var beginDate=$("#beginTime").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginTime").focus(); 
	    		return false;
			}
			var endDate=$("#endTime").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endTime").focus(); 
	    		return false;
			}
		 	$("#listForm").attr("action","${base}/report/refundReports").submit();
		}
		function exportProfitReport(){
			var beginDate=$("#beginTime").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginTime").focus(); 
	    		return false;
			}
			var endDate=$("#endTime").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endTime").focus(); 
	    		return false;
			}
			var reportType = $("#reportType").val();
			if(reportType.length <= 0){
				alert("请选择 报表类型！");
				return;
			}
			$("#listForm").attr("action","${base}/report/refundExport").submit();
		}
		function submitReportType(){
			 $("#listForm").attr("action","${base}/report/refundReports").submit();
		}
		
		function getCityByProvince(obj){
			var provinceId = $(obj).val();
		    if(provinceId != -1){
				$.ajax({
						url:"${base}/reportTool/getCityByProvince?provinceId="+provinceId, 
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
		
		</script>
		</form>
</body>

</HTML>
