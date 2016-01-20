package com.yuecheng.hops.identity.entity.identifier;


import com.yuecheng.hops.common.CodedEnum;


public enum IdentifierType implements CodedEnum<IdentifierType> {
    MERCHANT_CODE("01");

    private String code;

    private IdentifierType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(IdentifierType t)
    {
        if (null != t)
        {
            for (IdentifierType identifierType : IdentifierType.values())
            {
                if (identifierType.equals(t))
                {
                    return identifierType.code;
                }
            }
        }
        return null;
    }

    @Override
    public IdentifierType getEnum(String code)
    {
        if (null != code)
        {
            for (IdentifierType identifierType : IdentifierType.values())
            {
                if (identifierType.code.equals(code))
                {
                    return identifierType;
                }
            }
        }
        return null;
    }
}
