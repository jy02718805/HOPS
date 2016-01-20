	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = { "Y" : "", "N" : "" };
			showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		function start(tree,m1id,m1name,m2id,m2name,type){
			//type 0:添加页面    1:编辑页面
 			var zNodes=eval('([' + tree + '])');
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
			if(type=="0"){
				
				//目前只做本级返佣，其他级别暂不开放，开放只需将下列两行注释即可
				m1id=0;
				m2id=0;
				//目前只做本级返佣，其他级别暂不开放，开放只需将上面两行注释即可
				
				if(m1id=="0")
				{
					$("#merchantRebate1").attr('disabled','disabled');
	    			$("#merchantRebate1 input").attr('disabled','disabled');
	    		}else{
	    			$("#merchantRebate1").removeAttr('disabled');
	    			$("#merchantRebate1 input").removeAttr('disabled');
	    			$("#merchantId1").val(m1id);
	    			document.getElementById("merchantName1").innerHTML=m1name;
		        	$("#merchantRebate1").attr('style','display:block');
		        	$("#merchantRebate2").attr('style','display:none');
	    		}
				
				if(m2id=="0"){
					$("#merchantRebate2").attr('disabled','disabled');
	    			$("#merchantRebate2 input").attr('disabled','disabled');
	    		}else{
	    			$("#merchantRebate2").removeAttr('disabled');
	    			$("#merchantRebate2 input").removeAttr('disabled');
	    			$("#merchantId2").val(m2id);
	    			document.getElementById("merchantName2").innerHTML=m2name;
		        	$("#merchantRebate1").attr('style','display:block');
		        	$("#merchantRebate2").attr('style','display:block');
				}
				document.getElementById("merchantName0").innerHTML=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].text;
			}
		}
		function changeVolumeLow(trid,type)
		{
			var tableName="merchantRebateDiscount"+type;
	    	var tbl=$("#"+tableName);
		    var trlist=tbl.find("tr");
		    var tr=null;
			var afterTR=null;
			for(var i=1;i<trlist.length;i++){
				var trtemp=$(trlist[i]);
				var afterlength=i+1;
				if(trid==trtemp.attr("id")){
					tr=trtemp;
					if(afterlength<trlist.length){
						afterTR=$(trlist[afterlength]);
					}
					break;
				}
		    }
			var lowName="tradingVolumeLow"+type;
	    	var highName="tradingVolumeHigh"+type;
	        var highValue =tr.find("INPUT[id='"+highName+"']").val();
	        if(highValue!="无穷")	{
	        	if(afterTR!=null){
	    			afterTR.find("INPUT[id='"+lowName+"']").val(highValue);
	    		}
	        }
		}
		
    
    function addMerchantRebate(trid,type){
    	var tableName="merchantRebateDiscount"+type;
    	var tbl=$("#"+tableName);
	    var trlist=tbl.find("tr");
		var _len = $("#"+tableName+" tr").length;
	    var tr=null;
		var afterTR=null;
		for(var i=1;i<trlist.length;i++){
			var trtemp=$(trlist[i]);
			var afterlength=i+1;
			if(trid==trtemp.attr("id")){
				tr=trtemp;
				if(afterlength<trlist.length){
					afterTR=$(trlist[afterlength]);
				}
				break;
			}
	    }
	    var lowName="tradingVolumeLow"+type;
    	var highName="tradingVolumeHigh"+type;
    	var lowValue =tr.find("INPUT[id='"+lowName+"']").val();
        var highValue =tr.find("INPUT[id='"+highName+"']").val();
        if(highValue=="无穷")	{
        	tr.find("INPUT[id='"+highName+"']").val(lowValue);
        	highValue=lowValue;
        }
        var newLowValue=highValue;
        var newHighValue=null;
		if(afterTR!=null){
			newHighValue =afterTR.find("INPUT[id='"+lowName+"']").val();
		}
		if(newHighValue==null){
			newHighValue="无穷";
		}
			
		var htm="<tr id='"+_len+"'><td><input type='text' id='tradingVolumeLow"+type+"' name='tradingVolumeLow"+type+"' class='text' maxlength='200' value='"+newLowValue+"' disabled='disabled'/></td><td><input type='text' id='tradingVolumeHigh"+type+"' name='tradingVolumeHigh"+type+"' class='text' maxlength='200' value='"+newHighValue+"' onmouseout='changeVolumeLow("+_len+","+type+")' /></td><td><input type='text' id='discount"+type+"' name='discount"+type+"' class='text' maxlength='200' value='0'/></td><td><input type='button' class='button' id='add"+type+"' name='add"+type+"' value='添加' onclick='addMerchantRebate("+_len+","+type+")' /><a onclick='deleteMerchantRebate("+_len+","+type+")'>[删除]</a></td>";
		
		$("#"+trid).after(htm);
	}
	
	function deleteMerchantRebate(index,type){
    	var tableName="merchantRebateDiscount"+type;
		var _len = $("#"+tableName+" tr").length;
		if(_len<=2){
			alert("请保留至少一条有效数据，谢谢。");
			return true;
		}
    	var tbl=$("#"+tableName);
	    var trlist=tbl.find("tr");
	    var tr=$(trlist[_len-2]);
    	var highName="tradingVolumeHigh"+type;
        var highValue =tr.find("INPUT[id='"+highName+"']").val("无穷");
		
		$("#"+tableName+" tr[id='"+index+"']").remove();
	}
	
	function showMerchantRebate(type,index){
		var tableName="merchantRebateDiscount"+index;
		var addName="add"+index;
    	if(type==0)
    	{
    		$("#"+tableName).removeAttr('disabled');
    		$("#"+tableName+" input").removeAttr('disabled');
    		$("#"+addName).removeAttr('disabled');
    		$("#tradingVolumeLow"+type).attr('disabled','disabled');
    		$("#addRebateRule").removeAttr('disabled');
    	}else{
    		$("#"+tableName).attr('disabled','disabled');
    		$("#"+tableName+" input").attr('disabled','disabled');
    		$("#"+addName).attr('disabled','disabled');
    		$("#addRebateRule").attr('disabled','disabled');
    	}
	}
	
	function showMerchantRebateType(type,index){
		var divName="rebateDiscount"+index;
    	if(type==0)
    	{
    		document.getElementById(divName).innerHTML='定比返佣(比例为：0~1之间，单位：%)';
    	}else{
    		document.getElementById(divName).innerHTML='定额返佣';
    	}
	}
    
	function getParentMerchant(merchantId)
	{
		if(merchantId!=0)
		{
	    $.ajax({
	        type: "post",
	        data: null,
	        url: "getParentMerchant?merchantId="+merchantId,
			async: false,
	        success: function (data) {
	        	var list=data.split("@");
	        	var tree=list[1]; 
	        	var merchantLevel=list[2];
		        var parentMerchantlist=list[0].split("|");
	        	var m1id="0";
	        	var m1name="";
	        	var m2id="0";
	        	var m2name="";
		        var i=0;
		        var length=parentMerchantlist.length-1;
		        if(length!=0)
		        {
			        while(i<length)
			        {
			        	var parentMerchant=parentMerchantlist[i].split("*");
			        	i++;
			        	if(parentMerchant[2]=="Zero"){
			        		m1id=parentMerchant[0];
			        		m1name=parentMerchant[1];
			        	}else if(parentMerchant[2]=="One"){
			        		m2id=parentMerchant[0];
			        		m2name=parentMerchant[1];
			        	}
			        }
		        }else{
	            alert("此商户暂无父级,该商户编号:"+merchantId);
		        }
		        start(tree,m1id,m1name,m2id,m2name,"0");
		        if(merchantLevel=="Zero"){
		        	document.getElementById("merchantlevel").innerHTML="一级商户";
		        	$("#merchantRebate1").attr('style','display:none');
		        	$("#merchantRebate2").attr('style','display:none');
	        	}else if(merchantLevel=="One"){
		        	document.getElementById("merchantlevel").innerHTML="二级商户";
		        	$("#merchantRebate1").attr('style','display:block');
		        	$("#merchantRebate2").attr('style','display:none');
	        	}else if(merchantLevel=="Two"){
		        	document.getElementById("merchantlevel").innerHTML="三级商户";
		        	$("#merchantRebate1").attr('style','display:block');
		        	$("#merchantRebate2").attr('style','display:block');
	        	}else{
		        }
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
		}else{
			document.getElementById("merchantlevel").innerHTML="";
			document.getElementById("merchantName0").innerHTML="";
        	$("#merchantRebate1").attr('style','display:none');
        	$("#merchantRebate2").attr('style','display:none');
			start("","0","","0","","0");
		}
	}
    
    function getMerchant(){
    	var merchantName=document.getElementById("merchantName").value;
	    $.ajax({
	        type: "post",
	        data: null,
	        url: "getMerchant?merchantName="+merchantName,
			async: false,
	        success: function (data) {
		        var merchantlist=data.split("|");
		        document.getElementById("merchantId").options.length=0; 
		        document.getElementById("merchantId").options.add(new Option('--请选择--','0'));
		        var i=0;
		        var length=merchantlist.length-1;
		        if(length!=0)
		        {
			        while(i<length)
			        {
			        	var merchant=merchantlist[i].split("*");
			        	document.getElementById("merchantId").options.add(new Option(merchant[1],merchant[0])); 
			        	i++;
			        }
					$("#merchantId").attr('style','display:block');
		        }else{
	            alert("暂无此商户:"+merchantName);
		        }
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
}
    
    function addRebateRule(){
    var merchantId=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
    if(merchantId==null||merchantId==0){
    	alert('返佣商户不能为空');
    }else{
	    var productIds = "";
    	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    	var change = treeObj.getChangeCheckedNodes(true);
    	if(change.length>0){
			var nodes = treeObj.getCheckedNodes();
				for(var i=0;i<nodes.length;i++){ 
					var node =nodes[i];
					productIds = productIds + node.id+ "|";
				}
				productIds = productIds.substring(0, productIds.length-1);
			var rebateTimeType=document.getElementById("rebateTimeType").options[document.getElementById("rebateTimeType").selectedIndex].value;
		    
		    var status0=getRebateStatus('0');
		    var type0=getRebateType('0');
		    var discount0=getRebateDiscountStr('0');
		    var merchant0=merchantId;

		    var discountmsg0="";
		    if(discount0[0]=="true"){
		    	discountmsg0=discount0[1];
		    }else{
		    	alert(discount0[1]);
		    	return false;
		    }
		    
		    var status1=getRebateStatus('1');
		    var type1=getRebateType('1');
		    var discount1=getRebateDiscountStr('1');
		    var merchant1=$("#merchantId1").val();

		    var discountmsg1="";
		    if(discount1[0]=="true"){
		    	discountmsg1=discount1[1];
		    }else{
		    	alert(discount1[1]);
		    	return false;
		    }
		    var status2=getRebateStatus('2');
		    var type2=getRebateType('2');
		    var discount2=getRebateDiscountStr('2');
		    var merchant2=$("#merchantId2").val();

		    var discountmsg2="";
		    if(discount2[0]=="true"){
		    	discountmsg2=discount2[1];
		    }else{
		    	alert(discount2[1]);
		    	return false;
		    }
		    
		    if(productIds!=null&&productIds!=""){
			    var data="[{'rebateMerchantId':"+merchant0+",'rebateStatus':"+status0+",'rebateType':"+type0+",'rebateDiscounts':"+discountmsg0+"},{'rebateMerchantId':"+merchant1+",'rebateStatus':"+status1+",'rebateType':"+type1+",'rebateDiscounts':"+discountmsg1+"},{'rebateMerchantId':"+merchant2+",'rebateStatus':"+status2+",'rebateType':"+type2+",'rebateDiscounts':"+discountmsg2+"}]";
			    $.ajax({
			        type: "post",
			        data: null,
			        url: "addRebateRule?merchantId="+merchantId+"&productIds="+productIds+"&rebateTimeType="+rebateTimeType+"&data="+data,
					async: false,
			        success: function (data) {
				        if(data=="true")
				        {
				                alert('操作成功');
				        }else{
				                alert('操作失败');
				        }
		                self.location.href='list';
			        },
			        error: function () {
			            alert("操作失败，请重试");
			        }
			    });
		    } else{
	            alert("请选择产品信息！");
		    }
    	} else{
            alert("此批产品已经配置或未选择产品，请勿随便操作！");
	    }
    }
}
    
     function getRebateStatus(type){
    	var statusName="status"+type;
    	var statusValue=$("input[name='"+statusName+"']:checked").val();
	    return statusValue;
    }
    
    function getRebateType(type){
    	var rebateTypeName="rebateType"+type;
    	var rebateTypeValue=$("input[name='"+rebateTypeName+"']:checked").val();
	    return rebateTypeValue;
    }
    
    function getRebateDiscountStr(type){
    	var tableName="merchantRebateDiscount"+type;
    	var lowName="tradingVolumeLow"+type;
    	var highName="tradingVolumeHigh"+type;
    	var discountName="discount"+type;
    	var tbl=$("#"+tableName);
	    var trlist=tbl.find("tr");
	    var rebateDiscountParams ="";
	    var backParams=[2];
	    backParams[0]="false";
		backParams[1]="输入参数异常，请检查";
	    for(var i=1;i<trlist.length;i++){
	        var tr=$(trlist[i]);
	        var tradingVolumeLow =tr.find("INPUT[id='"+lowName+"']").val();
	        var tradingVolumeHigh =tr.find("INPUT[id='"+highName+"']").val();
	        if(!checkNumber(tradingVolumeHigh)){
	        	if(checkChinese(tradingVolumeHigh)){
	        		if(tradingVolumeHigh=="无穷"){
	        			if(i==trlist.length-1){
	        				tradingVolumeHigh="999999999999999999";
	        			}else{
		        			backParams[0]="false";
		        			backParams[1]="无穷只能在最后一行输入";
		        		    return backParams;
	        			}
	    	        }
	        		else{
	        			backParams[0]="false";
	        			backParams[1]="中文请输入无穷";
	        		    return backParams;
	        		}
	        	}else{
	        		backParams[0]="false";
	        		backParams[1]="请输入正整数或无穷";
	        	    return backParams;
	        	}
	        }else{
		        if(tradingVolumeHigh<0){
	    			backParams[0]="false";
	    			backParams[1]="请输入正整数";
	    		    return backParams;
		        }
	        }
	        if(parseInt(tradingVolumeLow)>=parseInt(tradingVolumeHigh)){
    			backParams[0]="false";
    			backParams[1]="交易下限不能大于或等于交易上限";
    		    return backParams;
	        }
	        var discount =tr.find("INPUT[id='"+discountName+"']").val();
	        if(!(checkNumber(discount)||checkPoint(discount))){
	        	backParams[0]="false";
    			backParams[1]="请输入正确返佣比格式（小数保留1-4位,定额请输入正整数）";
    		    return backParams;
	        }
	        var rebateType=getRebateType(type);
	        if(rebateType=="0")
	        {
		        if(parseFloat(discount) > 1 || parseFloat(discount) < 0){
		        	backParams[0]="false";
	    			backParams[1]="返佣比例不能大于1,请输入正确返佣比（小数保留1-4位,定额请输入正整数）";
					return backParams;
				}
	        }
	       	rebateDiscountParams = rebateDiscountParams + tradingVolumeLow+"|"+tradingVolumeHigh+"|"+discount+"*";
	    }
	    rebateDiscountParams = rebateDiscountParams.substring(0, rebateDiscountParams.length-1);
	    backParams[0]="true";
		backParams[1]=rebateDiscountParams;
	    return backParams;
    }
    
    
    function editRebateRule(){
    	var productIds = "";
    	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes();
			for(var i=0;i<nodes.length;i++){ 
				var node =nodes[i];
				productIds = productIds + node.id+ "|";
			}
			productIds = productIds.substring(0, productIds.length-1);
    
        var rebateRuleId=document.getElementById("rebateRuleId").value;
    	var rebateTimeType=document.getElementById("rebateTimeType").options[document.getElementById("rebateTimeType").selectedIndex].value;
        var status=getRebateStatus('0');
        var rebateType=getRebateType('0');
        var discount=getRebateDiscountStr('0');

	    var discountmsg="";
	    if(discount[0]=="true"){
	    	discountmsg=discount[1];
	    }else{
	    	alert(discount[1]);
	    	return false;
	    }
        if(productIds!=null&&productIds!=""){
	       if(discountmsg!=null&&discountmsg!=""){
		        $.ajax({
		            type: "post",
		            data: null,
		            url: "editRebateRule?rebateRuleId="+rebateRuleId+"&rebateTimeType="+rebateTimeType+"&status="+status+"&rebateType="+rebateType+"&discount="+discountmsg+"&productIds="+productIds,
		    		async: false,
		            success: function (data) {
		    	        if(data=="true")
		    	        {
		    	                alert('操作成功');
		    	                self.location.href='list';
		    	        }else{
		    	                alert('操作失败');
		    	        }
		            },
		            error: function () {
		                alert("操作失败，请重试");
		            }
		        }); 
	       }else{
	    	   alert("请设置返佣规则！");
	       }
	    } else{
            alert("请选择产品信息！");
	    }
    }
    
    
    function checkForm()
    {
    	var $inputForm = $("#inputForm");
		// 表单验证     /^[0]+(\.\d{4})?$/
			$inputForm.validate({
				onsubmit:true,
				rules: {
						tradingVolumeLow0: {
								   	required: true,
									pattern: /^\d+$/,
									minlength: 0,
									maxlength: 20
									},
						discount0:{
							       	required:true,
									pattern: /^(\d+$)|([0,1]\.\d{1,4})/,
									minlength: 0,
									maxlength: 6
									},
						tradingVolumeHigh0:{
								    required:true,
								    minlength: 0,
									maxlength: 20
									},
						tradingVolumeLow1: {
								   	required: true,
									pattern: /^\d+$/,
									minlength: 0,
									maxlength: 20
									},
						discount1:{
							       	required:true,
									pattern: /^(\d+$)|([0,1]\.\d{1,4})/,
									minlength: 0,
									maxlength: 6
									},
						tradingVolumeHigh1:{
								    required:true,
								    minlength: 0,
									maxlength: 20
									},
						tradingVolumeLow2: {
								   	required: true,
									pattern: /^\d+$/,
									minlength: 0,
									maxlength: 20
									},
						discount2:{
							       	required:true,
									pattern: /^(\d+$)|([0,1]\.\d{1,4})/,
									minlength: 0,
									maxlength: 6
									},
						tradingVolumeHigh2:{
								    required:true,
								    minlength: 0,
									maxlength: 20
									},
					    messages: {
									mer_name: {
									pattern: "非法字符"
									}
								  }
				}
				
			});
    }
    
    function checkNumber(str){
    	var reg=/^\d+$/;
    	if(reg.test(str)){
    		return true;
    	}else{
    		return false;
    	}
    }

    function checkPoint(str){
    	var reg=/^(\d)*(\.\d{1,4})?$/;
    	if(reg.test(str)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    function checkChinese(str){
    	var reg=/.*[\u4e00-\u9fa5]+.*$/;
    	if(reg.test(str)){
    		return true;
    	}else{
    		return false;
    	}
    }
    function addRR(){
	    if($("#inputForm").valid()){ 
	    	addRebateRule();     	
	    }
    }

    function editRR(){
	    if($("#inputForm").valid()){ 
	    	editRebateRule();     	
	    }
    }
    
