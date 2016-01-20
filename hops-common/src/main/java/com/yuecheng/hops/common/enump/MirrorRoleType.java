package com.yuecheng.hops.common.enump;


import com.yuecheng.hops.common.CodedEnum;


public enum MirrorRoleType implements CodedEnum<MirrorRoleType> {
    PersonRole("01"), OrganizationRole("02");

    private String code;

    private MirrorRoleType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(MirrorRoleType t)
    {
        if (null != t)
        {
            for (MirrorRoleType identityRoleType : MirrorRoleType.values())
            {
                if (identityRoleType.equals(t))
                {
                    return identityRoleType.code;
                }
            }
        }
        return null;
    }

    @Override
    public MirrorRoleType getEnum(String code)
    {
        if (null != code)
        {
            for (MirrorRoleType identityRoleType : MirrorRoleType.values())
            {
                if (identityRoleType.code.equals(code))
                {
                    return identityRoleType;
                }
            }
        }
        return null;
    }
}
