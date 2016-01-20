
function checkOrder(orderNo)
{
	var html = $.ajax({ url: "${ctx}/transaction/checkOrder?orderNo="+orderNo, async: false }).responseText;
    var title = "人工审核操作";
    art.dialog({
        content: html,
        title: title,
        lock: true,
        ok: false
    });
}
		
function getStatus(status,type){
	if(status=="0")
	{
		if(type==0){
			$("#successTable").attr('style','display:block');
			$("#failTable").attr('style','display:none');
		}else{
			$("#successTable").attr('style','display:none');
			$("#failTable").attr('style','display:block');
		}
	}else if(status=="1")
	{
		$("#successTable").attr('style','display:none');
		$("#failTable").attr('style','display:block');
	} 
}

function checkDiscount(pid,discount)
{
	var dis=pid.split("&");
	document.getElementById("discountshow").innerHTML="供货商产品折扣为："+dis[1]+",代理商产品折扣为："+discount;
	if(parseFloat(dis[1]) >= parseFloat(discount)){
		alert("请注意，该产品折扣高于或等于代理商产品折扣！");
	}
}

function intiCloseOrder(){
	$("#successTable").attr('style','display:none');
	$("#failTable").attr('style','display:block');
}

function checkOrderDo(orderNo)
{
	if(confirm("是否确认？")){
		var phoneNo = document.getElementById("phoneNo").value.trim(); 
	    if(phoneNo!=""&&phoneNo!=null&&phoneNo.length<11){  
	       alert('手机号码长度不能少于11位~~~！');  
		     document.getElementById("phoneNo").focus(); 
	       return false;  
	    }
	    checkFailOrder(orderNo);
	}
}

function changePhoneNo(){
	$("#merchantId").val("");
	$("#merchantPid").empty();
    $("#merchantPid").show();
}

function checkSuccessOrder(orderNo)
{
	var successFee=document.getElementById("successFee").value;
	var userCode=document.getElementById("userCode").value;
	var url=document.getElementById("url").value;
	if(successFee==null||successFee.trim()=="")
	{
		alert("成功金额不能为空或空格！");
		return;
	}
//	if(type==0){
		window.location.href="checkSuccessOrder?orderNo="+orderNo+"&orderSuccessFee="+successFee+"&userCode="+userCode+"&url="+url;
//	}else{
//		var merchantPid=document.getElementById("merchantPid").options[document.getElementById("merchantPid").selectedIndex].value;
//		var phoneNo=document.getElementById("phoneNo").value;
//		window.location.href="checkSuccessOrder?orderNo="+orderNo+"&orderSuccessFee="+successFee+"&userCode="+userCode+"&productId="+merchantPid+"&phoneNo="+phoneNo+"&url="+url;
//	}
}

function checkFailOrder(orderNo)
{
	var orderStatus = document.getElementById("orderStatus").value;
	if(orderStatus == 3){
		var merchantPid = $("#merchantPid").val();
		var phoneNo= $("#phoneNo").val();
		
		if(merchantPid == null){
			merchantPid = "";
		}
		
		if(merchantPid==null||merchantPid.trim()=="")
		{
			alert("请选择产品信息！");
			return;
		}
		
		if(phoneNo==null||phoneNo.trim()=="")
		{
			alert("手机号不能为空或空格！");
			return;
		}
		window.location.href="reOpenOrder?orderNo="+orderNo+"&productId="+merchantPid+"&phoneNo="+phoneNo;
	}else{
		var merchantPid=document.getElementById("merchantPid").options[document.getElementById("merchantPid").selectedIndex].value;
		var phoneNo=document.getElementById("phoneNo").value;
		var url=document.getElementById("url").value;
		if(merchantPid==null||merchantPid.trim()=="")
		{
			alert("请选择产品信息！");
			return;
		}
		if(phoneNo==null||phoneNo.trim()=="")
		{
			alert("手机号不能为空或空格！");
			return;
		}
		window.location.href="checkFailOrder?orderNo="+orderNo+"&productId="+merchantPid+"&phoneNo="+phoneNo+"&url="+url;
	}
	
}

function getProduct(mid,productFace){
	$("#merchantPid option:all").remove();
	 var phoneNum = $("#phoneNo").val();
	 if(phoneNum==null||phoneNum.trim()=="")
	 {
		var a = document.getElementById("merchantId");
		a.options[0].selected = true;
		alert("手机号不能为空或空格！");
		return;
	 }
	 $.ajax({
        type: "post",
        data: null,
        url: "getProducts?identityId="+mid+"&phoneNum="+phoneNum+"&productFace="+productFace,
		async: false,
        success: function (data) {
        	if(data!=null&&data!="")
        	{
		        var productlist=data.split("|");
		        document.getElementById("merchantPid").options.length=0; 
		    	document.getElementById("merchantPid").options.add(new Option('--请选择--',''));
		        var i=0;
		        while(i<productlist.length-1)
		        {
		        	var product=productlist[i].split("*");
		        	document.getElementById("merchantPid").options.add(new Option(product[1],product[0])); 
		        	i++;
		        }
	        }else{
	        	alert("该商户没有匹配的产品！"); 
	        	document.getElementById("merchantPid").options.length=0; 
		    	document.getElementById("merchantPid").options.add(new Option('--请选择--',''));
	        }
	        
        },
        error: function () {
            alert("操作失败，请重试");
        }
    }); 
}
