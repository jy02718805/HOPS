/*
 * 文件名：AccountRefundService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月18日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.service;

import java.util.Date;

public interface AccountRefundService
{
    public boolean refund(Long transactionId, Long transactionNo);
    
    public boolean refundTransfer(Long transactionNo, Date orderRequestTime);
    
    public boolean refundDeliveryTransfer(Long transferNo, Date orderRequestTime);
}
