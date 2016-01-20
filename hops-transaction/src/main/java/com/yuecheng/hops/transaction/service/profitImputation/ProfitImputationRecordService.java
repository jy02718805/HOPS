/*
 * 文件名：ProfitImputationRecordService.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月24日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.service.profitImputation;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationRecord;


/**
 * 记录利润归集服务〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2014年10月28日
 * @see ProfitImputationRecordService
 * @since
 */
public interface ProfitImputationRecordService
{
    List<ProfitImputationRecord> queryProfitImputationRecordList(String recordStatus);

    ProfitImputationRecord saveProfitImputationRecord(ProfitImputationRecord profitImputationRecord);

    public ProfitImputationRecord queryProfitImputationRecord(Date imputationDate);

    /**
     * 保存利润归集记录
     * 
     * @param profitImputationRecord
     * @param recordStatus
     * @param recordDescribe
     * @return
     * @see
     */
    ProfitImputationRecord saveProfitImputationRecord(ProfitImputationRecord profitImputationRecord,
                                                      String recordStatus, String recordDescribe);

    /**
     * update利润归集记录
     * 
     * @param profitImputationRecord
     * @param recordStatus
     * @param recordDescribe
     * @return
     * @see
     */
    ProfitImputationRecord updateProfitImputationRecord(String recordStatus, String recordDescribe);
}
