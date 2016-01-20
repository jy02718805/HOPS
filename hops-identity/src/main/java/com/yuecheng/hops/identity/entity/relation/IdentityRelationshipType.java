package com.yuecheng.hops.identity.entity.relation;


import com.yuecheng.hops.common.CodedEnum;


public enum IdentityRelationshipType implements CodedEnum<IdentityRelationshipType> {
    ;

    private String code;

    private IdentityRelationshipType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(IdentityRelationshipType t)
    {
        if (null != t)
        {
            for (IdentityRelationshipType identityRelationshipType : IdentityRelationshipType.values())
            {
                if (identityRelationshipType.equals(t))
                {
                    return identityRelationshipType.code;
                }
            }
        }
        return null;
    }

    @Override
    public IdentityRelationshipType getEnum(String code)
    {
        if (null != code)
        {
            for (IdentityRelationshipType identityRelationshipType : IdentityRelationshipType.values())
            {
                if (identityRelationshipType.code.equals(code))
                {
                    return identityRelationshipType;
                }
            }
        }
        return null;
    }

}
