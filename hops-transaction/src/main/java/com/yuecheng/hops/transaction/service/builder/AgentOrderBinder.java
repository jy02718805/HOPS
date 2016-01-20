package com.yuecheng.hops.transaction.service.builder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.event.SendOrderEvent;
import com.yuecheng.hops.transaction.execution.bind.action.OrderMissBindAction;
import com.yuecheng.hops.transaction.execution.bind.director.OrderMissBindSelector;
import com.yuecheng.hops.transaction.execution.product.SupplyProductTransaction;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
import com.yuecheng.hops.transaction.service.process.DeliveryCreatorProcess;

@org.springframework.core.annotation.Order(1)
@Service("agentOrderBinder")
public class AgentOrderBinder implements TransactionService
{
    private static Logger             logger = LoggerFactory.getLogger(AgentOrderBinder.class);

    @Autowired
    private DeliveryManagement deliveryManagement;

    @Autowired
    private OrderManagement orderManagement;
    
    @Autowired
    private SupplyProductTransaction  supplyProductTransaction;

    @Autowired
    private OrderMissBindSelector     orderMissBindSelector;

    @Autowired
    private DeliveryCreatorProcess deliveryCreatorProcess;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    @Qualifier("orderStatusWaitAction")
    private AbstractEventAction orderStatusWaitAction;
    
    @Autowired
    @Qualifier("manualReviewOrderAction")
    private OrderMissBindAction manualReviewOrderAction;
    

    @Override
    @Transactional
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        Long orderNo = (Long)TransactionContextUtil.getTransactionContextParam(EntityConstant.Order.ORDER_NO);
        try
        {
            logger.debug("订单绑定流程:订单编号" + orderNo);
            Order order = orderManagement.findOne(orderNo);
            TransactionContextUtil.setTransactionContext(TransactionMapKey.ORDER, order);
            Integer unFailDeliveryNum = deliveryManagement.countUnFailDeliveryNum(orderNo);
            if(0 != unFailDeliveryNum){
                //修改人工审核标示
                manualReviewOrderAction.execute(order);
                return response;
            }
//            try{
//                Assert.isTrue(unFailDeliveryNum == 0);
//            }
//            catch(IllegalArgumentException e){
//                logger.error("订单绑定流程:存在未完成的发货记录。需要先处理完未完成记录才能重新绑定订单！[结束] orderNo[" + orderNo+ "]");
//                throw new ApplicationException("transaction002037", e);
//            }
            
           
            // 获取供货商信息(号段、金额)
            SupplyProductRelation upr = supplyProductTransaction.getSupplyProductRelation(order);
            order = (Order)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER);
            if (BeanUtils.isNotNull(upr))
            {
                logger.debug("找到的供货商信息：["+String.valueOf(upr).toString()+"]");
                AirtimeProduct airtimeProduct=productService.findOne(upr.getProductId());
                logger.debug("查找系统产品信息：["+String.valueOf(airtimeProduct).toString()+"]");
                                
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.SUPPLY_PRODUCT_RELATION, upr);
                ActionContextUtil.setActionContext(ActionMapKey.ORDER, order);
                ActionContextUtil.setActionContext(ActionMapKey.USER_CODE, order.getUserCode());
                // 添加Delivery记录
                deliveryCreatorProcess.execute();
                // 发起发货事件 存在事务交替问题  应该将dotransaction方法优化 前处理 后处理 方法。前处理 组参数，后处理 提交事件。中间做业务。
                Delivery delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
                HopsRequestEvent hre = new HopsRequestEvent(AgentOrderBinder.class);
                hre = new SendOrderEvent(AgentOrderBinder.class, delivery.getDeliveryId(),airtimeProduct.getBusinessType(),upr);
                TransactionContextUtil.setTransactionContext(TransactionMapKey.HOPS_REQUEST_EVENT, hre);
            }
            else
            {
                logger.debug("未找到的供货商信息");
                OrderMissBindAction action = orderMissBindSelector.select(order);
                action.execute(order);
            }
        }
        catch (HopsException e)
        {
            ActionContextUtil.init();
            ActionContextUtil.setActionContext(ActionMapKey.ORDER, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.ORDER));
            orderStatusWaitAction.handleRequest();
            logger.error("订单绑定流程:发生异常，将订单状态修改成等待发货。Exception[" + ExceptionUtil.getStackTraceAsString(e) + "] ,进入单笔订单绑定！[结束]");
        }
        logger.debug("进入单笔订单绑定！[结束]");
        return response;
    }
}
