/*
 * 文件名：AssignExcludeSqlDaoImpl.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015-4-14
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.transaction.config.repository.impl.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.transaction.config.entify.product.AssignExclude;
import com.yuecheng.hops.transaction.config.repository.AssignExcludeSqlDao;

@Service("assignExcludeSqlDao")
public class AssignExcludeSqlDaoImpl implements AssignExcludeSqlDao
{
    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssignExcludeSqlDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<AssignExclude> getAssignExcludeByParams(Map<String, Object> searchParams)
    {
        String allSql = "select * from assign_exclude t where t.merchant_id = :merchantId "
            +" and t.rule_type = :ruleType ";
        String carrierNo=(String)searchParams.get(EntityConstant.AssignExclude.CARRIER_NO);
        String ruleType=(String)searchParams.get(EntityConstant.AssignExclude.RULE_TYPE);
        String merchantId=(String)searchParams.get(EntityConstant.AssignExclude.MERCHANT_ID);
        String objectMerchantId=(String)searchParams.get(EntityConstant.AssignExclude.OBJECT_MERCHANT_ID);
        String proviceNo=(String)searchParams.get(EntityConstant.AssignExclude.PROVICE_NO);
        String cityNo=(String)searchParams.get(EntityConstant.AssignExclude.CITY_NO);
        String parValue=(String)searchParams.get(EntityConstant.AssignExclude.PAR_VALUE);
        String merchantType=(String)searchParams.get(EntityConstant.AssignExclude.MERCHANT_TYPE);
        LOGGER.debug("AssignExcludeSqlDaoImpl---getAssignExcludeByParams---allSql=" + allSql);
        Map<String, Object> fields = new HashMap<String,Object>();
        fields.put(EntityConstant.AssignExclude.RULE_TYPE, BeanUtils.isNotNull(ruleType)?ruleType:"");
        fields.put(EntityConstant.AssignExclude.MERCHANT_ID, BeanUtils.isNotNull(merchantId)?merchantId:"");
        
        if(StringUtils.isNotBlank(objectMerchantId))
        {
            allSql=allSql+" and t.object_merchant_id = :objectMerchantId ";
            fields.put(EntityConstant.AssignExclude.OBJECT_MERCHANT_ID, BeanUtils.isNotNull(objectMerchantId)?objectMerchantId:"");
        }
        
        if(StringUtils.isNotBlank(parValue))
        {
            allSql=allSql+" and t.par_Value = :parValue ";
            fields.put(EntityConstant.AssignExclude.PAR_VALUE, BeanUtils.isNotNull(parValue)?parValue:"");
        }
        
        Set<AssignExclude> set=new HashSet<AssignExclude>();
        if(StringUtils.isBlank(carrierNo)&&StringUtils.isBlank(proviceNo)&&StringUtils.isBlank(cityNo))
        {
            //运省城均为空获取全部数据
            Query allQuery = em.createNativeQuery(allSql, AssignExclude.class);
            allQuery = setParameter(fields, allQuery);
            set.addAll(allQuery.getResultList());
        }
        if(StringUtils.isNotBlank(carrierNo)&&StringUtils.isBlank(proviceNo)&&StringUtils.isBlank(cityNo))
        {
            //运营商不为空省城为空，获取运营商所有数据
            allSql=allSql+" and t.carrier_no = :carrierNo ";
            fields.put(EntityConstant.AssignExclude.CARRIER_NO, BeanUtils.isNotNull(carrierNo)?carrierNo:"");
            Query allQuery = em.createNativeQuery(allSql, AssignExclude.class);
            allQuery = setParameter(fields, allQuery);
            set.addAll(allQuery.getResultList());
        }
        if(StringUtils.isNotBlank(carrierNo)&&StringUtils.isNotBlank(proviceNo)&&StringUtils.isBlank(cityNo))
        {
            //运省不为空城市为空，获取该运营商且省城都为空的数据
            String allSql1=allSql+" and t.carrier_no = :carrierNo and t.province_no is null and t.city_no is null ";
            fields.put(EntityConstant.AssignExclude.CARRIER_NO, BeanUtils.isNotNull(carrierNo)?carrierNo:"");
            //如为供货商指定（排除）代理商，则不需要获取其父级产品
            if(StringUtils.isNotBlank(merchantType)&&MerchantType.AGENT.toString().equals(merchantType))
            {
                Query allQuery1 = em.createNativeQuery(allSql1, AssignExclude.class);
                allQuery1 = setParameter(fields, allQuery1);
                set.addAll(allQuery1.getResultList());
            }
            //运省不为空城市为空，获取该运营商和该省份且城市为空的所有数据
            String allSql2=allSql+" and t.carrier_no = :carrierNo and t.province_no = :provinceNo and (t.city_no is null or t.city_no is not null)";
            fields.put(EntityConstant.AssignExclude.PROVICE_NO, BeanUtils.isNotNull(proviceNo)?proviceNo:"");
            Query allQuery2 = em.createNativeQuery(allSql2, AssignExclude.class);
            allQuery2 = setParameter(fields, allQuery2);
            set.addAll(allQuery2.getResultList());
        }
        if(StringUtils.isNotBlank(carrierNo)&&StringUtils.isNotBlank(proviceNo)&&StringUtils.isNotBlank(cityNo))
        {
            //运省城都不为空，获取该运营商且省城都为空的数据
            String allSql1=allSql+" and t.carrier_no = :carrierNo and t.province_no is null and t.city_no is null ";
            fields.put(EntityConstant.AssignExclude.CARRIER_NO, BeanUtils.isNotNull(carrierNo)?carrierNo:"");
            //如为供货商指定（排除）代理商，则不需要获取其父级产品
            if(StringUtils.isNotBlank(merchantType)&&MerchantType.AGENT.toString().equals(merchantType))
            {
                Query allQuery1 = em.createNativeQuery(allSql1, AssignExclude.class);
                allQuery1 = setParameter(fields, allQuery1);
                set.addAll(allQuery1.getResultList());
            }
            //运省城都不为空，获取该运营商和该城市且城市都为空的数据
            String allSql2=allSql+" and t.carrier_no = :carrierNo and t.province_no = :provinceNo and t.city_no is null ";
            fields.put(EntityConstant.AssignExclude.PROVICE_NO, BeanUtils.isNotNull(proviceNo)?proviceNo:"");
            //如为供货商指定（排除）代理商，则不需要获取其父级产品
            if(StringUtils.isNotBlank(merchantType)&&MerchantType.AGENT.toString().equals(merchantType))
            {
                Query allQuery2 = em.createNativeQuery(allSql2, AssignExclude.class);
                allQuery2 = setParameter(fields, allQuery2);
                set.addAll(allQuery2.getResultList());
            }
            //运省城都不为空，获取该运营商和该省份以及该城市的数据
            String allSql3=allSql+" and t.carrier_no = :carrierNo and t.province_no = :provinceNo and t.city_no = :cityNo ";
            fields.put(EntityConstant.AssignExclude.CITY_NO, BeanUtils.isNotNull(cityNo)?cityNo:"");
            Query allQuery3 = em.createNativeQuery(allSql3, AssignExclude.class);
            allQuery3 = setParameter(fields, allQuery3);
            set.addAll(allQuery3.getResultList());
        }
        if(StringUtils.isBlank(carrierNo)&&StringUtils.isNotBlank(proviceNo)&&StringUtils.isBlank(cityNo))
        {
            //运营商和城市为空，省份不为空时，获取所有运营商和该省城都为空的数据
            String allSql0=allSql+" and t.province_no is null and t.city_no is null ";
            //如为供货商指定（排除）代理商，则不需要获取其父级产品
            if(StringUtils.isNotBlank(merchantType)&&MerchantType.AGENT.toString().equals(merchantType))
            {
                Query allQuery0 = em.createNativeQuery(allSql0, AssignExclude.class);
                allQuery0 = setParameter(fields, allQuery0);
                set.addAll(allQuery0.getResultList());
            }
            //运营商和城市为空，省份不为空时，获取所有运营商和该省份且城市为空的数据
            String allSql1=allSql+" and t.province_no = :provinceNo and (t.city_no is null or t.city_no is not null)";
            fields.put(EntityConstant.AssignExclude.PROVICE_NO, BeanUtils.isNotNull(proviceNo)?proviceNo:"");
            Query allQuery1 = em.createNativeQuery(allSql1, AssignExclude.class);
            allQuery1 = setParameter(fields, allQuery1);
            set.addAll(allQuery1.getResultList());
        }
        if(StringUtils.isBlank(carrierNo)&&StringUtils.isNotBlank(proviceNo)&&StringUtils.isNotBlank(cityNo))
        {
            //运营商为空，省城不为空时，获取所有运营商和该省城都为空的数据
            String allSql0=allSql+" and t.province_no is null and t.city_no is null ";
            //如为供货商指定（排除）代理商，则不需要获取其父级产品
            if(StringUtils.isNotBlank(merchantType)&&MerchantType.AGENT.toString().equals(merchantType))
            {
                Query allQuery0 = em.createNativeQuery(allSql0, AssignExclude.class);
                allQuery0 = setParameter(fields, allQuery0);
                set.addAll(allQuery0.getResultList());
            }
            //运营商为空，省城不为空时，获取所有运营商和该省份且城市为空的数据
            String allSql1=allSql+" and t.province_no = :provinceNo and t.city_no is null";
            fields.put(EntityConstant.AssignExclude.PROVICE_NO, BeanUtils.isNotNull(proviceNo)?proviceNo:"");
            //如为供货商指定（排除）代理商，则不需要获取其父级产品
            if(StringUtils.isNotBlank(merchantType)&&MerchantType.AGENT.toString().equals(merchantType))
            {
                Query allQuery1 = em.createNativeQuery(allSql1, AssignExclude.class);
                allQuery1 = setParameter(fields, allQuery1);
                set.addAll(allQuery1.getResultList());
            }
            //运营商为空，生成不为空时，获取所有运营商和该省份以及该城市的所有数据
            String allSql2=allSql+" and t.province_no = :provinceNo and t.city_no = :cityNo ";
            fields.put(EntityConstant.AssignExclude.CITY_NO, BeanUtils.isNotNull(cityNo)?cityNo:"");
            Query allQuery2 = em.createNativeQuery(allSql2, AssignExclude.class);
            allQuery2 = setParameter(fields, allQuery2);
            set.addAll(allQuery2.getResultList());
        }
        List<AssignExclude> assigenExcludes = new ArrayList<AssignExclude>(set);
        return assigenExcludes;
    }
    
    public static Query setParameter(Map<String, Object> fields, Query query)
    {
        try{
            for (Map.Entry<String, Object> entry : fields.entrySet())
            {
                query.setParameter(entry.getKey(), BeanUtils.isNotNull(entry.getValue())?entry.getValue().toString():"");
            }
        }
        catch(Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
            return query;
    }
}
