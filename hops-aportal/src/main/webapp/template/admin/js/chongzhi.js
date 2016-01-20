$(function () {

	$.extend({
		atrim : function (e) {
			return e.replace(/\s+/g, '');
		}
	});

	// 手机充值
	var $mobile = $('#mobile'),
	$btnhist = $('.btn-hist'),
	$histTip = $('#histTip');

	$btnhist.mouseover(function () {
		$(this).addClass("btn-hist-on");
		$histTip.show();
		if (Card.cookie('Q') != null) {
			$.ajax({
				url : '/mobile/getHistoryNo?tm='+new Date().getTime(),
				dataType : 'json',
				success : function (e) {
					if (e.status == 1) {
						var len = e.data.length,
						sheets = [],
						i = 0;
						if (len > 0) {
							for (; i < len; i++) {
								sheets.push('<li><strong>' + e.data[i].Mobile + '</strong><em>' + e.data[i].NickName.substring(0, 9) + '</em><div class="modify-form" style="display:none"><input type="text" class="ipt-modify"/><button class="btn-modify">保存</button></div><a href="#" class="modify" title="修改"></a><a href="#" class="del"  title="删除"></a></li>');
							}
						} else {
							sheets.push('<li><span style="font-size:12px;color:#999999;text-algin:center;">暂无历史记录(仅收录充值成功号码)</span></li>');
						}
						$('#hisUl').html(sheets.join(''));
					}
				}
			});
			
			
		} else {
			$('#hisUl').html('<span>历史号码收藏您登录时充值过的号码，请先<a href="#" id="loginHis">登录</a></span>');
		}

	});

	$btnhist.mouseleave(function () {
		$(this).removeClass("btn-hist-on");
		$histTip.hide();

	});

	$histTip.mouseover(function () {
		$btnhist.addClass("btn-hist-on");
		$(this).show();
	});

	$histTip.mouseleave(function () {
		$btnhist.removeClass("btn-hist-on");
		$(this).hide();
	});

	$('#hisUl').on('click', 'a.modify', function () {
		var _this = $(this),
		parent = _this.parent(),
		em = parent.find('em'),
		input = parent.find('input');
		parent.find('div.modify-form').show();
		parent.find('input').focus();
		em.hide();
		_this.hide();
	});
	
	$('#hisUl').on('click','strong',function(){
	    var _this = $(this);
        $mobile.val( _this.text());
		$mobile.keyup();
	});

	$('#hisUl').on('click', '.btn-modify', function () {
		var _this = $(this),
		parent = _this.parent().parent(),
		input = parent.find('input'),
		em = parent.find('em');

		if (parent.find('input').val().length > 0) {
			em.text(parent.find('input').val());
			parent.find('div.modify-form').hide();
			em.show();
			parent.find('a.modify').show();
			var data = {
				mobile : parent.find('strong').text(),
				nickName : input.val()
			}
			$.getScript("/mobile/updateNickName?mobile=" + data.mobile + "&nickName=" + encodeURIComponent(data.nickName));
		} else {
			parent.find('div.modify-form').hide();
			em.show();
			parent.find('a.modify').show();
		}
		return false;
	});

	$('#hisUl').on('click', 'a.del', function () {
		var _this = $(this),
		parent = _this.parent(),
		num = parent.find('strong').text();
		parent.remove();
		$.getScript("/mobile/removeHistoryNo?mobile=" + num);
	});
	
	$('#hisUl').on('click', '#loginHis', function () {
		$('.btn-login-pop').trigger('click');
	});
	

	$(".parvalue span").click(function () {
		$(".parvalue span").removeClass("cur");
		$(this).addClass("cur");
        $("#priceShow").html('<span class="font-price">'+ $(this).attr("show") +'</span>元');

	});
	var checkNull = 0;
	$mobile.blur(function () {
		var v = $.atrim(this.value);
		var $parent = $(this).parent();
		$('#mobile_confirm div.error').remove();
		if (checkNull) {
			if (v == "") {
				var errerMsg = "请输入11位手机号";
				$('<div class="mob-prompt error" >' + errerMsg + '</div>').insertAfter('#checkTip');
				$('#myarea').hide();
			}
			checkNull = 0;
		}
		if (v == "") {}
		else if (!Card.valid.rules.mobile(v)) {
			var errerMsg = "请输入11位手机号";
			$('<div class="mob-prompt error" >' + errerMsg + '</div>').insertAfter('#checkTip');
             $('#myarea').hide();
		} else {

			$mobile.val($('#checkMe').text());
		}

	});
	


	$mobile.focus(function () {
		this.value = $.atrim(this.value);
		$('#myarea').show();
		var $parent = $(this).parent();
		$parent.find('div.error').remove();
	});
     var submitFlag = 0;
	$('#btnpay').click(function () {
		checkNull = 1;
		$mobile.trigger('blur');
		var numError = $('form .error').length;

		if (numError) {
			return false;
		}else{
		if (submitFlag){
			return true;
			}
		var $form = $('#formSubmit');
		var data = {
			mobile : $.atrim($mobile.val()),
			cardprice : $('div.parvalue>span.cur').text()
		}
		Card.cookie('mobile', data.mobile);

		$.ajax({
			url : '/mobile/get_mobile_status',
			data : data,
			dataType : 'json',
			success : function (data) {
				if (data.status == 1) {
					$('#prodID').val(data.prodID);
					submitFlag = 1;
					$form.submit();
				} else {
					Card.alert(data.desc || '无法充值，请选择其他面额或稍后再试。给您带来不便，敬请谅解！');
				}
			}
		})

		return false;
		}
	});

	
	
	
	$mobile.on('keyup', function (e) {
		var v = $.atrim(this.value);
		if (v == Card.lastValue)
			return true;
		var splitV = [v.substring(0, 3), v.substring(3, 7), v.substring(7, 11)];
		$('#checkMe').html(splitV.join(' '));
		if (v.length == 11 && Card.valid.rules.mobile($.atrim(this.value))) {
			$mobile.val(splitV.join(' '));
			$.ajax({
				url : '/mobile/getmobileinfo',
				data : {
					mobile : v
				},
				dataType : 'json',
				success : function (data) {
					if (data.status == 1) {
						$('#mobile_confirm div.error').remove();
						$('#myarea').addClass('correct').show();
						$('#myarea').html(data.loc + (['移动', '联通', '电信'][data.telecomID * 1 - 1]) || '');
					}
				}
			})
		} else {
			$('#myarea').removeClass('correct').hide();
		    $('#myarea').show();
			$('#myarea').html('');
            if (v.length == 0) {
            	$('#mobile_confirm').hide();
            	$('.mob-list dl:eq(0)').addClass('prepaid-phone');
            } else {
            	$('#mobile_confirm').show();
            	$('.mob-list dl:eq(0)').removeClass('prepaid-phone');
        	}
		}
		Card.lastValue = v;
	});


    $(document).keyup(function(e){
     if(e.keyCode==13){
		$('#btnpay').trigger('click');
		return ;
		}
    })
	setTimeout(function () {
		$mobile.val(Card.cookie('mobile') || '').trigger('keyup').focus();
		if(Card.cookie('mobile')){
			$('#mobile_confirm').show();
        	$('.mob-list dl:eq(0)').removeClass('prepaid-phone');
		}
	}, 200);

	//confirm

	Card.confirmPay = function (order_id) {
		var tmpl = '<dl><dt><img src="/static/v2/images/ico_3.png"></dt><dd>请您在新打开的网上银行页面进行付款，付款完成前不要关闭该窗口。</dd></dl>';
		var btn = '<a href="#" class="btn-2 btna">已完成付款</a><a href="#" class="btn-3 btna">付款遇到问题</a><p style="text-align:left; padding-top: 10px; margin-right: -50px; position: relative;"><a href="#"  class="reSelect">返回重新选择银行</a></p>';

		//已完成付款
		var paySucess = function () {
			location.replace('/mobile/userSuccessConfirm?order_id=' + order_id);
		}
		//付款遇到问题
		var resend = function () {
			window.open('/Help/index');
		}
		var dialog = Card.confirm('360生活服务提示', tmpl, [paySucess, resend], btn, 400);
		dialog.hideCall = Card.reSelect;
	};

	Card.reSelect = function () {
		var data = {
			prodID : $('#prodID').text(),
			mobile : $('#mobileID').text().replace(/-/g, '')

		}
		var param = $.param(data);

		location.href = '/mobile/confirm?' + param;
		return false;
	}

	Card.updateCode = function () {
		$('#imgCode').attr('src', $('#imgCode').attr('src').replace(/t\=\-\d+$/g, '') + '&t=' + (-new Date()));
		return false;
	}

	//显示全部银行
	$('#alls').on('click', function () {
		$('#all').height('auto');
		$(this).parent().hide();
		return false;
	});

	//设置选中银行状态和值
	(function () {
		var bank_key = Card.cookie('bank_key') || 'ICBCB2C';
		var $radio = $(':radio[value="' + bank_key + '"]');
		$radio = $radio.length ? $radio : $('.bankList :radio').eq(0);
		$('.defaultBank').append($radio.parents('li'));
		$radio.prop({
			checked : true
		});
		$('#bank_key').val($radio.val());
	})();

	//选择银行
	$('input[type="radio"][name="bank"]').on('click', function () {
		$('#bank_key').val($(this).attr('value'));
		if ($(this).attr('value') == 'zfb') {}
		else {
			Card.cookie('bank_key', $(this).attr('value'));
		}

	});

	//兼容ie6，label点击选中
	$('.bankList').find('img').on('click', function () {
		$(this).prev('input').trigger('click');
	});

	//验证码
	Card.submitFlag = false;
	$('#check_code').on('keyup', function () {
		var self = $(this);
		var v = this.value;
		var mobile = $.atrim($('#mobile').val()).replace(/-/g, '');
		var orderid = $('#order_id').text();
		if (/\d{4}/.test(v)) {
			$.ajax({
				url : '/mobile/checkvercode',
				dataType : 'json',
				data : {
					check_code : v,
					mobile : mobile,
					orderid : orderid
				},
				success : function (data) {
					if (data.status == '1') {
						Card.submitFlag = true;
						self.siblings('.red').hide();
						self.siblings('.green').show();
					} else {
						Card.submitFlag = false;
						self.siblings('.green').hide();
						self.siblings('.red').show();
						Card.updateCode();
					}
				}

			})
		} else {
			self.siblings('.green,.red').hide();
		}
	});

	//提交表单
	$('#confirm-pay').on('submit', function () {
		//提交前没有通过验证码验证
		if ($('#check_code:visible').length && !Card.submitFlag) {
			Card.alert('请输入正确的验证码');
			return false;
		} else {
			Card.confirmPay($('#order_id').text());
			$(this).find('.btn-pay').css({
				'opacity' : 0.4
			})
			.on('click', function () {
				Card.alert('你已经提交过此订单，不能重复提交，请刷新页面后继续！');
				return false;
			});
		}
	});

	$(document).delegate('.reSelect', 'click', Card.reSelect);

	//更新验证码
	$(document).delegate('a[name=switchCheckCode]', 'click', Card.updateCode);
	/*
	$(':radio[value=SDB]').on('click', function () {
		window.open('http://www.bank.pingan.com');
	
		var dialogCon = new Card.ui.Dialog({
				title : '360生活服务提示',
				content : '<p>原深圳发展银行以合并成“平安银行”，付款请登录<a href="http://">www.bank.pingan.com</a>,原深圳发展银行以合并成“平安银行”，付款请登录原深圳发展银行以合并成“平安银行”，付款请登录</p><div class="pop-btn"><a href="#" class="btn-1 btna">确定</a></div>',
				opacity : 0.5,
				disdrag : true,
				holdon : true,
				noClose : false
			});
		dialogCon.show();
          
	});
 */
	$('#usersucce').click(function () {
		var mobile = $('#mobile').val();
		var prodID = $('#prodID').val();
		window.open('/mobile/confirm?prodID=' + prodID + '&mobile=' + mobile);
		return false;

	});
	
	

	
});