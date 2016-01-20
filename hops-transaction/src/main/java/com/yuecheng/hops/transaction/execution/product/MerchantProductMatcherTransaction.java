package com.yuecheng.hops.transaction.execution.product;


import java.math.BigDecimal;

import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;


public interface MerchantProductMatcherTransaction
{
    AirtimeProduct matchAirtimeProduct(NumSection numSection, BigDecimal face, int businessType);
    
    AgentProductRelation matchAgentProduct(Long merchantId, AirtimeProduct product);

    SupplyProductRelation matchSupplyProduct(Long merchantId, NumSection numSection, BigDecimal face, int resourceType);
    
    AirtimeProduct matchAirtimeProduct(String agentProdId, Long merchantId);
}
