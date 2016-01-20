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
 * 菜单表实体
 * 
 * @author：Jinger
 * @date：2013-09-16
 */
@Entity
@Table(name = "menu")
@SequenceGenerator(name = "MenuIdSeq", sequenceName = "Menu_ID_SEQ")
public class Menu implements Serializable
{
    private static final long serialVersionUID = -7779982121215638469L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MenuIdSeq")
    @Column(name = "menu_id", length = 64)
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "page_resource_id", nullable = false, updatable = true)
    protected PageResource pageResource;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "parent_menu_id", length = 20)
    private Long parentMenuId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_Name", length = 20)
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status", length = 2)
    private String status;

    @Column(name = "menu_level")
    private String menuLevel; // 菜单级别

    @Column(name = "remark", length = 200)
    private String remark;

    @Column(name = "portaltype")
    private String portaltype; // 菜单在哪个portal展示 PORTAL 所有的，APORTAL aportal，MPORTAL mportal

    public Long getMenuId()
    {
        return menuId;
    }

    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }

    public String getMenuName()
    {
        return menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }

    public int getDisplayOrder()
    {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder)
    {
        this.displayOrder = displayOrder;
    }

    public Long getParentMenuId()
    {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId)
    {
        this.parentMenuId = parentMenuId;
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

    public String getMenuLevel()
    {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel)
    {
        this.menuLevel = menuLevel;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public PageResource getPageResource()
    {
        return pageResource;
    }

    public void setPageResource(PageResource pageResource)
    {
        this.pageResource = pageResource;
    }

    /**
     * getter method
     * 
     * @return the portaltype
     */

    public String getPortaltype()
    {
        return portaltype;
    }

    /**
     * setter method
     * 
     * @param portaltype
     *            the portaltype to set
     */

    public void setPortaltype(String portaltype)
    {
        this.portaltype = portaltype;
    }

    @Override
    public String toString()
    {
        return "Menu [id=" + menuId + ", pageResource=" + pageResource + ", menuName=" + menuName
               + ", displayOrder=" + displayOrder + ", parentMenuId=" + parentMenuId
               + ", createTime=" + createTime + ", updateName=" + updateName + ", updateTime="
               + updateTime + ", status=" + status + ", menuLevel=" + menuLevel + ", remark="
               + remark + " , portaltype=" + portaltype + "]";
    }

}
