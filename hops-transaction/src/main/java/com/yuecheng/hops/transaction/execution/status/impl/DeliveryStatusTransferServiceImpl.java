/*
 * 文件名：DeliveryStatusTransferServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.execution.status.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.basic.entity.DeliveryStatusTransfer;
import com.yuecheng.hops.transaction.basic.repository.DeliveryStatusTransferDao;
import com.yuecheng.hops.transaction.execution.status.DeliveryStatusTransferService;

@Service("deliveryStatusTransferService")
public class DeliveryStatusTransferServiceImpl implements DeliveryStatusTransferService
{
    @Autowired
    private DeliveryStatusTransferDao deliveryStatusTransferDao;
    
    @Override
    public Boolean checkStatus(Integer targetStatus, Integer originalStatus)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.DeliveryStatusTransfer.OLD_DELIVERY_STATUS, new SearchFilter(EntityConstant.DeliveryStatusTransfer.OLD_DELIVERY_STATUS, Operator.EQ, originalStatus));
        filters.put(EntityConstant.DeliveryStatusTransfer.NEW_DELIVERY_STATUS, new SearchFilter(EntityConstant.DeliveryStatusTransfer.NEW_DELIVERY_STATUS, Operator.EQ, targetStatus));
        Specification<DeliveryStatusTransfer> spec = DynamicSpecifications.bySearchFilter(filters.values(), DeliveryStatusTransfer.class);
        DeliveryStatusTransfer deliveryStatusTransfer = deliveryStatusTransferDao.findOne(spec);
        return BeanUtils.isNotNull(deliveryStatusTransfer);
    }

}
