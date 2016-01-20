<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script language="javascript">
function getMerchantType(mtype){
	document.getElementById("merchantId").options.length=0; 
	document.getElementById("merchantId").options.add(new Option('--请选择--',''));
	document.getElementById("reMerchantId").options.length=0; 
	document.getElementById("reMerchantId").options.add(new Option('--请选择--',''));
	if(mtype=="SUPPLY")
	{
		<#if (upMerchant?size>0)>
			var objSelect=document.getElementById("merchantId");
			<#list upMerchant as list>						
					objSelect.options.add(new Option('${list.merchantName}','${list.id}'));
			</#list>
			for (var i = 0; i < objSelect.options.length; i++) {			
				<#if (merchantId)??>
			        if (objSelect.options[i].value == '${merchantId}') {        
			            objSelect.options[i].selected=true;
			            break;
			        } 
				</#if>
   			 }
		</#if>
		<#if (downMerchant?size>0)>
			var objSelect=document.getElementById("reMerchantId");	
			<#list downMerchant as list>									
					objSelect.options.add(new Option('${list.merchantName}','${list.id}'));
			</#list>
			for (var i = 0; i < objSelect.options.length; i++) {			
				<#if (reMerchantId)??>
			        if (objSelect.options[i].value == '${reMerchantId}') {        
			            objSelect.options[i].selected=true;
			            break;
			        } 
				</#if>
   			 }
		</#if>
	}else
	{
		<#if (downMerchant?size>0)>
			var objSelect=document.getElementById("merchantId");
			<#list downMerchant as list>										
					objSelect.options.add(new Option('${list.merchantName}','${list.id}'));
			</#list>
			for (var i = 0; i < objSelect.options.length; i++) {			
				<#if (merchantId)??>
			        if (objSelect.options[i].value == '${merchantId}') {        
			            objSelect.options[i].selected=true;
			            break;
			        } 
				</#if>
   			 }
		</#if>
		<#if (upMerchant?size>0)>
			var objSelect=document.getElementById("reMerchantId");	
			<#list upMerchant as list>									
					objSelect.options.add(new Option('${list.merchantName}','${list.id}'));
			</#list>
			for (var i = 0; i < objSelect.options.length; i++) {			
				<#if (reMerchantId)??>
			        if (objSelect.options[i].value == '${reMerchantId}') {        
			            objSelect.options[i].selected=true;
			            break;
			        } 
				</#if>
   			 }
		</#if>
	} 
}


function getCtiy(provinceid){
    $.ajax({
        type: "post",
        data: null,
        url: "addCity?id="+provinceid,
		async: false,
        success: function (data) {
	        var citylist=data.split("|");
	        document.getElementById("cityId").options.length=0; 
	        document.getElementById("cityId").options.add(new Option('-请选择-','')); 
	        var i=0;
	        while(i<citylist.length-1)
	        {
	        	var city=citylist[i].split("*");
	        	document.getElementById("cityId").options.add(new Option(city[1],city[0])); 
	        	i++;
	        }
        },
        error: function () {
            alert("操作失败，请重试");
        }
    }); 
}
$(function(){
	getCtiy('${provinceNo}');	
	var objSelect=document.getElementById("cityId");
	for (var i = 0; i < objSelect.options.length; i++) {			
		<#if (cityId)??>
	        if (objSelect.options[i].value == '${cityId}') {        
	            objSelect.options[i].selected=true;
	            break;
	        } 
		</#if>
    }
    getMerchantType('${merchantType}');
})

function checkProducts(allCheckbox){
		var bl = allCheckbox.checked;	
		
		$('input[name="assignExclude"]').each(function(){
			this.checked=bl;
		});
	}
	
	function unCheckProducts(cbox){
		var bl = cbox.checked;	
		var flag=true;
		$('input[name="assignExclude"]').each(function(){
			if(this.checked!=bl)
			{
				flag=false;
			}
		});
		if(flag||(!flag&&!bl)){
			document.getElementById('checkproduct').checked=bl;
		}
	}
	
function delAssignExclude(remid){
	var r=document.getElementsByName("assignExclude"); 
    var result="";
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    }
    if(result!=""&&result!=null){
		$.ajax({
	        type: "post",
	        data: null,
	        url: "deleteAssignExcludes?ids="+result,
			async: false,
	        success: function (data) {
		        if(data=="true")
		        {
		                alert('操作成功'); 
		        }else{
		                alert('操作失败');
		        }
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
	}else{
 	   alert("请选择指定排除的商户！");
 	   return false;
    }
    self.location.href='listAssignExclude';
    return false;
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

<BODY>
<FORM id=listForm method=get action=listAssignExclude>
<div class="line_bar">
		<INPUT id=page type=hidden name=page> 
		<INPUT id=hasExpired type=hidden name=hasExpired> 
								 商户类型:<select name="merchantType" id="merchantType" onchange="getMerchantType(this.value)" class="select w80">
											<option value="" selected=selected>--请选择--</option>	
											<option value="SUPPLY" <#if merchantType=="SUPPLY">selected=selected</#if>>供货商</option>
											<option value="AGENT" <#if merchantType=="AGENT">selected=selected</#if>>代理商</option>
										</select>
								商户名称:<select name="merchantId" id="merchantId"  class="select">
											<option value="" selected=selected>--请选择--</option>	
										</select>
								被作用商户:<select name="reMerchantId" id="reMerchantId"  class="select">
											<option value="" selected=selected>--请选择--</option>	
										</select>
								运营商:<select name="carrierNo" id="carrierNo" class="select w80">
											<option value="" selected=selected>--请选择--</option>	
										<#list carrierInfo as list>
											<option value="${list.carrierNo}" <#if carrierNo==list.carrierNo>selected=selected</#if>>
												${(list.carrierName)!""}
											</option>
										</#list>
									</select>
								省份:<select name="provinceNo" id="provinceNo" onchange="getCtiy(this.value)" class="select w80">
											<option value="" selected=selected>--请选择--</option>	
										<#list province as list>
											<option value="${list.provinceId}" <#if provinceNo==list.provinceId>selected=selected</#if>>
												${(list.provinceName)!""}
											</option>
										</#list>
									</select>
								城市:
									<select name="cityId" id="cityId" class="select w80">
									</select>
								类型:	<select name="ruleType" id="ruleType" class="select w80">
											<option value="" selected=selected>--请选择--</option>	
										<option value="1" <#if ruleType=="1">selected=selected</#if>>指定</option>
										<option value="2" <#if ruleType=="2">selected=selected</#if>>排除</option>										
									</select>
								<@shiro.user>	
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								</@shiro.user>
								 <@shiro.hasPermission name="assignExclude:add_show">
								  <input type="button" class="button" onclick="self.location.href='addAssignExclude';"  value="添&nbsp;&nbsp;加(商户)" />
								</@shiro.hasPermission>
								<@shiro.hasPermission name="assignExclude:add_product_show">
								  <input type="button" class="button" onclick="self.location.href='addProductAssignExclude';"  value="添&nbsp;&nbsp;加(产品)" />
								</@shiro.hasPermission>
								<@shiro.hasPermission name="assignExclude:del_product_show">
								  <input type="button" class="button" onclick="delAssignExclude();"  value="批量删除" />
								</@shiro.hasPermission>
				
					 </DIV>
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
  	<TH><input type='checkbox' id='checkproduct' name='checkproduct' onclick='checkProducts(this);'/></TH>
    <TH><SPAN>序号</SPAN> </TH>
    <TH><SPAN>业务编号</SPAN> </TH>
    <TH><SPAN>商户</SPAN> </TH>
    <TH><SPAN>商户类型</SPAN> </TH>
    <TH><SPAN>运营商</SPAN> </TH>
    <TH><SPAN>省份</SPAN> </TH>
    <TH><SPAN>城市</SPAN> </TH>
    <TH><SPAN>产品</SPAN> </TH>
    <TH><SPAN>规则类型</SPAN> </TH>
    <TH><SPAN>被作用商户</SPAN> </TH>
    <TH><SPAN>被作用商户类型</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
   <#if (mlist?size>0)>
    <#list mlist as list>
		  <TR>
		  <td><input type='checkbox' name='assignExclude' value='${(list.id)!""}' onclick='unCheckProducts(this);'/></td>
		  <td>${(page-1)*pageSize+list_index+1}</td>
			    <TD>话费业务</TD>
			    <TD>${(list.merchantName)!""}</TD>
			    <TD><#if list.merchantType == "SUPPLY">供货商<#else>代理商</#if></TD>
			    <TD>${(list.carrierName)!""}</TD>
			    <TD>${(list.provinceName)!""}</TD>
			    <TD>${(list.cityName)!""}</TD>
			    <TD>${(list.productName)!""}</TD>
			    <TD><#if list.ruleType==1>指定<#else>排除</#if></TD>
			    <TD>${(list.objectMerchantName)!""}</TD>
			    <TD><#if list.objectMerchantType == "SUPPLY">供货商<#else>代理商</#if></TD>
			    <TD>
			    <@shiro.hasPermission name="assignExclude:edit_show">
				    <A href="assignExclude_edit?id=${list.id}">[编辑]</A>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="assignExclude:delete">
					<A href="javascript:void(0);" onClick="delData(${list.id})">[删除]</A>
				</@shiro.hasPermission>
				</TD>
		   </TR>
</#list>
<#else>
			<tr>
				<td colspan="12">没数据</td>
			</tr>
	</#if>
</TBODY></TABLE>
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
		}); 
		PageClick = function(pageclickednumber) { 
			  $("#pager").pager({ 
			       pagenumber: pageclickednumber,
				   pagecount: ${pagetotal}, 
				   buttonClickCallback: PageClick 
			});
			
			$("#page").val(pageclickednumber);
		   $("#listForm").submit();
		
		}
		
		function delData(id)
		{
			if (!confirm("确认要删除该数据吗?")) {
		        return false;
		    }
			window.location.href="assignExclude_delete?id="+id;
		}
</script> 
</FORM></BODY></HTML>

