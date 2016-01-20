package com.yuecheng.hops.transaction.service.order.builder.support;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.config.AgentQueryFakeRuleService;
import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;
import com.yuecheng.hops.transaction.mq.producer.OrderNotifyProducerService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("orderInitialization")
public class OrderInitialization
{
    private static Logger         logger = LoggerFactory.getLogger(OrderInitialization.class);

    @Autowired
    private ProductPageQuery      productPageQuery;

    @Autowired
    private AgentQueryFakeRuleService agentQueryFakeRuleService;

    @Autowired
    private OrderManagement       orderManagement;
    
    @Autowired
    private OrderNotifyProducerService orderNotifyProducerService;
    
    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public Order initOrder(Order order, Merchant merchant, AgentProductRelation downProduct,
                           NumSection numSection)
    {
        try
        {
            Boolean needNotify = false;
            AirtimeProduct product = (AirtimeProduct)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.PRODUCT);
            logger.debug("begin to record the order"+String.valueOf(order).toString()+" product:"+String.valueOf(product).toString());
            order = setOrderCommonParams(order, merchant, downProduct, numSection, product);
            
            //!!!参数放入Constant
            ParameterConfiguration hc = parameterConfigurationService.getParameterConfigurationByKey("repeat_binding_times");
            
            order.setLimitBindTimes(Long.valueOf(hc.getConstantValue()));
            //!!!如果最大绑定次数没有拿到
            
            AgentQueryFakeRule fakeRule = agentQueryFakeRuleService.queryAgentQueryFakeRuleById(merchant.getId());
            if (BeanUtils.isNotNull(fakeRule))
            {
                order.setPreSuccessStatus(Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT);
                Date preSuccessTime = DateUtil.addTime(fakeRule.getIntervalUnit(),
                    fakeRule.getIntervalTime());
                order.setOrderPreSuccessTime(preSuccessTime);
                needNotify = true;
            }
            else
            {
                order.setPreSuccessStatus(Constant.OrderStatus.PRE_SUCCESS_STATUS_NO_NEED);
                needNotify = false;
            }
            // 保存订单
            order = orderManagement.save(order);
            orderManagement.saveAgentOrderKey(order.getMerchantId(), order.getMerchantOrderNo());
            Assert.notNull(order);
            if(needNotify){
                long delay = order.getOrderPreSuccessTime().getTime() - new Date().getTime();
                orderNotifyProducerService.sendMessage(order.getOrderNo(), delay);
            }
            return order;
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("订单初始化异常，异常信息：" + ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException(Constant.ErrorCode.FAIL, new String[]{ExceptionUtil.getStackTraceAsString(e)});
        }
    }

    public Order setOrderCommonParams(Order order, Merchant merchant,
                                      AgentProductRelation downProduct, NumSection numSection,
                                      AirtimeProduct product)
    {
        order.setMerchantId(merchant.getId());
        order.setMerchantName(merchant.getMerchantName());
        order.setOrderStatus(Constant.OrderStatus.WAIT_PAY);
        order.setErrorCode(order.getOrderStatus().toString());
        order.setNotifyStatus(Constant.NotifyStatus.NO_NEED_NOTIFY);
        order.setProductId(downProduct.getProductId());
        order.setProductNo(product.getProductNo());
        order.setProductFace(downProduct.getParValue());
        order.setManualFlag(Constant.OrderManualFlag.ORDER_MANUAL_FLAG_NO_NEED);
        order.setProductSaleDiscount(downProduct.getDiscount());
        order.setBusinessNo(Constant.BusinessNo.HU_FEI);
        order.setBusinessChannel(Constant.Channel.CHANNEL_API);
        order.setOrderRequestTime(new Date());
        order.setOrderSuccessFee(new BigDecimal(Constant.OrderInitParames.ORDER_INIT_SUCCESS_FEE));
        order.setPreOrderBindTime(new Date());
        order.setBindTimes(Constant.OrderInitParames.ORDER_INIT_BIND_TIMES);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        order.setOrderTimeout(calendar.getTime());
        order.setExt1(numSection.getCarrierInfo().getCarrierNo());
        order.setExt2(numSection.getProvince().getProvinceId());
        order.setExt3(numSection.getCity().getCityId());
        order.setExt4(downProduct.getQuality().toString());
        if(null == order.getSpecialDown())//!!!
        {
        	order.setSpecialDown(Constant.SpecialDown.GENERAL);
        }
		if (null == order.getBusinessType())//!!!
		{
			order.setBusinessType(Long.valueOf(Constant.BusinessType.BUSINESS_TYPE_HF));
		}
        //如果不上话费业务,从数据库中取面额价值
		if (Constant.BusinessType.BUSINESS_TYPE_HF.equals(String.valueOf(order.getBusinessType())) || Constant.BusinessType.BUSINESS_TYPE_FIXED.equals(String.valueOf(order.getBusinessType())))
		{
			order.setOrderSalesFee(BigDecimalUtil.multiply(order.getOrderFee(), downProduct.getDiscount(),
					DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP));
		}
		else
		{
			order.setOrderSalesFee(BigDecimalUtil.multiply(downProduct.getDisplayValue(), downProduct.getDiscount(),
					DecimalPlaces.THREE.value(), BigDecimal.ROUND_HALF_UP));
		}
      
        return order;
    }
}
