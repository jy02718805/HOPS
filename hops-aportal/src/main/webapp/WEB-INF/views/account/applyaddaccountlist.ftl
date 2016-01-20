<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
<TITLE>加款申请</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script language="javascript">
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
<DIV class="path">
		<A href="/admin/common/index.jhtml">首页</A> » 账户管理     » 加款申请 列表<SPAN>(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</SPAN>
</DIV>
<FORM id="listForm" method="post" action="${base}/account/applyaddaccountlist">
		<INPUT id="page" type="hidden" name="page"/> 
		<INPUT id="hasExpired" type="hidden" name="hasExpired"/> 
		<DIV class="bar">
				<DIV class="buttonWrap">
								审核状态:<select name="auditstatus" id="auditstatus" >
											<option value="0" >待审核</option>	
											<option value="1" >已审核</option>	
										</select>
								  <input type="submit" class="button" value="查&nbsp;&nbsp;询" />
								  <input type="button" class="button" value="加款申请" onclick="doAddAccount()"/>
		 			</DIV>
		</DIV>
<TABLE id="listTable" class="list">
  <TBODY>
  <TR>
    <TH><A class="sort" href="javascript:;" name="member">账户ID</A> </TH>
    <TH><A class="sort" href="javascript:;" name="member">加款申请金额</A> </TH>
    <TH><A class="sort" href="javascript:;" name="consignee">币种</A> </TH>
    <TH><A class="sort" href="javascript:;" name="member">申请状态</A> </TH>
    <TH><A class="sort" href="javascript:;" name="member">审核状态</A> </TH>
    <TH><A class="sort" href="javascript:;" name="consignee">备注</A> </TH>
    <TH><A class="sort" href="javascript:;" name="consignee">操作</A> </TH>
    <#list accountlist as account>
				<tr>
					<td>${(account.accountId)!""}</td>
				   	<td>${account.availableBalance?string("0.0000")}</td>
					<td>人民币</td><!--// 币种-->
					<td>
						<#if account.status =='1'>
							 有效
						<#elseif account.status =='-1'>
							 无效
						</#if>
					</td>
					<td>
						<#if account.auditstatus =='0'>
							 待审核
						<#elseif account.auditstatus =='1'>
							 已审核
						</#if>
					</td>
					<td>${(account.rmk)!""}</td>
					
					<td>
						<#if account.auditstatus =='0'>
							<a href="${base}/account/editaccount?id=${account.id}" >[修改]</a>
						</#if>
						<#if account.auditstatus =='0'>
							<a href="${base}/account/deleteaccount?id=${account.id}" onclick="return dodelete();">[删除]</a>
						</#if>
					<td>
				</tr>
			</#list>
</TBODY></TABLE>
  <div id="pager" style="float:right;"></div> <BR>
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
		
		//申请加款页面
		function doAddAccount() {
 		 	$("#listForm").attr("action","${base}/account/doaddaccount").submit();
		}
		
		//确认删除
		function dodelete(){
		    return confirm("确认删除吗？");
		}
</script> 
</FORM></BODY></HTML>

