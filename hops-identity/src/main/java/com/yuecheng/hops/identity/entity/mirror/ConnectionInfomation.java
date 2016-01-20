package com.yuecheng.hops.identity.entity.mirror;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "connectioninfomation")
@SequenceGenerator(name = "connectinfomationIdSeq", sequenceName = "CONNECTION_INFOMATION_SEQ")
public class ConnectionInfomation
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connectinfomationIdSeq")
    @Column(name = "CONNECTIONINFOMATION_ID", length = 64)
    private String connectinfomationId;

    @Column(name = "MIRROR_ID", length = 64)
    private String mirrorId;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "EMAIL", length = 200)
    private String email;

    @Column(name = "MOBILE_PHONE", length = 20)
    private String mobilePhone;

    public String getMirrorId()
    {
        return mirrorId;
    }

    public void setMirrorId(String mirrorId)
    {
        this.mirrorId = mirrorId;
    }

    public String getConnectinfomationId()
    {
        return connectinfomationId;
    }

    public void setConnectinfomationId(String connectinfomationId)
    {
        this.connectinfomationId = connectinfomationId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

}
