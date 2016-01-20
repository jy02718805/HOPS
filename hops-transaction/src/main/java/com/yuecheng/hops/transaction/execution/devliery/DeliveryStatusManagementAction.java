package com.yuecheng.hops.transaction.execution.devliery;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.injection.service.MerchantRequestService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.repository.DeliveryJpaDao;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Service("deliveryStatusManagementAction")
public class DeliveryStatusManagementAction
{
    
    private static Logger                  logger = LoggerFactory.getLogger(DeliveryStatusManagementAction.class);
    
    @Autowired
    private DeliveryManagement       deliveryManagement;
    
    @Autowired
    private DeliveryJpaDao deliveryJpaDao;

    @Autowired
    private MerchantRequestService merchantRequestService;
    
    @Autowired
    private OrderManagement         orderManagement;
    
    private final static Integer SUCCESS = 1;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Long> updateDeliveryStatusToSENDING(List<Delivery> deliveryings)
    {
        List<Long> result = new ArrayList<Long>();
        for (Iterator<Delivery> iterator = deliveryings.iterator(); iterator.hasNext();)
        {
            Delivery delivery = iterator.next();
            try
            {
                int updateResult = deliveryJpaDao.updateDeliveryStatus(delivery.getDeliveryId(), Constant.Delivery.DELIVERY_STATUS_SENDING, Constant.Delivery.DELIVERY_STATUS_WAIT);
                if(SUCCESS.equals(updateResult)){
                    result.add(delivery.getDeliveryId());
                }
            }
            catch (Exception e)
            {
                logger.error("fail to updateDeliveryStatusToSENDING with deliveryId["+delivery.getDeliveryId()+"]" + ExceptionUtil.getStackTraceAsString(e));
            }
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Long> updateDeliveryQueryFlagToQUERYING(List<Delivery> deliverySended)
    {
        List<Long> deliveryIds = new ArrayList<Long>();
        
        for (Iterator<Delivery> iterator = deliverySended.iterator(); iterator.hasNext();)
        {
            Delivery delivery = iterator.next();
            try
            {
                Date createDeliveryTime = delivery.getDeliveryStartTime();
                Date now = new Date();
                Date queryTime = merchantRequestService.getNextQueryDate(createDeliveryTime, now, delivery.getMerchantId(), Constant.Interface.INTERFACE_TYPE_QUERY_ORDER);
                Integer result = deliveryManagement.updateDeliveryNextQuery(delivery.getDeliveryId(), Constant.Delivery.QUERY_FLAG_QUERYING, queryTime);
                if(SUCCESS.equals(result)){
                    deliveryIds.add(delivery.getDeliveryId());
                }
            }
            catch (Exception e)
            {
                logger.error("fail to updateDeliveryQueryFlagToQUERYING with deliveryId["+delivery.getDeliveryId()+"]" + ExceptionUtil.getStackTraceAsString(e));
            }
        }
        
        return deliveryIds;
    }
}
