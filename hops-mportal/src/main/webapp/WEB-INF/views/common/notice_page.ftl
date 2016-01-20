<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>添加商户</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<#assign base=request.contextPath>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/error.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>

</script>
<body>
	<div class="wrap">
		<div class="error">
			<dl>
				<dt>${message}</dt>
				<dd>
				    <#if canback == true>
				    			<a href="javascript:;" onclick="window.history.back(); return false;">返回上一页</a>
					<#else>
						<#if next_url !=''>
								<a href="${base}/${(next_url)!''}">${(next_msg)!''}</a>
						</#if>
					</#if>
				</dd>
			</dl>
		</div>
	</div>
</body>


</HTML>
