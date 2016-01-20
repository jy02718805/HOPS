<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理界面</title>
<#assign base=request.contextPath>
<#setting number_format="#">
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/manage.css" rel="stylesheet" type="text/css" />
</head>

<!--充值流程-->
<body style="overflow-y: hidden;">

<div class="credit-content">
  <div class="step credit-step3">
    <ul>
      <li class="step01">1. 填写充值信息<span></span></li>
      <li class="step02">2. 确认订单<span></span></li>
      <li class="step03 cur">3. 充值成功</li>
    </ul>
  </div>
  <div class="good">
    <div class="mob-list" id="order_info">
      <div class="order2">
        <dl>
          <dt><img src="${base}/template/admin/images/credit_ico2.png"></dt>
          <dd>
            <h3>充值成功！</h3>
            <p>您为 <strong class="red">13647316383</strong> 充值 <strong class="red">100元</strong> 话费，充值成功！</p>
            <p>订单号：M14114297912397 ，您可以在<a href="JavaScript:void(0);">历史订单</a>中查看。</p>
            <button type="button" class="btn-pay" id="btnpay" onClick="window.location.href='userinput';">继续缴费</button>
          </dd>
        </dl>
      </div>
    </div>
  </div>
</div>
<!--充值流程-->

</body>
</html>
