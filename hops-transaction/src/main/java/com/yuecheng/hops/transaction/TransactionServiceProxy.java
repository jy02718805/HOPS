package com.yuecheng.hops.transaction;


import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.log4j.Log4jConstant;
import com.yuecheng.hops.common.utils.PrintUtil;

public class TransactionServiceProxy implements TransactionService
{
    private static Logger             logger = LoggerFactory.getLogger(TransactionServiceProxy.class);

    @Autowired
    private TransactionFulfilment       transactionFulfilment;
    
    private static AtomicInteger  processingTransaction = new AtomicInteger();

    @Autowired
    private TransactionCallableResolver resolver;

    @Override
    public TransactionResponse doTransaction(TransactionRequest request)
    {
        try
        {
            logger.debug("begin transaction in processing num:"+processingTransaction.addAndGet(1));
            long beginTime = System.currentTimeMillis();
            logger.info(Log4jConstant.TRANSACTION_BEGIN + "currentTime:["+beginTime+"] request_RequestMap_hashcode[" + request +"]     mapValue["+ PrintUtil.mapToString(request)+"]");
            TransactionContextUtil.init();
            TransactionContextUtil.copyPropertiesIfAbsent(request);
            
            Transaction transaction = new DefaultTransaction();
            transaction.setRequest(request);
            
            TransactionService transactionService = resolver.getTransactionService(transaction);
            TransactionResponse response = transactionService.doTransaction(request);
            
            long endtime = System.currentTimeMillis();
            long costTime=  endtime - beginTime;
            logger.info(Log4jConstant.TRANSACTION_END+ "cost Time:["+costTime+"] responseMap"+ transaction.getResponse());
            logger.debug("end transaction in processing num:"+processingTransaction.addAndGet(-1));
            return response;
        }
        catch (HopsException e)
        {
            logger.error("failed to process the transaction request:["+request+"]");
            logger.debug("end transaction in processing num:"+processingTransaction.addAndGet(-1));
            TransactionResponse response = new DefaultTransactionResponseImpl();
            response.copyPropertiesIfAbsent(request);
            response.setParameter(Constant.TransactionCode.ERROR_CODE, e.getCode());
            response.setParameter(Constant.TransactionCode.RESULT, Constant.TrueOrFalse.FALSE);
            return response;
        }
        catch (Exception e)
        {
            logger.error("failed to process the transaction caused by" + ExceptionUtil.getStackTraceAsString(e));
            logger.debug("end transaction in processing num:"+processingTransaction.addAndGet(-1));
            TransactionResponse response = new DefaultTransactionResponseImpl();
            response.copyPropertiesIfAbsent(request);
            response.setParameter(Constant.TransactionCode.ERROR_CODE, Constant.ErrorCode.MANUAL);
            response.setParameter(Constant.TransactionCode.RESULT, Constant.TrueOrFalse.FALSE);
            return response;
        }
    }
}
