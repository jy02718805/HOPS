<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看订单 - Powered By SHOP++</title>
<#assign base=request.contextPath>
<meta name="copyright" content="SHOP++" />
<link href="${base}/template/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $confirmForm = $("#confirmForm");
	var $completeForm = $("#completeForm");
	var $cancelForm = $("#cancelForm");
	var $confirmButton = $("#confirmButton");
	var $paymentButton = $("#paymentButton");
	var $shippingButton = $("#shippingButton");
	var $completeButton = $("#completeButton");
	var $refundsButton = $("#refundsButton");
	var $returnsButton = $("#returnsButton");
	var $cancelButton = $("#cancelButton");
	var isLocked = false;
	
	
	// 检查锁定
	function checkLock() {
		if (!isLocked) {
			$.ajax({
				url: "check_lock.jhtml",
				type: "POST",
				data: {id: 433},
				dataType: "json",
				cache: false,
				success: function(message) {
					if (message.type != "success") {
						$.message(message);
						$confirmButton.prop("disabled", true);
						$paymentButton.prop("disabled", true);
						$shippingButton.prop("disabled", true);
						$completeButton.prop("disabled", true);
						$refundsButton.prop("disabled", true);
						$returnsButton.prop("disabled", true);
						$cancelButton.prop("disabled", true);
						isLocked = true;
					}
				}
			});
		}
	}
	
	// 检查锁定
	checkLock();
	setInterval(function() {
		checkLock();
	}, 10000);
	
	// 确认
	$confirmButton.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "订单确认后将无法编辑，是否继续?",
			onOk: function() {
				$confirmForm.submit();
			}
		});
	});
	
	// 完成
	$completeButton.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "订单完成后将无法操作，是否继续?",
			onOk: function() {
				$completeForm.submit();
			}
		});
	});
	
	// 取消
	$cancelButton.click(function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "订单取消后将无法操作，是否继续?",
			onOk: function() {
				$cancelForm.submit();
			}
		});
	});

	
	
	

});
</script>
</head>
<body>
	<form id="confirmForm" action="confirm.jhtml" method="post">
		<input type="hidden" name="id" value="433" />
	</form>
	<form id="completeForm" action="complete.jhtml" method="post">
		<input type="hidden" name="id" value="433" />
	</form>
	<form id="cancelForm" action="cancel.jhtml" method="post">
		<input type="hidden" name="id" value="433" />
	</form>
	<div class="path">
		<a href="/admin/common/index.jhtml">首页</a> &raquo; 查看订单
	</div>
	<ul id="tab" class="tab">
		<li>
			<input type="button" value="订单信息" />
		</li>
		<li>
			<input type="button" value="商品信息" />
		</li>
		<li>
			<input type="button" value="收款信息" />
		</li>
		<li>
			<input type="button" value="发货信息" />
		</li>
		<li>
			<input type="button" value="退款信息" />
		</li>
		<li>
			<input type="button" value="退货信息" />
		</li>
		<li>
			<input type="button" value="订单日志" />
		</li>
	</ul>
	<table class="input tabContent">
		<tr>
			<td>&nbsp;
				
			</td>
			<td>
				<input type="button" id="confirmButton" class="button" value="确 认" disabled="disabled" />
				<input type="button" id="paymentButton" class="button" value="支 付" disabled="disabled" />
				<input type="button" id="shippingButton" class="button" value="发 货" disabled="disabled" />
				<input type="button" id="completeButton" class="button" value="完 成" disabled="disabled" />
			</td>
			<td>&nbsp;
				
			</td>
			<td>
				<input type="button" id="refundsButton" class="button" value="退 款" disabled="disabled" />
				<input type="button" id="returnsButton" class="button" value="退 货" disabled="disabled" />
				<input type="button" id="cancelButton" class="button" value="取 消" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<th>
				订单编号:
			</th>
			<td width="360">
				201308251107
			</td>
			<th>
				创建日期:
			</th>
			<td>
				2013-08-25 22:44:17
			</td>
		</tr>
		<tr>
			<th>
				订单状态:
			</th>
			<td>
				未确认
					<span title="到期时间: 2013-08-26 22:44:17">(已过期)</span>
			</td>
			<th>
				支付状态:
			</th>
			<td>
				未支付
			</td>
		</tr>
		<tr>
			<th>
				配送状态:
			</th>
			<td>
				未发货
			</td>
			<th>
				用户名:
			</th>
			<td>
				snowlov
			</td>
		</tr>
		<tr>
			<th>
				订单金额:
			</th>
			<td>
				￥259.20
			</td>
			<th>
				已付金额:
			</th>
			<td>
				￥0.00
			</td>
		</tr>
		<tr>
			<th>
				商品重量:
			</th>
			<td>
				0
			</td>
			<th>
				商品数量:
			</th>
			<td>
				1
			</td>
		</tr>
		<tr>
			<th>
				促销:
			</th>
			<td>
				限时抢购
			</td>
			<th>
				使用优惠券:
			</th>
			<td>
				-
			</td>
		</tr>
		<tr>
			<th>
				促销折扣:
			</th>
			<td>
				￥28.80
			</td>
			<th>
				优惠券折扣:
			</th>
			<td>
				￥0.00
			</td>
		</tr>
		<tr>
			<th>
				调整金额:
			</th>
			<td>
				￥0.00
			</td>
			<th>
				赠送积分:
			</th>
			<td>
				288
			</td>
		</tr>
		<tr>
			<th>
				运费:
			</th>
			<td>
				￥0.00
			</td>
			<th>
				支付手续费:
			</th>
			<td>
				￥0.00
			</td>
		</tr>
		<tr>
			<th>
				支付方式:
			</th>
			<td>
				网上支付
			</td>
			<th>
				配送方式:
			</th>
			<td>
				普通快递
			</td>
		</tr>
		<tr>
			<th>
				收货人:
			</th>
			<td>
				aaaa
			</td>
			<th>
				地区:
			</th>
			<td>
				北京市西城区
			</td>
		</tr>
		<tr>
			<th>
				地址:
			</th>
			<td>
				aaaa
			</td>
			<th>
				邮编:
			</th>
			<td>
				111111
			</td>
		</tr>
		<tr>
			<th>
				电话:
			</th>
			<td>
				1111111
			</td>
			<th>
				附言:
			</th>
			<td>
		</td>
		</tr>
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				商品编号
			</th>
			<th>
				商品名称
			</th>
			<th>
				商品价格
			</th>
			<th>
				数量
			</th>
			<th>
				已发货数量
			</th>
			<th>
				已退货数量
			</th>
			<th>
				小计
			</th>
		</tr>
			<tr>
				<td>
					201304151
				</td>
				<td width="400">
					<span title="梵希蔓2013夏装淑女连衣裙雪纺刺绣背心裙高腰荷叶边连衣裙高腰[米黄色 S]">梵希蔓2013夏装淑女连衣裙雪纺刺绣背心裙高腰荷叶边连...</span>
				</td>
				<td>
						￥288.00
				</td>
				<td>
					1
				</td>
				<td>
					0
				</td>
				<td>
					0
				</td>
				<td>
						￥288.00
				</td>
			</tr>
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				编号
			</th>
			<th>
				方式
			</th>
			<th>
				支付方式
			</th>
			<th>
				付款金额
			</th>
			<th>
				状态
			</th>
			<th>
				付款日期
			</th>
		</tr>
			<tr>
				<td>
					201308251006
				</td>
				<td>
					在线支付
				</td>
				<td>
					网上支付 - 支付宝即时交易
				</td>
				<td>
					￥259.20
				</td>
				<td>
					等待支付
				</td>
				<td>
						-
				</td>
			</tr>
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				编号
			</th>
			<th>
				配送方式
			</th>
			<th>
				物流公司
			</th>
			<th>
				运单号
			</th>
			<th>
				收货人
			</th>
			<th>
				创建日期
			</th>
		</tr>
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				编号
			</th>
			<th>
				方式
			</th>
			<th>
				支付方式
			</th>
			<th>
				退款金额
			</th>
			<th>
				创建日期
			</th>
		</tr>
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				编号
			</th>
			<th>
				配送方式
			</th>
			<th>
				物流公司
			</th>
			<th>
				运单号
			</th>
			<th>
				发货人
			</th>
			<th>
				创建日期
			</th>
		</tr>
	</table>
	<table class="input tabContent">
		<tr class="title">
			<th>
				类型
			</th>
			<th>
				操作员
			</th>
			<th>
				内容
			</th>
			<th>
				创建日期
			</th>
		</tr>
			<tr>
				<td>
					订单创建
				</td>
				<td>
						<span class="green">会员</span>
				</td>
				<td>
				</td>
				<td>
					<span title="2013-08-25 22:44:17">2013-08-25</span>
				</td>
			</tr>
	</table>
	<table class="input">
		<tr>
			<th>&nbsp;
				
			</th>
			<td>
				<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='list.jhtml'" />
			</td>
		</tr>
	</table>
</body>
</html>