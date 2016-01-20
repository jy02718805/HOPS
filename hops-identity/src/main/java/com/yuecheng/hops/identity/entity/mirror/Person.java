package com.yuecheng.hops.identity.entity.mirror;


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

import com.yuecheng.hops.identity.entity.PersonalRole;


/**
 * Person表实体
 * 
 * @author：Jinger
 * @date：2013-09-24
 */
@Entity
@Table(name = "person")
@SequenceGenerator(name = "PersonIdSeq", sequenceName = "Person_ID_SEQ")
public class Person implements Serializable
{
    private static final long serialVersionUID = -5884518937220511796L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonIdSeq")
    @Column(name = "person_id", length = 64)
    private Long personId;

    @Column(name = "sex", length = 2)
    private String sex;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user", length = 20)
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "remark", length = 200)
    private String remark;

    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Column(name = "qq", length = 20)
    private String qq;
    
    @Transient
    private PersonalRole personalRole;

    @Transient
    private Long identityId;

    public Long getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(Long identityId)
    {
        this.identityId = identityId;
    }

    public Long getPersonId()
    {
        return personId;
    }

    public void setPersonId(Long personId)
    {
        this.personId = personId;
    }

    public PersonalRole getPersonalRole()
    {
        return personalRole;
    }

    public void setPersonalRole(PersonalRole personalRole)
    {
        this.personalRole = personalRole;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", sex=" + sex + ", birthday="
				+ birthday + ", createTime=" + createTime + ", updateName="
				+ updateName + ", updateTime=" + updateTime + ", remark="
				+ remark + ", email=" + email + ", phone=" + phone + ", qq="
				+ qq + ", personalRole=" + personalRole + ", identityId="
				+ identityId + "]";
	}

}
