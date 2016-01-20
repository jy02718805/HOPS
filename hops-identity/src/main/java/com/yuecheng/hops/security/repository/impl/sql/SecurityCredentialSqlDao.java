package com.yuecheng.hops.security.repository.impl.sql;


import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.SecurityCredential;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.security.entity.vo.SecurityCredentialVo;
import com.yuecheng.hops.security.repository.SecurityCredentialDao;


/**
 * 密匙表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-26
 */
@Service
public class SecurityCredentialSqlDao implements SecurityCredentialDao
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityCredentialSqlDao.class);

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
	public YcPage<SecurityCredentialVo> queryPageSecurityCredential(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize)
    {
        try
        {
	        int startIndex = pageNumber * pageSize - pageSize;
	        int endIndex = startIndex + pageSize;
	
	        String insidesql ="select  security.*,rownum rn from ( ";
	        String sqlSelect = "";
	        String sqlWhere = "";
	
	        if (BeanUtils.isNotNull(searchParams.get(EntityConstant.SecurityCredential.IDENTITY_TYPE)))
	        {
	            if (IdentityType.CUSTOMER.toString().equals(
	                searchParams.get(EntityConstant.SecurityCredential.IDENTITY_TYPE).toString()))
	            {
	                sqlSelect = "select s.security_id as securityId,s.security_name as securityName,s.identity_type as identityType,sct.security_type_name as securityTypeName,to_char(s.validity_date,'yyyy-mm-dd HH24:mi:ss')  as validityDate,s.status,to_char(s.create_date,'yyyy-mm-dd HH24:mi:ss') as createDate,to_char(s.update_date,'yyyy-mm-dd HH24:mi:ss') as updateDate,s.update_user as updateUser,c.customer_name as identityName from security_credential s left join customer c on s.identity_id=c.identity_id left join security_credential_type sct on s.security_type_id=sct.security_type_id";
	           
	                sqlWhere=" and ('"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"'is null or c.customer_name like '%"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"%')";
	            }
	
	            if (IdentityType.MERCHANT.toString().equals(
	                searchParams.get(EntityConstant.SecurityCredential.IDENTITY_TYPE).toString()))
	            {
	                sqlSelect = "select s.security_id as securityId,s.security_name as securityName,s.identity_type as identityType,sct.security_type_name as securityTypeName,to_char(s.validity_date,'yyyy-mm-dd HH24:mi:ss')  as validityDate,s.status,to_char(s.create_date,'yyyy-mm-dd HH24:mi:ss') as createDate,to_char(s.update_date,'yyyy-mm-dd HH24:mi:ss') as updateDate,s.update_user as updateUser,m.merchant_name as identityName from security_credential s left join merchant m on s.identity_id=m.identity_id left join security_credential_type sct on s.security_type_id=sct.security_type_id";
	                sqlWhere=" and ('"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"'is null or m.merchant_name like '%"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"%')";
	            }
	
	            if (IdentityType.SP.toString().equals(
	                searchParams.get(EntityConstant.SecurityCredential.IDENTITY_TYPE).toString()))
	            {
	                sqlSelect = "select s.security_id as securityId,s.security_name as securityName,s.identity_type as identityType,sct.security_type_name as securityTypeName,to_char(s.validity_date,'yyyy-mm-dd HH24:mi:ss')  as validityDate,s.status,to_char(s.create_date,'yyyy-mm-dd HH24:mi:ss') as createDate,to_char(s.update_date,'yyyy-mm-dd HH24:mi:ss') as updateDate,s.update_user as updateUser,p.sp_name as identityName from security_credential s left join sp p on s.identity_id=p.identity_id left join security_credential_type sct on s.security_type_id=sct.security_type_id";
	            
	                sqlWhere=" and ('"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"'is null or p.sp_name like '%"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"%')";
	            }
	
	            if (IdentityType.OPERATOR.toString().equals(
	                searchParams.get(EntityConstant.SecurityCredential.IDENTITY_TYPE).toString()))
	            {
	                sqlSelect = "select s.security_id as securityId,s.security_name as securityName,s.identity_type as identityType,sct.security_type_name as securityTypeName,to_char(s.validity_date,'yyyy-mm-dd HH24:mi:ss')  as validityDate,s.status,to_char(s.create_date,'yyyy-mm-dd HH24:mi:ss') as createDate,to_char(s.update_date,'yyyy-mm-dd HH24:mi:ss') as updateDate,s.update_user as updateUser,o.operator_name as identityName from security_credential s left join operator o on s.identity_id=o.identity_id left join security_credential_type sct on s.security_type_id=sct.security_type_id";
	                sqlWhere=" and ('"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"'is null or o.operator_name like '%"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_NAME)+"%') ";
	            }
	        }
	
	        sqlSelect += " where 1=1";
	        
	        if (BeanUtils.isNotNull(searchParams.get(EntityConstant.SecurityCredential.IDENTITY_TYPE))
	            && StringUtil.isNotBlank(searchParams.get(
	                EntityConstant.SecurityCredential.IDENTITY_TYPE).toString()))
	        {
	            sqlWhere+=" and s.identity_type='"+searchParams.get(EntityConstant.SecurityCredential.IDENTITY_TYPE)+"'";
	        }
	
	        if (BeanUtils.isNotNull(searchParams.get(EntityConstant.SecurityCredential.SECURITY_TYPE))
	            && StringUtil.isNotBlank(searchParams.get(
	                EntityConstant.SecurityCredential.SECURITY_TYPE).toString()))
	        {
	            sqlWhere+=" and s.security_type_id='"+searchParams.get(EntityConstant.SecurityCredential.SECURITY_TYPE)+"'";
	        }
	        if (BeanUtils.isNotNull(searchParams.get(EntityConstant.SecurityCredential.STATUS))
	            && StringUtil.isNotBlank(searchParams.get(EntityConstant.SecurityCredential.STATUS).toString()))
	        {
	            sqlWhere+=" and s.status='"+searchParams.get(EntityConstant.SecurityCredential.STATUS)+"'";
	        }else{
	            sqlWhere+=" and (s.status='"+Constant.SecurityCredentialStatus.DISABLE_STATUS+"' or s.status='"+Constant.SecurityCredentialStatus.ENABLE_STATUS+"' "
	                + " or s.status='"+Constant.SecurityCredentialStatus.EXPIRATION_STATUS+"') ";
	        }
	
	        insidesql += sqlSelect+sqlWhere +" order by s.create_date desc) security where 1=1 ";
	        if (endIndex > 0)
	        {
	            insidesql = insidesql + " and rownum <= " + endIndex;
	        }
	
	        String pageTotal_sql = "select count(*) from (" + sqlSelect + sqlWhere + ") security where 1=1 ";
	        Query query = em.createNativeQuery(pageTotal_sql);
	        BigDecimal total=(BigDecimal)query.getSingleResult();
	        
	        Double pageTotal = BigDecimalUtil.divide(total, new BigDecimal(pageSize),
	            DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
	        String sql = "select * from (" + insidesql + ") where rn>=" + startIndex + "";
	
	        query = em.createNativeQuery(sql);
	        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	
	        List<SecurityCredentialVo> svoList = new ArrayList<SecurityCredentialVo>();
	        List<SecurityCredential> rows = query.getResultList();
	        for (Object obj : rows)
	        {
	            Map<String, Object> row = (Map<String, Object>)obj;
	
	            SecurityCredentialVo securityCredentialVo = new SecurityCredentialVo();
	            BeanUtils.transMap2Bean(row, securityCredentialVo);
	            svoList.add(securityCredentialVo);
	        }
	        YcPage<SecurityCredentialVo> ycPage = new YcPage<SecurityCredentialVo>();
	        ycPage.setList(svoList);
	        ycPage.setCountTotal(total.intValue());
	        ycPage.setPageTotal(pageTotal.intValue());
	        return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryPageSecurityCredential exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

}
