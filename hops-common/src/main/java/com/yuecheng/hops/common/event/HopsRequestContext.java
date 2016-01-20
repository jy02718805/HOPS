package com.yuecheng.hops.common.event;


import java.io.Serializable;


public class HopsRequestContext implements Serializable
{

    private static final long serialVersionUID = 1417610601009035798L;

    private HopsRequest hopsRequest;

    /**
     * @return the hopsRequest
     */
    public HopsRequest getHopsRequest()
    {
        return hopsRequest;
    }

    /**
     * @param hopsRequest
     *            the hopsRequest to set
     */
    public void setHopsRequest(HopsRequest hopsRequest)
    {
        this.hopsRequest = hopsRequest;
    }
}
