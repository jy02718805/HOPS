package com.yuecheng.hops.transaction;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.utils.PrintUtil;


@Service("transactionFulfilment")
public class TransactionFulfilment
{
    private static Logger             logger = LoggerFactory.getLogger(TransactionFulfilment.class);
    
//    @Autowired
//    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public TransactionResponse doTransaction(TransactionCallable transactionCallable)
    {
        try
        {
            logger.debug("TransactionFulfilment doTransaction Request[" + PrintUtil.mapToString(transactionCallable.getTransaction().getRequest()) +"]");
            FutureTask<TransactionResponse> futureTask = new FutureTask<TransactionResponse>(
                transactionCallable );
//            threadPoolTaskExecutor.submit(futureTask);
            TransactionResponse transactionResponse = futureTask.get();
            return transactionResponse;
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException();
        }
        catch (ExecutionException e)
        {
            throw new RuntimeException();
        }
    }
}
