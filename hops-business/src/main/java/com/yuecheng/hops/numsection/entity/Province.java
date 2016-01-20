package com.yuecheng.hops.numsection.entity;


/**
 * 省份表实体
 * 
 * @author Jinger
 * @date：2013-10-15
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "province")
public class Province implements Serializable
{
    @Override
    public String toString()
    {
        return "Province [id=" + provinceId + ", provinceName=" + provinceName + ", status=" + status
               + ", sortId=" + sortId + "]";
    }

    private static final long serialVersionUID = -7658457497313973608L;

    @Id
    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "status", length = 1)
    private int status;

    @Column(name = "sort_id", length = 2)
    private int sortId;


    public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceName()
    {
        return provinceName;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }
}
