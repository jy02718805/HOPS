<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>账户日志列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
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



body {
	font-family:Arial, Helvetica, sans-serif;
	font-size:12px;
	margin:0;
	}
	#main {
	height:100%;
	padding-top:90px;
	text-align:center;
	text-align:center;
	}
	#fullbg {
	left:0;
	opacity:0.5;
	position:absolute;
	top:0;
	z-index:3;
	filter:alpha(opacity=50);
	-moz-opacity:0.5;
	-khtml-opacity:0.5;
	}
	#dialog {
	background-color:#fff;
	border:5px solid rgba(0,0,0, 0.4);
	height:300px;
	left:50%;
	margin:-200px 0 0 -200px;
	padding:1px;
	position:fixed !important; /* 浮动对话框 */
	position:absolute;
	top:300px;
	width:600px;
	z-index:5;
	border-radius:5px;
	display:none;
	}
	#dialog p {
	margin:0 0 12px;
	height:24px;
	line-height:24px;
	background:#CCCCCC;
	}
	#dialog p.close {
	text-align:right;
	padding-right:10px;
	}
	#dialog p.close a {
	color:#fff;
	text-decoration:none;
	}


#divTableInfo{overflow:auto;}


.drag {
	border: 2px solid #000;
	width: 100px;
	height: 100px;
	cursor: move;
	position: absolute;
	left: 0;
	top: 0;
	overflow:scroll;
}
</style>
<body>
<form id="listForm" method="post" action="${ctx}/profitImputation/profitImputationList">
	<input id='page' type='hidden' name='page'/>
	<input id='reProfitImputationId' type='hidden' name='reProfitImputationId' value=''/>
	<input id='profitImputationId' type='hidden' name='profitImputationId' value=''/>
	<input id='profitImputationIds' type='hidden' name='profitImputationIds' value=''/>
	<div class="line_bar">
	<label>时间区间:</label>
	<input id="imputationBeginDate" name="imputationBeginDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${imputationBeginDate}" class='ipt w100'/>
	至
	<input id="imputationEndDate" name="imputationEndDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${imputationEndDate}" class='ipt w100'/>

	<label>商户名称:</label>
	<input type="text" id='merchantName' name="merchantName" value="${merchantName}"  class="ipt"/>
	归集：<select id="imputationStatus" name="imputationStatus" class="select w80">
		<c:choose>
				<c:when test="${imputationStatus==''}">
					<option value="" selected="selected">请选择</option>
				</c:when>
				<c:otherwise>
					<option value="">请选择</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${imputationStatus=='1'}">
					<option value="1" selected="selected" >待归集</option>
				</c:when>
				<c:otherwise>
					<option value="1">待归集</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${imputationStatus=='2'}">
					<option value="2" selected="selected" >已归集</option>
				</c:when>
				<c:otherwise>
					<option value="2">已归集</option>
				</c:otherwise>
			</c:choose>
	</select>
	<div style="float:right;">
	<shiro:user>
	<input type="button" class="button" value="查询" onclick="queryProfit();" class="button"/>
	</shiro:user>
	<shiro:hasPermission name="profitImputation:imputation_show">
	<input type="button" class="button" value="归集" onclick="showBg();" class="button"/>
	</shiro:hasPermission>
	</div>
	</div>
<!-- 	<label>付款账户:</label> -->
<%-- 	<input type="text" id="payerAccountId" name="payerAccountId" value="${transaction.payerAccountId}"/> --%>
<!-- 	&nbsp; -->
<!-- 	<label>收款账户:</label> -->
<%-- 	<input type="text" id="payeeAccountId" name="payeeAccountId" value="${transaction.payeeAccountId}"/> --%>
	&nbsp;
	<table id='listTable' class='list'>
			<tr>
				<th>&nbsp;<input type="checkbox" id="checkProfit" name="checkProfit" ></input> </th>
				<th><a class=sort href="javascript:;" name=identityName>序列</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>交易ID</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>归集时间</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>归集商户</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>账户类型</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>归集利润(元)</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>归集状态</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>更新时间</a> </th>
				<th><a class=sort href="javascript:;" name=identityName>操作</a> </th>
			</tr>
			<tbody id="tb_list">
			<c:choose>
			<c:when test="${fn:length(profitImputationInfos) > 0}">
			<c:forEach items="${profitImputationInfos}" var="profitImputation" varStatus="status">
				<tr>
					<td>
					<c:if test="${profitImputation.imputationStatus=='1'}">
					<input type="checkbox" name="profitBox_${status.count}" onclick="clickbox(this);"></input>
					</c:if>
					
					<input type="hidden"  id="profitImputationId_${status.count}" name="profitImputationId__${status.count}"  value="${profitImputation.profitImputationId}"/>
					<input type="hidden"  id="imputationProfit_${status.count}" name="imputationProfit__${status.count}"  value="${profitImputation.imputationProfit}"/>
					
					</td>
					<td>${(page-1)*pageSize+status.index+1}</td>
					<td><a href="javascript:void(0);" 
		    onclick="clickId('${profitImputation.profitImputationId}',
		    '<fmt:formatDate value="${profitImputation.imputationEndDate}" type="both" dateStyle="medium" pattern="yyyy-MM-dd 00:00:00"/>',
		    '<fmt:formatDate value="${profitImputation.imputationEndDate}" type="both" dateStyle="medium" pattern="yyyy-MM-dd 23:59:59"/>');">
		    ${profitImputation.profitImputationId}</a></td>
					
					 <td>
						<fmt:formatDate value="${profitImputation.imputationBeginDate}" type="date" dateStyle="medium"/>
						</td>
					<td>${profitImputation.merchantName}</td>
					<td>${profitImputation.profitAccountType.accountTypeName}</td>
					<td>${profitImputation.imputationProfit}</td>
					<td>
					<c:choose>
					<c:when test="${profitImputation.imputationStatus=='1'}">
						待归集
					</c:when>
					<c:when test="${profitImputation.imputationStatus=='2'}">
						已归集
					</c:when>
					<c:otherwise>
						未知
					</c:otherwise>
				</c:choose>
					</td>
					<td> 
					<fmt:formatDate value="${profitImputation.imputationEndDate}" type="date" dateStyle="medium"/>
					</td>
					<td>
					<shiro:hasPermission name="profitImputation:reImputation_show">
					<c:if test="${profitImputation.imputationStatus=='1'}">
					<!--<a href="javascript:void(0);" onclick='reImputation(${profitImputation.profitImputationId})'>[重新清算]</a>-->
					</c:if>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="profitImputation:imputation_show">
					<c:if test="${profitImputation.imputationStatus=='1'}">
					<a href="javascript:void(0);" onclick="imputationProfit(${profitImputation.profitImputationId});">[归集]</a>
					</c:if>
					</shiro:hasPermission>
					
					<%-- <a href="${ctx}/profitImputation/profitImputationDetail?profitImputationId=${profitImputation.profitImputationId}">[对比订单详情]</a>--%>
					<shiro:hasPermission name="ProfitImputation:view">
					<a href="${ctx}/profitImputation/profitImputationInfo?profitImputationId=${profitImputation.profitImputationId}">[利润详情]</a>
					</shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>
			</c:when>
			<c:otherwise>
					<tr>
						<td colspan="10">没数据</td>
					</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	<div class="line_bar">
	<div style="float:left;">
  	显示条数：
  	<select name="pageSize" id="pageSize" >
		<option value="10" <c:if test="${pageSize==10}">selected=selected</c:if>>10</option>
		<option value="20"<c:if test="${pageSize==20}">selected=selected</c:if>>20</option>
		<option value="30" <c:if test="${pageSize==30}">selected=selected</c:if>>30</option>
		<option value="50" <c:if test="${pageSize==50}">selected=selected</c:if>>50</option>
		<option value="100"<c:if test="${pageSize==100}">selected=selected</c:if>>100</option>
		<option value="500" <c:if test="${pageSize==500}">selected=selected</c:if>>500</option>
		<option value="1000"<c:if test="${pageSize==1000}">selected=selected</c:if>>1000</option>
	</select>&nbsp; 条
	</div>
	<div id="pager" style="float:right;"></div>  
	<div style="float:right;">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
	</div>
  	<br/>
  	
  	<div id="main">
	<div id="fullbg"></div>
	<div id="dialog" class='drag'>
	<p class="close"><a href="#" onclick="closeBg();">关闭</a></p>
	<div id='divTableInfo'  >
	
	<table class="input">
			<tr>
				<td colspan='2'>
				
				</td>
			</tr>
	
			<tr>
				<th>
					归集利润金额(元):
				</th>
				<td>
				<input type="text" id='profit'  name="profit" value=""  class="ipt" readonly="true"/>
				&nbsp;&nbsp;&nbsp;
				<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="imputationProfits();" class="button"/>&nbsp; 
				 &nbsp; <input type="button" class="button" value="关&nbsp;&nbsp;闭" onclick="closeBg();"  class="button"/>
				</td>
			</tr>

		<tr>
			<td colspan='2'>
			</td>
		</tr>
			<tr>
				<td>
				
				</td>
			<td>
			
			</td>
		</tr>
		</table>
		
		<table id="tableInfo" name='tableInfo' class='list'>
		<thead>
		<th><a class=sort href="javascript:;" name=identityName>归集账户</a> </th>
		<th><a class=sort href="javascript:;" name=identityName>归集时间</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>归集商户</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>归集利润(元)</a> </th>
			<th><a class=sort href="javascript:;" name=identityName>归集状态</a> </th>
		</thead>
		<tbody id='tbInfo'>
		</tbody>
		</table>
	</div>
	</div>
	</div> 
  	
  	
  	
  	
 	<script type="text/javascript" language="javascript">
		$(document).ready(function() { 
				$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
				});
				PageClick = function(pageclickednumber) { 
				$("#pager").pager({ pagenumber: pageclickednumber, pagecount: ${pagetotal}, buttonClickCallback: PageClick 
				}); 
				$("#page").val(pageclickednumber);
				$("#listForm").submit();
			}
			
			function queryProfit(){
				var beginDate=$("#imputationBeginDate").val();
				if(beginDate==""||beginDate==null)
				{
		    		alert("开始时间不能为空！");
		    		document.getElementById("imputationBeginDate").focus(); 
		    		return false;
				}
				var endDate=$("#imputationEndDate").val();
				if(endDate==""||endDate==null)
				{
		    		alert("结束时间不能为空！");
		    		document.getElementById("imputationEndDate").focus(); 
		    		return false;
				}
				$("#listForm").submit();
			}
			
			
			function imputationProfit(profitImputationId) {
				$('#profitImputationId').val(profitImputationId);
			//	$("#listForm").attr("action","${ctx}/profitImputation/imputationProfit").submit();
				$.ajax({  
		            type: "post",  
		            url: "${ctx}/profitImputation/imputationProfit",       
		            data: $("#listForm").serialize(),      
		            success: function(data) {
		            	if(data=='true'){
		                  	alert('操作成功!');
		                  	window.location.reload();
		                 }else{
		                 	alert('操作失败!');
		              }
		            },  
		            error: function(data) {  
		            }  
		        });
			}
			
			
			$(document).ready(function(){
				   $('#checkProfit').click(function(){
						var bl = this.checked;	
						$('input[type="checkbox"]').each(function(){
							this.checked=bl;
							var b=Number($("table input:checkbox").index(this));
						});
				});
				   
			});   
			
			
			function imputationProfits(){
				var profitImputationIds='';
				$(":checkbox:checked",'#tb_list').each(function(){
					 var b=Number($("table input:checkbox").index(this));
					 var id = $("#id_"+b).val();                 
					 var profitImputationId= $("#profitImputationId_"+b).val();
					 profitImputationIds+=profitImputationId+"_";
				});
				$('#profitImputationIds').val(profitImputationIds);
				//$("#listForm").attr("action","${ctx}/profitImputation/profitImputations").submit();
				
				$.ajax({  
		            type: "post",  
		            url: "${ctx}/profitImputation/profitImputations",       
		            data: $("#listForm").serialize(),      
		            success: function(data) {
		            	if(data=='true'){
		                  	alert('操作成功!');
		                  	window.location.reload();
		                 }else{
		                 	alert('操作失败!');
		              }
		            },  
		            error: function(data) {  
		            }  
		        });
			}
			
			function reImputation(imputationId){
//				$('#reProfitImputationId').val(ImputationId);
//				$("#listForm").submit();
				$.ajax({  
		            type: "post",  
		            url: "${ctx}/profitImputation/reImputationProfit?profitImputationId="+imputationId,       
		            contentType:'application/text;charset=UTF-8', 
		            success: function(data) {
		            	if(data=='true'){
		                  	alert('操作成功!');
		                  	window.location.reload();
		                 }else{
		                 	alert('操作失败!');
		              }
		            },  
		            error: function(data) {  
		            }  
		        });
				
				
			}
			
			//${profitImputation.profitImputationId}
			
			
			
	</script> 
	
	
	
	
	
	
	
<script type="text/javascript">
	// 显示灰色 jQuery 遮罩层
	function showBg() {
		if($(':checkbox:checked','#tb_list').is(':checked')) {
		    
		}else{
			 alert("请选择需归集的数据!");
		}
		
		
		
		$("#tbInfo").empty();
// $("#tableInfo tbody").empty();
        var html = ""
        // 找到第一个表中，大于第一行中被选中的 checkbox
        $(":checkbox:checked",'#tb_list').each(function() {
            // 然后找到对应 tr ，变成对应的 html
        	 var b=Number($(":checkbox:checked",'#tb_list').index(this));
            html += "<tr>" + $(this).parent().parent().html() + "</tr>";
        })
        // 最后再把第一行标题行的html加入
// if(html!=''){
// html = "<tr>" + $("#tb_list tr:eq(0)").html() + "</tr>" + html;
// }
        
        // 然后把这些累加起来的 tr append 到第二个表中
        $("#tbInfo").append($(html));
        // 然后找到所有 checkbox ，删除对应的父 td
        $("#tbInfo input:checkbox").each(function() {
        	$(this).parent().parent().find('td').eq(1).remove();
        	$(this).parent().parent().find('td').eq(4).remove();
        	$(this).parent().parent().find('td').eq(6).remove();
        	$(this).parent().parent().find('td').eq(6).remove();
            $(this).parents("td").remove();
        });
        var imputationProfits=parseFloat(0);
        
	    	$(":checkbox:checked",'#tb_list').each(function(){
		     // 行下标;
		     var b=Number($("table input:checkbox").index(this));
		     var id = $("#id_"+b).val();                 
		     var imputationProfit= $("#imputationProfit_"+b).val();
		     imputationProfits+=parseFloat(imputationProfit);
		   });
	    	
	    	
		 $('#profit').val(imputationProfits.toFixed(3));  
					     
		var bh = $("body").height();
		var bw = $("body").width();
		$("#fullbg").css({
			height:bh,
			width:bw,
			display:"block"
		});
			$("#dialog").show();
	}
		// 关闭灰色 jQuery 遮罩
	function closeBg() {
			$("#fullbg,#dialog").hide();
	}
	
	function clickId(transactionId,beginDate,endDate){
		window.location.href="${ctx}/accountHistory/accountTransactionLogList?transactionNo="+transactionId+"&beginDate="+beginDate+"&endDate="+endDate;
	}

//	$(function() {
//		$(".drag").mousedown(function(e) {
//			$(".drag").fadeTo(20, 0.5); // 点击后开始拖动并透明显示
//		});
//		$(document).mouseup(function() {
//			$(".drag").fadeTo("fast", 1); // 松开鼠标后停止移动并恢复成不透明
//		});
//	});
</script>
	
	
</form>
</body>
</html>

