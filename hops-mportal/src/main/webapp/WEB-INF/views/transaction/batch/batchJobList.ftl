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
<form id='listForm' method='post' action='batchJoblist'>
<div class="line_bar">
			<input id='hasExpired' type='hidden' name='hasExpired'> 
			<input id='page' type=hidden name='page'> 
								  文件名:<input id="fileName" name="fileName" type="text" value="${batchJob.fileName!''}" class="ipt w100" />
								状态:<select name="status" id="status" class="select">
											<option value=""  selected=selected>请选择</option>	
											<option value="0" <#if batchJob.status??><#if batchJob.status=='0'>selected=selected</#if> </#if>>待审核</option>	
											<option value="1"  <#if batchJob.status??><#if batchJob.status=='1'>selected=selected</#if> </#if>>已审核</option>
											<option value="2" <#if batchJob.status??> <#if batchJob.status=='2'>selected=selected</#if> </#if>>已启动</option>
											<option value="3"  <#if batchJob.status??><#if batchJob.status=='3'>selected=selected</#if> </#if>>已暂停</option>
											<option value="4"  <#if batchJob.status??><#if batchJob.status=='4'>selected=selected</#if> </#if>>已完成</option>
											<option value="5"  <#if batchJob.status??><#if batchJob.status=='6'>selected=selected</#if> </#if>>已废除</option>
										</select>
								  <@shiro.user>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  </@shiro.user>
								   <@shiro.hasPermission name="batchOrder:importBatch_show">
								  <input type="button" class="button" value="批量导入" onclick="uploadBacthFile();" />
								  </@shiro.hasPermission>
</div>
<table id='listtable' class='list'>
  <tbody>
	  <tr>
	    <th><SPAN>序号</SPAN></th>
	    <th><SPAN>批次号</SPAN> </th>
	    <th><SPAN>批充文件名</SPAN> </th>
	    <th><SPAN>批充号码总数</SPAN> </th>
	    <th><SPAN>批充总金额</SPAN></th>
	    <th><SPAN>批次状态</SPAN></th>
	    <th><SPAN>待下单笔数</SPAN></th>
	    <th><SPAN>处理中笔数</SPAN></th>
	    <th><SPAN>创建时间</SPAN></th>
	    <th><SPAN>审核时间</SPAN></th>
	    <th><SPAN>启动时间</SPAN></th>
	    <th><SPAN>暂停时间</SPAN></th>
	    <th><SPAN>完成时间</SPAN></th>
	     <th><SPAN>操作</SPAN></th>
	   </tr>
  <#if (mlist?size>0)>
    <#list mlist as list>
	  <tr>
		    <td>${(page-1)*pageSize+list_index+1}</td>
		    <td>
		    <a href="${base}/Batch/batchJobDetailList?batchId=${list.batchId!}">${list.batchId!}</a>
		    </td>
		    <td>${(list.fileName)!""}</td>
		    <td>${(list.totalNum)!""}</td>
		    <td>${(list.totalAmt)!""}</td>
		    <#if list.status??>
		    	<#if list.status == 0>
				<td>
				              		待审核 
		        <#elseif list.status == 1>
				<td>
				              		已审核
		        <#elseif list.status == 2>
				<td>
				              		 已启动
				              		 
				<#elseif list.status == 3>
				<td>
				              		已暂停
				<#elseif list.status == 5>
				<td class="tdgb">
				              		已废弃
				<#elseif list.status == 4>
				<td>
				              		已完成
				<#else>
				<td>
						未知
		        </#if>
			<#else>
				<td>
					未知
			</#if>
		    </td>
		    <td>${(list.waitHandleNum)!""}</td>
		    <td>${(list.processedNum)!""}</td>
		    <td><#if list.createdTime??>
		   	  ${list.createdTime?string("yyyy-MM-dd HH:mm:ss")}
			</#if>
			</td>
		   	<td>
		   	<#if list.auditTime??>
		   	  ${list.auditTime?string("yyyy-MM-dd HH:mm:ss")}
			</#if>
		   	</td>
		    <td>
		    <#if list.startedTime??>
		   	  ${list.startedTime?string("yyyy-MM-dd HH:mm:ss")}
			</#if>
		    </td>
		    <td>
		    <#if list.pausedTime??>
		   	  ${list.pausedTime?string("yyyy-MM-dd HH:mm:ss")}
			</#if>
		    </td>
		    <td> 
		    <#if list.finishedTime??>
		   	  ${list.finishedTime?string("yyyy-MM-dd HH:mm:ss")}
			</#if>
		    </td>
		    <td> 
		    <#if list.status==0>
		    <@shiro.hasPermission name="batchOrder:auditBatchJob_show">
				[ <a href="javascript:void(0);" onclick="auditSs(${list.batchId!})">审核通过</a>|
				 <a href="javascript:void(0);" onclick="auditFf(${list.batchId!})">审核不通过</a>]
				
				</@shiro.hasPermission>
				 </#if>
				  <@shiro.hasPermission name="batchOrder:startBatch_show">
				<#if list.status==1 || list.status==3>
				<a href="javascript:void(0);" onclick="startup(${list.batchId!})">[启动]</a>
				</#if>
				</@shiro.hasPermission>
				 <@shiro.hasPermission name="batchOrder:pasuseBatchJob_show">
				<#if list.status==2>
				<a href="javascript:void(0);" onclick="shutDown(${list.batchId!})">[暂停]</a>
				</#if>
				</@shiro.hasPermission>
								</td>
	   </tr>
</#list>
<#else>
			<tr>
				<td colspan="14">没数据</td>
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
	
	var identityId=$('#identityId').val();
      if(identityId==''){  
         alert('请选择商户！');  
         return false;  
      }  
      /******  应用名称   ******/  
      var title = document.getElementById("userfile1").value; 
      if(title==''){  
         alert('请选择上传文件~~~！');  
         return false;  
      }  
      
        
      // 如果用button,则要加上下面一句，执行提交功能。  
      // document.forms['formsave'].submit();  
 }  
 
 
 
 
 // 批量导入
function uploadBacthFile() {
	var html = $.ajax({
		url : "uploadBacthFileInfo",
		async : false
	}).responseText;
	art.dialog({
		content : html,
		title : '批量导入订单信息',
		lock : true,
		ok : false
	});
}
 
 
 // 审核数据
function auditBatchJob() {
	var html = $.ajax({
		type : 'POST',
		url : "auditdatapage",
		data : '',
		dataType : 'text',
		async : false
	}).responseText;
	art.dialog({
		content : html,
		title : '审核数据',
		lock : true,
		ok : false
	});
}
 
 
 function auditSs(batchId)
  {
		    var msg="确认审核通过?";
            var status=1;
		    if(confirm(msg)){
		        $.ajax({
	                 url:'auditBatchJob',
	                 type: 'GET',
	                 dataType:'text',
	                 data: "batchId="+batchId+"&status="+status, 
	                 contentType:'application/text;charset=UTF-8', 
	                 success: function(result) {
	                       if(result==1){
	                        alert('操作成功!');
	                        window.location.reload();
	                      }else{
	                        alert('操作失败!');
	                   }
	                }
	               });
		    }
		    
        }
        
    function auditFf(batchId)
        {
		    var  msg="确认审核不通过?";
		    var status=5;
		    if(confirm(msg)){
		        $.ajax({
	                 url:'auditBatchJob',
	                 type: 'GET',
	                 dataType:'text',
	                 data: "batchId="+batchId+"&status="+status, 
	                 contentType:'application/text;charset=UTF-8', 
	                 success: function(result) {
	                       if(result==1){
	                        alert('操作成功!');
	                        window.location.reload();
	                      }else{
	                        alert('操作失败!');
	                   }
	                }
	               });
		    }
		    
        }
        function startup(batchId)
        {
		    var msg="确认启动?";
			var status=2;
		    if(confirm(msg)){
		        $.ajax({
	                 url:'startBatchJob',
	                 type: 'GET',
	                 dataType:'text',
	                 data: "batchId="+batchId+"&status="+status, 
	                 contentType:'application/text;charset=UTF-8', 
	                 success: function(result) {
	                       if(result==1){
	                        alert('操作成功!');
	                        window.location.reload();
	                      }else if(result==3){
	                        alert('已有正在进行的任务！');
	                      }else{
	                        alert('操作失败!');
	                      }
	                }
	               });
		    }
		    
        }
        
        
        function shutDown(batchId)
        {
		    var msg="确认暂停?";
		    var status=3;
		    if(confirm(msg)){
		        $.ajax({
	                 url:'pasuseBatchJob',
	                 type: 'GET',
	                 dataType:'text',
	                 data: "batchId="+batchId+"&status="+status, 
	                 contentType:'application/text;charset=UTF-8', 
	                 success: function(result) {
	                       if(result==1){
	                        alert('操作成功!');
	                        window.location.reload();
	                      }else{
	                        alert('操作失败!');
	                   }
	                }
	               });
		    }
		    
        }
 
</script>
  </form>
  </BODY>
  </HTML>

