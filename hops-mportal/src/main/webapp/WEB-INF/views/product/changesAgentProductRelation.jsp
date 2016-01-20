<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<META content="IE=7.0000" http-equiv="X-UA-Compatible">
</head>
<title>商户产品列表</title>
<META content="text/html; charset=utf-8" http-equiv=content-type>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/template/common/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript"
	src="${ctx}/template/common/js/provinceCity.js"></script>
<LINK rel=stylesheet type=text/css
	href="${ctx}/template/admin/css/common.css">
<style>
#pager ul.pages {
	display: block;
	border: none;
	text-transform: uppercase;
	font-size: 10px;
	margin: 10px 0 50px;
	padding: 0;
}

#pager ul.pages li {
	list-style: none;
	float: left;
	border: 1px solid #ccc;
	text-decoration: none;
	margin: 0 5px 0 0;
	padding: 5px;
}

#pager ul.pages li:hover {
	border: 1px solid #003f7e;
}

#pager ul.pages li.pgEmpty {
	border: 1px solid #eee;
	color: #eee;
}

#pager ul.pages li.pgCurrent {
	border: 1px solid #003f7e;
	color: #000;
	font-weight: 700;
	background-color: #eee;
}
</style>
<body>
	<form id="listForm"
		action="${ctx}/product/changesAgentProductRelation">
		<input id='page' type='hidden' name='page' />
		<input id='pageSize' type='hidden' name='pageSize' value='${pageSize}'/> 
		<div class="line_bar">
			代理商商户:<select name="identityId" id="identityId" class="select w120">
				<option value="-1">请选择</option>
				<c:forEach items="${downMerchants}" var="merchant">
					<c:choose>
					<c:when test="${productRelation.identityId==merchant.id}">
			             	<option value="${merchant.id}" selected="selected">${merchant.merchantName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${merchant.id}">${merchant.merchantName}</option>
			        </c:otherwise>
			    </c:choose>
				</c:forEach>
			</select>
			运营商 :<select name="carrierInfo" id="carrierInfo" class="select w80">
				<option value="-1">请选择</option>
				<c:forEach items="${carrierInfos}" var="carrierInfo">
					<c:choose>
							<c:when test="${productRelation.carrierInfo==carrierInfo.carrierNo}">
					             	<option value="${carrierInfo.carrierNo}" selected="selected">${carrierInfo.carrierName}</option>
					        </c:when>
					        <c:otherwise>
					              	<option value="${carrierInfo.carrierNo}">${carrierInfo.carrierName}</option>
					        </c:otherwise>
					    </c:choose>
				</c:forEach>
			</select> 
			省份:<select name="province" id="province" class="select w80">
				<option value="-1">请选择</option>
				<c:forEach items="${provinces}" var="province">
					<c:choose>
					<c:when test="${province.provinceId==productRelation.province}">
			             	<option value="${province.provinceId}" selected="selected">${province.provinceName}</option>
			        </c:when>
			        <c:otherwise>
			              	<option value="${province.provinceId}">${province.provinceName}</option>
			        </c:otherwise>
			    </c:choose>
				</c:forEach>
			</select>
			业务类型: <select id='businessType' name='businessType'  class="select w80" >
    <option value='' <c:if test='${businessType==""}'>selected='selected'</c:if>>请选择</option>
    <option value='0' <c:if test='${businessType=="0"}'>selected='selected'</c:if>>话费</option>
    <option value='1' <c:if test='${businessType=="1"}'>selected='selected'</c:if>>流量</option>	
    </select>
			 面 值:<input type="text" id="parValue" name="parValue" value="${productRelation.parValue}" class="ipt w80" onkeyup="value=value.replace(/[^\d]/g,'')" />
			 
			 折扣范围:<input type="text" id="discount" name="discount" value="${productRelation.discount}" class="ipt w80" onkeyup="value=value.replace(/[^\d.-]/g,'')"/>-
     		 <input type="text" id="discount2" name="discount2" value="${productRelation.discount2}" class="ipt w80" onkeyup="value=value.replace(/[^\d.-]/g,'')"/>
			 
			 
			<div style="float: right;">
				<shiro:user>
					<input type="button" class="button" value="查询"
						onclick="queryUpProductRelation()" />
				</shiro:user>
				<shiro:hasPermission name="agentProduct:editlist_execute">
					<input type="button" class="button" id="changes_discount"
						value="批量修改折扣" />
				</shiro:hasPermission>
				<shiro:hasPermission name="agentProduct:openlist_execute">
					<input type="button" class="button" id="changes_open" value="批量开启" />
				</shiro:hasPermission>
				<shiro:hasPermission name="agentProduct:closelist_execute">
					<input type="button" class="button" id="changes_close" value="批量关闭" />
				</shiro:hasPermission>
				<shiro:hasPermission name="agentProduct:deletelist_execute">
					<input type="button" class="button" id="changes_delete" value="批量删除" />
				</shiro:hasPermission>
				<input id="cancel_btn" class="button" type="button" value="返回" onclick="back()" />
			</div>
		</div>
		</form>
		<table id=listTable class=list>
			<tbody>
				<tr>
					<th>&nbsp;<input type="checkbox" id="checkproduct"
						name="checkproduct"></input>
					</th>
					<th><span>序号</span></th>
					<th><span>编号</span></th>
					<th><span>商户名称</span></th>
					<th><span>产品名称</span></th>
					<th><span>产品折扣</span></th>
					<th><span>折扣权重</span></th>
					<th><span>质量</span></th>
					<th><span>质量权重</span></th>
					<th><span>销售金额</span></th>
					<th><span>状态</span></th>
					<th><span>运营商</span></th>
					<th><span>省份</span></th>
					<th><span>城市</span></th>
					<th><span>面值</span></th>
				</tr>
			<tbody id="tb_list" class="listTable">
				<c:choose>
					<c:when test="${fn:length(mlist) > 0}">
						<c:forEach items="${mlist}" var="DownProductRelation"
							varStatus="status">
							<tr>
								<td><input type="checkbox"
									name="checkproduct_${status.count}" onclick="clickbox(this);"></input></td>
								<td>${(page-1)*pageSize+status.index+1}</td>
								<td><input type="hidden" id="id_${status.count}" name="id_"
									value="${DownProductRelation.id}" /> 
									<input type="hidden"  id="productStatus_${status.count}" name="productStatus"  value="${DownProductRelation.airtimeProduct.productStatus}"/>
									
									${DownProductRelation.id}</td>
								<td>
								<input type="hidden" id="productId_${status.count}"
									name="productId__${status.count}"
									value="${DownProductRelation.productId}" />
									
									<input type="hidden" id="businessType_${status.count}"
									name="businessType__${status.count}"
									value="${DownProductRelation.businessType}" />
									
									${DownProductRelation.identityName}</td>
								<td>${DownProductRelation.productName}</td>
								<td><input type="text" width="10" disabled="true"
									onKeyUp="this.value=this.value.replace(/[^\d.]/g,'')" size="5"
									id="discount_${status.count}" name="discount_${status.count}"
									value="${DownProductRelation.discount}" /> <input type="button"
									id="discountButton_${status.count}"
									name="discountButton_${status.count}"
									onclick="discount(${status.count});" value="同上"
									style="display: none;" /> <input type="button"
									id="discountChangesButton_${status.count}"
									name="discountChangesButton_${status.count}"
									onclick="discountChanges(${status.count});" value="批量同上"
									style="display: none;" /></td>
								<td>${DownProductRelation.discountWeight}</td>
								<td>${DownProductRelation.quality}</td>
								<td>${DownProductRelation.qualityWeight}</td>
								<td>${DownProductRelation.price}</td>
								<c:if test="${DownProductRelation.status=='0'}">
								<td  class="tdgb">
										关闭
								</td>
								</c:if>
								 <c:if test="${DownProductRelation.status=='1'}">
									<td>
										开启
									</td>
								</c:if>
								<td><span class="carrierInfo_${status.count}">${DownProductRelation.carrierName}</span>
								</td>
								<td><span class="p_${status.count}">${DownProductRelation.province}</span>
								</td>
								<td><span class="c_${status.count}">${DownProductRelation.city}</span>
								</td>
								<td>${DownProductRelation.parValue}
									<c:choose>
			       <c:when test="${DownProductRelation.businessType==0}">
			             	 元
			       </c:when>
			       <c:when test="${DownProductRelation.businessType==1}">
			             	 M
			       </c:when>
			       <c:otherwise>
			              	未知
			       </c:otherwise>
				</c:choose>	 
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="15">没数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
			</tbody>
		</table>
		<div class="line_pages">
	<div style="float:left;">
	  	显示条数：
	  	<select name="pageSizeValue" id="pageSizeValue" onchange='setPageSize();'>
	  		<c:choose>
				<c:when test="${pageSize==10}">
					<option value="10" selected="selected">10</option>
				</c:when>
				<c:otherwise>
					<option value="10" >10</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==20}">
					<option value="20" selected="selected">20</option>
				</c:when>
				<c:otherwise>
					<option value="20" >20</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==30}">
					<option value="30" selected="selected">30</option>
				</c:when>
				<c:otherwise>
					<option value="30" >30</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==50}">
					<option value="50" selected="selected">50</option>
				</c:when>
				<c:otherwise>
					<option value="50" >50</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==100}">
					<option value="100" selected="selected">100</option>
				</c:when>
				<c:otherwise>
					<option value="100" >100</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==500}">
					<option value="500" selected="selected">500</option>
				</c:when>
				<c:otherwise>
					<option value="500" >500</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${pageSize==1000}">
					<option value="1000" selected="selected">1000</option>
				</c:when>
				<c:otherwise>
					<option value="1000" >1000</option>
				</c:otherwise>
			</c:choose>
		</select>&nbsp; 条
	  </div>
	<div id="pager" style="float:right;"></div>  
	<div class="pages_menber">(共<SPAN id=pageTotal>${counttotal}</SPAN>条记录)</div>
  	</div>
 	<script type="text/javascript" language="javascript">
		$(document).ready(function() { 
			$("#pager").pager({ pagenumber: ${page}, pagecount: ${pagetotal}, buttonClickCallback: PageClick }); 
		}); 
		PageClick = function(pageclickednumber) { 
		  	$("#pager").pager({ 
		       pagenumber: pageclickednumber,
			   pagecount: ${pagetotal}, 
			   buttonClickCallback: PageClick 
		});
		
		$("#page").val(pageclickednumber);
	   $("#listForm").submit();
	
	}
		
		function queryUpProductRelation(){
			var parValue=$("#parValue").val();
            if(parValue!=""&&parValue!=null)
            {
                if(!checkPoint(parValue)){
                    alert("面值格式错误，只能是数字！");
                    document.getElementById("parValue").focus(); 
                    return false;
                }
            }
            
            var discount=$("#discount").val();
            if(discount!=""&&discount!=null)
            {
                if(!checkPoint(discount)){
                    alert("折扣范围格式错误，只能是数字！");
                    document.getElementById("discount").focus(); 
                    return false;
                }
            }
            
            var discount2=$("#discount2").val();
            if(discount2!=""&&discount2!=null)
            {
                if(!checkPoint(discount2)){
                    alert("折扣范围格式错误，只能是数字！");
                    document.getElementById("discount2").focus(); 
                    return false;
                }
            }
			
			$('#listForm').submit();
		}
		
		function checkPoint(str){
			 var reg=/^(\d)*(\.\d{1,4})?$/;
            if(reg.test(str)){
             return true;
            }else{
             return false;
            }
        }
		
		$(document).ready(function(){
			   $('#checkproduct').click(function(){
					var bl = this.checked;	
					
					$('input[type="checkbox"]').each(function(){
						this.checked=bl;
						var b=Number($("table input:checkbox").index(this));
						if(b==0){
							
						}else{
							if($('#productStatus_'+b).val()=='0'){
								this.checked=false;
							}else{
									if($(this).attr("checked")==true){
									if(b==1){
										$("#discountChangesButton_"+b).hide();
									}else{
										$("#discountButton_"+b).show();
									}
										$("#discount_"+b).attr("disabled",false);
									}else{
										$("#discountChangesButton_"+b).hide();
										$("#discountButton_"+b).hide();
										$("#discount_"+b).attr("disabled",true);
								}
							}
						}
					});
					eachBox();
			});
			   
			  
			   
			   
			   var dProductData ='';
			   var war=1;
			   //批量修改
			   $('#changes_discount').click(function(){
				   if($(':checkbox:checked','#tb_list').is(':checked')) {
					    
					}else{
						 alert("请选择!");
					}
				   
				   $(":checkbox:checked",'#tb_list').each(function(){
				     //行下标;
				     var b=Number($("table input:checkbox").index(this));
				     var id = $("#id_"+b).val();                 
				     var productId = $("#productId_"+b).val();   
				     var discount =$("#discount_"+b).val();
				     var busi_type = $("#businessType_"+b).val();
				     if(busi_type=='0' && (discount == "" ||  Number(discount) > 1.2 || Number(discount) < 0.9 || discount.length>6)){
							war=0;
				     }
				  	 if(busi_type=='1' && (discount == "" ||  Number(discount) > 1.9 || Number(discount) < 0.1 || discount.length>6)){
						war=0;
			     	}
				     
				     dProductData+=id+"_"+discount+"_"+productId+"__";
				   });
				   
				   if(war==0){
				   		war=1;
				   	  alert("话费请输入0.9~1.2之间4位小数，流量请输入0.1~1.9之间4位小数！");
						return; 
				   }
				   if(dProductData!=""){
					   //window.location.href="${ctx}/product/toEditAgentProductRelations?dProductData="+dProductData;
					   $.ajax({
						   url:'toEditAgentProductRelations',
  							type: 'GET',
                           data: "dProductData="+dProductData, 
                           contentType:'application/text;charset=UTF-8', 
                           success: function(result) {
                                 if(result=='true'){
                                 	alert('操作成功!');
                                 	window.location.reload();
                                }else{
                                	alert('操作失败!');
                             }
                          }
					   });
					 }
			   });
			   
			   var closeid='';
			   //批量关闭
			   $('#changes_close').click(function(){
			   			 if($(':checkbox:checked','#tb_list').is(':checked')) {
					    
						}else{
							 alert("请选择!");
						}
				   
			  			 $(":checkbox:checked",'#tb_list').each(function(){
					  		 var b=Number($("table input:checkbox").index(this));
					  		 var id = $("#id_"+b).val();
					  		 closeid+=id+"_";
				 		  });
				    
				    	if(closeid!=""){
				    		getProductStatus(closeid,0);	
					  	 }
			   });
			   
			   
			   var openid="";
			   $('#changes_open').click(function(){ 
				   if($(':checkbox:checked','#tb_list').is(':checked')) {
					    
					}else{
						 alert("请选择!");
					}
				   $(":checkbox:checked",'#tb_list').each(function(){
					   var b=Number($("table input:checkbox").index(this));
					   var id = $("#id_"+b).val();
					   openid+=id+"_";
				   });
				   if(openid!=""){
					   getProductStatus(openid,1);
				   }
			   });
			   
			   
			   var deleteid="";
			   $('#changes_delete').click(function(){
				   if($(':checkbox:checked','#tb_list').is(':checked')) {
					    
					}else{
						 alert("请选择要删除的产品!");
					}
				   $(":checkbox:checked",'#tb_list').each(function(){
					   var b=Number($("table input:checkbox").index(this));
					   var id = $("#id_"+b).val();
					   deleteid+=id+"|";
				   });
				   
				    if(deleteid!=""){
				    	$.ajax({
							   url:'deleteAgentProductRelationList',
	  							type: 'GET',
	                           data: "ids="+deleteid, 
	                           contentType:'application/text;charset=UTF-8', 
	                           success: function(result) {
	                                 if(result=='true'){
	                                 	alert('操作成功!');
	                                 	window.location.reload();
	                                }else{
	                                	alert('操作失败!');
	                             }
	                          }
						   });
					}
				    deleteid ="";
			   });
			   
		});
		
		
		function discount(i){
			var b2 = 0;
			$(":checkbox:checked",'#tb_list').each(function(n,v){
				$(":checkbox:checked",'#tb_list').each(function(n2,v2){
					if (n == (n2+1))
					{
						b2 = Number($("table input:checkbox").index(v2));
					}
				});
				var b=Number($("table input:checkbox").index(v));
				if (i == b)
				{
					var str='#discount_'+b2;
					var str2='#discount_'+b;
					$(str2).val($(str).val());
				}
			});
		}
		
		function discountChanges(){
			var discount = "";
			 $(":checkbox:checked",'#tb_list').each(function(n,v){
				 var b=Number($("table input:checkbox").index(v));
				 if (n ==0)
				 {
					 discount=$("#discount_"+b).val();
				 }
				 else
			     {
					 $("#discount_"+b).val(discount);
			     }
			 /* $(":checkbox:checked",'#tb_list').each(function(){
			     //行下标;
			     
			    	var b=Number($("table input:checkbox").index(this));
			    	var str='#discount_'+0;
			    	var discount=$("#discount_1").val();
			    	if(b==1){
			    		
			    	}else{
			    		$("#discount_"+b).val(discount);	
			    	} */
			   });
		}
		//遍历以选择的checkbox
		function eachBox(){
			var i = 0;
			var j = 0;
			$(":checkbox",'#tb_list').each(function(n,v){
				i = n;
			});
			$(":checkbox:checked",'#tb_list').each(function(n,v){
				   j = n;
				   var b=Number($("table input:checkbox").index(v));
				  
				   if (n == 0)
				   {
					   $("#discountChangesButton_"+b).show();
					   $("#discountButton_"+b).hide();
				   }
				   else
				   {
					   $("#discountButton_"+b).show();
					   $("#discountChangesButton_"+b).hide();
				   }
			   });
			if (i != j) //说明有没有被选中的checkBox
			{
				$("#checkproduct").attr("checked",false);
			}
			else
		    {
			   $("#checkproduct").attr("checked",true);
		    }
		}
		
		function toCloneDownMerchantProduct(){
			window.location.href="${ctx}/product/toCloneAgentMerchantProduct";
		}
		
		function clickbox(box){
			var b=Number($("table input:checkbox").index(box));
			if($(box).attr("checked")==true){
				if($('#productStatus_'+b).val()=='0'){
					box.checked=false;
					alert('该产品已关闭!');
					return;
				}
				$("#discount_"+b).attr("disabled",false);
				if(b==1){
					$("#discountChangesButton_"+b).hide();
				}else{
					$("#discountButton_"+b).show();
				}
			}else{
				$("#discountChangesButton_"+b).hide();
				$("#discount_"+b).attr("disabled",true);
				$("#discountButton_"+b).hide();
			}
			eachBox();
		}
		
		function setPageSize(){
			var pageSize=$('#pageSizeValue').val();
			$('#pageSize').val(pageSize);
		}
		
		function getProductStatus(id,status){
			var cfg = {
    						url:'batchUpdateAgentProductRelationStatus',
   							type: 'GET',
                            data: "id="+id+"&status="+status+"&t="+ new Date().getTime(), 
                            contentType:'application/text;charset=UTF-8', 
                            success: function(result) {
                                  if(result=='true'){
                                  	alert('操作成功!');
                                  	window.location.reload();
                                 }else{
                                 	alert('操作失败!');
                              }
                           }
                     };
                    $.ajax(cfg);
		}
		
		function back(){
			window.location.href='${ctx}/product/allAgentProductRelation';
		}
	</script>
</body>
</html>

