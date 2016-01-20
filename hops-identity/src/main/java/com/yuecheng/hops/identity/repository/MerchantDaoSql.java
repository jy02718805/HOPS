package com.yuecheng.hops.identity.repository;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.merchant.Merchant;


public interface MerchantDaoSql
{

    /**
     * 获取该商户的所有子节点以及子孙辈节点（不包括自己）
     * 
     * @param id
     * @return
     */
    public List<Merchant> queryChildMerchantTreeListById(Long id);

    /**
     * 获取该商户的所有子节点以及子孙辈节点（包括自己）
     * 
     * @param pid
     * @return
     */
    public List<Merchant> queryChildMerchantTreeListByPId(Long pid);

    /**
     * 获取该商户的所有父节点以及长辈节点（包括自己）
     * 
     * @param id
     * @return
     */
    public List<Merchant> queryParentMerchantTreeListById(Long id);

    /**
     * 获取该商户的所有父节点以及长辈节点（不包括自己）
     * 
     * @param id
     * @return
     */
    public List<Merchant> queryParentMerchantTreeListByPId(Long pid);

    public YcPage<Merchant> queryPageMerchant(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize, String sortType, MerchantType type,
                                              String name);

    /**
     * 模糊查询商户名称
     */
    public List<Merchant> queryMerchantsByName(String merchantName);
}
