/*
 * 文件名：OracleSql.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月10日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.config.repository;

import java.util.Map;

import javax.persistence.Query;

import com.yuecheng.hops.common.utils.BeanUtils;

public class OracleSql
{
    public class ProfitImputationInfo
    {
        public static final String QUERYPROFITIMPUTATIONINFOS_SQL = "select imputation.*,rownum rn from (select * from Profit_Imputation p where 1=1 "
                                                                    + "and ((p.imputation_begin_date>=to_date(:imputationBeginDate,'yyyy/mm/dd hh24:mi:ss') and :imputationBeginDate is not null) or :imputationBeginDate is null) "
                                                                    + "and ((p.imputation_begin_date<=to_date(:imputationEndDate,'yyyy/mm/dd hh24:mi:ss') and :imputationEndDate is not null) or :imputationEndDate is null) "
                                                                    + "and ((p.imputation_end_date>=to_date(:updateBeginDate,'yyyy/mm/dd hh24:mi:ss') and :updateBeginDate is not null) or :updateBeginDate is null)  "
                                                                    + "and ((p.imputation_begin_date<=to_date(:updateEndDate,'yyyy/mm/dd hh24:mi:ss') and :updateEndDate is not null) or :updateEndDate is null) "
                                                                    + "and  ((p.merchant_name like '%'||:merchantName||'%'  and :merchantName is not null) or :merchantName is null) "
                                                                    + "and ((p.imputation_status=:imputationStatus  and :imputationStatus is not null) or :imputationStatus is null)"
                                                                    + " order by p.imputation_begin_date desc)imputation where 1=1 and  ((rownum<=:rownum and :rownum is not null) or :rownum is null) ";
    }
    
    
    
    
    
    
    
    
    
    
    public static Query setParameter(Map<String, Object> fields, Query query, int startIndex,
                                     int endIndex)
    {
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
            query.setParameter(entry.getKey(),
                BeanUtils.isNotNull(entry.getValue()) ? entry.getValue().toString() : "");
        }

        if (endIndex > 0)
        {
            query.setParameter("rownum", endIndex);

            query.setParameter("startIndex", startIndex);
        }
        else
        {
            query.setParameter("rownum", "");
        }
        return query;

    }
}
