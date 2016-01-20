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
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.repository.OracleSql;
import com.yuecheng.hops.security.repository.SecurityTypeDao;


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
public class SecurityTypeSqlDao implements SecurityTypeDao
{

    private static final Logger logger = LoggerFactory.getLogger(SecurityTypeSqlDao.class);

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public YcPage<SecurityCredentialType> querySecurityTypeList(String securityTypeName,
                                                                String modelType,
                                                                String encryptType,
                                                                Long securityRuleId, Long status,
                                                                int pageNumber, int pageSize)
    {
        try
        {
	        String pageSql = OracleSql.SecurityCredentialType.PAGE_SQL;
	        String allSql = OracleSql.SecurityCredentialType.All_SQL;
	        logger.debug("SecurityTypeSqlDao---querySecurityTypeList---pageSql=" + pageSql
	                     + ",allSql=" + allSql);
	        Map<String, Object> fields = new HashMap<String, Object>();
	        fields.put(EntityConstant.SecurityType.TYPE_NAME, BeanUtils.isNotNull(securityTypeName)?securityTypeName:"");
	        fields.put(EntityConstant.SecurityType.MODEL_TYPE, BeanUtils.isNotNull(modelType)?modelType:"");
	        fields.put(EntityConstant.SecurityType.ENCRYPT_TYPE, BeanUtils.isNotNull(encryptType)?encryptType:"");
	        fields.put(EntityConstant.SecurityType.SECURITY_RULE_ID, BeanUtils.isNotNull(securityRuleId)?securityRuleId:"");
	        fields.put(EntityConstant.SecurityType.STATUS, BeanUtils.isNotNull(status)?status:"");
	        Query allQuery = em.createNativeQuery(allSql, SecurityCredentialType.class);
            allQuery = setParameter(fields, allQuery);
	        List<SecurityCredentialType> allList=allQuery.getResultList();
	        Double pageTotal = BigDecimalUtil.divide(new BigDecimal(allList.size()), new BigDecimal(pageSize),
	            DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();

	        fields.put(EntityConstant.Page.PAGE_NUMBER, BeanUtils.isNotNull(pageNumber)?pageNumber:0);
	        fields.put(EntityConstant.Page.PAGE_SIZE, BeanUtils.isNotNull(pageSize)?pageSize:10);
	        Query pageQuery = em.createNativeQuery(pageSql, SecurityCredentialType.class);
            pageQuery = setParameter(fields, pageQuery);
	        List<SecurityCredentialType> securityTypes=pageQuery.getResultList();
	        YcPage<SecurityCredentialType> ycPage = new YcPage<SecurityCredentialType>();
	        ycPage.setList(securityTypes);
	        ycPage.setCountTotal(allList.size());
	        ycPage.setPageTotal(pageTotal.intValue());
	        return ycPage;
        }
        catch (Exception e)
        {
            logger.error("querySecurityTypeList exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

    public static Query setParameter(Map<String, Object> fields, Query query)
    {
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
        	query.setParameter(entry.getKey(), BeanUtils.isNotNull(entry.getValue())?entry.getValue().toString():"");
        }
        return query;
    }
}
