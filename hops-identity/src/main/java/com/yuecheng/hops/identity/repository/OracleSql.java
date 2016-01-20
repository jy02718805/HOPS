package com.yuecheng.hops.identity.repository;

public class OracleSql
{
    public class Merchant
    {
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
            + "select t.* from Merchant t where 1=1 "
            + "and (t.merchant_name like '%'||:merchantName||'%' or :merchantName is null) "
            + "and (t.merchant_type = :merchantType or :merchantType is null) "
            + "order by t.identity_Id desc) a where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String All_SQL = "select * from Merchant t where 1=1 "
            + "and (t.merchant_name like '%'||:merchantName||'%' or :merchantName is null) "
            + "and (t.merchant_type = :merchantType or :merchantType is null) "
           + "order by t.identity_Id desc";
    }
}
