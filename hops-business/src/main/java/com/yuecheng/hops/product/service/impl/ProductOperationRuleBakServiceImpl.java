package com.yuecheng.hops.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.product.entity.history.ProductOperationRuleBak;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationRuleAssist;
import com.yuecheng.hops.product.repository.ProductOperationRuleBakDao;
import com.yuecheng.hops.product.service.ProductOperationRuleBakService;
@Service("productOperationRuleBakService")
public class ProductOperationRuleBakServiceImpl implements ProductOperationRuleBakService{

	@Autowired
    private ProductOperationRuleBakDao productOperationRuleBakDao;
	
	@Autowired
	private MerchantService merchantService;
	
	@Override
	public List<ProductOperationRuleBak> queryProductOperationRuleByHisId(
			Long historyId) {
		List<ProductOperationRuleBak> porList=productOperationRuleBakDao.queryProductOperationRuleBakByHisId(historyId);
		return porList;
	}

    @Override
    public List<ProductOperationRuleAssist> queryProductOperationRuleAssistByHisId(Long historyId)
    {
        List<ProductOperationRuleAssist> productOperationRuleAssists=new ArrayList<ProductOperationRuleAssist>();
        List<ProductOperationRuleBak> productOperationRules=this.queryProductOperationRuleByHisId(historyId);
        for (ProductOperationRuleBak productOperationRule : productOperationRules)
        {
            ProductOperationRuleAssist productOperationRuleAssist=new ProductOperationRuleAssist();
            Merchant merchant=(Merchant)merchantService.findIdentityByIdentityId(productOperationRule.getMerchantId(),IdentityType.MERCHANT);
            productOperationRuleAssist.setMerchantName(merchant.getMerchantName());
            productOperationRuleAssist.setProductOperationRuleBak(productOperationRule);
            productOperationRuleAssists.add(productOperationRuleAssist);
        }
        return productOperationRuleAssists;
    }

}
