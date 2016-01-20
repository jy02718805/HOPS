/*
 * 文件名：Task.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年8月25日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.mportal.thread;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.service.order.BatchOrderRequestHandlerManagement;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.SpringUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.injection.service.ResponseCodeTranslationService;
import com.yuecheng.hops.mportal.constant.PortalTransactionMapKey;
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

    private Merchant merchant;

    private BatchOrderRequestHandler borh;
    
    private ApplicationContext ctx = SpringUtils.getApplicationContext();
    
    private TransactionService transactionService=(TransactionService)ctx.getBean("transactionService");
    
    private ResponseCodeTranslationService responseCodeTranslationService= (ResponseCodeTranslationService)ctx.getBean("responseCodeTranslationService");
    
    private BatchOrderRequestHandlerManagement batchOrderRequestHandlerManagement = (BatchOrderRequestHandlerManagement)ctx.getBean("batchOrderRequestHandlerManagement");
    
    private SecurityCredentialService securityCredentialService=(SecurityCredentialService)ctx.getBean("securityCredentialService");

    @SuppressWarnings("unused")
    private int status;

    // 声明一个任务的自有业务含义的变量，用于标识任务
    private int taskId;

    // 任务的初始化方法
    public Task(int taskId, Merchant merchant, BatchOrderRequestHandler borh)
    {
        this.status = READY;
        this.taskId = taskId;
        this.merchant = merchant;
        this.borh = borh;
    }

    public BatchOrderRequestHandler getBorh()
    {
        return borh;
    }

    public void setBorh(BatchOrderRequestHandler borh)
    {
        this.borh = borh;
    }

    public Merchant getMerchant()
    {
        return merchant;
    }

    public void setMerchant(Merchant merchant)
    {
        this.merchant = merchant;
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
                merchant.getId(), IdentityType.MERCHANT, Constant.SecurityCredentialType.AGENTMD5KEY,Constant.SecurityCredentialStatus.ENABLE_STATUS);
            Random r = new Random();
            DateFormat df = new SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
            OrderVo order = new OrderVo();
            order.setMerchantId(merchant.getId());
            order.setMerchantOrderNo(Constant.Common.FAKE_ORDER_HEAD + df.format(new Date())
                                     + r.nextInt());
            order.setProductFace(borh.getOrderFee());
            order.setOrderFee(borh.getOrderFee());
            order.setNotifyUrl("notifyUrl");
            order.setUserCode(borh.getPhoneNo());
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
            transactionRequest.setParameter(PortalTransactionMapKey.INTERFACE_TYPE,
                Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER);
            transactionRequest.setParameter(PortalTransactionMapKey.SIGN, signValue);
            TransactionResponse response = transactionService.doTransaction(transactionRequest);

            Map<String, Object> response_fields = responseCodeTranslationService.translationMapToResponse(
                Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER, response);
            String result = (String)response_fields.get(Constant.TransactionCode.RESULT);
            String msg = (String)response_fields.get(Constant.TransactionCode.MSG);
            if (result.equalsIgnoreCase(Constant.TrueOrFalse.TRUE))
            {
                borh.setMerchantId(merchant.getId());
                borh.setMerchantName(merchant.getMerchantName());
                borh.setOrderStatus(Constant.BatchOrderStatus.RECHARGE_SUCCESS);
                borh.setOrderFinishTime(getNowTime());
                borh.setRemark(msg);
                batchOrderRequestHandlerManagement.save(borh);
                logger.debug("AatchOrderListThread---下单成功~~~~~!BatchOrderRequestHandler="
                             + borh.toString());
            }
            else
            {
                borh.setMerchantId(merchant.getId());
                borh.setMerchantName(merchant.getMerchantName());
                borh.setOrderStatus(Constant.BatchOrderStatus.RECHARGE_FAIL);
                borh.setOrderFinishTime(getNowTime());
                borh.setRemark(msg);
                batchOrderRequestHandlerManagement.save(borh);
                logger.debug("AatchOrderListThread---下单失败~~~~~!BatchOrderRequestHandler="
                             + borh.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
