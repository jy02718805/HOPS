package com.yuecheng.hops.privilege.entity;


import java.io.Serializable;


/**
 * 菜单展示需要的辅助类（是否拥有此菜单权限）
 * 
 * @author Jinger
 */

public class MenuSelect implements Serializable
{
    private static final long serialVersionUID = -3636255279461981103L;

    private Menu menu;

    private RoleMenu roleMenu;

    private String status;

    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }

    public RoleMenu getRoleMenu()
    {
        return roleMenu;
    }

    public void setRoleMenu(RoleMenu roleMenu)
    {
        this.roleMenu = roleMenu;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "MenuSelect [menu=" + menu + ", rolePrivilege=" + roleMenu + ", status=" + status
               + "]";
    }

}
