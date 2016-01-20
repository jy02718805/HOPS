package com.yuecheng.hops.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.product.entity.history.ProductOperationHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationRule;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationRuleAssist;
import com.yuecheng.hops.product.repository.ProductOperationRuleDao;
import com.yuecheng.hops.product.service.ProductOperationRuleService;
@Service("productOperationRuleService")
public class ProductOperationRuleServiceImpl implements ProductOperationRuleService{

	@Autowired
    private ProductOperationRuleDao productOperationRuleDao;
	
	@Autowired
	private MerchantService merchantService;
	
	private static Logger logger = LoggerFactory.getLogger(ProductOperationRuleServiceImpl.class);
	@Override
	public ProductOperationRule saveProductOperationRule(
			ProductOperationRule productOperationRule) {
		productOperationRule=productOperationRuleDao.save(productOperationRule);
		return productOperationRule;
	}

	@Override
	public List<ProductOperationRule> queryProductOperationRuleByHisId(
			Long historyId) {
		List<ProductOperationRule> porList=productOperationRuleDao.queryProductOperationRuleByHisId(historyId);
		return porList;
	}

	@Override
	public List<ProductOperationRule> saveProductOperationRules(ProductOperationHistory productOperationHistory,
			String merchantIds,String updateUser) {
		try{
			logger.debug("saveProductOperationRules(productOperationHistory:"+productOperationHistory!=null?productOperationHistory.toString():"History为空"+"merchantIds:"+merchantIds+",updateUser:"+updateUser);
			List<ProductOperationRule> porList=new ArrayList<ProductOperationRule>();
			String[] merchant=merchantIds.split(Constant.StringSplitUtil.DECODE); 
			int i=0;
			while(i<merchant.length)
			{
				ProductOperationRule productOperationRule=new ProductOperationRule();
				productOperationRule.setHistoryId(productOperationHistory.getId());
				productOperationRule.setMerchantId(new Long(merchant[i]));
				productOperationRule.setMerchantType(productOperationHistory.getMerchantType());
				productOperationRule.setCreateDate(new Date());
				productOperationRule.setCreateUser(updateUser);
				productOperationRule=productOperationRuleDao.save(productOperationRule);
				porList.add(productOperationRule);
				i++;
			}
			
			return porList;
		}catch(Exception e){
			logger.error("[ProductOperationRuleServiceImpl:saveProductOperationRules(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveProductOperationRules"};
            ApplicationException ae = new ApplicationException("businesss100002", msgParams);
            throw ExceptionUtil.throwException(ae);
		}
	}

    @Override
    public List<ProductOperationRuleAssist> queryProductOperationRuleAssistByHisId(Long historyId)
    {
        List<ProductOperationRuleAssist> productOperationRuleAssists=new ArrayList<ProductOperationRuleAssist>();
        List<ProductOperationRule> productOperationRules=this.queryProductOperationRuleByHisId(historyId);
        for (ProductOperationRule productOperationRule : productOperationRules)
        {
            ProductOperationRuleAssist productOperationRuleAssist=new ProductOperationRuleAssist();
            Merchant merchant=(Merchant)merchantService.findIdentityByIdentityId(productOperationRule.getMerchantId(),IdentityType.MERCHANT);
            productOperationRuleAssist.setMerchantName(merchant.getMerchantName());
            productOperationRuleAssist.setProductOperationRule(productOperationRule);
            productOperationRuleAssists.add(productOperationRuleAssist);
        }
        return productOperationRuleAssists;
    }

    @Override
    public void delProductOperationRule(Long historyId)
    {
        List<ProductOperationRule> productOperationRules=this.queryProductOperationRuleByHisId(historyId);
        for (ProductOperationRule productOperationRule : productOperationRules)
        {
            productOperationRuleDao.delete(productOperationRule.getId());
        }
    }

}
