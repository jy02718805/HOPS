package com.yuecheng.hops.injection.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "interface_packets_definition")
public class InterfacePacketsDefinition implements Serializable
{
    private static final long       serialVersionUID = -1882068604517443210L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InterfacePacketsDefinitionIdSeq")
    @SequenceGenerator(name = "InterfacePacketsDefinitionIdSeq", sequenceName = "INTERFACE_DEFINITION_ID_SEQ")
    @Column(name = "id")
    private Long                    id;

    @Column(name = "is_conf")
    private Long                    isConf;                                   // 是否配置

    @Column(name = "merchant_id")
    private Long                    merchantId;                               // 商户ID

    @Column(name = "interface_type", length = 32)
    private String                  interfaceType;                            // 接口类型：下单成功、下单失败

    @Column(name = "in_or_out", length = 3)
    private String                  inOrOut;                                  // 接入接出接口标示

    @Column(name = "encoding", length = 32)
    private String                  encoding;                                 // 字符编码

    @Column(name = "status")
    private String                  status;                                   // 字符编码

    @Column(name = "connection_type", length = 32)
    private String                  connectionType;                           // 连接方式
                                                                              // http、https、socket、sockets

    @Column(name = "request_url", length = 50)
    private String                  requestUrl;                               // 接口地址

    @Column(name = "entity_name", length = 20)
    private String                  entityName;                               // 实体名称(param_conf表里面初始化)

    @Column(name = "method_type", length = 20)
    private String                  methodType; 
    
	@Transient
    private List<InterfaceParam>    requestParams;
    
    @Transient
    private List<InterfaceParam>    requestParamsFail;

    public List<InterfaceParam> getRequestParamsFail() {
		return requestParamsFail;
	}

	public void setRequestParamsFail(List<InterfaceParam> requestParamsFail) {
		this.requestParamsFail = requestParamsFail;
	}

	@Transient
    private List<InterfaceParam>    responseParams;

    @Transient
    private InterfacePacketTypeConf requestInterfacePacketTypeConf;

    @Transient
    private InterfacePacketTypeConf responseInterfacePacketTypeConf;

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
    
    public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public String getConnectionType()
    {
        return connectionType;
    }

    public void setConnectionType(String connectionType)
    {
        this.connectionType = connectionType;
    }

    public String getRequestUrl()
    {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl)
    {
        this.requestUrl = requestUrl;
    }

    public List<InterfaceParam> getRequestParams()
    {
        return requestParams;
    }

    public void setRequestParams(List<InterfaceParam> requestParams)
    {
        this.requestParams = requestParams;
    }

    public List<InterfaceParam> getResponseParams()
    {
        return responseParams;
    }

    public void setResponseParams(List<InterfaceParam> responseParams)
    {
        this.responseParams = responseParams;
    }

    public Long getIsConf()
    {
        return isConf;
    }

    public void setIsConf(Long isConf)
    {
        this.isConf = isConf;
    }

    public String getInOrOut()
    {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut)
    {
        this.inOrOut = inOrOut;
    }

    public String getEntityName()
    {
        return entityName;
    }

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public InterfacePacketTypeConf getRequestInterfacePacketTypeConf()
    {
        return requestInterfacePacketTypeConf;
    }

    public void setRequestInterfacePacketTypeConf(InterfacePacketTypeConf requestInterfacePacketTypeConf)
    {
        this.requestInterfacePacketTypeConf = requestInterfacePacketTypeConf;
    }

    public InterfacePacketTypeConf getResponseInterfacePacketTypeConf()
    {
        return responseInterfacePacketTypeConf;
    }

    public void setResponseInterfacePacketTypeConf(InterfacePacketTypeConf responseInterfacePacketTypeConf)
    {
        this.responseInterfacePacketTypeConf = responseInterfacePacketTypeConf;
    }

}
