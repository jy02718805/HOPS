package com.yuecheng.hops.transaction;


import java.util.Date;
import java.util.HashMap;


public class DefaultTransactionRequestImpl extends HashMap<String,Object> implements TransactionRequest
{

    private static final long   serialVersionUID = -7146072461618622669L;

    @Override
    public String getTransactionCode()
    {
        return (String)this.get(TransactionRequest.Key.TRANSACTION_CODE);
    }

    @Override
    public void setTransactionCode(String transactionCode)
    {
        this.put(TransactionRequest.Key.TRANSACTION_CODE, transactionCode);
    }

    @Override
    public Long getTransactionId()
    {
        return (Long)this.get(TransactionRequest.Key.TRANSACTION_ID);
    }

    @Override
    public void setTransactionId(Long transactionId)
    {
        this.put(TransactionRequest.Key.TRANSACTION_ID, transactionId);
    }

    @Override
    public Date getTransactionTime()
    {
        return (Date)this.get(TransactionRequest.Key.DATE_TIME);
    }

    @Override
    public void setTransactionTime(Date dateTime)
    {
        this.put(TransactionRequest.Key.DATE_TIME, dateTime);
    }

    @Override
    public Object getParameter(String paraName)
    {
        Object obj = this.get(paraName);
        return obj;
    }

    @Override
    public void setParameter(String paraName, Object value)
    {
        this.put(paraName, value);
    }

    @Override
    public void clear(){
        this.clear();
    }
}
