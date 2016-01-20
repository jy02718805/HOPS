/**
 * @Title: BatchOrderRequestHandler.java
 * @Package com.yuecheng.hops.transaction.repository
 * @Description: 批量手工补单数据库操作 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午9:58:13
 * @version V1.0
 */

package com.yuecheng.hops.batch.repository.sql.impl;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.batch.repository.sql.BatchJobDetailSqlDao;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;
import com.yuecheng.hops.transaction.basic.repository.OracleSql;


/**
 */
@Service
public class BatchJobDetailSqlDaoImpl implements BatchJobDetailSqlDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobDetailSqlDaoImpl.class);

    public YcPage<BatchJobDetail> queryPageBatchJobDetail(Map<String, Object> searchParams,
                                                          int pageNumber, int pageSize)
    {
        try
        {
            String contion = "";

            if (BeanUtils.isNotNull(searchParams.get(EntityConstant.BatchJobDetail.ORDER_NO))
                && StringUtil.isNotBlank(searchParams.get(EntityConstant.BatchJobDetail.ORDER_NO)
                                         + ""))
            {
                contion += " and b.order_no= '"
                           + searchParams.get(EntityConstant.BatchJobDetail.ORDER_NO) + "'";
            }
            
            if (BeanUtils.isNotNull(searchParams.get(EntityConstant.BatchJobDetail.BATCH_ID))
                && StringUtil.isNotBlank(searchParams.get(EntityConstant.BatchJobDetail.BATCH_ID)
                                         + ""))
            {
                contion += " and b.batch_id= '"
                           + searchParams.get(EntityConstant.BatchJobDetail.BATCH_ID) + "'";
            }
            
            
            if (BeanUtils.isNotNull(searchParams.get(EntityConstant.BatchJobDetail.SERIAL_NUM))
                && StringUtil.isNotBlank(searchParams.get(EntityConstant.BatchJobDetail.SERIAL_NUM)
                                         + ""))
            {
                contion += " and b.serial_num= '"
                           + searchParams.get(EntityConstant.BatchJobDetail.SERIAL_NUM) + "'";
            }
            
            
            if (BeanUtils.isNotNull(searchParams.get(EntityConstant.BatchJobDetail.STATUS))
                && StringUtil.isNotBlank(searchParams.get(EntityConstant.BatchJobDetail.STATUS)
                                         + ""))
            {
                contion += " and b.status= '"
                           + searchParams.get(EntityConstant.BatchJobDetail.STATUS) + "'";
            }
            

            String sqlContion = "select bj.*,rownum rn from (select * from batch_job_detail b where 1=1 "
                                + contion
                                + " order by b.batch_id desc,b.serial_num asc)bj ";
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;
            String pageTotal_sql = "select count(*) from (" + sqlContion + ")";
            Query query = em.createNativeQuery(pageTotal_sql);
            // query = OracleSql.setParameter(searchParams, query, 0, 0);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
            String sql = "select * from (" +sqlContion+ "  where 1 = 1 and rownum <= " + endIndex + ") "
                         + "where rn > " + startIndex;
            query = em.createNativeQuery(sql, BatchJobDetail.class);
            YcPage<BatchJobDetail> ycPage = new YcPage<BatchJobDetail>();
            List<BatchJobDetail> result =query.getResultList();
            ycPage.setList(result);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            LOGGER.error("queryPageBatchJobDetail exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }
}
