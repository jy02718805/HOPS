package com.yuecheng.hops.mportal.vo.report;


import java.io.Serializable;


public class ProfitReportVo implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String merchantName;

    private String merchantType;

    private String parValue;

    private String province;

    private String city;

    private String carrierNo;

    private String reportsStatus;

    private String beginTime;

    private String endTime;

    private String reportType;
    
    private String businessType;

    public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(String merchantType)
    {
        this.merchantType = merchantType;
    }

    public String getParValue()
    {
        return parValue;
    }

    public void setParValue(String parValue)
    {
        this.parValue = parValue;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }


    
    public String getCarrierNo()
    {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo)
    {
        this.carrierNo = carrierNo;
    }

    public String getReportsStatus()
    {
        return reportsStatus;
    }

    public void setReportsStatus(String reportsStatus)
    {
        this.reportsStatus = reportsStatus;
    }

    public String getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getReportType()
    {
        return reportType;
    }

    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    @Override
    public String toString()
    {
        return "ProfitReportVo [merchantName=" + merchantName + ", merchantType=" + merchantType
               + ", parValue=" + parValue + ", province=" + province + ", city=" + city
               + ", carrierInfo=" + carrierNo + ", reportsStatus=" + reportsStatus
               + ", beginTime=" + beginTime + ", endTime=" + endTime + ", reportType="
               + reportType + "]";
    }

}
