/*
 * 文件名：MessageBuilder.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;


public interface MessageBuilder
{
    AbstractMessage builder(String uri, String requestStr, InterfacePacketsDefinitionBo ipd, String interfaceType);
}
