package com.yuecheng.hops.transaction.config.entify.profitImputation;


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
 * 记录利润归集
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 2014年10月28日
 * @see ProfitImputationRecord
 * @since
 */
@Entity
@Table(name = "profit_imputation_record")
public class ProfitImputationRecord implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profitImputationIdSeq")
    @SequenceGenerator(name = "profitImputationIdSeq", sequenceName = "PROFIT_IMPUTATION_ID_SEQ")
    @Column(name = "imputation_record_id")
    private Long imputationRecordId;

    @Column(name = "record_begin_date")
    private Date recordBeginDateate;

    @Column(name = "record_end_date")
    private Date recordEndDateate;

    @Column(name = "record_status")
    private String recordStatus;

    @Column(name = "record_update_date")
    private Date recordUpdateDate;

    @Column(name = "record__describe")
    private String recordDescribe;

    public Long getImputationRecordId()
    {
        return imputationRecordId;
    }

    public void setImputationRecordId(Long imputationRecordId)
    {
        this.imputationRecordId = imputationRecordId;
    }

    public Date getRecordBeginDateate()
    {
        return recordBeginDateate;
    }

    public void setRecordBeginDateate(Date recordBeginDateate)
    {
        this.recordBeginDateate = recordBeginDateate;
    }

    public Date getRecordEndDateate()
    {
        return recordEndDateate;
    }

    public void setRecordEndDateate(Date recordEndDateate)
    {
        this.recordEndDateate = recordEndDateate;
    }


    public String getRecordStatus()
    {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus)
    {
        this.recordStatus = recordStatus;
    }

    public Date getRecordUpdateDate()
    {
        return recordUpdateDate;
    }

    public void setRecordUpdateDate(Date recordUpdateDate)
    {
        this.recordUpdateDate = recordUpdateDate;
    }

    public String getRecordDescribe()
    {
        return recordDescribe;
    }

    public void setRecordDescribe(String recordDescribe)
    {
        this.recordDescribe = recordDescribe;
    }

    @Override
    public String toString()
    {
        return "ProfitImputationRecord [imputationRecordId=" + imputationRecordId
               + ", record_begin_date=" + recordBeginDateate + ", record_end_date="
               + recordEndDateate + ", record_status=" + recordStatus + ", recordUpdateDate="
               + recordUpdateDate + ", recordDescribe=" + recordDescribe + "]";
    }

}
