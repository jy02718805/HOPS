/*
 * 文件名：ProducerService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：yangyi
 * 修改时间：2015年1月15日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.mq.producer;


public interface ProducerService<T>
{
    public void sendMessage(final Long id,long delay);
}
