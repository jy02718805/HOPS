/*
 * 文件名：ApacheRegistry.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.communication.apache.http;

import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;

import com.yuecheng.hops.common.utils.BeanUtils;

public class ApacheRegistry
{
    public static <T,P extends T,S extends T> Registry<T> create(P plain,S ssl)
    {
        RegistryBuilder<T> builder = RegistryBuilder.<T> create();
        if (BeanUtils.isNotNull(plain))
        {
            builder.register(HttpHost.DEFAULT_SCHEME_NAME, plain);
        }
        if (BeanUtils.isNotNull(ssl))
        {
            builder.register("https", ssl);
        }
        Registry<T> registry = builder.build();
        return registry;
    }
}
