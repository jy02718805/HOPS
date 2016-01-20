package com.yuecheng.hops.transaction.service.order.builder.support;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;
import com.yuecheng.hops.transaction.basic.entity.Notify;
import com.yuecheng.hops.transaction.service.notify.NotifyService;


@Service("notifyBuilder")
public class NotifyBuilder
{

    @Autowired
    private NotifyService notifyService;
    
    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    @Transactional
    public Notify createNotify(Long orderNo, Long merchantId, String url,Long orderType, String failedUrl)
    {
        ParameterConfiguration parameterConfiguration = parameterConfigurationService.getParameterConfigurationByKey(Constant.ParameterConfiguration.NOTIFY_MAX_TIMES);
        Long notifyMaxTimes = null;
        try
        {
            notifyMaxTimes = Long.valueOf(parameterConfiguration.getConstantUnitValue());
        }
        catch (Exception e)
        {
            notifyMaxTimes = 3l;
        }
        
        Notify notify = new Notify();
        notify.setOrderNo(orderNo);
        notify.setMerchantId(merchantId);
        notify.setNotifyUrl(url);
        notify.setFailedUrl(failedUrl);
        notify.setNotifyStatus(Constant.NotifyStatus.NO_NEED_NOTIFY);
        notify.setNotifyCntr(0l);
        notify.setNeedNotify(0l);
        notify.setLimitedCntr(notifyMaxTimes);
        notify.setCreateTime(new Date());
        notify.setOrderType(orderType);
        notify = notifyService.save(notify);
        return notify;
    }
}
