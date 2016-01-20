package com.yuecheng.hops.numsection.entity;


/**
 * 运营商实体
 * 
 * @author Jinger
 * @date：2013-10-15
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "carrier_info")
public class CarrierInfo implements Serializable
{
    @Override
    public String toString()
    {
        return "CarrierInfo [id=" + carrierNo + ", carrierName=" + carrierName + ", carrierType="
               + carrierType + ", createTime=" + createTime + ", updateName=" + updateName
               + ", updateTime=" + updateTime + ", status=" + status + "]";
    }

    private static final long serialVersionUID = -7713317049589822028L;

    @Id
    @Column(name = "carrier_no")
    private String carrierNo;

    @Column(name = "carrier_name")
    private String carrierName;

    @Column(name = "carrier_type")
    private CarrierType carrierType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user", length = 20)
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status", length = 2)
    private String status;


    public String getCarrierNo() {
		return carrierNo;
	}

	public void setCarrierNo(String carrierNo) {
		this.carrierNo = carrierNo;
	}

	public String getCarrierName()
    {
        return carrierName;
    }

    public void setCarrierName(String carrierName)
    {
        this.carrierName = carrierName;
    }

    public CarrierType getCarrierType()
    {
        return carrierType;
    }

    public void setCarrierType(CarrierType carrierType)
    {
        this.carrierType = carrierType;
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
}
