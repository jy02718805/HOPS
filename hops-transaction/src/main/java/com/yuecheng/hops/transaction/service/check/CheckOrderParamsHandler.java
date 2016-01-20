package com.yuecheng.hops.transaction.service.check;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Service("checkOrderParamsHandler")
public class CheckOrderParamsHandler extends ActionHandler
{
    private static Logger logger = LoggerFactory.getLogger(CheckOrderParamsHandler.class);

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
            logger.debug("检查订单必输项！ 通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查订单必输项，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        String notifyUrl = (String)ActionContextUtil.getActionContextParam(ActionMapKey.NOTIFY_URL);
        String sign = (String)ActionContextUtil.getActionContextParam(ActionMapKey.SIGN);

        Boolean productNumIsError = (Boolean)ActionContextUtil.getActionContextParam(ActionMapKey.PORDUCT_NUM);
        try
        {
            // 当为淘宝订单，并且productNumIsError为空true时，说明传过来的productNum为空或者非数字
            if (null != order.getProductNo() && (order.getProductNum() != 1) || productNumIsError)
            {
                logger.error("淘宝订单，购买数量是否合法，不等于1！");
                throw new ApplicationException(Constant.ErrorCode.CARD_ERROR);
            }
            Assert.notNull(order.getMerchantId());
            Assert.isTrue(order.getMerchantId() > 0);
            //!!!Assert.notNull(order.getMerchantOrderNo());
            Assert.isTrue(StringUtil.isNotBlank(order.getMerchantOrderNo()));
            //!!!Assert.notNull(order.getUserCode());
            Assert.isTrue(StringUtil.isNotBlank(order.getUserCode()));
            Assert.notNull(order.getProductNum());
            Assert.isTrue(order.getProductNum() > 0);
            Assert.notNull(order.getOrderFee());
            // Assert.isTrue(order.getOrderFee().compareTo(order.getProductFace().multiply(new
            // BigDecimal(order.getProductNum()))) == 0);
            if (String.valueOf(order.getBusinessType()).equals(Constant.BusinessType.BUSINESS_TYPE_HF) || String.valueOf(order.getBusinessType()).equals(Constant.BusinessType.BUSINESS_TYPE_FIXED))
            {
                Assert.notNull(notifyUrl);
                Assert.isTrue(StringUtil.isNotBlank(notifyUrl));
            }
            else if (String.valueOf(order.getBusinessType()).equals(Constant.BusinessType.BUSINESS_TYPE_FLOW))
            {
                Assert.notNull(order.getProductNo());
                Assert.isTrue(StringUtil.isNotBlank(order.getProductNo()));
            }
            //!!!!Assert.notNull(sign);
            Assert.isTrue(StringUtil.isNotBlank(sign));
            if (order.getOrderRequestTime() == null && order.getProductNo() != null)//!!!
            {
                if (order.getOrderDesc() == null || order.getOrderDesc().isEmpty())
                {
                    logger.error("淘宝订单，商品信息快照为空！");
                    throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR);
                }
                if (order.getProductNum() != 1)
                {
                    logger.error("淘宝订单，购买数量是否合法，不等于1！");
                    throw new ApplicationException(Constant.ErrorCode.CARD_ERROR);
                }
            }
            else if (order.getOrderRequestTime() != null && order.getProductNo() == null)//!!!
            {
                if (order.getProductFace() == null
                    || order.getProductFace().compareTo(new BigDecimal(0)) <= 0)
                {
                    logger.error("普通订单，面值为空！");
                    throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR);
                }
            }
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        catch (HopsException e)
        {
            logger.error("必填参数异常！");
            throw new ApplicationException(e.getCode());
        }
        catch (Exception e)
        {
            logger.error("必填参数为空！");
            throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR);
        }
    }

}
