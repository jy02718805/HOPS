package com.yuecheng.hops.transaction;


import java.io.Serializable;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.exception.HopsException;

@Service("transactionCallable")
public class TransactionCallable implements Serializable,Callable<TransactionResponse>
{
    private static final long serialVersionUID = 5786786822569701652L;
    
    protected Transaction  transaction;
    
    private TransactionService transactionService;
    
    public void setTransactionService(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    
    
    public TransactionService getTransactionService()
    {
        return transactionService;
    }



    public Transaction getTransaction()
    {
        return transaction;
    }

    public void setTransaction(Transaction transaction)
    {
        this.transaction=transaction;
    }

    @Override
    public TransactionResponse call()
    {
        TransactionResponse transactionResponse = null;
        try
        {
            transactionResponse = transactionService.doTransaction(transaction.getRequest());
            return transactionResponse;
        }
        catch (Exception e)
        {
            if (!(e instanceof HopsException))
            {
                // HopsException hopsException = new SystemException(code, msgParams, viewMsg, t)
            }
            else
            {

            }
            return null;
        }

    }

}
