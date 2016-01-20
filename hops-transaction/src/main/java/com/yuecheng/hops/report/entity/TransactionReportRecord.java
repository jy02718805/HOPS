/*
 * 文件名：TransactionReportRecord.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月17日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see TransactionReportRecord
 * @since
 */
@Entity
@Table(name = "Transaction_Report_Record")
public class TransactionReportRecord implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportRecordIdSEQ")
    @SequenceGenerator(name = "reportRecordIdSEQ", sequenceName = "Report_Record_id_SEQ")
    @Column(name = "REPORT_Record_ID")
    private Long reportRecordId;

    @Column(name = "MERCHANT_TYPE")
    private String merchantType;

    @Column(name = "BEGIN_DATE")
    private Date beginDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "REPORT_STATUS")
    private String reportStatus;

    @Column(name = "REPORT_DESCRIBE")
    private String reportDescribe;

    // 记录的报表类型
    @Column(name = "reportType")
    private String reportType;

    public Long getReportRecordId()
    {
        return reportRecordId;
    }

    public void setReportRecordId(Long reportRecordId)
    {
        this.reportRecordId = reportRecordId;
    }

    public String getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(String merchantType)
    {
        this.merchantType = merchantType;
    }

    public Date getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getReportStatus()
    {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
    }

    public String getReportType()
    {
        return reportType;
    }

    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    public String getReportDescribe()
    {
        return reportDescribe;
    }

    public void setReportDescribe(String reportDescribe)
    {
        this.reportDescribe = reportDescribe;
    }
}
