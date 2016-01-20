/*
 * 文件名：RebateRuleAssist.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：返佣规则辅助类
 * 修改人：Jinger
 * 修改时间：2014年10月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.entity.assist;

import java.io.Serializable;
import java.util.List;

import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.RebateTradingVolume;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Jinger
 * @version 2014年10月23日
 * @see RebateRuleAssist
 * @since
 */

public class RebateRuleAssist implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 4617700373475388491L;

    private RebateRule rebateRule;
    
    private List<RebateProductAssist> rebateProducts; //返佣产品列表

    private String productNames;    //产品名称简写

    private String productNamesAlt; //产品全名称

    private String merchantName;    //发生商户名称

    private String rebateMerchantName;  //返佣商户名称
    
    private String ruleName;        //规则名称
    
    private List<RebateTradingVolume> rebateTradingVolume;

    public RebateRule getRebateRule()
    {
        return rebateRule;
    }

    public void setRebateRule(RebateRule rebateRule)
    {
        this.rebateRule = rebateRule;
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

    public String getRuleName()
    {
        return ruleName;
    }

    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }

    public List<RebateTradingVolume> getRebateTradingVolume()
    {
        return rebateTradingVolume;
    }

    public void setRebateTradingVolume(List<RebateTradingVolume> rebateTradingVolume)
    {
        this.rebateTradingVolume = rebateTradingVolume;
    }

    @Override
    public String toString()
    {
        return "RebateRuleAssist [rebateRule=" + rebateRule + ", rebateProducts=" + rebateProducts
               + ", productNames=" + productNames + ", productNamesAlt=" + productNamesAlt
               + ", merchantName=" + merchantName + ", rebateMerchantName=" + rebateMerchantName
               + ", ruleName=" + ruleName + ", rebateTradingVolume=" + rebateTradingVolume + "]";
    }
}
