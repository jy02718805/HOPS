package com.yuecheng.hops.transaction.basic.repository;


import java.math.BigDecimal;
import java.util.List;

import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.vo.DeliveryStatisticsVo;


public interface DeliveryDao
{
    List<Delivery> selectDeliveryByIntervalTime(long intervalTime, Integer status);

    List<Delivery> findSendOrders(Integer deliveryStatus);

    List<Delivery> findQueryOrders(Integer deliveryStatus, Integer queryFlag);

    List<BigDecimal> getDeliveryMerchantIdByOrderNo(Long orderNo);

    Delivery findDeliveryBySupplyOrderNo(Long merchantId, String supplyMerchantOrderNo);

    DeliveryStatisticsVo statisticsDeliveryInfo(String beginDate,
    		String endDate, String deliveryStatus, String carrierInfo, String province, String supplymerchant, String agentmerchant,
    		String supply_no, String agent_no,  String parValue, String usercode, String orderNo, String businessType);
    
    public void updateDeliveryStatus(Delivery delivery, Integer originalStatus) throws Exception;
    
    public void updateQueryFlag(Delivery delivery, Integer originalQueryFlag) throws Exception;
    
    public List<BigDecimal> findCloseDeliverys();
    
    public Integer countUnFailDeliveryNum(Long orderNo);
}
