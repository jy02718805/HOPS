package com.yuecheng.hops.batch.schedule.quartz;


import com.yuecheng.hops.batch.service.order.BatchOrderHandlerService;


public class BatchOrderHandlerServiceJob
{
	BatchOrderHandlerService batchOrderHandlerService;

    public BatchOrderHandlerService getBatchOrderHandlerService()
    {
        return batchOrderHandlerService;
    }

    public void setBatchOrderHandlerService(BatchOrderHandlerService batchOrderHandlerService)
    {
        this.batchOrderHandlerService = batchOrderHandlerService;
    }

    public void execute()
    {
    	batchOrderHandlerService.process();
    }
}
