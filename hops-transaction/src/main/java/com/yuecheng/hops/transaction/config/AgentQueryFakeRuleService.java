package com.yuecheng.hops.transaction.config;


import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.config.entify.fake.AgentQueryFakeRule;


/**
 * 下游预查询规则服务
 * 
 * @author
 */
public interface AgentQueryFakeRuleService
{
    /**
     * 保存下游预查询规则
     * 
     * @param fakeRule
     *            下游预查询规则
     * @return
     */
    public AgentQueryFakeRule save(AgentQueryFakeRule fakeRule);

    /**
     * 根据ID查询下游预查询规则
     * 
     * @param id
     *            下游预查询规则ID
     * @return
     */
    public AgentQueryFakeRule queryAgentQueryFakeRuleById(Long merchantId);

    /**
     * 删除下游预查询规则
     * 
     * @param fakeRule
     *            下游预查询规则
     */
    public Long deleteAgentQueryFakeRule(Long merchantId);

    /**
     * 分页查询下游预查询规则
     * 
     * @param searchParams
     *            查询条件Map
     * @param pageNumber
     *            页码
     * @param pageSize
     *            页大小
     * @param bsort
     *            排序
     * @return
     */
    public YcPage<AgentQueryFakeRule> queryAgentQueryFakeRule(Map<String, Object> searchParams,
                                                              int pageNumber, int pageSize,
                                                              BSort bsort);
}
