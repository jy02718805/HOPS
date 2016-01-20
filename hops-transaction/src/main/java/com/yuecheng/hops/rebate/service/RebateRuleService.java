package com.yuecheng.hops.rebate.service;


import java.util.List;

import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.assist.RebateRuleAssist;


public interface RebateRuleService
{
    /**
     * 保存返佣配置
     * 
     * @param rebateRule
     * @return
     */
    public RebateRuleAssist saveRebateRule(RebateRule rebateRule);

    /**
     * 根据返佣配置ID删除返佣配置
     * 
     * @param rebateRuleId
     */
    public void deleteRebateRule(Long rebateRuleId);

    /**
     * 更新返佣配置信息状态
     * 
     * @param rebateRuleId
     * @param status
     */
    public void updateRebateRule(String loginUser,Long rebateRuleId, String status);

    /**
     * 根据返佣ID查找返佣配置
     * 
     * @param rebateRuleId
     * @return
     */
    public RebateRuleAssist queryRebateRuleById(Long rebateRuleId);

    /**
     * 获取所有返佣配置列表
     * 
     * @return
     */
    public List<RebateRuleAssist> getAllRebateRule();

    /**
     * 批量保存返佣配置
     * 
     * @param rebateRuleList
     * @return
     */
    public List<RebateRuleAssist> saveRebateRuleList(List<RebateRule> rebateRuleList);

}
