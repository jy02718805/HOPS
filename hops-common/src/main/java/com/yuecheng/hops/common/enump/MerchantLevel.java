package com.yuecheng.hops.common.enump;

import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;

public enum MerchantLevel implements CodedEnum<MerchantLevel>, Serializable {
    Zero("0"),One("1"),Two("2"),Three("3");
    private String code;

    private MerchantLevel(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(MerchantLevel merchantLevel)
    {
        if (null != merchantLevel)
        {
            for (MerchantLevel temp : MerchantLevel.values())
            {
                if (merchantLevel.equals(temp))
                {
                    return merchantLevel.code;
                }
            }
        }
        return null;
    }

    @Override
    public MerchantLevel getEnum(String code)
    {
        if (null != code)
        {
            for (MerchantLevel merchantLevel : MerchantLevel.values())
            {
                if (merchantLevel.code.equals(code))
                {
                    return merchantLevel;
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
