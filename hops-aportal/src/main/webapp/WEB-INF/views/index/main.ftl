<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>管理中心</title>
<meta content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name=author content="">
<#assign base=request.contextPath>
<link rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<link rel=stylesheet type=text/css href="${base}/template/admin/css/manage.css">
<script type=text/javascript src="${base}/template/admin/js/jquery.js"></script>

<script type=text/javascript>
$().ready(function() {
	var $nav = $("#nav a");
	var $menu = $("#menu ul");
	var $menuItem = $("#menu a");
	
	$nav.click(function() {
		var $this = $(this);
		$nav.removeClass("active");
		$this.addClass("active");
		var $currentMenu = $($this.attr("href"));
		$menu.hide();
		$currentMenu.show();
		$("#topmenuname").html("<strong>"+$("#nav .active").text()+"</strong>");
		return false;
	});
	
	$menuItem.click(function() {
		var $this = $(this);
		$menuItem.removeClass("active");
		$this.addClass("active");
	});
	
	$("#nav a").eq(0).click();
});
</script>

<script type=text/javascript>
		if (self != top) {
			top.location = self.location;
		};
		
	</script>

</head>
<body>
<div class="mg-wrap">
<div class="mg-header">
<h1 class="logo fl"><a href="#"><img src="${base}/template/admin/images/pt-login-logo.png" /></a></h1>
<dl class="status fr">
<dt class="fl"><strong>${loginUser.displayName}</strong>您好！</dt>
<dd><i class="status-user"></i><a href="${base}/Operator/viewinfo?id=${loginUser.id}" target="iframe">个人资料</a></dd>
<dd><i class="status-pass"></i><a href="${base}/Operator/toModifyPassword?id=${loginUser.id}" target="iframe">修改密码</a></dd>
<dd><i class="status-out"></i><a href="${base}/login/loginout" target="_top">退出</a></dd>
</dl>
</div>



    <div class="g-sd1">
        <div class="mg-sub-menu">
        <div class="sub-menu-title" id="topmenuname"><strong>订单管理</strong></div>
        <div id=menu >
        <#list menulist0 as list0>
        <ul id="${list0.menu.pageResource.pageUrl}">
        		<#list menulist1 as list1>	        				
			    	<#if (list1.menu.status=="0" && list1.menu.parentMenuId==list0.menu.menuId && list1.status=="0")>
			    	<li><a href="${list1.menu.pageResource.pageUrl}" target=iframe>${list1.menu.menuName}</a></li>
			        </#if>
			    </#list>
		</ul>
	</#list>
        </div>
        
        </div>
    </div>
    <div class="g-mn1">
        <div class="g-mn1c">
            <div  id=nav class="mg-menu-box">
            <ul class="mg-menu">
            
        <#list menulist0 as list0>
      	<#if list0.status=="0">
		        <LI>
		        	<a href="#${list0.menu.pageResource.pageUrl!""}" title="${list0.menu.menuName}"><i class="mg-menu-ico mg-menu-ico${list0_index+1}"></i>${list0.menu.menuName}</a>
		        </LI>
		</#if>
	  </#list>
            
            </ul>
            </div>
            
            <div class="mg-body">
             <iframe src="${base}/Operator/viewinfo?id=${loginUser.id}" scrolling="no" frameborder="0" height="100%" id="iframepage" onload="tablewidth();"  name="iframe" width="100%"></iframe>	

            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
	function tablewidth(){
	$("#iframepage").contents().find('body').css('overflowY','hidden');
	var wscreen=screen.width;
	var rtsreen=wscreen-200;
	$("#iframepage").contents().find("#listtable").css('width',rtsreen);
		}
    function reinitIframe(){
    var iframe = document.getElementById("iframepage");
    try{
    var bHeight = iframe.contentWindow.document.body.scrollHeight;
    var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
    var ifheight = Math.max(bHeight, dHeight);
    iframe.height =  ifheight;
    }catch (ex){}
    }
    window.setInterval("reinitIframe()", 200);
    </script>






</body>
</html>

</HTML>
