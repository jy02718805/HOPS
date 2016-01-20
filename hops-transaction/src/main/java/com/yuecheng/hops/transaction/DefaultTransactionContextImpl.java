package com.yuecheng.hops.transaction;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultTransactionContextImpl extends HashMap<String, Object> implements TransactionContext
{

    private static final long serialVersionUID = 4006038158960517042L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransactionContextImpl.class);

    public Map<String, Object> getContext()
    {
        return this;
    }

    public String getErrorCode()
    {
        return (String)this.get(TransactionContext.Key.ERROR_CODE);
    }

    public void setErrorCode(String errorCode)
    {
        this.put(TransactionContext.Key.ERROR_CODE, errorCode);
    }

    @Override
    public void setContext(Map<String, Object> context)
    {
        try
        {
            for (Map.Entry<String, Object> m : context.entrySet())
            {
                this.put(m.getKey(), m.getValue());
            }
        }
        catch (Exception e)
        {
            LOGGER.error("[DefaultTransactionContextImpl. setContext(Map<String, Object> context)][异常: "+e.getMessage()+"]");
        }
    }
}
