package com.yuecheng.hops.account.repository;

import com.yuecheng.hops.account.entity.CurrencyAccountAddCashRecord;
import com.yuecheng.hops.account.entity.vo.CurrencyAccountAddCashRecordVo;
import com.yuecheng.hops.common.query.YcPage;




public interface CurrencyAccountAddCashRecordSqlDao
{
    public YcPage<CurrencyAccountAddCashRecord> findCurrencyAccountAddCashRecordByParams(CurrencyAccountAddCashRecordVo currencyAccountAddCashRecordVo,
        int pageNumber,
        int pageSize,
        String sortType);
}
