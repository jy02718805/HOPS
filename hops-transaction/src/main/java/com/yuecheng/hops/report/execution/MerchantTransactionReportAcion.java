/*
 * 文件名：MerchantTransactionReportAcion.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月18日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.report.execution;


import java.util.Date;
import java.util.List;


/**
 * 商户报表统计
 * 
 * @author Administrator
 * @version 2014年10月23日
 * @see MerchantTransactionReportAcion
 * @since
 */
public interface MerchantTransactionReportAcion
{
    /**
     * 统计代理商户各个产品总量
     * 
     * @see
     */
    void censusAgentTransactionReport();

    /**
     * 统计供货商各个产品总量
     * 
     * @see
     */
    void censusSupplyTransactionReport();

    /**
     * 统计交易量
     * 
     * @param beginTime
     * @param endTime
     * @param merchantType
     * @return
     * @see
     */
    List<?> censusTransactionReports(Date beginTime, Date endTime, String merchantType);
    
    
    
    /**
     * 统计代理商户各个产品总量测试
     * 
     * @see
     */
    void censusAgentTransactionReportTest(String beginTime,String endTime);
    void censusSupplyTransactionReportTest(String beginTime,String endTime);
}
