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

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Scope("prototype")
@Service("queryStatusNeedQueryAction")
public class QueryStatusNeedQueryAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(QueryStatusNeedQueryAction.class);

    @Autowired
    @Qualifier("deliveryQueryStatusManagementServiceImpl")
    private StatusManagementService deliveryQueryStatusManagementServiceImpl;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Override
    public void handleRequest()
        throws HopsException
    {
        Delivery delivery = (Delivery)ActionContextUtil.getActionContextParam(ActionMapKey.DELIVERY);
        logger.debug("queryStatusNeedQueryAction_delivery["+delivery.toString()+"]");
        try
        {
            logger.debug("修改发货记录查询标示(QUERY_FLAG_QUERYING->QUERY_FLAG_NEED_QUERY) [开始]"+delivery.getDeliveryId());
            Integer originalStatus = delivery.getQueryFlag();
            delivery = (Delivery)deliveryQueryStatusManagementServiceImpl.updateStatus(
                delivery.getDeliveryId(), delivery.getQueryFlag(),
                Constant.Delivery.QUERY_FLAG_NEED_QUERY);
            deliveryManagement.updateQueryFlag(delivery, originalStatus);
            logger.debug("修改发货记录查询标示(QUERY_FLAG_QUERYING->QUERY_FLAG_NEED_QUERY) [结束]"+delivery.getDeliveryId());
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY,delivery);
        }
        catch (Exception e)
        {
            logger.error("queryStatusWaitAction happen Exception caused by "+ ExceptionUtil.getStackTraceAsString(e));
            throw new ApplicationException("transaction002018", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);
        }
    }
}
