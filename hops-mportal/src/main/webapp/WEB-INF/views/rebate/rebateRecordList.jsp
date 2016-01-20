<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<title>返佣记录</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<script src="${ctx}/template/admin/js/ArtDialog/artDialog.js?skin=chrome" type="text/javascript"></script>
<script src="${ctx}/template/admin/js/ArtDialog/plugins/iframeTools.js" type="text/javascript"></script>
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
</style>
</head>
<body>
<form id="listForm" method="get" action="${ctx}/rebateRecord/rebateRecordList">
	<input id='page' type='hidden' name='page'> 
	<input id=hasExpired type=hidden name=hasExpired> 
<div class="line_bar">	
	发生商户:<select name="merchantId" id="merchantId" class="select">
			<option value="">请选择</option>
			<c:forEach items="${rebateMerchants}" var="merchant">
				<c:choose>
			       <c:when test="${rebateRecordVo.merchantId==merchant.id}">
			           	  <option selected="selected" value="${merchant.id}">${merchant.merchantName}</option>
			       </c:when>
			       <c:otherwise>
			          	   <option value="${merchant.id}">${merchant.merchantName}</option>
			       </c:otherwise>
				</c:choose>
			</c:forEach>
		 </select>
	返佣商户:<select name="rebateMerchantId" id="rebateMerchantId" class="select">
			<option value="">请选择</option>
			<c:forEach items="${merchants}" var="merchant">
				<c:choose>
			       <c:when test="${rebateRecordVo.rebateMerchantId==merchant.id}">
			           	  <option selected="selected" value="${merchant.id}">${merchant.merchantName}</option>
			       </c:when>
			       <c:otherwise>
			          	  <option value="${merchant.id}">${merchant.merchantName}</option>
			       </c:otherwise>
				</c:choose>
			</c:forEach>
		 </select>
	清算时间:
			<input type="text" name="beginDate" id="beginDate" class="ipt" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${rebateRecordVo.beginDate}" />
                   	至
            <input type="text" name="endDate" id="endDate" class="ipt" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"  value="${rebateRecordVo.endDate}" />
	<br/>返佣状态:<select name="status" id="status" class="select w80">
			<option value="" selected="selected" >请选择</option>
			<c:choose>
			       <c:when test="${rebateRecordVo.status=='1'}">
			           	  <option selected="selected" value="1">未返</option>
			       </c:when>
			       <c:otherwise>
			          	  <option value="1">未返</option>
			       </c:otherwise>
			</c:choose>
			<c:choose>
			       <c:when test="${rebateRecordVo.status=='0'}">
			           	  <option selected="selected" value="0">已返</option>
			       </c:when>
			       <c:otherwise>
						  <option value="0">已返</option>
			       </c:otherwise>
			</c:choose>
		 </select>
		 <shiro:user>
	<input type="button" class="button" value="查询" onclick="rebateRecordList()"/>
	</shiro:user>
	<shiro:hasPermission name="rebateRecord:bulid_show">
	<input type="button" class="button" value="生成数据" onclick="generateData()"/>
	
	<!-- 别删，以后做测试可以打开 -->
	<!-- <input type="button" class="button" value="手动触发生成数据" onclick="generateDatas()"/> -->
	
	</shiro:hasPermission>
</div>
	<table id=listTable class=list>
		<tbody>
		<tr>
		<th>&nbsp;<input type="checkbox" onclick="selectAllRebate(this);" ></input> </th>
			<th><span>序号</span> </th>
			<th><span>发生商户</span> </th>
			<th><span>返佣商户</span> </th>
			<th><span>商户类型</span> </th>
			<th><span>清算日期</span> </th>
			<th><span>返佣方式</span> </th>
			<th><span>返佣产品</span> </th>
			<th><span>交易量</span> </th>
			<th><span>返佣金额</span> </th>
			<th><span>状态</span> </th>
			<th><span>更新人</span> </th>
			<th><span>更新时间</span> </th>
			<th><span>操作</span> </th>
		</tr>
		<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
		<c:forEach items="${mlist}" var="rebateRecord" varStatus="status">
			<tr>
			<td>
				<c:choose>
					<c:when test="${rebateRecord.rebateRecord.status == '1'}">
						<input type="checkbox" name="checkrebate" value="${rebateRecord.rebateRecord.id}"></input>
					</c:when>
				</c:choose>
			</td>
			<td>${(page-1)*pageSize+status.index+1}</td>
				<td>${rebateRecord.merchantName}</td>
				<td>${rebateRecord.rebateMerchantName}</td>
				<td><c:choose>
						<c:when test="${rebateRecord.rebateRecord.merchantType == 'AGENT'}">
							代理商
						</c:when>
						<c:when test="${rebateRecord.rebateRecord.merchantType == 'SUPPLY'}">
							供货商
						</c:when>
						<c:otherwise>
							未知
				        </c:otherwise>
					</c:choose></td>
				<td>
					<fmt:formatDate value="${rebateRecord.rebateRecord.rebateDate}" type="both" dateStyle="medium" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${rebateRecord.rebateRecord.rebateType == '0'}">
							定比返佣
						</c:when>
						<c:when test="${rebateRecord.rebateRecord.rebateType == '1'}">
							定额返佣
						</c:when>
						<c:otherwise>
							未知
				        </c:otherwise>
					</c:choose>
				</td>
				<c:choose>
						<c:when test="${rebateRecord.productNames != ''}">
							<td title="全部：${rebateRecord.productNamesAlt}" style="color:blue;">${rebateRecord.productNames}</td>
						</c:when>
						<c:otherwise>
							<td title="产品未开通或暂无产品"  style="color:blue;">/</td>
				        </c:otherwise>
					</c:choose>
				<td>${rebateRecord.rebateRecord.transactionVolume}</td>
				<!-- 返佣金额 -->
				<td>${rebateRecord.rebateRecord.rebateAmt}</td>
				<input type="hidden" id="oldrebateAmt${rebateRecord.rebateRecord.id}" name="oldrebateAmt${rebateRecord.rebateRecord.id}" value="${rebateRecord.rebateRecord.rebateAmt}"/>
				<c:choose>
						<c:when test="${rebateRecord.rebateRecord.status == '1'}">
						<td class="tdgb">
						未返
						</c:when>
						<c:when test="${rebateRecord.rebateRecord.status == '0'}">
						<td>
						已返
						</c:when>
						<c:otherwise>
						<td>
						未知
				        </c:otherwise>
					</c:choose>
				</td>
				<td>${rebateRecord.rebateRecord.updateUser}</td>
				<td><fmt:formatDate value="${rebateRecord.rebateRecord.updateDate}" type="both" dateStyle="medium"/></td>
				<td>
					<c:choose>
						<c:when test="${rebateRecord.rebateRecord.status == '1'}">
							<shiro:hasPermission name="rebate:rebuild_show">
								<a onclick="rebuildRebateRecord(${rebateRecord.rebateRecord.id})">[重新清算]</a>
							</shiro:hasPermission>
						</c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</c:when>
			<c:otherwise>
					<tr>
						<td colspan="14">没数据</td>
					</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
<div class="line_pages">
  <div style="float:left;">
  	&nbsp; 显示条数：
  	<select name="pageSize" id="pageSize" class="select">
		<option value="10" <c:choose><c:when test="${pageSize==10}">selected=selected</c:when></c:choose>>10</option>
		<option value="20" <c:choose><c:when test="${pageSize==20}">selected=selected</c:when></c:choose>>20</option>
		<option value="30" <c:choose><c:when test="${pageSize==30}">selected=selected</c:when></c:choose>>30</option>
		<option value="50" <c:choose><c:when test="${pageSize==50}">selected=selected</c:when></c:choose>>50</option>
		<option value="100" <c:choose><c:when test="${pageSize==100}">selected=selected</c:when></c:choose>>100</option>
		<option value="500" <c:choose><c:when test="${pageSize==500}">selected=selected</c:when></c:choose>>500</option>
		<option value="1000" <c:choose><c:when test="${pageSize==1000}">selected=selected</c:when></c:choose>>1000</option>	
	</select>&nbsp; 条
  </div>
	<div id="pager" style="float:right;"></div>  
  <div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
  	<br/>
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
			
		function deleteRebateRecord(id){
			window.location.href="${ctx}/rebateRecord/deleteRebateRecord?id="+id;
		}
		
		function doTransaction(id){
			window.location.href="${ctx}/rebateRecord/doTransaction?id="+id;
		}
		
		function rebateRecordList(){
			//查询条件为空不能查询
			
			var mid=$("#merchantId").val();
			var rmid=$("#rebateMerchantId").val();
			var beginDate=$("#beginDate").val();
			var endDate=$("#endDate").val();
			var status=$("#status").val();
			/* if(mid==""||rmid==""||beginDate==""||endDate==""||status==""){
				alert("条件请选择完整！");
		        return false;
			} */
			$("#listForm").submit();
		}
		function generateDatas()
		{
			window.location.href="${ctx}/rebateRecord/generateDatas";
		}
		function generateData(){
			var mid=$("#merchantId").val();
			var rmid=$("#rebateMerchantId").val();
			var beginDate=$("#beginDate").val();
			var endDate=$("#endDate").val();
			var status=$("#status").val();
			if(mid==""||rmid==""||beginDate==""||endDate==""||status==""){
				alert("条件请选择完整，查询结果后再生成。");
		        return false;
			}
			var r=document.getElementsByName("checkrebate"); 
		    var result="";
		    for(var i=0;i<r.length;i++){
		         if(r[i].checked){
		         result=result+r[i].value+"|";
		       }
		    }
		    if (result == "") {
		        alert("请至少选择一条数据");
		        return false;
		    }
		    var url="${ctx}/rebateRecord/generateData?rebateRecordId="+result+"&mid="+mid+"&rmid="+rmid+"&beginDate="+beginDate+"&endDate="+endDate;
		    var html = $.ajax({ url: url, async: false }).responseText;
            var title = "生成 数据信息确认";
            art.dialog({
                content: html,
                title: title,
                lock: true,
                ok: false
            });
		}
		
		function selectAllRebate(obj){
			 $("input[type='checkbox']").attr("checked", obj.checked);
		}
		//
		function rebuildRebateRecord(id){
			if (!confirm("确认要重新清算该数据吗?")) {
		        return false;
		    }
			window.location.href="${ctx}/rebateRecord/rebuildRebateRecord?id="+id;
		}
		
		
	</script> 
</form>
</body>
</html>

