package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum IdentityType implements CodedEnum<IdentityType>, Serializable {

    SP("01"), CUSTOMER("02"), MERCHANT("03"), OPERATOR("04");

    private String code;

    private IdentityType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(IdentityType t)
    {
        if (null != t)
        {
            for (IdentityType identityType : IdentityType.values())
            {
                if (identityType.equals(t))
                {
                    return identityType.code;
                }
            }
        }
        return null;
    }

    @Override
    public IdentityType getEnum(String code)
    {
        if (null != code)
        {
            for (IdentityType identityType : IdentityType.values())
            {
                if (identityType.code.equals(code))
                {
                    return identityType;
                }
            }
        }
        return null;
    }
    
    public String toString()
    {
        return super.toString();
    }
}
