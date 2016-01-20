//查询
function queryinfo() {
	returnstr = '';
	$("#listForm").attr("action", "${base}/Batch/orderrequesthandlerlist")
			.submit();
}

// 审核数据
function auditdata() {
	var html = $.ajax({
		type : 'POST',
		url : "auditdatapage",
		data : '',
		dataType : 'text',
		async : false
	}).responseText;
	art.dialog({
		content : html,
		title : '审核数据',
		lock : true,
		ok : false
	});
}

// 批量导入
function addSupplyOrderList() {
	var html = $.ajax({
		url : "addsupplyorderlist",
		async : false
	}).responseText;
	art.dialog({
		content : html,
		title : '批量导入订单信息',
		lock : true,
		ok : false
	});
}

// 批量充值
function batchorderlistpage() {
	// ////alert(upfile);
	var html = $.ajax({
		type : 'POST',
		url : "batchorderlistpage",
		data : '',
		dataType : 'text',
		async : false
	}).responseText;
	art.dialog({
		content : html,
		title : '批量充值',
		lock : true,
		ok : false
	});
}

// 删除导入文件
function deleteupfile() {
	var upfile = document.getElementById("upfile").value;
	// /alert(upfile);
	if (upfile != "") {
		if (confirm("确认要删除   " + upfile + " 导入文件的数据吗？")) {
			self.location.href = 'deleteupfile?upfile=' + upfile;
		} else {
			alert('不要删除   '+upfile+' 导入文件的数据~~~！');
		}
	} else {
		alert('请选择需要删除的文件名~~~！');
	}
}

// 获取待审核数据条数
function getAuditnum(upfile) {
	$.ajax({
		dataType:'json',
		type : "post",
		data : {upfile:encodeURI(encodeURI(upfile)),status:0},
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		url : "getAuditnum",
		async : false,
		cache: false, 
		success : function(data) {
			$("#borhnum").val(data);
			$("#auditnum").val(data);
		}, 
		error : function() {
			alert("操作失败，请重试");
		}
	});
}
// 检查审核数据是否合法
function checkAudit() {
	var title = document.getElementById("upfile").options[document
			.getElementById("upfile").selectedIndex].value;
	if (title == '') {
		alert('请选择文件~~~！');
		return false;
	}
	var borhnum = $("#borhnum").val();
	var auditnum = $("#auditnum").val();
	if (borhnum == 0) {
		alert(title + "该文件暂无待审核订单。");
		return false;
	}
	if (auditnum == 0) {
		alert("审核条数不能0，请重新输。");
		return false;
	}
	if (parseInt(auditnum) > parseInt(borhnum)) {
		alert("审核条数输入错误，总条数：" + borhnum + "，请重新输入。");
		return false;
	}
}
// 获取待充值条数
function getRechargenum(upfile) {
	$.ajax({
		dataType:'json',
		type : "post",
		data : {upfile:encodeURI(encodeURI(upfile)),status:1},
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		url : "getAuditnum",
		async : false,
		cache: false, 
		success : function(data) {
			$("#allnum").val(data);
			$("#auditnum").val(data);
		},
		error : function() {
			alert("操作失败，请重试");
		}
	});
}
// 检查批充数据是否选择合法
function checkRecharge() {
	var title = document.getElementById("upfile").options[document
			.getElementById("upfile").selectedIndex].value;
	if (title == '') {
		alert('请选择文件~~~！');
		return false;
	}
	var merchant = document.getElementById("merchantid").options[document
			.getElementById("merchantid").selectedIndex].value;
	if (merchant == '') {
		alert('请选择商户~~~！');
		return false;
	}
	var allnum = $("#allnum").val();
	var auditnum = $("#auditnum").val();
	if (allnum == 0) {
		alert(title + "该文件暂无待充订单，请检查是否审核。");
		return false;
	}
	if (auditnum == 0) {
		alert("批充条数不能0，请重新输,。");
		return false;
	}
//	if (parseInt(auditnum) > 1000 && parseInt(auditnum) > parseInt(allnum)) {
//		alert("批充条数输入错误，总条数：" + allnum + ",最高条数限制是1000条，请重新输入。");
//		return false;
//	}
}