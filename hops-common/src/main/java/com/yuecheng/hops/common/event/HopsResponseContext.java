package com.yuecheng.hops.common.event;


import java.io.Serializable;


public class HopsResponseContext implements Serializable
{

    private static final long serialVersionUID = -2063393751964754711L;

    private HopsResponse hopsResponse;

    /**
     * @return the hopsResponse
     */
    public HopsResponse getHopsResponse()
    {
        return hopsResponse;
    }

    /**
     * @param hopsResponse
     *            the hopsResponse to set
     */
    public void setHopsResponse(HopsResponse hopsResponse)
    {
        this.hopsResponse = hopsResponse;
    }
}
