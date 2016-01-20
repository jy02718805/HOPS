/*
 * 文件名：OracleSql.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015-4-17 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.product.repository;

public class OracleSql
{
    public class AgentProductRelation
    {
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
                                              + "select t.* from agent_product_relation t where 1=1 "
                                              + "and (t.identity_id =:identityId or :identityId is null) "
                                              + "and (t.identity_type = :identityType or :identityType is null) "
                                              + "and (t.carrier_name = :carrierName or :carrierName is null) "
                                              + "and (t.province = :province or :province is null) "
                                              + "and (t.city = :city or :city is null) "
                                              + "and (t.business_type = :businessType or :businessType is null) "
                                              + "and (t.par_value = :parValue or :parValue is null) "
                                              + "and ((t.discount>=:discount and :discount is not null) or :discount is null) "
                                              + "and ((t.discount<=:discount2 and :discount2 is not null) or :discount2 is null) "
                                              + "order by t.discount desc,to_number(t.par_value) asc,t.identity_id desc,t.carrier_name desc,t.province desc,t.city desc) a "
                                              + "where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String PAGE_All_SQL = "select * from agent_product_relation t where 1=1 "
                                                  + "and (t.identity_id =:identityId or :identityId is null) "
                                                  + "and (t.identity_type = :identityType or :identityType is null) "
                                                  + "and (t.carrier_name = :carrierName or :carrierName is null) "
                                                  + "and (t.province = :province or :province is null) "
                                                  + "and (t.city = :city or :city is null) "
                                                  + "and (t.business_type = :businessType or :businessType is null)"
                                                  + "and (t.par_value = :parValue or :parValue is null) "
                                                  + "and ((t.discount>=:discount and :discount is not null) or :discount is null) "
                                                  + "and ((t.discount<=:discount2 and :discount2 is not null) or :discount2 is null) "
                                                  + "order by t.discount desc,to_number(t.par_value) asc,t.identity_id desc,t.carrier_name desc,t.province desc,t.city desc";

        public static final String Query_List = " select * from agent_Product_Relation a where 1=1 and exists (select p.product_id from airtime_product p where p.product_id=a.product_id "
                                                + " and ((p.is_common_use=:isCommonUse and :isCommonUse is not null) or :isCommonUse is null)) "
                                                + " and not exists(select b.product_id from agent_Product_Relation b where b.identity_id=:newMerchantId and a.product_id=b.product_id)"
                                                + " and ((a.identity_id=:identityId and :identityId is not null) or :identityId is null) "
                                                + " and ((a.carrier_name=:carrierName and :carrierName is not null) or :carrierName is null)"
                                                + " and ((a.business_type=:businessType and :businessType is not null) or :businessType is null)"
                                                + " and ((a.province=:province and :province is not null) or :province is null)"
                                                + " and ((a.city=:city and :city is not null) or :city is null)"
                                                + " and ((to_number(a.par_value)>=:parValue and :parValue is not null) or :parValue is null) "
                                                + " and ((to_number(a.par_value)<=:parValue2 and :parValue2 is not null) or :parValue2 is null) "
                                                + "order by a.carrier_name, a.province,to_number(a.par_value) desc";

        public static final String Query_Product_Relation = "select * from agent_product_relation t where t.par_value = :parValue "
                                                            + "and t.identity_type = :identityType and t.identity_id = :identityId "
                                                            + "and t.carrier_name = :carrierName ";
    }

    public class SupplyProductRelation
    {
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
                                              + "select t.* from supply_product_relation t where 1=1 "
                                              + "and (t.identity_id =:identityId or :identityId is null) "
                                              + "and (t.identity_type = :identityType or :identityType is null) "
                                              + "and (t.carrier_name = :carrierName or :carrierName is null) "
                                              + "and (t.province = :province or :province is null) "
                                              + "and (t.city = :city or :city is null) "
                                              + "and (t.business_type = :businessType or :businessType is null)"
                                              + "and (t.par_value = :parValue or :parValue is null) "
                                              + "and ((t.discount>=:discount and :discount is not null) or :discount is null) "
                                              + "and ((t.discount<=:discount2 and :discount2 is not null) or :discount2 is null) "
                                              + "order by t.discount desc,to_number(t.par_value) asc,t.identity_id desc,t.carrier_name desc,t.province desc,t.city desc) a "
                                              + "where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String PAGE_All_SQL = "select * from supply_product_relation t where 1=1 "
                                                  + "and (t.identity_id =:identityId or :identityId is null) "
                                                  + "and (t.identity_type = :identityType or :identityType is null) "
                                                  + "and (t.carrier_name = :carrierName or :carrierName is null) "
                                                  + "and (t.province = :province or :province is null) "
                                                  + "and (t.city = :city or :city is null) "
                                                  + "and (t.business_type = :businessType or :businessType is null)"
                                                  + "and (t.par_value = :parValue or :parValue is null) "
                                                  + "and ((t.discount>=:discount and :discount is not null) or :discount is null) "
                                                  + "and ((t.discount<=:discount2 and :discount2 is not null) or :discount2 is null) "
                                                  + "order by t.discount desc,to_number(t.par_value) asc,t.identity_id desc,t.carrier_name desc,t.province desc,t.city desc";

        public static final String Query_List = " select * from supply_Product_Relation a where 1=1 and exists (select p.product_id from airtime_product p where p.product_id=a.product_id "
                                                + " and ((p.is_common_use=:isCommonUse and :isCommonUse is not null) or :isCommonUse is null))"
                                                + " and not exists(select b.product_id from supply_Product_Relation b where b.identity_id=:newMerchantId and a.product_id=b.product_id)"
                                                + " and ((a.identity_id=:identityId and :identityId is not null) or :identityId is null) "
                                                + " and ((a.carrier_name=:carrierName and :carrierName is not null) or :carrierName is null)"
                                                + " and ((a.business_type=:businessType and :businessType is not null) or :businessType is null)"
                                                + " and ((a.province=:province and :province is not null) or :province is null)"
                                                + " and ((a.city=:city and :city is not null) or :city is null)"
                                                + " and ((to_number(a.par_value)>=:parValue and :parValue is not null) or :parValue is null) "
                                                + " and ((to_number(a.par_value)<=:parValue2 and :parValue2 is not null) or :parValue2 is null) order by a.carrier_name, a.province,to_number(a.par_value) desc";
    }

    public class AirtimeProduct
    {
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
                                              + "select t.* from airtime_product t where 1=1 "
                                              + "and (t.carrier_name = :carrierName or :carrierName is null) "
                                              + "and (t.province = :province or :province is null) "
                                              + "and (t.city = :city or :city is null) "
                                              + "and (t.par_value = :parValue or :parValue is null) "
                                              + "and (t.product_status <> :productStatus or :productStatus is null) "
                                              + "and (t.business_type = :businessType or :businessType is null)"
                                              + "order by t.carrier_name desc,t.province desc,t.city desc,to_number(t.par_value) asc) a "
                                              + "where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String PAGE_All_SQL = "select * from airtime_product t where 1=1 "
                                                  + "and (t.carrier_name = :carrierName or :carrierName is null) "
                                                  + "and (t.province = :province or :province is null) "
                                                  + "and (t.city = :city or :city is null) "
                                                  + "and (t.par_value = :parValue or :parValue is null) "
                                                  + "and (t.product_status <> :productStatus or :productStatus is null) "
                                                  + "and (t.business_type = :businessType or  :businessType is null)"
                                                  + "order by t.carrier_name desc,t.province desc,t.city desc,to_number(t.par_value) asc";

    }
}
