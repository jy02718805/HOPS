package com.yuecheng.hops.transaction.config;


import java.util.Date;


public interface SupplyQueryTimeService
{
    /**
     * 根据条件获取上游商户下次查询时间
     * 
     * @param merchantId
     *            商户ID
     * @param pastTime
     *            时间间隔
     * @param now
     *            当前时间
     * @return
     */
    public Date getQueryTime(Long merchantId, Date pastTime, Date now);
}
