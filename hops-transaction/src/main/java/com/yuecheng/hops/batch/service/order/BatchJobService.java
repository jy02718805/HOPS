/**
 * @Title: BatchOrderRequestHandlerManagement.java
 * @Package com.yuecheng.hops.transaction.service.order
 * @Description: 批量手工补单管理 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午10:47:44
 * @version V1.0
 */

package com.yuecheng.hops.batch.service.order;


import java.util.Date;
import java.util.List;

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.batch.entity.BatchJobDetail;

import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;


public interface BatchJobService
{
    YcPage<BatchJob> queryPageBatchJob(Map<String, Object> searchParams, int pageNumber,
                                       int pageSize);

    BatchJob saveBatchJob(BatchJob batchJob);

    public List<BatchJob> queryCanHandleJob();

    BatchJob getBatchJob(Long batchId);

    List<BatchJob> queryBatchJobByFileName(String fileName);
    
    List<BatchJob> queryBatchJobByStatus(int status);

    public void updateBatchJobParam(Long batchId, int startIndex, int handleNum, int threadNum);

    public void finishBatchJob(Long batchId, int status, int finishedNum, Date finishedTime);

    public void subThreadNum(Long batchId);

    public void start(BatchJob job, List<BatchJobDetail> batchJobDetails);
    
    int updateAuditBatchJob(int status, Long batchId,Date auditDate);
    
    int updateStartBatchJob(int status, Long batchId,Date pasuseDate);
    
    int updatePasuseBatchJob(int status, Long batchId,Date pasuseDate);
}
