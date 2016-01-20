package com.yuecheng.hops.common.enump;

import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;

public enum ResourceType implements CodedEnum<ResourceType>, Serializable  {
    Page("0");
    private String code;

    private ResourceType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(ResourceType resourceType)
    {
        if (null != resourceType)
        {
            for (ResourceType temp : ResourceType.values())
            {
                if (resourceType.equals(temp))
                {
                    return resourceType.code;
                }
            }
        }
        return null;
    }

    @Override
    public ResourceType getEnum(String code)
    {
        if (null != code)
        {
            for (ResourceType resourceType : ResourceType.values())
            {
                if (resourceType.code.equals(code))
                {
                    return resourceType;
                }
            }
        }
        return null;
    }

    public String toString()
    {
        return code.toString();
    }
}
