<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>批量手工补单</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>
<script type=text/javascript src="${base}/template/admin/js/common.js"></script>
<script type=text/javascript src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/ArtDialog/artDialog.js?skin=chrome" ></script>
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
</head>
<body>
	<div class="order_list">
		<form id="uploadform" action="uploadFile" onsubmit="return check()" method="post" enctype="multipart/form-data">
			<input id='displayName' type=hidden name='displayName' value=${loginUser.displayName}> 
			<div class="list_head">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr height="30">
							<td width="200" class="textalign">请选择要导入的补充订单列表文件：</td>
							<td>
								<input type="file" id="userfile1" name="userfile1" size="15" style="width: 260px; height: 20px;" /> 
								<@shiro.hasPermission name="supplyOrder:upload_show">
									<input class="button" id="button1" type="submit" value="上传并导入数据" />
								</@shiro.hasPermission>
							<td>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript" language="javascript">  
 var returnstr = ${returnstr};
 if(returnstr!=''){
 	alert(returnstr);
 	returnstr = '';
 }
 
 function uploadfile() {
 	if (confirm("确认要批量导入所传补充订单列表吗？")){  
 		 $('uploadform').action='uploadFile';
 		 $('uploadform').submit();
 		}
 }
 
 function check(){  
  		 
      /******  应用名称   ******/  
      var title = document.getElementById("file1").value; 
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
</body>
</html>
 

