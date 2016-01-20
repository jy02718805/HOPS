package com.yuecheng.hops.transaction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceRegister
{
    @Autowired
    @Qualifier("agentOrderReceptor")
    private TransactionService agentOrderReceptor;
    
    @Autowired
    @Qualifier("agentOrderQuerier")
    private TransactionService agentOrderQuerier;
    
    @Autowired
    @Qualifier("agentOrderBinder")
    private TransactionService agentOrderBinder;
    
    @Autowired
    @Qualifier("supplyOrderSender")
    private TransactionService supplyOrderSender;
    
    @Autowired
    @Qualifier("supplyOrderQuerier")
    private TransactionService supplyOrderQuerier;
    
    @Autowired
    @Qualifier("supplyOrderResulter")
    private TransactionService supplyOrderResulter;
    
    @Autowired
    @Qualifier("agentOrderNotificator")
    private TransactionService agentOrderNotificator;
    
    @Autowired
    @Qualifier("agentAccountQuerier")
    private TransactionService agentAccountQuerier;
    
    //预下单检查
    @Autowired
    @Qualifier("agentPrepareOrderReceptor")
    private TransactionService agentPrepareOrderReceptor;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceRegister.class);
    
    public TransactionService getService(String code)
    {
        if(TransactionCodeConstant.AGENT_ORDER_RECEPTOR_CODE.equalsIgnoreCase(code)){
            LOGGER.debug("agentOrderReceptor hashcode=["+agentOrderReceptor.hashCode()+"]");
            return agentOrderReceptor;
        }else if(TransactionCodeConstant.AGENT_QUERY_ORDER_CODE.equalsIgnoreCase(code)){
            return agentOrderQuerier;
        }else if(TransactionCodeConstant.BIND_ORDER_CODE.equalsIgnoreCase(code)){
            return agentOrderBinder;
        }else if(TransactionCodeConstant.SEND_ORDER_CODE.equalsIgnoreCase(code)){
            return supplyOrderSender;
        }else if(TransactionCodeConstant.SUPPLY_QUERY_ORDER_CODE.equalsIgnoreCase(code)){
            return supplyOrderQuerier;
        }else if(TransactionCodeConstant.SUPPLY_NOTIFY_ORDER_CODE.equalsIgnoreCase(code)){
            return supplyOrderResulter;
        }else if(TransactionCodeConstant.AGENT_NOTIFY_ORDER_CODE.equalsIgnoreCase(code)){
            return agentOrderNotificator;
        }else if(TransactionCodeConstant.AGENT_QUERY_ACCOUNT_CODE.equalsIgnoreCase(code)){
            return agentAccountQuerier;
        }else if(TransactionCodeConstant.AGENT_PRODUCT_STATUS_CODE.equalsIgnoreCase(code)){
            return agentPrepareOrderReceptor;
        }
        return null;
    }
}
