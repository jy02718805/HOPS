/*
 * 文件名：RefundReportRecord.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
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
 * 利润报表记录
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see RefundReportRecord
 * @since
 */
@Entity
@Table(name = "REFUND_REPORT_Record")
public class RefundReportRecord implements Serializable
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
    
    @Column(name = "MERCHANT_TYPE")
    private String merchantType;

    public RefundReportRecord()
    {
        super();
    }

    public Long getReportRecordId()
    {
        return reportRecordId;
    }

    public void setReportRecordId(Long reportRecordId)
    {
        this.reportRecordId = reportRecordId;
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

    public String getReportDescribe()
    {
        return reportDescribe;
    }

    public void setReportDescribe(String reportDescribe)
    {
        this.reportDescribe = reportDescribe;
    }

    public String getMerchantType()
    {
        return merchantType;
    }

    public void setMerchantType(String merchantType)
    {
        this.merchantType = merchantType;
    }
}
