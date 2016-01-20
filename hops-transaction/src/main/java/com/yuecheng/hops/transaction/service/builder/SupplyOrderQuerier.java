package com.yuecheng.hops.transaction.service.builder;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.event.HopsRequestEvent;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.PrintUtil;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.transaction.DefaultTransactionResponseImpl;
import com.yuecheng.hops.transaction.TransactionContextUtil;
import com.yuecheng.hops.transaction.TransactionMapKey;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.event.AsynQueryDeliveryEvent;
import com.yuecheng.hops.transaction.execution.devliery.DeliveryExecutionService;
import com.yuecheng.hops.transaction.service.action.AbstractEventAction;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.order.OrderManagement;

@Scope("prototype")
@Service("supplyOrderQuerier")
public class SupplyOrderQuerier implements TransactionService
{
    private static Logger                   logger = LoggerFactory.getLogger(SupplyOrderQuerier.class);
    
    @Autowired
    private DeliveryExecutionService       deliveryExecutionService;
    
    @Autowired
    private HopsPublisher publisher;
    
    @Autowired
    private OrderManagement orderManagement;
    @Autowired
    private ProductService productService;
    @Autowired
    @Qualifier("queryStatusWaitAction")
    private AbstractEventAction queryStatusWaitAction;
    
    @Override
    public TransactionResponse doTransaction(TransactionRequest transactionRequest)
    {
        logger.debug("SupplyOrderQuerier_transactionRequest"+PrintUtil.mapToString(transactionRequest));
        TransactionContextUtil.copyPropertiesIfAbsent(transactionRequest);
        TransactionResponse response = new DefaultTransactionResponseImpl();
        Long deliveryId = (Long)TransactionContextUtil.getTransactionContextParam(EntityConstant.Delivery.DELIVERY_ID);
        //添加查询次数100011820614
        Delivery delivery = deliveryExecutionService.addQueryTime(deliveryId);
        if((Constant.Delivery.DELIVERY_STATUS_FAIL != delivery.getDeliveryStatus() && Constant.Delivery.DELIVERY_STATUS_SUCCESS != delivery.getDeliveryStatus()) && Constant.Delivery.QUERY_FLAG_QUERY_END != delivery.getQueryFlag()){
            TransactionContextUtil.setTransactionContext(TransactionMapKey.DELIVERY, delivery);
            logger.debug("查询供货商订单信息流程：发货记录信息[" + delivery.toString() + "]");
            try
            {
                // 1.发送查询报文
                //查找供货商产品关系并找到系统产品得到businessType
                AirtimeProduct airtimeProduct=productService.findOne(delivery.getProductId());//!!!
                //通过businessType判断查询的接口类型
                String interfaceType = Constant.Interface.INTERFACE_TYPE_QUERY_ORDER;
                if(Constant.BusinessType.BUSINESS_TYPE_FLOW.equals(String.valueOf(airtimeProduct.getBusinessType())))
                {
                    interfaceType = Constant.Interface.INTERFACE_TYPE_SUPPLY_QUERY_ORDER_FLOW;
                }
                final Map<String, Object> fileds = BeanUtils.transBean2Map(delivery);
                fileds.put(TransactionMapKey.INTERFACE_TYPE, interfaceType);
                fileds.put(TransactionMapKey.INTERFACE_MERCHANT_ID, delivery.getMerchantId());
                // 2.发送发货记录 responseStr --> Map
                HopsRequestEvent hre = new HopsRequestEvent(SupplyOrderQuerier.class);
                hre = new AsynQueryDeliveryEvent(SupplyOrderQuerier.class, fileds, delivery);
                publisher.publishRequestEvent(hre);
            }
            catch (HopsException e)
            {
                logger.error("QueryOrderUnKnowAction_ActionContext["+PrintUtil.mapToString(ActionContextUtil.getActionContextLocal())+"]");
                delivery = (Delivery)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY);
                ActionContextUtil.init();
                ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, TransactionContextUtil.getTransactionContextParam(TransactionMapKey.DELIVERY));
                queryStatusWaitAction.handleRequest();
            }
        }
        HopsRequestEvent hre = (HopsRequestEvent)TransactionContextUtil.getTransactionContextParam(TransactionMapKey.HOPS_REQUEST_EVENT);
        if(BeanUtils.isNotNull(hre))
        {
            publisher.publishRequestEvent(hre);
        }
        TransactionContextUtil.clear();
        return response;
    }
}
