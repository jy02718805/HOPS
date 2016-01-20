package com.yuecheng.hops.security.repository.impl.sql;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;
import com.yuecheng.hops.security.repository.OracleSql;
import com.yuecheng.hops.security.repository.SecurityRuleDao;


/**
 * @Title: SecurityRuleSqlDao.java
 * @Package com.yuecheng.hops.identity.repository.impl.sql
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @version V1.0
 * @ClassName: SecurityRuleSqlDao
 * @Description: TODO
 * @author 肖进
 * @date 2014年9月1日 下午2:59:52
 */
@Service
public class SecurityRuleSqlDao implements SecurityRuleDao
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityRuleSqlDao.class);

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<SecurityCredentialRule> querySecurityRuleList(String securityrulename,
                                                                String letter, String figure,
                                                                String specialCharacter,
                                                                Long status, int pageNumber,
                                                                int pageSize)
    {
        try
        {
	        String pageSql = OracleSql.SecurityCredentialRule.PAGE_SQL;
	        String allSql = OracleSql.SecurityCredentialRule.All_SQL;
	        logger.debug("SecurityRuleSqlDao---querySecurityRuleList---pageSql=" + pageSql
	                     + ",allSql=" + allSql);
	        Map<String, Object> fields = new HashMap<String, Object>();
	        fields.put(EntityConstant.SecurityRule.RULE_NAME, BeanUtils.isNotNull(securityrulename)?securityrulename:"");
	        fields.put(EntityConstant.SecurityRule.LETTER, BeanUtils.isNotNull(letter)?letter:"");
	        fields.put(EntityConstant.SecurityRule.FIGURE, BeanUtils.isNotNull(figure)?figure:"");
	        fields.put(EntityConstant.SecurityRule.SPECIAL_CHARACTER, BeanUtils.isNotNull(specialCharacter)?specialCharacter:"");
	        fields.put(EntityConstant.SecurityRule.STATUS, BeanUtils.isNotNull(status)?status:"");
	        Query allQuery = em.createNativeQuery(allSql, SecurityCredentialRule.class);
            allQuery = setParameter(fields, allQuery);
	        List<SecurityCredentialRule> allList=allQuery.getResultList();
	        
	        Double pageTotal =BigDecimalUtil.divide(new BigDecimal(allList.size()), new BigDecimal(pageSize),
	            DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
	        fields.put(EntityConstant.Page.PAGE_NUMBER, BeanUtils.isNotNull(pageNumber)?pageNumber:0);
	        fields.put(EntityConstant.Page.PAGE_SIZE, BeanUtils.isNotNull(pageSize)?pageSize:10);
	        Query pageQuery = em.createNativeQuery(pageSql, SecurityCredentialRule.class);
            pageQuery = setParameter(fields, pageQuery);
	        List<SecurityCredentialRule> scrList=pageQuery.getResultList();
	        YcPage<SecurityCredentialRule> ycPage = new YcPage<SecurityCredentialRule>();
	        ycPage.setList(scrList);
	        ycPage.setCountTotal(allList.size());
	        ycPage.setPageTotal(pageTotal.intValue());
	        return ycPage;
        }
        catch (Exception e)
        {
            logger.error("querySecurityRuleList exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public static Query setParameter(Map<String, Object> fields, Query query)
    {
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
        	query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }

}
