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
<script src="${base}/template/admin/js/chongzhi.js"></script>
<style>
</style>
<script>
</script>
</head>

<body style="overflow-y: hidden;">

<!--充值流程-->
<div class="credit-content">
  <div class="step credit-step2">
    <ul>
      <li class="step01">1. 填写充值信息<span></span></li>
      <li class="step02 cur">2. 确认订单<span></span></li>
      <li class="step03">3. 充值成功</li>
    </ul>
  </div>
  <form id="formSubmit" name="formSubmit" method="GET" action="userresult">
  <div class="mob-list" id="order_info">
    <div class="order">
      <dl>
        <dt>充值号码：</dt>
        <dd><strong class="prepaid-phone">136-4731-6383</strong> <span class="prepaid-phonetype">（湖南移动）</span></dd>
      </dl>
      <dl>
        <dt>订&nbsp;&nbsp;单&nbsp;&nbsp;号：</dt>
        <dd>M14114297912397</dd>
      </dl>
      <dl>
        <dt>充值面额：</dt>
        <dd id="priceShow">¥100</dd>
      </dl>
      <dl>
        <dt>支付面额：</dt>
        <dd id="priceShow"><span class="font-price">¥99.95</span></dd>
      </dl>
      <dl>
        <dt></dt>
        <dd>
          <button type="submit" class="btn-pay" id="btnpay" >确认并充值</button>
          <button type="button" class="btn-pay2" id="btnpay" onClick="window.location.href='userinput'">返回修改</button>
        </dd>
      </dl>
    </div>
  </div>
</form>
</div>
<!--end-->

</body>
</html>
