/*
 * 文件名：OracleSql.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.rebate.repository;

public class OracleSql
{
    public class RebateRule
    {
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
            + "select t.* from Rebate_Rule t where 1=1 "
            + "and (t.merchant_Id like '%'||:merchantId||'%' or :merchantId is null) "
            + "and (t.REBATE_MERCHANT_ID = :rebateMerchantId or :rebateMerchantId is null) "
            + "and (t.REBATE_TIME_TYPE = :rebateTimeType or :rebateTimeType is null) "
            + "and (t.REBATE_TYPE = :rebateType or :rebateType is null) "
            + "and (t.STATUS != :status or :status is null) "
            + "order by t.REBATE_RULE_ID desc) a where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String All_SQL = "select * from Rebate_Rule t where 1=1 "
            + "and (t.merchant_Id like '%'||:merchantId||'%' or :merchantId is null) "
            + "and (t.REBATE_MERCHANT_ID = :rebateMerchantId or :rebateMerchantId is null) "
            + "and (t.REBATE_TIME_TYPE = :rebateTimeType or :rebateTimeType is null) "
            + "and (t.REBATE_TYPE = :rebateType or :rebateType is null) "
            + "and (t.STATUS != :status or :status is null) "
           + "order by t.REBATE_RULE_ID desc";
    }
    
    public class RebateRecord
    {
        public static final String All_SQL = "select * from Rebate_Record t where 1=1 "
            + "and (t.merchant_Id like '%'||:merchantId||'%' or :merchantId is null) "
            + "and (t.REBATE_MERCHANT_ID = :rebateMerchantId or :rebateMerchantId is null) "
            + "and (t.rebate_Date = to_date(:rebateDate, 'yyyy-mm-dd hh24:mi:ss') or :rebateDate is null) "
            + "and (t.rebate_Product_Id = :rebateProductId or :rebateProductId is null) "
            + "and (t.rebate_Rule_Id != :rebateRuleId or :rebateRuleId is null) "
           + "order by t.ID desc";
        
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
                + "select t.* from Rebate_Record t where 1=1 "
                + "and (t.merchant_Id like '%'||:merchantId||'%' or :merchantId is null) "
                + "and (t.REBATE_MERCHANT_ID = :rebateMerchantId or :rebateMerchantId is null) "
                + "and (t.rebate_Date >= to_date(:beginDate, 'yyyy-mm-dd hh24:mi:ss') or :beginDate is null) "
                + "and (t.rebate_Date <= to_date(:endDate, 'yyyy-mm-dd hh24:mi:ss') or :endDate is null) "
                + "and (t.STATUS = :status or :status is null) "
                + "order by t.ID desc) a where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String PAGE_All_SQL = "select * from Rebate_Record t where 1=1 "
                + "and (t.merchant_Id like '%'||:merchantId||'%' or :merchantId is null) "
                + "and (t.REBATE_MERCHANT_ID = :rebateMerchantId or :rebateMerchantId is null) "
                + "and (t.rebate_Date >= to_date(:beginDate, 'yyyy-mm-dd hh24:mi:ss') or :beginDate is null) "
                + "and (t.rebate_Date <= to_date(:endDate, 'yyyy-mm-dd hh24:mi:ss') or :endDate is null) "
                + "and (t.STATUS = :status or :status is null) "
               + "order by t.ID desc";
    }
}
