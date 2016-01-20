package com.yuecheng.hops.identity.entity.merchant;


import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.yuecheng.hops.identity.entity.identifier.Identifier;
import com.yuecheng.hops.identity.entity.identifier.IdentifierType;


@Embeddable
public class MerchantCode implements Identifier
{

    private static final long serialVersionUID = -2598868276025416152L;

    private String code;

    @Transient
    private IdentifierType identifierType = IdentifierType.MERCHANT_CODE;

    public MerchantCode()
    {

    }

    public MerchantCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Override
    public IdentifierType getIdentifierType()
    {
        return this.identifierType;
    }
}
