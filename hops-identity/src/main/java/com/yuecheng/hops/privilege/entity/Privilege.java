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
import javax.persistence.Transient;


/**
 * 权限表实体
 * 
 * @author：Jinger
 * @date：2014-08-28
 */

@Entity
@Table(name = "Privilege")
@SequenceGenerator(name = "PrivilegeIdSeq", sequenceName = "Privilege_ID_SEQ")
public class Privilege implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = -8933986693543660819L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PrivilegeIdSeq")
    @Column(name = "Privilege_id", length = 64)
    private Long privilegeId;

    @Column(name = "Privilege_NAME")
    private String privilegeName; // 后台权限界面展示名称

    @Column(name = "Permission_Name")
    private String permissionName; // 控制shiro标签名称

    @Column(name = "Parent_Privilege_Id", length = 64)
    private Long parentPrivilegeId;

    @Column(name = "create_user", length = 20)
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user", length = 20)
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "remark", length = 200)
    private String remark;

    @Transient
    private String parentPrivilegeName;

    public String getParentPrivilegeName() {
		return parentPrivilegeName;
	}

	public void setParentPrivilegeName(String parentPrivilegeName) {
		this.parentPrivilegeName = parentPrivilegeName;
	}

	public Long getPrivilegeId()
    {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId)
    {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeName()
    {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName)
    {
        this.privilegeName = privilegeName;
    }

    public String getPermissionName()
    {
        return permissionName;
    }

    public void setPermissionName(String permissionName)
    {
        this.permissionName = permissionName;
    }

    public Long getParentPrivilegeId()
    {
        return parentPrivilegeId;
    }

    public void setParentPrivilegeId(Long parentPrivilegeId)
    {
        this.parentPrivilegeId = parentPrivilegeId;
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
        return "Privilege [id=" + privilegeId + ", privilegeName=" + privilegeName
               + ", permissionName=" + permissionName + ", parentPrivilegeId=" + parentPrivilegeId
               + ", createName=" + createUser + ", createTime=" + createTime + ", updateName="
               + updateUser + ", updateTime=" + updateTime + ", remark=" + remark + "]";
    }

}
