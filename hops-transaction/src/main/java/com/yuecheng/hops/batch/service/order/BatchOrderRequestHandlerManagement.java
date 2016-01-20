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
import java.util.Map;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;


/**
 * @ClassName: BatchOrderRequestHandlerManagement
 * @Description: 批量手工补单管理
 * @author 肖进
 * @date 2014年8月14日 上午10:47:44
 */

public interface BatchOrderRequestHandlerManagement
{
    /**
     *  通过上传文件名查询
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param upFile
     * @return 
     * @see
     */
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandlerByUpFile(String upFile);

    /**
     * 通过状态查询
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param orderStatus
     * @return 
     * @see
     */
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandlerByOrderStatus(Long orderStatus);

    /**
     * 通过上传文件名和状态查询
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param upFile
     * @param orderStatus
     * @return 
     * @see
     */
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandler(String upFile,
        Long orderStatus);

    /**
     * 上传补单文件保存
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param borh
     * @return 
     * @see
     */
    public BatchOrderRequestHandler save(BatchOrderRequestHandler borh);

    /**
     *  得到全部上传文件名列表
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @return 
     * @see
     */
    public List<String> getUpfileAll();

    /**
     * 得到全部状态列表
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @return 
     * @see
     */
    public List<String> getOrderStatusAll();

    /**
     *  通过id更新状态
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param orderStatus
     * @param id
     * @return 
     * @see
     */
    public int update(Long orderStatus, Long id);

    /**
     *  分页
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return 
     * @see
     */
    public YcPage<BatchOrderRequestHandler> queryBatchOrderRequestHandler(Map<String, Object> searchParams,
                                                                          int pageNumber,
                                                                          int pageSize, BSort bsort);

    /**
     *  分页
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param pageNumber
     * @param pageSize
     * @param upfile
     * @param orderStatus
     * @return 
     * @see
     */
    public YcPage<BatchOrderRequestHandler> queryBatchOrderRequestHandlerBySQL(int pageNumber,
                                                                               int pageSize,
                                                                               String upfile,
                                                                               Long orderStatus);

    /**
     *  删除
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param orderStatus
     * @param orderDeleteTime
     * @param upFile
     * @return 
     * @see
     */
    public int updateByUpFile(Long orderStatus, Date orderDeleteTime, String upFile);

    /**
     *  审核
     * Description: <br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param orderStatus
     * @param upFile
     * @param number
     * @return 
     * @see
     */
    public int updateAuditByUpFile(Long orderStatus, String upFile,Long number);

}
