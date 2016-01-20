<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0326)/admin/order/list.jhtml -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE>订单列表 - Powered By SHOP++</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<META name=author content="SHOP++ Team">
<META name=copyright content=SHOP++><LINK rel=stylesheet type=text/css 
href="resource/css/common.css">
<SCRIPT type=text/javascript src="resource/js/jquery.js"></SCRIPT>

<SCRIPT type=text/javascript src="resource/js/common.js"></SCRIPT>

<SCRIPT type=text/javascript src="resource/js/list.js"></SCRIPT>

<SCRIPT type=text/javascript>
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	var $print = $("select[name='print']");

	
	// 订单筛选
	$filterSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	// 筛选选项
	$filterOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});
	
	// 打印选择
	$print.change(function() {
		var $this = $(this);
		if ($this.val() != "") {
			window.open($this.val());
		}
	});

});
</SCRIPT>
</HEAD>
<BODY>
<DIV class=path><A href="/admin/common/index.jhtml">首页</A> 
» 订单列表 <SPAN>(共<SPAN id=pageTotal>445</SPAN>条记录)</SPAN> </DIV>
<FORM id=listForm method=get action=list.jhtml><INPUT id=orderStatus type=hidden 
name=orderStatus> <INPUT id=paymentStatus type=hidden name=paymentStatus> <INPUT 
id=shippingStatus type=hidden name=shippingStatus> <INPUT id=hasExpired 
type=hidden name=hasExpired> 
<DIV class=bar>
<DIV class=buttonWrap><A id=deleteButton class="iconButton disabled" 
href="javascript:;"><SPAN class=deleteIcon>&nbsp;</SPAN>删除 </A><A 
id=refreshButton class=iconButton href="javascript:;"><SPAN 
class=refreshIcon>&nbsp;</SPAN>刷新 </A>
<DIV class=menuWrap><A id=filterSelect class=button 
href="javascript:;">订单筛选<SPAN class=arrow>&nbsp;</SPAN> </A>
<DIV class=popupMenu>
<UL id=filterOption class=check>
  <LI><A href="javascript:;" name=orderStatus val="unconfirmed">未确认</A> </LI>
  <LI><A href="javascript:;" name=orderStatus val="confirmed">已确认</A> </LI>
  <LI><A href="javascript:;" name=orderStatus val="completed">已完成</A> </LI>
  <LI><A href="javascript:;" name=orderStatus val="cancelled">已取消</A> </LI>
  <LI class=separator><A href="javascript:;" name=paymentStatus 
  val="unpaid">未支付</A> </LI>
  <LI><A href="javascript:;" name=paymentStatus val="partialPayment">部分支付</A> 
  </LI>
  <LI><A href="javascript:;" name=paymentStatus val="paid">已支付</A> </LI>
  <LI><A href="javascript:;" name=paymentStatus val="partialRefunds">部分退款</A> 
  </LI>
  <LI><A href="javascript:;" name=paymentStatus val="refunded">已退款</A> </LI>
  <LI class=separator><A href="javascript:;" name=shippingStatus 
  val="unshipped">未发货</A> </LI>
  <LI><A href="javascript:;" name=shippingStatus val="partialShipment">部分发货</A> 
  </LI>
  <LI><A href="javascript:;" name=shippingStatus val="shipped">已发货</A> </LI>
  <LI><A href="javascript:;" name=shippingStatus val="partialReturns">部分退货</A> 
  </LI>
  <LI><A href="javascript:;" name=shippingStatus val="returned">已退货</A> </LI>
  <LI class=separator><A href="javascript:;" name=hasExpired val="true">已过期</A> 
  </LI>
  <LI><A href="javascript:;" name=hasExpired val="false">未过期</A> 
</LI></UL></DIV></DIV>
<DIV class=menuWrap><A id=pageSizeSelect class=button 
href="javascript:;">每页显示<SPAN class=arrow>&nbsp;</SPAN> </A>
<DIV class=popupMenu>
<UL id=pageSizeOption>
  <LI><A href="javascript:;" val="10">10</A> </LI>
  <LI><A class=current href="javascript:;" val="20">20</A> </LI>
  <LI><A href="javascript:;" val="50">50</A> </LI>
  <LI><A href="javascript:;" val="100">100</A> </LI></UL></DIV></DIV></DIV>
<DIV class=menuWrap>
<DIV class=search><SPAN id=searchPropertySelect class=arrow>&nbsp;</SPAN> <INPUT 
id=searchValue maxLength=200 name=searchValue><BUTTON 
type=submit>&nbsp;</BUTTON> </DIV>
<DIV class=popupMenu>
<UL id=searchPropertyOption>
  <LI><A href="javascript:;" val="sn">订单编号</A> </LI></UL></DIV></DIV></DIV>
<TABLE id=listTable class=list>
  <TBODY>
  <TR>
    <TH class=check><INPUT id=selectAll type=checkbox> </TH>
    <TH><A class=sort href="javascript:;" name=sn>订单编号</A> </TH>
    <TH><SPAN>订单金额</SPAN> </TH>
    <TH><A class=sort href="javascript:;" name=member>会员</A> </TH>
    <TH><A class=sort href="javascript:;" name=consignee>收货人</A> </TH>
    <TH><A class=sort href="javascript:;" name=paymentMethodName>支付方式名称</A> 
</TH>
    <TH><A class=sort href="javascript:;" name=shippingMethodName>配送方式名称</A> 
    </TH>
    <TH><A class=sort href="javascript:;" name=orderStatus>订单状态</A> </TH>
    <TH><A class=sort href="javascript:;" name=paymentStatus>支付状态</A> </TH>
    <TH><A class=sort href="javascript:;" name=shippingStatus>配送状态</A> </TH>
    <TH><A class=sort href="javascript:;" name=createDate>创建日期</A> </TH>
    <TH><SPAN>打印</SPAN> </TH>
    <TH><SPAN>操作</SPAN> </TH></TR>
  <TR>
    <TD><INPUT value=445 type=checkbox name=ids> </TD>
    <TD>201308261119 </TD>
    <TD>￥546.40 </TD>
    <TD>tengyan </TD>
    <TD>1 </TD>
    <TD>货到付款 </TD>
    <TD>顺丰速递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 22:22:30">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=445>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=445>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=445>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=445>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=445">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=445">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=444 type=checkbox name=ids> </TD>
    <TD>201308261118 </TD>
    <TD>￥294.29 </TD>
    <TD>zz5238251 </TD>
    <TD>范德萨发的 </TD>
    <TD>货到付款 </TD>
    <TD>顺丰速递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 22:11:36">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=444>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=444>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=444>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=444>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=444">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=444">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=443 type=checkbox name=ids> </TD>
    <TD>201308261117 </TD>
    <TD>￥268.20 </TD>
    <TD>dong </TD>
    <TD>t </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 20:50:45">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=443>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=443>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=443>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=443>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=443">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=443">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=442 type=checkbox name=ids> </TD>
    <TD>201308261116 </TD>
    <TD>￥421.88 </TD>
    <TD>chen_cw </TD>
    <TD>111 </TD>
    <TD>银行汇款 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 20:33:01">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=442>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=442>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=442>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=442>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=442">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=442">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=441 type=checkbox name=ids> </TD>
    <TD>201308261115 </TD>
    <TD>￥453.00 </TD>
    <TD>sjziyou </TD>
    <TD>马博 </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 19:37:31">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=441>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=441>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=441>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=441>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=441">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=441">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=440 type=checkbox name=ids> </TD>
    <TD>201308261114 </TD>
    <TD>￥268.20 </TD>
    <TD>sjziyou </TD>
    <TD>马博 </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 19:33:25">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=440>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=440>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=440>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=440>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=440">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=440">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=439 type=checkbox name=ids> </TD>
    <TD>201308261113 </TD>
    <TD>￥323.00 </TD>
    <TD>sjziyou </TD>
    <TD>马博 </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 19:31:18">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=439>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=439>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=439>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=439>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=439">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=439">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=438 type=checkbox name=ids> </TD>
    <TD>201308261112 </TD>
    <TD>￥1453.30 </TD>
    <TD>弟弟 </TD>
    <TD>弟弟 </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 17:26:44">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=438>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=438>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=438>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=438>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=438">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=438">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=437 type=checkbox name=ids> </TD>
    <TD>201308261111 </TD>
    <TD>￥288.00 </TD>
    <TD>miaobihua </TD>
    <TD>zxt </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 16:31:05">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=437>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=437>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=437>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=437>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=437">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=437">[编辑]</A> 
</TD></TR>
  <TR>
    <TD><INPUT value=436 type=checkbox name=ids> </TD>
    <TD>201308261110 </TD>
    <TD>￥288.00 </TD>
    <TD>snowlov </TD>
    <TD>aaaa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 09:58:43">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=436>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=436>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=436>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=436>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=436">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=435 type=checkbox name=ids> </TD>
    <TD>201308261109 </TD>
    <TD>￥242.10 </TD>
    <TD>test </TD>
    <TD>aa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 09:54:52">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=435>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=435>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=435>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=435>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=435">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=434 type=checkbox name=ids> </TD>
    <TD>201308261108 </TD>
    <TD>￥298.00 </TD>
    <TD>test </TD>
    <TD>aa </TD>
    <TD>货到付款 </TD>
    <TD>顺丰速递 </TD>
    <TD>已取消 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-26 09:53:43">2013-08-26</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=434>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=434>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=434>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=434>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=434">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=433 type=checkbox name=ids> </TD>
    <TD>201308251107 </TD>
    <TD>￥259.20 </TD>
    <TD>snowlov </TD>
    <TD>aaaa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 22:44:17">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=433>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=433>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=433>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=433>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=433">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=432 type=checkbox name=ids> </TD>
    <TD>201308251106 </TD>
    <TD>￥288.00 </TD>
    <TD>testonly </TD>
    <TD>afefaewfa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 22:05:24">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=432>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=432>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=432>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=432>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=432">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=431 type=checkbox name=ids> </TD>
    <TD>201308251105 </TD>
    <TD>￥414.07 </TD>
    <TD>leoliu </TD>
    <TD>leo </TD>
    <TD>网上支付 </TD>
    <TD>顺丰速递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 21:45:49">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=431>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=431>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=431>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=431>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=431">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=430 type=checkbox name=ids> </TD>
    <TD>201308251104 </TD>
    <TD>￥778.40 </TD>
    <TD>testonly </TD>
    <TD>afefaewfa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 21:15:25">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=430>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=430>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=430>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=430>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=430">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=429 type=checkbox name=ids> </TD>
    <TD>201308251103 </TD>
    <TD>￥241.20 </TD>
    <TD>testonly </TD>
    <TD>afefaewfa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 15:49:09">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=429>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=429>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=429>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=429>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=429">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=428 type=checkbox name=ids> </TD>
    <TD>201308251102 </TD>
    <TD>￥536.40 </TD>
    <TD>testonly </TD>
    <TD>afefaewfa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 14:26:02">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=428>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=428>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=428>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=428>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=428">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=427 type=checkbox name=ids> </TD>
    <TD>201308251101 </TD>
    <TD>￥266.00 </TD>
    <TD>testonly </TD>
    <TD>afefaewfa </TD>
    <TD>网上支付 </TD>
    <TD>普通快递 </TD>
    <TD>未确认 <SPAN class=gray>(已过期)</SPAN> </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 14:24:35">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=427>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=427>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=427>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=427>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=427">[查看]</A> <SPAN 
      title=该订单状态不允许编辑>[编辑]</SPAN> </TD></TR>
  <TR>
    <TD><INPUT value=426 type=checkbox name=ids> </TD>
    <TD>201308251100 </TD>
    <TD>￥284.75 </TD>
    <TD>testonly </TD>
    <TD>afefaewfa </TD>
    <TD>货到付款 </TD>
    <TD>顺丰速递 </TD>
    <TD>未确认 </TD>
    <TD>未支付 </TD>
    <TD>未发货 </TD>
    <TD><SPAN title="2013-08-25 14:21:02">2013-08-25</SPAN> </TD>
    <TD><SELECT name=print> <OPTION selected value="">请选择...</OPTION> 
        <OPTION value=../print/order.jhtml?id=426>订单</OPTION> <OPTION 
        value=../print/product.jhtml?id=426>购物单</OPTION> <OPTION 
        value=../print/shipping.jhtml?id=426>配送单</OPTION> <OPTION 
        value=../print/delivery.jhtml?orderId=426>快递单</OPTION></SELECT> </TD>
    <TD><A 
      href="/admin/order/view.jhtml?id=426">[查看]</A> <A 
      href="/admin/order/edit.jhtml?id=426">[编辑]</A> 
  </TD></TR></TBODY></TABLE><INPUT id=pageSize value=20 type=hidden name=pageSize> 
<INPUT id=searchProperty type=hidden name=searchProperty> <INPUT 
id=orderProperty type=hidden name=orderProperty> <INPUT id=orderDirection 
type=hidden name=orderDirection> 
<DIV class=pagination><SPAN class=firstPage>&nbsp;</SPAN> <SPAN 
class=previousPage>&nbsp;</SPAN> <SPAN class=currentPage>1</SPAN> <A 
href="javascript: $.pageSkip(2);">2</A> <A 
href="javascript: $.pageSkip(3);">3</A> <SPAN class=pageBreak>...</SPAN> <A 
class=nextPage href="javascript: $.pageSkip(2);">&nbsp;</A> <A class=lastPage 
href="javascript: $.pageSkip(23);">&nbsp;</A> <SPAN class=pageSkip>共23页 到第<INPUT 
id=pageNumber onpaste="return false;" value=1 maxLength=9 
name=pageNumber>页<BUTTON type=submit>&nbsp;</BUTTON> 
</SPAN></DIV></FORM></BODY></HTML>
