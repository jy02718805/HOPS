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

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.batch.repository.sql.BatchJobSqlDao;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;


@Service
public class BatchJobSqlDaoImpl implements BatchJobSqlDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobSqlDaoImpl.class);

    public YcPage<BatchJob> queryPageBatchJob(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize)
    {
        try
        {

            String contion = "";

            if (BeanUtils.isNotNull(searchParams.get(EntityConstant.BatchJob.FILENAME))
                && StringUtil.isNotBlank(searchParams.get(EntityConstant.BatchJob.FILENAME) + ""))
            {
                contion += " and b.file_name like '%"
                           + searchParams.get(EntityConstant.BatchJob.FILENAME) + "%'";
            }

            if (BeanUtils.isNotNull(searchParams.get(EntityConstant.BatchJob.STATUS))
                && StringUtil.isNotBlank(searchParams.get(EntityConstant.BatchJob.STATUS) + ""))
            {
                contion += " and b.status=" + searchParams.get(EntityConstant.BatchJob.STATUS);
            }

            String sqlContion = "select bj.*,rownum rn from (select * from batch_job b  where 1=1 "
                                + contion + "  order by b.created_time desc)bj ";
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String pageTotal_sql = "select count(*) from (" + sqlContion + ")";
            Query query = em.createNativeQuery(pageTotal_sql);
            // query = OracleSql.setParameter(searchParams, query, 0, 0);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from (" + sqlContion + " where 1 = 1 and rownum <= " + endIndex
                         + ")" + "where rn > " + startIndex + "                                ";
            query = em.createNativeQuery(sql, BatchJob.class);
            YcPage<BatchJob> ycPage = new YcPage<BatchJob>();
            List<BatchJob> result = query.getResultList();
            ycPage.setList(result);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            LOGGER.error("queryPageRebateRecord exception info[" + e + "]");
            throw ExceptionUtil.throwException(e);
        }
    }
}
