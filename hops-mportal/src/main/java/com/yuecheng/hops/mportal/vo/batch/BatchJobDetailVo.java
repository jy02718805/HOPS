/**
 * @Title: BatchOrderRequestHandler.java
 * @Package com.yuecheng.hops.transaction.entity.order
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午10:10:20
 * @version V1.0
 */

package com.yuecheng.hops.mportal.vo.batch;


import java.io.Serializable;


/**
 */
public class BatchJobDetailVo implements Serializable
{
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -9206377523156545729L;

    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */
    public Long id;

    public Long batchId;

    public String serialNum;

    public String phoneNum;

    public String status;

    public Long orderNo;

    public String beginTime;

    public String endTime;

    public synchronized Long getId()
    {
        return id;
    }

    public synchronized void setId(Long id)
    {
        this.id = id;
    }

    public synchronized Long getBatchId()
    {
        return batchId;
    }

    public synchronized void setBatchId(Long batchId)
    {
        this.batchId = batchId;
    }


    public static synchronized long getSerialversionuid()
    {
        return serialVersionUID;
    }


    public synchronized String getPhoneNum()
    {
        return phoneNum;
    }

    public synchronized void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public synchronized Long getOrderNo()
    {
        return orderNo;
    }

    public synchronized void setOrderNo(Long orderNo)
    {
        this.orderNo = orderNo;
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

    public synchronized String getSerialNum()
    {
        return serialNum;
    }

    public synchronized void setSerialNum(String serialNum)
    {
        this.serialNum = serialNum;
    }

    public synchronized String getStatus()
    {
        return status;
    }

    public synchronized void setStatus(String status)
    {
        this.status = status;
    }

}
