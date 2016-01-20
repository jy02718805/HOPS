/*
 * 文件名：AbstractMessageSender.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

import com.yuecheng.hops.common.utils.BeanUtils;

public abstract class AbstractMessageSender implements MessageSender
{
    @Override
    public AbstractMessage call()
        throws Exception
    {
        AbstractMessage request = this.request;
        if (BeanUtils.isNotNull(request))
        {
            return send(request);
        }
        throw new IllegalArgumentException();
    }

    private AbstractMessage request;

    @Override
    public void setRequestMessage(AbstractMessage request)
    {
        this.request = request;
    }

}
