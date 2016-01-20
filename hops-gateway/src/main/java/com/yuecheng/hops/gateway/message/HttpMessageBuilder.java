/*
 * 文件名：HttpMessageBuilder.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月20日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.message;


import org.jboss.netty.handler.codec.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;


@Service("messageBuilder")
public class HttpMessageBuilder implements MessageBuilder
{

    @Override
    public AbstractMessage builder(String uri, String requestStr,
                                   InterfacePacketsDefinitionBo ipd, String interfaceType)
    {
        HttpRequestMessage message = new HttpRequestMessage();
        if (Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_SUCCESS_PAIPAI.equals(interfaceType)
            || Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FAIL_PAIPAI.equals(interfaceType)
            || Constant.MethodType.GET.equalsIgnoreCase(ipd.getMethodType()))
        {
            message.setMethod(HttpMethod.GET);
        }
        else if (Constant.MethodType.POST.equalsIgnoreCase(ipd.getMethodType()))
        {
            message.setMethod(HttpMethod.POST);
        }
        message.setUri(uri);
        message.setEncoding(ipd.getEncoding());
        message.setMessage(requestStr);
        message.setContentType(ipd.getRequestInterfacePacketTypeConf().getPacketType());
        return message;
    }

}
