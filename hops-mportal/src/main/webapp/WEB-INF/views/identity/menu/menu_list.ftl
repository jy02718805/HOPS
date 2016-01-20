<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<TITLE></TITLE>
<META content="text/html; charset=utf-8" http-equiv=content-type>
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
<script src="${base}/template/admin/js/yuecheng/menu/usercenter.js" type="text/javascript"></script>
<script type="text/javascript">
    //菜单js
    $(function () {
        initFunctionManage();
    });
</script>
<div class="functionSet">
    <form method="get">
    <div class="user_text">
        <ul id='functionTree' class="treeL1">
        	<#list menulist0 as list0>
        		<#if list0.status=="0">
	                <li class="clear"><span class="hand treeL1_li_span span_up" funcId="${list0.id}" parentId="0" enabled="0">${list0.menuName}</span>
	                    <ul class="treeL2">
	        				<#list menulist1 as list1>	        				
        						<#if (list1.status=="0" && list1.parentMenuId==list0.id)>
	                            <li class="clear"><span class="hand treeL2_li_span span_down" funcId="${list1.id}" parentId="${list0.id}" enabled="0">${list1.menuName}</span>
	                                <ul class="treeL3">
	        							<#list menulist2 as list2>
        									<#if (list2.status=="0" && list2.parentMenuId==list1.id)>
	                                        <li class="clear"><span class="treeL3_li_span hand span_down" funcId="${list2.id}" parentId="${list1.id}" level="end" enabled="0">${list2.menuName}</span>
	                                            
	                                            <ul class="treeL4 clearfix">
	        										<#list menulist3 as list3>
        												<#if (list3.status=="0" && list3.parentMenuId==list2.id)>
	                                                    <li><span class="hand treeL4_li_span span_up" funcId="${list3.id}" parentId="${list2.id}" level="end" enabled="0">${list3.menuName}</span></li>	                                                
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
    </form>
</div>
<div id="funcMenu" style="display: none">
    <ul>
        <li id="addSibling" class="funcMenu_bg funcMenu_bg_title1">
            添加[同级]</li>
        <li id="addChild" class="funcMenu_bg funcMenu_bg_title2">
            添加[子级]</li>
        <li id="edit" class="funcMenu_bg funcMenu_bg_title3">
            编辑</li>
        <li id="delete" class="funcMenu_bg funcMenu_bg_title4">
            删除</li>
        <li id="close" class="funcMenu_bg funcMenu_bg_title5">
            关闭</li>
    </ul>
</div>

</BODY></HTML>

