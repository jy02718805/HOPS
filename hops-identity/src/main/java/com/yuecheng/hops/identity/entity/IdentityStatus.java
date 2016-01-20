package com.yuecheng.hops.identity.entity;


import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class IdentityStatus implements Serializable
{
    private static final long serialVersionUID = -8753692320246508408L;

    private String status;

    public IdentityStatus()
    {}

    public IdentityStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

}
