/*
 * 文件名：DefaultGatewayContextImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DefaultGatewayContextImpl implements GatewayContext,Serializable
{
    private static final long serialVersionUID = -8426212655609541726L;

    private Map<String, Object> context = new HashMap<String, Object>();
    
    private String interfaceTypeString;

    public String getInterfaceTypeString()
    {
        return interfaceTypeString;
    }

    public void setInterfaceTypeString(String interfaceTypeString)
    {
        this.interfaceTypeString = interfaceTypeString;
    }

    @Override
    public Map<String, Object> getContext()
    {
        return context;
    }
}
