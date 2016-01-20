package com.yuecheng.hops.privilege.entity;


import java.io.Serializable;


/**
 * 用户在全区角色中订购的角色
 * 
 * @author Jinger
 * @date 2013-10-14
 */
public class IdentityRoleSelect implements Serializable
{
    private static final long serialVersionUID = -7506236721862694705L;

    private Role role; // 角色

    private IdentityRole identityRole; // 用户和角色的关系数据

    private String status; // 是否订购，0已订购 1未订购

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public IdentityRole getIdentityRole()
    {
        return identityRole;
    }

    public void setIdentityRole(IdentityRole identityRole)
    {
        this.identityRole = identityRole;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

}
