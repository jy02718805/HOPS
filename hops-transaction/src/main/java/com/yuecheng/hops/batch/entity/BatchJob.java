/**
 * @Title: BatchJob.java
 * @Package com.yuecheng.hops.batch.entity
 * @Description: TODO Copyright: Copyright (c) 2015 Company:湖南跃程网络科技有限公司
 * @author yupeng
 * @date 2015年11月20日 上午10:10:20
 * @version V1.0
 */

package com.yuecheng.hops.batch.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @ClassName: BatchJob
 * @Description: TODO
 * @author yupeng
 * @date 2015年11月20日 上午10:10:20
 */
@Entity
@Table(name = "batch_job")
public class BatchJob implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batchjobidseq")
    @SequenceGenerator(name = "batchjobidseq", sequenceName = "BATCH_JOB_ID_SEQ")
    @Column(name = "batch_id")
    public Long batchId;

    @Column(name = "status")
    public int status;

    @Column(name = "file_name")
    public String fileName;

    @Column(name = "total_num")
    public int totalNum;

    @Column(name = "wait_handle_num")
    public int waitHandleNum;

    @Column(name = "processed_num")
    public int processedNum;

    @Column(name = "total_amt")
    public BigDecimal totalAmt;

    @Column(name = "started_time")
    public Date startedTime;

    @Column(name = "finished_time")
    public Date finishedTime;

    @Column(name = "paused_time")
    public Date pausedTime;

    @Column(name = "created_time")
    public Date createdTime;

    @Column(name = "start_index")
    public int startIndex;

    @Column(name = "identity_id")
    public Long identityId;

    @Column(name = "thread_num")
    public int threadNum;

    @Column(name = "operator_name")
    public String operatorName;

    @Column(name = "AUDIT_TIME")
    public Date auditTime;

    @Transient
    private String merchantName;
    
    public Long getBatchId()
    {
        return batchId;
    }

    public void setBatchId(Long batchId)
    {
        this.batchId = batchId;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public int getTotalNum()
    {
        return totalNum;
    }

    public void setTotalNum(int totalNum)
    {
        this.totalNum = totalNum;
    }

    public int getWaitHandleNum()
    {
        return waitHandleNum;
    }

    public void setWaitHandleNum(int waitHandleNum)
    {
        this.waitHandleNum = waitHandleNum;
    }

    public int getProcessedNum()
    {
        return processedNum;
    }

    public void setProcessedNum(int processedNum)
    {
        this.processedNum = processedNum;
    }

    public BigDecimal getTotalAmt()
    {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt)
    {
        this.totalAmt = totalAmt;
    }

    public Date getStartedTime()
    {
        return startedTime;
    }

    public void setStartedTime(Date startedTime)
    {
        this.startedTime = startedTime;
    }

    public Date getFinishedTime()
    {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime)
    {
        this.finishedTime = finishedTime;
    }

    public Date getPausedTime()
    {
        return pausedTime;
    }

    public void setPausedTime(Date pausedTime)
    {
        this.pausedTime = pausedTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public int getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public int getThreadNum()
    {
        return threadNum;
    }

    public void setThreadNum(int threadNum)
    {
        this.threadNum = threadNum;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public synchronized Date getAuditTime()
    {
        return auditTime;
    }

    public synchronized void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public synchronized String getMerchantName()
    {
        return merchantName;
    }

    public synchronized void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }
}
