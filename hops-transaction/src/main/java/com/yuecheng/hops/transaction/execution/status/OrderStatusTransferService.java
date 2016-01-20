/*
 * 文件名：OrderStatusTransferService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.execution.status;

public interface OrderStatusTransferService
{
    public Boolean checkStatus(Integer targetStatus, Integer originalStatus);
}
