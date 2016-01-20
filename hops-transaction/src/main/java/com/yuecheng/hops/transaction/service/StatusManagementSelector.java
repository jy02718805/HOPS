/*
 * 文件名：StatusManagementSelector.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service;

public interface StatusManagementSelector
{
    public StatusManagementService select(String activeTransferType);
}
