package com.yuecheng.hops.transaction.execution.product.action;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;


@Service("scoreFilterAction")
public class ScoreFilterAction
{
    @Autowired
    private DeliveryManagement          deliveryManagement;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    /**
     * 排序获取得分最高的供货商
     * 
     * @param uprs
     * @param dpr
     * @return
     */
    public SupplyProductRelation filter(List<SupplyProductRelation> uprs, Order order)
    {
        AgentProductRelation dpr = agentProductRelationService.queryAgentProductRelationByParams(
            order.getProductId(), order.getMerchantId(),Constant.AgentProductStatus.OPEN_STATUS);
        List<SupplyProductRelation> sort_uprs = new ArrayList<SupplyProductRelation>();
        List<SupplyProductRelation> result = new ArrayList<SupplyProductRelation>();
        for (Iterator<SupplyProductRelation> iterator2 = uprs.iterator(); iterator2.hasNext();)
        {
            SupplyProductRelation upProductRelation = iterator2.next();
            // 价格分 价格分=100*(销售价格折扣-成本价格折扣)/(1-成本价格折扣）
//            BigDecimal discount_point = order.getProductSaleDiscount().subtract(
//                upProductRelation.getDiscount()).divide(
//                new BigDecimal(1).subtract(upProductRelation.getDiscount()), mc).multiply(
//                new BigDecimal(100));
            
            BigDecimal subtract = BigDecimalUtil.sub(new BigDecimal("1.2"), upProductRelation.getDiscount(), DecimalPlaces.FOUR.value(), BigDecimal.ROUND_HALF_UP);
            BigDecimal discount_point = BigDecimalUtil.sub(order.getProductSaleDiscount(), upProductRelation.getDiscount(), DecimalPlaces.FOUR.value(), BigDecimal.ROUND_HALF_UP);
            if(subtract.compareTo(new BigDecimal("0")) == 0)
            {
                discount_point = new BigDecimal("0");
            }
            else
            {
                discount_point = BigDecimalUtil.divide(discount_point, subtract,DecimalPlaces.FOUR.value(), BigDecimal.ROUND_HALF_UP);
                discount_point = BigDecimalUtil.multiply(discount_point, new BigDecimal("100"), DecimalPlaces.FOUR.value(), BigDecimal.ROUND_HALF_UP);
            }
            // 总分 = 质量分*质量比重 + 价格分*价格比重
//            BigDecimal total_point = discount_point.multiply(dpr.getDiscountWeight()).add(upProductRelation.getQuality().multiply(dpr.getQualityWeight()));
            BigDecimal quality = BigDecimalUtil.multiply(upProductRelation.getQuality(), dpr.getQualityWeight(), DecimalPlaces.FOUR.value(), BigDecimal.ROUND_HALF_UP);
            BigDecimal discount = BigDecimalUtil.multiply(discount_point, dpr.getDiscountWeight(), DecimalPlaces.FOUR.value(), BigDecimal.ROUND_HALF_UP);
            BigDecimal total_point = BigDecimalUtil.add(quality, discount, DecimalPlaces.FOUR.value(), BigDecimal.ROUND_HALF_UP);
            upProductRelation.setTotalPoint(total_point);
            sort_uprs.add(upProductRelation);
        }
        if (sort_uprs.size() > 0)
        {
            // 排序
            Collections.sort(sort_uprs, new SupplyProductRelation());
            SupplyProductRelation upr = sort_uprs.get(sort_uprs.size() - 1);
            sort_uprs.remove(upr);
            result.add(upr);
            for (int i = 0; i < sort_uprs.size(); i++ )
            {
                if(upr.getTotalPoint().compareTo(sort_uprs.get(i).getTotalPoint()) == 0){
                    result.add(sort_uprs.get(i));
                }
            }
            Random r = new Random();
            Integer index = r.nextInt(result.size());
            upr = result.get(index);
            return upr;
        }
        else
        {
            return null;
        }
    }
}
