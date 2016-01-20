package com.yuecheng.hops.identity.entity;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MirrorRoleType;
import com.yuecheng.hops.identity.entity.identifier.Identifier;
import com.yuecheng.hops.identity.entity.identifier.IdentifierType;
import com.yuecheng.hops.identity.entity.relation.IdentityRelationship;
import com.yuecheng.hops.identity.entity.relation.IdentityRelationshipType;


@MappedSuperclass
public abstract class AbstractIdentity implements Identity
{
    private static final long serialVersionUID = -8728779302601847627L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identityIdSeq")
    @SequenceGenerator(name = "identityIdSeq", sequenceName = "Identity_ID_SEQ")
    @Column(name = "identity_Id")
    protected Long identityId;
    
//    @Column(name = "identity_name")
//    protected String identityName;

    @Transient
    protected IdentityType identityType;

    @Transient
    protected MirrorRoleType identityRoleType;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "status", column = @Column(name = "status", updatable = true))})
    protected IdentityStatus identityStatus;

    @Transient
    protected Map<IdentifierType, Identifier> identifierMap = new HashMap<IdentifierType, Identifier>();

    @Transient
    protected Map<IdentityRelationshipType, IdentityRelationship> relationMap = new HashMap<IdentityRelationshipType, IdentityRelationship>();

    @Override
    public Long getId()
    {
        return identityId;
    }

    @Override
    public void setId(Long id)
    {
        this.identityId = id;
    }

    @Override
    public MirrorRoleType getIdentityRoleType()
    {
        return this.identityRoleType;
    }

    @Override
    public void setIdentityRoleType(MirrorRoleType identityRoleType)
    {
        this.identityRoleType = identityRoleType;
    }

    @Override
    public IdentityType getIdentityType()
    {
        return this.identityType;
    }

    @Override
    public void setIdentityType(IdentityType identityType)
    {
        this.identityType = identityType;
    }

    public Map<IdentifierType, Identifier> getIdentifierMap()
    {
        return identifierMap;
    }

    public void setIdentifierMap(Map<IdentifierType, Identifier> identifierMap)
    {
        this.identifierMap = identifierMap;
    }

    public Map<IdentityRelationshipType, IdentityRelationship> getRelationMap()
    {
        return relationMap;
    }

    public void setRelationMap(Map<IdentityRelationshipType, IdentityRelationship> relationMap)
    {
        this.relationMap = relationMap;
    }

    @Override
    public IdentityStatus getIdentityStatus()
    {
        return this.identityStatus;
    }

    @Override
    public void setIdentityStatus(IdentityStatus identityStatus)
    {
        this.identityStatus = identityStatus;
    }

    @Override
    public Identifier getIdentifier(IdentifierType identifierType)
    {
        if (null != identifierType)
        {
            return identifierMap.get(identifierType);
        }
        return null;
    }

    @Override
    public void addIdentifier(Identifier identifier)
    {
        if (null != identifier && null != identifier.getIdentifierType())
        {
            identifierMap.put(identifier.getIdentifierType(), identifier);
        }
    }

    @Override
    public IdentityRelationship getIdentityRelationship(IdentityRelationshipType identityRelationshipType)
    {
        if (null != identityRelationshipType)
        {
            return this.relationMap.get(identityRelationshipType);
        }
        return null;
    }

    @Override
    public void setIdentityRelationship(IdentityRelationship identityRelationship)
    {
        if (null != identityRelationship
            && null != identityRelationship.getIdentityRelationshipType())
        {
            this.relationMap.put(identityRelationship.getIdentityRelationshipType(),
                identityRelationship);
        }
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
