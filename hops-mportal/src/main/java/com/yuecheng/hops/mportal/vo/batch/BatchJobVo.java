/**
 * @Title: BatchJob.java
 * @Package com.yuecheng.hops.batch.entity
 * @Description: TODO Copyright: Copyright (c) 2015 Company:湖南跃程网络科技有限公司
 * @author yupeng
 * @date 2015年11月20日 上午10:10:20
 * @version V1.0
 */

package com.yuecheng.hops.mportal.vo.batch;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 */
public class BatchJobVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    public Long batchId;

    public String status;

    public String fileName;

    public int totalNum;

    public int waitHandleNum;

    public String processedNum;

    public BigDecimal tatalAmt;

    public Date startedTime;

    public Date finishedTime;

    public Date pausedTime;

    public Date createdTime;

    public String startIndex;

    public Long identityId;

    public int threadNum;

    public String operatorName;

    public String beginTime;

    public String endTime;


    public Long getBatchId()
    {
        return batchId;
    }

    public void setBatchId(Long batchId)
    {
        this.batchId = batchId;
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

    public String getProcessedNum()
    {
        return processedNum;
    }

    public void setProcessedNum(String processedNum)
    {
        this.processedNum = processedNum;
    }

    public BigDecimal getTatalAmt()
    {
        return tatalAmt;
    }

    public void setTatalAmt(BigDecimal tatalAmt)
    {
        this.tatalAmt = tatalAmt;
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

    public String getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(String startIndex)
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

    public synchronized String getBeginTime()
    {
        return beginTime;
    }

    public synchronized void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public synchronized String getEndTime()
    {
        return endTime;
    }

    public synchronized void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

}
