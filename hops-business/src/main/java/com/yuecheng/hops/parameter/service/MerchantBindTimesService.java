package com.yuecheng.hops.parameter.service;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.parameter.entity.MerchantBindTimes;


public interface MerchantBindTimesService
{
    public MerchantBindTimes getTimes(Long id);

    public YcPage<MerchantBindTimes> queryMerchantBindTimes(Map<String, Object> searchParams,
                                                                    int pageNumber, int pageSize,
                                                                    String sortType);

    public MerchantBindTimes saveMerchantBindTimes(MerchantBindTimes merchantBindTimes);

    public MerchantBindTimes updateMerchantBindTimes(MerchantBindTimes merchantBindTimes);
    // 取得缓存中参数
    public MerchantBindTimes getMerchantBindTimesByMerchantId(Long merchantId);
    //取得所有参数
    public List<MerchantBindTimes> findAll();
}
