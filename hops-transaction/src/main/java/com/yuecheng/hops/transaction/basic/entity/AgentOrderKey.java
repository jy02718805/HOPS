package com.yuecheng.hops.transaction.basic.entity;


import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name = "agent_order_key")
public class AgentOrderKey implements Serializable
{
    public static Logger logger = LoggerFactory.getLogger(AgentOrderKey.class);

    public static final long serialVersionUID = -1882068604517443210L;

    @EmbeddedId
    private AgentOrderKeyPK agentOrderKeyPK;

    public AgentOrderKeyPK getAgentOrderKeyPK()
    {
        return agentOrderKeyPK;
    }

    public void setAgentOrderKeyPK(AgentOrderKeyPK agentOrderKeyPK)
    {
        this.agentOrderKeyPK = agentOrderKeyPK;
    }

}
