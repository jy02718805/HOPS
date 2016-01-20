package com.yuecheng.hops.mportal.vo.report;


import java.io.Serializable;


public class AccountReportVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String accountReportId;

    private String accountId;

    private String accountTypeId;

    private String identityId;

    private String identityName;

    private String beginTime;

    private String endTime;

    private String identityType;

    private String identityTypeName;

    private String reportType;

    public String getAccountReportId()
    {
        return accountReportId;
    }

    public void setAccountReportId(String accountReportId)
    {
        this.accountReportId = accountReportId;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountTypeId()
    {
        return accountTypeId;
    }

    public void setAccountTypeId(String accountTypeId)
    {
        this.accountTypeId = accountTypeId;
    }

    public String getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(String identityId)
    {
        this.identityId = identityId;
    }

    public String getIdentityName()
    {
        return identityName;
    }

    public void setIdentityName(String identityName)
    {
        this.identityName = identityName;
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

    public String getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(String identityType)
    {
        this.identityType = identityType;
    }

    public String getIdentityTypeName()
    {
        return identityTypeName;
    }

    public void setIdentityTypeName(String identityTypeName)
    {
        this.identityTypeName = identityTypeName;
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
        return "AccountReportVo [accountReportId=" + accountReportId + ", accountId=" + accountId
               + ", accountTypeId=" + accountTypeId + ", identityId=" + identityId
               + ", identityName=" + identityName + ", beginTime=" + beginTime + ", endTime="
               + endTime + ", identityType=" + identityType + ", identityTypeName="
               + identityTypeName + ", reportType=" + reportType + "]";
    }

}
