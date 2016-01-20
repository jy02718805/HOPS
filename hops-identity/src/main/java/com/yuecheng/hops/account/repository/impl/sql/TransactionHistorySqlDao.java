package com.yuecheng.hops.account.repository.impl.sql;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryAssistVo;
import com.yuecheng.hops.account.repository.OracleSql;
import com.yuecheng.hops.account.repository.TransactionHistoryDao;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;


@Service
public class TransactionHistorySqlDao implements TransactionHistoryDao
{
    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Logger logger = LoggerFactory.getLogger(TransactionHistorySqlDao.class);

    @Override
    public YcPage<TransactionHistoryAssistVo> queryTransactionHistoryList(Map<String, Object> searchParams,
                                                                          int pageNumber,
                                                                          int pageSize)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String pageTotal_sql = OracleSql.TransactionHistory.queryTransactionHistoryList_Sql_Count;
            Query query = em.createNativeQuery(pageTotal_sql);
            setParameter(searchParams, query, 0, 0);
            BigDecimal total = (BigDecimal)query.getSingleResult();

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from ("
                         + OracleSql.TransactionHistory.queryTransactionHistoryList_Sql
                         + ") where rn>:startIndex";
            query = em.createNativeQuery(sql);
            setParameter(searchParams, query, startIndex, endIndex);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<TransactionHistoryAssistVo> thavList = new ArrayList<TransactionHistoryAssistVo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;
                TransactionHistoryAssistVo transactionHistoryAssistVo = new TransactionHistoryAssistVo();
                BeanUtils.transMap2Bean(row, transactionHistoryAssistVo);
                thavList.add(transactionHistoryAssistVo);
            }
            YcPage<TransactionHistoryAssistVo> ycPage = new YcPage<TransactionHistoryAssistVo>();
            ycPage.setList(thavList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryTransactionHistoryList exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    private void setParameter(Map<String, Object> searchParams, Query query, int startIndex,
                              int endIndex)
    {
        query.setParameter(
            "beginDate",
            null == searchParams.get(EntityConstant.TransactionHistory.BEGIN_DATE) ? "" : searchParams.get(
                EntityConstant.TransactionHistory.BEGIN_DATE).toString());

        query.setParameter(
            "endDate",
            null == searchParams.get(EntityConstant.TransactionHistory.END_DATE) ? "" : searchParams.get(
                EntityConstant.TransactionHistory.END_DATE).toString());

        query.setParameter(
            "transactionNo",
            null == searchParams.get(EntityConstant.TransactionHistory.TRANSACTION_NO) ? "" : searchParams.get(EntityConstant.TransactionHistory.TRANSACTION_NO));

        query.setParameter(
            "transactionId",
            null == searchParams.get(EntityConstant.TransactionHistory.TRANSACTION_ID) ? "" : searchParams.get(EntityConstant.TransactionHistory.TRANSACTION_ID));

        query.setParameter(
            "payerIdentityName",
            null == searchParams.get(EntityConstant.TransactionHistory.PAYER_IDENTITY_NAME) ? "" : searchParams.get(EntityConstant.TransactionHistory.PAYER_IDENTITY_NAME));

        query.setParameter(
            "payeeIdentityName",
            null == searchParams.get(EntityConstant.TransactionHistory.PAYEE_IDENTITY_NAME) ? "" : searchParams.get(EntityConstant.TransactionHistory.PAYEE_IDENTITY_NAME));

        query.setParameter(
            "payerAccountTypeId",
            null == searchParams.get(EntityConstant.TransactionHistory.PAYER_ACCOUNT_TYPE) ? "" : searchParams.get(EntityConstant.TransactionHistory.PAYER_ACCOUNT_TYPE));

        query.setParameter(
            "payeeAccountTypeId",
            null == searchParams.get(EntityConstant.TransactionHistory.PAYEE_ACCOUNT_TYPE) ? "" : searchParams.get(EntityConstant.TransactionHistory.PAYEE_ACCOUNT_TYPE));

        query.setParameter(
            "type",
            null == searchParams.get(EntityConstant.TransactionHistory.TYPE) ? "" : searchParams.get(EntityConstant.TransactionHistory.TYPE));

        if (endIndex > 0)
        {
            query.setParameter("rownum", endIndex);
            query.setParameter("startIndex", startIndex);
        }
    }

    public BigDecimal getTransactionHistoryOfAmtSum(Long accoutId, String logType, Date beginTime,
                                                    Date endTime)
    {
        try
        {
            SimpleDateFormat fomat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String beginDate = fomat.format(beginTime);
            String endDate = fomat.format(endTime);
            Query query = em.createNativeQuery(OracleSql.TransactionHistory.getTransactionHistoryOfAmtSum_Sql);
            query.setParameter("accoutId", accoutId);
            query.setParameter("type", logType);
            query.setParameter("beginDate", beginDate);
            query.setParameter("endDate", endDate);
            BigDecimal sumAmtStr = (BigDecimal)query.getSingleResult();
            return sumAmtStr;
        }
        catch (Exception e)
        {
            logger.error("getTransactionHistoryOfAmtSum exception error["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<TransactionHistory> queryNoRefundTransactionHistoryByTransactionNo(String transactionNo)
    {
        try
        {
            Query query = em.createNativeQuery(
                "select * from transaction_history where is_refund=:isRefund and transaction_no=:transactionNo order by create_date desc",
                TransactionHistory.class);
            query.setParameter("isRefund", Constant.RefundConfiguration.NO_REFUND);
            query.setParameter("transactionNo", transactionNo);
            List<TransactionHistory> transactionHistorys = query.getResultList();
            return transactionHistorys;
        }
        catch (Exception e)
        {
            logger.error("queryNoRefundTransactionHistoryByTransactionNo exception error["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<TransactionHistory> queryTransactionHistoryByParams(String transactionNo,
                                                                    String type)
    {
        try
        {
            Query query = em.createNativeQuery(
                "select * from transaction_history where is_refund=:isRefund and transaction_no=:transactionNo and type = :type order by transaction_id desc",
                TransactionHistory.class);
            query.setParameter("isRefund", Constant.RefundConfiguration.NO_REFUND);
            query.setParameter("transactionNo", transactionNo);
            query.setParameter("type", type);
            List<TransactionHistory> transactionHistorys = query.getResultList();
            return transactionHistorys;
        }
        catch (Exception e)
        {
            logger.error("queryNoRefundTransactionHistoryByTransactionNo exception error["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<TransactionHistory> queryTransactionHistoryByTransactionNo(String transactionNo)
    {
        try
        {
            Query query = em.createNativeQuery(
                "select * from transaction_history where 1=1 and transaction_no=:transactionNo order by CREATE_DATE asc,transaction_id asc",
                TransactionHistory.class);
            query.setParameter("transactionNo", transactionNo);

            @SuppressWarnings("unchecked")
            List<TransactionHistory> transactionHistorys = query.getResultList();
            return transactionHistorys;
        }
        catch (Exception e)
        {
            logger.error("queryTransactionHistoryByTransactionNo exception error["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

}
