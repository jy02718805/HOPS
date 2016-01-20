package com.yuecheng.hops.rebate.service;


import java.util.List;

import com.yuecheng.hops.rebate.entity.RebateTradingVolume;


public interface RebateTradingVolumeService
{
    /**
     * 保存返佣规则
     * 
     * @param rebateTradingVolume
     * @return
     */
    public RebateTradingVolume saveRebateTradingVolume(RebateTradingVolume rebateTradingVolume);

    /**
     * 根据ID删除返佣规则
     * 
     * @param rebateTradingVolumeId
     */
    public void deleteRebateTradingVolume(Long rebateTradingVolumeId);

    /**
     * 根据ID查找返佣规则
     * 
     * @param rebateTradingVolumeId
     * @return
     */
    public RebateTradingVolume queryRebateTradingVolumeById(Long rebateTradingVolumeId);

    /**
     * 根据返佣ID和交易量获取返佣规则
     * 
     * @param rebateRuleID
     * @param tradingVolume
     * @return
     */
    public RebateTradingVolume queryRebateTradingVolumeByParams(Long rebateRuleID, Long tradingVolume);

    /**
     * 根据返佣ID获取返佣规则列表
     * 
     * @param rebateRuleID
     * @return
     */
    public List<RebateTradingVolume> queryRebateTradingVolumesByParams(Long rebateRuleID);

    /**
     * 根据返佣ID删除返佣规则
     * 
     * @param rebateRuleId
     */
    public void delRebateTradingVolumeByRuleId(Long rebateRuleId);

    /**
     * 根据返佣ID和规则字符串保存返佣规则
     * 
     * @param ruleId
     * @param tradingVolumeStr
     */
    public void saveRebateTradingVolume(Long ruleId, String tradingVolumeStr);
}
