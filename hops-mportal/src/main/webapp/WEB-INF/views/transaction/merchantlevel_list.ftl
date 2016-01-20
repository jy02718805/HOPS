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
<SCRIPT type=text/javascript>
function editMerchantLevel(type){
	if(type==0){
		$("#low").attr("style","display:none");
		$("#high").attr("style","display:none");
		$("#orderPercentagelow").attr("style","display:block");
		$("#orderPercentageHigh").attr("style","display:block");
		$("#save").attr("style","display:block");
		$("#nosave").attr("style","display:none");
	}else if(type==1){
		$("#low").attr("style","display:block");
		$("#high").attr("style","display:block");
		$("#orderPercentagelow").attr("style","display:none");
		$("#orderPercentageHigh").attr("style","display:none");
		$("#save").attr("style","display:none");
		$("#nosave").attr("style","display:block");
	}else{
		var orderPercentagelow = $("#orderPercentagelow").val();
		var orderPercentageHigh = $("#orderPercentageHigh").val();
		
		
		if(orderPercentagelow==''){
			alert("优质产品充值比不能为空!");
			return;
		}
		
		if(orderPercentageHigh==''){
			alert("中等产品充值比不能为空!");
			return;
		}
		
		if(orderPercentagelow <= 0 || orderPercentagelow >=100){
			alert("优质产品充值比不能小于0或者大于100 !");
			return;
		}
		if(orderPercentageHigh <= 0 || orderPercentageHigh >= 100){
			alert("中等产品充值比不能小于0或者大于100 !");
			return;
		}
		if(Number(orderPercentagelow) >= Number(orderPercentageHigh)){
			alert("优质产品充值比需要小于中等产品充值比 !");
			return;
		}
		$("#listForm").submit();
	}
}
</SCRIPT>
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
<div class="mg10"></div>
<FORM id='listForm' method='post' action='edit'>
		<INPUT id=page type=hidden name=page> 
		<INPUT id=hasExpired type=hidden name=hasExpired> 
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
    <TH><SPAN>充值比</SPAN> </TH>
    <TH><SPAN>优质产品充值比</SPAN> </TH>
    <TH><SPAN>中等产品充值比</SPAN> </TH>
    <TH><SPAN>普通产品充值比</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH>
  </TR>
    <TR>
    	<TD>
    		0
    		<input type="hidden" id="id" name="id" class="text" maxlength="100" value="${(list.id)!""}"/>
    		<input type="hidden" id="merchantLevel" name="merchantLevel" class="text" maxlength="100" value="${(list.merchantLevel)!""}"/>
    	</TD>
    	<TD>
    		<div id="low" style="display:block" >${(list.orderPercentagelow)!""}</div>
    		<input type="text" style="display:none"  id="orderPercentagelow" name="orderPercentagelow" class="text" maxlength="100" value="${(list.orderPercentagelow)!""}"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
                                    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
    	</TD>
    	<TD>
	    	<div id="high" style="display:block" >${(list.orderPercentageHigh)!""}</div>
	    	<input type="text" style="display:none" id="orderPercentageHigh" name="orderPercentageHigh" class="text" maxlength="100" value="${(list.orderPercentageHigh)!""}"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
                                    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
    	</TD>
    	<TD>100</TD>
    	<TD> 
			<div id="nosave" style="display:block" >
				<@shiro.hasPermission name="merchantLevel:edit_show">
					<A href="javascript:;" onclick='editMerchantLevel(0)'>[编辑]</A>
				</@shiro.hasPermission>
			</div>					
			<div id="save" style="display:none" >
					<A href="javascript:;" onclick='editMerchantLevel(1)'>[取消]</A>
				<@shiro.hasPermission name="merchantLevel:add_show">
					<A href="javascript:;" onclick='editMerchantLevel(2)'>[保存]</A>
				</@shiro.hasPermission>
			</div> 
		</TD> 	
    </TR>
</TBODY></TABLE>
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
</script> 
 </FORM></BODY></HTML>

