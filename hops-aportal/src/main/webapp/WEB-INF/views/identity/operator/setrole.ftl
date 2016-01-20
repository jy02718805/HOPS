<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<TITLE>设置操作员角色</TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#assign base=request.contextPath>
<#setting number_format="#">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.tools.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.validate.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/input.js"></SCRIPT>
<script>
function json2str(o) {
            var arr = [];
            var fmt = function(s) {
                if (typeof s == 'object' && s != null) return json2str(s);
                 return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
              }
             for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));
             return '{' + arr.join(',') + '}';
          }
function addrole(oid){
    var r=document.getElementsByName("roleid"); 
    var result="";
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    }
    $.ajax({
        type: "GET",
        data: null,
        url: "setRoleDo?oid="+oid+"&roleid="+result+"&t="+ new Date().getTime(),
		async: false,
        success: function (data) {
           alert(data.message);
        },
        error: function (msg) {
            alert("操作失败，请重试:"+msg);
        }
    }); 
}

    
</script>
<body>
<div class="mg10"></div>
	<form id="inputForm" action="setRoleDo"  method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>用户名称:
				</th>
				<td>
					${(operator.operatorName)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>显示名称:
				</th>
				<td>
					${(operator.displayName)!""}	 
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>角色列表:
				</th>
				<td>
				<div id="divshelflist">
					<#list rolelist as list>						
						<input type="checkbox" name="roleid" value="${list.role.roleId}" <#if list.status=="0">checked</#if> /> ${list.role.roleName}
					</#list>	
				</div> 
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" class="button" value="确&nbsp;&nbsp;定" onclick="addrole('${operator.id}')"/>
					<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back();" />
				</td>
			</tr>
		</table>
	</form>
</body>

</HTML>
