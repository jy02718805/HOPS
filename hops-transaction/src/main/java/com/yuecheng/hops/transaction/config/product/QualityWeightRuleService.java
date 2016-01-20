package com.yuecheng.hops.transaction.config.product;


import com.yuecheng.hops.transaction.config.entify.product.QualityWeightRule;


/**
 * 质量与权重比重配置
 * 
 * @author Jinger 2014-03-26
 */
public interface QualityWeightRuleService
{
    /**
     * 获取质量与权重比重配置
     * 
     * @return
     */
    public QualityWeightRule getQualityWeightRule();

    /**
     * 更新质量与权重比重配置
     * 
     * @param qwr
     *            质量与权重比重配置
     * @return
     */
    public QualityWeightRule updateOrSaveQualityWeightRule(QualityWeightRule qwr);
}
