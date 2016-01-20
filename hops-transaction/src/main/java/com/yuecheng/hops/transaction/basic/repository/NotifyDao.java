package com.yuecheng.hops.transaction.basic.repository;


import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.Notify;


public interface NotifyDao
{
    public List<Notify> getNotifyOrdersByStatus(Integer notifyStatus, Integer scannerNotifyNum);
    
    public Notify findNotifyByOrderNo(Long orderNo);
}
