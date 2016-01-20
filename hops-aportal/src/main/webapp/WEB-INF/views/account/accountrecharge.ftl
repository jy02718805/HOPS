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
	$('.bankc').find('li').click(function () { 
	  $(this).click(function(){
			if($(this).hasClass('cur')){
				$('.bankc').find('li').removeClass('cur'); 
			}else{
				$('.bankc').find('li.cur').removeClass('cur'); 
				$(this).addClass('cur');
			}
		})
	})
	});
	
   </script>
</head>

<!--账户充值-->
<body style="overflow-y: hidden;">

<div class="credit-content">
  <div class="mob-list banks">
    <dl>
      <dt>充值金额：</dt>
      <dd>
        <input value="" id="jine" class="ipt-num" name="jine" type="text">
        <!--end btn-hist--> 
      </dd>
    </dl>
   

    <dl>
      <dt>选择银行：</dt>
      <dd>
        <div class="bank" id="con_one_1">
          <div class="bankc" id="all" style="height: auto;">
            <ul class="defaultBank" id="defaultBank">
              <li class="cur" >
                <label>
                  <input name="bank" value="ICBCB2C" type="radio">
                  <span></span>
                  <img src="${base}/template/admin/images/bank_zhifubao.jpg" height="22" width="117" alt="支付宝" title="支付宝"></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="ICBCB2C" type="radio">
                  <img src="${base}/template/admin/images/bank_caifutong.jpg" height="22" width="117" alt="财付通" title="财付通"></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="ICBCB2C" type="radio">
                  <img src="${base}/template/admin/images/bank_hb.jpg" height="22" width="117" alt="和包" title="和包"></label>
              </li>
            </ul>
            <div class="line"></div>
            <ul class="bankList">
              <li>
                <label>
                  <input name="bank" value="CCB" type="radio">
                  <img src="${base}/template/admin/images/bank_jianhang0021.jpg" height="22" width="117" alt="建设银行" title="建设银行"></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="ABC" type="radio">
                  <img src="${base}/template/admin/images/bank_nonghang0021.jpg" height="22" width="117" alt="中国农业银行" title="中国农业银行" ></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="CMB" type="radio">
                  <img src="${base}/template/admin/images/bank_zhaohang0021.jpg" height="22" width="117" alt="招商银行" title="招商银行" ></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="BOCB2C" type="radio">
                  <img src="${base}/template/admin/images/bank_zhonghang0021.jpg" height="22" width="117" alt="中国银行" title="中国银行"></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="COMM" type="radio">
                  <img src="${base}/template/admin/images/bank_jiaotong0021.jpg" height="22" width="117" alt="交通银行" title="交通银行"></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="PSBC-DEBIT" type="radio">
                  <img src="${base}/template/admin/images/bank_youzhengchuxu0021.jpg" height="22" width="117" alt="邮政储蓄银行" title="邮政储蓄银行"></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="CEBBANK" type="radio">
                  <img src="${base}/template/admin/images/bank_guandai0021.jpg" height="22" width="117" alt="光大银行" title="光大银行"></label>
              </li>
              <li>
                <label>
                  <input name="bank" value="CIB" type="radio">
                  <img src="${base}/template/admin/images/bank_xingye0021.jpg" height="22" width="117" alt="兴业银行" title="兴业银行"></label>
              </li>
            </ul>
            <div class="line"></div>
          </div>
          <form action="" method="post" id="confirm-pay">
            <input class="btn-pay" value="立即充值" id="button" name="button" type="button">
          </form>
        </div>
      </dd>
    </dl>

    

  </div>
</div>
<!--end--> 

</body>
</html>
