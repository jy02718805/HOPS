package com.yuecheng.hops.privilege.entity;


import java.io.Serializable;

import com.yuecheng.hops.common.enump.ResourceType;


public interface Resource extends Serializable
{
    Long getResourceId();

    void setResourceId(Long resourceId);

    ResourceType getResourceType();

    void setResourceType(ResourceType resourceType);
}
