package com.yuecheng.hops.transaction.config.impl;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.DecimalPlaces;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.BigDecimalUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.transaction.config.AgentQueryFakeRuleService;
import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;
import com.yuecheng.hops.transaction.config.repository.AgentQueryFakeRuleDao;


/**
 * 下游预查询规则服务
 * 
 * @author
 */
@Service("agentQueryFakeRuleService")
public class AgentQueryFakeRuleServiceImpl implements AgentQueryFakeRuleService
{
    private static Logger logger = LoggerFactory.getLogger(AgentQueryFakeRuleServiceImpl.class);
    
    @Autowired
    private MerchantService       merchantService;

    @Autowired
    private AgentQueryFakeRuleDao agentQueryFakeRuleDao;

    @Autowired
    private IdentityService       identityService;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public AgentQueryFakeRule save(AgentQueryFakeRule fakeRule)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentQueryFakeRule.MERCHANT_ID, new SearchFilter(EntityConstant.AgentQueryFakeRule.MERCHANT_ID, Operator.EQ, fakeRule.getMerchantId()));
        Specification<AgentQueryFakeRule> spec_fakeRule = DynamicSpecifications.bySearchFilter(filters.values(), AgentQueryFakeRule.class);
        AgentQueryFakeRule fakeRuleTemp = agentQueryFakeRuleDao.findOne(spec_fakeRule);
        if (BeanUtils.isNotNull(fakeRuleTemp))
        {
            fakeRuleTemp.setIntervalTime(fakeRule.getIntervalTime());
            fakeRuleTemp.setIntervalUnit(fakeRule.getIntervalUnit());
            fakeRuleTemp.setMerchantName(fakeRule.getMerchantName());
            agentQueryFakeRuleDao.save(fakeRuleTemp);
        }
        else
        {
            fakeRule = agentQueryFakeRuleDao.save(fakeRule);
        }
        return fakeRule;
    }

    @Override
    public AgentQueryFakeRule queryAgentQueryFakeRuleById(Long merchantId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentQueryFakeRule.MERCHANT_ID, new SearchFilter(EntityConstant.AgentQueryFakeRule.MERCHANT_ID, Operator.EQ, merchantId));
        Specification<AgentQueryFakeRule> spec_fakeRule = DynamicSpecifications.bySearchFilter(filters.values(), AgentQueryFakeRule.class);
        AgentQueryFakeRule fakeRule = agentQueryFakeRuleDao.findOne(spec_fakeRule);
        return fakeRule;
    }

    @Override
    public YcPage<AgentQueryFakeRule> queryAgentQueryFakeRule(Map<String, Object> searchParams,
                                                              int pageNumber, int pageSize,
                                                              BSort bsort)
    {
        YcPage<AgentQueryFakeRule> ycPage = new YcPage<AgentQueryFakeRule>();
        try
        {
            int startIndex = pageNumber * pageSize - pageSize;
            int endIndex = startIndex + pageSize;
            String insidesql = "select  d.*,rownum rn from agent_query_fake_rule d  where 1=1";
            if (endIndex > 0)
            {
                insidesql = insidesql + " and rownum <= " + endIndex;
            }
    
            String condition = StringUtil.initString();
    
            List<Merchant> mList = null;
            if (searchParams.get(EntityConstant.Merchant.MERCHANT_NAME) != null
                && !searchParams.get(EntityConstant.Merchant.MERCHANT_NAME).toString().isEmpty())
            {
                Map<String, Object> merchantMap = new HashMap<String, Object>();
                merchantMap.put(Operator.LIKE + "_" + EntityConstant.Merchant.MERCHANT_NAME,
                    searchParams.get(EntityConstant.Merchant.MERCHANT_NAME));
                mList = merchantService.queryMerchantList(merchantMap);
                if (mList != null && mList.size() > 0)
                {
                    condition = condition + " and ";
                    for (int i = 0; i < mList.size(); i++ )
                    {
                        condition = condition + " d.merchant_id=" + mList.get(i).getId();
                        if (i != (mList.size() - 1))
                        {
                            condition = condition + " or";
                        }
                    }
                }
                else
                {
                    condition = condition + " and d.merchant_id=null";
                }
            }
    
            String pageTotal_sql = "select * from agent_query_fake_rule d where 1=1" + condition;
            Query query = em.createNativeQuery(pageTotal_sql);
            List<AgentQueryFakeRule> pageTotal_list = query.getResultList();
            Double pageTotal = BigDecimalUtil.divide(new BigDecimal(pageTotal_list.size()), new BigDecimal(pageSize),
                DecimalPlaces.ZERO.value(), BigDecimal.ROUND_CEILING).doubleValue();
            insidesql = insidesql + condition;
    
            String sql = "select * from (" + insidesql + ") where rn>" + startIndex;
            query = em.createNativeQuery(sql, AgentQueryFakeRule.class);
            List<AgentQueryFakeRule> dqfrList = query.getResultList();
    
            for (AgentQueryFakeRule dqfr : dqfrList)
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                    dqfr.getMerchantId(), IdentityType.MERCHANT);
                dqfr.setMerchantName(merchant.getMerchantName());
            }
            ycPage.setList(dqfrList);
            ycPage.setCountTotal((int)pageTotal_list.size());
            ycPage.setPageTotal(pageTotal.intValue());
            return ycPage;
        }
        catch (Exception e)
        {
            logger.error("queryAgentQueryFakeRule exception info["+ e +"]");
            throw ExceptionUtil.throwException(e);
        }
    }

    @Override
    @Transactional
    public Long deleteAgentQueryFakeRule(Long merchantId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentQueryFakeRule.MERCHANT_ID, new SearchFilter(EntityConstant.AgentQueryFakeRule.MERCHANT_ID, Operator.EQ, merchantId));
        Specification<AgentQueryFakeRule> spec_fakeRule = DynamicSpecifications.bySearchFilter(filters.values(), AgentQueryFakeRule.class);
        AgentQueryFakeRule fakeRule = agentQueryFakeRuleDao.findOne(spec_fakeRule);
        
        if (fakeRule == null)
        {
            throw ExceptionUtil.throwException(new ApplicationException("transaction000003"));
        }
        else
        {
            agentQueryFakeRuleDao.delete(fakeRule);
        }
        return merchantId;
    }
}
