package com.yuecheng.hops.transaction.service.order.impl;


import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.account.entity.CCYAccount;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.injection.service.ErrorCodeService;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;
import com.yuecheng.hops.transaction.basic.repository.impl.sql.OrderSqlDao;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryService;
import com.yuecheng.hops.transaction.service.order.OrderService;


/**
 * 订单相关操作服务层
 * 
 * @author Jinger 2014-03-05
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService
{

    @Autowired
    private IdentityAccountRoleService            identityAccountRoleService;

    @Autowired
    private OrderJpaDao                          orderJpaDao;

    @Autowired
    private ParameterConfigurationService     parameterConfigurationService;

    @Autowired
    private ErrorCodeService                  errorCodeService;

    @Autowired
    private DeliveryService                   deliveryService;
    
    @Autowired
    private OrderSqlDao orderSqlDao;

    static Logger                             logger    = LoggerFactory.getLogger(OrderServiceImpl.class);

    SimpleDateFormat                          formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Order> findBindOrders()
    {
        List<Order> orders = orderSqlDao.findOrdersByParams(Constant.OrderStatus.WAIT_RECHARGE);
        return orders;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Order> findFakeOrders()
    {
        List<Order> orders = orderJpaDao.findFakeOrdersByParams(Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT);
        return orders;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Order> findPartSuccessOrders()
    {
        List<Order> orders = orderJpaDao.findOrdersByParams(Constant.OrderStatus.SUCCESS_PART);
        return orders;
    }

    @Override
    public void OrderDataInitialization()
    {
        try
        {
            logger.debug("初始化下单必须项！");
            Merchant agentMerchant = (Merchant)ActionContextUtil.getActionContextParam(ActionMapKey.AGENT_MERCHANT);
            CCYAccount payerAccount = (CCYAccount)ActionContextUtil.getActionContextParam(ActionMapKey.PAYER_ACCOUNT);
            NumSection numSection = (NumSection)ActionContextUtil.getActionContextParam(ActionMapKey.NUM_SECTION);
            AgentProductRelation agentProduct =(AgentProductRelation)ActionContextUtil.getActionContextParam(ActionMapKey.AGENT_PRODUCT);
            Assert.notNull(agentProduct);
            logger.debug("初始化下单必须项完成！详细信息：agentMerchant=[" + agentMerchant.toString()
                         + "]  payerAccount=[" + payerAccount.toString() + "]  numSection=["
                         + numSection.toString() + "]  agentProduct=[" + agentProduct.toString()
                         + "]");
        }
        catch (HopsException e)
        {
            String errorCode = e.getCode();
            throw new ApplicationException(errorCode);
        }
        catch(Exception e)
        {
            String errorCode = Constant.ErrorCode.MANUAL;
            throw new ApplicationException(errorCode);
        }
    }
}
