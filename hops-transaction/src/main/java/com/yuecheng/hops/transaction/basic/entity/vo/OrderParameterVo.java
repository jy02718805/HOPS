/*
 * 文件名：OrderParameter.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年11月24日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.basic.entity.vo;


import java.io.Serializable;


public class OrderParameterVo implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    private Integer orderStatus;

    private String carrierInfo;

    private String province;

    private String parValue;

    private String agentMerchant;

    private String beginDate;

    private String endDate;

    private Integer notifyStatus;

    private String preSuccessStatus;

    private String usercode;

    private String orderNo;

    private String sortType;

    private String merchantOrderNo;

    private String merchantId;

    private String changeDate;

    private Integer statisticsOrder;

    private String businessType;

    private String reBindType;

    private String supplyMerchant;

    private String manualFlag;

    private boolean isFake;

    public Integer getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getCarrierInfo()
    {
        return carrierInfo;
    }

    public void setCarrierInfo(String carrierInfo)
    {
        this.carrierInfo = carrierInfo;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getParValue()
    {
        return parValue;
    }

    public void setParValue(String parValue)
    {
        this.parValue = parValue;
    }

    public String getAgentMerchant()
    {
        return agentMerchant;
    }

    public void setAgentMerchant(String agentMerchant)
    {
        this.agentMerchant = agentMerchant;
    }

    public String getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public Integer getNotifyStatus()
    {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus)
    {
        this.notifyStatus = notifyStatus;
    }

    public String getPreSuccessStatus()
    {
        return preSuccessStatus;
    }

    public void setPreSuccessStatus(String preSuccessStatus)
    {
        this.preSuccessStatus = preSuccessStatus;
    }

    public String getUsercode()
    {
        return usercode;
    }

    public void setUsercode(String usercode)
    {
        this.usercode = usercode;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getSortType()
    {
        return sortType;
    }

    public void setSortType(String sortType)
    {
        this.sortType = sortType;
    }

    public String getMerchantOrderNo()
    {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo)
    {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(String merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getChangeDate()
    {
        return changeDate;
    }

    public void setChangeDate(String changeDate)
    {
        this.changeDate = changeDate;
    }

    public Integer getStatisticsOrder()
    {
        return statisticsOrder;
    }

    public void setStatisticsOrder(Integer statisticsOrder)
    {
        this.statisticsOrder = statisticsOrder;
    }

    public String getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }

    public String getReBindType()
    {
        return reBindType;
    }

    public void setReBindType(String reBindType)
    {
        this.reBindType = reBindType;
    }

    public String getSupplyMerchant()
    {
        return supplyMerchant;
    }

    public void setSupplyMerchant(String supplyMerchant)
    {
        this.supplyMerchant = supplyMerchant;
    }

    public String getManualFlag()
    {
        return manualFlag;
    }

    public void setManualFlag(String manualFlag)
    {
        this.manualFlag = manualFlag;
    }

    public boolean isFake()
    {
        return isFake;
    }

    public void setFake(boolean isFake)
    {
        this.isFake = isFake;
    }

}
