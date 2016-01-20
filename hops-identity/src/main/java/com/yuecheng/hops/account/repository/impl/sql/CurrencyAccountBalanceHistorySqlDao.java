package com.yuecheng.hops.account.repository.impl.sql;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CurrencyAccountBalanceHistory;
import com.yuecheng.hops.account.entity.bo.AccountBalanceHistoryBo;
import com.yuecheng.hops.account.entity.vo.AccountHistoryAssistVo;
import com.yuecheng.hops.account.repository.CurrencyAccountBalanceHistoryDao;
import com.yuecheng.hops.account.repository.OracleSql;
import com.yuecheng.hops.account.repository.OracleSql.CurrencyAccountBianceHistory;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;


@Service
public class CurrencyAccountBalanceHistorySqlDao implements CurrencyAccountBalanceHistoryDao
{
    @PersistenceContext
    private EntityManager em;

    private static Logger logger = LoggerFactory.getLogger(CurrencyAccountBalanceHistorySqlDao.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("#{configProperties['transaction_history.business']}")
    private String business;

    @Value("#{configProperties['transaction_history.transaction']}")
    private String transaction;

    @Override
    public YcPage<AccountHistoryAssistVo> queryCurrencyAccountBalanceHistory(Map<String, Object> searchParams,
                                                                             int pageNumber,
                                                                             int pageSize,
                                                                             BSort bsort)
    {

        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String sqlCount = "select count(*) from "
                              + OracleSql.CurrencyAccountBianceHistory.queryCurrencyAccountBalanceHistory_Sql;
            Query query = em.createNativeQuery(sqlCount);
            setParameter(searchParams, query, 0, 0);
            BigDecimal total = (BigDecimal)query.getSingleResult();
            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            String sql = "select * from ("
                         + OracleSql.CurrencyAccountBianceHistory.queryCurrencyAccountBalanceHistory_Insidesql
                         + OracleSql.CurrencyAccountBianceHistory.queryCurrencyAccountBalanceHistory_Sql
                         + ") where rn>:startIndex";
            query = em.createNativeQuery(sql);
            setParameter(searchParams, query, startIndex, endIndex);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<AccountHistoryAssistVo> thavList = new ArrayList<AccountHistoryAssistVo>();
            List<?> rows = query.getResultList();
            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;
                AccountHistoryAssistVo accountHistoryAssistVo = new AccountHistoryAssistVo();
                BeanUtils.transMap2Bean(row, accountHistoryAssistVo);
                thavList.add(accountHistoryAssistVo);
            }

            YcPage<AccountHistoryAssistVo> ycPage = new YcPage<AccountHistoryAssistVo>();
            ycPage.setList(thavList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryCurrencyAccountBalanceHistory exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    private void setParameter(Map<String, Object> searchParams, Query query, int startIndex,
                              int endIndex)
    {
        query.setParameter(
            "accountId",
            null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.ACCOUNT_ID) ? "" : searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.ACCOUNT_ID));
        // try
        // {
        query.setParameter(
            "beginDate",
            null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.BEGIN_DATE) ? "" : searchParams.get(
                EntityConstant.CurrencyAccountBalanceHistory.BEGIN_DATE).toString());
        query.setParameter(
            "endDate",
            null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.END_DATE) ? "" : searchParams.get(
                EntityConstant.CurrencyAccountBalanceHistory.END_DATE).toString());

        // }
        // catch (ParseException e)
        // {
        // // TODO Auto-generated catch block
        // throw new ApplicationContextException(
        // "[账户查询设置参数异常:]" +
        // "[CurrencyAccountBalanceHistorySqlDao: queryCurrencyAccountBalanceHistory[setParameter]()]"
        // + ExceptionUtil.getStackTraceAsString(e), e);
        // }

        // query.setParameter(
        // "accountTypeId",
        // null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.ACCOUNT_TYPE) ? ""
        // : searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.ACCOUNT_TYPE));

        query.setParameter(
            "transactionId",
            null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_ID) ? "" : searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_ID));

        query.setParameter(
            "transactionNo",
            null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_NO) ? "" : searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.TRANSACTION_NO));

        // query.setParameter(
        // "identityName",
        // null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.IDENTITY_NAME) ?
        // "" : searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.IDENTITY_NAME));

        query.setParameter(
            "type",
            null == searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.TYPE) ? "" : searchParams.get(EntityConstant.CurrencyAccountBalanceHistory.TYPE));

        if (endIndex > 0)
        {
            query.setParameter("rownum", endIndex);

            query.setParameter("startIndex", startIndex);
        }
        else
        {
            query.setParameter("rownum", "");
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<AccountHistoryAssistVo> queryAccountFundsChange(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize)
    {
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;

            String insetSql = "";
            Query query = null;
            List<?> rows;
            BigDecimal total = new BigDecimal("0");
            List<AccountHistoryAssistVo> thavList = new ArrayList<AccountHistoryAssistVo>();
            if (Constant.AccountFundChange.LOGTYPE_TRANSACTION.equals(searchParams.get(
                EntityConstant.AccountFundChange.LOGTYPE).toString()))
            {
                String pageTotal_sql = "select count(*) from ("
                                       + OracleSql.CurrencyAccountBianceHistory.QUERYACCOUNTTRANACTION_SQL_COUNT
                                       + ")";

                query = em.createNativeQuery(pageTotal_sql);
                setAccountFundsChangeOfParameter(searchParams, query,
                    Constant.AccountFundChange.LOGTYPE_TRANSACTION);

                total = (BigDecimal)query.getSingleResult();

                // String sqlend = "select * from ("
                // + OracleSql.CurrencyAccountBianceHistory.QUERYACCOUNTTRANACTION_SQL_BEGIN;
                //
                // sqlend +=
                // "(select o.order_no,o.user_code,o.merchant_order_no,o.product_face,o.product_id from "
                // + transaction
                // +
                // ".yc_order o where 1=1   and o.order_request_time >= to_date(:beginDate2, 'yyyy-mm-dd HH24:mi:ss')"
                // +
                // "  and o.order_request_time <=  to_date(:endDate, 'yyyy-mm-dd HH24:mi:ss')) o on th.transactionNo = o.order_no left join "
                // + business
                // + ".airtime_product p on o.product_id =  p.product_id "
                // + OracleSql.CurrencyAccountBianceHistory.QUERYACCOUNTTRANACTION_SQL_END
                // + ") where rn>:startIndex";
                String sqlend = this.fundsChangeOfSql(searchParams, startIndex, endIndex);
                query = em.createNativeQuery(sqlend);
                //
                // setAccountFundsChangeOfParameter(searchParams, query,
                // Constant.AccountFundChange.LOGTYPE_TRANSACTION);
                //
                // query.setParameter("beginDate2",
                // getSubTime(searchParams.get(EntityConstant.AccountFundChange.BEGIN_DATE) + ""));
                // query.setParameter("startIndex", startIndex);
                // query.setParameter("rownum", endIndex);
                query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                rows = query.getResultList();
            }
            else
            {
                searchParams.remove(EntityConstant.AccountFundChange.LOGTYPE);
                insetSql = OracleSql.CurrencyAccountBianceHistory.QUERYACCOUNTTRANACTION_SQL_COUNT;

                String pageTotal_sql = "select count(*) from (" + insetSql + ")";
                query = em.createNativeQuery(pageTotal_sql);
                setAccountFundsChangeOfParameter(searchParams, query,
                    Constant.AccountFundChange.LOGTYPE_ACCOUNT);
                total = (BigDecimal)query.getSingleResult();

                insetSql = "select * from ("
                           + OracleSql.CurrencyAccountBianceHistory.QUERYACCOUNTFUNDSCHANGE_SQL
                           + ") where rn>:startIndex";
                query = em.createNativeQuery(insetSql);
                setAccountFundsChangeOfParameter(searchParams, query,
                    Constant.AccountFundChange.LOGTYPE_ACCOUNT);

                query.setParameter("startIndex", startIndex);
                query.setParameter("rownum", endIndex);
                query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                rows = query.getResultList();
            }
            // 去掉日志类型，map其它用于sql绑定变量

            Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

            for (Object obj : rows)
            {
                Map<String, Object> row = (Map<String, Object>)obj;
                AccountHistoryAssistVo accountHistoryAssistVo = new AccountHistoryAssistVo();
                BeanUtils.transMap2Bean(row, accountHistoryAssistVo);
                thavList.add(accountHistoryAssistVo);
            }

            YcPage<AccountHistoryAssistVo> ycPage = new YcPage<AccountHistoryAssistVo>();
            ycPage.setList(thavList);
            ycPage.setCountTotal(total.intValue());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryAccountFundsChange exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    private void setAccountFundsChangeOfParameter(Map<String, Object> searchParams, Query query,
                                                  String logtypeTransaction)
    {
        query.setParameter(EntityConstant.AccountFundChange.BEGIN_DATE,
            searchParams.get(EntityConstant.AccountFundChange.BEGIN_DATE));
        query.setParameter(EntityConstant.AccountFundChange.END_DATE,
            searchParams.get(EntityConstant.AccountFundChange.END_DATE));
        query.setParameter(
            EntityConstant.AccountFundChange.TRANSACTION_TYPE,
            null == searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_TYPE) ? "" : searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_TYPE));
        query.setParameter(
            EntityConstant.AccountFundChange.TRANSACTION_NO,
            null == searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_NO) ? "" : searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_NO));
        query.setParameter(EntityConstant.Account.ACCOUNT_ID,
            searchParams.get(EntityConstant.Account.ACCOUNT_ID));
        query.setParameter("endDate2",
            getAddTime(searchParams.get(EntityConstant.AccountFundChange.END_DATE) + ""));

        if (Constant.AccountFundChange.LOGTYPE_TRANSACTION.equals(logtypeTransaction))
        {
            query.setParameter("type1", Constant.TransferType.TRANSFER_AGENT_ORDERED);
            query.setParameter("type2", Constant.TransferType.TRANSFER_UN_AGENT_ORDERED);
        }
        else
        {
            query.setParameter("type1", Constant.TransferType.TRANSFER_SUB_CASH);
            query.setParameter("type2", Constant.TransferType.TRANSFER_ADD_CASH);
        }
    }

    private String fundsChangeOfSql(Map<String, Object> searchParams, Integer startIndex,
                                    Integer endIndex)
    {

        String beginTime = this.getSubTime(searchParams.get(EntityConstant.AccountFundChange.BEGIN_DATE)
                                           + "");
        String endTime = this.getAddTime(searchParams.get(EntityConstant.AccountFundChange.END_DATE)
                                         + "");
        String sql = "  select th.*,rownum rn from (select * "
                     + "  from (select c.account_id as accountId,                                          "
                     + "               t.type,             "
                     + "               c.new_available_balance as newAvailableBalance,                                                   "
                     + "               t.amt as CHANGEAMOUNT,                                              "
                     + "               to_char(t.create_date, 'YYYY/mm/dd HH24:mi:ss') as createDate,      "
                     + "               o.order_no as TRANSACTIONNO,                                        "
                     + "               p.product_name as productNo,                                        "
                     + "               o.merchant_order_no as merchantOrderNo,                             "
                     + "               o.user_code as userCode,                                            "
                     + "               o.product_face as productFace                                       "
                     + "                                                                                   "
                     + "          from (select c.account_id,                                               "
                     + "                       c.new_available_balance,                                    "
                     + "                       c.transaction_id                                            "
                     + "                  from ccy_account_balance_history_0 c       "
                     + "                 where c.account_id ="
                     + searchParams.get(EntityConstant.Account.ACCOUNT_ID)
                     + "                   and c.create_date >=                                            "
                     + "                       to_date('"
                     + beginTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')     "
                     + "                   and c.create_date <=                                            "
                     + "                       to_date('"
                     + endTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')     "
                     + "                union all                                                          "
                     + "                select c1.account_id,                                              "
                     + "                       c1.new_available_balance,                                   "
                     + "                       c1.transaction_id                                           "
                     + "                  from ccy_account_balance_history_1 c1                           "
                     + "                 where c1.account_id ="
                     + searchParams.get(EntityConstant.Account.ACCOUNT_ID)
                     + "                   and c1.create_date >=                                           "
                     + "                       to_date('"
                     + beginTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')     "
                     + "                   and c1.create_date <=                                           "
                     + "                       to_date('"
                     + endTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')     "
                     + "                union all                                                          "
                     + "                select c2.account_id,                                              "
                     + "                       c2.new_available_balance,                                   "
                     + "                       c2.transaction_id                                           "
                     + "                  from ccy_account_balance_history_2 c2                           "
                     + "                 where c2.account_id ="
                     + searchParams.get(EntityConstant.Account.ACCOUNT_ID)
                     + "                   and c2.create_date >=                                           "
                     + "                       to_date('"
                     + beginTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')     "
                     + "                   and c2.create_date <=                                           "
                     + "                       to_date('"
                     + endTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')     "
                     + "                                                                                   "
                     + "                union all                                                          "
                     + "                select c3.account_id,                                              "
                     + "                       c3.new_available_balance,                                   "
                     + "                       c3.transaction_id                                           "
                     + "                  from ccy_account_balance_history_3 c3                           "
                     + "                 where c3.account_id = "
                     + searchParams.get(EntityConstant.Account.ACCOUNT_ID)
                     + "                   and c3.create_date >=                                           "
                     + "                       to_date('"
                     + beginTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')     "
                     + "                   and c3.create_date <=                                           "
                     + "                       to_date('"
                     + endTime
                     + "', 'yyyy-mm-dd HH24:mi:ss')) c, "
                     + "               transaction_history t,                                              "
                     + transaction
                     + ".yc_order o,                                         "
                     + business
                     + ".airtime_product p                                     "
                     + "                                                                                   "
                     + "         where 1 = 1                                                               "
                     + " and c.transaction_id = t.transaction_id        "
                     + "           and (t.payer_account_id = "
                     + searchParams.get(EntityConstant.Account.ACCOUNT_ID)
                     + " or t.payee_account_id = "
                     + searchParams.get(EntityConstant.Account.ACCOUNT_ID)
                     + ")                                 ";
        if (BeanUtils.isNotNull(searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_TYPE)))
        {
            sql += " and t.type='"
                   + searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_TYPE) + "' ";
        }
        else
        {
            sql += "           and (t.type = 1 or t.type = 10)                                 ";
        }

        sql += "           and p.product_id = o.product_id(+)                                         ";
        if (BeanUtils.isNotNull(searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_NO)))
        {
            sql += " and t.transaction_no='"
                   + searchParams.get(EntityConstant.AccountFundChange.TRANSACTION_NO) + "' ";
        }
        sql += /*
                * "   and o.order_request_time >=            " + " to_date('" + beginTime +
                * "', 'yyyy-mm-dd HH24:mi:ss')     " + " and o.order_request_time <=  " +
                * " to_date('" + endTime + "', 'yyyy-mm-dd HH24:mi:ss')             "
                */
            "     and t.transaction_no=o.order_no    "
            + "   and t.create_date >=                 " + "               to_date('"
            + searchParams.get(EntityConstant.AccountFundChange.BEGIN_DATE)
            + "', 'yyyy-mm-dd HH24:mi:ss')             "
            + "           and t.create_date <=         " + "               to_date('"
            + searchParams.get(EntityConstant.AccountFundChange.END_DATE)
            + "', 'yyyy-mm-dd HH24:mi:ss')             "
            + "         order by t.create_date desc,t.transaction_id desc)   ";

        if (BeanUtils.isNotNull(endIndex))
        {
            sql += " where rownum<=" + endIndex;
        }
        sql += ")th";

        if (BeanUtils.isNotNull(startIndex))
        {
            sql = "select * from (" + sql + ") where rn>" + startIndex;
        }

        return sql;
    }

    @Override
    public List<BigDecimal> queryCurrencyAccountId(Date beginTime, Date endTime,
                                                   String accountTypeId)
    {
        try
        {
            SimpleDateFormat fomat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String beginDate = fomat.format(beginTime);
            String endDate = fomat.format(endTime);

            Query query = em.createNativeQuery(OracleSql.CurrencyAccountBianceHistory.queryCurrencyAccountId_Sql);
            query.setParameter("beginDate", beginDate);
            query.setParameter("endDate", endDate);
            query.setParameter("accountTypeId", accountTypeId);
            @SuppressWarnings("unchecked")
            List<BigDecimal> accountIds = query.getResultList();
            return accountIds;
        }
        catch (Exception e)
        {
            logger.error("queryCurrencyAccountId exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public AccountBalanceHistoryBo getAccountReportBo(Long accountId, Date beginDate, Date endDate)
    {
        try
        {
            SimpleDateFormat fomat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Query query = em.createNativeQuery(OracleSql.CurrencyAccountBianceHistory.getAccountReportBo_Sql);

            query.setParameter("accountId", accountId);
            query.setParameter("beginDate", fomat.format(beginDate));
            query.setParameter("endDate", fomat.format(endDate));
            query.setParameter("addType",
                Constant.AccountBalanceOperationType.ACT_BAL_OPR_ADD_CASH);
            query.setParameter("subType",
                Constant.AccountBalanceOperationType.ACT_BAL_OPR_SUB_CASH);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<?> rows = query.getResultList();
            Map<String, Object> row = new HashedMap();
            for (Object obj : rows)
            {
                row = (Map<String, Object>)obj;
            }

            AccountBalanceHistoryBo accountBalanceHistoryBo = new AccountBalanceHistoryBo();
            BeanUtils.transMap2Bean(row, accountBalanceHistoryBo);
            return accountBalanceHistoryBo;
        }
        catch (Exception e)
        {
            logger.error("getAccountReportBo exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public void saveHistory(Long transactionId, Long accountId, BigDecimal newAvailableBalance,
                            BigDecimal newUnavailableBanlance, BigDecimal newCreditableBanlance,
                            Date createDate, String historyType, String descStr,
                            String identityName)
    {
        try
        {
            Query query = em.createNativeQuery("{call save_ccyAcBalance_History(?,?,?,?,?,?,?,?,?)}");
            query.setParameter(1, transactionId);
            query.setParameter(2, accountId);
            query.setParameter(3, newAvailableBalance);
            query.setParameter(4, newUnavailableBanlance);
            query.setParameter(5, newCreditableBanlance);
            query.setParameter(6, createDate);
            query.setParameter(7, historyType);
            query.setParameter(8, descStr);
            query.setParameter(9, identityName);
            query.executeUpdate();
        }
        catch (Exception e)
        {
            logger.error("saveHistory exception info[" + ExceptionUtil.getStackTraceAsString(e)
                         + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<CurrencyAccountBalanceHistory> getCurrencyAccountBalanceHistoryByParams(String transactionId,
                                                                                        String descStr,
                                                                                        String type)
    {
        try
        {
            String sql = "select * from ("
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_0 where 1=1 and ((type=:type and :type is not null) or :type is null) and ((desc_str=:descStr and :descStr is not null) or :descStr is null) and ((transaction_id=:transactionId  and :transactionId is not null) or  :transactionId is null)"
                         + " union all"
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_1 where 1=1 and ((type=:type and :type is not null) or :type is null) and ((desc_str=:descStr and :descStr is not null) or :descStr is null) and ((transaction_id=:transactionId  and :transactionId is not null) or  :transactionId is null)"
                         + " union all"
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_2 where 1=1 and ((type=:type and :type is not null) or :type is null) and ((desc_str=:descStr and :descStr is not null) or :descStr is null) and ((transaction_id=:transactionId  and :transactionId is not null) or  :transactionId is null)"
                         + " union all"
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_3 where 1=1 and ((type=:type and :type is not null) or :type is null) and ((desc_str=:descStr and :descStr is not null) or :descStr is null) and ((transaction_id=:transactionId  and :transactionId is not null) or  :transactionId is null)"
                         + ") ";
            Query query = em.createNativeQuery(sql, CurrencyAccountBalanceHistory.class);
            query.setParameter("type", null == type ? "" : type);
            query.setParameter("descStr", null == descStr ? "" : descStr);
            query.setParameter("transactionId", null == transactionId ? "" : transactionId);
            List<CurrencyAccountBalanceHistory> cabList = query.getResultList();
            return cabList;
        }
        catch (Exception e)
        {
            logger.error("getCurrencyAccountBalanceHistoryByParams exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<CurrencyAccountBalanceHistory> getFrozenBalanceHistoryByOrderNo(Long orderNo,
                                                                                String type)
    {
        try
        {
            String sql = "select * from ("
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_0 where type=:type and transaction_id=:orderNo "
                         + " union all "
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_1 where type=:type and transaction_id=:orderNo "
                         + " union all "
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_2 where type=:type and transaction_id=:orderNo "
                         + " union all "
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_3 where type=:type and transaction_id=:orderNo "
                         + ") ";
            Query query = em.createNativeQuery(sql, CurrencyAccountBalanceHistory.class);
            query.setParameter("type", type);
            query.setParameter("orderNo", orderNo);
            List<CurrencyAccountBalanceHistory> cabList = query.getResultList();
            return cabList;
        }
        catch (Exception e)
        {
            logger.error("getCurrencyAccountBalanceHistoryByParams exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<CurrencyAccountBalanceHistory> getTransferBalanceHistoryByParams(String transferId)
    {
        try
        {
            String sql = "select * from ("
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_0 where transaction_id=:transferId"
                         + " union all "
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_1 where transaction_id=:transferId"
                         + " union all "
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_2 where transaction_id=:transferId"
                         + " union all "
                         + " select * from CCY_ACCOUNT_BALANCE_HISTORY_3 where transaction_id=:transferId"
                         + ") ";
            Query query = em.createNativeQuery(sql, CurrencyAccountBalanceHistory.class);
            query.setParameter("transferId", transferId);
            List<CurrencyAccountBalanceHistory> cabList = query.getResultList();
            return cabList;
        }
        catch (Exception e)
        {
            logger.error("getCurrencyAccountBalanceHistoryByParams exception info["
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    public List<AccountBalanceHistoryBo> queryAccountReportBos(Date beginTime, Date endTime)
    {
        // TODO Auto-generated method stub
        List<AccountBalanceHistoryBo> acountBos = new ArrayList<AccountBalanceHistoryBo>();
        try
        {
            SimpleDateFormat fomat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Query query = em.createNativeQuery(CurrencyAccountBianceHistory.queryAccountReportBos_Sql);
            query.setParameter("beginDate", fomat.format(beginTime));
            query.setParameter("endDate", fomat.format(endTime));
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<?> rows = query.getResultList();

            for (Object obj : rows)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> row = (Map<String, Object>)obj;
                AccountBalanceHistoryBo accountBalanceHistoryBo = new AccountBalanceHistoryBo();
                BeanUtils.transMap2Bean(row, accountBalanceHistoryBo);
                acountBos.add(accountBalanceHistoryBo);
            }

        }
        catch (Exception e)
        {
            logger.error("[CurrencyAccountBalanceHistorySqlDao.queryAccountReportBos()]exception info["
                         + e + "]");
            throw ExceptionUtil.throwException(e);
        }
        return acountBos;
    }

    public static void main(String[] args)
    {
        String sql = "select * from ("
                     + " select * from CCY_ACCOUNT_BALANCE_HISTORY_0 where transaction_id=:transferId"
                     + " union all "
                     + " select * from CCY_ACCOUNT_BALANCE_HISTORY_1 where transaction_id=:transferId"
                     + " union all "
                     + " select * from CCY_ACCOUNT_BALANCE_HISTORY_2 where transaction_id=:transferId"
                     + " union all "
                     + " select * from CCY_ACCOUNT_BALANCE_HISTORY_3 where transaction_id=:transferId"
                     + ") ";
        System.out.println(sql);
    }

    public String getAddTime(String time)
    {
        Calendar currentDate = new GregorianCalendar();
        try
        {
            Date date = format.parse(time);
            currentDate.setTime(date);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        currentDate.add(Calendar.HOUR_OF_DAY, 3);
        return format.format((Date)currentDate.getTime().clone());
    }

    public String getSubTime(String time)
    {
        Calendar currentDate = new GregorianCalendar();
        try
        {
            Date date = format.parse(time);
            currentDate.setTime(date);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        currentDate.add(Calendar.HOUR_OF_DAY, -3);
        return format.format((Date)currentDate.getTime().clone());
    }
}
