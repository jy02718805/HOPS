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
 * 角色菜单表实体
 * 
 * @author：Jinger
 * @date：2014-08-28
 */
@Entity
@Table(name = "role_menu")
@SequenceGenerator(name = "RoleMenuIdSeq", sequenceName = "Role_Menu_ID_SEQ")
public class RoleMenu implements Serializable
{
    private static final long serialVersionUID = -9142462001232954381L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RoleMenuIdSeq")
    @SequenceGenerator(name = "RoleMenuIdSeq", sequenceName = "ROLE_MENU_ID_SEQ", allocationSize = 1)
    @Column(name = "role_menu_id", length = 64)
    private Long roleMenuId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    protected Role role;

    @ManyToOne
    @JoinColumn(name = "menu_ID", nullable = false)
    protected Menu menu;

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

    public Long getRoleMenuId()
    {
        return roleMenuId;
    }

    public void setRoleMenuId(Long id)
    {
        this.roleMenuId = id;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
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

    @Override
    public String toString()
    {
        return "RoleMenu [id=" + roleMenuId + ", role=" + role + ", menu=" + menu
               + ", createName=" + createUser + ", createTime=" + createTime + ", updateName="
               + updateUser + ", updateTime=" + updateTime + ", status=" + status + ", remark="
               + remark + "]";
    }

}
