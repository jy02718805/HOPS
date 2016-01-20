package com.yuecheng.hops.transaction.service.action.context;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultActionContextImpl extends HashMap<String,Object> implements ActionContext
{

    private static final long serialVersionUID = 4006038158960517042L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultActionContextImpl.class);

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
            LOGGER.error("[DefaultActionContextImpl: setContext(Map<String, Object> context)] [异常: "
                         + e.getMessage() + "]");
        }
    }
}
