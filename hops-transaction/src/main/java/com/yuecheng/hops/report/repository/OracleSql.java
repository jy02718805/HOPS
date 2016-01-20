/*
 * 文件名：OracleSql.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年1月6日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.repository;

public class OracleSql
{
    public class TransactionReport
    {
       /* public static final String GETTRANSACTIONREPORTBOS_SUPPLY_SQL = "select new com.yuecheng.hops.report.entity.bo.TransactionReportBo( "
                                                                        + "d.merchantId,o.ext1,o.ext2,o.ext3,o.productFace,sum(o.orderSalesFee),sum(o.productFace),sum(d.costFee),count(o),o.orderStatus,o.userCode)"
                                                                        + " from Order o ,Delivery d  where 1=1  and o.orderNo=d.orderNo and o.orderFinishTime>=to_date(:beginTime,'yyyy-mm-dd hh24:mi:ss') "
                                                                        + "and o.orderFinishTime<=to_date(:endTime,'yyyy-mm-dd hh24:mi:ss')  group by d.merchantId,o.ext1,o.ext2,o.ext3,o.productFace,o.orderStatus,o.userCode";

        public static final String GETTRANSACTIONREPORTBOS_AGENT_SQL = "select new com.yuecheng.hops.report.entity.bo.TransactionReportBo( "
                                                                       + "o.merchantId,o.ext1,o.ext2,o.ext3,o.productFace,sum(o.orderSalesFee),sum(o.productFace),count(o),o.orderStatus,o.userCode)"
                                                                       + " from Order o where 1=1 and o.orderFinishTime>=to_date(:beginTime,'yyyy-mm-dd hh24:mi:ss') "
                                                                       + "and o.orderFinishTime<=to_date(:endTime,'yyyy-mm-dd hh24:mi:ss')  group by o.merchantId,o.ext1,o.ext2,o.ext3,o.productFace,o.orderStatus,o.userCode";
*/
    	public static final String GETTRANSACTIONREPORTBOS_SUPPLY_SQL = "select new com.yuecheng.hops.report.entity.bo.TransactionReportBo( "
                + "d.merchantId,o.ext1,o.ext2,o.ext3,o.productFace,o.orderSalesFee,o.productFace,d.costFee,'1' ,o.orderStatus,d.userCode,o.businessType,o.merchantOrderNo)"
                + " from Order o ,Delivery d  where 1=1  and o.orderNo=d.orderNo "
                + " and d.deliveryStartTime=(select max(d.deliveryStartTime) from Delivery d where d.orderNo=o.orderNo)"
                + " and o.orderFinishTime>=to_date(:beginTime,'yyyy-mm-dd hh24:mi:ss') "
                + "and o.orderFinishTime<=to_date(:endTime,'yyyy-mm-dd hh24:mi:ss')  ";
    	public static final String GETTRANSACTIONREPORTBOS_AGENT_SQL = "select new com.yuecheng.hops.report.entity.bo.TransactionReportBo( "
                + "o.merchantId,o.ext1,o.ext2,o.ext3,o.productFace,o.orderSalesFee,o.productFace,'1',o.orderStatus,o.userCode,o.businessType,o.merchantOrderNo)"
                + " from Order o where 1=1 and o.orderFinishTime>=to_date(:beginTime,'yyyy-mm-dd hh24:mi:ss') "
                + "and o.orderFinishTime<=to_date(:endTime,'yyyy-mm-dd hh24:mi:ss')";
    }

    public class AgentTransactionReport
    {
    	
        public static final String GETMERCHANTTRANSACTIONINFO_SQL = "select new com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo("
                                                                    + "o.merchantId,o.productId,sum(o.orderSalesFee),sum(o.displayValue),count(o),o.orderStatus,o.businessType)  from Order o where 1=1 "
                                                                    + "and o.orderFinishTime>=to_date(:beginTime,'yyyy-mm-dd hh24:mi:ss') and o.orderFinishTime<=to_date(:endTime,'yyyy-mm-dd hh24:mi:ss') "
                                                                    + "group by o.merchantId,o.productId,o.orderStatus,o.businessType";
    }

    public class SupplyTransactionReport
    {
        public static final String GETMERCHANTTRANSACTIONINFO_SQL = "select new com.yuecheng.hops.report.entity.bo.MerchantTransactionReportBo("
                                                                    + "d.merchantId,o.productId,sum(o.orderSalesFee),sum(o.displayValue),sum(d.costFee),count(o),o.orderStatus,o.businessType)  from Order o,Delivery d where 1=1 "
                                                                    + "and o.orderNo=d.orderNo and o.orderFinishTime>=to_date(:beginTime,'yyyy-mm-dd hh24:mi:ss') "
                                                                    + "and o.orderFinishTime<=to_date(:endTime,'yyyy-mm-dd hh24:mi:ss') "
                                                                    + "and d.deliveryStartTime=(select max(d.deliveryStartTime) from Delivery d where d.orderNo=o.orderNo) group by d.merchantId,o.productId,o.orderStatus,o.businessType";
    }
}
