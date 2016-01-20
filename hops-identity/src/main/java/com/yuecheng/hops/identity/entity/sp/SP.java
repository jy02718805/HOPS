package com.yuecheng.hops.identity.entity.sp;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuecheng.hops.identity.entity.AbstractIdentity;


@Entity
@Table(name = "sp")
public class SP extends AbstractIdentity
{
    private static final long serialVersionUID = -2234664007468513028L;

    @Column(name = "sp_name", length = 50)
    private String spName;

    @Column(name = "update_user", length = 50)
    private String updateUser;

    @Column(name = "update_date", length = 2)
    private Date updateDate;

    @Column(name = "rmk", length = 50)
    private String rmk;

    @Column(name = "business", length = 50)
    private String business;

    public String getSpName()
    {
        return spName;
    }

    public void setSpName(String spName)
    {
        this.spName = spName;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getRmk()
    {
        return rmk;
    }

    public void setRmk(String rmk)
    {
        this.rmk = rmk;
    }

    public String getBusiness()
    {
        return business;
    }

    public void setBusiness(String business)
    {
        this.business = business;
    }

    @Override
    public String toString()
    {
        return "SP [spName=" + spName + ", updateUser=" + updateUser + ", updateDate="
               + updateDate + ", rmk=" + rmk + ", business=" + business + "]";
    }

}
