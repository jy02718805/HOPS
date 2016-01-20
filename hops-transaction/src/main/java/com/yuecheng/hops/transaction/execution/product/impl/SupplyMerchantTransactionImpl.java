package com.yuecheng.hops.transaction.execution.product.impl;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;
import com.yuecheng.hops.transaction.config.entify.monitor.SupplyMonitor;
import com.yuecheng.hops.transaction.config.entify.order.CalcQualityRule;
import com.yuecheng.hops.transaction.config.entify.product.QualityWeightRule;
import com.yuecheng.hops.transaction.config.repository.CalcQualityRuleDao;
import com.yuecheng.hops.transaction.config.repository.QualityWeightRuleDao;
import com.yuecheng.hops.transaction.config.repository.SupplyMonitorDao;
import com.yuecheng.hops.transaction.execution.product.SupplyMerchantTransaction;


@Service("supplyMerchantTransaction")
public class SupplyMerchantTransactionImpl implements SupplyMerchantTransaction
{
    private static final Logger          logger = LoggerFactory.getLogger(SupplyMerchantTransactionImpl.class);

    @Autowired
    private MerchantService              merchantService;

    @Autowired
    private ProductService               productService;

    @Autowired
    private SupplyMonitorDao             upMonitorDao;

    @Autowired
    private CalcQualityRuleDao           calcQualityRuleDao;

    @Autowired
    private QualityWeightRuleDao         qualityWeightRuledao;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private AgentProductRelationService  agentProductRelationService;

    /**
     * 总分 = 质量分*质量比重 + 价格分*价格比重 质量分=成功率分*成功率比重+速度分*速度比重 成功率分 = 成功订单数/总订单数 * 100 速度分= tanh(成功率/修正值)
     * * 100 价格分 价格分=100*(销售价格折扣-成本价格折扣)/(1-成本价格折扣）
     */
    @Override
    public void calculateSupplyProductQuality()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("in calculateSupplyProductQuality!");
        }
        Map<String, SearchFilter> filters = null;

        List<QualityWeightRule> qualityWeightRules = (List<QualityWeightRule>)qualityWeightRuledao.findAll();
        QualityWeightRule qualityWeightRule = qualityWeightRules.get(0);
        if (logger.isDebugEnabled())
        {
            logger.debug("in calculateSupplyProductQuality!:qualityWeightRule ["
                         + qualityWeightRule.toString() + "]");
        }
        // 获取所有上游商户
        List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);// !!!getAllUpMerchant();
        for (Iterator<Merchant> iterator = merchants.iterator(); iterator.hasNext();)
        {
            Merchant merchant = iterator.next();
            if (logger.isDebugEnabled())
            {
                logger.debug("in calculateSupplyProductQuality!:Merchant [" + merchant.toString()
                             + "]");
            }
            Map<String, Object> searchParams = new HashMap<String, Object>();
            // 获取此上游商户所有相关产品信息
            List<SupplyProductRelation> upr = supplyProductRelationService.getAllProductBySupplyMerchantId(
                searchParams, merchant.getId(), IdentityType.MERCHANT.toString(),
                Constant.SupplyProductStatus.OPEN_STATUS);// !!!getAllProductByMerchantId(merchant.getId());
            for (Iterator<SupplyProductRelation> iterator2 = upr.iterator(); iterator2.hasNext();)
            {
                SupplyProductRelation supplyProductRelation = iterator2.next();
                if (logger.isDebugEnabled())
                {
                    logger.debug("in calculateSupplyProductQuality!:supplyProductRelation ["
                                 + supplyProductRelation.toString() + "]");
                }
                filters = new HashMap<String, SearchFilter>();
                filters.put(EntityConstant.SupplyMonitor.MERCHANT_ID, new SearchFilter(
                    EntityConstant.SupplyMonitor.MERCHANT_ID, Operator.EQ, merchant.getId()));
                filters.put(EntityConstant.SupplyMonitor.PRODUCT_ID,
                    new SearchFilter(EntityConstant.SupplyMonitor.PRODUCT_ID, Operator.EQ,
                        supplyProductRelation.getProductId()));
                Specification<SupplyMonitor> spec_UpMonitor = DynamicSpecifications.bySearchFilter(
                    filters.values(), SupplyMonitor.class);
                SupplyMonitor upMonitor = upMonitorDao.findOne(spec_UpMonitor);
                //
                if (upMonitor != null)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("in calculateSupplyProductQuality!:upMonitor ["
                                     + upMonitor.toString() + "]");
                    }
                    BigDecimal quailty_count = new BigDecimal(0);
                    BigDecimal total_count = new BigDecimal(upMonitor.getTotal_count());
                    BigDecimal success_count = new BigDecimal(upMonitor.getSuccess_count());
                    BigDecimal success_ratio = success_count.divide(total_count);
                    // 成功率得分
                    BigDecimal success_point = success_ratio.multiply(new BigDecimal(100));

                    filters = new HashMap<String, SearchFilter>();
                    filters.put(EntityConstant.CalcQualityRule.ORDER_NUM_LOW,
                        new SearchFilter(EntityConstant.CalcQualityRule.ORDER_NUM_LOW,
                            Operator.LTE, upMonitor.getTotal_count()));
                    filters.put(EntityConstant.CalcQualityRule.ORDER_NUM_HIGH,
                        new SearchFilter(EntityConstant.CalcQualityRule.ORDER_NUM_HIGH,
                            Operator.GTE, upMonitor.getTotal_count()));
                    Specification<CalcQualityRule> spec_CalcQualityRule = DynamicSpecifications.bySearchFilter(
                        filters.values(), CalcQualityRule.class);
                    // 查询此时订单量与配置表匹配，得出修正值。
                    CalcQualityRule cqr = calcQualityRuleDao.findOne(spec_CalcQualityRule);
                    BigDecimal speed_point = new BigDecimal(Math.tanh(success_ratio.divide(
                        new BigDecimal(cqr.getFactor())).doubleValue())).multiply(new BigDecimal(
                        100));
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("in calculateSupplyProductQuality!:speed_point ["
                                     + speed_point + "]");
                    }
                    // 质量分=成功率分*成功率比重+速度分*速度比重
                    quailty_count = success_point.multiply(
                        new BigDecimal(qualityWeightRule.getSpeedWeight())).add(
                        speed_point.multiply(new BigDecimal(qualityWeightRule.getSpeedWeight())));
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("in calculateSupplyProductQuality!:quailty_count ["
                                     + quailty_count + "]");
                    }
                    // 更新上游商户与产品关系表中的质量分。
                    supplyProductRelation.setQuality(quailty_count);
                    supplyProductRelationService.editSupplyProductRelation(supplyProductRelation, Constant.Common.SYSTEM);
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("in calculateSupplyProductQuality!:editSupplyProductRelation!");
                    }
                }
                else
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("in calculateSupplyProductQuality!:upMonitor [null]");
                    }
                }
            }
        }
    }

}
