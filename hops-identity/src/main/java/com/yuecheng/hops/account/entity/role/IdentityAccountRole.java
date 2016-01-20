package com.yuecheng.hops.account.entity.role;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 用户账户关系类
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see IdentityAccountRole
 * @since
 */
@Entity
@Table(name = "Identity_Account_Role")
public class IdentityAccountRole implements Serializable
{

    private static final long serialVersionUID = -4811768422921845318L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identityAccountRoleIdSeq")
    @SequenceGenerator(name = "identityAccountRoleIdSeq", sequenceName = "IDENTITY_AC_ROLE_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "identity_id")
    private Long              identityId;

    @Column(name = "identity_type")
    private String            identityType;

    @Column(name = "account_id")
    private Long              accountId;

    @Column(name = "account_type")
    private Long              accountType;

    @Column(name = "relation")
    private String            relation;

    @Column(name = "table_name")
    private String            tableName;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

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

    public String getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(String identityType)
    {
        this.identityType = identityType;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public Long getAccountType()
    {
        return accountType;
    }

    public void setAccountType(Long accountType)
    {
        this.accountType = accountType;
    }

    public String getRelation()
    {
        return relation;
    }

    public void setRelation(String relation)
    {
        this.relation = relation;
    }

    public IdentityAccountRole()
    {

    }

    public IdentityAccountRole(Long identityId, String identityType, Long accountId,
                               Long accountType, String relation, String tableName)
    {
        this.identityId = identityId;
        this.identityType = identityType;
        this.accountId = accountId;
        this.accountType = accountType;
        this.relation = relation;
        this.tableName = tableName;
    }

}
