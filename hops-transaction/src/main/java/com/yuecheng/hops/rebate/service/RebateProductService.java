package com.yuecheng.hops.rebate.service;


import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.rebate.entity.RebateProduct;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;


public interface RebateProductService
{

    /**
     * 保存返佣产品
     * 
     * @param rebateProduct
     *            待保存的返佣产品
     * @return
     */
    public RebateProductAssist saveRebateProduct(RebateProduct rebateProduct);

    /**
     * 根据ID删除返佣产品
     * 
     * @param rebateProductId
     *            返佣产品ID
     */
    public void deleteRebateProduct(Long id);

    /**
     * 跟去返佣产品区间ID删除产品
     * 
     * @param rebateProductId
     *            返佣产品区间ID
     */
    public void deleteRebateProductByRProductId(String rebateProductId);

    /**
     * 根据ID查找返佣产品
     * 
     * @param rebateProductId
     *            返佣产品ID
     * @return
     */
    public RebateProductAssist queryRebateProductById(Long id);

    /**
     * 根据返佣产品区间ID获取返佣产品列表并组装产品名称
     * 
     * @param rebateProductId
     * @return
     */
    public String getRebateProductStr(String rebateProductId);

    /**
     * 根据返佣产品区间ID、商户、选中的产品信息保存返佣产品
     * 
     * @param rebateProductId
     * @param merchant
     * @param productIds
     * @return 返回产品区间ID
     */
    public String saveRebateProductIds(String rebateProductId, Merchant merchant, String productIds);
}
