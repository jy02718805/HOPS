package com.yuecheng.hops.transaction.execution.product.action;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;


@Service("deliveryFilterAction")
public class DeliveryFilterAction
{
    @Autowired
    private DeliveryManagement deliveryManagement;

    /**
     * 如果之前有绑定记录，需要将已经绑定过的上游排除
     * 
     * @return
     */
    public List<SupplyProductRelation> filter(Order ycOrder, List<SupplyProductRelation> sortUprs)
    {
        List<BigDecimal> merchantIds = deliveryManagement.getDeliveryMerchantIdByOrderNo(ycOrder.getOrderNo());
        Map<Long, Integer> merchantBindTimes = new HashMap<Long, Integer>();
        for (Iterator iterator = merchantIds.iterator(); iterator.hasNext();)
        {
            BigDecimal merchantId = (BigDecimal)iterator.next();
            if(BeanUtils.isNull(merchantBindTimes.get(merchantId.longValue()))){
                merchantBindTimes.put(merchantId.longValue(), 1);
            }else{
                Integer times = merchantBindTimes.get(merchantId.longValue());
                merchantBindTimes.put(merchantId.longValue(), times + 1);
            }
        }
        List<SupplyProductRelation> deleteList = new ArrayList<SupplyProductRelation>();
        for (Iterator<SupplyProductRelation> iterator3 = sortUprs.iterator(); iterator3.hasNext();)
        {
            SupplyProductRelation upProductRelation = iterator3.next();
            if (!BeanUtils.isNull(merchantBindTimes.get(upProductRelation.getIdentityId())))
            {
                if(upProductRelation.getBindTimes() - merchantBindTimes.get(upProductRelation.getIdentityId()) <= 0){
                    deleteList.add(upProductRelation);
                }
            }
        }
        for (SupplyProductRelation supplyProductRelation : deleteList)
        {
            sortUprs.remove(supplyProductRelation);
        }
        return sortUprs;
    }
}
