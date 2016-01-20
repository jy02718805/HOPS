package com.yuecheng.hops.rebate.schedule.quartz;


import java.util.Date;

import com.yuecheng.hops.rebate.service.RebateRecordService;


public class CalRebateRecordServiceJob
{
    RebateRecordService rebateRecordService;

    public RebateRecordService getRebateRecordService()
    {
        return rebateRecordService;
    }

    public void setRebateRecordService(RebateRecordService rebateRecordService)
    {
        this.rebateRecordService = rebateRecordService;
    }

    public void execute()
    {
        rebateRecordService.createRebateRecords(new Date());
    }
}
