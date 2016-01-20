/*
 * 文件名：RebateProductQueryManager.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月23日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.rebate.service;


import java.util.List;

import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.rebate.entity.RebateProduct;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;


public interface RebateProductQueryManager
{

    /**
     * 根据返佣产品区间ID获取返佣产品列表
     * 
     * @param rebateProductId
     *            返佣产品区间ID
     * @return
     */
    public  List<RebateProductAssist> queryProductsByRProductId(String rebateProductId);

    /**
     * 根据返佣产品区间ID列表获取返佣产品列表
     * 
     * @param rebateProductIds
     * @return
     */
    public  List<RebateProductAssist> queryProductsByRProductId(List<String> rebateProductIds);

    /**
     * 根据商户产品列表和返佣产品列表获取未配置的商户产品列表
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param merchantProducts
     * @param rebateProductS
     * @param merchantType
     * @return 
     * @see
     */
    public <T> List<T> queryNoConfigProducts(List<T> merchantProducts,
        List<RebateProduct> rebateProductS,MerchantType merchantType);

    /**
     * 根据商户ID获取返佣产品列表
     * 
     * @param merchantId
     * @return
     */
    public  List<RebateProductAssist> queryRebateProductByMerchantId(Long merchantId);
    public List<RebateProductAssist> getAllName(List<RebateProduct> listRebateProduct);
    public RebateProductAssist getAllName(RebateProduct rebateProduct);

}