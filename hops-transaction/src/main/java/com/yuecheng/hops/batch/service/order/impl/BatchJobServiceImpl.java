/**
 * @Title: BatchOrderRequestHandlerManagementImpl.java
 * @Package com.yuecheng.hops.transaction.service.order.impl
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午10:53:12
 * @version V1.0
 */

package com.yuecheng.hops.batch.service.order.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.batch.repository.order.BatchJobDao;
import com.yuecheng.hops.batch.repository.sql.BatchJobSqlDao;
import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.batch.service.order.BatchJobService;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.batch.thread.AatchOrderListThread;


@Transactional
@Service("batchJobService")
public class BatchJobServiceImpl implements BatchJobService
{
    public static Logger logger = LoggerFactory.getLogger(BatchJobServiceImpl.class);

    @Autowired
    private BatchJobDao batchJobDao;

    @Override
    public List<BatchJob> queryCanHandleJob()
    {
        return batchJobDao.queryCanHandleJob();
    }

    @Override
    public void updateBatchJobParam(Long batchId, int preIndex, int handleNum, int threadNum)
    {
        batchJobDao.updateBatchJobParamById(batchId, preIndex, threadNum, preIndex + handleNum);

    }

    @Override
    public void finishBatchJob(Long batchId, int status, int finishedNum, Date finishedTime)
    {
        batchJobDao.finishBatchJob(batchId, finishedNum, status,finishedTime);

    }

    @Override
    public void subThreadNum(Long batchId)
    {
        batchJobDao.subBatchJobThreadNum(batchId);
    }

    @Autowired
    private BatchJobDao batchJobJpaDao;

    @Autowired
    private BatchJobSqlDao batchJobSqlDao;

    @Override
    public YcPage<BatchJob> queryPageBatchJob(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize)
    {
        // TODO Auto-generated method stub
        YcPage<BatchJob> ycPage = batchJobSqlDao.queryPageBatchJob(searchParams, pageNumber,
            pageSize);
        return ycPage;
    }

    @Override
    public BatchJob saveBatchJob(BatchJob batchJob)
    {
        // TODO Auto-generated method stub
        batchJob = batchJobJpaDao.save(batchJob);
        return batchJob;
    }

    @Override
    public int updateAuditBatchJob(int status, Long batchId, Date auditDate)
    {
        // TODO Auto-generated method stub
        int i = 0;
        try
        {
            i = batchJobJpaDao.updateAuditBatchJob(status, auditDate, batchId);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            logger.error("BatchJobService:updateAuditBatchJob [" + e.getMessage() + "]");
        }
        return i;
    }

    @Override
    public BatchJob getBatchJob(Long batchId)
    {
        // TODO Auto-generated method stub
        BatchJob batchJob = batchJobJpaDao.findOne(batchId);
        return batchJob;
    }

    @Override
    public List<BatchJob> queryBatchJobByFileName(String fileName)
    {
        List<BatchJob> bjList = batchJobJpaDao.queryBatchJobFileName(fileName);
        return bjList;
    }

    @Override
    public void start(BatchJob job, List<BatchJobDetail> batchJobDetails)
    {
        AatchOrderListThread a = new AatchOrderListThread(job, batchJobDetails);
        Thread t = new Thread(a);
        t.start();
    }

    @Override
    public int updateStartBatchJob(int status, Long batchId, Date pasuseDate)
    {
        int i = 0;
        try
        {
            i = batchJobJpaDao.updateStartedBatchJob(status, pasuseDate, batchId);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            logger.error("BatchJobService: updateStartBatchJob [" + e.getMessage() + "]");
        }

        return i;
    }

    @Override
    public int updatePasuseBatchJob(int status, Long batchId, Date pasuseDate)
    {
        // TODO Auto-generated method stub
        int i = 0;
        try
        {
            i = batchJobJpaDao.updatePausedBatchJob(status, pasuseDate, batchId);
        }
        catch (Exception e)
        {
            logger.error("BatchJobService: updatePasuseBatchJob [" + e.getMessage() + "]");
        }

        return i;
    }

    @Override
    public List<BatchJob> queryBatchJobByStatus(int status)
    {
        List<BatchJob> bjList = batchJobJpaDao.queryBatchJobByStatus(status);
        return bjList;
    }

}