/*
 * 文件名：RebateRecordAssist.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：返佣元数据辅助类
 * 修改人：Jinger
 * 修改时间：2014年10月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.yuecheng.hops.rebate.entity.assist;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yuecheng.hops.rebate.entity.RebateRecord;

/**
 * 返佣元数据辅助类
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Jinger
 * @version 2014年10月23日
 * @see RebateRuleAssist
 * @since
 */
public class RebateRecordAssist implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 4543360605369550096L;

    private RebateRecord rebateRecord;
    
    private List<RebateProductAssist> rebateProducts; //返佣产品列表

    private String productNames;    //产品名称简写

    private String productNamesAlt; //产品全名称

    private Date beginTime;         //清算开始时间

    private Date endTime;           //清算结束时间

    private String merchantName;    //发生商户名称

    private String rebateMerchantName;  //返佣商户名称

    public RebateRecord getRebateRecord()
    {
        return rebateRecord;
    }

    public void setRebateRecord(RebateRecord rebateRecord)
    {
        this.rebateRecord = rebateRecord;
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

    public Date getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
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
        return "RebateRecordAssist [rebateRecord=" + rebateRecord + ", rebateProducts="
               + rebateProducts + ", productNames=" + productNames + ", productNamesAlt="
               + productNamesAlt + ", beginTime=" + beginTime + ", endTime=" + endTime
               + ", merchantName=" + merchantName + ", rebateMerchantName=" + rebateMerchantName
               + "]";
    }
}
