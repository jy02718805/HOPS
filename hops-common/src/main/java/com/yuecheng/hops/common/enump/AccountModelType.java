package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum AccountModelType implements CodedEnum<AccountModelType>, Serializable {

    FUNDS("01"), CARD("02");

    private String code;

    private AccountModelType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(AccountModelType t)
    {
        if (null != t)
        {
            for (AccountModelType identityType : AccountModelType.values())
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
    public AccountModelType getEnum(String code)
    {
        if (null != code)
        {
            for (AccountModelType identityType : AccountModelType.values())
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
