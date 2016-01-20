/*
 * 文件名：OracleSql.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月12日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.basic.repository;


import java.util.Map;

import javax.persistence.Query;

import com.yuecheng.hops.common.utils.BeanUtils;


public class OracleSql
{
    public class Order
    {
        public static final String QUERYORDERS_SQL = "select * from (select yc.*,rownum rn from ("
                                                     + " select * from yc_order o where 1=1 and  ((o.ext1=:ext1 and :ext1 is not null) or :ext1 is null )"
                                                     + " and ((o.ext2=:ext2 and :ext2 is not null) or :ext2 is null ) and ((o.order_fee=:orderFee and :orderFee is not null) or :orderFee is null ) "
                                                     + " and ((o.merchant_id=:merchantId and :merchantId is not null) or :merchantId is null) "
                                                     + " and ((o.order_status=:orderStatus and :orderStatus is not null) or :orderStatus is null)"
                                                     + " and ((o.notify_status=:notifyStatus and :notifyStatus is not null) or :notifyStatus is null)"
                                                     + " and ((o.pre_success_status=:preSuccessStatus and :preSuccessStatus is not null) or :preSuccessStatus is null)"
                                                     + " and ((o.user_code=:userCode and :userCode is not null) or :userCode is null)"
                                                     + " and ((o.order_no=:orderNo and :orderNo is not null) or :orderNo is null)"
                                                     + " and ((o.merchant_order_no=:merchantOrderNo and :merchantOrderNo is not null) or :merchantOrderNo is null)"
                                                     + " and ((o.order_request_time >= to_date(:beginDate,'yyyy-mm-dd hh24:mi:ss') and :beginDate is not null) or :beginDate is null) "
                                                     + " and ((o.order_request_time <= to_date(:endDate,'yyyy-mm-dd hh24:mi:ss') and :endDate is not null) or :endDate is null)"
                                                     + " order by o.order_request_time desc)yc  where 1=1 and ((rownum<=:rownum and :rownum is not null) or :rownum is null)) ";

        public static final String QUERYFAKEORDERS_SQL = "select * from (select yc.*,rownum rn from ("
                                                         + " select * from yc_order o where 1=1 and  ((o.ext1=:ext1 and :ext1 is not null) or :ext1 is null )"
                                                         + " and ((o.ext2=:ext2 and :ext2 is not null) or :ext2 is null ) and ((o.order_fee=:orderFee and :orderFee is not null) or :orderFee is null ) "
                                                         + " and ((o.merchant_id=:merchantId and :merchantId is not null) or :merchantId is null) "
                                                         + " and ((o.order_status=:orderStatus and :orderStatus is not null) or :orderStatus is null)"
                                                         + " and ((o.notify_status=:notifyStatus and :notifyStatus is not null) or :notifyStatus is null)"
                                                         + " and ((o.pre_success_status=:preSuccessStatus and :preSuccessStatus is not null) or :preSuccessStatus is null)"
                                                         + " and ((o.user_code=:userCode and :userCode is not null) or :userCode is null)"
                                                         + " and ((o.order_no=:orderNo and :orderNo is not null) or :orderNo is null)"
                                                         + " and ((o.merchant_order_no=:merchantOrderNo and :merchantOrderNo is not null) or :merchantOrderNo is null)"
                                                         + " and ((o.order_request_time >= to_date(:beginDate,'yyyy-mm-dd hh24:mi:ss') and :beginDate is not null) or :beginDate is null) "
                                                         + " and ((o.order_request_time <= to_date(:endDate,'yyyy-mm-dd hh24:mi:ss') and :endDate is not null) or :endDate is null)"
                                                         + " and o.pre_success_status!='0' "
                                                         + " order by o.order_request_time desc)yc  where 1=1 and ((rownum<=:rownum and :rownum is not null) or :rownum is null)) ";

        
    }

    public class Delivery
    {
        public static final String STATISTICSDELIVERY_SQL = "select sum(yd.success_fee) as ORDERSUCCESSFEE,sum(yo.display_value*yo.product_sale_discount) as SALESFEE,sum(yd.cost_fee) as COSTFEE from yc_delivery yd"
                                                            + ",yc_order yo where yd.order_no=yo.order_no and yo.order_status = 3 and yd.delivery_status = 4 ";

        public static final String STATISTICSDELIVERY_TOTAL_SQL = "select sum(yd.product_face) as PRODUCTFACE from yc_delivery yd, yc_order yo where yd.order_no=yo.order_no ";

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

    public static Query setParameter(Map<String, Object> fields, Query query)
    {
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
            query.setParameter(entry.getKey(),
                BeanUtils.isNotNull(entry.getValue()) ? entry.getValue().toString() : "");
        }
        return query;

    }
}
