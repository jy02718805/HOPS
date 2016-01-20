package com.yuecheng.hops.aportal.vo.securitycredential;

public class SecurityCredentialVO
{
    private Long id;

    private Long identityId;

    public Long securityPropertyId;

    public String securityPropertyValue;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public Long getSecurityPropertyId()
    {
        return securityPropertyId;
    }

    public void setSecurityPropertyId(Long securityPropertyId)
    {
        this.securityPropertyId = securityPropertyId;
    }

    public String getSecurityPropertyValue()
    {
        return securityPropertyValue;
    }

    public void setSecurityPropertyValue(String securityPropertyValue)
    {
        this.securityPropertyValue = securityPropertyValue;
    }

}
