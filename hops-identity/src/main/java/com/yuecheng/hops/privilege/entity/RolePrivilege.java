package com.yuecheng.hops.privilege.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 角色权限表实体
 * 
 * @author：Jinger
 * @date：2013-09-16
 */

@Entity
@Table(name = "role_privilege")
@SequenceGenerator(name = "RolePrivilegeIdSeq", sequenceName = "Role_Privilege_ID_SEQ")
public class RolePrivilege implements Serializable
{
    private static final long serialVersionUID = 6473553712952637027L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RolePrivilegeIdSeq")
    @SequenceGenerator(name = "RolePrivilegeIdSeq", sequenceName = "ROLE_PRIVILEGE_ID_SEQ", allocationSize = 1)
    @Column(name = "role_privilege_id", length = 64)
    private Long rolePrivilegeId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    protected Role role;

    @ManyToOne
    @JoinColumn(name = "privilege_ID", nullable = false)
    protected Privilege privilege;

    @Column(name = "create_user", length = 20)
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user", length = 20)
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status", length = 2)
    private String status;

    @Column(name = "remark", length = 200)
    private String remark;

    public Long getId()
    {
        return rolePrivilegeId;
    }

    public void setId(Long id)
    {
        this.rolePrivilegeId = id;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
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

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public Privilege getPrivilege()
    {
        return privilege;
    }

    public void setPrivilege(Privilege privilege)
    {
        this.privilege = privilege;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    @Override
    public String toString()
    {
        return "RolePrivilege [id=" + rolePrivilegeId + ", role=" + role + ", privilege="
               + privilege + ", createName=" + createUser + ", createTime=" + createTime
               + ", updateName=" + updateUser + ", updateTime=" + updateTime + ", status="
               + status + ", remark=" + remark + "]";
    }

}
