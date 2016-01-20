/*
 * 文件名：ApacheDnsResolver.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.conn.DnsResolver;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;

public class ApacheDnsResolver
{
    public static final DnsResolver dnsResolver = new SystemDefaultDnsResolver()
    {
        @Override
        public InetAddress[] resolve(final String host)
            throws UnknownHostException
        {
            if (host.equalsIgnoreCase("myhost"))
            {
                return new InetAddress[] {InetAddress.getByAddress(new byte[] {127, 0, 0, 1})};
            }
            else
            {
                return super.resolve(host);
            }
        }

    };
}
