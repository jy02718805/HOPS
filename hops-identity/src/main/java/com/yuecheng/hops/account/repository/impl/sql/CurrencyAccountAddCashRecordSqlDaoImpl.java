/*
 * 文件名：CurrencyAccountAddCashRecordSqlDaoImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年11月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.account.repository.impl.sql;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.CurrencyAccountAddCashRecord;
import com.yuecheng.hops.account.entity.vo.CurrencyAccountAddCashRecordVo;
import com.yuecheng.hops.account.repository.CurrencyAccountAddCashRecordSqlDao;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;

@Service("currencyAccountAddCashRecordSqlDao")
public class CurrencyAccountAddCashRecordSqlDaoImpl implements CurrencyAccountAddCashRecordSqlDao
{

    @PersistenceContext
    private EntityManager em;
    
	private static Logger logger = LoggerFactory.getLogger(CurrencyAccountAddCashRecordSqlDaoImpl.class);
	
    @Override
    public YcPage<CurrencyAccountAddCashRecord> findCurrencyAccountAddCashRecordByParams(CurrencyAccountAddCashRecordVo currencyAccountAddCashRecordVo,
                                                                                         int pageNumber,
                                                                                         int pageSize,
                                                                                         String sortType)
    {
        try
        {
	        int startIndex = pageNumber * pageSize - pageSize;
	        int endIndex = startIndex + pageSize;
	        if(StringUtil.isBlank(sortType)){
	        	sortType = " id ";
	        }
	        String insidesql = "select  c.*,rownum rn from (select * from ccy_Account_Add_Cash_Record order by "+sortType+" desc) c  where 1=1 ";
	
	        if (endIndex > 0)
	        {
	            insidesql = insidesql + " and rownum <= " + endIndex;
	        }
	        
	        String condition = StringUtil.initString();
	        
	        if(BeanUtils.isNotNull(currencyAccountAddCashRecordVo.getMerchantId())&&StringUtil.isNotBlank(currencyAccountAddCashRecordVo.getMerchantId().toString())){
	            condition = condition + "and c.merchant_id='"+currencyAccountAddCashRecordVo.getMerchantId()+"' ";
	        }
	        
	        if(StringUtil.isNotBlank(currencyAccountAddCashRecordVo.getMerchantName())){
	            condition = condition + " and c.merchant_Name like '%"+currencyAccountAddCashRecordVo.getMerchantName()+"%' ";
	        }
	        if(BeanUtils.isNotNull(currencyAccountAddCashRecordVo.getVerifyStatus())){
	            condition = condition + " and c.verify_Status='"+currencyAccountAddCashRecordVo.getVerifyStatus()+"'";
	        }
	        if(StringUtil.isNotBlank(currencyAccountAddCashRecordVo.getBeginApplyTime())){
	            condition = condition + " and c.apply_time >= to_date('" + currencyAccountAddCashRecordVo.getBeginApplyTime()
	                + "','yyyy-mm-dd hh24:mi:ss')";
	        }
	        if(StringUtil.isNotBlank(currencyAccountAddCashRecordVo.getEndApplyTime())){
	            condition = condition + " and c.apply_time <= to_date('" + currencyAccountAddCashRecordVo.getEndApplyTime()
	                + "','yyyy-mm-dd hh24:mi:ss')";
	        }
	        if(StringUtil.isNotBlank(currencyAccountAddCashRecordVo.getOperatorName())){
	            condition = condition + " and c.operator_name='"+currencyAccountAddCashRecordVo.getOperatorName()+"' ";
	        }
	        
	        String pageTotal_sql = "select * from ccy_Account_Add_Cash_Record c where 1=1" + condition;
	        Query query = em.createNativeQuery(pageTotal_sql);
	        @SuppressWarnings("unchecked")
	        List<CurrencyAccountAddCashRecord> pageTotal_list = query.getResultList();
	        
	        Double pageTotal = BigDecimalUtil.divide(new BigDecimal(pageTotal_list.size()), new BigDecimal(pageSize), DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
	
	        insidesql = insidesql + condition;
	
	        String sql = "select * from (" + insidesql + ") where rn>" + startIndex + "";
	        query = em.createNativeQuery(sql, CurrencyAccountAddCashRecord.class);
	        @SuppressWarnings("unchecked")
	        List<CurrencyAccountAddCashRecord> cabList = query.getResultList();
	
	        YcPage<CurrencyAccountAddCashRecord> ycPage = new YcPage<CurrencyAccountAddCashRecord>();
	        ycPage.setList(cabList);
	        ycPage.setCountTotal((int)pageTotal_list.size());
	        ycPage.setPageTotal(pageTotal.intValue());
	        return ycPage;
        }
        catch (Exception e)
        {
            logger.error("findCurrencyAccountAddCashRecordByParams exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

}
