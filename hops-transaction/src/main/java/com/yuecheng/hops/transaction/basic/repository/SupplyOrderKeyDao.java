package com.yuecheng.hops.transaction.basic.repository;




public interface SupplyOrderKeyDao
{
    public void saveKey(Long merchantId, Long deliveryId) throws Exception;
    
    public void deleteKey(Long merchantId, Long deliveryId);
}
