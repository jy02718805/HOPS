package com.yuecheng.hops.common.event;


import org.springframework.context.ApplicationEvent;


public class HopsResponseEvent extends ApplicationEvent
{
    private static final long serialVersionUID = -4249148359628434521L;

    private HopsResponseContext hopsResponseContext;

    /**
     * @return the hopsResponseContext
     */
    public HopsResponseContext getHopsResponseContext()
    {
        return hopsResponseContext;
    }

    /**
     * @param hopsResponseContext
     *            the hopsResponseContext to set
     */
    public void setHopsResponseContext(HopsResponseContext hopsResponseContext)
    {
        this.hopsResponseContext = hopsResponseContext;
    }

    public HopsResponseEvent(Object source, HopsResponseContext hopsResponseCtx)
    {
        super(source);
        this.hopsResponseContext = hopsResponseCtx;
    }

    public HopsResponseEvent(Object source)
    {
        super(source);
    }
}
