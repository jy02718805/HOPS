package com.yuecheng.hops.transaction.config.entify.product;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 天猫产品实体
 * 
 * @author Jinger 2014-03-11
 */
@Entity
@Table(name = "tmall_tsc")
public class TmallTSC implements Serializable
{

    private static final long serialVersionUID = -2923855545890386682L;

    @Id
    @Column(name = "tsc")
    private String tsc;

    @Column(name = "brandid")
    private String brandId;

    @Column(name = "brandname")
    private String brandName;

    @Column(name = "faceid")
    private String faceId;

    @Column(name = "facevalue")
    private String faceValue;

    @Column(name = "areaid")
    private String areaId;

    @Column(name = "cityid")
    private String cityId;

    @Column(name = "cityname")
    private String cityName;

    public String getTsc()
    {
        return tsc;
    }

    public void setTsc(String tsc)
    {
        this.tsc = tsc;
    }

    public String getBrandId()
    {
        return brandId;
    }

    public void setBrandId(String brandId)
    {
        this.brandId = brandId;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public String getFaceId()
    {
        return faceId;
    }

    public void setFaceId(String faceId)
    {
        this.faceId = faceId;
    }

    public String getFaceValue()
    {
        return faceValue;
    }

    public void setFaceValue(String faceValue)
    {
        this.faceValue = faceValue;
    }

    public String getAreaId()
    {
        return areaId;
    }

    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    @Override
    public String toString()
    {
        return "TmallTSC [tsc=" + tsc + ", brandId=" + brandId + ", brandName=" + brandName
               + ", faceId=" + faceId + ", faceValue=" + faceValue + ", areaId=" + areaId
               + ", cityId=" + cityId + ", cityName=" + cityName + "]";
    }

}
