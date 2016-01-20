/**
 * @Title: BatchOrderRequestHandler.java
 * @Package com.yuecheng.hops.transaction.repository
 * @Description: 批量手工补单数据库操作 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午9:58:13
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

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;


/**
 * @ClassName: BatchOrderRequestHandler
 * @Description: 批量手工补单
 * @author 肖进
 * @date 2014年8月14日 上午9:58:13
 */
@Service
public interface BatchOrderRequestHandlerDao extends PagingAndSortingRepository<BatchOrderRequestHandler, Long>, JpaSpecificationExecutor<BatchOrderRequestHandler>
{
    // 通过上传文件名查询
    @Query("select borh from BatchOrderRequestHandler borh where borh.upFile=:upFile and borh.orderStatus!=3 ORDER by borh.orderRequestTime")
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandlerByUpFile(@Param("upFile")
    String upFile);

    // 通过上传文件名和状态查询
    @Query("select borh from BatchOrderRequestHandler borh where borh.upFile=:upFile and borh.orderStatus=:orderStatus and borh.orderStatus!=3 ORDER by borh.orderRequestTime")
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandlerByUpFileAndOrderStatus(@Param("upFile")
                                                                                              String upFile,
                                                                                              @Param("orderStatus")
                                                                                              long orderStatus);

    // 通过状态查询
    @Query("select borh from BatchOrderRequestHandler borh where borh.upFile=:upFile and borh.orderStatus=:orderStatus and borh.orderStatus!=3 ORDER by borh.orderRequestTime")
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandlerByOrderStatus(@Param("orderStatus")
                                                                                     long orderStatus);

    // 通过id更新状态
    @Modifying
    @Transactional
    @Query("update BatchOrderRequestHandler borh set borh.orderStatus = :orderStatus where borh.id = :id")
    public int updateOrderStatusById(@Param("orderStatus")
    Long orderStatus, @Param("id")
    Long id);

    // 审核通过文件名更新状态
    @Modifying
    @Transactional
    @Query("update BatchOrderRequestHandler borh set borh.orderStatus = :orderStatus where borh.upFile=:upFile and rownum<=:number and borh.orderStatus!=3")
    public int updateAuditByUpFile(@Param("orderStatus")
    Long orderStatus, @Param("upFile")
    String upFile, @Param("number")
    Long number);

    // 删除通过文件名更新状态
    @Modifying
    @Transactional
    @Query("update BatchOrderRequestHandler borh set borh.orderStatus = :orderStatus,borh.orderDeleteTime=:orderDeleteTime  where borh.upFile=:upFile")
    public int updateByUpFile(@Param("orderStatus")
    Long orderStatus, @Param("orderDeleteTime")
    Date orderDeleteTime, @Param("upFile")
    String upFile);

}
