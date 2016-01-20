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
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.service.StatusManagementService;
import com.yuecheng.hops.transaction.service.action.ActionHandler;
import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
import com.yuecheng.hops.transaction.service.action.context.ActionMapKey;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;

@Scope("prototype")
@Service("queryStatusEndingAction")
public class QueryStatusEndingAction extends ActionHandler
{
    private static Logger           logger = LoggerFactory.getLogger(QueryStatusEndingAction.class);

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
        try
        {
            if(BeanUtils.isNotNull(delivery) && Constant.Delivery.QUERY_FLAG_QUERY_END != delivery.getQueryFlag())
            {
                logger.debug("修改发货记录查询状态查询结束 [开始]"+delivery.getDeliveryId());
                Integer originalStatus = delivery.getQueryFlag();
                delivery = (Delivery)deliveryQueryStatusManagementServiceImpl.updateStatus(delivery.getDeliveryId(), delivery.getQueryFlag(), Constant.Delivery.QUERY_FLAG_QUERY_END);
                deliveryManagement.updateQueryFlag(delivery, originalStatus);
    //            delivery = deliveryManagement.save(delivery);
                logger.debug("修改发货记录查询状态查询结束 [结束]"+delivery.getDeliveryId());
            }
            ActionContextUtil.setActionContext(ActionMapKey.DELIVERY, delivery);
            if(BeanUtils.isNotNull(this.successor)){
                this.successor.handleRequest();
            }
        }
        catch (Exception e)
        {
            String msg = delivery!=null?delivery.getDeliveryId().toString():"delivery is null";
            logger.error("queryStatusEndingAction happen Exception caused by " + msg);
            throw new ApplicationException("transaction002016", new String[]{ExceptionUtil.getStackTraceAsString(e)}, e);//!!!
        }
    }
}
