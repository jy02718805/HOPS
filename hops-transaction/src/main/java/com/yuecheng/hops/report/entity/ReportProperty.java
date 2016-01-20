package com.yuecheng.hops.report.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Report_Property")
public class ReportProperty implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReportPropertyIdSeq")
    @SequenceGenerator(name = "ReportPropertyIdSeq", sequenceName = "REPORT_PROPERTY_ID_SEQ")
    @Column(name = "Report_Property_Id")
    private Long reportPropertyId;

    @Column(name = "Report_Type_Id")
    private Long reportTypeId;

    @Column(name = "Report_Property_Name")
    private String reportPropertyName;

    @Column(name = "Report_Property_Type")
    private String reportPropertyType;

    @Column(name = "report_property_field_name")
    private String reportPropertyFieldName;

    @Column(name = "Report_Property_Num")
    private Long reportPropertyNum;

    public Long getReportPropertyId()
    {
        return reportPropertyId;
    }

    public void setReportPropertyId(Long reportPropertyId)
    {
        this.reportPropertyId = reportPropertyId;
    }

    public Long getReportTypeId()
    {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId)
    {
        this.reportTypeId = reportTypeId;
    }

    public String getReportPropertyName()
    {
        return reportPropertyName;
    }

    public void setReportPropertyName(String reportPropertyName)
    {
        this.reportPropertyName = reportPropertyName;
    }

    public String getReportPropertyType()
    {
        return reportPropertyType;
    }

    public void setReportPropertyType(String reportPropertyType)
    {
        this.reportPropertyType = reportPropertyType;
    }

    public Long getReportPropertyNum()
    {
        return reportPropertyNum;
    }

    public void setReportPropertyNum(Long reportPropertyNum)
    {
        this.reportPropertyNum = reportPropertyNum;
    }

    public String getReportPropertyFieldName()
    {
        return reportPropertyFieldName;
    }

    public void setReportPropertyFieldName(String reportPropertyFieldName)
    {
        this.reportPropertyFieldName = reportPropertyFieldName;
    }

    @Override
    public String toString()
    {
        return "ReportProperty [reportPropertyId=" + reportPropertyId + ", reportTypeId="
               + reportTypeId + ", reportPropertyName=" + reportPropertyName
               + ", reportPropertyType=" + reportPropertyType + ", reportPropertyFieldName="
               + reportPropertyFieldName + ", reportPropertyNum=" + reportPropertyNum + "]";
    }

}
