/**
 * @Title: BatchJobDao.java
 * @Package com.yuecheng.hops.transaction.repository
 * @Description: 批量补单数据库操作 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author yupeng
 * @date 2015年11月21日 上午9:58:13
 * @version V1.0
 */

package com.yuecheng.hops.batch.repository.order;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.common.Constant;


/**
 * @ClassName: BatchJobDao
 * @Description: 批冲
 * @author yupeng
 * @date 2015年11月21日 上午9:58:13
 */
@Service
public interface BatchJobDao extends PagingAndSortingRepository<BatchJob, Long>, JpaSpecificationExecutor<BatchJob>
{

    @Query("select t from BatchJob t where t.status = 2 and t.threadNum=0")
    public List<BatchJob> queryCanHandleJob();

    @Modifying
    @Transactional
    @Query("update BatchJob b set b.status = :status where b.batchId= :batchId")
    public int updateBatchJob(@Param("status")
    int status, @Param("batchId")
    Long batchId);

    @Modifying
    @Query("update BatchJob t set t.waitHandleNum = t.totalNum - :preIndex,t.processedNum = :preIndex,t.threadNum =:threadNum,t.startIndex =:newStartNum where t.batchId = :batchId")
    public int updateBatchJobParamById(@Param("batchId")
    Long batchId, @Param("preIndex")
    Integer preIndex, @Param("threadNum")
    int threadNum, @Param("newStartNum")
    int newStartNum);

    @Query("select borh from BatchJob borh where borh.fileName=:fileName and borh.status!="
           + Constant.Batch.CANCELED)
    public List<BatchJob> queryBatchJobFileName(@Param("fileName")
    String fileName);

    @Modifying
    @Query("update BatchJob t set t.waitHandleNum = 0,t.processedNum = :finishedNum,t.status= :status,t.finishedTime=:finishedTime where t.batchId = :batchId")
    public int finishBatchJob(@Param("batchId")
    Long batchId, @Param("finishedNum")
    Integer finishedNum, @Param("status")
    int status,@Param("finishedTime")
    Date finishedTime);

    @Modifying
    @Query("update BatchJob t set t.threadNum = t.threadNum - 1 where t.batchId = :batchId")
    public int subBatchJobThreadNum(@Param("batchId")
    Long batchId);

    @Modifying
    @Transactional
    @Query("update BatchJob b set b.status = :status,b.auditTime=:auditTime where b.batchId= :batchId")
    public int updateAuditBatchJob(@Param("status")
    int status, @Param("auditTime")
    Date auditTime, @Param("batchId")
    Long batchId);

    @Modifying
    @Transactional
    @Query("update BatchJob b set b.status = :status,b.pausedTime=:pausedTime where b.batchId= :batchId")
    public int updatePausedBatchJob(@Param("status")
    int status, @Param("pausedTime")
    Date pausedTime, @Param("batchId")
    Long batchId);

    @Modifying
    @Transactional
    @Query("update BatchJob b set b.status = :status,b.startedTime=:startedTime where b.batchId= :batchId")
    public int updateStartedBatchJob(@Param("status")
    int status, @Param("startedTime")
    Date startedTime, @Param("batchId")
    Long batchId);

    @Query("select borh from BatchJob borh where borh.status=:status")
    public List<BatchJob> queryBatchJobByStatus(@Param("status")
    int status);
}

