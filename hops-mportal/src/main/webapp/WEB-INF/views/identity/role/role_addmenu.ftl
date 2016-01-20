<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<#assign base=request.contextPath>
<#setting number_format="#">
<META name=copyright content=SHOP++>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/common.css">
<SCRIPT type=text/javascript src="${base}/template/admin/js/jquery.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/admin/js/common.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/template/common/js/jquery.pager.js"></SCRIPT>
<script src="${base}/template/admin/js/ArtDialog/artDialog.js?skin=chrome" type="text/javascript"></script>
<script src="${base}/template/admin/js/ArtDialog/plugins/iframeTools.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/jquery.serialize.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/jquery.validate.js" type="text/javascript"></script>
<SCRIPT type=text/javascript>
</SCRIPT>
<style type="text/css">
        body{background:#fff; color:#055586;}
    </style>
<BODY>
<body style="padding-top:10px;">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/user_layout.css">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/comm.css">
<script src="${base}/template/admin/js/yuecheng/menu/jquery.contextmenu.r2.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/yuecheng/menu/rolemenu.js" type="text/javascript"></script>
<script type="text/javascript">
function addmenu(rid){
    var r=document.getElementsByName("chkTop");  
    var result=""; 
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    } 
    r=document.getElementsByName("menuIds");  
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    } 
    r=document.getElementsByName("pageIds");
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    } 
    r=document.getElementsByName("functionIds");  
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    } 
    $.ajax({
        type: "post",
        data: null,
        url: "role_addmenu?rid="+rid+"&menuidstr="+result,
		async: false,
        success: function (data) {
        },
        error: function () {
            alert("操作失败，请重试");
        }
    }); 
                alert('操作成功');
                location.reload();
}

</script>
<div class="functionSet">   
<div class="mg10"></div>
    <form id="inputForm" method="post" action="role_addmenu">
    <div class="btn">
        <label style="color: Red;"></label>
        <input type="button" style="float:left;margin-left:200px;" class="button" value="确定" onclick="addmenu('${role.id}')"/>
        <input type="button" style="float:left;" class="button" value="返回" onclick="location.href='list'" /></div>
    <div class="user_text">
        <ul id='functionTree' class="treeL1">
        	<#list menulist0 as list0>
        		<#if list0.menu.status=="0">
	            <li class="clear">
	                <input type='checkbox' name='chkTop' value="${list0.menu.id}*Zero" <#if list0.status=="0">checked</#if>/>
	                <span class="hand treeL1_li_span">${list0.menu.menuName}</span> 
			            <ul class="treeL2">
							<#list menulist1 as list1>	        				
			        			<#if (list1.menu.status=="0" && list1.menu.parentMenuId==list0.menu.id)>
			                        <li class="clear">
			                            <input type='checkbox' name='menuIds' value="${list1.menu.id}*One" <#if list1.status=="0">checked</#if> />
			                            <span class="hand treeL2_li_span">${list1.menu.menuName}<img src="${base}/template/admin/images/triangle.gif"/ style="margin-left:5px;"></span>
			                            <ul class="treeL3">
											<#list menulist2 as list2>
			        							<#if (list2.menu.status=="0" && list2.menu.parentMenuId==list1.menu.id)>
				                                    <li class="clear">
				                                        <input type='checkbox' name='pageIds' value="${list2.menu.id}*Two" <#if list2.status=="0">checked</#if> />
				                                        <span class="treeL3_li_span hand">${list2.menu.menuName}</span>
				                                        <ul class="treeL4 clearfix">
					        								<#list menulist3 as list3>
				        										<#if (list3.menu.status=="0" && list3.menu.parentMenuId==list2.menu.id)>
				                                                <li>
				                                                    <input type='checkbox' name='functionIds' value="${list3.menu.id}*Three" <#if list3.status=="0">checked</#if> />
				                                                    <span class="hand treeL4_li_span">${list3.menu.menuName}</span> </li>  
				                                             	</#if>
				                							</#list>                 
				                                        </ul>
				                                    </li> 
			                                    </#if>
			                				</#list>                   
			                            </ul>
			                        </li>
								</#if>
							</#list>
			            </ul>
	            	</li>
            	</#if>
           	</#list>
        </ul>
    </div>
    <div style="clear:both;"></div>
    <div class="btn">
        <input type="button" style="float:left;margin-left:200px;"  class="button" value="确定" onclick="addmenu('${role.id}')"/>
        <input type="button" style="float:left;" class="button" value="返回" onclick="location.href='list'" /></div>
    </form>
     
</div>



</BODY></HTML>

