<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0046) -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>管理中心</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<META name=author content="">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/main.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>

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
    <TH class=logo>
    	<A href=""></A>
    </TH>
    <TH>
      <DIV id=nav class=nav>
	      <UL>
	        <LI>
	        	<A href="#product">商户管理</A> 
	        </LI>
	 	  </UL>
	 	  <UL>
	        <LI>
	        	<A href="#account">账户管理</A> 
	        </LI>
	 	  </UL>
 	  </DIV>
      <DIV class=link></DIV>
    </TH>
  </TR>
  <TR>
    <TD id=menu class=menu>
      <DL id="product" class=default>
        <DT>商户管理
        <DD><A href="Organization/list" target=iframe>组织机构管理</A> </DD>
		<DD><A href="Merchant/list"  target=iframe>商户管理</A> </DD>
		<DD><A href="Channel/add"  target=iframe>商户管理员维护</A></DD>
        </DD>
      </DL>
      <DL id="account" class=default>
        <DT>账户管理
        <DD><A href="accountType/accountTypeList" target=iframe>账户类型</A> 
        <DD><A href="account/addAccount" target=iframe>创建账户</A> 
        <DD><A href="account/listCurrencyAccount"  target=iframe>虚拟账户列表</A> 
        <DD><A href="account/listCardAccount"  target=iframe>实体卡账户列表</A>
        </DD>
      </DL>
      <DL id="account" class=default>
        <DT>用户管理
        <DD><A href="Customer/list" target=iframe>用户列表</A> 
        </DD>
      </DL>
      <DL id="account" class=default>
        <DT>角色管理
        <DD><A href="Role/list" target=iframe>角色列表</A> 
        </DD>
      </DL>
      <DL id="account" class=default>
        <DT>权限管理
        <DD><A href="Menu/list" target=iframe>菜单权限列表</A> 
        <DD><A href="Role/list" target=iframe>功能权限列表</A> 
        <DD><A href="PageResource/list" target=iframe>页面资源列表</A> 
        </DD>
      </DL>
     </TD>
    <TD>
    <IFRAME id=iframe src="Merchant/add" frameBorder=0 name=iframe>
    </IFRAME>
    </TD>
   </TR>
   </TBODY>
</TABLE>
</BODY>
</HTML>
