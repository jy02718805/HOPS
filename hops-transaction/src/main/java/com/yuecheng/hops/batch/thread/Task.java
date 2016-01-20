/*
 * 文件名：Task.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年8月25日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.batch.thread;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.service.order.BatchJobDetailService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.SpringUtils;
import com.yuecheng.hops.injection.service.ResponseCodeTranslationService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.transaction.DefaultTransactionRequestImpl;
import com.yuecheng.hops.transaction.TransactionCodeConstant;
import com.yuecheng.hops.transaction.TransactionRequest;
import com.yuecheng.hops.transaction.TransactionResponse;
import com.yuecheng.hops.transaction.TransactionService;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;


public class Task
{
    private static Logger logger = LoggerFactory.getLogger(Task.class);
    
    public static final int READY = 0;

    public static final int RUNNING = 1;

    public static final int FINISHED = 2;
    
    private static final String INTERFACE_TYPE = "interfaceType";
    
    private static final String SIGN = "sign";

    private BatchJobDetail batchJobDetail;
    
    private BatchJob batchJob;

    private BatchOrderRequestHandler borh;
    
    private ApplicationContext ctx = SpringUtils.getApplicationContext();
    
    private TransactionService transactionService=(TransactionService)ctx.getBean("transactionService");
    
    private ResponseCodeTranslationService responseCodeTranslationService= (ResponseCodeTranslationService)ctx.getBean("responseCodeTranslationService");
    
    private SecurityCredentialService securityCredentialService=(SecurityCredentialService)ctx.getBean("securityCredentialService");
    
    private BatchJobDetailService batchJobDetailService = (BatchJobDetailService)ctx.getBean("batchJobDetailService");

    @SuppressWarnings("unused")
    private int status;

    public BatchJobDetail getBatchJobDetail()
    {
        return batchJobDetail;
    }

    public void setBatchJobDetail(BatchJobDetail batchJobDetail)
    {
        this.batchJobDetail = batchJobDetail;
    }

    public BatchJob getBatchJob()
    {
        return batchJob;
    }

    public void setBatchJob(BatchJob batchJob)
    {
        this.batchJob = batchJob;
    }

    // 声明一个任务的自有业务含义的变量，用于标识任务
    private int taskId;

    // 任务的初始化方法
    public Task(int taskId,BatchJob batchJob,BatchJobDetail batchJobDetail)
    {
        this.status = READY;
        this.taskId = taskId;
        this.batchJob = batchJob;
        this.batchJobDetail = batchJobDetail;
    }

    public BatchOrderRequestHandler getBorh()
    {
        return borh;
    }

    public void setBorh(BatchOrderRequestHandler borh)
    {
        this.borh = borh;
    }

    /**
     * 执行任务
     */
    public void execute()
    {
        // 设置状态为运行中
        setStatus(Task.RUNNING);
        logger.debug("当前线程 ID 是：" + Thread.currentThread().getName() + " | 任务 ID 是：" + this.taskId);

        // 附加一个延时
        try
        {
            String signValue = securityCredentialService.querySecurityCredentialValueByParams(
                batchJob.getIdentityId(), IdentityType.MERCHANT, Constant.SecurityCredentialType.AGENTMD5KEY,Constant.SecurityCredentialStatus.ENABLE_STATUS);
            Random r = new Random();
            DateFormat df = new SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
            OrderVo order = new OrderVo();
            order.setOrderTitle(batchJob.getBatchId().toString());
            order.setMerchantId(batchJob.getIdentityId());
            order.setMerchantOrderNo(Constant.Common.FAKE_ORDER_HEAD + df.format(new Date())
                                     + r.nextInt());
            order.setProductFace(batchJobDetail.getFaceValue());
            order.setOrderFee(batchJobDetail.getFaceValue());
            order.setNotifyUrl("notifyUrl");
            order.setUserCode(batchJobDetail.getPhoneNum());
            order.setOrderRequestTime(new Date());
            TransactionRequest transactionRequest = new DefaultTransactionRequestImpl();
            transactionRequest.setTransactionCode(TransactionCodeConstant.AGENT_ORDER_RECEPTOR_CODE);
            transactionRequest.setTransactionTime(new Date());
            Map<String, Object> request_fields = (Map<String, Object>)BeanUtils.transBean2Map(order);
            for (Map.Entry<String, Object> entry : request_fields.entrySet())
            {
                transactionRequest.setParameter(entry.getKey(), entry.getValue());
            }
            transactionRequest.setParameter(EntityConstant.Order.PORDUCT_NUM, 1);
            transactionRequest.setParameter(INTERFACE_TYPE,
                Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER);
            transactionRequest.setParameter(SIGN, signValue);
            TransactionResponse response = transactionService.doTransaction(transactionRequest);
            
            String result = response.get(EntityConstant.Order.ERROR_CODE).toString();
            if (result.equalsIgnoreCase(Constant.ErrorCode.SUCCESS))
            {
                //下单成功
                Long orderNo = Long.valueOf(response.get(EntityConstant.Order.ORDER_NO).toString());
                batchJobDetailService.updateJobDetail(batchJobDetail.getId(), batchJobDetail.getBatchId(), Constant.BatchDetail.SUCCESS, orderNo, result);
            }
            else
            {
                //下单失败
                batchJobDetailService.updateJobDetail(batchJobDetail.getId(), batchJobDetail.getBatchId(), Constant.BatchDetail.FAILURE, null, result);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //下单存疑
            batchJobDetailService.updateJobDetail(batchJobDetail.getId(), batchJobDetail.getBatchId(), Constant.BatchDetail.DOUBTFUL, null, "下单存疑");
        }
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
}
