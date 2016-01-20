package com.yuecheng.hops.report.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "report_metadata")
public class ReportMetadata implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "report_metadata_id")
    private Long reportMetadataId;

    @Column(name = "metadata_type")
    private String metadataType;

    @Column(name = "metadata_type_name")
    private String metadataTypeName;

    @Column(name = "metadata_field")
    private String metadataField;

    @Column(name = "metadata_field_name")
    private String metadataFieldName;

    @Column(name = "metadata_entity_field")
    private String metadataEntityField;

    @Column(name = "metadata_sign")
    private String metadataSign;

    public Long getReportMetadataId()
    {
        return reportMetadataId;
    }

    public void setReportMetadataId(Long reportMetadataId)
    {
        this.reportMetadataId = reportMetadataId;
    }

    public String getMetadataType()
    {
        return metadataType;
    }

    public void setMetadataType(String metadataType)
    {
        this.metadataType = metadataType;
    }

    public String getMetadataTypeName()
    {
        return metadataTypeName;
    }

    public void setMetadataTypeName(String metadataTypeName)
    {
        this.metadataTypeName = metadataTypeName;
    }

    public String getMetadataField()
    {
        return metadataField;
    }

    public void setMetadataField(String metadataField)
    {
        this.metadataField = metadataField;
    }

    public String getMetadataFieldName()
    {
        return metadataFieldName;
    }

    public void setMetadataFieldName(String metadataFieldName)
    {
        this.metadataFieldName = metadataFieldName;
    }

    public String getMetadataEntityField()
    {
        return metadataEntityField;
    }

    public void setMetadataEntityField(String metadataEntityField)
    {
        this.metadataEntityField = metadataEntityField;
    }

    public String getMetadataSign()
    {
        return metadataSign;
    }

    public void setMetadataSign(String metadataSign)
    {
        this.metadataSign = metadataSign;
    }
}
