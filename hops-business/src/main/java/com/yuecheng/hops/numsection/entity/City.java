package com.yuecheng.hops.numsection.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 城市市表实体
 * 
 * @author Jinger
 * @date：2013-10-15
 */


@Entity
@Table(name = "city")
public class City implements Serializable
{
    @Override
    public String toString()
    {
        return "City [id=" + cityId + ", cityName=" + cityName + ", status=" + status + ", provinceId="
               + provinceId + "]";
    }

    private static final long serialVersionUID = -5703418279857089776L;

    @Id
    @Column(name = "city_id")
    private String cityId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "status", length = 1)
    private int status;

    @Column(name = "province_id", nullable = false)
    private String provinceId;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
    
}
