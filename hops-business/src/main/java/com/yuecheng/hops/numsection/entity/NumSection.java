package com.yuecheng.hops.numsection.entity;


/**
 * 号段表实体
 * 
 * @author Jinger
 * @date：2013-10-15
 */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "num_section")
public class NumSection implements Serializable
{
    private static final long serialVersionUID = -8476008804327744702L;

    @Id
    @Column(name = "section_id")
    private String sectionId;

    @ManyToOne
    @JoinColumn(name = "carrier_no")
    private CarrierInfo carrierInfo;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "mobile_type")
    private String mobileType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "priority", length = 20)
    private int priority;

    @Column(name = "used_times")
    private int usedTimes;


    public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public CarrierInfo getCarrierInfo()
    {
        return carrierInfo;
    }

    public void setCarrierInfo(CarrierInfo carrierInfo)
    {
        this.carrierInfo = carrierInfo;
    }

    public Province getProvince()
    {
        return province;
    }

    public void setProvince(Province province)
    {
        this.province = province;
    }

    public City getCity()
    {
        return city;
    }

    public void setCity(City city)
    {
        this.city = city;
    }

    public String getMobileType()
    {
        return mobileType;
    }

    public void setMobileType(String mobileType)
    {
        this.mobileType = mobileType;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public int getUsedTimes()
    {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes)
    {
        this.usedTimes = usedTimes;
    }

    @Override
    public String toString()
    {
        return "NumSection [id=" + sectionId + ", carrierInfo=" + carrierInfo + ", province=" + province
               + ", city=" + city + ", mobileType=" + mobileType + ", createTime=" + createTime
               + ", priority=" + priority + ", usedTimes=" + usedTimes + "]";
    }

}
