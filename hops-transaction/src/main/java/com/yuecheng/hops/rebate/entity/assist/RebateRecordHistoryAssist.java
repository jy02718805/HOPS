/*
 * 文件名：RebateRecordHistoryAssist.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：返佣统计数据辅助类
 * 修改人：Jinger
 * 修改时间：2014年10月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.yuecheng.hops.rebate.entity.assist;

import java.io.Serializable;
import java.util.List;

import com.yuecheng.hops.rebate.entity.RebateRecordHistory;
/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Jinger
 * @version 2014年10月23日
 * @see RebateRuleAssist
 * @since
 */
public class RebateRecordHistoryAssist implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 7351730394637023323L;

    private RebateRecordHistory rebateRecordHistory;
    
    private List<RebateProductAssist> rebateProducts; //返佣产品列表

    private String productNames;    //产品名称简写

    private String productNamesAlt; //产品全名称

    private String merchantName;    //发生商户名称

    private String rebateMerchantName;  //返佣商户名称

    public RebateRecordHistory getRebateRecordHistory()
    {
        return rebateRecordHistory;
    }

    public void setRebateRecordHistory(RebateRecordHistory rebateRecordHistory)
    {
        this.rebateRecordHistory = rebateRecordHistory;
    }

    public List<RebateProductAssist> getRebateProducts()
    {
        return rebateProducts;
    }

    public void setRebateProducts(List<RebateProductAssist> rebateProducts)
    {
        this.rebateProducts = rebateProducts;
    }

    public String getProductNames()
    {
        return productNames;
    }

    public void setProductNames(String productNames)
    {
        this.productNames = productNames;
    }

    public String getProductNamesAlt()
    {
        return productNamesAlt;
    }

    public void setProductNamesAlt(String productNamesAlt)
    {
        this.productNamesAlt = productNamesAlt;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getRebateMerchantName()
    {
        return rebateMerchantName;
    }

    public void setRebateMerchantName(String rebateMerchantName)
    {
        this.rebateMerchantName = rebateMerchantName;
    }

    @Override
    public String toString()
    {
        return "RebateRecordHistoryAssist [rebateRecordHistory=" + rebateRecordHistory
               + ", rebateProducts=" + rebateProducts + ", productNames=" + productNames
               + ", productNamesAlt=" + productNamesAlt + ", merchantName=" + merchantName
               + ", rebateMerchantName=" + rebateMerchantName + "]";
    }
}
