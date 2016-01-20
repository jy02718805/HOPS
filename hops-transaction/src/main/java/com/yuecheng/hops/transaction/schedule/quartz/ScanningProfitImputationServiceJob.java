package com.yuecheng.hops.transaction.schedule.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.transaction.service.scanner.ProfitImputationScanner;

/**
 * 利润归集定时任务
 * 
 * @author Administrator
 * @version 2014年10月24日
 * @see ScanningProfitImputationServiceJob
 * @since
 */
public class ScanningProfitImputationServiceJob
{
    private ProfitImputationScanner profitImputationScanner;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScanningProfitImputationServiceJob.class);

    public ProfitImputationScanner getProfitImputationScanner()
    {
        return profitImputationScanner;
    }

    public void setProfitImputationScanner(ProfitImputationScanner profitImputationScanner)
    {
        this.profitImputationScanner = profitImputationScanner;
    }

    public void execute()
    {
        // TODO Auto-generated method stub
        LOGGER.debug("任务开始。。。。。。。。。。taskProfitImputation。。。。。。。。。。。。。。");
        // 利润归集
        profitImputationScanner.taskProfitImputation();
        // 重新归集没有成功的记录
    }

}
