package com.yuecheng.hops.transaction;


import java.util.Map;

import com.yuecheng.hops.common.Constant;


public class TransactionContextUtil
{
    public static ThreadLocal<TransactionContext> transactionContextLocal = new ThreadLocal<TransactionContext>();

    public static void init(){
        TransactionContext context = new DefaultTransactionContextImpl();
        transactionContextLocal.set(context);
    }
    
    public static Object getTransactionContextParam(String key)
    {
        TransactionContext transactionContext = transactionContextLocal.get();
        if (transactionContext == null)
        {
            transactionContext = new DefaultTransactionContextImpl();
        }
        return transactionContext.getContext().get(key);
    }
    
    public static Map<String, Object> getTransactionContextLocal()
    {
        TransactionContext transactionContext = transactionContextLocal.get();
        if (transactionContext == null)
        {
            transactionContext = new DefaultTransactionContextImpl();
        }
        return transactionContext.getContext();
    }

    public static void setTransactionContext(String key, Object obj)
    {
        TransactionContext transactionContext = transactionContextLocal.get();
        if (transactionContext == null)
        {
            transactionContext = new DefaultTransactionContextImpl();
        }
        Map<String, Object> map = transactionContext.getContext();
        map.put(key, obj);
        transactionContext.setContext(map);
        transactionContextLocal.set(transactionContext);
    }
    
    public static void copyProperties(Map<String,Object> sourceMap)
    {
        TransactionContext transactionContext = transactionContextLocal.get();
        if (transactionContext == null)
        {
            transactionContext = new DefaultTransactionContextImpl();
        }
        for(String key:sourceMap.keySet()){
                transactionContext.getContext().put(key, sourceMap.get(key));
        }
        transactionContextLocal.set(transactionContext);
    }
    
    public static void copyPropertiesIfAbsent(Map<String,Object> sourceMap)
    {
        TransactionContext transactionContext = transactionContextLocal.get();
        if (transactionContext == null)
        {
            transactionContext = new DefaultTransactionContextImpl();
        }
        for(String key:sourceMap.keySet()){
            if(!transactionContext.getContext().containsKey(key))
            {
                transactionContext.getContext().put(key, sourceMap.get(key));
            }
        }
        transactionContextLocal.set(transactionContext);
    }

    public static String getErrorCode()
    {
        Map<String, Object> map = getTransactionContextLocal();
        return (String)map.get(Constant.TransactionCode.ERROR_CODE);
    }

    public static void setErrorCode(String errorCode)
    {
        setTransactionContext(Constant.TransactionCode.ERROR_CODE, errorCode);
    }

    public static String getResult()
    {
        Map<String, Object> map = getTransactionContextLocal();
        return (String)map.get(Constant.TransactionCode.RESULT);
    }

    public static void setResult(String result)
    {
        setTransactionContext(Constant.TransactionCode.RESULT, result);
    }

    public static String getMsg()
    {
        Map<String, Object> map = getTransactionContextLocal();
        return (String)map.get(Constant.TransactionCode.MSG);
    }

    public static void setMsg(String msg)
    {
        setTransactionContext(Constant.TransactionCode.MSG, msg);
    }
    
    public static void clear()
    {
        transactionContextLocal.get().getContext().clear();
    }
}
