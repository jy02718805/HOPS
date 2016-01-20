/*
 * 文件名：Task.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年8月25日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.mportal.thread;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.yuecheng.hops.common.utils.SpringUtils;
import com.yuecheng.hops.transaction.execution.order.OrderTransaction;


public class BatchVerifyOrderTask
{
    private static Logger logger = LoggerFactory.getLogger(BatchVerifyOrderTask.class);
    
    public static final int READY = 0;

    public static final int RUNNING = 1;

    public static final int FINISHED = 2;

    private ApplicationContext ctx = SpringUtils.getApplicationContext();
    
    private OrderTransaction orderTransaction=(OrderTransaction)ctx.getBean("orderTransaction");
    
    @SuppressWarnings("unused")
    private int status;

    // 声明一个任务的自有业务含义的变量，用于标识任务
    private int taskId;

    private List<Long> orderNos;
    
    private Long supplyMerchantid;
    
    private String operatorName;
    
    // 任务的初始化方法
    public BatchVerifyOrderTask(List<Long> orderNos, Long supplyMerchantid, String operatorName)
    {
        this.status = READY;
        this.taskId = taskId;
        this.orderNos = orderNos;
        this.supplyMerchantid = supplyMerchantid;
        this.operatorName = operatorName;
    }


    /**
     * 执行任务
     */
    public void execute()
    {
        // 设置状态为运行中
        setStatus(BatchVerifyOrderTask.RUNNING);
        orderTransaction.batchCheckOrders(orderNos, supplyMerchantid, operatorName);
        // 执行完成，改状态为完成
        setStatus(FINISHED);
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getTaskId()
    {
        return taskId;
    }
    
    public Date getNowTime()
    {
        GregorianCalendar now = new GregorianCalendar();
        SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss|SSSS", Locale.CHINA);
        String nowDate = fmtrq.format(now.getTime());
        Date nowdate = null;
        try
        {
            nowdate = fmtrq.parse(nowDate);
        }
        catch (ParseException e)
        {
            logger.error("AatchOrderListThread:[getNowTime]["+e.getMessage()+"]");
        }
        return nowdate;
    }
}
