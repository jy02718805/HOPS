package com.yuecheng.hops.transaction.config.product.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.transaction.config.entify.product.QualityWeightRule;
import com.yuecheng.hops.transaction.config.product.QualityWeightRuleService;
import com.yuecheng.hops.transaction.config.repository.QualityWeightRuleDao;


@Service("qualityWeightRuleService")
public class QualityWeightRuleServiceImpl implements QualityWeightRuleService
{
    @Autowired
    private QualityWeightRuleDao qualityWeightRuleDao;

    @Override
    public QualityWeightRule getQualityWeightRule()
    {
        List<QualityWeightRule> qualityWeightRules = (List<QualityWeightRule>)qualityWeightRuleDao.findAll();
        if (qualityWeightRules.size() > 0)
        {
            QualityWeightRule qualityWeightRule = qualityWeightRules.get(0);
            return qualityWeightRule;
        }
        else
        {
            return null;
        }
    }

    @Override
    public QualityWeightRule updateOrSaveQualityWeightRule(QualityWeightRule qwr)
    {
        // TODO Auto-generated method stub
        qwr = qualityWeightRuleDao.save(qwr);
        return qwr;
    }

}
