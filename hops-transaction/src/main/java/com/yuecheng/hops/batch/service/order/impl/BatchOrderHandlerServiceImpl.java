/*
 * 文件名：BatchOrderHandlerServiceImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：machenike
 * 修改时间：2015年11月21日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.batch.service.order.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.batch.service.order.BatchJobDetailService;
import com.yuecheng.hops.batch.service.order.BatchJobService;
import com.yuecheng.hops.batch.service.order.BatchOrderHandlerService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;

@Service("batchOrderHandlerService")
public class BatchOrderHandlerServiceImpl implements BatchOrderHandlerService
{
	private static final int DEFAULT_HANDEL_NUM = 100;

	private static final int DEFAULT_THREAD_NUM = 10;

	@Autowired
	private BatchJobService batchJobService;

	@Autowired
	private BatchJobDetailService batchJobDetailService;

	@Autowired
	private ParameterConfigurationService parameterConfigurationService;

	public static Logger logger = LoggerFactory.getLogger(BatchOrderHandlerServiceImpl.class);

	@Override
	public void process()
	{
		try
		{
			List<BatchJob> batchJobs = batchJobService.queryCanHandleJob();

			if (batchJobs.size() > 0)
			{
				int handleNum = DEFAULT_HANDEL_NUM;
				int threadNum = DEFAULT_THREAD_NUM;
				ParameterConfiguration hc = parameterConfigurationService
						.getParameterConfigurationByKey(Constant.ParameterConfiguration.BATCH_DETAIL_NUM);
				if (null != hc)
				{
					handleNum = Integer.parseInt(hc.getConstantValue());
				}
				BatchJob batchJob = batchJobs.get(0);
				Long batchId = batchJob.getBatchId();
				
				List<BatchJobDetail> jobDetails = batchJobDetailService.getJobDetailsByBatchId(batchId,
						batchJob.getStartIndex(), batchJob.getStartIndex() + handleNum + 1);
				
				int jobDetailsCount = jobDetails.size();
				if (jobDetailsCount > 0)
				{
				    logger.info("handleNum["+handleNum+"] jobDetails.size["+jobDetails.size()+"]");
				    
					hc = parameterConfigurationService
							.getParameterConfigurationByKey(Constant.ParameterConfiguration.BATCH_THREAD_NUM);
					if (null != hc)
					{
					    threadNum = Integer.parseInt(hc.getConstantValue());
					}
					if (jobDetailsCount < threadNum)
					{
						threadNum = jobDetailsCount;
					}
					batchJobService.updateBatchJobParam(batchId, batchJob.getStartIndex(), handleNum, threadNum);
					
					batchJob.setThreadNum(threadNum);
					batchJobService.start(batchJob, jobDetails);
				}
				else
				{
					batchJobService.finishBatchJob(batchId, Constant.Batch.FINSHED, batchJob.getTotalNum(), new Date());

				}
			}
		}
		catch (Exception e)
		{
			logger.error("批冲扫描出现错误,请检查相关配置!");
			e.printStackTrace();
		}

	}

}
