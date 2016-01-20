package com.yuecheng.hops.business.entity;


import com.yuecheng.hops.common.CodedEnum;


public enum BusinessType implements CodedEnum<BusinessType> {
    airtimes("01"), billing("02");

    private String code;

    private BusinessType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(BusinessType t)
    {
        if (null != t)
        {
            for (BusinessType businessType : BusinessType.values())
            {
                if (businessType.equals(t))
                {
                    return businessType.code;
                }
            }
        }
        return null;
    }

    @Override
    public BusinessType getEnum(String code)
    {
        if (null != code)
        {
            for (BusinessType businessType : BusinessType.values())
            {
                if (businessType.code.equals(code))
                {
                    return businessType;
                }
            }
        }
        return null;
    }
}
