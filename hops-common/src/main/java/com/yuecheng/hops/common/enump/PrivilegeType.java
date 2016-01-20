package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum PrivilegeType implements CodedEnum<PrivilegeType>, Serializable {

    MENU("01"), FUNCTION("02");

    private String code;

    private PrivilegeType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(PrivilegeType t)
    {
        if (null != t)
        {
            for (PrivilegeType privilegeTypeEnum : PrivilegeType.values())
            {
                if (privilegeTypeEnum.equals(t))
                {
                    return privilegeTypeEnum.code;
                }
            }
        }
        return null;
    }

    @Override
    public PrivilegeType getEnum(String code)
    {
        if (null != code)
        {
            for (PrivilegeType privilegeTypeEnum : PrivilegeType.values())
            {
                if (privilegeTypeEnum.code.equals(code))
                {
                    return privilegeTypeEnum;
                }
            }
        }
        return null;
    }
}
