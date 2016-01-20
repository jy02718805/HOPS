/*
 * 文件名：RebateProductAssist.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：返佣产品辅助类
 * 修改人：Jinger
 * 修改时间：2014年10月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.yuecheng.hops.rebate.entity.assist;

import java.io.Serializable;

import com.yuecheng.hops.rebate.entity.RebateProduct;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Jinger
 * @version 2014年10月23日
 * @see RebateRuleAssist
 * @since
 */
public class RebateProductAssist implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 2856196488854340376L;

    private RebateProduct rebateProduct;
    
    private String merchantName;    //发生商户名称
    
    private String productName;     //发生商户产品名称

    public RebateProduct getRebateProduct()
    {
        return rebateProduct;
    }

    public void setRebateProduct(RebateProduct rebateProduct)
    {
        this.rebateProduct = rebateProduct;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    @Override
    public String toString()
    {
        return "RebateProductAssist [rebateProduct=" + rebateProduct + ", merchantName="
               + merchantName + ", productName=" + productName + "]";
    }
    
    
}
