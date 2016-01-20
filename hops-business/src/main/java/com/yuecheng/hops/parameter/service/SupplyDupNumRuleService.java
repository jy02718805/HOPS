package com.yuecheng.hops.parameter.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.parameter.entity.SupplyDupNumRule;


public interface SupplyDupNumRuleService
{
    public SupplyDupNumRule getSupplyDupNumRule(Long id);

    public YcPage<SupplyDupNumRule> querySupplyDupNumRule(Map<String, Object> searchParams,
                                                                    int pageNumber, int pageSize,
                                                                    String sortType);

    public SupplyDupNumRule saveSupplyDupNumRule(SupplyDupNumRule supplyDupNumRule);

    public SupplyDupNumRule updateSupplyDupNumRule(SupplyDupNumRule supplyDupNumRule);
    // 取得缓存中参数
    public SupplyDupNumRule getSupplyDupNumRuleByMerchantId(Long merchantId);
    //取得所有参数
    public List<SupplyDupNumRule> findAll();
    
    public void deleteSupplyDupNumRule(Long id);
}
