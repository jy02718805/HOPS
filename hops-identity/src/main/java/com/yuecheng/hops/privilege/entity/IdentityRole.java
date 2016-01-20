package com.yuecheng.hops.privilege.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yuecheng.hops.common.enump.IdentityType;


/**
 * 用户角色表实体
 * 
 * @author：Jinger
 * @date：2013-09-16
 */
@Entity
@Table(name = "identity_role")
@SequenceGenerator(name = "IdentityRoleIdSeq", sequenceName = "Identity_Role_ID_SEQ")
public class IdentityRole implements Serializable
{
    private static final long serialVersionUID = -8435605077856031174L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdentityRoleIdSeq")
    @Column(name = "identity_role_id", length = 64)
    private Long identityRoleId;

    @JoinColumn(name = "identity_Id", nullable = false)
    private Long identityId;

    @Column(name = "identity_type")
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    protected Role role;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_name")
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status", length = 2)
    private String status;

    @Column(name = "remark", length = 200)
    private String remark;

    public Long getIdnetityRoleId()
    {
        return identityRoleId;
    }

    public void setIdentityRoleId(Long menuId)
    {
        this.identityRoleId = menuId;
    }

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public IdentityType getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType)
    {
        this.identityType = identityType;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Override
    public String toString()
    {
        return "IdentityRole [id=" + identityRoleId + ", identityId=" + identityId
               + ", identityType=" + identityType + ", role=" + role + ", createTime="
               + createTime + ", updateName=" + updateName + ", updateTime=" + updateTime
               + ", status=" + status + ", remark=" + remark + "]";
    }
}
