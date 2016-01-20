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
<link rel="stylesheet" href="${base}/template/common/css/demo.css" type="text/css">
<link rel="stylesheet" href="${base}/template/admin/css/zTreeStyle-2014_11_7.css" type="text/css">
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/template/common/js/ztree/jquery.popupSmallMenu.js"></script>
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/user_layout.css">
<LINK rel=stylesheet type=text/css href="${base}/template/admin/css/comm.css">
<script src="${base}/template/admin/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
<script src="${base}/template/admin/js/usercenter.js" type="text/javascript"></script>
<SCRIPT type="text/javascript">
	var setting = {
			check: {
				enable: true
			},
			data: {
				key: {
					checked: "isChecked",
					title:"title",
					name:"name"
				},
				simpleData: {
					enable: true,
					idKey :"key",
					pIdKey:"pkey"
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick,
				onNodeCreated:OnNodeCreated,
				onRightClick:OnRightClick

			}
		};


		var zNodes =[${tree}];
		//////alert("tree="+'${tree}');
		var log, className = "dark";
		function beforeClick(treeId, treeNode, clickFlag) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.nnnn );
			return (treeNode.click != false);
		}
		function onClick(event, treeId, treeNode, clickFlag) {
			////showLog("[ "+getTime()+" onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
		}		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds();
			return (h+":"+m+":"+s);
		}
		
		var ztree;
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			ztree = $.fn.zTree.getZTreeObj("treeDemo");
		});

		function OnRightClick(event, treeId, treeNode) {
			ztree.selectNode(treeNode);
			if(treeNode) {
				//console.log(treeNode.tId);
				//console.dir($('#'+treeNode.tId));

				$("#menu").popupSmallMenu({
					event : event,
					onClickItem  : function(item) {
						chuli(treeNode,item);
					}
				});
			}	 
		}

		//右键菜单触发
		function chuli(treeNode,item){
			//通过simpleData中定义参数得到值
			///alert("menu_list_tree.jsp----chuli---item="+item+" ; id="+treeNode.key+" ; pid="+treeNode.pkey);
			//添加[子级]=addChildren
			if(item=='addChildren'){
				window.location.replace("add?id="+treeNode.key);
			}
			//添加[同级]=add
			else if(item=='add'){
				window.location.replace("add?parentMenuId="+treeNode.pkey);
			}
			//编辑=edit
			else if(item=='edit'){
				window.location.replace("edit?id="+treeNode.key);
			}
			//删除=cut
			else if(item=='cut'){
				if(confirm("准备删除:["+treeNode.nnnn+"]?")){
					window.location.replace("deletemenuh?id="+treeNode.key);
				}
			}
			else alert("操作错误，请重新选择~~！");
		}
		
		function OnNodeCreated(event, treeId, treeNode) {
			
			//console.log(treeId);
			//console.dir(treeNode);
			//$(treeNode).contextMenu({x: 123, y: 123});

			//	console.dir($('#treeDemo_10'));
			
		}

		
	</SCRIPT>
<body>
<div class="content_wrap">
	<div class="zTreeDemoBackground left" style="padding-left:10px;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</div>
<div class="line_bar">
<div style="float:right;padding:10px;">
						<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="window.history.back(); return false;" />
			</div>

</div>
</body>
</HTML>

