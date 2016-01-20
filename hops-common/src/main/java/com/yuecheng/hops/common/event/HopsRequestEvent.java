package com.yuecheng.hops.common.event;


import org.springframework.context.ApplicationEvent;


public class HopsRequestEvent extends ApplicationEvent
{
    private static final long serialVersionUID = -4249148359628434521L;

    private HopsRequestContext hopsRequestContext;

    /**
     * @return the hopsRequestContext
     */
    public HopsRequestContext getHopsRequestContext()
    {
        return hopsRequestContext;
    }

    /**
     * @param hopsRequestContext
     *            the hopsRequestContext to set
     */
    public void setHopsRequestContext(HopsRequestContext hopsRequestContext)
    {
        this.hopsRequestContext = hopsRequestContext;
    }

    public HopsRequestEvent(Object source, HopsRequestContext hopsRequestCtx)
    {
        super(source);
        this.hopsRequestContext = hopsRequestCtx;
    }

    public HopsRequestEvent(Object source)
    {
        super(source);
    }
}
