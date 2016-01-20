package com.yuecheng.hops.identity.service.merchant;


import java.util.Map;

import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.merchant.Merchant;


public interface MerchantPageQuery
{

    /**
     * 根据商户的类型与名称模糊查询
     * 
     * @param type
     * @param name
     * @return
     */
    public abstract YcPage<Merchant> queryPageMerchant(Map<String, Object> searchParams,
                                                       int pageNumber, int pageSize,
                                                       String sortType, MerchantType type,
                                                       String name);

}