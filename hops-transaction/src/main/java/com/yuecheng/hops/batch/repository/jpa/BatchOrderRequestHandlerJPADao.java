/**
 * @Title: BatchOrderRequestHandlerJPADao.java
 * @Package com.yuecheng.hops.transaction.repository.jpa
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 下午1:46:35
 * @version V1.0
 */

package com.yuecheng.hops.batch.repository.jpa;


import java.util.List;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.common.query.YcPage;


/**
 * @ClassName: BatchOrderRequestHandlerJPADao
 * @Description: TODO
 * @author 肖进
 * @date 2014年8月14日 下午1:46:35
 */

public interface BatchOrderRequestHandlerJPADao
{
    // 得到全部上传文件名列表
    public List<String> getUpfileAll();

    // 得到全部状态列表
    public List<String> getOrderStatusAll();

    // 自助SQL分页
    public YcPage<BatchOrderRequestHandler> queryBatchOrderRequestHandlerBySQL(int pageNumber,
                                                                               int pageSize,
                                                                               String upfile,
                                                                               Long orderStatus);
}
