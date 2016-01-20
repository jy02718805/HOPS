package com.yuecheng.hops.transaction.service.notify.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.basic.repository.NotifyDao;
import com.yuecheng.hops.transaction.basic.repository.NotifyJpaDao;
import com.yuecheng.hops.transaction.service.notify.NotifyService;


/**
 * 订单相关操作服务层
 * 
 * @author Jinger 2014-03-07
 */
@Service("notifyService")
public class NotifyServiceImpl implements NotifyService
{

    @Autowired
    private NotifyJpaDao     notifyJpaDao;
    
    @Autowired
    private NotifyDao     notifyDao;
    
    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    private static Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);

    @Override
    @Transactional
    public Notify save(Notify notify)
    {
        return notifyJpaDao.save(notify);
    }

    @Override
    @Transactional
    public Notify findNotifyByOrderNo(Long orderNo)
    {
        Notify notify = notifyDao.findNotifyByOrderNo(orderNo);
        return notify;
    }

    @Override
    public List<Notify> findNotifyOrders()
    {
        ParameterConfiguration parameterConfiguration = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.SCANNER_NOTIFY_NUM);
        int scannerNotifyNum = 0;
        if(BeanUtils.isNull(parameterConfiguration))
        {
            scannerNotifyNum = 15;
        }
        else
        {
            scannerNotifyNum = Integer.valueOf(parameterConfiguration.getConstantValue()); 
        }
        
        List<Notify> notifys = notifyDao.getNotifyOrdersByStatus(Constant.NotifyStatus.WAIT_NOTIFY, scannerNotifyNum);
        return notifys;
    }

    @Override
    @Transactional
    public Notify findNotifyById(Long id)
    {
        Notify notify = notifyJpaDao.findOne(id);
        return notify;
    }

    
    @Override
    @Transactional
    public Integer reNotify(Integer status, Long orderNo) {
        Integer i =notifyJpaDao.updateReNotify(orderNo, status);
        return i;
    }
}
