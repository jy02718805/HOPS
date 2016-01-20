package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum AccountDirectoryType implements CodedEnum<AccountDirectoryType>, Serializable {

    DEBIT("01"), CREDIT("02");

    private String code;

    private AccountDirectoryType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(AccountDirectoryType t)
    {
        if (null != t)
        {
            for (AccountDirectoryType identityType : AccountDirectoryType.values())
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
    public AccountDirectoryType getEnum(String code)
    {
        if (null != code)
        {
            for (AccountDirectoryType identityType : AccountDirectoryType.values())
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
