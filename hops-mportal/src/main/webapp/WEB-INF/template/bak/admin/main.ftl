<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0046) -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>管理中心</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<META name=author content="">
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="resource/css/common.css">
<LINK rel=stylesheet type=text/css href="resource/css/main.css">
<SCRIPT type=text/javascript 
src="resource/js/jquery.js"></SCRIPT>

<STYLE type=text/css>* {
	FONT: 12px tahoma, Arial, Verdana, sans-serif
}
HTML {
	WIDTH: 100%; HEIGHT: 100%; OVERFLOW: hidden
}
BODY {
	WIDTH: 100%; HEIGHT: 100%; OVERFLOW: hidden
}
</STYLE>

<SCRIPT type=text/javascript>
$().ready(function() {

	var $nav = $("#nav a:not(:last)");
	var $menu = $("#menu dl");
	var $menuItem = $("#menu a");
	
	$nav.click(function() {
		var $this = $(this);
		$nav.removeClass("current");
		$this.addClass("current");
		var $currentMenu = $($this.attr("href"));
		$menu.hide();
		$currentMenu.show();
		return false;
	});
	
	$menuItem.click(function() {
		var $this = $(this);
		$menuItem.removeClass("current");
		$this.addClass("current");
	});

});
</SCRIPT>

<META name=GENERATOR content="MSHTML 8.00.7600.16385"></HEAD>
<BODY>
<SCRIPT type=text/javascript>
		if (self != top) {
			top.location = self.location;
		};
	</SCRIPT>

<TABLE class=main>
  <TBODY>
  <TR>
    <TH class=logo><A 
      href=""><!--<IMG alt=SHOP++ 
      src="resource/images/header_logo.jpg">--></A></TH>
    <TH>
      <DIV id=nav class=nav>
      <UL>
        <LI><A 
        href="#product">商品</A> 
        </LI>
        <LI><A 
        href="#order">订单</A> </LI>
        <LI><A 
        href="#member">会员</A> 
</LI>
        <LI><A 
        href="#content">内容</A> 
        </LI>
        <LI><A 
        href="#marketing">营销</A> 
        </LI>
        <LI><A 
        href="#statistics">统计</A> 
        </LI>
        <LI><A 
        href="#system">系统</A> 
</LI>
        <LI><A href="" target=_blank>跃程官网</A> 
</LI></UL></DIV>
      <DIV class=link><A href="" target=_blank>跃程官网</A>| 
      <A href="http://bbs.shopxx.net/" target=_blank>交流论坛</A>| <A 
      href="about.html" target=_blank>关于跃程</A> </DIV>
      <DIV class=link><STRONG>admin</STRONG> 您好! <A 
      href="http://demo.shopxx.net/admin/profile/edit.jhtml" 
      target=iframe>[账号设置]</A> <A href="http://demo.shopxx.net/admin/logout.jsp" 
      target=_top>[注销]</A> </DIV></TH></TR>
  <TR>
    <TD id=menu class=menu>
      <DL id=product class=default>
        <DT>商品管理 
        <DD><A href="http://demo.shopxx.net/admin/product/list.jhtml" 
        target=iframe>商品管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/product_category/list.jhtml" 
        target=iframe>商品分类</A> 
        <DD><A href="http://demo.shopxx.net/admin/parameter_group/list.jhtml" 
        target=iframe>商品参数</A> 
        <DD><A href="http://demo.shopxx.net/admin/attribute/list.jhtml" 
        target=iframe>商品属性</A> 
        <DD><A href="http://demo.shopxx.net/admin/specification/list.jhtml" 
        target=iframe>规格管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/brand/list.jhtml" 
        target=iframe>品牌管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/product_notify/list.jhtml" 
        target=iframe>到货通知</A> </DD></DL>
      <DL id=order>
        <DT>订单管理 
        <DD><A href="http://demo.shopxx.net/admin/order/list.jhtml" 
        target=iframe>订单管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/payment/list.jhtml" 
        target=iframe>收款管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/refunds/list.jhtml" 
        target=iframe>退款管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/shipping/list.jhtml" 
        target=iframe>发货管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/returns/list.jhtml" 
        target=iframe>退货管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/delivery_center/list.jhtml" 
        target=iframe>发货点管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/delivery_template/list.jhtml" 
        target=iframe>快递单模板</A> </DD></DL>
      <DL id=member>
        <DT>会员管理 
        <DD><A href="http://demo.shopxx.net/admin/member/list.jhtml" 
        target=iframe>会员管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/member_rank/list.jhtml" 
        target=iframe>会员等级</A> 
        <DD><A href="http://demo.shopxx.net/admin/member_attribute/list.jhtml" 
        target=iframe>会员注册项</A> 
        <DD><A href="http://demo.shopxx.net/admin/review/list.jhtml" 
        target=iframe>评论管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/consultation/list.jhtml" 
        target=iframe>咨询管理</A> </DD></DL>
      <DL id=content>
        <DT>内容管理 
        <DD><A href="http://demo.shopxx.net/admin/navigation/list.jhtml" 
        target=iframe>导航管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/article/list.jhtml" 
        target=iframe>文章管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/article_category/list.jhtml" 
        target=iframe>文章分类</A> 
        <DD><A href="http://demo.shopxx.net/admin/tag/list.jhtml" 
        target=iframe>标签管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/friend_link/list.jhtml" 
        target=iframe>友情链接</A> 
        <DD><A href="http://demo.shopxx.net/admin/ad_position/list.jhtml" 
        target=iframe>广告位</A> 
        <DD><A href="http://demo.shopxx.net/admin/ad/list.jhtml" 
        target=iframe>广告管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/template/list.jhtml" 
        target=iframe>模板管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/cache/clear.jhtml" 
        target=iframe>缓存管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/static/build.jhtml" 
        target=iframe>静态化管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/index/build.jhtml" 
        target=iframe>索引管理</A> </DD></DL>
      <DL id=marketing>
        <DT>营销管理 
        <DD><A href="resource/list.htm" 
        target=iframe>促销管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/coupon/list.jhtml" 
        target=iframe>优惠券管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/seo/list.jhtml" 
        target=iframe>SEO设置</A> 
        <DD><A href="http://demo.shopxx.net/admin/sitemap/build.jhtml" 
        target=iframe>Sitemap管理</A> </DD></DL>
      <DL id=statistics>
        <DT>统计报表 
        <DD><A href="http://demo.shopxx.net/admin/statistics/view.jhtml" 
        target=iframe>访问统计</A> 
        <DD><A href="http://demo.shopxx.net/admin/statistics/setting.jhtml" 
        target=iframe>统计设置</A> 
        <DD><A href="http://demo.shopxx.net/admin/sales/view.jhtml" 
        target=iframe>销售统计</A> 
        <DD><A href="http://demo.shopxx.net/admin/sales_ranking/list.jhtml" 
        target=iframe>销售排行</A> 
        <DD><A href="http://demo.shopxx.net/admin/purchase_ranking/list.jhtml" 
        target=iframe>消费排行</A> 
        <DD><A href="http://demo.shopxx.net/admin/deposit/list.jhtml" 
        target=iframe>预存款</A> </DD></DL>
      <DL id=system>
        <DT>系统设置 
        <DD><A href="http://demo.shopxx.net/admin/setting/edit.jhtml" 
        target=iframe>系统设置</A> 
        <DD><A href="http://demo.shopxx.net/admin/area/list.jhtml" 
        target=iframe>地区管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/payment_method/list.jhtml" 
        target=iframe>支付方式</A> 
        <DD><A href="http://demo.shopxx.net/admin/shipping_method/list.jhtml" 
        target=iframe>配送方式</A> 
        <DD><A href="http://demo.shopxx.net/admin/delivery_corp/list.jhtml" 
        target=iframe>物流公司</A> 
        <DD><A href="http://demo.shopxx.net/admin/payment_plugin/list.jhtml" 
        target=iframe>支付插件</A> 
        <DD><A href="http://demo.shopxx.net/admin/storage_plugin/list.jhtml" 
        target=iframe>存储插件</A> 
        <DD><A href="http://demo.shopxx.net/admin/admin/list.jhtml" 
        target=iframe>管理员</A> 
        <DD><A href="http://demo.shopxx.net/admin/role/list.jhtml" 
        target=iframe>角色管理</A> 
        <DD><A href="http://demo.shopxx.net/admin/message/send.jhtml" 
        target=iframe>发送消息</A> 
        <DD><A href="http://demo.shopxx.net/admin/message/list.jhtml" 
        target=iframe>消息列表</A> 
        <DD><A href="http://demo.shopxx.net/admin/message/draft.jhtml" 
        target=iframe>草稿箱</A> 
        <DD><A href="http://demo.shopxx.net/admin/log/list.jhtml" 
        target=iframe>日志管理</A> </DD></DL></TD>
    <TD><IFRAME id=iframe 
      src="resource/list.htm" frameBorder=0 
      name=iframe></IFRAME></TD></TR></TBODY></TABLE></BODY></HTML>
