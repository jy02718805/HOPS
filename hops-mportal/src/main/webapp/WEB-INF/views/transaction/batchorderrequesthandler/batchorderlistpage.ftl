<#assign base=request.contextPath>
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="IE=7.0000" http-equiv="X-UA-Compatible">
<title>批量充值</title>
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
<script type="text/javascript">
</script>
<body>
	<div class="order_list">
		<form id="auditdataform" action="batchorderlist" onsubmit="return checkRecharge()" method="post" enctype="multipart/form-data">
			<div class="list_head">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr height="30">
							<td width="200" class="textalign">文件名：</td>
							<td><select class="select" name="upfile" id="upfile"  onchange="getRechargenum(this.value)">
											<option value="" selected=selected>--请选择--</option>	
											<#if upfileList?exists>
												<#list upfileList as list>
												<option value="${list}">${list}</option>
												</#list>
											</#if>
										</select></td>
						</tr>
						<tr height="30">
							<td width="200" class="textalign">下游渠道：</td>
							<td>
								<select class="select" name="merchantid" id="merchantid" >
									<option value="" selected=selected>--请选择--</option>	
									<#if downMerchants?exists>
										<#list downMerchants as Merchant>
										<option value="${Merchant.id}" >${Merchant.merchantName}</option>
										</#list>
									</#if>
								</select>
							</td>
						</tr>
						<tr height="30">
							<td width="200" class="textalign">待充值数据：</td>
							<td><input type="text" class="ipt" id="allnum" name="allnum" class="text" maxlength="200" value="0"  disabled="disabled"/>&nbsp;条</td>
						</tr>
						<tr height="30">
							<td width="200" class="textalign">请选择要充值条数：</td>
							<td>
							<input value="0" class="ipt" id="auditnum" name="auditnum" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />&nbsp;条
							</td>
						</tr>
						<tr height="30">
							<td>
							</td>
							<td>
								<input class="button" id="button1" type="submit" value="批量充值" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</body>
</html>
 

