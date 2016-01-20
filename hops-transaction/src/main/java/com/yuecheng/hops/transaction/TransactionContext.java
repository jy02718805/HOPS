package com.yuecheng.hops.transaction;


import java.io.Serializable;
import java.util.Map;


public interface TransactionContext extends Serializable
{
    public class Key{
        public final static String ERROR_CODE = "errorCode";
    }
    
    public Map<String, Object> getContext();
    
    public void setContext(Map<String, Object> context);
}
