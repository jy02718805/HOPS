package com.yuecheng.hops.transaction.execution.product.action;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.parameter.entity.SupplyDupNumRule;
import com.yuecheng.hops.parameter.service.SupplyDupNumRuleService;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;


@Service("SupplyDupNumFilterAction")
public class SupplyDupNumFilterAction
{
    @Autowired
    private MerchantService merchantService;
    
    @Autowired
    private DeliveryManagement deliveryManagement;
    
    @Autowired
    private SupplyDupNumRuleService supplyDupNumRuleService;

    public List<SupplyProductRelation> filter(List<SupplyProductRelation> sortUprs, String userCode)
    {
        List<SupplyProductRelation> deleteList = new ArrayList<SupplyProductRelation>();
        for (Iterator<SupplyProductRelation> iterator3 = sortUprs.iterator(); iterator3.hasNext();)
        {
            SupplyProductRelation upProductRelation = iterator3.next();
            SupplyDupNumRule supplyDupNumRule = supplyDupNumRuleService.getSupplyDupNumRuleByMerchantId(upProductRelation.getIdentityId());
            if(BeanUtils.isNotNull(supplyDupNumRule)){
                if(deliveryManagement.checkSupplyDupNumRule(userCode, supplyDupNumRule)){
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
