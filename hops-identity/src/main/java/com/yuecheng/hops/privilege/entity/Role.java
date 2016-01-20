package com.yuecheng.hops.privilege.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 角色表实体
 * 
 * @author：Jinger
 * @date：2013-09-16
 */
@Entity
@Table(name = "role")
@SequenceGenerator(name = "RoleIdSeq", sequenceName = "Role_ID_SEQ")
public class Role implements Serializable
{
    private static final long serialVersionUID = -4004400990498493054L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RoleIdSeq")
    @SequenceGenerator(name = "RoleIdSeq", sequenceName = "ROLE_ID_SEQ", allocationSize = 1)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", length = 20)
    private String roleName;

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

    @Column(name = "role_type", length = 20)
    private String roleType;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
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

    public String getRoleType()
    {
        return roleType;
    }

    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }

    @Override
    public String toString()
    {
        return "Role [id=" + roleId + ", roleName=" + roleName + ", createTime=" + createTime
               + ", updateName=" + updateUser + ", updateTime=" + updateTime + ", status="
               + status + ", remark=" + remark + ", roleType=" + roleType + "]";
    }
}
