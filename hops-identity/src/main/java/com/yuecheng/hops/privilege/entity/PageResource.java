package com.yuecheng.hops.privilege.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 页面资源权限表实体
 * 
 * @author：Jinger
 * @date：2013-09-16
 */

@Entity
@Table(name = "page_resource")
public class PageResource extends AbstractResource implements Serializable
{
    private static final long serialVersionUID = 1665936119668841021L;

    @Column(name = "page_resource_name", length = 20)
    private String pageResourceName;

    @Column(name = "page_url", length = 20)
    private String pageUrl;

    @Column(name = "create_Name", length = 20)
    private String createName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_Name", length = 20)
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status", length = 2)
    private String status;

    @Column(name = "remark", length = 200)
    private String remark;

    public String getPageResourceName()
    {
        return pageResourceName;
    }

    public void setPageResourceName(String pageResourceName)
    {
        this.pageResourceName = pageResourceName;
    }

    public String getPageUrl()
    {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl)
    {
        this.pageUrl = pageUrl;
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

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    @Override
    public String toString()
    {
        return "PageResource [pageResourceName=" + pageResourceName + ", pageUrl=" + pageUrl
               + ", createName=" + createName + ", createTime=" + createTime + ", updateName="
               + updateName + ", updateTime=" + updateTime + ", status=" + status + ", remark="
               + remark + "]";
    }
}
