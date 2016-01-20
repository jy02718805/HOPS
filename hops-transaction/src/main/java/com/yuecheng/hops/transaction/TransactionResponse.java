package com.yuecheng.hops.transaction;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;


public interface TransactionResponse extends Map<String,Object>,Serializable
{
    public class Key{
        public final static String DATE_TIME = "dateTime";

        public final static String TRANSACTION_ID = "transactionId";

        public final static String TRANSACTION_CODE = "transactionCode";
        
        public final static String ERROR_CODE = "errorCode";//用于接入接口返回
    }
    
    public String getTransactionCode();

    public void setTransactionCode(String transactionCode);

    public Long getTransactionId();

    public void setTransactionId(Long transactionId);

    public Date getTransactionTime();

    public void setTransactionTime(Date dateTime);

    public Object getParameter(String paraName);

    public void setParameter(String paraName, Object value);

    public void copyPropertiesIfAbsent(Map<String,Object> sourceMap);
    
    public String getErrorCode();
    
    public void setErrorCode(String errorCode);
}
