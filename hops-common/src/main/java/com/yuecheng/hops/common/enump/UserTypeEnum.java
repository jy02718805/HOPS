package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum UserTypeEnum implements CodedEnum<UserTypeEnum>, Serializable {
    HuFei("01");

    private String code;

    private UserTypeEnum(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(UserTypeEnum t)
    {
        if (null != t)
        {
            for (UserTypeEnum userType : UserTypeEnum.values())
            {
                if (userType.equals(t))
                {
                    return userType.code;
                }
            }
        }
        return null;
    }

    @Override
    public UserTypeEnum getEnum(String code)
    {
        if (null != code)
        {
            for (UserTypeEnum userType : UserTypeEnum.values())
            {
                if (userType.code.equals(code))
                {
                    return userType;
                }
            }
        }
        return null;
    }
}
