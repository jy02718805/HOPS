package com.yuecheng.hops.privilege.entity;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.yuecheng.hops.common.enump.ResourceType;


@MappedSuperclass
public abstract class AbstractResource implements Resource
{
    private static final long serialVersionUID = 2443178549118727321L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resourceIdSeq")
    @SequenceGenerator(name = "resourceIdSeq", sequenceName = "RESOURCE_ID_SEQ")
    @Column(name = "Resource_Id")
    protected Long resourceId;

    @Transient
    protected ResourceType resourceType;

    public Long getResourceId()
    {
        return resourceId;
    }

    public void setResourceId(Long resourceId)
    {
        this.resourceId = resourceId;
    }

    public ResourceType getResourceType()
    {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType)
    {
        this.resourceType = resourceType;
    }
}
