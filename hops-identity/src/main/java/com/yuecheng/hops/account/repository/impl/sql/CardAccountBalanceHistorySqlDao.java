package com.yuecheng.hops.account.repository.impl.sql;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CardAccountBalanceHistory;
import com.yuecheng.hops.account.repository.CardAccountBalanceHistoryDao;
import com.yuecheng.hops.account.repository.CardAccountBalanceHistoryJpaDao;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;


/**
 * 卡账户交易记录
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see CardAccountBalanceHistorySqlDao
 * @since
 */
@Service("cardAccountBalanceHistoryDao")
public class CardAccountBalanceHistorySqlDao implements CardAccountBalanceHistoryDao
{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CardAccountBalanceHistoryJpaDao cardAccountBalanceHistoryJpaDao;
    
    private static Logger logger = LoggerFactory.getLogger(CardAccountBalanceHistorySqlDao.class);

    @SuppressWarnings("unchecked")
	@Override
    public YcPage<CardAccountBalanceHistory> queryCardAccountBalanceHistory(Map<String, Object> searchParams,
                                                                            int pageNumber,
                                                                            int pageSize,
                                                                            BSort bsort)
    {
        try
        {
	        int startIndex = pageNumber * pageSize - pageSize;
	        int endIndex = startIndex + pageSize;
	        String insidesql = "select  c.*,rownum rn from card_account_balance_history c  where 1=1";
	        if (endIndex > 0)
	        {
	            insidesql = insidesql + " and rownum <= " + endIndex;
	        }
	
	        String condition = StringUtil.initString();
	        if (searchParams.get(EntityConstant.Account.ACCOUNT_TYPE) != null
	            && !searchParams.get(EntityConstant.Account.ACCOUNT_TYPE).toString().isEmpty())
	        {
	            condition = " and exists (select ca.account_id from ccy_account ca where "
	                        + " ca.account_type_id="
	                        + searchParams.get(EntityConstant.Account.ACCOUNT_TYPE)
	                        + " and ca.account_id=c.account_id)";
	        }
	
	        if (searchParams.get(EntityConstant.TransactionHistory.TRANSACTION_ID) != null
	            && !searchParams.get(EntityConstant.TransactionHistory.TRANSACTION_ID).toString().isEmpty())
	        {
	            condition = condition + " and c.transaction_id='"
	                        + searchParams.get(EntityConstant.TransactionHistory.TRANSACTION_ID)
	                        + "' ";
	        }
	
	        if (searchParams.get(EntityConstant.Account.ACCOUNT_ID) != null
	            && !searchParams.get(EntityConstant.Account.ACCOUNT_ID).toString().isEmpty())
	        {
	            condition = condition + " and c.account_id='"
	                        + searchParams.get(EntityConstant.Account.ACCOUNT_ID) + "' ";
	        }
	
	        String pageTotal_sql = "select * from card_account_balance_history c where 1=1"
	                               + condition;
	        Query query = em.createNativeQuery(pageTotal_sql);
	        List<CardAccountBalanceHistory> pageTotal_list = query.getResultList();
	        Double pageTotal = BigDecimalUtil.divide(new BigDecimal(pageTotal_list.size()), new BigDecimal(pageSize),
	            DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
	
	        insidesql = insidesql + condition;
	
	        String sql = "select * from (" + insidesql + ") where rn>" + startIndex;
	        query = em.createNativeQuery(sql, CardAccountBalanceHistory.class);
	        List<CardAccountBalanceHistory> cabList = query.getResultList();
	
	        YcPage<CardAccountBalanceHistory> ycPage = new YcPage<CardAccountBalanceHistory>();
	        ycPage.setList(cabList);
	        ycPage.setCountTotal((int)pageTotal_list.size());
	        ycPage.setPageTotal(pageTotal.intValue());
	        return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryCardAccountBalanceHistory exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

}
