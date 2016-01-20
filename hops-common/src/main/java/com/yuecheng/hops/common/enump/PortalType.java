package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum PortalType implements CodedEnum<PortalType>, Serializable {
    // portal、aportal、mportal
    PORTAL("PORTAL"), APORTAL("APORTAL"), MPORTAL("MPORTAL");

    private String code;

    private PortalType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(PortalType t)
    {
        if (null != t)
        {
            for (PortalType portalType : PortalType.values())
            {
                if (portalType.equals(t))
                {
                    return portalType.code;
                }
            }
        }
        return null;
    }

    @Override
    public PortalType getEnum(String code)
    {
        if (null != code)
        {
            for (PortalType portalType : PortalType.values())
            {
                if (portalType.code.equals(code))
                {
                    return portalType;
                }
            }
        }
        return null;
    }
}
