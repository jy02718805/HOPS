/*
 * 文件名：NotifyStatusTransferServiceImpl.java
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
import com.yuecheng.hops.transaction.basic.entity.NotifyStatusTransfer;
import com.yuecheng.hops.transaction.basic.repository.NotifyStatusTransferDao;
import com.yuecheng.hops.transaction.execution.status.NotifyStatusTransferService;

@Service("notifyStatusTransferService")
public class NotifyStatusTransferServiceImpl implements NotifyStatusTransferService
{
    @Autowired
    private NotifyStatusTransferDao notifyStatusTransferDao;

    @Override
    public Boolean checkStatus(Integer targetStatus, Integer originalStatus)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.NotifyStatusTransfer.OLD_NOTIFY_STATUS, new SearchFilter(EntityConstant.NotifyStatusTransfer.OLD_NOTIFY_STATUS, Operator.EQ, originalStatus));
        filters.put(EntityConstant.NotifyStatusTransfer.NEW_NOTIFY_STATUS, new SearchFilter(EntityConstant.NotifyStatusTransfer.NEW_NOTIFY_STATUS, Operator.EQ, targetStatus));
        Specification<NotifyStatusTransfer> spec = DynamicSpecifications.bySearchFilter(filters.values(), NotifyStatusTransfer.class);
        NotifyStatusTransfer notifyStatusTransfer = notifyStatusTransferDao.findOne(spec);
        return BeanUtils.isNotNull(notifyStatusTransfer);
    }

}
