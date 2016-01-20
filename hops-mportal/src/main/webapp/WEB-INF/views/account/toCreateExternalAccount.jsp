<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<META content="text/html; charset=utf-8" http-equiv=content-type>
	<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<LINK rel=stylesheet type=text/css href="${ctx}/template/admin/css/common.css">
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/template/common/js/jquery.jslider.js"></script>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.tools.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/jquery.validate.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/common.js"></SCRIPT>
	<SCRIPT type=text/javascript src="${ctx}/template/admin/js/input.js"></SCRIPT>
	<title>供货商商户产品定价页面</title>
	</script>
	<style type="text/css">
		#range_discount {width:150px;margin-top: 10px;height: 10px;background-color: #FFFFE0;border: 1px solid #A9C9E2;position: relative;}             
		#range_quality {width:150px;margin-top: 10px;height: 10px;background-color: #FFFFE0;border: 1px solid #A9C9E2;position: relative;}             
		.ui-slider { position:relative; text-align:left;}
		.ui-slider .ui-slider-range {position:absolute; z-index:1; display:block; border:0; background:#f90}
		.ui-slider-horizontal {height:10px; }
		.ui-slider-horizontal .ui-slider-handle {top:14px; margin-left:0; }
		.ui-slider-horizontal .ui-slider-range {top:20px; height:4px; }
		.ui-slider-horizontal .ui-slider-range-min {left:0; }
		.ui-slider-horizontal .ui-slider-range-max {right:0; }
		.ui-slider .ui-slider-handle {width:10px;height: 15px;background-color: #E6E6FA;border: 1px solid #A5B6C8;top: -6px;display: block;
		cursor: pointer; position: absolute;}
	</style>
</head>

<body>
<div class="mg10"></div>
	<form id="inputForm" action="${ctx}/product/doSaveSupplyProductRelation" method="post" class="form-horizontal">
	<div class="mg10"></div>
		<table class="input">
			<tr>
				<th><span class="requiredField">*</span>账户备注:</th>
				<td>
					选择银行：
					<select name = "bank_select" id="bank_select" class="ipt input-large required">
						<option value="">请选择</option>
						<option value="中国工商银行">中国工商银行</option>
						<option value="中国农业银行">中国农业银行</option>
						<option value="中国建设银行">中国建设银行</option>
						<option value="中国银行">中国银行</option>
						<option value="招商银行">招商银行</option>
						<option value="交通银行">交通银行</option>
						<option value="中国浦发银行">中国浦发银行</option>
						<option value="广发银行">广发银行</option>
						<option value="民生银行">民生银行</option>
					</select>
					<br/>
					<br/>
					银行卡号：
					<input id="cardNo" name="cardNo" type="text" class='ipt w160'/>
			   </td>
			</tr>
			
			</tr>
				<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id="submit_btn" class="button" type="button" onclick="doCreateExternalAccount()" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form>
	<script>
		function doCreateExternalAccount(){
			var bank_select = $("#bank_select").val();
			if(bank_select.length <= 0){
				alert("请选择银行！");
				return;
			}
			var cardNo = $("#cardNo").val();
			if(cardNo.length <= 0){
				alert("请输入银行卡号！");
				return;
			}
			var rmk = bank_select + " cardNo:[" + cardNo +"]";
			
			
			$.ajax({
		        type: "post",
		        data: {rmk:rmk},
		        url: "${ctx}/account/doCreateExternalAccount",
				async: false,
		        success: function (data) {
			        if(data=="true")
			        {
			                alert('操作成功');
			        }else{
			                alert('操作失败');
			        }
		        },
		        error: function () {
		            alert("操作失败，请重试");
		        }
		    });
			//window.location.href="${ctx}/account/doCreateExternalAccount?rmk="+rmk;
		}
	</script>
	
</body>
</html>
