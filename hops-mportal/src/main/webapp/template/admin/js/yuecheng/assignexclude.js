function getCtiy(provinceid,type){
	if(provinceid=="0")
	{
		 document.getElementById("cityId").options.length=0; 
		 document.getElementById("cityId").options.add(new Option('--请选择--','0'));
	}else{
	    $.ajax({
	        type: "post",
	        data: null,
	        url: "addCity?id="+provinceid,
			async: false,
	        success: function (data) {
		        var citylist=data.split("|");
		        document.getElementById("cityId").options.length=0; 
		        document.getElementById("cityId").options.add(new Option('--请选择--','0'));
		        var i=0;
		        while(i<citylist.length-1)
		        {
		        	var city=citylist[i].split("*");
		        	document.getElementById("cityId").options.add(new Option(city[1],city[0])); 
		        	i++;
		        }
		        var mid=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
		        if(type=='1')
		        {
		        	getProduct(mid,'2');
		        }else if(type=='2')
	        	{
		        	mid=document.getElementById("reMerchantId").options[document.getElementById("reMerchantId").selectedIndex].value;
		        	getProductByMerchant(mid);
	        	}
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
    }
}


function getProduct(mid,selectType){
	mid=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
	if(mid=="0")
	{
		document.getElementById("productNo").options.length=0; 
	 	document.getElementById("productNo").options.add(new Option('--请选择--','0'));
	}else{

		var businessNo=document.getElementById("businessNo").options[document.getElementById("businessNo").selectedIndex].value;
		var type=document.getElementById("merchantType").options[document.getElementById("merchantType").selectedIndex].value;
		var carrier=document.getElementById("carrierId").options[document.getElementById("carrierId").selectedIndex].value;
		var province="";
		var city="";
		if(selectType=='1')
		{
			province=document.getElementById("provinceId").options[document.getElementById("provinceId").selectedIndex].value;
			city=document.getElementById("cityId").options[document.getElementById("cityId").selectedIndex].value;
		}	
		if(selectType=='2')
		{
			province=document.getElementById("provinceId").options[document.getElementById("provinceId").selectedIndex].value;
		}
	    $.ajax({
	        type: "post",
	        data: null,
	        url: "getProduct?businessNo="+businessNo+"&mid="+mid+"&type="+type+"&carrier="+carrier+"&province="+province+"&city="+city,
			async: false,
	        success: function (data) {
		        var productlist=data.split("|");
		        document.getElementById("productNo").options.length=0; 
		        var i=0;
				document.getElementById("productNo").options.add(new Option('--请选择--','0'));
		        while(i<productlist.length-1)
		        {
		        	var product=productlist[i].split("*");
		        	document.getElementById("productNo").options.add(new Option(product[1],product[0])); 
		        	i++;
		        }
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
	}
}


function getMerchant(pid){
	if(pid=="0"){
		document.getElementById("divshelflist").innerHTML="";
	}else{
		var mid=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
		var ruleType=document.getElementById("ruleType").options[document.getElementById("ruleType").selectedIndex].value;
		var type=document.getElementById("merchantType").options[document.getElementById("merchantType").selectedIndex].value;
		
	    $.ajax({
	        type: "post",
	        data: null,
	        url: "getMerchant?pid="+pid+"&mid="+mid+"&ruleType="+ruleType+"&type="+type,
			async: false,
	        success: function (data) {
		        var productlist=data.split("|");
		        var strhtml=""; 
		        var i=0;
		        while(i<productlist.length-1)
		        {
		        	if(i==0)
	        		{
		        		strhtml=strhtml+"<input type='checkbox' id='checkproduct' name='checkproduct' onclick='checkProducts(this);'/>全部<br/>";
	        		}
		        	var product=productlist[i].split("*");
		        	if(product[2]=="0")
		        	{
						strhtml=strhtml+"<div class='w150' style='float:left;'> <input type='checkbox' name='objectMerchantId' value='"+product[0]+"' checked onclick='unCheckProducts(this);'/>"+product[1]+"</div>";
					}else
					{
						strhtml=strhtml+"<div class='w150' style='float:left;'> <input type='checkbox' name='objectMerchantId' value='"+product[0]+"' onclick='unCheckProducts(this);'/>"+product[1]+"</div>";
					}
		        	i++;
		        }
		        document.getElementById("divshelflist").innerHTML=strhtml;
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
	}
}

function addAssignExcludeProduct(){
    var businessNo=document.getElementById("businessNo").options[document.getElementById("businessNo").selectedIndex].value;
    var merchantType=document.getElementById("merchantType").options[document.getElementById("merchantType").selectedIndex].value;
    var mid=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
	var remid=document.getElementById("reMerchantId").options[document.getElementById("reMerchantId").selectedIndex].value;
    var ruleType=document.getElementById("ruleType").options[document.getElementById("ruleType").selectedIndex].value;
    var carrier=document.getElementById("carrierId").options[document.getElementById("carrierId").selectedIndex].value;
	var province=document.getElementById("provinceId").options[document.getElementById("provinceId").selectedIndex].value;
	var city=document.getElementById("cityId").options[document.getElementById("cityId").selectedIndex].value;
	if(merchantType==null||merchantType=="")
	{
		alert("请选择商户类型！");
		return false;
	}
	if(mid=="0"||remid=="0")
	{
		alert("请选择指定排除的商户与被作用商户！");
		return false;
	}
    var r=document.getElementsByName("productNo");
    var result="";
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    }
   if(result!=""&&result!=null){
	  
	    $.ajax({
	        type: "post",
	        data: {businessNo:businessNo ,mid: mid ,type: merchantType ,remid: remid ,carrier: carrier ,province: province ,city:  city ,ruleType: ruleType ,productNo: result },
	        url: "saveAssignExcludeProduct",
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
        self.location.href='listAssignExclude';
        return false;
   }else{
	   alert("请选择指定排除的产品！");
 	   return false;
   }
}

function addAssignExclude(){
    var businessNo=document.getElementById("businessNo").options[document.getElementById("businessNo").selectedIndex].value;
    var merchantType=document.getElementById("merchantType").options[document.getElementById("merchantType").selectedIndex].value;
    var merchantId=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
    var productNo=document.getElementById("productNo").options[document.getElementById("productNo").selectedIndex].value;
    var ruleType=document.getElementById("ruleType").options[document.getElementById("ruleType").selectedIndex].value;
	if(merchantType==null||merchantType=="")
	{
		alert("请选择商户类型！");
		return false;
	}
    if(merchantId=="0")
	{
		alert("请选择商户！");
		return false;
	}
    var r=document.getElementsByName("objectMerchantId");
    var result="";
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    }
   if(result!=""&&result!=null){
	  
	    $.ajax({
	        type: "post",
	        data: {businessNo:businessNo ,merchantType: merchantType ,merchantId: merchantId ,productNo: productNo ,ruleType: ruleType ,objectMerchant: result },
	        url: "saveAssignExclude",
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
        self.location.href='listAssignExclude';
        return false;
   }else{
	   alert("请选择指定排除的商户！");
 	   return false;
   }
}


function editAssignExclude(){
    var businessNo=document.getElementById("businessNo").options[document.getElementById("businessNo").selectedIndex].value;
    var merchantType=document.getElementById("merchantType").options[document.getElementById("merchantType").selectedIndex].value;
    var merchantId=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
    var productNo=document.getElementById("productNo").options[document.getElementById("productNo").selectedIndex].value;
    var ruleType=document.getElementById("ruleType").options[document.getElementById("ruleType").selectedIndex].value;
    var r=document.getElementsByName("objectMerchantId"); 
    var result="";
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
         result=result+r[i].value+"|";
       }
    }
    if(result!=""&&result!=null){
	    $.ajax({
	        type: "post",
	        data: {businessNo:businessNo ,merchantType: merchantType ,merchantId: merchantId ,productNo: productNo ,ruleType: ruleType ,objectMerchant: result },
	        url: "assignExclude_edit",
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
		  
        self.location.href='listAssignExclude';
        return false;
    }else{
 	   alert("请选择指定排除的商户！");
 	   return false;
    }
}


function getProductByMerchant(remid){
	mid=document.getElementById("merchantId").options[document.getElementById("merchantId").selectedIndex].value;
	remid=document.getElementById("reMerchantId").options[document.getElementById("reMerchantId").selectedIndex].value;
	if(mid=="0"||remid=="0")
	{
		alert("请选择指定排除的商户与被作用商户！");
		return false;
	}else{
	    var businessNo=document.getElementById("businessNo").options[document.getElementById("businessNo").selectedIndex].value;
		var type=document.getElementById("merchantType").options[document.getElementById("merchantType").selectedIndex].value;
		var carrier=document.getElementById("carrierId").options[document.getElementById("carrierId").selectedIndex].value;
		var province=document.getElementById("provinceId").options[document.getElementById("provinceId").selectedIndex].value;
		var city=document.getElementById("cityId").options[document.getElementById("cityId").selectedIndex].value;
		var ruleType=document.getElementById("ruleType").options[document.getElementById("ruleType").selectedIndex].value;
	    $.ajax({
	        type: "post",
	        data: null,
	        url: "getProductByMerchant?businessNo="+businessNo+"&mid="+mid+"&type="+type+"&remid="+remid+"&carrier="+carrier+"&province="+province+"&city="+city+"&ruleType="+ruleType,
			async: false,
	        success: function (data) {
	        	
	        	var productlist=data.split("|");
		        var strhtml=""; 
		        var i=0;
		        while(i<productlist.length-1)
		        {
		        	if(i==0)
	        		{
		        		strhtml=strhtml+"<input type='checkbox' id='checkproduct' name='checkproduct' onclick='checkProducts(this);'/>全部<br/>";
	        		}
		        	var product=productlist[i].split("*");
		        	if(product[2]=="0")
		        	{
						strhtml=strhtml+"<div class='w150' style='float:left;'> <input type='checkbox' name='productNo' value='"+product[0]+"' checked='checked' onclick='unCheckProducts(this);'/>"+product[1]+"</div>";
					}else
					{
						strhtml=strhtml+"<div class='w150' style='float:left;'> <input type='checkbox' name='productNo' value='"+product[0]+"' onclick='unCheckProducts(this);'/>"+product[1]+"</div>";
					}
		        	i++;
		        }
		        document.getElementById("divshelflist").innerHTML=strhtml;
	        },
	        error: function () {
	            alert("操作失败，请重试");
	        }
	    }); 
	}
}
