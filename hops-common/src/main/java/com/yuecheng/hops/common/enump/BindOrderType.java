package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum BindOrderType implements CodedEnum<BindOrderType>, Serializable {

    BIND("01"), REBIND("02"), RETRY("03");

    private String code;

    private BindOrderType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(BindOrderType t)
    {
        if (null != t)
        {
            for (BindOrderType identityType : BindOrderType.values())
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
    public BindOrderType getEnum(String code)
    {
        if (null != code)
        {
            for (BindOrderType identityType : BindOrderType.values())
            {
                if (identityType.code.equals(code))
                {
                    return identityType;
                }
            }
        }
        return null;
    }
}
