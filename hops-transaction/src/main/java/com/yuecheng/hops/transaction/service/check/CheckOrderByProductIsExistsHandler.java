package com.yuecheng.hops.transaction.service.check;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.NumberUtil;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.config.entify.product.TmallTSC;
import com.yuecheng.hops.transaction.config.product.TmallTSCService;
import com.yuecheng.hops.transaction.execution.product.MerchantProductMatcherTransaction;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Service("checkOrderByProductIsExistsHandler")
public class CheckOrderByProductIsExistsHandler extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(CheckOrderByProductIsExistsHandler.class);

    @Autowired
    private ErrorCodeService errorCodeService;

    @Autowired
    private MerchantProductMatcherTransaction merchantProductMatcherTransaction;
    
    @Autowired
    private AgentProductRelationService  agentProductRelationService;

    @Autowired
    private TmallTSCService                       tmallTSCService;

    /**
     * 处理方法，调用此方法处理请求
     */
    @Override
    public void handleRequest()
        throws HopsException
    {
        try
        {
            Verify();
            logger.debug("检查订单产品是否存在、开通！通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查订单产品是否存在、开通，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        AgentProductRelation agentProduct = null;
        String errorCode = null;
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        NumSection numSection = (NumSection)ActionContextUtil.getActionContextParam(ActionMapKey.NUM_SECTION);
        AirtimeProduct product = null;
	
        try{
			if (order.getProductNo() != null && order.getBusinessType() == 1)//!!!
			{
				product = merchantProductMatcherTransaction.matchAirtimeProduct(order.getProductNo(),
						order.getMerchantId());
				// 普通找产品
				agentProduct = merchantProductMatcherTransaction.matchAgentProduct(order.getMerchantId(), product);
				
				//!!!调用1次
				
				if (BeanUtils.isNull(agentProduct))
				{
					// 系统不支持该产品
					logger.error("找不到拍拍对应的该产品,产品编号：" + order.getProductNo());
					throw new ApplicationException(Constant.ErrorCode.PRODUCT_DISABLED);
				}
				else
				{

					//如果是拍拍代理商需要验证价格是否一致,普通代理商以我方的价格为准
					if (Constant.SpecialDown.PAIPAI == order.getSpecialDown())
					{
						BigDecimal apPrice = agentProduct.getPrice().setScale(DecimalPlaces.TWO.value(),
								BigDecimal.ROUND_HALF_UP);
						// 检查金额是否正确
						if (apPrice.compareTo(order.getOrderFee()) != 0)
						{
							throw new ApplicationException(Constant.ErrorCode.FAIL_TRADING_MONEY);
						}
						order.setBusinessType(Long.valueOf(agentProduct.getBusinessType()));
					}
				}
			}
			else if (order.getProductNo() == null)//!!!
            {
				product = merchantProductMatcherTransaction.matchAirtimeProduct(numSection, order.getOrderFee(), order
						.getBusinessType().intValue());
                // 普通找产品
                agentProduct = merchantProductMatcherTransaction.matchAgentProduct(order.getMerchantId(), product);
                
            }
            else
            {
                // 淘宝找产品
                TmallTSC tmallTSC = tmallTSCService.findOne(order.getProductNo());
                if(BeanUtils.isNull(tmallTSC))
                {
                    // 系统不支持该产品
                    logger.error("找不到天猫对应的该产品,产品编号：" + order.getProductNo());
                    errorCode = Constant.ErrorCode.PRODUCT_DISABLED;
                    throw new ApplicationException(Constant.ErrorCode.PRODUCT_DISABLED);
                }else
                {
                    //判断面值和付款金额是否合理
                	order.setProductFace(new BigDecimal(tmallTSC.getFaceValue()));
                	
                	if(NumberUtil.isFixedPhone(order.getUserCode()))
                    {
                        // 固话
                	    BigDecimal faceValue=new BigDecimal(tmallTSC.getFaceValue());
                        BigDecimal minsalesfee = faceValue.multiply(new BigDecimal("0.5"));
                        BigDecimal maxsalesfee = faceValue.multiply(new BigDecimal("1.5"));
                        BigDecimal orderFee=order.getOrderFee();
                        if (orderFee.compareTo(minsalesfee) <= 0 || orderFee.compareTo(maxsalesfee) >= 0)
                        {
                            logger.error("付款金额大于面值1.5倍或小于面值0.5倍返回失败,下游订单号：" + order.getMerchantOrderNo());
                            //付款金额的1.5倍大于面值则返回失败0503
                            errorCode = Constant.ErrorCode.FAIL_TRADING_MONEY;
                            throw new ApplicationException(Constant.ErrorCode.FAIL_TRADING_MONEY);
                        }
                    }
                    else
                    {
                        // 话费 判断面值和付款金额是否合理
                        BigDecimal faceValue=new BigDecimal(tmallTSC.getFaceValue());
                        BigDecimal minsalesfee = faceValue.multiply(new BigDecimal("0.9"));
                        BigDecimal maxsalesfee = faceValue.multiply(new BigDecimal("1.2"));
                        BigDecimal orderFee=order.getOrderFee();
                        if (orderFee.compareTo(minsalesfee) <= 0 || orderFee.compareTo(maxsalesfee) >= 0)
                        {
                            logger.error("付款金额大于面值1.2倍或小于面值0.9倍返回失败,下游订单号：" + order.getMerchantOrderNo());
                            //付款金额的1.5倍大于面值则返回失败0503
                            errorCode = Constant.ErrorCode.FAIL_TRADING_MONEY;
                            throw new ApplicationException(Constant.ErrorCode.FAIL_TRADING_MONEY);
                        }
                    }
                }
                product = merchantProductMatcherTransaction.matchAirtimeProduct(numSection, new BigDecimal(tmallTSC.getFaceValue()),order.getBusinessType().intValue());
                // 普通找产品
                agentProduct = merchantProductMatcherTransaction.matchAgentProduct(order.getMerchantId(), product);
                if (null == agentProduct)//!!!交易下移
                {
                	 logger.error("产品与充值号码不匹配："+numSection.toString()+",产品编号："+order.getProductNo());
                	 errorCode = Constant.ErrorCode.NUMBER_BIND_PRODUCT;
                	 throw new ApplicationException(Constant.ErrorCode.NUMBER_BIND_PRODUCT);
                }
            }
            ActionContextUtil.setActionContext(TransactionMapKey.PRODUCT, product);
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        catch(RpcException e){
            throw e;
        }
        catch(Exception e)
        {
            logger.error("查询产品信息异常,号段："+String.valueOf(numSection).toString()+",产品编号："+String.valueOf(order.getProductNo()).toString()+"with exception info:"+ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException(Constant.ErrorCode.NOT_SUPPORT);
        }
        if (agentProduct == null)
        {
            // 渠道产品不存在
            logger.error("产品不存在:product["+product+"]  numSection["+numSection+"] order["+order+"]");
            if (null != errorCode)
            {
            	throw new ApplicationException(errorCode);
            }
            else
            {
            	throw new ApplicationException(Constant.ErrorCode.NOT_SUPPORT);
            }
            
        }
        else
        {
        	order.setProductFace(agentProduct.getParValue());
        	order.setDisplayValue(agentProduct.getDisplayValue());
        	if(order.getBusinessType() == 0)
        	{
//            	Assert.isTrue(order.getOrderFee().compareTo(order.getProductFace().multiply(new BigDecimal(order.getProductNum()))) == 0);
        	    order.setOrderFee(agentProduct.getParValue().multiply(new BigDecimal(order.getProductNum())));
        	}
        	else
        	{
        		order.setOrderFee(agentProduct.getDisplayValue());
        	}
            // 检查产品是否存在、开通
            boolean productFlag = checkProduct(agentProduct, Constant.AgentProductStatus.CLOSE_STATUS);
            if (productFlag)
            {
                logger.error("产品未开通"+",产品编号："+agentProduct.getProductId());
                // 渠道产品未开通
                throw new ApplicationException(Constant.ErrorCode.NOT_SUPPORT);
            }else{
                ActionContextUtil.setActionContext(ActionMapKey.AGENT_PRODUCT, agentProduct);
            }
        }
    }

    public boolean checkProduct(AgentProductRelation downProduct, String downProductStatus)
    {
        if (downProduct != null)
        {
            if (downProduct.getStatus() == null || downProduct.getStatus().isEmpty()
                || downProduct.getStatus().equals(downProductStatus))
            {
                // 渠道产品未开通
                return true;
            }
        }
        else
        {
            // 渠道产品未开通
            return true;
        }
        return false;
    }
}
