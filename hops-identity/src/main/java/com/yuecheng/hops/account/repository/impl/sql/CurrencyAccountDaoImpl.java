/*
 * 文件名：CurrencyAccountDaoImpl.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月29日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.repository.impl.sql;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CurrencyAccount;
import com.yuecheng.hops.account.repository.CurrencyAccountDao;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;


@Service
public class CurrencyAccountDaoImpl implements CurrencyAccountDao
{

    @PersistenceContext
    private EntityManager em;
	
	@Autowired
	private AccountTypeService accountTypeService;

	private static Logger logger = LoggerFactory.getLogger(CurrencyAccountDaoImpl.class);

    @Override
    public YcPage<CurrencyAccount> queryCurrencyAccountByMerchant(List<Long> ids,
                                                                  Long accountTypeId,
                                                                  int pageNumber, int pageSize)
    {
        try
        {
	        int startIndex = pageNumber * pageSize - pageSize;
	        int endIndex = startIndex + pageSize;
	        String insidesql = "select  c.*,rownum rn from ccy_account c  where 1=1";
	        if (endIndex > 0)
	        {
	            insidesql = insidesql + " and rownum <= " + endIndex;
	        }
	        String condition = " and account_id in (";
	        for (Long id : ids)
	        {
	            condition = condition + "'" + id + "',";
	        }
	        condition = condition.substring(0, condition.length() - 1);
	        condition = condition + ")";
	        if (BeanUtils.isNotNull(accountTypeId) && accountTypeId > 0)
	        {
	            condition = condition + " and c.account_type_id = " + accountTypeId + " ";
	        }
	
	        String pageTotal_sql = "select * from ccy_account c where 1=1" + condition;
	        Query query = em.createNativeQuery(pageTotal_sql);
	        @SuppressWarnings("unchecked")
	        List<CurrencyAccount> pageTotal_list = query.getResultList();
	        Double pageTotal = BigDecimalUtil.divide(new BigDecimal(pageTotal_list.size()),
	            new BigDecimal(pageSize), DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
	
	        insidesql = insidesql + condition;
	
	        String sql = "select * from (" + insidesql + ") where rn>" + startIndex + "";
	        query = em.createNativeQuery(sql, CurrencyAccount.class);
	        @SuppressWarnings("unchecked")
	        List<CurrencyAccount> cabList = query.getResultList();
	
	        YcPage<CurrencyAccount> ycPage = new YcPage<CurrencyAccount>();
	        ycPage.setList(cabList);
	        ycPage.setCountTotal((int)pageTotal_list.size());
	        ycPage.setPageTotal(pageTotal.intValue());
	        return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryCurrencyAccountByMerchant exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

    

    public void addCreditableBanlance(final Long accountId, final Long accountTypeId,final BigDecimal amt, final String tableName, final String remark)
        throws Exception
    {
        try
        {
            Session s = (Session)em.getDelegate();
            s.doWork(new Work()
            {
                @Override
                public void execute(Connection connection)
                    throws SQLException
                {
                    connection.setAutoCommit(false);
                    String sql = "{call ccyAc_creditableBalance_add(?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement call = connection.prepareCall(sql);
                    call.setObject(1, -1l);
                    call.setObject(2, accountId);
                    call.setObject(3, amt);
                    call.setObject(4, Constant.AccountBalanceOperationType.ACT_BAL_OPR_ADD_CREDIT);
                    call.setObject(5, accountId + "增加授信金额[" + amt + "]元");
                    call.setObject(6, tableName);
                    call.setObject(7, accountTypeId);
                    call.setObject(8, remark);
                    call.registerOutParameter(9, java.sql.Types.VARCHAR);
                    call.registerOutParameter(10, java.sql.Types.VARCHAR);
                    call.executeUpdate();
                    String result = call.getString(9);
                    String error_msg = call.getString(10);
                    if(!result.equalsIgnoreCase("0"))
                    {
                        logger.error("addCreditableBanlance Fail! error_msg["+error_msg+"]");
                        throw new ApplicationException("identity101073",new String[] {accountId.toString(), amt.toString()});
                    }
                }
            });
        }
        catch (Exception e)
        {
        	logger.error("addCreditableBanlance exception info["+ e +"][accountId="+accountId+", accountTypeId="+accountTypeId+", amt="+amt+",  tableName="+tableName+"]");
            throw e;
        }
    }

    public void subCreditableBanlance(final Long accountId, final Long accountTypeId, final BigDecimal amt, final String tableName, final String remark)
        throws Exception
    {
        try
        {
            Session s = (Session)em.getDelegate();
            s.doWork(new Work()
            {
                @Override
                public void execute(Connection connection)
                    throws SQLException
                {
                    connection.setAutoCommit(false);
                    String sql = "{call ccyAc_creditableBalance_sub(?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement call = connection.prepareCall(sql);
                    call.setObject(1, -1l);
                    call.setObject(2, accountId);
                    call.setObject(3, amt);
                    call.setObject(4, Constant.AccountBalanceOperationType.ACT_BAL_OPR_SUB_CREDIT);
                    call.setObject(5, accountId + "减少授信金额[" + amt + "]元");
                    call.setObject(6, tableName);
                    call.setObject(7, accountTypeId);
                    call.setObject(8, remark);
                    call.registerOutParameter(9, java.sql.Types.VARCHAR);
                    call.registerOutParameter(10, java.sql.Types.VARCHAR);
                    call.executeUpdate();
                    String result = call.getString(9);
                    String error_msg = call.getString(10);
                    if(!result.equalsIgnoreCase("0"))
                    {
                        logger.error("subCreditableBanlance Fail! error_msg["+error_msg+"]");
                        throw new ApplicationException("identity101074",new String[] {accountId.toString(), amt.toString()});
                    }
                }
            });
        }
        catch (Exception e)
        {
        	logger.error("subCreditableBanlance exception info["+ e +"][accountId="+accountId+", accountTypeId="+accountTypeId+", amt="+amt+",  tableName="+tableName+"]");
            throw e;
        }
    }

    public void credit(final Long accountId, final BigDecimal amt, final Long transactionId,
                       final String type, final Long accountTypeId, final String tableName, final String remark) throws Exception
    {
        try
        {
            Session s = (Session)em.getDelegate();
            s.doWork(new Work()
            {
                @Override
                public void execute(Connection connection)
                    throws SQLException
                {
                    connection.setAutoCommit(false);
                    String sql = "{call ccyAc_balance_credit(?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement call = connection.prepareCall(sql);
                    call.setObject(1, transactionId);
                    call.setObject(2, accountId);
                    call.setObject(3, amt);
                    call.setObject(4, type);
                    call.setObject(5, accountId + " credit 金额[" + amt + "]元");
                    call.setObject(6, accountTypeId);
                    call.setObject(7, tableName);
                    call.setObject(8, remark);
                    call.registerOutParameter(9, java.sql.Types.VARCHAR);
                    call.registerOutParameter(10, java.sql.Types.VARCHAR);
                    call.executeUpdate();
                    String result = call.getString(9);
                    String error_msg = call.getString(10);
                    if(!result.equalsIgnoreCase("0"))
                    {
                        logger.error("credit Fail! error_msg["+error_msg+"]");
                        throw new ApplicationException("identity101080",new String[] {accountId.toString(), amt.toString(), error_msg});
                    }
                }
            });
        }
        catch (Exception e)
        {
            logger.error("credit exception info["+ e +"][accountId="+accountId+", accountTypeId="+accountTypeId+", amt="+amt+", transactionId="+transactionId+", tableName="+tableName+"]");
            throw e;
        }
    }
    
    public void debit(final Long accountId, final BigDecimal amt, final Long transactionId,
                       final String type, final Long accountTypeId, final String tableName, final String remark) throws Exception
    {
        try
        {
            Session s = (Session)em.getDelegate();
            s.doWork(new Work()
            {
                @Override
                public void execute(Connection connection)
                    throws SQLException
                {
                    connection.setAutoCommit(false);
                    String sql = "{call ccyAc_balance_debit(?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement call = connection.prepareCall(sql);
                    call.setObject(1, transactionId);
                    call.setObject(2, accountId);
                    call.setObject(3, amt);
                    call.setObject(4, type);
                    call.setObject(5, accountId + " debit 金额[" + amt + "]元");
                    call.setObject(6, accountTypeId);
                    call.setObject(7, tableName);
                    call.setObject(8, remark);
                    call.registerOutParameter(9, java.sql.Types.VARCHAR);
                    call.registerOutParameter(10, java.sql.Types.VARCHAR);
                    call.executeUpdate();
                    String result = call.getString(9);
                    String error_msg = call.getString(10);
                    if(!result.equalsIgnoreCase("0"))
                    {
                        logger.error("debit Fail! error_msg["+error_msg+"]");
                        throw new ApplicationException("identity101079",new String[] {accountId.toString(), amt.toString(), error_msg});
                    }
                }
            });
        }
        catch (Exception e)
        {
            logger.error("debit exception info["+ e +"][accountId="+accountId+", accountTypeId="+accountTypeId+", amt="+amt+", transactionId="+transactionId+", tableName="+tableName+"]");
            throw e;
        }
    }
    
    @Override
    public void frozenBanlance(final Long accountId, final Long accountTypeId, final BigDecimal amt, final Long transactionId, final String tableName)
        throws Exception
    {
        try
        {
            Session s = (Session)em.getDelegate();
            s.doWork(new Work()
            {
                @Override
                public void execute(Connection connection)
                    throws SQLException
                {
                    connection.setAutoCommit(false);
                    String sql = "{call ccyAc_balance_frozen(?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement call = connection.prepareCall(sql);
                    call.setObject(1, transactionId);
                    call.setObject(2, accountId);
                    call.setObject(3, amt);
                    call.setObject(4, Constant.AccountBalanceOperationType.ACT_BAL_OPR_FORZEN);
                    call.setObject(5, accountId + "冻结"+amt+"元");
                    call.setObject(6, tableName);
                    call.setObject(7, accountTypeId);
                    call.setObject(8, StringUtil.initString());
                    call.registerOutParameter(9, java.sql.Types.VARCHAR);
                    call.registerOutParameter(10, java.sql.Types.VARCHAR);
                    call.executeUpdate();
                    String result = call.getString(9);
                    String error_msg = call.getString(10);
                    if(!result.equalsIgnoreCase("0"))
                    {
                        logger.error("frozenBanlance Fail! error_msg["+error_msg+"]");
                        throw new ApplicationException("identity101075");
                    }
                }
            });
        }
        catch (Exception e)
        {
            logger.error("frozenBanlance exception info["+ e +"][accountId="+accountId+", accountTypeId="+accountTypeId+", amt="+amt+", transactionId="+transactionId+", tableName="+tableName+"]");
            throw e;
        }
    }

    public void unFrozenBanlance(final Long accountId, final Long accountTypeId, final BigDecimal amt, final Long transactionId, final String tableName)
        throws Exception
    {
        try
        {
            Session s = (Session)em.getDelegate();
            s.doWork(new Work()
            {
                @Override
                public void execute(Connection connection)
                    throws SQLException
                {
                    connection.setAutoCommit(false);
                    String sql = "{call ccyAc_balance_unfrozen(?,?,?,?,?,?,?,?,?,?)}";
                    CallableStatement call = connection.prepareCall(sql);
                    call.setObject(1, transactionId);
                    call.setObject(2, accountId);
                    call.setObject(3, amt);
                    call.setObject(4, Constant.AccountBalanceOperationType.ACT_BAL_OPR_UNFORZEN);
                    call.setObject(5, accountId + "解冻"+amt+"元");
                    call.setObject(6, tableName);
                    call.setObject(7, accountTypeId);
                    call.setObject(8, StringUtil.initString());
                    call.registerOutParameter(9, java.sql.Types.VARCHAR);
                    call.registerOutParameter(10, java.sql.Types.VARCHAR);
                    call.executeUpdate();
                    String result = call.getString(9);
                    String error_msg = call.getString(10);
                    if(!result.equalsIgnoreCase("0"))
                    {
                        logger.error("unFrozenBanlance Fail! error_msg["+error_msg+"]");
                        throw new ApplicationException("identity101076",new String[] {accountId.toString(), amt.toString()});
                    }
                }
            });
        }
        catch (Exception e)
        {
            logger.error("unFrozenBanlance exception info["+ e +"][accountId="+accountId+", accountTypeId="+accountTypeId+", amt="+amt+", transactionId="+transactionId+", tableName="+tableName+"]");
            throw e;
        }
    }
}
