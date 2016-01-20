package com.yuecheng.hops.transaction;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;


public interface TransactionRequest extends Map<String,Object>,Serializable
{
    public class Key{
        public final static String DATE_TIME = "dateTime";

        public final static String TRANSACTION_ID = "transactionId";

        public final static String TRANSACTION_CODE = "transactionCode";
    }
    
    public String getTransactionCode();

    public void setTransactionCode(String transactionCode);

    public Long getTransactionId();

    public void setTransactionId(Long transactionId);

    public Date getTransactionTime();

    public void setTransactionTime(Date dateTime);

    public Object getParameter(String paraName);

    public void setParameter(String paraName, Object value);

    public void clear();
}
