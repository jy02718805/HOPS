package com.yuecheng.hops.transaction.service.action.context;


import java.util.Map;

public class ActionContextUtil
{
    public static ThreadLocal<ActionContext> actionContextLocal = new ThreadLocal<ActionContext>();

    public static Object getActionContextParam(String key)
    {
        ActionContext actionContext = actionContextLocal.get();
        if (actionContext == null)
        {
            actionContext = new DefaultActionContextImpl();
        }
        return actionContext.get(key);
    }
    
    public static Map<String, Object> getActionContextLocal()
    {
        ActionContext actionContext = actionContextLocal.get();
        if (actionContext == null)
        {
            actionContext = new DefaultActionContextImpl();
        }
        return actionContext;
    }

    public static void setActionContext(String key, Object obj)
    {
        ActionContext actionContext = actionContextLocal.get();
        if (actionContext == null)
        {
            actionContext = new DefaultActionContextImpl();
        }
        Map<String, Object> map = actionContext;
        map.put(key, obj);
        actionContext.setContext(map);
        actionContextLocal.set(actionContext);
    }

    public static void setActionContext(Map<String, Object> contextMap)
    {
        ActionContext actionContext = actionContextLocal.get();
        if (actionContext == null)
        {
            actionContext = new DefaultActionContextImpl();
        }
        actionContext.setContext(contextMap);
        actionContextLocal.set(actionContext);
    }
    
    public static void init(){
        ActionContext context = new DefaultActionContextImpl();
        actionContextLocal.set(context);
    }
    
    public static void clear()
    {
        actionContextLocal.get().clear();
    }
}
