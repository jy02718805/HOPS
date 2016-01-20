package com.yuecheng.hops.transaction.service.delivery;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.parameter.entity.SupplyDupNumRule;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.vo.DeliveryStatisticsVo;


public interface DeliveryManagement
{

    /**
     * 通过订单编号查询对应的发货记录
     * 
     * @param orderNo
     * @return
     */
    public abstract List<Delivery> findDeliveryByOrderNo(Long orderNo);

    /**
     * 绑定供货商时，需要过滤已经绑定过的商户记录
     * 
     * @param orderNo
     * @return
     * @see
     */
    public List<BigDecimal> getDeliveryMerchantIdByOrderNo(Long orderNo);

    /**
     * 根据订单编号、商户ID、发货状态。查询有无发货记录。如果有，则在发货流程中不生成新的发货记录。如果没有，则生成新发货记录
     * 
     * @param orderNo
     * @param merchantId
     * @param deliveryStatus
     * @return
     */
    public abstract List<Delivery> findDeliveryByParams(Long orderNo, Long merchantId,
                                                        Integer deliveryStatus);

    /**
     * Handler里面对发货记录进行的增加、修改操作。
     * 
     * @param delivery
     * @return
     */
    public Delivery save(Delivery delivery);

    public void save(List<Delivery> deliverys);

    /**
     * 根据主键查询发货记录
     * 
     * @param deliveryId
     * @return
     */
    public Delivery findDeliveryById(Long deliveryId);

    public Delivery findDeliveryByIdNoTransaction(Long deliveryId);

    /**
     * 供货商通知时使用
     * 
     * @param deliveryId
     * @return
     * @see
     */
    public Delivery findDeliveryBySupplyOrderNo(Long merchantId, String supplyMerchantOrderNo);

    public YcPage<Map<String, Object>> queryDeliveryList(String beginDate, String endDate,
                                                         String deliverySatus, String carrierNo,
                                                         String provinceNO, String supplyMerchant,
                                                         String agentMerchant,
                                                         String supply_order_no,
                                                         String agent_order_no, String order_no,
                                                         String parValue, String usercode,
                                                         int pageNumber, int pageSize,
                                                         String queryFlag, String businessType);

    /**
     * 查询未完成的发货记录
     * 
     * @param orderNo
     * @return
     * @see
     */
    public List<Delivery> findUnfinishedDeliveryList(Long orderNo);

    /**
     * 循环查询发货记录时，修改发货记录的查询状态和查询时间
     * 
     * @param deliveryId
     * @param queryflag
     * @param queryTime
     * @return
     * @see
     */
    public Integer updateDeliveryNextQuery(Long deliveryId, Integer queryflag, Date queryTime);

    /**
     * 页面统计供货记录
     * 
     * @param orderParameter
     * @param type
     * @return
     * @see
     */
    public DeliveryStatisticsVo statisticsDeliveryInfo(String beginDate, String endDate,
                                                       String deliveryStatus, String carrierInfo,
                                                       String province, String supplymerchant,
                                                       String agentmerchant, String supply_no,
                                                       String agent_no, String parValue,
                                                       String usercode, String orderNo,
                                                       String businessType);

    public void updateDeliveryStatus(Delivery delivery, Integer originalStatus)
        throws Exception;

    public void updateQueryFlag(Delivery delivery, Integer originalStatus)
        throws Exception;

    public void updateQueryFlag(Delivery delivery, Integer originalStatus, Date queryTime)
        throws Exception;

    public void beginQuery(Delivery delivery);

    public void closeDelivery(Long deliveryId);

    public void updateSupplyMerchantOrderNo(String supplyMerchantOrderNo, Long deliveryId);

    public void updateParams(String supplyMerchantOrderNo, String deliveryResult, Long deliveryId,
                             String queryMsg);

    public void addQueryTimes(Long queryTimes, Long deliveryId);

    public void updateDeliveryStatus(Integer targetStatus, Integer originalStatus, Long deliveryId)
        throws Exception;

    public void updateQueryMsg(Long devliveryId, String queryMsg);

    public boolean checkSupplyDupNumRule(String userCode, SupplyDupNumRule supplyDupNumRule);

    List<Long> batchUpdateQueryFlag(String deliveryIds, Integer queryFlag);
    
    /**
     * 绑定前检查此订单是否存在成功、未完成发货记录
     */
    public Integer countUnFailDeliveryNum(Long orderNo);
}