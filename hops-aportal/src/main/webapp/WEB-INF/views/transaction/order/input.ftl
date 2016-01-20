<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理界面</title>
<#assign base=request.contextPath>
<#setting number_format="#">
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/manage.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/admin/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function () { 
	$('.parvalue').find('span').click(function () { 
		$('.parvalue').find('span').removeClass('cur'); 
		$(this).addClass('cur'); 
	})
	}) 
   </script>
</head>
<script>
  
</script>
<body style="overflow-y: hidden;">

            <!--充值流程-->
            <div class="credit-content">
                <div class="step credit-step1">
					 <ul>
						<li class="step01 cur">1. 填写充值信息<span></span></li>
						<li class="step02">2. 确认订单<span></span></li>
						<li class="step03">3. 充值成功</li>
					</ul>
				</div>
               <form id="formSubmit" name="formSubmit" method="GET" action="usercomfim">
					<div class="mob-list">
						<dl class="prepaid-phone">
						<dt>充值号码：</dt>
						<dd>
							<div class="mob-num">
								<input autocomplete="off" maxlength="13" value="" id="mobile" class="ipt-num" name="mobile" type="text">
								<!--end btn-hist-->
							<p>湖南移动</p>
							</div><!--end mob-num-->
                            
						</dd>
						</dl>
						<dl>
						<dt>充值面额：</dt>
						<dd>
						<div class="parvalue">
							<span show="29.4" onClick="setamt('30','29.4')">30元</span>
							<span show="49" onClick="setamt('50','49.4')">50元</span>
							<span class="cur" show="98-99.9" onClick="setamt('100','98.9')">100元</span>
							<span show="294" onClick="setamt('300','294.4')">300元</span>
							<span show="490" onClick="setamt('500','490.4')">500元</span>
                            <input value="100" name="face" id="face" type="hidden">
                            <input value="98.9" name="amt" id="amt" type="hidden">
							<input value="KKDBRLC7GF7Q" name="order_id" id="order_id" type="hidden">
						</div>
						</dd>
						</dl>
						<dl>
						<dt style="padding-top:8px;">优惠价格：</dt>
						<dd id="priceShow"><span class="font-price">¥98-99.95</span></dd>
						</dl>
						<dl>
						<dt></dt>
						<dd>
						  <button type="submit" class="btn-pay" id="btnpay">立即充值</button>
                          </dd>
						</dl>
					</div>
					  </form>
            </div>
            <!--end-->

</body>
</html>
