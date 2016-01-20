<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>人工审核页面</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<#setting number_format="#">
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>

<body>
<div class="mg10"></div>
	<form id="inputForm" action=""  method="get">
		<table class="input">
		<tr>
			<th>
				订单号:
			</th>
			<td>
				<input type="hidden" id="url" name="url" class="ipt" value="${url}"/>
				<input type="hidden" id="userCode" name="userCode" class="ipt" value="${order.userCode}"/>
				<input type="hidden" id="orderStatus" name="orderStatus" class="ipt" value="${order.orderStatus}"/>
				${order.orderNo} 
			</td>
			<th>
				订单状态:
			</th>
			<td>
				<#if order.orderStatus==0>待付款
				<#elseif order.orderStatus==1>待发货
				<#elseif order.orderStatus==2>发货中
				<#elseif order.orderStatus==3>成功
				<#elseif order.orderStatus==4>失败
				<#elseif order.orderStatus==5>部分成功
				<#elseif order.orderStatus==91>部分失败
				<#else>未知
				</#if>
			</td>
		</tr>
		<tr>
			<th>
				代理商名称:
			</th>
			<td>
				${order.merchantName}
			</td>
			<th>
				代理商订单号:
			</th>
			<td>
				${order.merchantOrderNo}
			</td>
		</tr>
		<tr>
			<th>
				产品编号:
			</th>
			<td>
				${order.productNo}
			</td>
			<th>
				运营商/省/市:
			</th>
			<td>
				${order.ext1}/${order.ext2}/${order.ext3}
			</td>
		</tr>
		<tr>
			<th>
				充值账号:
			</th>
			<td>
				${order.userCode}
			</td>
			<th>
				面值
			</th>
			<td>
			${order.orderFee}
			</td>
		</tr>
		</table>
		<table class="input">
			<tr>
				<th>
					审核状态:
				</th>
				<td>
					<#if order.orderStatus==3>
						<@shiro.hasPermission name="orderouttime:success_order">
							<input type="button" class="button" onclick="intiCloseOrder()"  value="换号重绑" class="button"/>
						</@shiro.hasPermission>
					<#else>
						<#if (deliverys?size>0)>
							<!-- 有未完成的发货记录 -->
							<@shiro.hasPermission name="orderouttime:success_order">
								<input type="button" class="button" onclick="checkSuccessOrder('${order.orderNo}')"  value="审核成功" class="button"/>
							</@shiro.hasPermission>
							<!-- 没有未完成的发货记录 -->
							<@shiro.hasPermission name="orderouttime:rebind_order">
								<input type="button" class="button" onclick="intiCloseOrder()"  value="重绑" class="button"/>
							</@shiro.hasPermission>
						<#else>
							<!-- 没有未完成的发货记录 -->
							<@shiro.hasPermission name="orderouttime:rebind_order">
								<input type="button" class="button" onclick="intiCloseOrder()"  value="重绑" class="button"/>
							</@shiro.hasPermission>
						</#if>
					</#if>
				</td>
			</tr>
		</table>
		<table class="input" id="successTable" style="display:none;">
			<tr>
				<th>
					成功金额:
				</th>
				<td>
					<input type="text" id="successFee" name="successFee" class="ipt" disabled="disabled" value="${(order.orderFee)?string("0.0000")}"/>
				</td>
			</tr>
		</table>
		<table class="input" id="failTable" style="display:none;">
			<tr>
				<th>
					手机号码:
				</th>
				<td>
					<input type="text" id="phoneNo" name="phoneNo" class="ipt"  maxlength="11" onchange="changePhoneNo()" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				</td>
			</tr>
			<tr>
				<th>
					供货商名称:
				</th>
				<td>
					<select name="merchantId" id="merchantId" class="select"  onchange="getProduct(this.value, ${(order.orderFee)?string("0.0000")})">
						<option value="" selected=selected>--请选择--</option>
						<#list merchants as list>
							<option value="${list.id}" >${list.merchantName}</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					供货商产品:
				</th>
				<td>
					<select name="merchantPid" id="merchantPid" class="select" onchange="checkDiscount(this.value,'${(order.productSaleDiscount)?string("0.0000")}')">
					
					</select>
					<span id="discountshow" style="color:red;"></span>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input class="button" type="button" onclick="checkOrderDo('${order.orderNo}')" value="提交"/>
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
