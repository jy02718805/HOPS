package com.yuecheng.hops.injection.entity;


import java.io.Serializable;
import java.util.List;

public class InterfacePacketsDefinitionBo implements Serializable
{
    private static final long serialVersionUID = -1882068604517443210L;

    private Long id;

    private Long isConf; // 是否配置

    private Long merchantId; // 商户ID

    private String interfaceType; // 接口类型：下单成功、下单失败

    private String inOrOut; // 接入接出接口标示

    private String encoding; // 字符编码

    private String status; // 字符编码

    private String connectionType; // 连接方式
                                   // http、https、socket、sockets

    private String requestUrl; // 接口地址
    
    private String methodType;//请求方式


	private String entityName; // 实体名称(param_conf表里面初始化)
    
    private List<InterfaceParam> responseUnderwayParams; //淘宝订单增加underway状态

    private List<InterfaceParam> requestParams;

    private List<InterfaceParam> responseSuccessParams;

    private List<InterfaceParam> responseFailParams;

    private InterfacePacketTypeConf requestInterfacePacketTypeConf;

    private InterfacePacketTypeConf responseInterfacePacketTypeConf;
    
    

    public List<InterfaceParam> getResponseUnderwayParams() {
		return responseUnderwayParams;
	}

	public void setResponseUnderwayParams(
			List<InterfaceParam> responseUnderwayParams) {
		this.responseUnderwayParams = responseUnderwayParams;
	}

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getIsConf()
    {
        return isConf;
    }

    public void setIsConf(Long isConf)
    {
        this.isConf = isConf;
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

    public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
    public String getInOrOut()
    {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut)
    {
        this.inOrOut = inOrOut;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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

    public String getEntityName()
    {
        return entityName;
    }

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public List<InterfaceParam> getRequestParams()
    {
        return requestParams;
    }

    public void setRequestParams(List<InterfaceParam> requestParams)
    {
        this.requestParams = requestParams;
    }

    public List<InterfaceParam> getResponseSuccessParams()
    {
        return responseSuccessParams;
    }

    public void setResponseSuccessParams(List<InterfaceParam> responseSuccessParams)
    {
        this.responseSuccessParams = responseSuccessParams;
    }

    public List<InterfaceParam> getResponseFailParams()
    {
        return responseFailParams;
    }

    public void setResponseFailParams(List<InterfaceParam> responseFailParams)
    {
        this.responseFailParams = responseFailParams;
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
