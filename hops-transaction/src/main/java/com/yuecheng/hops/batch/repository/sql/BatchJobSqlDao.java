/**
 * @Title: BatchOrderRequestHandler.java
 * @Package com.yuecheng.hops.transaction.repository
 * @Description: 批量手工补单数据库操作 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午9:58:13
 * @version V1.0
 */

package com.yuecheng.hops.batch.repository.sql;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.common.query.YcPage;


@Service
public interface BatchJobSqlDao
{
    public YcPage<BatchJob> queryPageBatchJob(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize);
}
