package com.yuecheng.hops.transaction.execution.order;

import java.math.BigDecimal;
import java.util.List;

import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;

public interface OrderTransaction
{
    /**
     * 关闭订单
     * 
     * @param isAuto
     *            :手工false 系统 true
     * @param orderNo
     *            :订单号
     */
    public void closeOrder(String operatorName,Long orderNo,SupplyProductRelation upr, String userCode, String operaType);

    /**
     * 手工审核部分成功订单成功
     * 
     * @param orderNo
     */
    public void successPartSuccessOrder(Long orderNo);
    
    /**
     * 手工审核订单成功
     * 
     * @param orderNo
     */
    public void successOrder(Operator operator,Long orderNo,BigDecimal orderSuccessFee, SupplyProductRelation upr, String userCode);
    /**
     * 
     * 批量审核失败重绑流程
     * 
     * @param operator
     * @param orderNo
     * @param userCode 
     * @see
     */
    public boolean closeOrderNoUpr(String operatorName,Long orderNo, String userCode);
    
    /**
     * 处理批量人工审核订单
     * 
     * @param orderNo
     * @param supplyMerchantId
     * @param operatorName 
     * @see
     */
    public void batchCheckOrders(List<Long> orderNos, Long supplyMerchantId, String operatorName);
    
    /**
     * 批量人工审核订单之前，修改订单的人工审核标示。避免重复操作
     * @param orderNos
     * @param orderManualFlag 
     * @see
     */
    public List<Long> batchUpdateOrderManualFlag(String orderNos,Integer orderManualFlag);
    
    /**
     * 成功订单重新发货
     * 
     * @param orderNo
     * @param operatorName
     * @param type 
     * @see
     */
    public void reOpenOrder(Long orderNo, Long userCode, SupplyProductRelation upr, String operatorName, String type);
}
