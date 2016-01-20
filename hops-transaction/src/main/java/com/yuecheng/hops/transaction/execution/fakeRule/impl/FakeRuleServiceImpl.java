package com.yuecheng.hops.transaction.execution.fakeRule.impl;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;
import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;
import com.yuecheng.hops.transaction.config.repository.AgentQueryFakeRuleDao;
import com.yuecheng.hops.transaction.event.NotifyAgentOrderEvent;
import com.yuecheng.hops.transaction.execution.fakeRule.FakeRuleService;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.notify.NotifyService;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("updateFakeStatusTransaction")
public class FakeRuleServiceImpl implements FakeRuleService
{
    private static Logger   logger = LoggerFactory.getLogger(FakeRuleServiceImpl.class);

    @Autowired
    private NotifyService   notifyService;

    @Autowired
    private OrderManagement orderManagement;
    
    @Autowired
    private AgentQueryFakeRuleDao agentQueryFakeRuleDao;
    
    @Autowired
    @Qualifier("notifyStatusWaitAction") 
    private AbstractEventAction notifyStatusWaitAction;
    
    @Autowired
    private OrderJpaDao orderJpaDao;
    
    @Autowired
    private HopsPublisher publisher;

    @Override
    @Transactional
    public void updateOrderNotifyStatus(Long orderNo)
    {
        Notify notify = notifyService.findNotifyByOrderNo(orderNo);
        if(BeanUtils.isNotNull(notify))
        {
            Order order = orderManagement.findOne(orderNo);
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
            notifyStatusWaitAction.handleRequest();
            HopsRequestEvent hopsRequestEvent = new HopsRequestEvent(FakeRuleServiceImpl.class);
            hopsRequestEvent = new NotifyAgentOrderEvent(FakeRuleServiceImpl.class, notify.getOrderNo());
            publisher.publishRequestEvent(hopsRequestEvent);
            logger.warn("预成功订单流程：将通知记录状态修改成等待通知![" + notify.toString() + "]");
        }
        orderJpaDao.updateOrderPreSuccessStatus(Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE, orderNo);
        logger.warn("预成功订单流程：将订单预成功状态修改成预处理成功![" + orderNo + "]");
    }
    
    @Override
    public AgentQueryFakeRule checkMerchantIsFake(Long merchantId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentQueryFakeRule.MERCHANT_ID, new SearchFilter(
            EntityConstant.AgentQueryFakeRule.MERCHANT_ID, Operator.EQ, merchantId));
        Specification<AgentQueryFakeRule> spec_fakeRule = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentQueryFakeRule.class);
        AgentQueryFakeRule fakeRule = agentQueryFakeRuleDao.findOne(spec_fakeRule);
        return fakeRule;
    }

}
