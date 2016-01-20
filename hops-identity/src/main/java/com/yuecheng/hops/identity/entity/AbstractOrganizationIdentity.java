package com.yuecheng.hops.identity.entity;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.yuecheng.hops.identity.entity.mirror.Organization;


@MappedSuperclass
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractOrganizationIdentity extends AbstractIdentity implements OrganizationRole
{
    private static final long serialVersionUID = 3560354552415999690L;

    // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", nullable = false)
    protected Organization organization;

    public Organization getOrganization()
    {
        return organization;
    }

    public void setOrganization(Organization organization)
    {
        this.organization = organization;
    }

}
