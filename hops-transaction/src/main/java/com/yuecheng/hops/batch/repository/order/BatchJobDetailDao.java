/**
 * @Title: BatchJobDetailDao.java
 * @Package com.yuecheng.hops.transaction.repository
 * @Description: 批冲详细数据库操作 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author yupeng
 * @date 2015年11月21日 上午9:58:13
 * @version V1.0
 */

package com.yuecheng.hops.batch.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.batch.entity.BatchJobDetail;

/**
 * @ClassName: BatchJobDetailDao
 * @Description: 批冲
 * @author yupeng
 * @date 2015年11月21日 上午9:58:13
 */
@Service
public interface BatchJobDetailDao extends PagingAndSortingRepository<BatchJobDetail, Long>,
		JpaSpecificationExecutor<BatchJobDetail>
{
	@Query("select t from BatchJobDetail t where t.batchId=:batchId and t.serialNum> :startIndex and t.serialNum<:endIndex and t.status=0")
	public List<BatchJobDetail> getJobDetailsByBatchId(@Param("batchId") Long batchId,
			@Param("startIndex") int startIndex, @Param("endIndex") int endIndex);

	@Modifying
    @Query("update BatchJobDetail t set t.status = :status,t.orderNo = :orderNo,t.rmk = :rmk where t.batchId = :batchId and t.id = :id")
    public int updateBatchJobDetail(@Param("id") Long id, @Param("batchId") Long batchId, @Param("status") Integer status, @Param("orderNo") Long orderNo, @Param("rmk") String rmk);
	
	@Modifying
    @Query("update BatchJobDetail t set t.status = :status,t.rmk = :rmk where t.batchId = :batchId and t.id = :id")
    public int updateBatchJobDetail(@Param("id") Long id, @Param("batchId") Long batchId, @Param("status") Integer status, @Param("rmk") String rmk);
}
