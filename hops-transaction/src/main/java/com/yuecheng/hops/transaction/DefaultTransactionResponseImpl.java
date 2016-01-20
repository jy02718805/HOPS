package com.yuecheng.hops.transaction;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DefaultTransactionResponseImpl extends HashMap<String,Object> implements TransactionResponse
{

    private static final long   serialVersionUID = 5666769644675214855L;

    public String getErrorCode()
    {
        return (String)this.get(TransactionResponse.Key.ERROR_CODE);
    }

    public void setErrorCode(String errorCode)
    {
        this.put(TransactionResponse.Key.ERROR_CODE, errorCode);
    }

    @Override
    public String getTransactionCode()
    {
        return (String)this.get(TransactionResponse.Key.TRANSACTION_CODE);
    }

    @Override
    public void setTransactionCode(String transactionCode)
    {
        this.put(TransactionResponse.Key.TRANSACTION_CODE, transactionCode);
    }

    @Override
    public Long getTransactionId()
    {
        return (Long)this.get(TransactionResponse.Key.TRANSACTION_ID);
    }

    @Override
    public void setTransactionId(Long transactionId)
    {
        this.put(TransactionResponse.Key.TRANSACTION_ID, transactionId);
    }

    @Override
    public Date getTransactionTime()
    {
        return (Date)this.get(TransactionResponse.Key.DATE_TIME);
    }

    @Override
    public void setTransactionTime(Date dateTime)
    {
        this.put(TransactionResponse.Key.DATE_TIME, dateTime);
    }

    @Override
    public Object getParameter(String paraName)
    {
        return this.get(paraName);
    }

    @Override
    public void setParameter(String paraName, Object value)
    {
        this.put(paraName, value);
    }

    @Override
    public void copyPropertiesIfAbsent(Map<String,Object> sourceMap)
    {
        for(String key:sourceMap.keySet()){
            if(!this.containsKey(key))
            {
                this.put(key, sourceMap.get(key));
            }
        }
    }
}
