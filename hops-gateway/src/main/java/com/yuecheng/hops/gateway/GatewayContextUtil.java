/*
 * 文件名：GatewayContextUtil.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway;

import java.util.Map;


public class GatewayContextUtil
{
    public static ThreadLocal<GatewayContext> gatewayContextLocal = new ThreadLocal<GatewayContext>();
    
    public static Map<String, Object> getGatewayContext()
    {
        GatewayContext gatewayContext = gatewayContextLocal.get();
        if (gatewayContext == null)
        {
            gatewayContext = new DefaultGatewayContextImpl();
        }
        return gatewayContext.getContext();
    }

    public static void setParameter(String key, Object obj)
    {
        GatewayContext gatewayContext = gatewayContextLocal.get();
        if (gatewayContext == null)
        {
            gatewayContext = new DefaultGatewayContextImpl();
        }
        gatewayContext.getContext().put(key, obj);
        gatewayContextLocal.set(gatewayContext);
    }
    
    public static Object getParameter(String key)
    {
        GatewayContext gatewayContext = gatewayContextLocal.get();
        if (gatewayContext == null)
        {
            gatewayContext = new DefaultGatewayContextImpl();
        }
        return gatewayContext.getContext().get(key);
    }
}
