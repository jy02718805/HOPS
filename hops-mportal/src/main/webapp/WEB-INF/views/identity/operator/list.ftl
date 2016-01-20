<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>操作员列表</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
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
<div class=path>
		<a href="">首页</a> »操作员列表 <span>(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</SPAN>
</div>
<form id=listForm method=get action=list>
	    <input id=page type=hidden name=page> 
		<div class=bar>
				<div class=buttonWrap>
								
								   类型：
								 <select name = "ownerIdentityType" id = "type" onchange="changeOperatorType(this.value)">
								        
									    <option value="${IdentityType.SP}"
									     <#if ownerIdentityType !="" && ownerIdentityType ==IdentityType.SP >
									     	 selected
									     </#if>
									    >系统操作员</option>
									    <option value="${IdentityType.MERCHANT}" 
									     <#if ownerIdentityType =="" || ownerIdentityType ==IdentityType.MERCHANT >
									     	 selected
									     </#if>
									    >商户操作员</option>
								 
								 </select>
								  &nbsp; &nbsp; &nbsp;
								 <span id= "ownerIdentityIdfiled">
								  所属商户:
								     <select name="ownerIdentityId" id = "ownerIdentityId">
								        <option value=""></option>
									     <#list merchantlist  as merchant>
									      		<option value="${merchant.id}"
									      		 <#if ownerIdentityType ==IdentityType.MERCHANT>
									     	 		<#if ownerIdentityId == merchant.id>
									     	 		   selected
									     	 		</#if>
									          	</#if>
									      		>${(merchant.merchantName)!""}</option>
									      </#list>
									</select>
									</span>
								  &nbsp; &nbsp; &nbsp;
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								    &nbsp; &nbsp; 
								  <input type="button" class="button" onclick="self.location.href='addMerchantOperator';"  value="新增商户操作员" />
		 						   &nbsp; &nbsp; 
								  <input type="button" class="button" onclick="self.location.href='addSystemOperator';"  value="新增系统操作员" />
		 			</div>
		</div>
<table id=listtable class=list>
  <tbody>
	  <tr>
	  
	      <th><a class=sort href="javascript:;" name=sn>ID</a> </th>
		    <th><SPAN>所属商户</SPAN> </th>
		    <th><SPAN>类型</SPAN> </th>
		    <th><SPAN>用户名</SPAN> </th>
			<th><SPAN>姓名</SPAN> </th>
		    <th><SPAN>状态</SPAN> </th>
		    <th><SPAN>操作</SPAN></th>
		    
	   </tr>
	
    <#list mlist as list>
	  <tr>
		    <td>${list_index+1}</td>
		    <td>${list.ownerIdentityId}</td>
			<td>
		    <#switch list.operatorType>
		          <#case "SP_OPERATOR">
		              系统操作员
		             <#break>
		           <#case "MERCHANT_ADMIN">
		              商户操作员
		             <#break>
		        
		           <#default>
		             
		    </#switch>
		    </td>
		     <td>${(list.operatorName)!""}</td>
		    <td>${(list.displayName)!""}</td>
		   
		    <td>
		       
				    <#if list.identityStatus??>
				      <#if list.identityStatus.status =='1'>
				             禁用
				         <#elseif list.identityStatus.status =='0'>
				             启用
				        </#if>
				    </#if>
		    </td>
		 
		    <td>
		    <#if list.identityStatus??>
				          <#if list.identityStatus.status =='1'>
				               <a  onclick="enable_function('${list.id}')">[启用]</a> 
				         <#elseif list.identityStatus.status =='0'>
				               <a   onclick="disable_function('${list.id}')">[禁用]</a> 
				        </#if>
				    </#if>
		    
		       <a href="edit?oid=${list.id}">[编辑]</a>
		       <a href="toModifyPassword?id=${list.id}">修改用户密码</a>
		       <A href="tosetrole?oid=${list.id}">[设置角色]</A>
				
		     </td>
	   </tr>
</#list>
	</tbody>
</table>
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
  <div id="pager" style="float:right;"></div> <BR>
  <script type="text/javascript" language="javascript"> 
		$(document).ready(function() { 
			<#if ownerIdentityType !="" && ownerIdentityType ==IdentityType.SP >
				$("#ownerIdentityIdfiled").hide();
			</#if>   
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
		
		//启用操作员
		var enable_function = function(operatorId){
		                                 		var cfg =     {
												        url:'enable',
												        type: 'GET',
                                                        data: "id="+operatorId+"&t="+ new Date().getTime(),
                                                         dataType: 'json',
                                                         contentType:'application/json;charset=UTF-8', 
                                                         success: function(result) {
                                                                    alert(result.msg);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
   	      //禁用操作员                         
   	      var disable_function = function(operatorId){
   	      	                            	var cfg = {
												        url:'disable',
												        type: 'GET',
                                                        data: 'id='+operatorId+"&t="+ new Date().getTime(), 
                                                        dataType: 'json',
                                                         contentType:'application/json;charset=UTF-8', 
                                                         success: function(result) {
                                                                       alert(result.msg);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
   	         var reset_function = function(operatorId){
   	              
   	      	                            	var cfg = {
												        url:'reset',
												        type: 'GET',
                                                        data: 'id='+operatorId+"&t="+ new Date().getTime(), 
                                                        dataType: 'json',
                                                         contentType:'application/json;charset=UTF-8', 
                                                         success: function(result) {
                                                                       alert(result.msg);
                                                                     if(result.flage == 'true'){
                                                                        window.location.reload();
                                                                     }
                                                          }
                                                 };
                                                $.ajax(cfg);
   	                                }
   	    function changeOperatorType(operatortype)
   	    {
   	       if(operatortype == "${IdentityType.SP}" ){
   	          $("#ownerIdentityIdfiled").hide();
   	          
   	       }else if(operatortype == "${IdentityType.MERCHANT}"){
   	       	   $("#ownerIdentityIdfiled").show();
   	       }
   	    }
</script> 
  </div>
  </form>
  </body>
  </html>

