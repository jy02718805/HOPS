/*
 * 文件名：CurrencyAccountService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月29日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.repository;


import java.math.BigDecimal;
import java.util.List;

import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.common.query.YcPage;


public interface CurrencyAccountDao
{
    public YcPage<CurrencyAccount> queryCurrencyAccountByMerchant(List<Long> ids,
                                                                  Long accountTypeId,
                                                                  int pageNumber, int pageSize);

    public void addCreditableBanlance(Long accountId, Long accountTypeId, BigDecimal amt, String tableName, final String remark)
        throws Exception;

    public void subCreditableBanlance(Long accountId, Long accountTypeId, BigDecimal amt, String tableName, final String remark)
        throws Exception;

    public void frozenBanlance(Long accountId, Long accountTypeId, BigDecimal amt, Long transactionId, String tableName)
        throws Exception;

    public void unFrozenBanlance(Long accountId, Long accountTypeId, BigDecimal amt, Long transationId, String tableName)
        throws Exception;
    
    public void credit(Long accountId, BigDecimal amt, Long transactionId,
                       String type, Long accountTypeId, String tableName, String remark) throws Exception;
    
    public void debit(Long accountId, BigDecimal amt, Long transactionId,
                      String type, Long accountTypeId, String tableName, String remark) throws Exception;
}
