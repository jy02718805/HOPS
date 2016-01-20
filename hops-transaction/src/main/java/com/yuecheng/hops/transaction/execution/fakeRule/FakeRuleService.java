package com.yuecheng.hops.transaction.execution.fakeRule;


import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;


public interface FakeRuleService
{
    public void updateOrderNotifyStatus(Long orderNo);
    
    
    public AgentQueryFakeRule checkMerchantIsFake(Long merchantId);
}
