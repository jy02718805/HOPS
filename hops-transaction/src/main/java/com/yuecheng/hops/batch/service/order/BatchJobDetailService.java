/**
 * @Title: BatchOrderRequestHandlerManagement.java
 * @Package com.yuecheng.hops.transaction.service.order
 * @Description: 批量手工补单管理 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午10:47:44
 * @version V1.0
 */

package com.yuecheng.hops.batch.service.order;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.common.query.YcPage;



public interface BatchJobDetailService
{
	public List<BatchJobDetail> getJobDetailsByBatchId(Long batchId,int startIndex, int endIndex);
	
	public List<BatchJobDetail> updateJobDetailsStatus(List<BatchJobDetail> batchJobDetails, Integer targeStatus);
	
	public void updateJobDetail(Long id,Long batchId, Integer targeStatus, Long orderNo, String rmk);
    boolean saveBatchJobDetails(List<BatchJobDetail> batchJobDetails);

    public YcPage<BatchJobDetail> queryPageBatchJobDetail(Map<String, Object> searchParams,
                                                          int pageNumber, int pageSize);
}
