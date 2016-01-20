// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space
// Source File Name: AatchOrderListThread.java

package com.yuecheng.hops.mportal.thread;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.service.order.BatchOrderRequestHandlerManagement;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.utils.SpringUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;


public class AatchOrderListThread implements Runnable
{

    private static Logger logger = LoggerFactory.getLogger(AatchOrderListThread.class);

    private ApplicationContext ctx = SpringUtils.getApplicationContext();
    private IdentityService identityService=(IdentityService)ctx.getBean("identityService");
    private BatchOrderRequestHandlerManagement batchOrderRequestHandlerManagement = (BatchOrderRequestHandlerManagement)ctx.getBean("batchOrderRequestHandlerManagement");
    private Long merchantid;

    private String upfile;
    
    private Long auditnum;

    public AatchOrderListThread(Long merchantid,
                                String upfile,Long auditnum)
    {
        this.merchantid = merchantid;
        this.upfile = upfile;
        this.auditnum=auditnum;
    }

    public void run(){
        // 初始化要执行的任务列表
        Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(new Long(merchantid), IdentityType.MERCHANT);
        List taskList = new ArrayList();
        List<BatchOrderRequestHandler> borhList = batchOrderRequestHandlerManagement.queryBatchOrderRequestHandler(upfile, 1L);
        for (int i = 0; i < borhList.size(); i++ )
        {
            taskList.add(new Task(i, merchant, borhList.get(i)));
        }
        // 设定要启动的工作线程数为 4 个
        int threadCount = 4;
        List[] taskListPerThread = distributeTasks(taskList, threadCount);
        logger.debug("实际要启动的工作线程数：" + taskListPerThread.length);
        for (int i = 0; i < taskListPerThread.length; i++ )
        {
            Thread workThread = new WorkThread(taskListPerThread[i], i);
            workThread.start();
        }
    }
    
    /**
     * 把 List 中的任务分配给每个线程，先平均分配，剩于的依次附加给前面的线程 返回的数组有多少个元素 (List) 就表明将启动多少个工作线程
     * 
     * @param taskList
     *            待分派的任务列表
     * @param threadCount
     *            线程数
     * @return 列表的数组，每个元素中存有该线程要执行的任务列表
     */
    @SuppressWarnings("unchecked")
    public static List[] distributeTasks(List taskList, int threadCount)
    {
        // 每个线程至少要执行的任务数,假如不为零则表示每个线程都会分配到任务
        int minTaskCount = taskList.size() / threadCount;
        // 平均分配后还剩下的任务数，不为零则还有任务依个附加到前面的线程中
        int remainTaskCount = taskList.size() % threadCount;
        // 实际要启动的线程数,如果工作线程比任务还多
        // 自然只需要启动与任务相同个数的工作线程，一对一的执行
        // 毕竟不打算实现了线程池，所以用不着预先初始化好休眠的线程
        int actualThreadCount = minTaskCount > 0 ? threadCount : remainTaskCount;
        // 要启动的线程数组，以及每个线程要执行的任务列表
        List[] taskListPerThread = new List[actualThreadCount];
        int taskIndex = 0;
        // 平均分配后多余任务，每附加给一个线程后的剩余数，重新声明与 remainTaskCount
        // 相同的变量，不然会在执行中改变 remainTaskCount 原有值，产生麻烦
        int remainIndces = remainTaskCount;
        for (int i = 0; i < taskListPerThread.length; i++ )
        {
            taskListPerThread[i] = new ArrayList();
            // 如果大于零，线程要分配到基本的任务
            if (minTaskCount > 0)
            {
                for (int j = taskIndex; j < minTaskCount + taskIndex; j++ )
                {
                    taskListPerThread[i].add(taskList.get(j));
                }
                taskIndex += minTaskCount;
            }
            // 假如还有剩下的，则补一个到这个线程中
            if (remainIndces > 0)
            {
                taskListPerThread[i].add(taskList.get(taskIndex++ ));
                remainIndces-- ;
            }
        }
        // 打印任务的分配情况
        for (int i = 0; i < taskListPerThread.length; i++ )
        {
            logger.debug("线程 "
                               + i
                               + " 的任务数："
                               + taskListPerThread[i].size()
                               + " 区间["
                               + ((Task)taskListPerThread[i].get(0)).getTaskId()
                               + ","
                               + ((Task)taskListPerThread[i].get(taskListPerThread[i].size() - 1)).getTaskId()
                               + "]");
        }
        return taskListPerThread;
    }
    
    //!!!!!!!!!!!!!!!!!!多线程实现
//    public void run()
//    {
//        Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
//            new Long(merchantid), IdentityType.MERCHANT);
//        SecurityCredentialType securityTyp = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.AGENTMD5KEY);
//        SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialByParams(
//            merchant.getId(), IdentityType.MERCHANT, securityTyp!=null?securityTyp.getSecurityTypeId():null,Constant.SecurityCredentialStatus.ENABLE_STATUS);
//        String signValue = securityCredentialManagerService.decryptKeyBySecurityId(securityCredential.getSecurityId());
//        signValue = signValue.toString().toUpperCase();
//        List<BatchOrderRequestHandler> borhList = batchOrderRequestHandlerManagement.queryBatchOrderRequestHandler(upfile,
//            1L);
//        String notifyUrl="notifyUrl";
//        int i=0;
//        for (BatchOrderRequestHandler borh : borhList)
//        {
//            if(i<auditnum.intValue())
//            {
//                Random r = new Random();
//                DateFormat df = new SimpleDateFormat(Constant.Common.DATE_FORMAT_TYPE);
//                OrderVo order = new OrderVo();
//                order.setMerchantId(merchant.getId());
//                order.setMerchantOrderNo(Constant.Common.FAKE_ORDER_HEAD + df.format(new Date())
//                                         + r.nextInt());
//                order.setProductFace(borh.getOrderFee());
//                order.setOrderFee(borh.getOrderFee());
//                order.setNotifyUrl(notifyUrl);
//                order.setUserCode(borh.getPhoneNo());
//                order.setOrderRequestTime(new Date());
//                TransactionRequest transactionRequest = new DefaultTransactionRequestImpl();
//                transactionRequest.setTransactionCode(TransactionCodeConstant.AGENT_ORDER_RECEPTOR_CODE);
//                transactionRequest.setTransactionTime(new Date());
//                Map<String, Object> request_fields = (Map<String, Object>)BeanUtils.transBean2Map(order);
//                for(Map.Entry<String, Object> entry: request_fields.entrySet()) {
//                    transactionRequest.setParameter(entry.getKey(), entry.getValue());
//                }
//                transactionRequest.setParameter(EntityConstant.Order.PORDUCT_NUM,1);
//                transactionRequest.setParameter(PortalTransactionMapKey.INTERFACE_TYPE, Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER);
//                transactionRequest.setParameter(PortalTransactionMapKey.SIGN, signValue);
//                TransactionResponse response = transactionService.doTransaction(transactionRequest);
//
//                Map<String, Object> response_fields = responseCodeTranslationService.translationMapToResponse(Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER, response);
//                String result = (String)response_fields.get(Constant.TransactionCode.RESULT);
//                String msg = (String)response_fields.get(Constant.TransactionCode.MSG);
//                if (result.equalsIgnoreCase(Constant.TrueOrFalse.TRUE))
//                {
//                    borh.setMerchantId(new Long(merchantid));
//                    borh.setMerchantName(merchant.getMerchantName());
//                    borh.setOrderStatus(Constant.BatchOrderStatus.RECHARGE_SUCCESS);
//                    borh.setOrderFinishTime(getNowTime());
//                    borh.setRemark(msg);
//                    batchOrderRequestHandlerManagement.save(borh);
//                    logger.debug("AatchOrderListThread---下单成功~~~~~!BatchOrderRequestHandler="+borh.toString());
//                }
//                else
//                {
//                    borh.setMerchantId(new Long(merchantid));
//                    borh.setMerchantName(merchant.getMerchantName());
//                    borh.setOrderStatus(Constant.BatchOrderStatus.RECHARGE_FAIL);
//                    borh.setOrderFinishTime(getNowTime());
//                    borh.setRemark(msg);
//                    batchOrderRequestHandlerManagement.save(borh);
//                    logger.debug("AatchOrderListThread---下单失败~~~~~!BatchOrderRequestHandler="+borh.toString());
//                }
//            }else
//            {
//            	break;
//            }
//            i++;
//        }
//
//    }

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
