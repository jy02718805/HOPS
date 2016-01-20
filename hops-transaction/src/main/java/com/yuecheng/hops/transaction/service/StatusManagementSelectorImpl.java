/*
 * 文件名：StatusManagementSelectorImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;


@Service("statusManagementSelector")
public class StatusManagementSelectorImpl implements StatusManagementSelector
{
    @Autowired
    @Qualifier("orderStatusManagementServiceImpl")
    private StatusManagementService orderStatusManagementServiceImpl;

    @Autowired
    @Qualifier("notifyStatusManagementServiceImpl")
    private StatusManagementService notifyStatusManagementServiceImpl;

    @Autowired
    @Qualifier("deliveryStatusManagementServiceImpl")
    private StatusManagementService deliveryStatusManagementServiceImpl;

    @Autowired
    @Qualifier("deliveryQueryStatusManagementServiceImpl")
    private StatusManagementService deliveryQueryStatusManagementServiceImpl;

    @Override
    public StatusManagementService select(String activeTransferType)
    {
        StatusManagementService service = null;
        if (activeTransferType.equalsIgnoreCase(Constant.StatusEventType.ORDER))
        {
            service = orderStatusManagementServiceImpl;
        }
        else if (activeTransferType.equalsIgnoreCase(Constant.StatusEventType.DELIVERY))
        {
            service = deliveryStatusManagementServiceImpl;
        }
        else if (activeTransferType.equalsIgnoreCase(Constant.StatusEventType.DELIVERY_QUERY))
        {
            service = deliveryQueryStatusManagementServiceImpl;
        }
        else if (activeTransferType.equalsIgnoreCase(Constant.StatusEventType.NOTIFY))
        {
            service = notifyStatusManagementServiceImpl;
        }
        else
        {
            throw new ApplicationException("transaction001029", new String[] {activeTransferType});
        }
        return service;
    }

}
