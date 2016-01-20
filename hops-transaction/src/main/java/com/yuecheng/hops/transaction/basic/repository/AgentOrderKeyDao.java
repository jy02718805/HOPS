package com.yuecheng.hops.transaction.basic.repository;




public interface AgentOrderKeyDao
{
    public void saveKey(Long merchantId, String merchantOrderNo) throws Exception;
}
