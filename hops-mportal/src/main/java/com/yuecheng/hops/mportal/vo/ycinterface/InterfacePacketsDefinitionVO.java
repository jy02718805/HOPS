package com.yuecheng.hops.mportal.vo.ycinterface;

public class InterfacePacketsDefinitionVO
{
    private Long merchantId;// 商户ID

    private Long interfaceDefinitionId;

    private String interfaceType;

    private String requestUrl;// 接口地址

    private String encoding;// 字符编码

    private String connectionType;

    private String inOrOut;// 接入接出标示

    private String isConf;// 是否配置
    
    private String methodType;//请求方式

	private String requestInterfaceParamsStr;

    private String responseInterfaceParamsStr;

    private String requestMerchantRequestParams;

    private String responseMerchantResponseParams;

    private String requestPacketType;

    private String responsePacketType;

    private Long requestInterfacePacketTypeConfId;

    private Long responseInterfacePacketTypeConfId;

    public String getIsConf()
    {
        return isConf;
    }

    public void setIsConf(String isConf)
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

    public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	
    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Long getInterfaceDefinitionId()
    {
        return interfaceDefinitionId;
    }

    public void setInterfaceDefinitionId(Long interfaceDefinitionId)
    {
        this.interfaceDefinitionId = interfaceDefinitionId;
    }

    public String getInterfaceType()
    {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType)
    {
        this.interfaceType = interfaceType;
    }

    public String getRequestUrl()
    {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl)
    {
        this.requestUrl = requestUrl;
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

    public String getRequestInterfaceParamsStr()
    {
        return requestInterfaceParamsStr;
    }

    public void setRequestInterfaceParamsStr(String requestInterfaceParamsStr)
    {
        this.requestInterfaceParamsStr = requestInterfaceParamsStr;
    }

    public String getResponseInterfaceParamsStr()
    {
        return responseInterfaceParamsStr;
    }

    public void setResponseInterfaceParamsStr(String responseInterfaceParamsStr)
    {
        this.responseInterfaceParamsStr = responseInterfaceParamsStr;
    }

    public String getRequestMerchantRequestParams()
    {
        return requestMerchantRequestParams;
    }

    public void setRequestMerchantRequestParams(String requestMerchantRequestParams)
    {
        this.requestMerchantRequestParams = requestMerchantRequestParams;
    }

    public String getResponseMerchantResponseParams()
    {
        return responseMerchantResponseParams;
    }

    public void setResponseMerchantResponseParams(String responseMerchantResponseParams)
    {
        this.responseMerchantResponseParams = responseMerchantResponseParams;
    }

    public String getRequestPacketType()
    {
        return requestPacketType;
    }

    public void setRequestPacketType(String requestPacketType)
    {
        this.requestPacketType = requestPacketType;
    }

    public String getResponsePacketType()
    {
        return responsePacketType;
    }

    public void setResponsePacketType(String responsePacketType)
    {
        this.responsePacketType = responsePacketType;
    }

    public Long getRequestInterfacePacketTypeConfId()
    {
        return requestInterfacePacketTypeConfId;
    }

    public void setRequestInterfacePacketTypeConfId(Long requestInterfacePacketTypeConfId)
    {
        this.requestInterfacePacketTypeConfId = requestInterfacePacketTypeConfId;
    }

    public Long getResponseInterfacePacketTypeConfId()
    {
        return responseInterfacePacketTypeConfId;
    }

    public void setResponseInterfacePacketTypeConfId(Long responseInterfacePacketTypeConfId)
    {
        this.responseInterfacePacketTypeConfId = responseInterfacePacketTypeConfId;
    }

}
