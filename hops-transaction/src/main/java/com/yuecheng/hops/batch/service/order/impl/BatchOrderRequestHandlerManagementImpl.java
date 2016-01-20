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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.repository.jpa.BatchOrderRequestHandlerJPADao;
import com.yuecheng.hops.batch.repository.order.BatchOrderRequestHandlerDao;
import com.yuecheng.hops.batch.service.order.BatchOrderRequestHandlerManagement;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;


/**
 * @ClassName: BatchOrderRequestHandlerManagementImpl
 * @Description: TODO
 * @author 肖进
 * @date 2014年8月14日 上午10:53:12
 */
@Service("batchOrderRequestHandlerManagement")
public class BatchOrderRequestHandlerManagementImpl implements BatchOrderRequestHandlerManagement
{
    public static Logger logger = LoggerFactory.getLogger(BatchOrderRequestHandlerManagementImpl.class);

    @Autowired
    private BatchOrderRequestHandlerDao batchOrderRequestHandlerDao;

    @Autowired
    private BatchOrderRequestHandlerJPADao batchOrderRequestHandlerJPADao;

    @Override
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandlerByUpFile(String upFile)
    {
        List<BatchOrderRequestHandler> borhList = batchOrderRequestHandlerDao.queryBatchOrderRequestHandlerByUpFile(upFile);
        logger.debug("通过上传文件名查手动补单记录合计：" + (BeanUtils.isNotNull(borhList) ? borhList.size() : 0)
                     + " 条~~~~！");
        return borhList;

    }

    @Override
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandlerByOrderStatus(Long orderStatus)
    {
        List<BatchOrderRequestHandler> borhList = batchOrderRequestHandlerDao.queryBatchOrderRequestHandlerByOrderStatus(orderStatus);
        logger.debug("通过状态查手动补单记录合计：" + (BeanUtils.isNotNull(borhList) ? borhList.size() : 0)
                     + " 条~~~~！");
        return borhList;

    }

    @Override
    public List<BatchOrderRequestHandler> queryBatchOrderRequestHandler(String upFile,
        Long orderStatus)
    {
        List<BatchOrderRequestHandler> borhList = batchOrderRequestHandlerDao.queryBatchOrderRequestHandlerByUpFileAndOrderStatus(
            upFile, orderStatus);
        logger.debug("通过上传文件名和状态查手动补单记录合计："
                     + (BeanUtils.isNotNull(borhList) ? borhList.size() : 0) + " 条~~~~！");
        return borhList;
    }

    @Override
    public BatchOrderRequestHandler save(BatchOrderRequestHandler batchOrderRequestHandler)
    {
        return batchOrderRequestHandlerDao.save(batchOrderRequestHandler);
    }

    @Override
    public List<String> getUpfileAll()
    {
        List<String> upfileAlllist = batchOrderRequestHandlerJPADao.getUpfileAll();
        logger.debug("得到全部上传文件名列表合计："
                     + (BeanUtils.isNotNull(upfileAlllist) ? upfileAlllist.size() : 0) + " 条~~~~！");
        return upfileAlllist;
    }

    @Override
    public List<String> getOrderStatusAll()
    {
        List<String> orderStatusAlllist = batchOrderRequestHandlerJPADao.getOrderStatusAll();
        logger.debug("得到得到全部状态列表合计："
                     + (BeanUtils.isNotNull(orderStatusAlllist) ? orderStatusAlllist.size() : 0)
                     + " 条~~~~！");
        return orderStatusAlllist;
    }

    @Override
    public int update(Long orderStatus, Long id)
    {
        return batchOrderRequestHandlerDao.updateOrderStatusById(orderStatus, id);
    }

    @Override
    public YcPage<BatchOrderRequestHandler> queryBatchOrderRequestHandler(Map<String, Object> searchParams,
                                                                          int pageNumber,
                                                                          int pageSize, BSort bsort)
    {
        logger.debug("[BatchOrderRequestHandlerManagementImpl:queryBatchOrderRequestHandler("
                     + searchParams != null ? searchParams.toString() : null + ")]");
        // 查询条件
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

        // 按创建时间排序规则
        String orderCloumn = bsort == null ? EntityConstant.BatchOrderRequestHandler.ORDER_REQUEST_TIME : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);

        // 接口的Dao对象,查询条件,当前页码,每页条数,排序规则
        Page<BatchOrderRequestHandler> page = PageUtil.queryPage(batchOrderRequestHandlerDao,
            filters, pageNumber, pageSize, sort, BatchOrderRequestHandler.class);
        List<BatchOrderRequestHandler> batchOrderRequestHandlerList = page.getContent();
        YcPage<BatchOrderRequestHandler> ycPage = new YcPage<BatchOrderRequestHandler>();
        ycPage.setList(batchOrderRequestHandlerList);
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setCountTotal((int)page.getTotalElements());
        return ycPage;
    }

    @Override
    public YcPage<BatchOrderRequestHandler> queryBatchOrderRequestHandlerBySQL(int pageNumber,
                                                                               int pageSize,
                                                                               String upfile,
                                                                               Long orderStatus)
    {
        // 接口的Dao对象,查询条件,当前页码,每页条数,排序规则
        return batchOrderRequestHandlerJPADao.queryBatchOrderRequestHandlerBySQL(pageNumber,
            pageSize, upfile, orderStatus);
    }

    @Override
    public int updateByUpFile(Long orderStatus, Date orderDeleteTime, String upFile)
    {
        return batchOrderRequestHandlerDao.updateByUpFile(orderStatus, orderDeleteTime, upFile);
    }

    @Override
    public int updateAuditByUpFile(Long orderStatus, String upFile, Long number)
    {
        return batchOrderRequestHandlerDao.updateAuditByUpFile(orderStatus, upFile, number);
    }

}
