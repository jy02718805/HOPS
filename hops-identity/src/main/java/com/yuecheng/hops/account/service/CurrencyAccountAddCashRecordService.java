/*
 * 文件名：CurrencyAccountAddCashRecordService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.account.service;

import java.math.BigDecimal;

import com.yuecheng.hops.account.entity.CurrencyAccountAddCashRecord;
import com.yuecheng.hops.account.entity.vo.CurrencyAccountAddCashRecordVo;
import com.yuecheng.hops.common.query.YcPage;

public interface CurrencyAccountAddCashRecordService
{
    /**
     * 保存加款申请记录
     * @return 
     * @see
     */
    public CurrencyAccountAddCashRecord saveCurrencyAccountAddCashRecord(CurrencyAccountAddCashRecord caar);
    
    /**
     * 审核
     * @param id
     * @param status 
     * @see
     */
    public void verify(Long id,Integer status, BigDecimal amt);
    
    /**
     * 通过ID查询申请记录
     * @param id
     * @return 
     * @see
     */
    public CurrencyAccountAddCashRecord findOne(Long id);
    
    /**
     * 分页查询
     * @return 
     * @see
     */
    public YcPage<CurrencyAccountAddCashRecord> findCurrencyAccountAddCashRecordByParams(CurrencyAccountAddCashRecordVo currencyAccountAddCashRecordVo, int pageNumber, int pageSize, String sortType);
}
