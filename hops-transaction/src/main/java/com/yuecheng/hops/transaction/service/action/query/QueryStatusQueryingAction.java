/*
 * 文件名：CheckOrderAction.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.action.query;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.event.HopsPublisher;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Scope("prototype")
@Service("queryStatusQueryingAction")
public class QueryStatusQueryingAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(QueryStatusQueryingAction.class);

    @Autowired
    @Qualifier("deliveryQueryStatusManagementServiceImpl")
    private StatusManagementService deliveryQueryStatusManagementServiceImpl;

    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private HopsPublisher           publisher;

    @Override
    @Transactional
    public void handleRequest()
        throws HopsException
    {
        Delivery delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
        try
        {
            logger.debug("修改查询标示(QUERY_FLAG_WAIT_QUERY->QUERY_FLAG_QUERYING) [开始]"+delivery.getDeliveryId());
            if(Constant.Delivery.QUERY_FLAG_QUERY_END != delivery.getQueryFlag() && (Constant.Delivery.DELIVERY_STATUS_FAIL != delivery.getDeliveryStatus() || Constant.Delivery.DELIVERY_STATUS_SUCCESS != delivery.getDeliveryStatus())){
                Integer originalStatus = delivery.getQueryFlag();
                delivery = (Delivery)deliveryQueryStatusManagementServiceImpl.updateStatus(
                    delivery.getDeliveryId(), delivery.getQueryFlag(),
                    Constant.Delivery.QUERY_FLAG_QUERYING);
                deliveryManagement.updateQueryFlag(delivery, originalStatus);
            }
            logger.debug("修改查询标示(QUERY_FLAG_WAIT_QUERY->QUERY_FLAG_QUERYING) [结束]"+delivery.getDeliveryId());
            
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            logger.error("queryStatusQueryingAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002017", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
