package com.yuecheng.hops.injection.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "Merchant_Response")
public class MerchantResponse implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MerchantResponseIdSeq")
    @SequenceGenerator(name = "MerchantResponseIdSeq", sequenceName = "MERCHANT_RESPONSE_ID_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_id")
    private Long merchantId;// 商户号

    @Column(name = "interface_type", length = 32)
    private String interfaceType;// 接口类型

    @Column(name = "error_code", length = 32)
    private String errorCode;// 接口错误码

    @Column(name = "merchant_status", length = 32)
    private String merchantStatus;// 第三方状态

    @Column(name = "merchant_status_info", length = 50)
    private String merchantStatusInfo;

    @Column(name = "status")
    private Integer status;//系统状态

    @Transient
    private String responseStr;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getMerchantStatus()
    {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus)
    {
        this.merchantStatus = merchantStatus;
    }

    public String getMerchantStatusInfo()
    {
        return merchantStatusInfo;
    }

    public void setMerchantStatusInfo(String merchantStatusInfo)
    {
        this.merchantStatusInfo = merchantStatusInfo;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getResponseStr()
    {
        return responseStr;
    }

    public void setResponseStr(String responseStr)
    {
        this.responseStr = responseStr;
    }

}
