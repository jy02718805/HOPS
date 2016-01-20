package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum MerchantType implements CodedEnum<MerchantType>, Serializable {
    SUPPLY("01"), AGENT("02");
    private String code;

    private MerchantType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(MerchantType merchantType)
    {
        if (null != merchantType)
        {
            for (MerchantType temp : MerchantType.values())
            {
                if (merchantType.equals(temp))
                {
                    return merchantType.code;
                }
            }
        }
        return null;
    }

    @Override
    public MerchantType getEnum(String code)
    {
        if (null != code)
        {
            for (MerchantType merchantType : MerchantType.values())
            {
                if (merchantType.code.equals(code))
                {
                    return merchantType;
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
