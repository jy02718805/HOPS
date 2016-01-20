package com.yuecheng.hops.transaction.execution.imputation;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;


/**
 * 归集利润
 * 
 * @author Administrator
 * @version 2014年10月13日
 * @see ProfitImputationService
 * @since
 */
public interface ProfitImputationService
{
    /**
     * 定时归集任务
     * 
     * @see
     */
    void taskProfitImputation(String beginDate, String endDate);

    /**
     * 商户利润账户归集到中间利润账户
     * 
     * @param profitImputationInfo
     * @return
     * @see
     */
    boolean isAccountImputation(ProfitImputationInfo profitImputationInfo);

    /**
     * 定时重新归集任务
     * 
     * @see
     */
    void reTaskProfitImputation();

    /**
     * 重新清算
     * 
     * @see
     */
    ProfitImputationInfo reImputationProfit(Long profitImputationId);

    List<ProfitImputationInfo> imputationProfit(Date beginTime, Date endTime);

}
