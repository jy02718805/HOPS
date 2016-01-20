package com.yuecheng.hops.transaction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("transactionCallableResolver")
public class TransactionCallableFactoryImpl implements TransactionCallableResolver
{
    private static Logger logger = LoggerFactory.getLogger(TransactionCallableFactoryImpl.class);

    protected ThreadLocal<TransactionContext> contextLocal = new ThreadLocal<TransactionContext>();

    @Autowired
    private TransactionServiceRegister transactionServiceRegister;

    @Override
    public TransactionService getTransactionService(Transaction transaction)
    {
        TransactionRequest request = transaction.getRequest();
        logger.debug("TransactionCallableFactoryImpl request"
                     + request);
        /* 从request中requestCode获取接口类型 requestType */
        String requestCode = request.getTransactionCode();
        
        TransactionService transactionService = null;
        try
        {
            transactionService = transactionServiceRegister.getService(requestCode);
        }
        catch (Exception e)
        {
            logger.error("[TransactionCallableFactoryImpl. getTransactionService(Transaction transaction)][异常: "
                         + e.getMessage() + "]");
        }
        // TransactionCallable transactionCallable =(TransactionCallable)
        // SpringUtils.getBean("transactionCallable");
        // transactionCallable.setTransaction(transaction);
        // transactionCallable.setTransactionService(transactionService);
        return transactionService;
    }

}
