/*
 * 文件名：StatusManagementService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月22日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service;

public interface StatusManagementService
{
    /**
     * 修改order/delivery/notify/query 状态
     * 
     * @param id
     * @param targetStatus
     * @see
     */
    public Object updateStatus(Long id, Integer originalStatus, Integer targetStatus);

}
