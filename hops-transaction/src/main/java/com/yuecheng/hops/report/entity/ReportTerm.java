package com.yuecheng.hops.report.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "REPORT_TERM")
public class ReportTerm implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportTermIdSeq")
    @SequenceGenerator(name = "reportTermIdSeq", sequenceName = "REPORT_TERM_ID_SEQ")
    @Column(name = "report_term_id")
    private Long reportTermId;

    @Column(name = "report_type_id")
    private Long reportTypeId;

    @Column(name = "report_metadata_id")
    private Long reportMetadataId;

    @Column(name = "report_term_status")
    private String reportTermStatus;

    @Transient
    private ReportMetadata reportMetadata;

    public Long getReportTermId()
    {
        return reportTermId;
    }

    public void setReportTermId(Long reportTermId)
    {
        this.reportTermId = reportTermId;
    }

    public Long getReportTypeId()
    {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId)
    {
        this.reportTypeId = reportTypeId;
    }

    public Long getReportMetadataId()
    {
        return reportMetadataId;
    }

    public void setReportMetadataId(Long reportMetadataId)
    {
        this.reportMetadataId = reportMetadataId;
    }

    public String getReportTermStatus()
    {
        return reportTermStatus;
    }

    public void setReportTermStatus(String reportTermStatus)
    {
        this.reportTermStatus = reportTermStatus;
    }

    public ReportMetadata getReportMetadata()
    {
        return reportMetadata;
    }

    public void setReportMetadata(ReportMetadata reportMetadata)
    {
        this.reportMetadata = reportMetadata;
    }

    @Override
    public String toString()
    {
        return "ReportTerms [reportTermId=" + reportTermId + ", reportTypeId=" + reportTypeId
               + ", reportMetadataId=" + reportMetadataId + ", reportTermStatus="
               + reportTermStatus + ", reportMetadata=" + reportMetadata + "]";
    }

}
