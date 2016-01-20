<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0047)http://demo.shopxx.net/admin/setting/edit.jhtml -->
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD><META content="IE=7.0000" 
http-equiv="X-UA-Compatible">
<TITLE>系统设置 - Powered By SHOP++</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<META name=author content="">
<META name=copyright content="">
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>

<SCRIPT type=text/javascript>
$().ready(function() {
	
	var $inputForm = $("#inputForm");
	var $browserButton = $("input.browserButton");
	var $smtpFromMail = $("#smtpFromMail");
	var $smtpHost = $("#smtpHost");
	var $smtpPort = $("#smtpPort");
	var $smtpUsername = $("#smtpUsername");
	var $smtpPassword = $("#smtpPassword");
	var $toMailWrap = $("#toMailWrap");
	var $toMail = $("#toMail");
	var $mailTest = $("#mailTest");
	var $mailTestStatus = $("#mailTestStatus");
	
	
	$browserButton.browser();
	
	// 邮件测试
	$mailTest.click(function() {
		var $this = $(this);
		if ($this.val() == "邮件测试") {
			$this.val("发送邮件");
			$toMailWrap.show();
		} else {
			function valid(element) {
				return $inputForm.validate().element(element);
			}
			$.ajax({
				url: "mail_test.jhtml",
				type: "POST",
				data: {smtpFromMail: $smtpFromMail.val(), smtpHost: $smtpHost.val(), smtpPort: $smtpPort.val(), smtpUsername: $smtpUsername.val(), smtpPassword: $smtpPassword.val(), toMail: $toMail.val()},
				dataType: "json",
				cache: false,
				beforeSend: function() {
					if (valid($smtpFromMail) & valid($smtpHost) & valid($smtpPort) & valid($smtpUsername) & valid($toMail)) {
						$mailTestStatus.html('<span class="loadingIcon">&nbsp;<\/span>正在发送测试邮件，请稍后...');
						$this.prop("disabled", true);
					} else {
						return false;
					}
				},
				success: function(message) {
					$mailTestStatus.empty();
					$this.prop("disabled", false);
					$.message(message);
				}
			});
		}
	});
	
	$.validator.addMethod("compareLength", 
		function(value, element, param) {
			return this.optional(element) || $.trim(value) == "" || $.trim($(param).val()) == "" || parseFloat(value) >= parseFloat($(param).val());
		},
		"必须大于等于最小长度"
	);
	
	// 表单验证
	$inputForm.validate({
		rules: {
			siteName: "required",
			siteUrl: "required",
			logo: "required",
			email: "email",
			siteCloseMessage: "required",
			largeProductImageWidth: {
				required: true,
				integer: true,
				min: 1
			},
			largeProductImageHeight: {
				required: true,
				integer: true,
				min: 1
			},
			mediumProductImageWidth: {
				required: true,
				integer: true,
				min: 1
			},
			mediumProductImageHeight: {
				required: true,
				integer: true,
				min: 1
			},
			thumbnailProductImageWidth: {
				required: true,
				integer: true,
				min: 1
			},
			thumbnailProductImageHeight: {
				required: true,
				integer: true,
				min: 1
			},
			defaultLargeProductImage: "required",
			defaultMediumProductImage: "required",
			defaultThumbnailProductImage: "required",
			watermarkAlpha: {
				required: true,
				digits: true,
				max: 100
			},
			watermarkImageFile: {
				extension: ""
			},
			defaultMarketPriceScale: {
				required: true,
				min: 0,
				decimal: {
					integer: 3,
					fraction: 3
				}
			},
			usernameMinLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117
			},
			usernameMaxLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117,
				compareLength: "#usernameMinLength"
			},
			passwordMinLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117
			},
			passwordMaxLength: {
				required: true,
				integer: true,
				min: 1,
				max: 117,
				compareLength: "#passwordMinLength"
			},
			registerPoint: {
				required: true,
				integer: true,
				min: 0
			},
			registerAgreement: "required",
			accountLockCount: {
				required: true,
				integer: true,
				min: 1
			},
			accountLockTime: {
				required: true,
				digits: true
			},
			safeKeyExpiryTime: {
				required: true,
				digits: true
			},
			uploadMaxSize: {
				required: true,
				digits: true
			},
			imageUploadPath: "required",
			flashUploadPath: "required",
			mediaUploadPath: "required",
			fileUploadPath: "required",
			smtpFromMail: {
				required: true,
				email: true
			},
			smtpHost: "required",
			smtpPort: {
				required: true,
				digits: true
			},
			smtpUsername: "required",
			toMail: {
				required: true,
				email: true
			},
			currencySign: "required",
			currencyUnit: "required",
			stockAlertCount: {
				required: true,
				digits: true
			},
			defaultPointScale: {
				required: true,
				min: 0,
				decimal: {
					integer: 3,
					fraction: 3
				}
			},
			taxRate: {
				required: true,
				min: 0,
				decimal: {
					integer: 3,
					fraction: 3
				}
			},
			cookiePath: "required"
		}
	});
	
});
</SCRIPT>


<BODY>
<DIV class=path><A href="http://demo.shopxx.net/admin/common/index.jhtml">首页</A> » 系统设置 </DIV>
<FORM id=inputForm encType=multipart/form-data method=post action=update.jhtml>
<UL id=tab class=tab>
  <LI><INPUT value=基本设置 type=button> </LI>
  <LI><INPUT value=显示设置 type=button> </LI>
  <LI><INPUT value=注册与安全 type=button> </LI>
  <LI><INPUT value=邮件设置 type=button> </LI>
  <LI><INPUT value=其它设置 type=button> </LI></UL>
<TABLE class="input tabContent">
  <TBODY>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>网站名称: </TH>
    <TD><INPUT class=text value=SHOP++商城 maxLength=200 name=siteName> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>网站网址: </TH>
    <TD><INPUT class=text value=http://demo.shopxx.net maxLength=200 
      name=siteUrl> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>logo: </TH>
    <TD><SPAN class=fieldSet><INPUT class=text value=/upload/image/logo.gif 
      maxLength=200 name=logo> <INPUT class="button browserButton" value=选择文件 type=button> <A 
      href="http://demo.shopxx.net/upload/image/logo.gif" target=_blank>查看</A> 
      </SPAN></TD></TR>
  <TR>
    <TH>热门搜索: </TH>
    <TD><INPUT class=text title=多个关键字以(,)分隔 
      value=T恤,衬衫,西服,西裤,森马,七匹狼,梵希蔓,春夏新款,牛仔裤 maxLength=200 name=hotSearch> </TD></TR>
  <TR>
    <TH>联系地址: </TH>
    <TD><INPUT class=text value=湖南长沙经济技术开发区 maxLength=200 name=address> </TD></TR>
  <TR>
    <TH>联系电话: </TH>
    <TD><INPUT class=text value=400-8888888 maxLength=200 name=phone> </TD></TR>
  <TR>
    <TH>邮政编码: </TH>
    <TD><INPUT class=text value=400000 maxLength=200 name=zipCode> </TD></TR>
  <TR>
    <TH>E-mail: </TH>
    <TD><INPUT class=text value=service@shopxx.net maxLength=200 name=email> 
    </TD></TR>
  <TR>
    <TH>备案编号: </TH>
    <TD><INPUT class=text value=湘ICP备10000000号 maxLength=200 name=certtext> 
  </TD></TR>
  <TR>
    <TH>是否网站开启: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isSiteEnabled> <INPUT 
      value=false type=hidden name=_isSiteEnabled> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>网站关闭消息: </TH>
    <TD><TEXTAREA class=text name=siteCloseMessage>&lt;dl&gt;&lt;dt&gt;网站维护中，暂时无法访问！&lt;/dt&gt;&lt;dd&gt;本网站正在进行系统维护和技术升级，网站暂时无法访问，敬请谅解！&lt;/dd&gt;&lt;/dl&gt;</TEXTAREA> 
    </TD></TR></TBODY></TABLE>
<TABLE class="input tabContent">
  <TBODY>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>商品图片(大): </TH>
    <TD><SPAN class=fieldTitle>宽度:</SPAN> <INPUT style="WIDTH: 50px" 
      class=text title="单位: 像素" value=800 maxLength=9 
      name=largeProductImageWidth> <SPAN class=fieldTitle>高度:</SPAN> <INPUT 
      style="WIDTH: 50px" class=text title="单位: 像素" value=800 maxLength=9 
      name=largeProductImageHeight> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>商品图片(中): </TH>
    <TD><SPAN class=fieldTitle>宽度:</SPAN> <INPUT style="WIDTH: 50px" 
      class=text title="单位: 像素" value=300 maxLength=9 
      name=mediumProductImageWidth> <SPAN class=fieldTitle>高度:</SPAN> <INPUT 
      style="WIDTH: 50px" class=text title="单位: 像素" value=300 maxLength=9 
      name=mediumProductImageHeight> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>商品图片(缩略): </TH>
    <TD><SPAN class=fieldTitle>宽度:</SPAN> <INPUT style="WIDTH: 50px" 
      class=text title="单位: 像素" value=170 maxLength=9 
      name=thumbnailProductImageWidth> <SPAN class=fieldTitle>高度:</SPAN> <INPUT 
      style="WIDTH: 50px" class=text title="单位: 像素" value=170 maxLength=9 
      name=thumbnailProductImageHeight> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>默认商品图片(大): </TH>
    <TD><SPAN class=fieldSet><INPUT class=text 
      value=/upload/image/default_large.jpg maxLength=200 
      name=defaultLargeProductImage> <INPUT class="button browserButton" value=选择文件 type=button> <A 
      href="http://demo.shopxx.net/upload/image/default_large.jpg" 
      target=_blank>查看</A> </SPAN></TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>默认商品图片(中): </TH>
    <TD><SPAN class=fieldSet><INPUT class=text 
      value=/upload/image/default_medium.jpg maxLength=200 
      name=defaultMediumProductImage> <INPUT class="button browserButton" value=选择文件 type=button> <A 
      href="http://demo.shopxx.net/upload/image/default_medium.jpg" 
      target=_blank>查看</A> </SPAN></TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>默认商品图片(缩略): </TH>
    <TD><SPAN class=fieldSet><INPUT class=text 
      value=/upload/image/default_thumbnail.jpg maxLength=200 
      name=defaultThumbnailProductImage> <INPUT class="button browserButton" value=选择文件 type=button> <A 
      href="http://demo.shopxx.net/upload/image/default_thumbnail.jpg" 
      target=_blank>查看</A> </SPAN></TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>水印透明度: </TH>
    <TD><INPUT class=text title="取值范围: 0-100, 0代表完全透明" value=50 maxLength=9 
      name=watermarkAlpha> </TD></TR>
  <TR>
    <TH>水印图片: </TH>
    <TD><SPAN class=fieldSet><INPUT type=file name=watermarkImageFile> <A 
      href="http://demo.shopxx.net/upload/image/watermark.png" 
      target=_blank>查看</A> </SPAN></TD></TR>
  <TR>
    <TH>水印位置: </TH>
    <TD><SELECT name=watermarkPosition> <OPTION value=no>无</OPTION> <OPTION 
        value=topLeft>左上</OPTION> <OPTION value=topRight>右上</OPTION> <OPTION 
        value=center>居中</OPTION> <OPTION value=bottomLeft>左下</OPTION> <OPTION 
        selected value=bottomRight>右下</OPTION></SELECT> </TD></TR>
  <TR>
    <TH>价格精确位数: </TH>
    <TD><SELECT name=priceScale> <OPTION value=0>无小数位</OPTION> <OPTION 
        value=1>1位小数</OPTION> <OPTION selected value=2>2位小数</OPTION> <OPTION 
        value=3>3位小数</OPTION></SELECT> </TD></TR>
  <TR>
    <TH>价格精确方式: </TH>
    <TD><SELECT name=priceRoundType> <OPTION selected 
        value=roundHalfUp>四舍五入</OPTION> <OPTION value=roundUp>向上取整</OPTION> 
        <OPTION value=roundDown>向下取整</OPTION></SELECT> </TD></TR>
  <TR>
    <TH>是否前台显示市场价: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isShowMarketPrice> <INPUT 
      value=false type=hidden name=_isShowMarketPrice> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>默认市场价换算比例: </TH>
    <TD><INPUT class=text title=系统将根据该比例与销售价自动计算市场价 value=1.2 maxLength=7 
      name=defaultMarketPriceScale> </TD></TR></TBODY></TABLE>
<TABLE class="input tabContent">
  <TBODY>
  <TR>
    <TH>是否开放注册: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isRegisterEnabled> <INPUT 
      value=false type=hidden name=_isRegisterEnabled> </TD></TR>
  <TR>
    <TH>是否允许E-mail重复注册: </TH>
    <TD><INPUT value=true type=checkbox name=isDuplicateEmail> <INPUT 
      value=false type=hidden name=_isDuplicateEmail> </TD></TR>
  <TR>
    <TH>禁用用户名: </TH>
    <TD><INPUT class=text title=多个内容以(,)分隔 value=admin,管理员 maxLength=200 
      name=disabledUsername> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>用户名最小长度: </TH>
    <TD><INPUT id=usernameMinLength class=text value=2 maxLength=3 
      name=usernameMinLength> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>用户名最大长度: </TH>
    <TD><INPUT class=text value=20 maxLength=3 name=usernameMaxLength> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>密码最小长度: </TH>
    <TD><INPUT id=passwordMinLength class=text value=4 maxLength=3 
      name=passwordMinLength> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>密码最大长度: </TH>
    <TD><INPUT class=text value=20 maxLength=3 name=passwordMaxLength> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>注册初始积分: </TH>
    <TD><INPUT class=text value=0 maxLength=9 name=registerPoint> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>注册协议: </TH>
    <TD><TEXTAREA class=text name=registerAgreement>&lt;p&gt;尊敬的用户欢迎您注册成为本网站会员。请用户仔细阅读以下全部内容。如用户不同意本服务条款任意内容，请不要注册或使用本网站服务。如用户通过本网站注册程序，即表示用户与本网站已达成协议，自愿接受本服务条款的所有内容。此后，用户不得以未阅读本服务条款内容作任何形式的抗辩。&lt;/p&gt; &lt;p&gt;一、本站服务条款的确认和接纳&lt;br /&gt;本网站涉及的各项服务的所有权和运作权归本网站所有。本网站所提供的服务必须按照其发布的服务条款和操作规则严格执行。本服务条款的效力范围及于本网站的一切产品和服务，用户在享受本网站的任何服务时，应当受本服务条款的约束。&lt;/p&gt; &lt;p&gt;二、服务简介&lt;br /&gt;本网站运用自己的操作系统通过国际互联网络为用户提供各项服务。用户必须:  1. 提供设备，如个人电脑、手机或其他上网设备。 2. 个人上网和支付与此服务有关的费用。&lt;/p&gt; &lt;p&gt;三、用户在不得在本网站上发布下列违法信息&lt;br /&gt;1. 反对宪法所确定的基本原则的； 2. 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的； 3. 损害国家荣誉和利益的； 4. 煽动民族仇恨、民族歧视，破坏民族团结的； 5. 破坏国家宗教政策，宣扬邪教和封建迷信的； 6. 散布谣言，扰乱社会秩序，破坏社会稳定的； 7. 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的； 8. 侮辱或者诽谤他人，侵害他人合法权益的； 9. 含有法律、行政法规禁止的其他内容的。&lt;/p&gt; &lt;p&gt;四、有关个人资料&lt;br /&gt;用户同意:  1. 提供及时、详尽及准确的个人资料。 2. 同意接收来自本网站的信息。 3. 不断更新注册资料，符合及时、详尽准确的要求。所有原始键入的资料将引用为注册资料。 4. 本网站不公开用户的姓名、地址、电子邮箱和笔名。除以下情况外:  a) 用户授权本站透露这些信息。 b) 相应的法律及程序要求本站提供用户的个人资料。&lt;/p&gt; &lt;p&gt;五、服务条款的修改&lt;br /&gt;本网站有权在必要时修改服务条款，一旦条款及服务内容产生变动，本网站将会在重要页面上提示修改内容。如果不同意所改动的内容，用户可以主动取消获得的本网站信息服务。如果用户继续享用本网站信息服务，则视为接受服务条款的变动。&lt;/p&gt; &lt;p&gt;六、用户隐私制度&lt;br /&gt;尊重用户个人隐私是本网站的一项基本政策。所以，本网站一定不会在未经合法用户授权时公开、编辑或透露其注册资料及保存在本网站中的非公开内容，除非有法律许可要求或本网站在诚信的基础上认为透露这些信息在以下四种情况是必要的:  1. 遵守有关法律规定，遵从本网站合法服务程序。 2. 保持维护本网站的商标所有权。 3. 在紧急情况下竭力维护用户个人和社会大众的隐私安全。 4. 符合其他相关的要求。&lt;/p&gt; &lt;p&gt;七、用户的帐号、密码和安全性&lt;br /&gt;用户一旦注册成功，将获得一个密码和用户名。用户需谨慎合理的保存、使用用户名和密码。如果你不保管好自己的帐号和密码安全，将负全部责任。另外，每个用户都要对其帐户中的所有活动和事件负全责。你可随时根据指示改变你的密码。用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通告本网站。   八、 拒绝提供担保 用户明确同意信息服务的使用由用户个人承担风险。本网站不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保，但会在能力范围内，避免出错。&lt;/p&gt; &lt;p&gt;九、有限责任&lt;br /&gt;如因不可抗力或其它本站无法控制的原因使本站销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等本站会尽可能合理地协助处理善后事宜，并努力使客户免受经济损失，同时会尽量避免这种损害的发生。&lt;/p&gt; &lt;p&gt;十、用户信息的储存和限制&lt;br /&gt;本站有判定用户的行为是否符合国家法律法规规定及本站服务条款权利，如果用户违背本网站服务条款的规定，本网站有权中断对其提供服务的权利。&lt;/p&gt; &lt;p&gt;十一、用户管理&lt;br /&gt;用户单独承担发布内容的责任。用户对服务的使用是根据所有适用于本站的国家法律、地方法律和国际法律标准的。用户必须遵循:  1. 使用网络服务不作非法用途。 2. 不干扰或混乱网络服务。 3. 遵守所有使用网络服务的网络协议、规定、程序和惯例。 用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐性的、伤害性的、庸俗的，淫秽等信息资料。另外，用户也不能传输何教唆他人构成犯罪行为的资料；不能传输助长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法律的资料。未经许可而非法进入其它电脑系统是禁止的。 若用户的行为不符合以上提到的服务条款，本站将作出独立判断立即取消用户服务帐号。用户需对自己在网上的行为承担法律责任。用户若在本站上散布和传播反动、色情或其它违反国家法律的信息，本站的系统记录有可能作为用户违反法律的证据。&lt;/p&gt; &lt;p&gt;十二、通告&lt;br /&gt;所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。服务条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。&lt;/p&gt; &lt;p&gt;十三、信息内容的所有权&lt;br /&gt;本网站定义的信息内容包括: 文字、软件、声音、相片、录象、图表；在广告中全部内容；本网站为用户提供的其它信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在本网站和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。本站所有的文章版权归原文作者和本站共同所有，任何人需要转载本站的文章，必须征得原文作者或本站授权。&lt;/p&gt; &lt;p&gt;十四、法律&lt;br /&gt;本协议的订立、执行和解释及争议的解决均应适用中华人民共和国的法律。用户和本网站一致同意服从本网站所在地有管辖权的法院管辖。如发生本网站服务条款与中华人民共和国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它条款则依旧保持对用户的约束力。&lt;/p&gt;</TEXTAREA> 
    </TD></TR>
  <TR>
    <TH>是否允许E-mail登录: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isEmailLogin> <INPUT 
      value=false type=hidden name=_isEmailLogin> </TD></TR>
  <TR>
    <TH>验证码类型: </TH>
    <TD><LABEL><INPUT value=memberLogin CHECKED type=checkbox 
      name=captchaTypes>会员登录 </LABEL><LABEL><INPUT value=memberRegister CHECKED 
      type=checkbox name=captchaTypes>会员注册 </LABEL><LABEL><INPUT 
      value=adminLogin CHECKED type=checkbox name=captchaTypes>后台登录 
      </LABEL><LABEL><INPUT value=review CHECKED type=checkbox 
      name=captchaTypes>商品评论 </LABEL><LABEL><INPUT value=consultation CHECKED 
      type=checkbox name=captchaTypes>商品咨询 </LABEL><LABEL><INPUT 
      value=findPassword CHECKED type=checkbox name=captchaTypes>找回密码 
      </LABEL><LABEL><INPUT value=resetPassword CHECKED type=checkbox 
      name=captchaTypes>重置密码 </LABEL><LABEL><INPUT value=other CHECKED 
      type=checkbox name=captchaTypes>其它 </LABEL></TD></TR>
  <TR>
    <TH>账号锁定类型: </TH>
    <TD><LABEL><INPUT value=member CHECKED type=checkbox 
      name=accountLockTypes>会员 </LABEL><LABEL><INPUT value=admin CHECKED 
      type=checkbox name=accountLockTypes>管理员 </LABEL></TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>连续登录失败最大次数: </TH>
    <TD><INPUT class=text title=当连续登录失败次数超过设定值时，系统将自动锁定该账号 value=5 maxLength=9 
      name=accountLockCount> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>自动解锁时间: </TH>
    <TD><INPUT class=text title="账号锁定后，自动解除锁定的时间，单位: 分钟，0表示永久锁定" value=10 
      maxLength=9 name=accountLockTime> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>安全密匙有效时间: </TH>
    <TD><INPUT class=text title="找回密码时，安全密匙的有效时间，单位: 分钟，0表示永久有效" value=1440 
      maxLength=9 name=safeKeyExpiryTime> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>上传文件最大限制: </TH>
    <TD><INPUT class=text title="单位: MB，0表示无限制" value=10 maxLength=9 
      name=uploadMaxSize> </TD></TR>
  <TR>
    <TH>允许上传图片扩展名: </TH>
    <TD><INPUT class=text title=多个扩展名以(,)分隔，留空表示不允许上传 maxLength=200 
      name=uploadImageExtension> </TD></TR>
  <TR>
    <TH>允许上传Flash扩展名: </TH>
    <TD><INPUT class=text title=多个扩展名以(,)分隔，留空表示不允许上传 maxLength=200 
      name=uploadFlashExtension> </TD></TR>
  <TR>
    <TH>允许上传媒体扩展名: </TH>
    <TD><INPUT class=text title=多个扩展名以(,)分隔，留空表示不允许上传 maxLength=200 
      name=uploadMediaExtension> </TD></TR>
  <TR>
    <TH>允许上传文件扩展名: </TH>
    <TD><INPUT class=text title=多个扩展名以(,)分隔，留空表示不允许上传 maxLength=200 
      name=uploadFileExtension> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>图片上传路径: </TH>
    <TD><INPUT class=text  
      maxLength=200 name=imageUploadPath> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>Flash上传路径: </TH>
    <TD><INPUT class=text 
      maxLength=200 name=flashUploadPath> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>媒体上传路径: </TH>
    <TD><INPUT class=text 
      maxLength=200 name=mediaUploadPath> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>文件上传路径: </TH>
    <TD><INPUT class=text 
      maxLength=200 name=fileUploadPath> </TD></TR></TBODY></TABLE>
<TABLE class="input tabContent">
  <TBODY>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>发件人邮箱: </TH>
    <TD><INPUT id=smtpFromMail class=text value=test@shopxx.net maxLength=200 
      name=smtpFromMail> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>SMTP服务器地址: </TH>
    <TD><INPUT id=smtpHost class=text value=smtp.shopxx.net maxLength=200 
      name=smtpHost> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>SMTP服务器端口: </TH>
    <TD><INPUT id=smtpPort class=text title="默认端口: 25" value=25 maxLength=9 
      name=smtpPort> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>SMTP用户名: </TH>
    <TD><INPUT id=smtpUsername class=text value=test maxLength=200 
      name=smtpUsername> </TD></TR>
  <TR>
    <TH>SMTP密码: </TH>
    <TD><INPUT id=smtpPassword class=text title=留空则不进行密码修改 maxLength=200 
      type=password name=smtpPassword> </TD></TR>
  <TR>
    <TH>邮件测试: </TH>
    <TD><SPAN class=fieldSet><SPAN id=toMailWrap class=hidden>收件人邮箱: 
      <BR><INPUT id=toMail class="text ignore" maxLength=200 name=toMail> 
</SPAN><INPUT id=mailTest class=button value=邮件测试 type=button> <SPAN 
      id=mailTestStatus>&nbsp;</SPAN> </SPAN></TD></TR></TBODY></TABLE>
<TABLE class="input tabContent">
  <TBODY>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>货币符号: </TH>
    <TD><INPUT class=text value=￥ maxLength=200 name=currencySign> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>货币单位: </TH>
    <TD><INPUT class=text value=元 maxLength=200 name=currencyUnit> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>库存警告数: </TH>
    <TD><INPUT class=text value=5 maxLength=9 name=stockAlertCount> </TD></TR>
  <TR>
    <TH>库存分配时间点: </TH>
    <TD><SELECT name=stockAllocationTime> <OPTION selected 
        value=order>下订单</OPTION> <OPTION value=payment>订单支付</OPTION> <OPTION 
        value=ship>订单发货</OPTION></SELECT> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>默认积分换算比例: </TH>
    <TD><INPUT class=text title=系统将根据该比例与销售价自动计算赠送积分数 value=1 maxLength=7 
      name=defaultPointScale> </TD></TR>
  <TR>
    <TH>是否开启开发模式: </TH>
    <TD><LABEL title=开启该模式将关闭部分系统缓存，重启WEB服务器后生效><INPUT value=true 
      type=checkbox name=isDevelopmentEnabled> <INPUT value=false type=hidden 
      name=_isDevelopmentEnabled> </LABEL></TD></TR>
  <TR>
    <TH>是否开启评论: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isReviewEnabled> <INPUT 
      value=false type=hidden name=_isReviewEnabled> </TD></TR>
  <TR>
    <TH>是否审核评论: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isReviewCheck> <INPUT 
      value=false type=hidden name=_isReviewCheck> </TD></TR>
  <TR>
    <TH>评论权限: </TH>
    <TD><SELECT name=reviewAuthority> <OPTION value=anyone>任何访问者</OPTION> 
        <OPTION value=member>注册会员</OPTION> <OPTION selected 
        value=purchased>已购买会员</OPTION></SELECT> </TD></TR>
  <TR>
    <TH>是否开启咨询: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isConsultationEnabled> 
      <INPUT value=false type=hidden name=_isConsultationEnabled> </TD></TR>
  <TR>
    <TH>是否审核咨询: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isConsultationCheck> 
      <INPUT value=false type=hidden name=_isConsultationCheck> </TD></TR>
  <TR>
    <TH>咨询权限: </TH>
    <TD><SELECT name=consultationAuthority> <OPTION 
        value=anyone>任何访问者</OPTION> <OPTION selected 
      value=member>注册会员</OPTION></SELECT> </TD></TR>
  <TR>
    <TH>是否开启发票功能: </TH>
    <TD><INPUT value=true CHECKED type=checkbox name=isInvoiceEnabled> <INPUT 
      value=false type=hidden name=_isInvoiceEnabled> </TD></TR>
  <TR>
    <TH>是否开启含税价: </TH>
    <TD><INPUT title=启用后顾客将承担订单发票税金 value=true CHECKED type=checkbox 
      name=isTaxPriceEnabled> <INPUT value=false type=hidden 
      name=_isTaxPriceEnabled> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>税率: </TH>
    <TD><INPUT class=text value=0.06 maxLength=7 name=taxRate> </TD></TR>
  <TR>
    <TH><SPAN class=requiredField>*</SPAN>Cookie路径: </TH>
    <TD><INPUT class=text value=/ maxLength=200 name=cookiePath> </TD></TR>
  <TR>
    <TH>Cookie作用域: </TH>
    <TD><INPUT class=text maxLength=200 name=cookieDomain> </TD></TR>
  <TR>
    <TH>快递100授权KEY: </TH>
    <TD><INPUT class=text maxLength=200 name=kuaidi100Key> 
</TD></TR></TBODY></TABLE>
<TABLE class=input>
  <TBODY>
  <TR>
    <TH>&nbsp; </TH>
    <TD><INPUT class=button value=确&nbsp;&nbsp;定 type=submit> <INPUT class=button onClick="location.href='../common/index.jhtml'" value=返&nbsp;&nbsp;回 type=button> 
    </TD></TR></TBODY></TABLE></FORM></BODY>
</HTML>
