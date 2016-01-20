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
@Table(name = "Report_Type")
public class ReportType implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReportTypeIdSeq")
    @SequenceGenerator(name = "ReportTypeIdSeq", sequenceName = "REPORT_TYPE_ID_SEQ")
    @Column(name = "Report_Type_Id")
    private Long reportTypeId;

    @Column(name = "Report_File_Name")
    private String reportFileName;

    @Column(name = "Report_Type_Name")
    private String reportTypeName;

    @Column(name = "report_metadata_type")
    private String reportMetadataType;

    @Column(name = "report_metadata_type_name")
    private String reportMetadataTypeName;

    public Long getReportTypeId()
    {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId)
    {
        this.reportTypeId = reportTypeId;
    }

    public String getReportFileName()
    {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName)
    {
        this.reportFileName = reportFileName;
    }

    public String getReportTypeName()
    {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName)
    {
        this.reportTypeName = reportTypeName;
    }

    public String getReportMetadataType()
    {
        return reportMetadataType;
    }

    public void setReportMetadataType(String reportMetadataType)
    {
        this.reportMetadataType = reportMetadataType;
    }

    public String getReportMetadataTypeName()
    {
        return reportMetadataTypeName;
    }

    public void setReportMetadataTypeName(String reportMetadataTypeName)
    {
        this.reportMetadataTypeName = reportMetadataTypeName;
    }

    @Override
    public String toString()
    {
        return "ReportType [reportTypeId=" + reportTypeId + ", reportFileName=" + reportFileName
               + ", reportTypeName=" + reportTypeName + ", reportMetadataType="
               + reportMetadataType + ", reportMetadataTypeName=" + reportMetadataTypeName + "]";
    }
}
