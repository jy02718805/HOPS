package com.yuecheng.hops.transaction.service.action.context;


import java.io.Serializable;
import java.util.Map;


public interface ActionContext extends Map<String,Object>,Serializable
{
    public void setContext(Map<String, Object> context);
}
