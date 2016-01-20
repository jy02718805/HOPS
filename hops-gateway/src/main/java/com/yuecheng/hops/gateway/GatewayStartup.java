/*
 * 文件名：GatewayStartup.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway;

import com.yuecheng.hops.common.bootstrap.CommServletContextListener;
import com.yuecheng.hops.gateway.communication.apache.http.client.ApacheCloseableHttpClient;

public class GatewayStartup extends CommServletContextListener
{
    private ApacheCloseableHttpClient apacheCloseableHttpClient;
   
    @Override
    public void startup()
    {
        
    }

}
