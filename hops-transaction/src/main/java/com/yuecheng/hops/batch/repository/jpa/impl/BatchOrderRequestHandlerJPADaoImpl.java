/**
 * @Title: BatchOrderRequestHandlerJPADaoImpl.java
 * @Package com.yuecheng.hops.transaction.repository.jpa.impl
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 下午1:47:44
 * @version V1.0
 */

package com.yuecheng.hops.batch.repository.jpa.impl;


import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.repository.jpa.BatchOrderRequestHandlerJPADao;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;


/**
 * @ClassName: BatchOrderRequestHandlerJPADaoImpl
 * @Description:
 * @author 肖进
 * @date 2014年8月14日 下午1:47:44
 */
@Service("batchOrderRequestHandlerJPADao")
public class BatchOrderRequestHandlerJPADaoImpl implements BatchOrderRequestHandlerJPADao
{
    private static Logger logger = LoggerFactory.getLogger(BatchOrderRequestHandlerJPADaoImpl.class);

    @PersistenceContext
    private EntityManager em;

    /*
     * <p>Title: getUpfileAll</p> <p>Description: 得到全部上传文件名列表</p>
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getUpfileAll()
    {
        
        try
        {
            String sql = "select distinct t.up_file from batch_order_request_handler t where t.order_status !=3 ";
            Query query = em.createNativeQuery(sql);
            List<String> upfileAllList = query.getResultList();
            if (upfileAllList.isEmpty())
            {
                if (logger.isInfoEnabled()) logger.info("没有得到全部上传文件名列表~~~!");
                return null;
            }
            else
            {
                if (logger.isInfoEnabled())
                    logger.info("得到全部上传文件名列表合计：" + upfileAllList.size() + " 条~~~！");
                return upfileAllList;
            }
        }
        catch (Exception e)
        {
            logger.error("getUpfileAll exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

    /*
     * <p>Title: getOrderStatusAll</p> <p>Description: 得到全部状态列表</p>
     * @return
     * @see
     * com.yuecheng.hops.transaction.repository.jpa.BatchOrderRequestHandlerJPADao#getOrderStatusAll
     * ()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getOrderStatusAll()
    {
        
        try
        {
            String sql = "select distinct t.order_status from batch_order_request_handler t where t.order_status !=3 ";
            Query query = em.createNativeQuery(sql);
            List<String> orderStatusAllList = query.getResultList();
            if (orderStatusAllList.isEmpty())
            {
                if (logger.isInfoEnabled()) logger.info("没有得到全部状态列表~~~!");
                return null;
            }
            else
            {
                if (logger.isInfoEnabled())
                    logger.info("得到得到全部状态列表合计：" + orderStatusAllList.size() + " 条~~~！");
                return orderStatusAllList;
            }
        }
        catch (Exception e)
        {
            logger.error("getOrderStatusAll exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

    /*
     * <p>Title: queryBatchOrderRequestHandlerBySQL</p> <p>Description: 通过条件查询分页</p>
     * @param searchParams 查询条件
     * @param pageNumber 当前页码
     * @param pageSize 每页条数
     * @param orderStatus 查询条件
     * @return
     * @see com.yuecheng.hops.batch.repository.jpa.BatchOrderRequestHandlerJPADao#
     * queryBatchOrderRequestHandlerBySQL(java.util.Map, int, int, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public YcPage<BatchOrderRequestHandler> queryBatchOrderRequestHandlerBySQL(int pageNumber,
                                                                               int pageSize,
                                                                               String upfile,
                                                                               Long orderStatus)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;
            String insidesql = "select o.*,rownum rn from batch_order_request_handler o where order_status != 3";
            if (endIndex > 0)
            {
                insidesql = insidesql + " and rownum <= " + endIndex;
            }
            String condition = StringUtil.initString();
            if (!StringUtil.isNullOrEmpty(upfile))
            {
                condition = condition + " and up_file = '" + upfile + "'";
            }
            if (orderStatus != 3l)
            {
                condition = condition + " and order_status = " + orderStatus;
            }
            else
            {
                condition = condition + " and order_status != 3";
            }

            String pageTotal_sql = "select * from batch_order_request_handler where 1=1" + condition;
            Query query = em.createNativeQuery(pageTotal_sql, BatchOrderRequestHandler.class);
            List<Order> pageTotal_list = query.getResultList();
            Double pageTotal = BigDecimalUtil.divide(new BigDecimal(pageTotal_list.size()), new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            insidesql = insidesql + condition;
            String sql = "select * from (" + insidesql + ") where rn>" + startIndex;
            query = em.createNativeQuery(sql, BatchOrderRequestHandler.class);
            List<BatchOrderRequestHandler> orderList = query.getResultList();
            YcPage<BatchOrderRequestHandler> ycPage = new YcPage<BatchOrderRequestHandler>();
            ycPage.setList(orderList);
            ycPage.setCountTotal((int)pageTotal_list.size());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryBatchOrderRequestHandlerBySQL exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

}
