package com.yuecheng.hops.transaction.config.product;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.config.entify.product.MerchantProductLevel;


/**
 * 商户分组设置服务层
 * 
 * @author Jinger 2014-03-26
 */
public interface MerchantProductLevelService
{
    /**
     * 获取所有商户产品等级列表
     * 
     * @return
     */
    public List<MerchantProductLevel> getAllMerchantProductLevel();

    /**
     * 保存商户产品等级配置
     * 
     * @param merchantProductLevel
     *            商户产品等级配置
     * @return
     */
    public MerchantProductLevel saveMerchantProductLevel(MerchantProductLevel merchantProductLevel);

    /**
     * 根据ID删除商户产品等级配置
     * 
     * @param merchantProductLevelId
     *            商户产品等级配置ID
     */
    public void deleteMerchantProductLevel(Long merchantProductLevelId);

    /**
     * 更新商户产品等级配置
     * 
     * @param merchantProductLevel
     *            商户产品等级配置
     * @return
     */
    public MerchantProductLevel updateMerchantProductLevel(MerchantProductLevel merchantProductLevel);

    /**
     * 根据ID查找商户产品等级配置
     * 
     * @param merchantProductLevelId
     *            商户产品等级配置ID
     * @return
     */
    public MerchantProductLevel getMerchantProductLevelById(Long merchantProductLevelId);

    /**
     * 根据等级查询商户产品等级配置
     * 
     * @param merchantProductLevel
     *            商户产品等级
     * @return
     */
    public MerchantProductLevel getMerchantProductLevelByLevel(Long merchantProductLevel);

    /**
     * 分页查询商户产品等级配置
     * 
     * @param searchParams
     *            查询条件Map
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @return
     */
    public YcPage<MerchantProductLevel> queryMerchantProductLevel(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize,
                                                                  BSort bsort);

    /**
     * 根据随机数获取商户产品等级配置
     * 
     * @param radomNum
     *            随机数
     * @return
     */
    public MerchantProductLevel findMerchantProductLevelByRadom(int radomNum);
}
