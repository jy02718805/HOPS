package com.yuecheng.hops.transaction.service.check;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;


@Service("checkOrderByMerchantStatusHandler")
public class CheckOrderByMerchantStatusHandler extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(CheckOrderByMerchantStatusHandler.class);

    @Autowired
    private ErrorCodeService errorCodeService;

    @Autowired
    private IdentityService                   identityService;
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
            logger.debug("检查商家是否不存在或未启用！通过");
        }
        catch (HopsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.error("检查商家是否不存在或未启用，失败！");
            throw new ApplicationException(Constant.ErrorCode.MANUAL);
        }
    }

    public void Verify()
        throws Exception
    {
        Order order = (Order)ActionContextUtil.getActionContextParam(ActionMapKey.ORDER);
        Merchant agentMerchant = null;
        try{
            agentMerchant=(Merchant)identityService.findIdentityByIdentityId(
                order.getMerchantId(), IdentityType.MERCHANT);
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        catch(RpcException e){
            throw e;
        }
        catch(Exception e)
        {
            logger.error("查询商家异常，商家编号："+order.getMerchantId() + "异常信息："+ExceptionUtil.getStackTraceAsString(e));
            //agentMerchant=null;
            throw e;
        }
        if (null == agentMerchant)
        {
            logger.error("商家不存在，商家编号："+order.getMerchantId());
            throw new ApplicationException(Constant.ErrorCode.PARTNER_ERROR);
        }
        else
        {
            boolean merchantFlag = merchantIsExists(agentMerchant, Constant.MerchantStatus.ENABLE);
            if (merchantFlag)
            {
                logger.error("商家未启用"+String.valueOf(agentMerchant).toString());
                // 商家不存在或未启用
                throw new ApplicationException(Constant.ErrorCode.PARTNER_ERROR);
            }
            else
            {
                ActionContextUtil.setActionContext(ActionMapKey.AGENT_MERCHANT, agentMerchant);
            }
        }
    }

    public boolean merchantIsExists(Merchant merchant, String merchantStatus)
    {
        if (BeanUtils.isNotNull(merchant) && BeanUtils.isNotNull(merchant.getId()))
        {
            if (BeanUtils.isNull(merchant.getIdentityStatus()) || StringUtils.isBlank(merchant.getIdentityStatus().getStatus()) || !(merchant.getIdentityStatus().getStatus().equals(merchantStatus)))
            {
                // 商家不存在或未启用
                return true;
            }
        }
        else
        {
            // 商家不存在或未启用
            return true;
        }
        return false;
    }
}
