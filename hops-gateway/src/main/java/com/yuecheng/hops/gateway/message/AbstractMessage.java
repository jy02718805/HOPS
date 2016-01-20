/*
 * 文件名：AbstractMessage.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2014年10月15日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.message;

import java.io.Serializable;



public abstract class AbstractMessage implements Serializable
{
    protected String protocalType;
    
     public String getProtocalType()
    {
        return this.protocalType;
    }

    public void setProtocalType(String protocalType)
    {
        this.protocalType = protocalType;
    }

    public abstract String getMessage();
}
