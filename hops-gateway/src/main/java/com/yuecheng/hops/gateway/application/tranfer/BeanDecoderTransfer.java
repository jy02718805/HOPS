package com.yuecheng.hops.gateway.application.tranfer;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.SpringUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.common.utils.UUIDUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.injection.entity.InterfaceConstant;
import com.yuecheng.hops.injection.entity.InterfaceParam;
import com.yuecheng.hops.injection.service.InterfaceConstantService;

@Service("beanDecoderTransfer")
public class BeanDecoderTransfer {
    private static Logger logger = LoggerFactory.getLogger(BeanDecoderTransfer.class);
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private InterfaceConstantService interfaceConstantService;
	
	
	public Map<String,Object> decode(Map<String,Object> fields,List<InterfaceParam> interfaceParams,Long merchantId) throws Exception{
		Map<String,Object> orderMap = new HashMap<String,Object>();
		try {
			String inputParamName = StringUtil.initString();
			if(BeanUtils.isNotNull(interfaceParams) && fields.size() > 0){
    			for(InterfaceParam interfaceParam : interfaceParams){
    				inputParamName = interfaceParam.getInputParamName();
    				String outParamName = interfaceParam.getOutParamName();
    				Object value = null;
    				
    				if(interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_TRANSPARAM)){
    				    value = fields.get(outParamName);
                        if(BeanUtils.isNotNull(inputParamName) && inputParamName.equalsIgnoreCase(EntityConstant.Merchant.MERCHANT_CODE)){
                            if(merchantService == null){
                                ApplicationContext ctx = SpringUtils.getApplicationContext();
                                merchantService = (MerchantService)ctx.getBean("merchantService");
                            }
                            Merchant merchant = null;
                            if(BeanUtils.isNotNull(merchantId) && merchantId > 0)
                            {
                                merchant = merchantService.queryMerchantById(merchantId);
                            }
                            else if(value == null)
                            {
                            	throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR);
                            }
                            else
                            {
                                merchant = merchantService.queryMerchantByMerchantCode(String.valueOf(value).toString());
                            }
                            if(BeanUtils.isNull(merchant))
                            {
                                throw new ApplicationException(Constant.ErrorCode.MANUAL);
                            }
                            orderMap.put(EntityConstant.Order.MERCHANT_ID, merchant.getId());
                        }
    				}else if(interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT)){
    		            InterfaceConstant interfaceConstant=interfaceConstantService.getInterfaceConstant(merchantId, IdentityType.MERCHANT.toString(), outParamName);
    		            value = interfaceConstant.getValue().trim();
                    }else if(interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_AMOUNT)){
                    	if(Constant.Common.MONEY_FORMAT_TYPE_YUAN.equals(interfaceParam.getFormatType()))
                    	{
    						DecimalFormat mformat = new DecimalFormat(Constant.Common.MONEY_FORMAT_TYPE);
    						value= fields.get(outParamName);
    						if (BeanUtils.isNotNull(value))
                            {
                                value = mformat.format(Double.valueOf(String.valueOf(value)));
                            }
                    	}
                    	else if(Constant.Common.MONEY_FORMAT_TYPE_CENT.equals(interfaceParam.getFormatType())) 
                    	{
                    		DecimalFormat mformat = new DecimalFormat(Constant.Common.MONEY_FORMAT_TYPE);
    						value= fields.get(outParamName);
    						if (BeanUtils.isNotNull(value))
    						{
    							value = mformat.format(Double.valueOf(String.valueOf(value)));
        						value=BigDecimalUtil.divide(new BigDecimal(value.toString()),new BigDecimal("100"),DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP);
    						}
                    	}
    				}else if(interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_DATE)){
    					SimpleDateFormat formatter = new SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
    					if (interfaceParam.getFormatType() != null)
						{
							formatter = new SimpleDateFormat(interfaceParam.getFormatType());
						}
    					value= fields.get(outParamName);
    					if (BeanUtils.isNotNull(value)&&!StringUtil.initString().equals(value))
                        {
    					    value = URLDecoder.decode((String) value);
                            value = formatter.parse(value.toString());
                        }
    					
    				}else if (interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_RANDOM))
                    {
                    	value = UUIDUtils.uuid();
                    }else if (interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_COMBINNUM))
                    {
                    	//截取我们系统内部能识别的deliveryID
                    	value = fields.get(outParamName);
                    	orderMap.put("deliveryId", String.valueOf(value).substring(6, 18));
                    }
    				orderMap.put(inputParamName, value);
    			}
			}
		}
		catch(RpcException e){
		    throw new ApplicationException(Constant.ErrorCode.MANUAL,e);
		}
		catch(HopsException e){
		    throw e;
		}
		catch (Exception e) {
		    logger.error("BeanDecoderTransfer Faied caused by "+e);
			throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR,e);
		}
		logger.debug("BeanEncoderTransfer success!");
		return orderMap;
	}
	
}
