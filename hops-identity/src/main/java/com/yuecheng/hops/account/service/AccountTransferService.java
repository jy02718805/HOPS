/*
 * 文件名：AccountTransferService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.service;


import java.math.BigDecimal;


public interface AccountTransferService
{
    public Long doTransfer(Long payerAccountId, Long payerAccountTypeId, Long payeeAccountId,
                           Long payeeAccountTypeId, BigDecimal amt, String desc, String type,
                           Long transactionNo) throws Exception;
}
