package com.yuecheng.hops.mportal.web.transaction;


import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuecheng.hops.transaction.config.entify.product.QualityWeightRule;
import com.yuecheng.hops.transaction.config.product.QualityWeightRuleService;


@Controller
@RequestMapping(value = "/transaction")
public class QualityWeightRuleController
{
    @Autowired
    private QualityWeightRuleService qualityWeightRuleService;

    @RequestMapping(value = "/toUpdateQualityWeightRule")
    public String toUpdateQualityWeightRule(Model model)
    {
        QualityWeightRule qwr = qualityWeightRuleService.getQualityWeightRule();
        model.addAttribute("qwr", qwr);
        return "transaction/toUpdateQualityWeightRule";
    }

    @RequestMapping(value = "/doUpdateQualityWeightRule")
    public String doUpdateQualityWeightRule(QualityWeightRule qwr)
    {
        qualityWeightRuleService.updateOrSaveQualityWeightRule(qwr);
        return "redirect:/transaction/qualityWeightRuleList";
    }

    @RequestMapping(value = "/qualityWeightRuleList")
    public String qualityWeightRuleList(Model model, ServletRequest request)
    {
        QualityWeightRule qwr = qualityWeightRuleService.getQualityWeightRule();
        model.addAttribute("qwr", qwr);
        return "transaction/qualityWeightRuleList";
    }
}
