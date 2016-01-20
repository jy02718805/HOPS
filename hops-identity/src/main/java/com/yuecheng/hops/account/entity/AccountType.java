package com.yuecheng.hops.account.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.yuecheng.hops.common.enump.AccountDirectoryType;
import com.yuecheng.hops.common.enump.AccountModelType;


@Entity
@Table(name = "account_type")
public class AccountType implements Serializable
{

    private static final long    serialVersionUID = -6755128341628893930L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cardAccountIdSeq")
    @SequenceGenerator(name = "cardAccountIdSeq", sequenceName = "AC_TYPE_ID_SEQ")
    @Column(name = "account_type_id")
    private Long                 accountTypeId;

    @Column(name = "account_type_name", length = 50)
    private String               accountTypeName;

    @Column(name = "type", length = 10)
    private String               type;                                    // 可用(isavaible
                                                                           // support)、不可用(unavaible
                                                                           // support)、授信(creditable
                                                                           // support)

    @Column(name = "scope", length = 10)
    private String               scope;                                   // 业务属性 一般、结算...normol

    @Column(name = "directory", length = 10)
    @Enumerated(EnumType.STRING)
    private AccountDirectoryType directory;                               // 进、出 debit Credit

    @Column(name = "type_model", length = 10)
    @Enumerated(EnumType.STRING)
    private AccountModelType     typeModel;                               // CCY or Card

    @Column(name = "ccy", length = 10)
    private String               ccy;                                     // 币种

    @Column(name = "identity_type", length = 50)
    private String               identityType;                            // 可使用用户类型

    @Column(name = "account_type_status")
    private Long                 accountTypeStatus;

    @Column(name = "table_name")
    private String               tableName;                               // 数据库表名

    @Column(name = "sub_flag")
    private Integer              subFlag;                                 // 是否分表

    @Column(name = "sub_number")
    private Integer              subNumber;                               // 分表个数

    public Long getAccountTypeId()
    {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId)
    {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeName()
    {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName)
    {
        this.accountTypeName = accountTypeName;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public AccountDirectoryType getDirectory()
    {
        return directory;
    }

    public void setDirectory(AccountDirectoryType directory)
    {
        this.directory = directory;
    }

    public AccountModelType getTypeModel()
    {
        return typeModel;
    }

    public void setTypeModel(AccountModelType typeModel)
    {
        this.typeModel = typeModel;
    }

    public String getCcy()
    {
        return ccy;
    }

    public void setCcy(String ccy)
    {
        this.ccy = ccy;
    }

    public String getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(String identityType)
    {
        this.identityType = identityType;
    }

    public Long getAccountTypeStatus()
    {
        return accountTypeStatus;
    }

    public void setAccountTypeStatus(Long accountTypeStatus)
    {
        this.accountTypeStatus = accountTypeStatus;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public Integer getSubFlag()
    {
        return subFlag;
    }

    public void setSubFlag(Integer subFlag)
    {
        this.subFlag = subFlag;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public Integer getSubNumber()
    {
        return subNumber;
    }

    public void setSubNumber(Integer subNumber)
    {
        this.subNumber = subNumber;
    }
}
