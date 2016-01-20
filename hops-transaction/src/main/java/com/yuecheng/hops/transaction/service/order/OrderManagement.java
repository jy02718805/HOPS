package com.yuecheng.hops.transaction.service.order;


import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;


public interface OrderManagement
{

    /**
     * 保存订单
     * 
     * @param order
     * @return
     */
    public Order save(Order order);

    /**
     * 根据订单编号查询订单Vo对象
     * 
     * @param orderNo
     * @return
     * @see
     */
    public OrderVo getOrderVoById(Long orderNo);

    /**
     * 根据ID查询ORDER
     * 
     * @param orderNo
     * @return
     */
    public Order findOne(Long orderNo);

    public Order findOneNoTransactional(Long orderNo);

    /**
     * 根据商户订单号查询订单信息
     * 
     * @param merchantOrderNo
     * @return
     */
    public Order findOrderByMerchantOrderNo(Long merchantId, String merchantOrderNo);

    /**
     * 订单检查，根据商户号和商户订单号
     * 
     * @param merchantId
     * @param merchantOrderNo
     * @return
     * @see
     */
    public Boolean checkIsExit(Long merchantId, String merchantOrderNo);

    /**
     * 保存商户号和商户订单号
     */
    public void saveAgentOrderKey(Long merchantId, String merchantOrderNo)
        throws Exception;

    /**
     * 通知成功以后，修改订单通知状态
     * 
     * @see
     */
    public void updateOrderNotifyStatus(Long orderNo, int notifyStatus);

    /**
     * 订单分析者
     * 
     * @param orderNo
     * @see
     */
    public void orderAnalysts(Long orderNo, Integer originalOrderStatus);

    public void updateOrderSuccess(Order order, Integer originalStatus)
        throws Exception;

    /**
     * 人工审核排重
     * 
     * @param orderNo
     * @param status
     * @throws Exception
     * @see
     */
    public void updateManualFlag(Long orderNo, Integer originalStatus, Integer targerStatus)
        throws Exception;

    /**
     * 回滚人工审核标示
     * 
     * @param orderNo
     * @param targerStatus
     * @throws Exception
     * @see
     */
    public void updateManualFlag(Long orderNo, Integer targerStatus);

    /**
     * 修改订单原因
     * 
     * @see
     */
    public Integer updateOrderReason(Integer preSuccessStatus, String orderReason, Long orderNo);
    

}