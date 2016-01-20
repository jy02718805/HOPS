/*
 * 文件名：QueryStatusTransferServiceImpl.java
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
import com.yuecheng.hops.transaction.basic.entity.DeliveryQueryStatusTransfer;
import com.yuecheng.hops.transaction.basic.repository.DeliveryQueryStatusTransferDao;
import com.yuecheng.hops.transaction.execution.status.QueryStatusTransferService;

@Service("queryStatusTransferService")
public class QueryStatusTransferServiceImpl implements QueryStatusTransferService
{
    @Autowired
    private DeliveryQueryStatusTransferDao deliveryQueryStatusTransferDao;

    @Override
    public Boolean checkStatus(Integer targetStatus, Integer originalStatus)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.DeliveryQueryStatusTransfer.OLD_QUERY_STATUS, new SearchFilter(EntityConstant.DeliveryQueryStatusTransfer.OLD_QUERY_STATUS, Operator.EQ, originalStatus));
        filters.put(EntityConstant.DeliveryQueryStatusTransfer.NEW_QUERY_STATUS, new SearchFilter(EntityConstant.DeliveryQueryStatusTransfer.NEW_QUERY_STATUS, Operator.EQ, targetStatus));
        Specification<DeliveryQueryStatusTransfer> spec = DynamicSpecifications.bySearchFilter(filters.values(), DeliveryQueryStatusTransfer.class);
        DeliveryQueryStatusTransfer deliveryQueryStatusTransfer = deliveryQueryStatusTransferDao.findOne(spec);
        return BeanUtils.isNotNull(deliveryQueryStatusTransfer);
    }

}
