<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<title>批量手工补单</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/ArtDialog/artDialog.js?skin=chrome" ></script>
<SCRIPT type=text/javascript src="${base}/template/admin/js/yuecheng/batchorder.js"></SCRIPT>
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
<script type="text/javascript" language="javascript"> 
 var returnstr = '${returnstr}';
 if(returnstr!=' '){
 	alert(returnstr);
 	returnstr = '';
 }
 </script>
 </head>
<body>
<form id='listForm' method='post' action='batchJobDetailList'>
<div class="line_bar">
			<input id=hasExpired type=hidden name=hasExpired> 
			<input id='page' type=hidden name='page'> 
			 批次号:<input id="batchId" name="batchId" type="text" value="${batchJobDetailVo.batchId!''}" class="ipt w100" />
			 序列号:<input id="serialNum" name="serialNum" type="text" value="${batchJobDetailVo.serialNum!''}" class="ipt w100" />
			
			 订单号:<input id="orderNo" name="orderNo" type="text" value="${batchJobDetailVo.orderNo!''}" class="ipt w100" />
			状态:<select name="status" id="status" class="select"> 
											<option value=""  selected=selected>请选择</option>	
											<option value="0" <#if batchJobDetailVo.status??><#if batchJobDetailVo.status=='0'>selected=selected</#if> </#if>>初始</option>	
											<option value="1"  <#if batchJobDetailVo.status??><#if batchJobDetailVo.status=='1'>selected=selected</#if> </#if>>下单中</option>
											<option value="2" <#if batchJobDetailVo.status??> <#if batchJobDetailVo.status=='2'>selected=selected</#if> </#if>>下单成功</option>
											<option value="3"  <#if batchJobDetailVo.status??><#if batchJobDetailVo.status=='3'>selected=selected</#if> </#if>>下单失败</option>
											<option value="4"  <#if batchJobDetailVo.status??><#if batchJobDetailVo.status=='4'>selected=selected</#if> </#if>>下单存疑</option>
										</select>
								  <@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  </@shiro.user>
</div>
<table id=listtable class=list>
  <tbody>
	  <tr>
	    <th><SPAN>序号</SPAN></th>
	    <th><SPAN>批次号</SPAN></th>
	    <th><SPAN>序列号</SPAN></th>
	    <th><SPAN>手机号码</SPAN> </th>
	     <th><SPAN>面额</SPAN> </th>
	    <th><SPAN>状态</SPAN> </th>
	    <th><SPAN>订单号</SPAN></th>
	   </tr>
  <#if (mlist?size>0)>
    <#list mlist as list>
	  <tr>
		    <td>${(page-1)*pageSize+list_index+1}</td>
		    <td>${(list.batchId)!""}</td>
		    <td>${(list.serialNum)!""}</td>
		    <td>${(list.phoneNum)!""}</td>
		    <td>${(list.faceValue)!""}</td>
		    <#if list.status??>
		    	<#if list.status == 0>
				<td>
				              		初始
		        <#elseif list.status == 1>
				<td>
				              		下单中
		        <#elseif list.status == 2>
				<td>
				              		 下单成功
				<#elseif list.status == 3>
				<td class="tdgb">
				              		 下单失败
				<#elseif list.status ==4>
				<td>
				              		 下单存疑		 
				<#else>
				<td>
						未知
		        </#if>
			<#else>
				<td>
					未知
			</#if>
		    </td>
		    <td>${(list.orderNo)!""}</td>
	   </tr>
</#list>
<#else>
			<tr>
				<td colspan="8">没数据</td>
			</tr>
	</#if>
	</tbody>
</table>
<div class="line_bar">
  <div style="float:left;">
  	显示条数：
  	<select class="select" name="pageSize" id="pageSize" >
		<option value="10" <#if pageSize==10>selected=selected</#if>>10</option>
		<option value="20" <#if pageSize==20>selected=selected</#if>>20</option>
		<option value="30" <#if pageSize==30>selected=selected</#if>>30</option>
		<option value="50" <#if pageSize==50>selected=selected</#if>>50</option>
		<option value="100"<#if pageSize==100>selected=selected</#if>>100</option>	
		<option value="500" <#if pageSize==500>selected=selected</#if>>500</option>
		<option value="1000"<#if pageSize==1000>selected=selected</#if>>1000</option>
	</select>
  </div>
  <div id="pager" style="float:right;"></div>
  <div style="float:right;">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
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
		
		
	function check(){  
      /******  应用名称   ******/  
      var title = document.getElementById("userfile1").value; 
      if(title==''){  
         alert('请选择上传文件~~~！');  
         return false;  
      }  
      if (confirm("确认要批量导入所传补充订单列表吗？")){  
 		return true;  
 		}
      
        
      // 如果用button,则要加上下面一句，执行提交功能。  
      // document.forms['formsave'].submit();  
 }  
</script>
  </form>
  </BODY>
  </HTML>

