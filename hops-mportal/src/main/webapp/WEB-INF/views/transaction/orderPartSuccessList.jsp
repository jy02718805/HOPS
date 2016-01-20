<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head><META content="IE=7.0000" http-equiv="X-UA-Compatible"></head>
<title>部分成功列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/calendar.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/provinceCity.js"></script>
<script type="text/javascript" src="${ctx}/template/common/js/DateUtil.js"></script>
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
<body>
<form id="listForm" method="get" action="${ctx}/transaction/orderPartSuccessList">
	<input id='page' type='hidden' name='page'/>
	
	<div class="line_bar">
	
	订单时间: <select id='changeDate' name='changeDate' onChange='changeSetDate(this);' class="select w80">
	<option value=''>请选择</option>
	<option value='m' <c:if test='${order.changeDate=="m"}'>selected='selected'</c:if>>最近十分钟</option>
	<option value='h' <c:if test='${order.changeDate=="h"}'>selected='selected'</c:if>>最近一小时</option>
	<option value='d' <c:if test='${order.changeDate=="d"}'>selected='selected'</c:if>>今天</option>
	<option value='Y' <c:if test='${order.changeDate=="Y"}'>selected='selected'</c:if>>前一天</option>
	</select>	
	
	开始时间:<input id="beginDate" name="beginDate" type="text" 
		 onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-{%M-3}-%d',maxDate:'#F{$dp.$D(\'endDate\')|| \'%y-%M-%d\'}'});changeDateSelect();"  value="${order.beginDate}" class="ipt w150" />
	结束时间:<input id="endDate" name="endDate" type="text" 
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginDate\') || \'%y-{%M-3}-%d\'}',maxDate:'%y-%M-%d'});changeDateSelect();"  value="${order.endDate}" class="ipt w150"/>
	<br/>
	业务类型: <select id='businessType' name='businessType'  class="select w80"  onChange='changeBusinessType(this);'>
    <option value='' <c:if test='${order.businessType==""}'>selected='selected'</c:if>>请选择</option>
    <option value='0' <c:if test='${order.businessType=="0"}'>selected='selected'</c:if>>话费</option>
    <option value='1' <c:if test='${order.businessType=="1"}'>selected='selected'</c:if>>流量</option>
    <option value='2' <c:if test='${order.businessType=="2"}'>selected='selected'</c:if>>固话</option>
    </select>
	 
	通知状态:<select name="notifyStatus" id="notifyStatus" class="select w80">
			<c:choose>
				<c:when test="${order.notifyStatus==''}">
					<option value="" selected="selected">请选择</option>
				</c:when>
				<c:otherwise>
					<option value="">请选择</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${order.notifyStatus=='1'}">
					<option value="1" selected="selected" >等待通知</option>
				</c:when>
				<c:otherwise>
					<option value="1">等待通知</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${order.notifyStatus=='2'}">
					<option value="2" selected="selected" >正在通知</option>
				</c:when>
				<c:otherwise>
					<option value="2">正在通知</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${order.notifyStatus=='3'}">
					<option value="3" selected="selected" >通知成功</option>
				</c:when>
				<c:otherwise>
					<option value="3">通知成功</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${order.notifyStatus=='4'}">
					<option value="4" selected="selected" >通知失败</option>
				</c:when>
				<c:otherwise>
					<option value="4">通知失败</option>
				</c:otherwise>
			</c:choose>
		 </select>
		 
		 预成功状态:<select name="preSuccessStatus" id="preSuccessStatus" class="select w80">
	 		<c:choose>
				<c:when test="${order.preSuccessStatus==''}">
					<option value="" selected="selected" >请选择</option>
				</c:when>
				<c:otherwise>
					<option value="">请选择</option>
				</c:otherwise>
			</c:choose>
	 		<c:choose>
				<c:when test="${order.preSuccessStatus=='0'}">
					<option value="0" selected="selected" >无需预处理成功 </option>
				</c:when>
				<c:otherwise>
					<option value="0">无需预处理成功 </option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${order.preSuccessStatus=='1'}">
					<option value="1" selected="selected" >待处理为预成功 </option>
				</c:when>
				<c:otherwise>
					<option value="1">待处理为预成功 </option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${order.preSuccessStatus=='2'}">
					<option value="2" selected="selected" >预处理为成功   </option>
				</c:when>
				<c:otherwise>
					<option value="2">预处理为成功   </option>
				</c:otherwise>
			</c:choose>
		 </select>
		 
		 运营商:<select name="carrierInfo" id="carrierInfo" class="select w80">
			<option value="">请选择</option>
			<c:forEach items="${carrierInfos}" var="carrierInfo">
				<c:choose>
					<c:when test="${order.carrierInfo==carrierInfo.carrierNo}">
			             	<option value="${carrierInfo.carrierNo}" selected="selected">${carrierInfo.carrierName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
			        </c:otherwise>
			    </c:choose>
			</c:forEach>
		 </select>
	省份:<select name="province" id="province" class="select w80">
			<option value="">请选择</option>
			<c:forEach items="${provinces}" var="province">
				<c:choose>
					<c:when test="${order.province==province.provinceId}">
						<option value="${province.provinceId}" selected="selected">${province.provinceName}</option>
					</c:when>
					<c:otherwise>
						<option value="${province.provinceId}">${province.provinceName}</option>
			        </c:otherwise>
				</c:choose>
			</c:forEach>
		 </select>
		 
		 <br/>
			代理商商户:<select name="agentMerchant" id="agentMerchant" class="select w100">
					<option value="">请选择</option>
					<c:forEach items="${merchants}" var="merchant">
						<c:choose>
							<c:when test="${order.agentMerchant == merchant.id}">
								<option value="${merchant.id}" selected="selected">${merchant.merchantName}|[${merchant.id}]</option>
							</c:when>
							<c:otherwise>
								<option value="${merchant.id}">${merchant.merchantName}|[${merchant.id}]</option>
					        </c:otherwise>
						</c:choose>
					</c:forEach>
				 </select>
		 面值:<input type="text" id="parValue" name="parValue" size="5" value="${order.parValue}" class="ipt w80"/>
				 电话号码:<input id="usercode" name="usercode" type="text"  maxlength="11" value="${order.usercode}" class="ipt w100" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					
					系统订单号 :<input id="orderNo" name="orderNo" type="text" value="${order.orderNo}" class="ipt w80"/>
					商户订单号 :<input id="merchantOrderNo" name="merchantOrderNo" type="text" value="${order.merchantOrderNo}" class="ipt w80"/>
					
			<shiro:user>
			<input type="button" class="button" value="查询" onclick="queryOrder()"/>
			</shiro:user>
			
			<br/>
			<span><input type='checkbox' id='statisticsOrder' name='statisticsOrder' value='1' <c:if test='${order.statisticsOrder==1}'>checked="checked"</c:if>>统计信息</input>: 
				(总面额: <span class='msg1' style="color: red;" > </span>		成功面额: <span class='msg2' style="color: red;"> </span>		待充面额: <span class='msg3' style="color: red;"> </span>  失败面额:<span class='msg4' style="color: red;"> </span> )</span>
			
 
		</div>
	
	<table id=listTable class=list>
		<tbody>
<!-- 		下单时间	 充值订单号	 商家编号	 运营商	 省份	 充值账号	 金额	 成功	 待充	 订单状态	 通知状态	 完成时间	 操作 -->
		<tr>
			<th><SPAN>序号</SPAN> </th>
			<th><SPAN>下单时间</SPAN> </th>
			<th><SPAN>订单号</SPAN> </th>
			<th><SPAN>商家编号</SPAN> </th>
			<th><SPAN>业务类型</SPAN> </th>
			<th><SPAN>运营商</SPAN> </th>
			<th><SPAN>省份</SPAN> </th>
			<th><SPAN>城市</SPAN> </th>
			<th><SPAN>充值账户</SPAN> </th>
			<th><SPAN>面值(元/M)</SPAN> </th>
			<th><SPAN>面额(元)</SPAN> </th>
			<th><SPAN>成功面值</SPAN> </th>
			<th><SPAN>待充面值</SPAN> </th>
			<th><SPAN>订单状态</SPAN> </th>
			<th><SPAN>通知状态</SPAN> </th>
			<th><SPAN>手工处理标示</SPAN> </th>
			<th><SPAN>完成时间</SPAN> </th>
			<th><SPAN>操作</SPAN> </th>
		</tr>
		<tbody class="listTable">
		
		<c:choose>
			<c:when test="${fn:length(mlist) > 0}">
				<c:forEach items="${mlist}" var="order" varStatus="status">
					<tr>
						<td>
							${(page-1)*pageSize+status.index+1}
						</td>
						<td><fmt:formatDate value="${order.orderRequestTime}" type="both" dateStyle="medium"/></td>
						<td>
							<a href="${ctx}/transaction/showOrderDetail?orderNo=${order.orderNo}">${order.orderNo}</a>
						</td>
						<td>${order.merchantName}</td>
									<td><c:choose>
										<c:when test="${order.businessType==0}">
			             	 话费
			       </c:when>
										<c:when test="${order.businessType==1}">
			             	 流量
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	流量
			       </c:when>
										<c:otherwise>
			              	未知
			       </c:otherwise>
									</c:choose></td>
						<td>
						<span class="carrierInfo_${status.count}">${order.ext1}</span>
						</td>
						<td>
						<span class="p_${status.count}">${order.ext2}</span>
						</td>
						<td>
						<span class="c_${status.count}">${order.ext3}</span>
						</td>
						<td>${order.userCode}</td>
						<td>${order.productFace}
						<c:choose>
			       <c:when test="${order.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${order.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
						</td>
						<td>${order.orderFee}</td>
						<td>
						<c:choose>
						 <c:when test="${order.businessType==0}">
						${order.orderSuccessFee}
						 </c:when>
						<c:otherwise>
							<c:choose>
							 <c:when test="${order.orderStatus==3}">
							${order.productFace}
							 </c:when>
							<c:otherwise>
							0
							</c:otherwise>
							</c:choose>
						</c:otherwise>
						
						 </c:choose>
						 <c:choose>
			       <c:when test="${order.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${order.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
						</td>
						<td>
						<c:choose>
						 <c:when test="${order.orderStatus==4}">
						 0
						 </c:when>
						 <c:otherwise>
						 <c:choose>
						 	  <c:when test="${order.businessType==0}">
						 	   ${order.orderWaitFee}
						 	  </c:when>
						  	  <c:when test="${order.businessType==1 and order.orderStatus ==3}">
			             		0
			     			 </c:when>
			     			 <c:otherwise>
			              	 ${order.productFace}
			   			    </c:otherwise>
						 </c:choose>
						 </c:otherwise>
						</c:choose>
						<c:choose>
			       <c:when test="${order.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${order.businessType==1}">
			             	 M
			       </c:when>
			       <c:when test="${order.businessType==2}">
			             	 元
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
						</td>
							<c:choose>
							       <c:when test="${order.orderStatus==0}">
							      <td>
							               	 待付款
							       </c:when>
							       <c:when test="${order.orderStatus==1}">
							       <td>
							              	 待发货
							       </c:when>
							       <c:when test="${order.orderStatus==2}">
							       <td>
							              	 处理中
							       </c:when>
							       <c:when test="${order.orderStatus==3}">
							       <td>
							              	 成功
							       </c:when>
							       <c:when test="${order.orderStatus==5}">
							       <td>
							              	 部分成功
							       </c:when>
							       <c:when test="${order.orderStatus==4}">
							        <td class="tdgb">
							             	 失败
							       </c:when>
							       <c:when test="${order.orderStatus==9}">
							        <td>
							             	 部分失败
							       </c:when>
							       <c:otherwise>
							        <td>
							              	未知
							       </c:otherwise>
							</c:choose>
						</td>
							<c:choose>
								   <c:when test="${order.notifyStatus==0}">
							       <td>
							              	 无需通知
							       </c:when>
							       <c:when test="${order.notifyStatus==1}">
							       <td>
							              	等待通知
							       </c:when>
							       <c:when test="${order.notifyStatus==2}">
							        <td>
							             	 正在通知
							       </c:when>
							       <c:when test="${order.notifyStatus==3}">
							        <td>
							             	通知成功
							       </c:when>
							       <c:when test="${order.notifyStatus==4}">
							        <td class="tdgb">
							             	通知失败
							       </c:when>
							       <c:otherwise>
							        <td>
							              	未知
							       </c:otherwise>					
							</c:choose>					       
						</td>
						<td>
							<c:choose>
								   <c:when test="${order.manualFlag==0}">
							             	 无需手工处理
							       </c:when>
							       <c:when test="${order.manualFlag==1}">
							             	已转手工处理
							       </c:when>
							       <c:when test="${order.manualFlag==2}">
							             	已转手工处理
							       </c:when>
							       <c:otherwise>
							              	未知
							       </c:otherwise>					
							</c:choose>
						</td>
						<td><fmt:formatDate value="${order.orderFinishTime}" type="both" dateStyle="medium"/></td>
						<td>
		<%-- 					<a onclick="closePartSuccessOrder(${order.orderNo})">[强制失败]</a> --%>
		<%-- 					<a onclick="successPartSuccessOrder(${order.orderNo})">[强制成功]</a> --%>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
					<tr>
						<td colspan="18">没数据</td>
					</tr>
			</c:otherwise>
		</c:choose>
		
		</tbody>
		</tbody>
	</table>
<div class="line_pages">
	<div style="float:left;">
	  	显示条数：
	  	<select name="pageSize" id="pageSize" >
	  		<c:choose>
				<c:when test="${pageSize==10}">
					<option value="10" selected="selected">10</option>
				</c:when>
				<c:otherwise>
					<option value="10" >10</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==20}">
					<option value="20" selected="selected">20</option>
				</c:when>
				<c:otherwise>
					<option value="20" >20</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==30}">
					<option value="30" selected="selected">30</option>
				</c:when>
				<c:otherwise>
					<option value="30" >30</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==50}">
					<option value="50" selected="selected">50</option>
				</c:when>
				<c:otherwise>
					<option value="50" >50</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==100}">
					<option value="100" selected="selected">100</option>
				</c:when>
				<c:otherwise>
					<option value="100" >100</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
			<c:when test="${pageSize==500}">
				<option value="500" selected="selected">500</option>
			</c:when>
			<c:otherwise>
				<option value="500" >500</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${pageSize==1000}">
				<option value="1000" selected="selected">1000</option>
			</c:when>
			<c:otherwise>
				<option value="1000" >1000</option>
			</c:otherwise>
		</c:choose>
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
			$("#pager").pager({ pagenumber: pageclickednumber, pagecount: ${pagetotal}, buttonClickCallback: PageClick 
			}); 
			$("#page").val(pageclickednumber);
			$("#listForm").submit();
			}
		
		function queryOrder(){
			var beginDate=$("#beginDate").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginDate").focus(); 
	    		return false;
			}
			var endDate=$("#endDate").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endDate").focus(); 
	    		return false;
			}
			var orderNo=$("#orderNo").val();
			if(orderNo!=""&&orderNo!=null)
			{
				var reg=/^\d+$/;
		    	if(!reg.test(orderNo)){
		    		alert("系统订单号格式错误，只能是数字！");
		    		document.getElementById("orderNo").focus(); 
		    		return false;
		    	}
			}
			var parValue=$("#parValue").val();
			if(parValue!=""&&parValue!=null)
			{
				var reg=/^\d+$/;
		    	if(!reg.test(parValue)){
		    		alert("面值格式错误，只能是数字！");
		    		document.getElementById("parValue").focus(); 
		    		return false;
		    	}
			}
			/******  手机号码   ******/  
		      var usercode = $("#usercode").val().trim(); 
			if(usercode!=""&&usercode!=null)
			{
		      	if(usercode.length<11){  
			         alert('手机号码长度不能少于11位~~~！');  
				     document.getElementById("usercode").focus();  
			         return false;  
			  	}
			}
			var businessType=$("#businessType").val();
			var statisticsOrder=0;
			$('input[name="statisticsOrder"]:checked').each(function(){
				statisticsOrder=$("#statisticsOrder").val();
			   });
			window.location.href="${ctx}/transaction/orderPartSuccessList?carrierInfo="
				+$("#carrierInfo").val()
				+"&province="+$("#province").val()
				+"&parValue="+parValue
				+"&agentMerchant="+$("#agentMerchant").val()
				+"&beginDate="+$("#beginDate").val()
				+"&endDate="+$("#endDate").val()
				+"&notifyStatus="+$("#notifyStatus").val()
				+"&orderNo="+orderNo
				+"&merchantOrderNo="+$("#merchantOrderNo").val()
				+"&orderStatus=5"//+"&orderStatus="+$("#orderStatus").val()
				+"&preSuccessStatus="+$("#preSuccessStatus").val()
				+"&usercode="+usercode
				+"&pageSize="+$("#pageSize").val()
				+"&changeDate="+$('#changeDate').val()
				+"&statisticsOrder="+statisticsOrder
				+"&businessType="+businessType;
		}
		
		function closePartSuccessOrder(orderNo){
			window.location.href="${ctx}/transaction/closePartSuccessOrder?orderNo="+orderNo;
		}
		
		function successPartSuccessOrder(orderNo){
			window.location.href="${ctx}/transaction/successPartSuccessOrder?orderNo="+orderNo;
		}
		
		
		
		function changeSetDate(obj){
			var changeDate= $(obj).val();
			var date = new Date();
			$('#endDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
			if(changeDate=='m'){
				date.setMinutes(date.getMinutes()-10);
			}else if(changeDate=='h'){
				date.setHours(date.getHours()-1);
			}else if(changeDate=='d'){
				 date.setHours(23);
	                date.setMinutes(59);
	                date.setSeconds(59);
				    $('#endDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
					date.setHours(0);
					date.setMinutes(0);
					date.setSeconds(0);
			}else if (changeDate == 'Y')
			{
				date.setDate(date.getDate()-1);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				$('#endDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
			}
			$('#beginDate').val(date.Format("yyyy-MM-dd hh:mm:ss"));
		}
		
		//statisticsOrderInfo
		
		$(document).ready(function(){
			 $('#statisticsOrder').click(function(){
				   var bl = this.checked;	
				   if(bl){
					   var businessType=$("#businessType").val();
	                   if(businessType==null || businessType==""){
	                       alert("请先选择业务类型!");
	                       this.checked=false;
	                       return;
	                   }
					 statisticsOrderInfo();
				   }else{
					   $(".msg1").text("");
					   $(".msg2").text("");
					   $(".msg3").text("");
					   $(".msg4").text("");
				   }
			});
			   
			   $('input[name="statisticsOrder"]:checked').each(function(){
				   var businessType=$("#businessType").val();
                   if(businessType==null || businessType==""){
                       alert("请先选择业务类型!");
                       this.checked=false;
                       return;
                   }
				  statisticsOrderInfo();
			   });
		});
		
		
		function statisticsOrderInfo(){
			var beginDate=$("#beginDate").val();
			if(beginDate==""||beginDate==null)
			{
	    		alert("开始时间不能为空！");
	    		document.getElementById("beginDate").focus(); 
	    		return false;
			}
			var endDate=$("#endDate").val();
			if(endDate==""||endDate==null)
			{
	    		alert("结束时间不能为空！");
	    		document.getElementById("endDate").focus(); 
	    		return false;
			}
			var orderNo=$("#orderNo").val();
			if(orderNo!=""&&orderNo!=null)
			{
				var reg=/^\d+$/;
		    	if(!reg.test(orderNo)){
		    		alert("系统订单号格式错误，只能是数字！");
		    		document.getElementById("orderNo").focus(); 
		    		return false;
		    	}
			}
			var parValue=$("#parValue").val();
			if(parValue!=""&&parValue!=null)
			{
				var reg=/^\d+$/;
		    	if(!reg.test(parValue)){
		    		alert("面值格式错误，只能是数字！");
		    		document.getElementById("parValue").focus(); 
		    		return false;
		    	}
			}
			/******  手机号码   ******/  
		      var usercode = $("#usercode").val().trim(); 
			if(usercode!=""&&usercode!=null)
			{
		      	if(usercode.length<11){  
			         alert('手机号码长度不能少于11位~~~！');   
				     document.getElementById("usercode").focus(); 
			         return false;  
			  	}
			}
			var businessType=$("#businessType").val();
				$.ajax({
						url:"${ctx}/transaction/statisticsOrderInfo?carrierInfo="
						+$("#carrierInfo").val()
						+"&province="+$("#province").val()
						+"&parValue="+parValue
						+"&merchantId="+$("#downMerchant").val()
						+"&beginDate="+$("#beginDate").val()
						+"&endDate="+$("#endDate").val()
						+"&notifyStatus="+$("#notifyStatus").val()
						+"&orderNo="+orderNo
						+"&merchantOrderNo="+$("#merchantOrderNo").val()
						+"&orderStatus=5"//+$("#status").val()
						+"&preSuccessStatus="+$("#preSuccessStatus").val()
						+"&usercode="+usercode
						+"&businessType="+businessType,
						type: "post",
				        data: null,
				        async: false,
						success:function(data) {
							var obj =JSON.parse(data);
							if(obj.productface!=null && obj.productface!='undefined'){
								$(".msg1").text(obj.productface);
							}else{
								$(".msg1").text("0");
							}
							
							if(obj.ordersuccessfee!=null && obj.ordersuccessfee!='undefined'){
								$(".msg2").text(obj.ordersuccessfee);
							}else{
								$(".msg2").text("0");
							}
							
							if(obj.orderwaitfee!=null && obj.orderwaitfee!='undefined'){
								$(".msg3").text(obj.orderwaitfee);
							}else{
								$(".msg3").text("0");
							}
							
							if(obj.orderfailfee!=null && obj.orderfailfee!='undefined'){
								$(".msg4").text(obj.orderfailfee);
							}else{
								$(".msg4").text("0");
							}
						}
				});
		}
		
		function changeDateSelect(){
			$('#changeDate').val('');			
		}
		function changeBusinessType(obj){
		    var businessType=$(obj).val();
		    if(businessType==''){
		        $("[name = statisticsOrder]:checkbox").attr("checked", false);
		    }
		}
	</script> 
</form>
</body>
</html>

